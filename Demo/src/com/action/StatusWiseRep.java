package com.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.chart.PieChartModel;

import com.Dao.StatusWiseRepDao;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.ReportBean;
import com.bean.StackBean;

/**
 * @author admin
 * 
 */
@ManagedBean(name = "pc_statusWiseRep")
@SessionScoped
public class StatusWiseRep extends PageCodeBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// public class SchemeWiseRep {
	StatusWiseRepDao statusWiseRepDao = new StatusWiseRepDao();
	List<ReportBean> reportBeans = new ArrayList<ReportBean>();
	List<ReportBean> dispReportBeans = new ArrayList<ReportBean>();
	ReportBean bean = null;
	String role = UserUtil.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION);
	String roleBelong = UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION);
	String header = "";
	String znId = "";
	String crlId = "";
	String divId = "";
	String subDivId = "";
	String secId = "";
	StackBean stackBean = null;
	List<StackBean> stackBeans = new ArrayList<StackBean>();
	boolean backbtn = false;

	int wrkYetTotCnt = 0;
	int wrkTknTotCnt = 0;
	int wrkComTotCnt = 0;
	int serviceTotCnt = 0;
	int totCnt = 0;
	double wrkYetTotAmt = 0;
	double wrkTknTotAmt = 0;
	double wrkComTotAmt = 0;
	double serviceTotAmt = 0;
	double totAmt = 0;

	PieChartModel chartModel1;
	PieChartModel chartModel2;

	public StatusWiseRep() {
		if (role.equals("1")) {
			header = "Circle";
			znId = roleBelong;
			stackBeans.add(new StackBean(header, roleBelong));
		} else if (role.equals("2")) {
			header = "Division";
			crlId = roleBelong;
			stackBeans.add(new StackBean(header, roleBelong));
		} else if (role.equals("3")) {
			header = "Sub Division";
			divId = roleBelong;
			stackBeans.add(new StackBean(header, roleBelong));
		} else if (role.equals("4")) {
			header = "Section";
			subDivId = roleBelong;
			stackBeans.add(new StackBean(header, roleBelong));
		}
		dispReportBeans = statusWiseRepDao.getData(role, roleBelong);
		totalCount(dispReportBeans);
		pieChart();

	}

	public String getSchemeWiseRep(String id, String lvl) {

		
		if(lvl.equals("5") && stackBeans.size() == 1){
			backbtn = false;
			}else{
			backbtn = true;
			}
		/*wrkYetTotCnt = 0;
		wrkTknTotCnt = 0;
		wrkComTotCnt = 0;
		serviceTotCnt = 0;
		totCnt = 0;
		wrkYetTotAmt = 0;
		wrkTknTotAmt = 0;
		wrkComTotAmt = 0;
		serviceTotAmt =0;
		totAmt = 0;*/
		if (lvl.equals("4")) {
			header = "Section";
			stackBeans.add(new StackBean("Section", id));
			dispReportBeans = statusWiseRepDao.secWise(id);
			totalCount(dispReportBeans);
			pieChart();

		} else if (lvl.equals("3")) {
			header = "Sub Division";
			stackBeans.add(new StackBean("Sub Division", id));
			dispReportBeans = statusWiseRepDao.subDivWise(id);
			totalCount(dispReportBeans);
			pieChart();

		} else if (lvl.equals("2")) {
			header = "Division";
			stackBeans.add(new StackBean("Division", id));
			dispReportBeans = statusWiseRepDao.divWise(id);
			totalCount(dispReportBeans);
			pieChart();

		} else if (lvl.equals("1")) {
			header = "Circle";
			stackBeans.add(new StackBean("Circle", id));
			dispReportBeans = statusWiseRepDao.circleWise(id);
			totalCount(dispReportBeans);
			pieChart();

		}

		return "statusWiseReport";
	}

	public String back() {
		int size = stackBeans.size();
		String id = "";
		String lvl = "";
		size = size - 1;
		wrkYetTotCnt = 0;
		wrkTknTotCnt = 0;
		wrkComTotCnt = 0;
		serviceTotCnt = 0;
		totCnt = 0;
		wrkYetTotAmt = 0;
		wrkTknTotAmt = 0;
		wrkComTotAmt = 0;
		serviceTotAmt =0;
		totAmt = 0;
		if (size >= 1) {
			stackBeans.remove(size);
			size = size - 1;
			StackBean bean = stackBeans.get(size);
			id = bean.getId();
			lvl = bean.getLvl();
			if(stackBeans.size() == 1){
				backbtn = false;
				}

			if (lvl.equals("Section")) {
				header = "Section";
				dispReportBeans = statusWiseRepDao.secWise(id);
				totalCount(dispReportBeans);
				pieChart();

			} else if (lvl.equals("Sub Division")) {
				header = "Sub Division";
				dispReportBeans = statusWiseRepDao.subDivWise(id);
				totalCount(dispReportBeans);
				pieChart();

			} else if (lvl.equals("Division")) {
				header = "Division";
				dispReportBeans = statusWiseRepDao.divWise(id);
				totalCount(dispReportBeans);
				pieChart();

			} else if (lvl.equals("Circle")) {
				header = "Circle";
				dispReportBeans = statusWiseRepDao.circleWise(id);
				totalCount(dispReportBeans);
				pieChart();

			}

		} else {
			StackBean bean = stackBeans.get(0);
			id = bean.getId();
			lvl = bean.getLvl();
			if (lvl.equals("Section")) {
				header = "Section";
				dispReportBeans = statusWiseRepDao.secWise(id);
				totalCount(dispReportBeans);
				pieChart();

			} else if (lvl.equals("Sub Division")) {
				header = "Sub Division";
				dispReportBeans = statusWiseRepDao.subDivWise(id);
				totalCount(dispReportBeans);
				pieChart();

			} else if (lvl.equals("Division")) {
				header = "Division";
				dispReportBeans = statusWiseRepDao.divWise(id);
				totalCount(dispReportBeans);
				pieChart();

			} else if (lvl.equals("Circle")) {
				header = "Circle";
				dispReportBeans = statusWiseRepDao.circleWise(id);
				totalCount(dispReportBeans);
				pieChart();

			}

		}
		return "statusWiseReport";
	}

	public void totalCount(List<ReportBean> dispReportBeans) {
		wrkYetTotCnt = 0;
		wrkTknTotCnt = 0;
		wrkComTotCnt = 0;
		serviceTotCnt = 0;
		totCnt = 0;
		wrkYetTotAmt = 0;
		wrkTknTotAmt = 0;
		wrkComTotAmt = 0;
		serviceTotAmt =0;
		totAmt = 0;
		for (ReportBean bean : dispReportBeans) {
			wrkYetTotCnt = wrkYetTotCnt + bean.getWrkYetCnt();
			wrkTknTotCnt = wrkTknTotCnt + bean.getWrkTknCnt();
			wrkComTotCnt = wrkComTotCnt + bean.getWrkComCnt();
			serviceTotCnt = serviceTotCnt + bean.getServiceCnt();
			totCnt = totCnt + bean.getTtl();
			wrkYetTotAmt = wrkYetTotAmt + bean.getWrkYetAmt();
			wrkTknTotAmt = wrkTknTotAmt + bean.getWrkTknAmt();
			wrkComTotAmt = wrkComTotAmt + bean.getWrkComAmt();
			serviceTotAmt = serviceTotAmt + bean.getServiceAmt();
			totAmt = totAmt + bean.getTtlamnt();
		}
	}

	public void pieChart() {
		chartModel1 = new PieChartModel();
		chartModel1.set("Work Yet To Be Count", wrkYetTotCnt);
		chartModel1.set("Work Under Progress Total Count", wrkTknTotCnt);
		chartModel1.set("Work Completed Total Count", wrkComTotCnt);
		chartModel1.set("Service Provider Total Count", serviceTotCnt);

		chartModel2 = new PieChartModel();
		chartModel2.set("Work Yet To Be Amonut", wrkYetTotAmt);
		chartModel2.set("Work Under Progress Amonut", wrkTknTotAmt);
		chartModel2.set("Work Completed Amonut", wrkComTotAmt);
		chartModel2.set("Service Provider Amonut", serviceTotAmt);
	}

	public List<ReportBean> getReportBeans() {
		return reportBeans;
	}

	public void setReportBeans(List<ReportBean> reportBeans) {
		this.reportBeans = reportBeans;
	}

	public List<ReportBean> getDispReportBeans() {
		return dispReportBeans;
	}

	public void setDispReportBeans(List<ReportBean> dispReportBeans) {
		this.dispReportBeans = dispReportBeans;
	}

	public ReportBean getBean() {
		return bean;
	}

	public void setBean(ReportBean bean) {
		this.bean = bean;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public StatusWiseRepDao getStatusWiseRepDao() {
		return statusWiseRepDao;
	}

	public void setStatusWiseRepDao(StatusWiseRepDao statusWiseRepDao) {
		this.statusWiseRepDao = statusWiseRepDao;
	}

	public int getWrkYetTotCnt() {
		return wrkYetTotCnt;
	}

	public void setWrkYetTotCnt(int wrkYetTotCnt) {
		this.wrkYetTotCnt = wrkYetTotCnt;
	}

	public int getWrkTknTotCnt() {
		return wrkTknTotCnt;
	}

	public void setWrkTknTotCnt(int wrkTknTotCnt) {
		this.wrkTknTotCnt = wrkTknTotCnt;
	}

	public int getWrkComTotCnt() {
		return wrkComTotCnt;
	}

	public void setWrkComTotCnt(int wrkComTotCnt) {
		this.wrkComTotCnt = wrkComTotCnt;
	}

	public int getserviceTotCnt() {
		return serviceTotCnt;
	}

	public void setserviceTotCnt(int serviceTotCnt) {
		this.serviceTotCnt = serviceTotCnt;
	}

	public int getTotCnt() {
		return totCnt;
	}

	public void setTotCnt(int totCnt) {
		this.totCnt = totCnt;
	}

	public double getWrkYetTotAmt() {
		return wrkYetTotAmt;
	}

	public void setWrkYetTotAmt(double wrkYetTotAmt) {
		this.wrkYetTotAmt = wrkYetTotAmt;
	}

	public double getWrkTknTotAmt() {
		return wrkTknTotAmt;
	}

	public void setWrkTknTotAmt(double wrkTknTotAmt) {
		this.wrkTknTotAmt = wrkTknTotAmt;
	}

	public double getWrkComTotAmt() {
		return wrkComTotAmt;
	}

	public void setWrkComTotAmt(double wrkComTotAmt) {
		this.wrkComTotAmt = wrkComTotAmt;
	}

	public int getServiceTotCnt() {
		return serviceTotCnt;
	}

	public void setServiceTotCnt(int serviceTotCnt) {
		this.serviceTotCnt = serviceTotCnt;
	}

	public double getServiceTotAmt() {
		return serviceTotAmt;
	}

	public void setServiceTotAmt(double serviceTotAmt) {
		this.serviceTotAmt = serviceTotAmt;
	}

	public double getTotAmt() {
		return totAmt;
	}

	public void setTotAmt(double totAmt) {
		this.totAmt = totAmt;
	}

	public PieChartModel getChartModel1() {
		return chartModel1;
	}

	public void setChartModel1(PieChartModel chartModel1) {
		this.chartModel1 = chartModel1;
	}

	public PieChartModel getChartModel2() {
		return chartModel2;
	}

	public void setChartModel2(PieChartModel chartModel2) {
		this.chartModel2 = chartModel2;
	}

	public boolean isBackbtn() {
		return backbtn;
	}

	public void setBackbtn(boolean backbtn) {
		this.backbtn = backbtn;
	}
  
}
