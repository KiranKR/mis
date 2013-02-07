package com.ample.mis.loginCommon.login.bean;

import java.util.ArrayList;

public class UserDetailsBean {
	
	private String usrId;
	private String usrName;
	private ArrayList<Menu> menus = new ArrayList<Menu>();
	private String roleNme = "";
	private String mdGpNme = "";
	
	private int id = 0;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMdGpNme() {
		return mdGpNme;
	}
	public void setMdGpNme(String mdGpNme) {
		this.mdGpNme = mdGpNme;
	}
	public String getRoleNme() {
		return roleNme;
	}
	public void setRoleNme(String roleNme) {
		this.roleNme = roleNme;
	}
	public ArrayList<Menu> getMenus() {
		return menus;
	}
	public void setMenus(ArrayList<Menu> menus) {
		this.menus = menus;
	}
	public String getUsrId() {
		return usrId;
	}
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}
	public String getUsrName() {
		return usrName;
	}
	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}
	
	
	public static void main(String[] args) {
		String a = "jt_abc18";
		String []b = a.split("[^0-9]+");
		System.out.println(b[1]);
		
	}
	
}
