package com.bean;

public class InstalationBean {

	String secId = "";
	String subDivId = "";
	String divId = "";
	String crlId = "";
	String znId = "";
	String secNme = "";
	String subDivNme = "";
	String divNme = "";
	String crlNme = "";
	String znNme = "";
	String lvl = "";
	String mnthId = "";
	String mnthNm = "";
	String yearId = "";
	String instlId = "";
	String thId = "";
	String id = "";
	String lable = "";
	String header = "";
	int mtrWtrSply = 0;
	int unMtrWtrSply = 0;
	int total = 0;
	double openBalPrinAmt = 0.0;
	double openBalIntAmt = 0.0;
	double openBalTtlAmt = 0.0;
	double closBalPrinAmt = 0.0;
	double closBalIntAmt = 0.0;
	double closBalTtlAmt = 0.0;
	double demdBalPrinAmt = 0.0;
	double demdBalIntAmt = 0.0;
	double demdBalTtlAmt = 0.0;
	double clctnBalPrinAmt = 0.0;
	double clctnBalIntAmt = 0.0;
	double clctnBalTtlAmt = 0.0;
	boolean newOB = true;
	boolean onlyView = false;
	boolean existing = true;

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

	public String getInstlId() {
		return instlId;
	}

	public void setInstlId(String instlId) {
		this.instlId = instlId;
	}

	public int getMtrWtrSply() {
		return mtrWtrSply;
	}

	public void setMtrWtrSply(int mtrWtrSply) {
		this.mtrWtrSply = mtrWtrSply;
	}

	public int getUnMtrWtrSply() {
		return unMtrWtrSply;
	}

	public void setUnMtrWtrSply(int unMtrWtrSply) {
		this.unMtrWtrSply = unMtrWtrSply;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public double getOpenBalPrinAmt() {
		return openBalPrinAmt;
	}

	public void setOpenBalPrinAmt(double openBalPrinAmt) {
		this.openBalPrinAmt = openBalPrinAmt;
	}

	public double getClosBalPrinAmt() {
		return closBalPrinAmt;
	}

	public void setClosBalPrinAmt(double closBalPrinAmt) {
		this.closBalPrinAmt = closBalPrinAmt;
	}

	public double getOpenBalIntAmt() {
		return openBalIntAmt;
	}

	public void setOpenBalIntAmt(double openBalIntAmt) {
		this.openBalIntAmt = openBalIntAmt;
	}

	public double getClosBalIntAmt() {
		return closBalIntAmt;
	}

	public void setClosBalIntAmt(double closBalIntAmt) {
		this.closBalIntAmt = closBalIntAmt;
	}

	public double getOpenBalTtlAmt() {
		return openBalTtlAmt;
	}

	public void setOpenBalTtlAmt(double openBalTtlAmt) {
		this.openBalTtlAmt = openBalTtlAmt;
	}

	public double getClosBalTtlAmt() {
		return closBalTtlAmt;
	}

	public void setClosBalTtlAmt(double closBalTtlAmt) {
		this.closBalTtlAmt = closBalTtlAmt;
	}

	public boolean isNewOB() {
		return newOB;
	}

	public void setNewOB(boolean newOB) {
		this.newOB = newOB;
	}

	public double getDemdBalPrinAmt() {
		return demdBalPrinAmt;
	}

	public void setDemdBalPrinAmt(double demdBalPrinAmt) {
		this.demdBalPrinAmt = demdBalPrinAmt;
	}

	public double getDemdBalIntAmt() {
		return demdBalIntAmt;
	}

	public void setDemdBalIntAmt(double demdBalIntAmt) {
		this.demdBalIntAmt = demdBalIntAmt;
	}

	public double getDemdBalTtlAmt() {
		return demdBalTtlAmt;
	}

	public void setDemdBalTtlAmt(double demdBalTtlAmt) {
		this.demdBalTtlAmt = demdBalTtlAmt;
	}

	public double getClctnBalPrinAmt() {
		return clctnBalPrinAmt;
	}

	public void setClctnBalPrinAmt(double clctnBalPrinAmt) {
		this.clctnBalPrinAmt = clctnBalPrinAmt;
	}

	public double getClctnBalIntAmt() {
		return clctnBalIntAmt;
	}

	public void setClctnBalIntAmt(double clctnBalIntAmt) {
		this.clctnBalIntAmt = clctnBalIntAmt;
	}

	public double getClctnBalTtlAmt() {
		return clctnBalTtlAmt;
	}

	public void setClctnBalTtlAmt(double clctnBalTtlAmt) {
		this.clctnBalTtlAmt = clctnBalTtlAmt;
	}

	public String getThId() {
		return thId;
	}

	public void setThId(String thId) {
		this.thId = thId;
	}

	public boolean isOnlyView() {
		return onlyView;
	}

	public void setOnlyView(boolean onlyView) {
		this.onlyView = onlyView;
	}

	public boolean isExisting() {
		return existing;
	}

	public void setExisting(boolean existing) {
		this.existing = existing;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getSubDivId() {
		return subDivId;
	}

	public void setSubDivId(String subDivId) {
		this.subDivId = subDivId;
	}

	public String getDivId() {
		return divId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
	}

	public String getCrlId() {
		return crlId;
	}

	public void setCrlId(String crlId) {
		this.crlId = crlId;
	}

	public String getZnId() {
		return znId;
	}

	public void setZnId(String znId) {
		this.znId = znId;
	}

	public String getSecNme() {
		return secNme;
	}

	public void setSecNme(String secNme) {
		this.secNme = secNme;
	}

	public String getSubDivNme() {
		return subDivNme;
	}

	public void setSubDivNme(String subDivNme) {
		this.subDivNme = subDivNme;
	}

	public String getDivNme() {
		return divNme;
	}

	public void setDivNme(String divNme) {
		this.divNme = divNme;
	}

	public String getCrlNme() {
		return crlNme;
	}

	public void setCrlNme(String crlNme) {
		this.crlNme = crlNme;
	}

	public String getZnNme() {
		return znNme;
	}

	public void setZnNme(String znNme) {
		this.znNme = znNme;
	}

	public String getLvl() {
		return lvl;
	}

	public void setLvl(String lvl) {
		this.lvl = lvl;
	}

	public String getMnthNm() {
		return mnthNm;
	}

	public void setMnthNm(String mnthNm) {
		this.mnthNm = mnthNm;
	}

	public boolean isViewOlyOB() {
		return viewOlyOB;
	}

	public void setViewOlyOB(boolean viewOlyOB) {
		this.viewOlyOB = viewOlyOB;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
