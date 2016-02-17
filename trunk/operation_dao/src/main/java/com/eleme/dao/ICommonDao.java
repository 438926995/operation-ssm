package com.eleme.dao;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * commonDao接口.
 * 
 * @author penglau
 *
 */
public interface ICommonDao {

  /**
   * 根据参数查找记录.
   * 
   * @param sql sql语句
   * @param params 参数
   * @return 符合条件的记录
   * @throws SQLException
   */
  <T> List<Map<String, Object>> getListBySpringJDBC(String sql, Object... params)
      throws SQLException;

  /**
   * 根据参数查找唯一记录（unique:取第一条）.
   * 
   * @param sql sql语句
   * @param params 参数
   * @return 符合条件的记录
   * @throws SQLException
   */
  public Map<String, Object> getUniqueMap(String sql, Object... params) throws SQLException;

  /**
   * 根据参数查找记录,并将查找结果封装成指定Class类型的List集合。
   * 
   * @param sql sql语句
   * @param objClass 指定的Class类型
   * @param params 参数
   * @return 指定Class类型的List集合
   * @throws Exception
   */
  <T> List<T> getListBySpringJDBC(String sql, Class<T> objClass, Object... params) throws Exception;

  /**
   * 执行sql查询，并将结果封装成指定Class类型（注意查找结果为空或大于1的情况）.
   * 
   * @param sql sql语句
   * @param objType 指定的Class类型
   * @param params 参数
   * @return 转换后的结果
   * @throws SQLException
   */
  <T> T getObjectBySpringJDBC(String sql, Class<T> objType, Object... params) throws SQLException;

  /**
   * 更新操作.
   * 
   * @param sql sql语句
   * @param params 参数
   * @return 影响行数
   * @throws SQLException
   */
  int updateEntityBySpringJDBC(String sql, Object... params) throws SQLException;

  /**
   * 批量更新
   * 
   * @param sql sql语句集合
   * @return 每条sql影响行数的数组
   * @throws SQLException
   */
  int[] batchUpdateEntitysBySpringJDBC(String... sql) throws SQLException;

  /**
   * 批量更新（参数）
   * 
   * @param sql sql语句
   * @param list 参数集合
   * @return 每条sql影响行数的数组
   * @throws SQLException
   */
  int[] batchUpdateEntitysBySpringJDBC(String sql, List<Object[]> list) throws SQLException;

  /**
   * 执行更新，并返回主键.
   * 
   * @param sql sql语句
   * @param params 参数
   * @return 主键
   * @throws SQLException
   */
  int runUpdateReturnId(String sql, Object... params) throws SQLException;

  /**
   * 执行存储过程
   * 
   * @param sql eg. {call testpro(?,?)}
   * @param outParams 输出参数
   * @param inParams 输入参数
   * @return 执行结果的map集合
   */
  Map<String, Object> produceGetUniqueMap(String sql, List<String> outParams, Object... inParams);

  /**
   * 查找出blob对象并转换成流对象.
   * 
   * @param sql sql语句
   * @param params 参数
   * @return
   */
  List<InputStream> getBlobAsBinaryStream(String sql, Object... params);

}
