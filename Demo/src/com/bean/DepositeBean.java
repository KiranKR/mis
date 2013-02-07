package com.bean;


public class DepositeBean {
	
	String id ="";
	String depParticulars = "";
	double amnt = 0.0;
	private boolean amtChecked;
	private String  amtApplicable=""; 
	
	public DepositeBean() {
	}
	
	public DepositeBean(String depParticulars,double amnt) {
		this.depParticulars = depParticulars;
		this.amnt = amnt;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDepParticulars() {
		return depParticulars;
	}
	public void setDepParticulars(String depParticulars) {
		this.depParticulars = depParticulars;
	}

	public double getAmnt() {
		return amnt;
	}

	public void setAmnt(double amnt) {
		this.amnt = amnt;
	}

	public boolean isAmtChecked() {
		return amtChecked;
	}

	public void setAmtChecked(boolean amtChecked) {
		this.amtChecked = amtChecked;
	}

	public String getAmtApplicable() {
		return amtApplicable;
	}

	public void setAmtApplicable(String amtApplicable) {
		this.amtApplicable = amtApplicable;
	}
	
	
}
