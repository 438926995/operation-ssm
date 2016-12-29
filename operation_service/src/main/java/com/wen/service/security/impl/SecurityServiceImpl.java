package com.wen.service.security.impl;

import com.wen.bean.security.AuthoritiesBean;
import com.wen.bean.security.LoginBean;
import com.wen.bean.security.ResourcesBean;
import com.wen.bean.security.RolesBean;
import com.wen.dao.security.IUserSecurityDao;
import com.wen.service.security.ISecurityService;
import com.wen.service.user.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SecurityServiceImpl implements ISecurityService {

	/**
	 * log实例
	 */
	private static Logger log = LoggerFactory.getLogger(SecurityServiceImpl.class);
	/**
	 * 权限dao接口
	 */
	@Inject
	private IUserSecurityDao userSecurityDao;

	@Inject
	private IUserService userServiceImpl;

	@Override
	public List<AuthoritiesBean> findAllAuthorities() {
		List<Map<String, Object>> aaList = userSecurityDao.findAllAuthorities();
		long authId = -1;
		List<AuthoritiesBean> abList = new ArrayList<AuthoritiesBean>();
		List<ResourcesBean> resourceList = null;
		AuthoritiesBean ab = null;
		if(aaList != null){
    		for (Map<String, Object> tmap : aaList) {
    			long tauthId = Long.parseLong(tmap.get("AUTHID").toString());
    			if (authId != tauthId) {
    				resourceList = new ArrayList<ResourcesBean>();
    				ab = new AuthoritiesBean();
    				authId = tauthId;
    				abList.add(ab);
    			}
    			ab.setAuthId(tauthId);
    			ab.setAuthName(tmap.get("AUTHNAME").toString());
    			ResourcesBean rbBean = new ResourcesBean();
    			rbBean.setResourceId(Long.parseLong(tmap.get("RESOURCEID").toString()));
    			rbBean.setResourceName(tmap.get("RESOURCENAME").toString());
    			rbBean.setResourceString(tmap.get("RESOURCESTRING").toString());
    			resourceList.add(rbBean);
    			ab.setResourceList(resourceList);
    		}
		}
		return abList;
	}

	@Override
	public List<RolesBean> findUsersSecurityByUserId(long userId) {
		List<Map<String, Object>> usList = userSecurityDao.findUsersSecurityByUserId(userId,
				userServiceImpl.isAdminByUserId(userId));

		List<RolesBean> rbList = new ArrayList<RolesBean>();
		Long roleId = -1l;
		Long authId = -1l;
		for (int i = 0; i < usList.size(); i++) {
			if (roleId.longValue() != Long.valueOf(usList.get(i).get("ROLEID").toString()).longValue()) {
				RolesBean rb = new RolesBean();
				rb.setRoleName(usList.get(i).get("ROLENAME").toString());
				rb.setRoleId(Long.valueOf(usList.get(i).get("ROLEID").toString()));
				rb.setAuthList(new ArrayList<AuthoritiesBean>());
				rbList.add(rb);
			}

			// 此处完全用authId不等判断，会漏掉roleId不同但authId依然相等的情况
			if ((authId.longValue() != Long.valueOf(usList.get(i).get("AUTHID").toString()).longValue()) || ((roleId
					.longValue() != Long.valueOf(usList.get(i).get("ROLEID").toString()).longValue())
					&& (authId.longValue() == Long.valueOf(usList.get(i).get("AUTHID").toString()).longValue()))) {
				AuthoritiesBean ab = new AuthoritiesBean();
				ab.setAuthId(Long.valueOf(usList.get(i).get("AUTHID").toString()).longValue());
				ab.setAuthName(usList.get(i).get("AUTHNAME").toString());
				ab.setResourceList(new ArrayList<ResourcesBean>());
				rbList.get(rbList.size() - 1).getAuthList().add(ab);
			}

			ResourcesBean res = new ResourcesBean();
			res.setResourceId(Long.valueOf(usList.get(i).get("RESOURCEID").toString()).longValue());
			res.setResourceString(usList.get(i).get("RESOURCESTRING").toString());
			int index = rbList.get(rbList.size() - 1).getAuthList().size();
			try {
				rbList.get(rbList.size() - 1).getAuthList().get(index - 1).getResourceList().add(res);
			} catch (Exception e) {
			}

			roleId = Long.valueOf(usList.get(i).get("ROLEID").toString());
			authId = Long.valueOf(usList.get(i).get("AUTHID").toString());
		}
		return rbList;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("根据用户名{},查找相关数据，封装成spring security的User对象", username);
		LoginBean lb = userServiceImpl.findByUserName(username);
		// 查询出当前登录用户对象userId
		List<RolesBean> roleList = findUsersSecurityByUserId(lb.getUserId());
		// 得到该用户的所有权限 拿出所需要的数据 并封装成spring security自定义的权限对象中
		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(roleList);
		boolean enables = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		String pwd = userServiceImpl.findPwdByUserId(lb.getUserId());
		// pwd加密过，如果UsernamePasswordAuthenticationToken中的密码未加密，则会不通过。
		User userdetail = new User(username, pwd, enables, accountNonExpired, credentialsNonExpired, accountNonLocked,
				grantedAuths);
		return userdetail;
	}

	@Override
	public Set<GrantedAuthority> obtionGrantedAuthorities(List<RolesBean> roleList) {
		log.info("获取用户所有权限");
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		// 得到用户的所有角色集合
		List<String> rescourceStrlist = new ArrayList<String>();
		// 遍历角色集合
		for (RolesBean role : roleList) {
			List<AuthoritiesBean> auths = role.getAuthList(); // 取得角色拥有的权限
			// 得到所有角色的权限，封装到集合当中
			for (AuthoritiesBean auth : auths) {
				List<ResourcesBean> resourceList = auth.getResourceList(); // 取得权限对应的资源
				for (ResourcesBean rb : resourceList) {
					// 将用户的权限对应的资源String封装到spring security的权限对象集合中
					if (!rescourceStrlist.contains(rb.getResourceString())) {
						// TODO 此处SimpleGrantedAuthority内，放置了资源url。
						authSet.add(new SimpleGrantedAuthority(rb.getResourceString()));
						rescourceStrlist.add(rb.getResourceString());
					}
				}
			}
		}
		return authSet;
	}

}
