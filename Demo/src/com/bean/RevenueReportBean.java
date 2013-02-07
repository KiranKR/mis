package com.bean;

import java.util.ArrayList;
import java.util.List;

public class RevenueReportBean {

	private String monthId="";
	private List<RevenueDetailsBean> revenueDetailsBeans = new ArrayList<RevenueDetailsBean>();
	public String getMonthId() {
		return monthId;
	}
	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}
	public List<RevenueDetailsBean> getRevenueDetailsBeans() {
		return revenueDetailsBeans;
	}
	public void setRevenueDetailsBeans(List<RevenueDetailsBean> revenueDetailsBeans) {
		this.revenueDetailsBeans = revenueDetailsBeans;
	}
	
	
	
	
}
