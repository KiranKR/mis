package com.bean;


public class RevenueBean {
	
	private String sectionId = "";
	private String monthId = "";
	private String yearId = "";
	private int totalNoInstall=0;
	private int inputEnergy=0;
	private int engSoldPerDCB=0;
	
	private double openBal=0.0;
	private double closeBal=0.0;
	private double demand=0.0;
	private double collection=0.0;
	
	private String reason = "";
	private String transHeadId = "";
	
	private int balanceAsonID;
	private int revenueID;
	
	
	boolean onlyView = false;
	private boolean viewOlyOB = false; 
	
	private String sectionNme="";
	
	
	public String getSectionNme() {
		return sectionNme;
	}
	public void setSectionNme(String sectionNme) {
		this.sectionNme = sectionNme;
	}
	public boolean isViewOlyOB() {
		return viewOlyOB;
	}
	public void setViewOlyOB(boolean viewOlyOB) {
		this.viewOlyOB = viewOlyOB;
	}
	public boolean isOnlyView() {
		return onlyView;
	}
	public void setOnlyView(boolean onlyView) {
		this.onlyView = onlyView;
	}
	public int getRevenueID() {
		return revenueID;
	}
	public void setRevenueID(int revenueID) {
		this.revenueID = revenueID;
	}
	public int getBalanceAsonID() {
		return balanceAsonID;
	}
	public void setBalanceAsonID(int balanceAsonID) {
		this.balanceAsonID = balanceAsonID;
	}
	public String getTransHeadId() {
		return transHeadId;
	}
	public void setTransHeadId(String transHeadId) {
		this.transHeadId = transHeadId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getMonthId() {
		return monthId;
	}
	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}
	public String getYearId() {
		return yearId;
	}
	public void setYearId(String yearId) {
		this.yearId = yearId;
	}
	public int getTotalNoInstall() {
		return totalNoInstall;
	}
	public void setTotalNoInstall(int totalNoInstall) {
		this.totalNoInstall = totalNoInstall;
	}
	public int getInputEnergy() {
		return inputEnergy;
	}
	public void setInputEnergy(int inputEnergy) {
		this.inputEnergy = inputEnergy;
	}
	public int getEngSoldPerDCB() {
		return engSoldPerDCB;
	}
	public void setEngSoldPerDCB(int engSoldPerDCB) {
		this.engSoldPerDCB = engSoldPerDCB;
	}
	public double getOpenBal() {
		return openBal;
	}
	public void setOpenBal(double openBal) {
		this.openBal = openBal;
	}
	public double getCloseBal() {
		return closeBal;
	}
	public void setCloseBal(double closeBal) {
		this.closeBal = closeBal;
	}
	public double getDemand() {
		return demand;
	}
	public void setDemand(double demand) {
		this.demand = demand;
	}
	public double getCollection() {
		return collection;
	}
	public void setCollection(double collection) {
		this.collection = collection;
	}
	
	
	
	
}
