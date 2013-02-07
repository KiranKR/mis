package com.bean;

public class StackBean {
	String lvl = "";
	String id = "";
	String name = "";
	int slno = 0;

	public StackBean() {
	}

	public StackBean(String lvl, String id) {
		this.id = id;
		this.lvl = lvl;
	}

	public StackBean(String lvl, String id, String name, int slno) {
		this.id = id;
		this.lvl = lvl;
		this.name = name;
		this.slno = slno;
	}

	public String getLvl() {
		return lvl;
	}

	public void setLvl(String lvl) {
		this.lvl = lvl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSlno() {
		return slno;
	}

	public void setSlno(int slno) {
		this.slno = slno;
	}

}
