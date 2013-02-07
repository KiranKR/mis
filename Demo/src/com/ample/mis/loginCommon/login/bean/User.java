package com.ample.mis.loginCommon.login.bean;



public class User {

	private String userName="";
	private String password="";
	private int uMapIdenFlag; 
	private String userId = "";
	private String loginId = "";
	private String roleId = "";
	private String roleNme = "";
	
	
 	
	
	public String getRoleNme() {
		return roleNme;
	}
	public void setRoleNme(String roleNme) {
		this.roleNme = roleNme;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getuMapIdenFlag() {
		return uMapIdenFlag;
	}
	public void setuMapIdenFlag(int uMapIdenFlag) {
		this.uMapIdenFlag = uMapIdenFlag;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
