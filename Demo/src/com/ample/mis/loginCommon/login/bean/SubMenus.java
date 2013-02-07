package com.ample.mis.loginCommon.login.bean;

import java.io.Serializable;
import java.util.List;

public class SubMenus implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String subMenuId;
	private String subMenu;
	private String link;
	private List<LoginActBean> actBeans;

	public SubMenus(String subMenu, String link) {
		this.subMenu = subMenu;
		this.link = link;
	}
	public SubMenus(String subMenuId,String subMenu, String link) {
		this.subMenuId = subMenuId;
		this.subMenu = subMenu;
		this.link = link;
	}

	public String getSubMenuId() {
		return subMenuId;
	}

	public void setSubMenuId(String subMenuId) {
		this.subMenuId = subMenuId;
	}

	public String getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(String subMenu) {
		this.subMenu = subMenu;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<LoginActBean> getActBeans() {
		return actBeans;
	}

	public void setActBeans(List<LoginActBean> actBeans) {
		this.actBeans = actBeans;
	}
	
}

