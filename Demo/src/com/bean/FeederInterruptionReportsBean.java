package com.bean;


public class FeederInterruptionReportsBean
{

    private String fedName;
    private int fedTrippedCount = 0;
    private String fedReason;
    private int fedCount = 0;
    
    private int sectionId = 0;
    private String sectionNme = "";
    private int subDivId = 0;
    private String subDivNme = "";
    private int divId = 0;
    private String divNme = "";
    private int circleId = 0;
    private String circleNme = "";
    private int zoneId = 0;
    private String zoneNme = "";
    
    private int transHeadID= 0;
    private String year = "";
    private String mnthId = "";
    private String mnthNme = "";
    
    private String dispLabel = "";
    private String level = "";
    
	private int totalFeederCount = 0 ;
	private int totalTrippedCount = 0;
	
    private int id = 0;
	
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFedCount() {
		return fedCount;
	}
	public void setFedCount(int fedCount) {
		this.fedCount = fedCount;
	}
	public int getTotalTrippedCount() {
		return totalTrippedCount;
	}
	public void setTotalTrippedCount(int totalTrippedCount) {
		this.totalTrippedCount = totalTrippedCount;
	}
	public int getTotalFeederCount() {
		return totalFeederCount;
	}
	public void setTotalFeederCount(int totalFeederCount) {
		this.totalFeederCount = totalFeederCount;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getDispLabel() {
		return dispLabel;
	}
	public void setDispLabel(String dispLabel) {
		this.dispLabel = dispLabel;
	}
	public int getSectionId() {
		return sectionId;
	}
	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}
	
	public String getSectionNme() {
		return sectionNme;
	}
	public void setSectionNme(String sectionNme) {
		this.sectionNme = sectionNme;
	}
	public String getMnthNme() {
		return mnthNme;
	}
	public void setMnthNme(String mnthNme) {
		this.mnthNme = mnthNme;
	}
	public int getSubDivId() {
		return subDivId;
	}
	public void setSubDivId(int subDivId) {
		this.subDivId = subDivId;
	}
	public String getSubDivNme() {
		return subDivNme;
	}
	public void setSubDivNme(String subDivNme) {
		this.subDivNme = subDivNme;
	}
	public int getDivId() {
		return divId;
	}
	public void setDivId(int divId) {
		this.divId = divId;
	}
	public String getDivNme() {
		return divNme;
	}
	public void setDivNme(String divNme) {
		this.divNme = divNme;
	}
	public int getCircleId() {
		return circleId;
	}
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}
	public String getCircleNme() {
		return circleNme;
	}
	public void setCircleNme(String circleNme) {
		this.circleNme = circleNme;
	}
	public int getZoneId() {
		return zoneId;
	}
	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}
	public String getZoneNme() {
		return zoneNme;
	}
	public void setZoneNme(String zoneNme) {
		this.zoneNme = zoneNme;
	}
	public int getTransHeadID() {
		return transHeadID;
	}
	public void setTransHeadID(int transHeadID) {
		this.transHeadID = transHeadID;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMnthId() {
		return mnthId;
	}
	public void setMnthId(String mnthId) {
		this.mnthId = mnthId;
	}
	public String getFedName() {
		return fedName;
	}
	public void setFedName(String fedName) {
		this.fedName = fedName;
	}
	public int getFedTrippedCount() {
		return fedTrippedCount;
	}
	public void setFedTrippedCount(int fedTrippedCount) {
		this.fedTrippedCount = fedTrippedCount;
	}
	public String getFedReason() {
		return fedReason;
	}
	public void setFedReason(String fedReason) {
		this.fedReason = fedReason;
	}
    
    

   

	
   
}
