package com.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.DetailReportsDao;
import com.Util.PageCodeBase;
import com.bean.ReportsBean;


@ManagedBean(name="pc_detailReports")
@SessionScoped
public class DetailReports extends PageCodeBase implements Serializable{

	private List<ReportsBean> reportsBeans = new ArrayList<ReportsBean>();
	private DetailReportsDao detailReportsDao = new DetailReportsDao();
	
	public DetailReports() {
        reportsBeans = detailReportsDao.getReports();
	}

	public List<ReportsBean> getReportsBeans() {
		return reportsBeans;
	}

	public void setReportsBeans(List<ReportsBean> reportsBeans) {
		this.reportsBeans = reportsBeans;
	}

	public DetailReportsDao getDetailReportsDao() {
		return detailReportsDao;
	}

	public void setDetailReportsDao(DetailReportsDao detailReportsDao) {
		this.detailReportsDao = detailReportsDao;
	}
	
}
