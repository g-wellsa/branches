package com.djh.weixiaotong.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 用户实体类
 * @author Administrator
 * 
 */
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private long userId; // 用户ID
	private long lastModified; // 登录日期
	private boolean authorize; // 是否认证
	private String userName; // 用户名字
	private String userNick; // 用户昵称
	private String userPwd; // 用户密码
	private String userHead; // 用户头像(超链接)

	private String userAge; // 用户年龄
	private String birthday; // 用户出生日期
	private String userSex; // 用户性别
	// private String user_star; //用户星座
	private String userRole; // 用户角色
	private String userIdentity; // 跟学生身份
	// private String user_blood; //用户血型
	private String userEmail; // 用户邮箱
	private String userPhone; // 用户手机
	private String userRemark; // 备注名
	private String userAddress; // 用户地址

	private String signature; // 个性签名
	private String[] gallery; // 用户相册集(多个超链接,特殊字符区别)

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public boolean isAuthorize() {
		return authorize;
	}

	public void setAuthorize(boolean authorize) {
		this.authorize = authorize;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserHead() {
		return userHead;
	}

	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}

	public String getUserAge() {
		return userAge;
	}

	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserIdentity() {
		return userIdentity;
	}

	public void setUserIdentity(String userIdentity) {
		this.userIdentity = userIdentity;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserRemark() {
		return userRemark;
	}

	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String[] getGallery() {
		return gallery;
	}

	public void setGallery(String[] gallery) {
		this.gallery = gallery;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "UserEntity [userId=" + userId + ", lastModified="
				+ lastModified + ", authorize=" + authorize + ", userName="
				+ userName + ", userNick=" + userNick + ", userPwd=" + userPwd
				+ ", userHead=" + userHead + ", userAge=" + userAge
				+ ", birthday=" + birthday + ", userSex=" + userSex
				+ ", userRole=" + userRole + ", userIdentity=" + userIdentity
				+ ", userEmail=" + userEmail + ", userPhone=" + userPhone
				+ ", userRemark=" + userRemark + ", userAddress=" + userAddress
				+ ", signature=" + signature + ", gallery="
				+ Arrays.toString(gallery) + "]";
	}

}
