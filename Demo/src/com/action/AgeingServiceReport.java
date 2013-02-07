package com.action;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.Dao.AgeingServiceDao;
import com.Util.AppUtil;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.ReportBean;
import com.bean.StackBean;

/**
 * @author admin
 * 
 */
@ManagedBean(name = "pc_ageingServiceReport")
@SessionScoped
public class AgeingServiceReport extends PageCodeBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// public class SchemeWiseRep {
	AgeingServiceDao ageingServiceDao = new AgeingServiceDao();
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
	

	 int btn30to60daysTotCnt =0;
	 int btn60to90daysTotCnt =0;
	 int btn90to120daysTotCnt =0;
	 int btn120to150daysTotCnt =0;
	 int btn150to180daysTotCnt =0;
	 int grt180daysTotCnt =0;
	 int totCnt =0;
     CartesianChartModel cartesianChartModel;
	

	public AgeingServiceReport() {
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
		dispReportBeans = ageingServiceDao.getData(role, roleBelong);
		totalCount(dispReportBeans);
		barGraph(dispReportBeans);

	}

	public String getSchemeWiseRep(String id, String lvl) {

		if(lvl.equals("5") && stackBeans.size() == 1){
			backbtn = false;
			}else{
			backbtn = true;
			}
		if (lvl.equals("4")) {
			header = "Section";
			stackBeans.add(new StackBean("Section", id));
			dispReportBeans = ageingServiceDao.secWise(id);
			totalCount(dispReportBeans);
			barGraph(dispReportBeans);

		} else if (lvl.equals("3")) {
			header = "Sub Division";
			stackBeans.add(new StackBean("Sub Division", id));
			dispReportBeans = ageingServiceDao.subDivWise(id);
			totalCount(dispReportBeans);
			barGraph(dispReportBeans);

		} else if (lvl.equals("2")) {
			header = "Division";
			stackBeans.add(new StackBean("Division", id));
			dispReportBeans = ageingServiceDao.divWise(id);
			totalCount(dispReportBeans);
			barGraph(dispReportBeans);

		} else if (lvl.equals("1")) {
			header = "Circle";
			stackBeans.add(new StackBean("Circle", id));
			dispReportBeans = ageingServiceDao.circleWise(id);
			totalCount(dispReportBeans);
			barGraph(dispReportBeans);

		}

		return "ageingServiceReport";
	}

	public String back() {
		int size = stackBeans.size();
		String id = "";
		String lvl = "";
		size = size - 1;
		  btn30to60daysTotCnt =0;
		  btn60to90daysTotCnt =0;
		  btn90to120daysTotCnt =0;
		 btn120to150daysTotCnt =0;
		 btn150to180daysTotCnt =0;
		  grt180daysTotCnt =0;
		 totCnt =0;
		if(stackBeans.size() == 1){
			backbtn = false;
			}
		if (size >= 1) {
			stackBeans.remove(size);
			size = size - 1;
			StackBean bean = stackBeans.get(size);
			id = bean.getId();
			lvl = bean.getLvl();

			if (lvl.equals("Section")) {
				header = "Section";
				dispReportBeans = ageingServiceDao.secWise(id);
				totalCount(dispReportBeans);
				barGraph(dispReportBeans);

			} else if (lvl.equals("Sub Division")) {
				header = "Sub Division";
				dispReportBeans = ageingServiceDao.subDivWise(id);
				totalCount(dispReportBeans);
				barGraph(dispReportBeans);

			} else if (lvl.equals("Division")) {
				header = "Division";
				dispReportBeans = ageingServiceDao.divWise(id);
				totalCount(dispReportBeans);
				barGraph(dispReportBeans);

			} else if (lvl.equals("Circle")) {
				header = "Circle";
				dispReportBeans = ageingServiceDao.circleWise(id);
				totalCount(dispReportBeans);
				barGraph(dispReportBeans);
			

			}

		} else {
			StackBean bean = stackBeans.get(0);
			id = bean.getId();
			lvl = bean.getLvl();
			if (lvl.equals("Section")) {
				header = "Section";
				dispReportBeans = ageingServiceDao.secWise(id);
				totalCount(dispReportBeans);
				barGraph(dispReportBeans);
				

			} else if (lvl.equals("Sub Division")) {
				header = "Sub Division";
				dispReportBeans = ageingServiceDao.subDivWise(id);
				totalCount(dispReportBeans);
				barGraph(dispReportBeans);
				

			} else if (lvl.equals("Division")) {
				header = "Division";
				dispReportBeans = ageingServiceDao.divWise(id);
				totalCount(dispReportBeans);
				barGraph(dispReportBeans);
				

			} else if (lvl.equals("Circle")) {
				header = "Circle";
				dispReportBeans = ageingServiceDao.circleWise(id);
				totalCount(dispReportBeans);
				barGraph(dispReportBeans);
				
			}

		}
		return "ageingServiceReport";
	}

	public void totalCount(List<ReportBean> dispReportBeans) {
		btn30to60daysTotCnt =0;
		btn60to90daysTotCnt =0;
		btn90to120daysTotCnt =0;
	    btn120to150daysTotCnt =0;
	    btn150to180daysTotCnt =0;
	    grt180daysTotCnt =0;
		totCnt =0;
		for (ReportBean bean : dispReportBeans) {
		    btn30to60daysTotCnt = btn30to60daysTotCnt + bean.getBtn30to60daysCnt();
		    btn60to90daysTotCnt = btn60to90daysTotCnt + bean.getBtn60to90daysCnt();
		    btn90to120daysTotCnt = btn90to120daysTotCnt +bean.getBtn90to120daysCnt();
		    btn120to150daysTotCnt =btn120to150daysTotCnt +bean.getBtn120to150daysCnt();
		    btn150to180daysTotCnt =btn120to150daysTotCnt +bean.getBtn150to180daysCnt();
		    grt180daysTotCnt = grt180daysTotCnt +bean.getGrt180daysCnt();
		    totCnt = totCnt +bean.getTtl();
		}
	}

	
	public void barGraph(List<ReportBean> dispReportBeans)
	{
		
		cartesianChartModel = new CartesianChartModel();
		
		ChartSeries chartSeries1 = new ChartSeries();
		chartSeries1.setLabel("Between 30 to 60 days");
		for (ReportBean bean : dispReportBeans) 
		{
			chartSeries1.set(bean.getLable(),bean.getBtn30to60daysCnt());
		}
		
		ChartSeries chartSeries2 = new ChartSeries();
		chartSeries2.setLabel("Between 60 to 90 days");
		for (ReportBean bean : dispReportBeans) 
		{
			chartSeries2.set(bean.getLable(),bean.getBtn60to90daysCnt());
		}
		
		ChartSeries chartSeries3 = new ChartSeries();
		chartSeries3.setLabel("Between 90 to 120 days");
		for (ReportBean bean : dispReportBeans) 
		{
			chartSeries3.set(bean.getLable(),bean.getBtn90to120daysCnt());
		}
		
		ChartSeries chartSeries4 = new ChartSeries();
		chartSeries4.setLabel("Between 120 to 150 days");
		for (ReportBean bean : dispReportBeans) 
		{
			chartSeries4.set(bean.getLable(),bean.getBtn120to150daysCnt());
		}
		
		ChartSeries chartSeries5 = new ChartSeries();
		chartSeries5.setLabel("Between 150 to 180 days");
		for (ReportBean bean : dispReportBeans) 
		{
			chartSeries5.set(bean.getLable(),bean.getBtn150to180daysCnt());
		}
		
		ChartSeries chartSeries6 = new ChartSeries();
		chartSeries6.setLabel("Greater than 180 days");
		for (ReportBean bean : dispReportBeans) 
		{
			chartSeries6.set(bean.getLable(),bean.getGrt180daysCnt());
		}
		
		cartesianChartModel.addSeries(chartSeries1);
		cartesianChartModel.addSeries(chartSeries2);
		cartesianChartModel.addSeries(chartSeries3);
		cartesianChartModel.addSeries(chartSeries4);
		cartesianChartModel.addSeries(chartSeries5);
		cartesianChartModel.addSeries(chartSeries6);
		
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

	

	public AgeingServiceDao getAgeingServiceDao() {
		return ageingServiceDao;
	}

	public void setAgeingServiceDao(AgeingServiceDao ageingServiceDao) {
		this.ageingServiceDao = ageingServiceDao;
	}



	
	public int getTotCnt() {
		return totCnt;
	}

	public void setTotCnt(int totCnt) {
		this.totCnt = totCnt;
	}

	public int getBtn30to60daysTotCnt() {
		return btn30to60daysTotCnt;
	}

	public void setBtn30to60daysTotCnt(int btn30to60daysTotCnt) {
		this.btn30to60daysTotCnt = btn30to60daysTotCnt;
	}

	public int getBtn60to90daysTotCnt() {
		return btn60to90daysTotCnt;
	}

	public void setBtn60to90daysTotCnt(int btn60to90daysTotCnt) {
		this.btn60to90daysTotCnt = btn60to90daysTotCnt;
	}

	public int getBtn90to120daysTotCnt() {
		return btn90to120daysTotCnt;
	}

	public void setBtn90to120daysTotCnt(int btn90to120daysTotCnt) {
		this.btn90to120daysTotCnt = btn90to120daysTotCnt;
	}

	public int getBtn120to150daysTotCnt() {
		return btn120to150daysTotCnt;
	}

	public void setBtn120to150daysTotCnt(int btn120to150daysTotCnt) {
		this.btn120to150daysTotCnt = btn120to150daysTotCnt;
	}

	public int getBtn150to180daysTotCnt() {
		return btn150to180daysTotCnt;
	}

	public void setBtn150to180daysTotCnt(int btn150to180daysTotCnt) {
		this.btn150to180daysTotCnt = btn150to180daysTotCnt;
	}

	public int getGrt180daysTotCnt() {
		return grt180daysTotCnt;
	}

	public void setGrt180daysTotCnt(int grt180daysTotCnt) {
		this.grt180daysTotCnt = grt180daysTotCnt;
	}

	public CartesianChartModel getCartesianChartModel() {
		return cartesianChartModel;
	}

	public void setCartesianChartModel(CartesianChartModel cartesianChartModel) {
		this.cartesianChartModel = cartesianChartModel;
	}

	public boolean isBackbtn() {
		return backbtn;
	}

	public void setBackbtn(boolean backbtn) {
		this.backbtn = backbtn;
	}

}
