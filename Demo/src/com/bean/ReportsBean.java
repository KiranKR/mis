package com.bean;

public class ReportsBean {
	private String register = "";
	private String path = "";
	
	
	public ReportsBean() {
	}

	public ReportsBean(String register,String path) {
		super();
		this.register = register;
		this.path = path;
	}

	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	

	
}
