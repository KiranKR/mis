package com.bean;

public class TarifDetailsBean {

	private String id;
	private String lable;
	private String tarifName;
	double demand = 0.0;
	double consumption = 0.0;
	double arr = 0.0;

	public String getTarifName() {
		return tarifName;
	}

	public void setTarifName(String tarifName) {
		this.tarifName = tarifName;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

}
