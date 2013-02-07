package com.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.AnalyticalReportsDao;
import com.Util.PageCodeBase;
import com.bean.ReportsBean;

@ManagedBean(name="pc_analyticalReports")
@SessionScoped 
public class AnalyticalReports extends PageCodeBase implements Serializable{
	
	
	private List<ReportsBean> reportsBeans = new ArrayList<ReportsBean>();
	private AnalyticalReportsDao analyticalReportsDao = new AnalyticalReportsDao();

	
	public AnalyticalReports() {
           reportsBeans = analyticalReportsDao.getReports();
	}


	public List<ReportsBean> getReportsBeans() {
		return reportsBeans;
	}


	public void setReportsBeans(List<ReportsBean> reportsBeans) {
		this.reportsBeans = reportsBeans;
	}


	public AnalyticalReportsDao getAnalyticalReportsDao() {
		return analyticalReportsDao;
	}


	public void setAnalyticalReportsDao(AnalyticalReportsDao analyticalReportsDao) {
		this.analyticalReportsDao = analyticalReportsDao;
	}
	
}
