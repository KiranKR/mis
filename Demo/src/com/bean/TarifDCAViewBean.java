package com.bean;



public class TarifDCAViewBean {
	
	private int secId;
	private String yearId;
	public String monthId;
	private int rowStatus;
	private int idenFlag;
	
	private String tarifName;
	
	int tariffId;
	
	int TarifRWStatus;
	int thId=0;
	
	double demand=0.0;
	double consumption=0.0;
	double arr=0.0;
	
	
//	private List<Tariff>arlTarif=new ArrayList<Tariff>();
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
	public int getTariffId() {
		return tariffId;
	}
	public void setTariffId(int tariffId) {
		this.tariffId = tariffId;
	}
	public int getTarifRWStatus() {
		return TarifRWStatus;
	}
	public void setTarifRWStatus(int tarifRWStatus) {
		TarifRWStatus = tarifRWStatus;
	}
	public int getThId() {
		return thId;
	}
	public void setThId(int thId) {
		this.thId = thId;
	}
	public double getDemand() {
		return demand;
	}
	public void setDemand(double demand) {
		this.demand = demand;
	}
	public double getConsumption() {
		return consumption;
	}
	public void setConsumption(double consumption) {
		this.consumption = consumption;
	}
	public double getArr() {
		return arr;
	}
	public void setArr(double arr) {
		this.arr = arr;
	}
	
	
	
	
	

}
