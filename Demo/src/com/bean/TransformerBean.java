package com.bean;

import java.util.ArrayList;
import java.util.List;

public class TransformerBean {
  
	private int secId;
	
	private int rowStatus;
	private int idenFlag;
	
	private String capName;
	private List<Capacity> capacities = new ArrayList<Capacity>();
	private int thId;
	private int tId;
	
	
	private boolean onlyView = false;
    
	private String yearId="";
	private String monthId="";
	private String toMonthId="";
	private String toYearId="";
	public int getSecId() {
		return secId;
	}
	public void setSecId(int secId) {
		this.secId = secId;
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
	public String getCapName() {
		return capName;
	}
	public void setCapName(String capName) {
		this.capName = capName;
	}
	public List<Capacity> getCapacities() {
		return capacities;
	}
	public void setCapacities(List<Capacity> capacities) {
		this.capacities = capacities;
	}
	public int getThId() {
		return thId;
	}
	public void setThId(int thId) {
		this.thId = thId;
	}
	public int gettId() {
		return tId;
	}
	public void settId(int tId) {
		this.tId = tId;
	}
	public boolean isOnlyView() {
		return onlyView;
	}
	public void setOnlyView(boolean onlyView) {
		this.onlyView = onlyView;
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
	public String getToMonthId() {
		return toMonthId;
	}
	public void setToMonthId(String toMonthId) {
		this.toMonthId = toMonthId;
	}
	public String getToYearId() {
		return toYearId;
	}
	public void setToYearId(String toYearId) {
		this.toYearId = toYearId;
	}
}
