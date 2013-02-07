package com.ample.mis.loginCommon.login.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Menu implements Serializable{
	private String name;
	private ArrayList<SubMenus> subMenus;
	private int id;
	public Menu() {
	}

	public Menu(String name, ArrayList<SubMenus> subMenus, int id) {
		this.name = name;
		this.subMenus = subMenus;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<SubMenus> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(ArrayList<SubMenus> subMenus) {
		this.subMenus = subMenus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
