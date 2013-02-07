package com.bean;

public class DisconnectionBean {
	
	String secId = "";
	String mnthId = "";
	String yearId = "";
	String reason = "";
	String disId = "";
	int openBal = 0;
	int closeBal = 0;
	int visitDurMnth = 0;
	int disconctDurMnth = 0;
	int reconctDurMnth = 0;
	double openBalAmt = 0.0;
	double closBalAmt = 0.0;
	double amtInv = 0.0;
	double amtRel = 0.0;
	int obId = 0;
	boolean newOB = true;
	boolean existing = true;
	String SecName = "";
	String thId = "";
	boolean onlyView = false;
	double ttlOB = 0.0;
	double ttlAmtInv = 0.0;
	double ttlAmtRel = 0.0;
	double ttlCB = 0.0;
   private boolean viewOlyOB = false; 
	
	
	public String getSecId() {
		return secId;
	}
	public void setSecId(String secId) {
		this.secId = secId;
	}
	public String getMnthId() {
		return mnthId;
	}
	public void setMnthId(String mnthId) {
		this.mnthId = mnthId;
	}
	public String getYearId() {
		return yearId;
	}
	public void setYearId(String yearId) {
		this.yearId = yearId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getOpenBal() {
		return openBal;
	}
	public void setOpenBal(int openBal) {
		this.openBal = openBal;
	}
	
	public int getCloseBal() {
		return closeBal;
	}
	public void setCloseBal(int closeBal) {
		this.closeBal = closeBal;
	}
	public int getVisitDurMnth() {
		return visitDurMnth;
	}
	public void setVisitDurMnth(int visitDurMnth) {
		this.visitDurMnth = visitDurMnth;
	}
	public int getDisconctDurMnth() {
		return disconctDurMnth;
	}
	public void setDisconctDurMnth(int disconctDurMnth) {
		this.disconctDurMnth = disconctDurMnth;
	}
	public int getReconctDurMnth() {
		return reconctDurMnth;
	}
	public void setReconctDurMnth(int reconctDurMnth) {
		this.reconctDurMnth = reconctDurMnth;
	}
	public double getOpenBalAmt() {
		return openBalAmt;
	}
	public void setOpenBalAmt(double openBalAmt) {
		this.openBalAmt = openBalAmt;
	}
	public double getClosBalAmt() {
		return closBalAmt;
	}
	public void setClosBalAmt(double closBalAmt) {
		this.closBalAmt = closBalAmt;
	}
	public double getAmtInv() {
		return amtInv;
	}
	public void setAmtInv(double amtInv) {
		this.amtInv = amtInv;
	}
	public double getAmtRel() {
		return amtRel;
	}
	public void setAmtRel(double amtRel) {
		this.amtRel = amtRel;
	}
	public int getObId() {
		return obId;
	}
	public void setObId(int obId) {
		this.obId = obId;
	}
	public boolean isNewOB() {
		return newOB;
	}
	public void setNewOB(boolean newOB) {
		this.newOB = newOB;
	}
	public String getDisId() {
		return disId;
	}
	public void setDisId(String disId) {
		this.disId = disId;
	}
	public String getSecName() {
		return SecName;
	}
	public void setSecName(String secName) {
		SecName = secName;
	}
	public String getThId() {
		return thId;
	}
	public void setThId(String thId) {
		this.thId = thId;
	}
	public double getTtlOB() {
		return ttlOB;
	}
	public void setTtlOB(double ttlOB) {
		this.ttlOB = ttlOB;
	}
	public double getTtlAmtInv() {
		return ttlAmtInv;
	}
	public void setTtlAmtInv(double ttlAmtInv) {
		this.ttlAmtInv = ttlAmtInv;
	}
	public double getTtlAmtRel() {
		return ttlAmtRel;
	}
	public void setTtlAmtRel(double ttlAmtRel) {
		this.ttlAmtRel = ttlAmtRel;
	}
	public double getTtlCB() {
		return ttlCB;
	}
	public void setTtlCB(double ttlCB) {
		this.ttlCB = ttlCB;
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
	
}
