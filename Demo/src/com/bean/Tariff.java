package com.bean;

public class Tariff {
	int tariffId;
	String tarifName;
	int TarifRWStatus;
	int thId=0;
	
	double demand=0.0;
	double consumption=0.0;
	double arr=0.0;
	
	   private double ttlDemand=0.00;
	   private double ttlConsumption=0.00;
	   private double ttlArr=0.00;
	   
	   
	public int getTariffId() {
		return tariffId;
	}
	public void setTariffId(int tariffId) {
		this.tariffId = tariffId;
	}
	public String getTarifName() {
		return tarifName;
	}
	public void setTarifName(String tarifName) {
		this.tarifName = tarifName;
	}
	public int getTarifRWStatus() {
		return TarifRWStatus;
	}
	public void setTarifRWStatus(int tarifRWStatus) {
		TarifRWStatus = tarifRWStatus;
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
	

}
