package com.bean;

public class DoorLockBean {

	private int secId=0;
	private String yearId="";
	private String monthId="";
	private int rowStatus=0;
	private int idenFlag=0;
	
	private int noOFDoorLock=0;
	private int opBalance=0;
	private int noticeServed=0;
	private int metersShifted=0;
	private int notShift=0;
	private int cloBalance=0;
	
	
	private int gtSixNoOFDoorLock=0;
	private int gtSixOpBalance=0;
	private int gtSixNoticeServed=0;
	private int gtSixMetersShifted=0;
	private int gtSixNotShift=0;
	private int gtSixCloBalance=0;
	
	
	private String actionTaken="";
	private int thId =0;
    private String drThId ="";
    
    boolean newOB = true;
	boolean existing = true;
	boolean onlyView = false;
	
	
	
/*	newly  added*/
	
	
	private int totalNoOfInstances;
	private int totalOpeningBalance;
	private int totalNoticesServed;
	private int totalMeterShifted;
	private int totalNotPossibleShift;
	private int totalClosingBalance;
	
	
	
    
	private boolean viewOlyOB = false;  
	
	
	public int getSecId() {
		return secId;
	}


	public void setSecId(int secId) {
		this.secId = secId;
	}


	public String getYearId() {
		return yearId;
	}


	public void setYearId(String yearId) {
		this.yearId = yearId;
	}


	public String getMonthId() {
		return monthId;
	}


	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}


	public int getRowStatus() {
		return rowStatus;
	}


	public void setRowStatus(int rowStatus) {
		this.rowStatus = rowStatus;
	}


	public int getIdenFlag() {
		return idenFlag;
	}


	public void setIdenFlag(int idenFlag) {
		this.idenFlag = idenFlag;
	}


	public int getNoOFDoorLock() {
		return noOFDoorLock;
	}


	public void setNoOFDoorLock(int noOFDoorLock) {
		this.noOFDoorLock = noOFDoorLock;
	}


	public int getOpBalance() {
		return opBalance;
	}


	public void setOpBalance(int opBalance) {
		this.opBalance = opBalance;
	}


	public int getNoticeServed() {
		return noticeServed;
	}


	public void setNoticeServed(int noticeServed) {
		this.noticeServed = noticeServed;
	}


	public int getMetersShifted() {
		return metersShifted;
	}


	public void setMetersShifted(int metersShifted) {
		this.metersShifted = metersShifted;
	}


	public int getNotShift() {
		return notShift;
	}


	public void setNotShift(int notShift) {
		this.notShift = notShift;
	}


	public int getCloBalance() {
		return cloBalance;
	}


	public void setCloBalance(int cloBalance) {
		this.cloBalance = cloBalance;
	}


	public int getGtSixNoOFDoorLock() {
		return gtSixNoOFDoorLock;
	}


	public void setGtSixNoOFDoorLock(int gtSixNoOFDoorLock) {
		this.gtSixNoOFDoorLock = gtSixNoOFDoorLock;
	}


	public int getGtSixOpBalance() {
		return gtSixOpBalance;
	}


	public void setGtSixOpBalance(int gtSixOpBalance) {
		this.gtSixOpBalance = gtSixOpBalance;
	}


	public int getGtSixNoticeServed() {
		return gtSixNoticeServed;
	}


	public void setGtSixNoticeServed(int gtSixNoticeServed) {
		this.gtSixNoticeServed = gtSixNoticeServed;
	}


	public int getGtSixMetersShifted() {
		return gtSixMetersShifted;
	}


	public void setGtSixMetersShifted(int gtSixMetersShifted) {
		this.gtSixMetersShifted = gtSixMetersShifted;
	}


	public int getGtSixNotShift() {
		return gtSixNotShift;
	}


	public void setGtSixNotShift(int gtSixNotShift) {
		this.gtSixNotShift = gtSixNotShift;
	}


	public int getGtSixCloBalance() {
		return gtSixCloBalance;
	}


	public void setGtSixCloBalance(int gtSixCloBalance) {
		this.gtSixCloBalance = gtSixCloBalance;
	}


	public String getActionTaken() {
		return actionTaken;
	}


	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}


	public int getThId() {
		return thId;
	}


	public void setThId(int thId) {
		this.thId = thId;
	}


	public String getDrThId() {
		return drThId;
	}


	public void setDrThId(String drThId) {
		this.drThId = drThId;
	}


	public boolean isNewOB() {
		return newOB;
	}


	public void setNewOB(boolean newOB) {
		this.newOB = newOB;
	}


	public boolean isExisting() {
		return existing;
	}


	public void setExisting(boolean existing) {
		this.existing = existing;
	}


	public boolean isOnlyView() {
		return onlyView;
	}


	public void setOnlyView(boolean onlyView) {
		this.onlyView = onlyView;
	}


	public boolean isViewOlyOB() {
		return viewOlyOB;
	}


	public void setViewOlyOB(boolean viewOlyOB) {
		this.viewOlyOB = viewOlyOB;
	}


	public int getTotalNoOfInstances() {
		return totalNoOfInstances;
	}


	public void setTotalNoOfInstances(int totalNoOfInstances) {
		this.totalNoOfInstances = totalNoOfInstances;
	}


	public int getTotalOpeningBalance() {
		return totalOpeningBalance;
	}


	public void setTotalOpeningBalance(int totalOpeningBalance) {
		this.totalOpeningBalance = totalOpeningBalance;
	}


	public int getTotalNoticesServed() {
		return totalNoticesServed;
	}


	public void setTotalNoticesServed(int totalNoticesServed) {
		this.totalNoticesServed = totalNoticesServed;
	}


	public int getTotalMeterShifted() {
		return totalMeterShifted;
	}


	public void setTotalMeterShifted(int totalMeterShifted) {
		this.totalMeterShifted = totalMeterShifted;
	}


	public int getTotalNotPossibleShift() {
		return totalNotPossibleShift;
	}


	public void setTotalNotPossibleShift(int totalNotPossibleShift) {
		this.totalNotPossibleShift = totalNotPossibleShift;
	}


	public int getTotalClosingBalance() {
		return totalClosingBalance;
	}


	public void setTotalClosingBalance(int totalClosingBalance) {
		this.totalClosingBalance = totalClosingBalance;
	}
	
	
	
    
	
}
