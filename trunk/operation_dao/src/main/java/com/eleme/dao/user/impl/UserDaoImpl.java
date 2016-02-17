package com.eleme.dao.user.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eleme.bean.SessionBean;
import com.eleme.bean.security.LoginBean;
import com.eleme.bean.user.MUsers;
import com.eleme.bean.user.UserQueryBean;
import com.eleme.constants.PagerConstants;
import com.eleme.dao.ICommonDao;
import com.eleme.dao.user.IUserDao;
import com.eleme.util.CommonUtil;
import com.eleme.util.DateUtil;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * IUserDao实现类.
 * 
 * @author penglau
 *
 */
@Repository
public class UserDaoImpl implements IUserDao {

  /**
   * log
   */
  protected static final Log log = LogFactory.getLog(UserDaoImpl.class);

  @Inject
  protected ICommonDao commonDao;

  @Override
  public List<Map<String, Object>> userList(Integer currentPage, UserQueryBean uqb)
      throws SQLException, ParseException {
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT * FROM M_USERS where 1=1 ");

    List<Object> paramList = new ArrayList<Object>();
    if (uqb != null) {
      if (!StringUtils.isBlank(uqb.getUserName())) {
        sql.append(" AND USER_NAME LIKE ? ");
        paramList.add("%" + uqb.getUserName().trim() + "%");
      }
      if (!StringUtils.isBlank(uqb.getUserStatus())) {
        sql.append(" AND USER_STATUS = ? ");
        paramList.add(uqb.getUserStatus().trim());
      }
      if (!StringUtils.isBlank(uqb.getIsAdmin())) {
        sql.append(" AND IS_ADMIN = ? ");
        paramList.add(uqb.getIsAdmin());
      }
      if (!StringUtils.isBlank(uqb.getUserCreateDateRange())) {
        String startDateStr = uqb.getUserCreateDateRange().split(" - ")[0];
        String endDateStr = uqb.getUserCreateDateRange().split(" - ")[1] + " 23:59:59";
        Date startDate = DateUtils.parseDate(startDateStr, DateUtil.datePattern);
        Date endDate = DateUtils.parseDate(endDateStr, DateUtil.ISO_DATETIME_FORMAT);
        sql.append(" AND CREATED_AT > ? AND CREATED_AT < ? ");
        paramList.add(startDate);
        paramList.add(endDate);
      }
      // 分页显示
      // 第一次进入时，currentPage为空
      if (currentPage == null) {
        currentPage = 1;
      }
      sql.append(" limit ?,? ");
      paramList.add((currentPage - 1) * PagerConstants.PAGE_SIZE);
      paramList.add(PagerConstants.PAGE_SIZE);
    }
    return commonDao.getListBySpringJDBC(sql.toString(), paramList.toArray());
  }

  @Override
  public Integer userListCount(UserQueryBean uqb) throws SQLException {
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT COUNT(*) FROM M_USERS where 1=1 ");

    List<Object> paramList = new ArrayList<Object>();
    if (uqb != null) {
      if (!StringUtils.isBlank(uqb.getUserName())) {
        sql.append(" AND USER_NAME LIKE ? ");
        paramList.add("%" + uqb.getUserName().trim() + "%");
      }
      if (!StringUtils.isBlank(uqb.getUserStatus())) {
        sql.append(" AND USER_STATUS = ? ");
        paramList.add(uqb.getUserStatus().trim());
      }
      if (!StringUtils.isBlank(uqb.getIsAdmin())) {
        sql.append(" AND IS_ADMIN = ? ");
        paramList.add(uqb.getIsAdmin());
      }
    }
    return commonDao.getObjectBySpringJDBC(sql.toString(), Integer.class, paramList.toArray());
  }

  public Map<String, Object> findUserByName(String username) throws Exception {
    String sql = "SELECT * FROM M_USERS WHERE USER_NAME = ?";
    Map<String, Object> map = commonDao.getUniqueMap(sql.toString(), username);
    return map;
  }

  @Override
  public LoginBean findUserByUserName(String userName) {
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT MU.USER_ID AS USERID,MU.USER_NAME AS USERNAME,MU.PSWD AS HASHEDUSERPASS ");
    sql.append(" FROM M_USERS MU WHERE MU.USER_NAME = ? AND MU.USER_STATUS = 0 ");
    LoginBean loginBean = null;
    try {
      List<LoginBean> lblBeans =
          commonDao.getListBySpringJDBC(sql.toString(), LoginBean.class, userName);
      // TODO 此处需要禁止重名
      if (lblBeans.size() > 0) {
        loginBean = lblBeans.get(0);
      }
    } catch (Exception e) {
      log.error(CommonUtil.getErrorMessage(e));
    }
    return loginBean;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public int userAdd(MUsers mu) throws SQLException {
    StringBuffer insertSQLBuffer = new StringBuffer();
    insertSQLBuffer.append("INSERT INTO M_USERS (USER_NAME,PSWD,");
    if (mu.getUser_status() != null) {
      insertSQLBuffer.append("USER_STATUS,");
    }
    insertSQLBuffer.append("IS_ADMIN) VALUES (?,?,");
    if (mu.getUser_status() != null) {
      insertSQLBuffer.append("?,");
    }
    insertSQLBuffer.append("?) ");
    List<Object> params = new ArrayList<Object>();
    params.add(mu.getUser_name());
    params.add(mu.getPswd());

    if (mu.getUser_status() != null) {
      params.add(mu.getUser_status());
    }
    params.add(mu.getIs_admin());
    int lines = commonDao.updateEntityBySpringJDBC(insertSQLBuffer.toString(), params.toArray());
    log.info("添加用户{}，影响行数{}。", mu.getUser_name(), lines);
    return lines;
  }

  @Override
  public String findPwdByUserId(long userId) {
    try {
      return commonDao.getObjectBySpringJDBC("SELECT PSWD FROM M_USERS WHERE USER_ID=?",
          String.class, userId);
    } catch (SQLException e) {
      log.error(CommonUtil.getErrorMessage(e));
    }
    return null;
  }

  @Override
  public SessionBean findSessionBeanByUserId(long userId) {
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT MU.USER_ID AS userId,MU.USER_NAME AS userName,MU.IS_ADMIN AS admin ");
    sql.append(" FROM M_USERS MU WHERE MU.USER_ID = ? AND MU.USER_STATUS = 0");
    try {
      List<SessionBean> list =
          commonDao.getListBySpringJDBC(sql.toString(), SessionBean.class, userId);
      if (list != null && list.size() > 0) {
        return list.get(0);
      } else {
        return null;
      }
    } catch (Exception e) {
      log.error(CommonUtil.getErrorMessage(e));
    }
    return null;
  }

}
