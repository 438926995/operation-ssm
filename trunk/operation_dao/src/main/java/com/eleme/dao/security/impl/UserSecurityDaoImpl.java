package com.eleme.dao.security.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.eleme.dao.ICommonDao;
import com.eleme.dao.security.IUserSecurityDao;
import com.eleme.util.CommonUtil;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * IUserLoginDao 实现类
 * 
 * @author huwenwen
 *
 */
@Service
public class UserSecurityDaoImpl implements IUserSecurityDao {

	/**
	 * log
	 */
	protected static final Log log = LogFactory.getLog(UserSecurityDaoImpl.class);

	@Inject
	protected ICommonDao commonDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eleme.dao.account.IUserLoginDao#findAllAuthorities()
	 */
	@Override
	public List<Map<String, Object>> findAllAuthorities() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT AUTH.AUTHORITIES_ID AS AUTHID,");
		sql.append(" AUTH.AUTH_NAME AS AUTHNAME,RES.RESOURCE_ID AS RESOURCEID,");
		sql.append(" RES.RESOURCE_STRING AS RESOURCESTRING,RES.RESOURCE_NAME AS RESOURCENAME");
		sql.append(" FROM T_ATR_AUTHORITIES AUTH");
		sql.append(" LEFT JOIN T_ATR_RESOURCE_AUTHORITIES RES_AUTH ON AUTH.AUTHORITIES_ID = RES_AUTH.AUTHORITIES_ID");
		sql.append(" LEFT JOIN M_RESOURCE RES ON RES.RESOURCE_ID = RES_AUTH.RESOURCE_ID");
		sql.append(" WHERE AUTH.IS_ENABLED = 1 AND RES.IS_ENABLED = 1 AND RES.RESOURCE_TYPE='URL' AND AUTH.PRJ_TYPE=0");
		sql.append(" ORDER BY AUTH.AUTHORITIES_ID,RES.RESOURCE_ID");
		try {
			return commonDao.getListBySpringJDBC(sql.toString());
		} catch (Exception e) {
			log.error(CommonUtil.getErrorMessage(e));
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eleme.dao.account.IUserLoginDao#findUsersSecurityByUserId(long,
	 * boolean)
	 */
	@Override
	public List<Map<String, Object>> findUsersSecurityByUserId(long userId, boolean isAdmin) {
		StringBuilder sql = new StringBuilder("SELECT DISTINCT T.* FROM( ");
		sql.append(" SELECT ROL.ROLE_ID AS ROLEID,ROL.ROLE_NAME AS ROLENAME,");
		sql.append(" AUTH.AUTHORITIES_ID AS AUTHID,AUTH.AUTH_NAME AS AUTHNAME,");
		sql.append(" RES.RESOURCE_ID AS RESOURCEID,RES.RESOURCE_STRING AS RESOURCESTRING");
		sql.append(" FROM M_USERS USR");
		sql.append(" LEFT JOIN T_ATR_USERS_ROLES USR_ROLE ON USR.USER_ID = USR_ROLE.USER_ID");
		sql.append(" LEFT JOIN T_ATR_ROLES ROL ON USR_ROLE.ROLE_ID = ROL.ROLE_ID");
		sql.append(" LEFT JOIN T_ATR_AUTHORITIES_ROLES AUTH_ROLE ON ROL.ROLE_ID = AUTH_ROLE.ROLE_ID");
		sql.append(" LEFT JOIN T_ATR_AUTHORITIES AUTH ON AUTH.AUTHORITIES_ID = AUTH_ROLE.AUTHORITIES_ID");
		sql.append(" LEFT JOIN T_ATR_RESOURCE_AUTHORITIES RES_AUTH ON AUTH.AUTHORITIES_ID = RES_AUTH.AUTHORITIES_ID");
		sql.append(" LEFT JOIN M_RESOURCE RES ON RES.RESOURCE_ID = RES_AUTH.RESOURCE_ID");
		sql.append(" WHERE  AUTH.IS_ENABLED = 1 AND RES.IS_ENABLED = 1");
		// 系统区分，此处可根据系统设置不同，设置不同type的值
		sql.append(" AND ROL.PRJ_TYPE=0 AND AUTH.PRJ_TYPE=0 AND RES.PRJ_TYPE=0 ");
		List<Object> paramList = new ArrayList<Object>();
		// 非超级管理员
		if (!isAdmin) {
			sql.append(" AND USR.USER_ID = ? ");
			paramList.add(userId);
		}
		// 默认权限
		sql.append(" UNION ALL");
		sql.append(" SELECT 0 AS ROLEID,'默认资源' AS ROLENAME,");
		sql.append(" AUTH.AUTHORITIES_ID AS AUTHID,AUTH.AUTH_NAME AS AUTHNAME,");
		sql.append(" RES.RESOURCE_ID AS RESOURCEID,RES.RESOURCE_STRING AS RESOURCESTRING");
		sql.append(" FROM T_ATR_AUTHORITIES AUTH ");
		sql.append(" LEFT JOIN T_ATR_RESOURCE_AUTHORITIES RES_AUTH ON AUTH.AUTHORITIES_ID = RES_AUTH.AUTHORITIES_ID");
		sql.append(" LEFT JOIN M_RESOURCE RES ON RES.RESOURCE_ID = RES_AUTH.RESOURCE_ID");
		sql.append(" WHERE AUTH.IS_ENABLED = 1 AND RES.IS_ENABLED = 1 AND AUTH.IS_DEFAULT=1");
		// 系统区分
		sql.append(" AND AUTH.PRJ_TYPE=0 AND RES.PRJ_TYPE=0 ");
		sql.append(" ) T ORDER BY T.ROLEID, T.AUTHID,T.RESOURCEID");
		try {
			return commonDao.getListBySpringJDBC(sql.toString(), paramList.toArray());
		} catch (Exception e) {
			log.error(CommonUtil.getErrorMessage(e));
		}
		return null;
	}

}
