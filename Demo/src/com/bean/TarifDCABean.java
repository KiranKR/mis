package com.bean;

import java.util.ArrayList;
import java.util.List;

public class TarifDCABean {
	
	private int secId;
	private String yearId;
	private String monthId;
	private int rowStatus;
	private int idenFlag;
	
	private String tarifName;
	private boolean onlyView = false;
  
   
	private List<Tariff>arlTarif=new ArrayList<Tariff>();
	  private double ttlDemand=0.00;
	   private double ttlConsumption=0.00;
	   private double ttlArr=0.00;
	   
	private int thId=0;
	
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
	public String getTarifName() {
		return tarifName;
	}
	public void setTarifName(String tarifName) {
		this.tarifName = tarifName;
	}
	public List<Tariff> getArlTarif() {
		return arlTarif;
	}
	public void setArlTarif(List<Tariff> arlTarif) {
		this.arlTarif = arlTarif;
	}
	
	public int getThId() {
		return thId;
	}
	public void setThId(int thId) {
		this.thId = thId;
	}
	public double getTtlDemand() {
		return ttlDemand;
	}
	public void setTtlDemand(double ttlDemand) {
		this.ttlDemand = ttlDemand;
	}
	public double getTtlConsumption() {
		return ttlConsumption;
	}
	public void setTtlConsumption(double ttlConsumption) {
		this.ttlConsumption = ttlConsumption;
	}
	public double getTtlArr() {
		return ttlArr;
	}
	public void setTtlArr(double ttlArr) {
		this.ttlArr = ttlArr;
	}
	public boolean isOnlyView() {
		return onlyView;
	}
	public void setOnlyView(boolean onlyView) {
		this.onlyView = onlyView;
	}
    
	

}
