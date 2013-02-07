package com.bean;

import java.util.ArrayList;
import java.util.List;

public class ZeroConsumptionBean {
	private int secId;
	private String yearId;
	private String monthId;
	private int rowStatus;
	private int idenFlag;
	private int noInspection =0;
	private String transHeadId = "";
	private String reason = "";
	
	private String toMonthId="";
	private String toYear="";
	
	private List<DiscrepancyBean> list = new ArrayList<DiscrepancyBean>();

	private int totalNoZeroCon ;
	private double totalDemandRaised;
	private int grandTotlZeroCon;
	private double grandTotlDemaRaised;
	private boolean onlyView = false;

	
	private String groupNmeString = "";
	private int rowSpanSize = 0;
	private int role = 0;
	
	private int idNxt =0;
	private int idCurrent = 0;
	
	

	

	public int getIdNxt() {
		return idNxt;
	}

	public void setIdNxt(int idNxt) {
		this.idNxt = idNxt;
	}

	public int getIdCurrent() {
		return idCurrent;
	}

	public void setIdCurrent(int idCurrent) {
		this.idCurrent = idCurrent;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	

	public int getRowSpanSize() {
		return rowSpanSize;
	}

	public void setRowSpanSize(int rowSpanSize) {
		this.rowSpanSize = rowSpanSize;
	}

	public String getGroupNmeString() {
		return groupNmeString;
	}

	public void setGroupNmeString(String groupNmeString) {
		this.groupNmeString = groupNmeString;
	}

	public String getToMonthId() {
		return toMonthId;
	}

	public void setToMonthId(String toMonthId) {
		this.toMonthId = toMonthId;
	}

	public String getToYear() {
		return toYear;
	}

	public void setToYear(String toYear) {
		this.toYear = toYear;
	}

	public int getGrandTotlZeroCon() {
		return grandTotlZeroCon;
	}

	public void setGrandTotlZeroCon(int grandTotlZeroCon) {
		this.grandTotlZeroCon = grandTotlZeroCon;
	}

	public double getGrandTotlDemaRaised() {
		return grandTotlDemaRaised;
	}

	public void setGrandTotlDemaRaised(double grandTotlDemaRaised) {
		this.grandTotlDemaRaised = grandTotlDemaRaised;
	}

	public int getTotalNoZeroCon() {
		return totalNoZeroCon;
	}

	public void setTotalNoZeroCon(int totalNoZeroCon) {
		this.totalNoZeroCon = totalNoZeroCon;
	}

	public double getTotalDemandRaised() {
		return totalDemandRaised;
	}

	public void setTotalDemandRaised(double totalDemandRaised) {
		this.totalDemandRaised = totalDemandRaised;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getTransHeadId() {
		return transHeadId;
	}

	public void setTransHeadId(String transHeadId) {
		this.transHeadId = transHeadId;
	}

	public int getNoInspection() {
		return noInspection;
	}

	public void setNoInspection(int noInspection) {
		this.noInspection = noInspection;
	}

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

	public List<DiscrepancyBean> getList() {
		return list;
	}

	public void setList(List<DiscrepancyBean> list) {
		this.list = list;
	}

	public boolean isOnlyView() {
		return onlyView;
	}

	public void setOnlyView(boolean onlyView) {
		this.onlyView = onlyView;
	}
	
	
	
}
