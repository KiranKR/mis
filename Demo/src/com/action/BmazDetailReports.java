package com.action;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.BmazDetailReportsDao;
import com.bean.ReportsBean;

@ManagedBean(name="pc_bmazDetailReports")
@SessionScoped
public class BmazDetailReports {
	

    BmazDetailReportsDao bmazDetailReportsDao = new BmazDetailReportsDao(); 
	List<ReportsBean> reportsBeans = new ArrayList<ReportsBean>();
	
	
	public BmazDetailReports() {
		reportsBeans = bmazDetailReportsDao.getReports();
	}
    
	public String reportPage()
	{
		return "reportPage";
	}
	public List<ReportsBean> getReportsBeans() {
		return reportsBeans;
	}


	public void setReportsBeans(List<ReportsBean> reportsBeans) {
		this.reportsBeans = reportsBeans;
	}

	public BmazDetailReportsDao getBmazDetailReportsDao() {
		return bmazDetailReportsDao;
	}

	public void setBmazDetailReportsDao(BmazDetailReportsDao bmazDetailReportsDao) {
		this.bmazDetailReportsDao = bmazDetailReportsDao;
	}
	
	
}
