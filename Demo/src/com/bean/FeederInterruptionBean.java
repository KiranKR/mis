package com.bean;

import java.util.ArrayList;
import java.util.List;

public class FeederInterruptionBean
{   
	private String sectionId = "";
	private String monthId = "";
	private String yearId = "" ;
	private int rowStatus; 
	private int idenFlag;
	
	private String feeName;
	private int totalFstripped_Count;
	private int transHead;
	
	private boolean onlyView = false;
	
	
	public int getTransHead() {
		return transHead;
	}
	public void setTransHead(int transHead) {
		this.transHead = transHead;
	}
	private List<Feeder> feedList = new ArrayList<Feeder>();
	    
	    public int getTotalFstripped_Count() {
			return totalFstripped_Count;
		}
		public void setTotalFstripped_Count(int totalFstripped_Count) {
			this.totalFstripped_Count = totalFstripped_Count;
		}
	
	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public List<Feeder> getFeedList() {
		return feedList;
	}
	public void setFeedList(List<Feeder> feedList) {
		this.feedList = feedList;
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
	public boolean isOnlyView() {
		return onlyView;
	}
	public void setOnlyView(boolean onlyView) {
		this.onlyView = onlyView;
	}
	
}
