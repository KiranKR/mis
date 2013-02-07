package com.action;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.BmazAnalyticalReportsDao;
import com.bean.ReportsBean;


@ManagedBean(name="pc_bmazAnalyticalReports")
@SessionScoped
public class BmazAnalyticalReports {

	
	BmazAnalyticalReportsDao bmazAnaRepDao = new BmazAnalyticalReportsDao();
	List<ReportsBean> reportsBeans = new ArrayList<ReportsBean>();
	
	
	public BmazAnalyticalReports() {
		reportsBeans = bmazAnaRepDao.getReports();
	}
    
	public String reportPage()
	{
		return "reportPage";
	}
	public BmazAnalyticalReportsDao getBmazAnaRepDao() {
		return bmazAnaRepDao;
	}

	public void setBmazAnaRepDao(BmazAnalyticalReportsDao bmazAnaRepDao) {
		this.bmazAnaRepDao = bmazAnaRepDao;
	}

	public List<ReportsBean> getReportsBeans() {
		return reportsBeans;
	}


	public void setReportsBeans(List<ReportsBean> reportsBeans) {
		this.reportsBeans = reportsBeans;
	}

}
