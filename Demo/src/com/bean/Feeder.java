package com.bean;

public class Feeder {
	
	private int fdr_id=0;
    private String fdr_Nmae;
    private int fdrtripped_count;
    private String fdr_rsn;
    private int rowStatus;
   
	private int totalcount;
	private int transHeadFdr;
	
	
    
    public int getTransHeadFdr() {
		return transHeadFdr;
	}
	public void setTransHeadFdr(int transHeadFdr) {
		this.transHeadFdr = transHeadFdr;
	}
	public int getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}
	public int getFdrtripped_count() {
		return fdrtripped_count;
	}
	public void setFdrtripped_count(int fdrtripped_count) {
		this.fdrtripped_count = fdrtripped_count;
	}
	public String getFdr_rsn() {
		return fdr_rsn;
	}
	public void setFdr_rsn(String fdr_rsn) {
		this.fdr_rsn = fdr_rsn;
	}
	public int getRowStatus() {
		return rowStatus;
	}
	public void setRowStatus(int rowStatus) {
		this.rowStatus = rowStatus;
	}
	public int getFdr_id() {
		return fdr_id;
	}
	public void setFdr_id(int fdr_id) {
		this.fdr_id = fdr_id;
	}
	public String getFdr_Nmae() {
		return fdr_Nmae;
	}
	public void setFdr_Nmae(String fdr_Nmae) {
		this.fdr_Nmae = fdr_Nmae;
	}	

}
