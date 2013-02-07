package com.bean;

import java.util.ArrayList;
import java.util.List;

public class FeederInterruptionReportMainBean 
{
	private String monthId;
	List<FeederInterruptionReportsBean> fedintrputionDetails = new ArrayList<FeederInterruptionReportsBean>();
	int size = 0;
	
	
	
	
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getMonthId() {
		return monthId;
	}
	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}
	public List<FeederInterruptionReportsBean> getFedintrputionDetails() {
		return fedintrputionDetails;
	}
	public void setFedintrputionDetails(
			List<FeederInterruptionReportsBean> fedintrputionDetails) {
		this.fedintrputionDetails = fedintrputionDetails;
	}
	
	
	
}
