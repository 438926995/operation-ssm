package com.eleme.domain.mart.martusers;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户基础信息画面显示封装类
 * 
 * @author yonglin.zhu
 *
 */
public class MMartUsers implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8698635835241223630L;

	// 主键ID
	private Integer userId;
	// 用户名
	private String userName;
	// 用户密码
	private String userPswd;
	// 用户标识1 商家，2 金融机构'
	private Integer userFlag;
	// 用户状态1 有效，2 冻结
	private Integer userStatus;
	// 1 帐号密码登录
	private Integer loginMethod;
	// 创建者ID
	private Integer createUser;
	// 创建者名
	private String createUserName;
	// 创建 时间
	private Date createTime;
	// 昵称
	private String nickName;
	// 手机号
	private String mobilePhone;
	// 邮箱
	private String userEmail;
	// 所属金融机构
	private Integer foId;
	// 性别
	private String userSex;
	// 年龄
	private Integer userAge;
	// 身份证号
	private String userSid;
	// 住址
	private String userAddr;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getUserPswd() {
		return userPswd;
	}

	public void setUserPswd(String userPswd) {
		this.userPswd = userPswd == null ? null : userPswd.trim();
	}

	public Integer getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(Integer userFlag) {
		this.userFlag = userFlag;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public Integer getLoginMethod() {
		return loginMethod;
	}

	public void setLoginMethod(Integer loginMethod) {
		this.loginMethod = loginMethod;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName == null ? null : createUserName.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName == null ? null : nickName.trim();
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail == null ? null : userEmail.trim();
	}

	public Integer getFoId() {
		return foId;
	}

	public void setFoId(Integer foId) {
		this.foId = foId;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public Integer getUserAge() {
		return userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

	public String getUserSid() {
		return userSid;
	}

	public void setUserSid(String userSid) {
		this.userSid = userSid;
	}

	public String getUserAddr() {
		return userAddr;
	}

	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}

}
