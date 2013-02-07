package com.bean;

import java.util.ArrayList;
import java.util.List;

public class TarifDCAViewReportBean {
	private String monthId;
	private List<TarifDetailsBean> tarifDetailsBeans = new ArrayList<TarifDetailsBean>();
	private int size=0;

	public String getMonthId() {
		return monthId;
	}

	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}

	public List<TarifDetailsBean> getTarifDetailsBeans() {
		return tarifDetailsBeans;
	}

	public void setTarifDetailsBeans(List<TarifDetailsBean> tarifDetailsBeans) {
		this.tarifDetailsBeans = tarifDetailsBeans;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
    
}
