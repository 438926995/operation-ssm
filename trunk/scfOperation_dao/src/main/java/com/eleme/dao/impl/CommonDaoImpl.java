package com.eleme.dao.impl;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Service;

import com.eleme.dao.ICommonDao;
import com.eleme.util.pager.PackagingParamsCondition;

/**
 * ICommonDao接口实现类
 * 
 * @author penglau
 *
 */
@Service
public class CommonDaoImpl implements ICommonDao {

  // 主数据库
  @Inject
  private JdbcTemplate jdbcTemplate;

  /*
   * (non-Javadoc)
   * 
   * @see com.eleme.dao.ICommonDao#getListBySpringJDBC(java.lang.String, java.lang.Object)
   */
  @Override
  public <T> List<Map<String, Object>> getListBySpringJDBC(String sql, Object... params)
      throws SQLException {
    return jdbcTemplate.queryForList(sql, params);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.eleme.dao.ICommonDao#getUniqueMap(java.lang.String, java.lang.Object)
   */
  public Map<String, Object> getUniqueMap(String sql, Object... params) throws SQLException {
    List<Map<String, Object>> mapList = getListBySpringJDBC(sql.toString(), params);
    // 判断集合是否为空
    if (mapList.size() > 0) {
      return mapList.get(0);
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.eleme.dao.ICommonDao#getListBySpringJDBC(java.lang.String, java.lang.Class,
   * java.lang.Object)
   */
  @Override
  public <T> List<T> getListBySpringJDBC(String sql, Class<T> objClass, Object... params)
      throws Exception {
    List<Map<String, Object>> mapList = getListBySpringJDBC(sql, params);
    return PackagingParamsCondition.packagingMapListToObjectList(mapList, objClass);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.eleme.dao.ICommonDao#getObjectBySpringJDBC(java.lang.String, java.lang.Class,
   * java.lang.Object)
   */
  @Override
  public <T> T getObjectBySpringJDBC(String sql, Class<T> objType, Object... params)
      throws SQLException {
    return jdbcTemplate.queryForObject(sql, objType, params);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.eleme.dao.ICommonDao#updateEntityBySpringJDBC(java.lang.String, java.lang.Object)
   */
  @Override
  public int updateEntityBySpringJDBC(String sql, Object... params) throws SQLException {
    return jdbcTemplate.update(sql, params);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.eleme.dao.ICommonDao#batchUpdateEntitysBySpringJDBC(java.lang.String)
   */
  @Override
  public int[] batchUpdateEntitysBySpringJDBC(String... sql) throws SQLException {
    return jdbcTemplate.batchUpdate(sql);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.eleme.dao.ICommonDao#batchUpdateEntitysBySpringJDBC(java.lang.String, java.util.List)
   */
  @Override
  public int[] batchUpdateEntitysBySpringJDBC(String sql, List<Object[]> list) throws SQLException {
    return jdbcTemplate.batchUpdate(sql, list);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.eleme.dao.ICommonDao#runUpdateReturnId(java.lang.String, java.lang.Object)
   */
  @Override
  public int runUpdateReturnId(final String sql, final Object... params) throws SQLException {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < params.length; i++) {
          ps.setObject(i + 1, params[i]);
        }
        return ps;
      }
    }, keyHolder);
    return keyHolder.getKey().intValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.eleme.dao.ICommonDao#produceGetUniqueMap(java.lang.String, java.util.List,
   * java.lang.Object)
   */
  @Override
  public Map<String, Object> produceGetUniqueMap(final String sql, final List<String> outParams,
      final Object... inParams) {
    return getJdbcTemplate().execute(sql, new CallableStatementCallback<Map<String, Object>>() {
      public Map<String, Object> doInCallableStatement(CallableStatement cs)
          throws SQLException, DataAccessException {
        int paramsIndex = 1;
        // 输入参数
        for (Object inParam : inParams) {
          cs.setObject(paramsIndex++, inParam);
        }
        // 注册输出参数的类型 (VARCHAR)
        for (int i = 0; i < outParams.size(); i++) {
          cs.registerOutParameter(paramsIndex++, Types.VARCHAR);
        }
        cs.execute();
        Map<String, Object> rMap = new HashMap<String, Object>();
        for (String outParam : outParams) {
          rMap.put(outParam, cs.getObject(outParam));
        }
        return rMap;
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.eleme.dao.ICommonDao#getBlobAsBinaryStream(java.lang.String, java.lang.Object)
   */
  @Override
  public List<InputStream> getBlobAsBinaryStream(String sql, Object... params) {
    return jdbcTemplate.query(sql, params, new ResultSetExtractor<List<InputStream>>() {
      public List<InputStream> extractData(ResultSet rs) throws SQLException, DataAccessException {
        LobHandler lobHandler = new DefaultLobHandler();
        List<InputStream> list = new ArrayList<InputStream>();
        while (rs.next()) {
          list.add(lobHandler.getBlobAsBinaryStream(rs, 1));
        }
        return list;
      }
    });
  }


  /****************** get and set *******************************************/

  /**
   * @return the jdbcTemplate.
   */
  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  /**
   * @param jdbcTemplate the jdbcTemplate to set.
   */
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

}
