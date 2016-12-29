package com.wen.service.user.impl;

import com.wen.bean.SessionBean;
import com.wen.bean.security.LoginBean;
import com.wen.bean.user.UserQueryBean;
import com.wen.constants.PagerConstants;
import com.wen.dao.user.IUserDao;
import com.wen.domain.user.MUsers;
import com.wen.domain.user.UserRole;
import com.wen.mapper.user.IUserMapper;
import com.wen.service.BaseService;
import com.wen.service.user.IUserService;
import com.wen.util.pager.TbData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = java.lang.Throwable.class)
public class UserServiceImpl extends BaseService implements IUserService {

  /**
   * log
   */
  private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

  @Inject
  private IUserDao userDaoImpl;

  @Inject
  private IUserMapper userMapper;

  @Inject
  private Md5PasswordEncoder passwordEncoder;

  @Transactional(readOnly = true)
  public TbData userList(Integer currentPage, UserQueryBean uqb) throws Exception {
    int totalCount = userDaoImpl.userListCount(uqb);
    if (currentPage == null) {
      currentPage = 1;
    }
    List<Map<String, Object>> list = userDaoImpl.userList(currentPage, uqb);
    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, list);
  }

  public boolean judgeIfUserNameSingle(String username) throws Exception {
    Map<String, Object> userMap = userDaoImpl.findUserByName(username);
    return userMap == null ? true : false;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
  public int userAdd(UserRole ur) throws SQLException {

    // 加密密码.
    String encryptPswd = passwordEncoder.encodePassword(ur.getPswd(), ur.getUserName());
    ur.setPswd(encryptPswd);
    ur.setUserStatus("0");
    //添加用户
    userMapper.insert(ur);
    //添加用户和角色关联表
    int result = userMapper.insertUserAndRole(ur);
    return result;
  }

  @Override
  public String findPwdByUserId(long userId) {
    return userDaoImpl.findPwdByUserId(userId);
  }

  @Override
  public LoginBean findByUserName(String userName) {
    return userDaoImpl.findUserByUserName(userName);
  }

  @Override
  public SessionBean findSessionBeanByUserId(long userId) {
    return userDaoImpl.findSessionBeanByUserId(userId);
  }

  @Override
  public boolean isAdminByUserId(long userId) {
    SessionBean sb = findSessionBeanByUserId(userId);
    return sb.isAdmin();
  }

  @Override
  @Transactional(readOnly = true, rollbackFor = java.lang.Throwable.class)
  public boolean judgePwd(long userId, String pwd) throws Exception {
    MUsers mu = userMapper.selectById((int) userId);
    // 加密密码.
    String encryptPswd = passwordEncoder.encodePassword(pwd, mu.getUserName());
    if (!encryptPswd.equals(mu.getPswd())) {
      return true;
    }
    return false;
  }

  @Override
  public int userEdit(MUsers mu) throws SQLException {
    // 加密密码.
    String encryptPswd = passwordEncoder.encodePassword(mu.getPswd(), mu.getUserName());
    mu.setPswd(encryptPswd);
    userMapper.updateById(mu);
    return userMapper.updateById(mu);
  }

  @Override
  public UserRole getUserInfoById(Integer userId) {
    return userMapper.userInfo(userId);
  }

  @Override
  public int updateUserAndRole(UserRole ur) {
    //更行时间
    ur.setUpdatedAt(new Date());
    userMapper.updateById(ur);
    //更行用户和角色表之前先判断是否存在
    int lines = 0;
    if(userMapper.isUserIdExist(ur.getUserId()) == 0){
      //不存在插入
      lines = userMapper.insertUserAndRole(ur);
    } else {
      //存在更行
      lines = userMapper.updateUserAndRole(ur);
    }
    return lines;
  }

  @Override
  public int updateUser(Integer id) {
    return userMapper.updateUserOfDel(id);
  }

  @Override
  public List<UserRole> getUserInfoByRoleId(Integer roleId) {
    return userMapper.selectUserByRoleId(roleId);
  }

  @Override
  public int delUserAndRole(UserRole ur) {
    return userMapper.deleteUserAndRole(ur);
  }

}
