package com.bean;

public class MNRBean {
	
	
	
	
	
	private int mnrNineOpenBal=0;
	private int mnrInstall=0;
	private int mnrReplace=0;
	private int mnrNineCloseBal=0;
	private int mnrGr1MntId=0;
	
	private int mnrTenOpenBal=0;
	private int gtThrmnrInsall=0;
	private int gtThrmnrReplace=0;
	private int mnrTenCloseBal=0;
	private int mnrGr3MntId=0;
	
	private int mnrElevenOpenBal=0;
	private int gtSixmnrInstall=0;
	private int gtSixmnrReplace=0;
	private int mnrElevenCloseBal=0;
	private int mnrGr6MntId=0;
	
	
	
	
	
	
	
	
	
	
	
	
	private String mnrRsonFrNtReplace="";
	
	private String sectionId = "";
	private String monthId = "";
	private String yearId = "" ;
	
	
	private int totalInstall;
	private int totalReplaced;
	private int totalOpenBal;
	private int totalCloseBal;

	boolean onlyView = false;
	private int transHeadId=0;
	
	private boolean viewOnlyOB = false;
	
	
	public boolean isViewOnlyOB() {
		return viewOnlyOB;
	}
	public void setViewOnlyOB(boolean viewOnlyOB) {
		this.viewOnlyOB = viewOnlyOB;
	}
	public int getMnrGr1MntId() {
		return mnrGr1MntId;
	}
	public void setMnrGr1MntId(int mnrGr1MntId) {
		this.mnrGr1MntId = mnrGr1MntId;
	}
	public int getMnrGr3MntId() {
		return mnrGr3MntId;
	}
	public void setMnrGr3MntId(int mnrGr3MntId) {
		this.mnrGr3MntId = mnrGr3MntId;
	}
	public int getMnrGr6MntId() {
		return mnrGr6MntId;
	}
	public void setMnrGr6MntId(int mnrGr6MntId) {
		this.mnrGr6MntId = mnrGr6MntId;
	}
	public int getTransHeadId() {
		return transHeadId;
	}
	public void setTransHeadId(int transHeadId) {
		this.transHeadId = transHeadId;
	}
	public boolean isOnlyView() {
		return onlyView;
	}
	public void setOnlyView(boolean onlyView) {
		this.onlyView = onlyView;
	}
	public int getMnrNineOpenBal() {
		return mnrNineOpenBal;
	}
	public void setMnrNineOpenBal(int mnrNineOpenBal) {
		this.mnrNineOpenBal = mnrNineOpenBal;
	}
	public int getMnrInstall() {
		return mnrInstall;
	}
	public void setMnrInstall(int mnrInstall) {
		this.mnrInstall = mnrInstall;
	}
	public int getMnrReplace() {
		return mnrReplace;
	}
	public void setMnrReplace(int mnrReplace) {
		this.mnrReplace = mnrReplace;
	}
	public int getMnrNineCloseBal() {
		return mnrNineCloseBal;
	}
	public void setMnrNineCloseBal(int mnrNineCloseBal) {
		this.mnrNineCloseBal = mnrNineCloseBal;
	}
	public int getMnrTenOpenBal() {
		return mnrTenOpenBal;
	}
	public void setMnrTenOpenBal(int mnrTenOpenBal) {
		this.mnrTenOpenBal = mnrTenOpenBal;
	}
	public int getGtThrmnrInsall() {
		return gtThrmnrInsall;
	}
	public void setGtThrmnrInsall(int gtThrmnrInsall) {
		this.gtThrmnrInsall = gtThrmnrInsall;
	}
	public int getGtThrmnrReplace() {
		return gtThrmnrReplace;
	}
	public void setGtThrmnrReplace(int gtThrmnrReplace) {
		this.gtThrmnrReplace = gtThrmnrReplace;
	}
	public int getMnrTenCloseBal() {
		return mnrTenCloseBal;
	}
	public void setMnrTenCloseBal(int mnrTenCloseBal) {
		this.mnrTenCloseBal = mnrTenCloseBal;
	}
	public int getMnrElevenOpenBal() {
		return mnrElevenOpenBal;
	}
	public void setMnrElevenOpenBal(int mnrElevenOpenBal) {
		this.mnrElevenOpenBal = mnrElevenOpenBal;
	}
	public int getGtSixmnrInstall() {
		return gtSixmnrInstall;
	}
	public void setGtSixmnrInstall(int gtSixmnrInstall) {
		this.gtSixmnrInstall = gtSixmnrInstall;
	}
	public int getGtSixmnrReplace() {
		return gtSixmnrReplace;
	}
	public void setGtSixmnrReplace(int gtSixmnrReplace) {
		this.gtSixmnrReplace = gtSixmnrReplace;
	}
	public int getMnrElevenCloseBal() {
		return mnrElevenCloseBal;
	}
	public void setMnrElevenCloseBal(int mnrElevenCloseBal) {
		this.mnrElevenCloseBal = mnrElevenCloseBal;
	}
	public String getMnrRsonFrNtReplace() {
		return mnrRsonFrNtReplace;
	}
	public void setMnrRsonFrNtReplace(String mnrRsonFrNtReplace) {
		this.mnrRsonFrNtReplace = mnrRsonFrNtReplace;
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
	public int getTotalInstall() {
		return totalInstall;
	}
	public void setTotalInstall(int totalInstall) {
		this.totalInstall = totalInstall;
	}
	public int getTotalReplaced() {
		return totalReplaced;
	}
	public void setTotalReplaced(int totalReplaced) {
		this.totalReplaced = totalReplaced;
	}
	public int getTotalOpenBal() {
		return totalOpenBal;
	}
	public void setTotalOpenBal(int totalOpenBal) {
		this.totalOpenBal = totalOpenBal;
	}
	public int getTotalCloseBal() {
		return totalCloseBal;
	}
	public void setTotalCloseBal(int totalCloseBal) {
		this.totalCloseBal = totalCloseBal;
	}
	
	
	
		
}
