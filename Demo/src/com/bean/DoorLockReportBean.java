package com.bean;

public class DoorLockReportBean {

	private String monthId;
	private int drLockNo = 0;
	private int openBalance = 0;
	private int noOfNotices = 0;
	private int metersShifted = 0;
	private int notShifted = 0;
	private int closingBalance = 0;
	private String reason = "";

	private int totdrLockNo = 0;
	private int totopenBalance = 0;
	private int totnoOfNotices = 0;
	private int totmetersShifted = 0;
	private int totnotShifted = 0;
	private int totclosingBalance = 0;

	public DoorLockReportBean(){
		
	}
	
	public DoorLockReportBean(int drLockNo, int openBalance, int noOfNotices,
			int metersShifted, int notShifted, int closingBalance,
			String reason, int totdrLockNo, int totopenBalance,
			int totnoOfNotices, int totmetersShifted, int totnotShifted,
			int totclosingBalance) {
          this.drLockNo = drLockNo;
          this.openBalance = openBalance;
          this.noOfNotices = noOfNotices;
          this.metersShifted = metersShifted;
          this.notShifted = notShifted;
          this.closingBalance = closingBalance;
          this.reason = reason;
          this.totdrLockNo = totdrLockNo;
          this.totopenBalance =  totopenBalance;
          this.totnoOfNotices = noOfNotices;
          this.totmetersShifted = totmetersShifted;
          this.totnotShifted =  totnotShifted;
          this.totclosingBalance = closingBalance;
	}

	public String getMonthId() {
		return monthId;
	}

	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}

	public int getDrLockNo() {
		return drLockNo;
	}

	public void setDrLockNo(int drLockNo) {
		this.drLockNo = drLockNo;
	}

	public int getOpenBalance() {
		return openBalance;
	}

	public void setOpenBalance(int openBalance) {
		this.openBalance = openBalance;
	}

	public int getNoOfNotices() {
		return noOfNotices;
	}

	public void setNoOfNotices(int noOfNotices) {
		this.noOfNotices = noOfNotices;
	}

	public int getMetersShifted() {
		return metersShifted;
	}

	public void setMetersShifted(int metersShifted) {
		this.metersShifted = metersShifted;
	}

	public int getNotShifted() {
		return notShifted;
	}

	public void setNotShifted(int notShifted) {
		this.notShifted = notShifted;
	}

	public int getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(int closingBalance) {
		this.closingBalance = closingBalance;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getTotdrLockNo() {
		return totdrLockNo;
	}

	public void setTotdrLockNo(int totdrLockNo) {
		this.totdrLockNo = totdrLockNo;
	}

	public int getTotopenBalance() {
		return totopenBalance;
	}

	public void setTotopenBalance(int totopenBalance) {
		this.totopenBalance = totopenBalance;
	}

	public int getTotnoOfNotices() {
		return totnoOfNotices;
	}

	public void setTotnoOfNotices(int totnoOfNotices) {
		this.totnoOfNotices = totnoOfNotices;
	}

	public int getTotmetersShifted() {
		return totmetersShifted;
	}

	public void setTotmetersShifted(int totmetersShifted) {
		this.totmetersShifted = totmetersShifted;
	}

	public int getTotnotShifted() {
		return totnotShifted;
	}

	public void setTotnotShifted(int totnotShifted) {
		this.totnotShifted = totnotShifted;
	}

	public int getTotclosingBalance() {
		return totclosingBalance;
	}

	public void setTotclosingBalance(int totclosingBalance) {
		this.totclosingBalance = totclosingBalance;
	}

}
