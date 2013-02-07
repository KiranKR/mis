package com.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.chart.PieChartModel;

import com.Dao.SchemeWiseRepDao;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.ReportBean;
import com.bean.StackBean;

@ManagedBean(name = "pc_schemeWiseRep")
@SessionScoped
public class SchemeWiseRep extends PageCodeBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// public class SchemeWiseRep {
	SchemeWiseRepDao dao = new SchemeWiseRepDao();
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
	boolean backbtn = false;

	StackBean stackBean = null;
	List<StackBean> stackBeans = new ArrayList<StackBean>();

	PieChartModel chartModel1;
	PieChartModel chartModel2;
	int scTotCnt = 0;
	int stTotCnt = 0;
	int bccTotCnt = 0;
	int mcTotCnt = 0;
	int totCnt = 0;
	double scTotAmt = 0.00;
	double stTotAmt = 0.00;
	double bccTotAmt = 0.00;
	double mcTotAmt = 0.00;
	double totAmt = 0.00;

	public SchemeWiseRep() {
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
		scTotCnt = 0;
		stTotCnt = 0;
		bccTotCnt = 0;
		mcTotCnt = 0;
		scTotAmt = 0.00;
		stTotAmt = 0.00;
		bccTotAmt = 0.00;
		mcTotAmt = 0.00;
		totCnt = 0;
		totAmt = 0.00;
		dispReportBeans = dao.viewData(role, roleBelong);
		for (ReportBean reportBean : dispReportBeans) {
			scTotCnt = scTotCnt + reportBean.getScCnt();
			stTotCnt = stTotCnt + reportBean.getStCnt();
			bccTotCnt = bccTotCnt + reportBean.getBcCnt();
			mcTotCnt = mcTotCnt + reportBean.getMineCnt();
			scTotAmt = scTotAmt + reportBean.getScttlamnt();
			stTotAmt = stTotAmt + reportBean.getStttlamnt();
			bccTotAmt = bccTotAmt + reportBean.getBcttlamnt();
			mcTotAmt = mcTotAmt + reportBean.getMinettlamnt();
			totCnt = totCnt + reportBean.getTtl();
			totAmt = totAmt + reportBean.getTtlamnt();
		}
		if (role.equals("1")) {
			chartModel1 = new PieChartModel();
			chartModel1.set("Dr. B.R. Ambedkar Total Count", scTotCnt);
			chartModel1.set("Tribal Colony Total Count", stTotCnt);
			chartModel1.set("D.Devaraj Urs Total Count", bccTotCnt);
			chartModel1.set("Minorty Total Count", mcTotCnt);

			chartModel2 = new PieChartModel();
			chartModel2.set("Dr.B.R.Ambedkar Total Amonut", scTotAmt);
			chartModel2.set("Tribal Colony Total Amonut", stTotAmt);
			chartModel2.set("D.Devaraj Urs Total Amonut", bccTotAmt);
			chartModel2.set("MinorityTotal Amonut", mcTotAmt);

		} else if (role.equals("2")) {
			chartModel1 = new PieChartModel();
			chartModel1.set("Dr. B.R. Ambedkar Total Count", scTotCnt);
			chartModel1.set("Tribal Colony Total Count", stTotCnt);
			chartModel1.set("D.Devaraj Urs Total Count", bccTotCnt);
			chartModel1.set("Minorty Total Count", mcTotCnt);

			chartModel2 = new PieChartModel();
			chartModel2.set("Dr.B.R.Ambedkar Total Amonut", scTotAmt);
			chartModel2.set("Tribal Colony Total Amonut", stTotAmt);
			chartModel2.set("D.Devaraj Urs Total Amonut", bccTotAmt);
			chartModel2.set("MinorityTotal Amonut", mcTotAmt);

		} else if (role.equals("3")) {
			chartModel1 = new PieChartModel();
			chartModel1.set("Dr. B.R. Ambedkar Total Count", scTotCnt);
			chartModel1.set("Tribal Colony Total Count", stTotCnt);
			chartModel1.set("D.Devaraj Urs Total Count", bccTotCnt);
			chartModel1.set("Minorty Total Count", mcTotCnt);

			chartModel2 = new PieChartModel();
			chartModel2.set("Dr.B.R.Ambedkar Total Amonut", scTotAmt);
			chartModel2.set("Tribal Colony Total Amonut", stTotAmt);
			chartModel2.set("D.Devaraj Urs Total Amonut", bccTotAmt);
			chartModel2.set("MinorityTotal Amonut", mcTotAmt);

		} else if (role.equals("4")) {
			chartModel1 = new PieChartModel();
			chartModel1.set("Dr. B.R. Ambedkar Total Count", scTotCnt);
			chartModel1.set("Tribal Colony Total Count", stTotCnt);
			chartModel1.set("D.Devaraj Urs Total Count", bccTotCnt);
			chartModel1.set("Minorty Total Count", mcTotCnt);

			chartModel2 = new PieChartModel();
			chartModel2.set("Dr.B.R.Ambedkar Total Amonut", scTotAmt);
			chartModel2.set("Tribal Colony Total Amonut", stTotAmt);
			chartModel2.set("D.Devaraj Urs Total Amonut", bccTotAmt);
			chartModel2.set("MinorityTotal Amonut", mcTotAmt);

		}
	}

	public String getSchemeWiseRep(String id, String lvl) {

		System.out.println(id);
		System.out.println(lvl);
		
		if(lvl.equals("5") && stackBeans.size() == 1){
			backbtn = false;
			}else{
			backbtn = true;
			}
		/*scTotCnt = 0;
		stTotCnt = 0;
		bccTotCnt = 0;
		mcTotCnt = 0;
		scTotAmt = 0.00;
		stTotAmt = 0.00;
		bccTotAmt = 0.00;
		mcTotAmt = 0.00;
		totCnt = 0;
		totAmt = 0.00;*/


		if (lvl.equals("4")) {
			header = "Section";
			stackBeans.add(new StackBean("Section", id));
			dispReportBeans = dao.secWise(id);
			for (ReportBean reportBean : dispReportBeans) {
				scTotCnt = scTotCnt + reportBean.getScCnt();
				stTotCnt = stTotCnt + reportBean.getStCnt();
				bccTotCnt = bccTotCnt + reportBean.getBcCnt();
				mcTotCnt = mcTotCnt + reportBean.getMineCnt();
				scTotAmt = scTotAmt + reportBean.getScttlamnt();
				stTotAmt = stTotAmt + reportBean.getStttlamnt();
				bccTotAmt = bccTotAmt + reportBean.getBcttlamnt();
				mcTotAmt = mcTotAmt + reportBean.getMinettlamnt();
				totCnt = totCnt + reportBean.getTtl();
				totAmt = totAmt + reportBean.getTtlamnt();
			}
			chartModel1 = new PieChartModel();
			chartModel1.set("Dr. B.R. Ambedkar Total Count", scTotCnt);
			chartModel1.set("Tribal Colony Total Count", stTotCnt);
			chartModel1.set("D.Devaraj Urs Total Count", bccTotCnt);
			chartModel1.set("Minorty Total Count", mcTotCnt);

			chartModel2 = new PieChartModel();
			chartModel2.set("Dr.B.R.Ambedkar Total Amonut", scTotAmt);
			chartModel2.set("Tribal Colony Total Amonut", stTotAmt);
			chartModel2.set("D.Devaraj Urs Total Amonut", bccTotAmt);
			chartModel2.set("MinorityTotal Amonut", mcTotAmt);

		} else if (lvl.equals("3")) {
			header = "Sub Division";
			stackBeans.add(new StackBean("Sub Division", id));
			dispReportBeans = dao.subDivWise(id);
			for (ReportBean reportBean : dispReportBeans) {
				scTotCnt = scTotCnt + reportBean.getScCnt();
				stTotCnt = stTotCnt + reportBean.getStCnt();
				bccTotCnt = bccTotCnt + reportBean.getBcCnt();
				mcTotCnt = mcTotCnt + reportBean.getMineCnt();
				scTotAmt = scTotAmt + reportBean.getScttlamnt();
				stTotAmt = stTotAmt + reportBean.getStttlamnt();
				bccTotAmt = bccTotAmt + reportBean.getBcttlamnt();
				mcTotAmt = mcTotAmt + reportBean.getMinettlamnt();
				totCnt = totCnt + reportBean.getTtl();
				totAmt = totAmt + reportBean.getTtlamnt();
			}
			chartModel1 = new PieChartModel();
			chartModel1.set("Dr. B.R. Ambedkar Total Count", scTotCnt);
			chartModel1.set("Tribal Colony Total Count", stTotCnt);
			chartModel1.set("D.Devaraj Urs Total Count", bccTotCnt);
			chartModel1.set("Minorty Total Count", mcTotCnt);

			chartModel2 = new PieChartModel();
			chartModel2.set("Dr.B.R.Ambedkar Total Amonut", scTotAmt);
			chartModel2.set("Tribal Colony Total Amonut", stTotAmt);
			chartModel2.set("D.Devaraj Urs Total Amonut", bccTotAmt);
			chartModel2.set("MinorityTotal Amonut", mcTotAmt);

		} else if (lvl.equals("2")) {
			header = "Division";
			stackBeans.add(new StackBean("Division", id));
			dispReportBeans = dao.divWise(id);
			for (ReportBean reportBean : dispReportBeans) {
				scTotCnt = scTotCnt + reportBean.getScCnt();
				stTotCnt = stTotCnt + reportBean.getStCnt();
				bccTotCnt = bccTotCnt + reportBean.getBcCnt();
				mcTotCnt = mcTotCnt + reportBean.getMineCnt();
				scTotAmt = scTotAmt + reportBean.getScttlamnt();
				stTotAmt = stTotAmt + reportBean.getStttlamnt();
				bccTotAmt = bccTotAmt + reportBean.getBcttlamnt();
				mcTotAmt = mcTotAmt + reportBean.getMinettlamnt();
				totCnt = totCnt + reportBean.getTtl();
				totAmt = totAmt + reportBean.getTtlamnt();
			}
			chartModel1 = new PieChartModel();
			chartModel1.set("Dr. B.R. Ambedkar Total Count", scTotCnt);
			chartModel1.set("Tribal Colony Total Count", stTotCnt);
			chartModel1.set("D.Devaraj Urs Total Count", bccTotCnt);
			chartModel1.set("Minorty Total Count", mcTotCnt);

			chartModel2 = new PieChartModel();
			chartModel2.set("Dr.B.R.Ambedkar Total Amonut", scTotAmt);
			chartModel2.set("Tribal Colony Total Amonut", stTotAmt);
			chartModel2.set("D.Devaraj Urs Total Amonut", bccTotAmt);
			chartModel2.set("MinorityTotal Amonut", mcTotAmt);

		} else if (lvl.equals("1")) {
			header = "Circle";
			stackBeans.add(new StackBean("Circle", id));
			dispReportBeans = dao.circleWise(id);
			for (ReportBean reportBean : dispReportBeans) {
				scTotCnt = scTotCnt + reportBean.getScCnt();
				stTotCnt = stTotCnt + reportBean.getStCnt();
				bccTotCnt = bccTotCnt + reportBean.getBcCnt();
				mcTotCnt = mcTotCnt + reportBean.getMineCnt();
				scTotAmt = scTotAmt + reportBean.getScttlamnt();
				stTotAmt = stTotAmt + reportBean.getStttlamnt();
				bccTotAmt = bccTotAmt + reportBean.getBcttlamnt();
				mcTotAmt = mcTotAmt + reportBean.getMinettlamnt();
				totCnt = totCnt + reportBean.getTtl();
				totAmt = totAmt + reportBean.getTtlamnt();
			}
			chartModel1 = new PieChartModel();
			chartModel1.set("Dr. B.R. Ambedkar Total Count", scTotCnt);
			chartModel1.set("Tribal Colony Total Count", stTotCnt);
			chartModel1.set("D.Devaraj Urs Total Count", bccTotCnt);
			chartModel1.set("Minorty Total Count", mcTotCnt);

			chartModel2 = new PieChartModel();
			chartModel2.set("Dr.B.R.Ambedkar Total Amonut", scTotAmt);
			chartModel2.set("Tribal Colony Total Amonut", stTotAmt);
			chartModel2.set("D.Devaraj Urs Total Amonut", bccTotAmt);
			chartModel2.set("MinorityTotal Amonut", mcTotAmt);

		}
		
		return "schemewiseReport";
	}

	public String back() {
		int size = stackBeans.size();
		String id = "";
		String lvl = "";
		size = size - 1;
		scTotCnt = 0;
		stTotCnt = 0;
		bccTotCnt = 0;
		mcTotCnt = 0;
		scTotAmt = 0.00;
		stTotAmt = 0.00;
		bccTotAmt = 0.00;
		mcTotAmt = 0.00;
		totCnt = 0;
		totAmt = 0.00;

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
				dispReportBeans = dao.secWise(id);
				for (ReportBean reportBean : dispReportBeans) {
					scTotCnt = scTotCnt + reportBean.getScCnt();
					stTotCnt = stTotCnt + reportBean.getStCnt();
					bccTotCnt = bccTotCnt + reportBean.getBcCnt();
					mcTotCnt = mcTotCnt + reportBean.getMineCnt();
					scTotAmt = scTotAmt + reportBean.getScttlamnt();
					stTotAmt = stTotAmt + reportBean.getStttlamnt();
					bccTotAmt = bccTotAmt + reportBean.getBcttlamnt();
					mcTotAmt = mcTotAmt + reportBean.getMinettlamnt();
					totCnt = totCnt + reportBean.getTtl();
					totAmt = totAmt + reportBean.getTtlamnt();
				}
				chartModel1 = new PieChartModel();
				chartModel1.set("Dr. B.R. Ambedkar Total Count", scTotCnt);
				chartModel1.set("Tribal Colony Total Count", stTotCnt);
				chartModel1.set("D.Devaraj Urs Total Count", bccTotCnt);
				chartModel1.set("Minorty Total Count", mcTotCnt);

				chartModel2 = new PieChartModel();
				chartModel2.set("Dr.B.R.Ambedkar Total Amonut", scTotAmt);
				chartModel2.set("Tribal Colony Total Amonut", stTotAmt);
				chartModel2.set("D.Devaraj Urs Total Amonut", bccTotAmt);
				chartModel2.set("MinorityTotal Amonut", mcTotAmt);

			} else if (lvl.equals("Sub Division")) {
				header = "Sub Division";
				dispReportBeans = dao.subDivWise(id);
				for (ReportBean reportBean : dispReportBeans) {
					scTotCnt = scTotCnt + reportBean.getScCnt();
					stTotCnt = stTotCnt + reportBean.getStCnt();
					bccTotCnt = bccTotCnt + reportBean.getBcCnt();
					mcTotCnt = mcTotCnt + reportBean.getMineCnt();
					scTotAmt = scTotAmt + reportBean.getScttlamnt();
					stTotAmt = stTotAmt + reportBean.getStttlamnt();
					bccTotAmt = bccTotAmt + reportBean.getBcttlamnt();
					mcTotAmt = mcTotAmt + reportBean.getMinettlamnt();
					totCnt = totCnt + reportBean.getTtl();
					totAmt = totAmt + reportBean.getTtlamnt();
				}
				chartModel1 = new PieChartModel();
				chartModel1.set("Dr. B.R. Ambedkar Total Count", scTotCnt);
				chartModel1.set("Tribal Colony Total Count", stTotCnt);
				chartModel1.set("D.Devaraj Urs Total Count", bccTotCnt);
				chartModel1.set("Minorty Total Count", mcTotCnt);

				chartModel2 = new PieChartModel();
				chartModel2.set("Dr.B.R.Ambedkar Total Amonut", scTotAmt);
				chartModel2.set("Tribal Colony Total Amonut", stTotAmt);
				chartModel2.set("D.Devaraj Urs Total Amonut", bccTotAmt);
				chartModel2.set("MinorityTotal Amonut", mcTotAmt);

			} else if (lvl.equals("Division")) {
				header = "Division";
				dispReportBeans = dao.divWise(id);
				for (ReportBean reportBean : dispReportBeans) {
					scTotCnt = scTotCnt + reportBean.getScCnt();
					stTotCnt = stTotCnt + reportBean.getStCnt();
					bccTotCnt = bccTotCnt + reportBean.getBcCnt();
					mcTotCnt = mcTotCnt + reportBean.getMineCnt();
					scTotAmt = scTotAmt + reportBean.getScttlamnt();
					stTotAmt = stTotAmt + reportBean.getStttlamnt();
					bccTotAmt = bccTotAmt + reportBean.getBcttlamnt();
					mcTotAmt = mcTotAmt + reportBean.getMinettlamnt();
					totCnt = totCnt + reportBean.getTtl();
					totAmt = totAmt + reportBean.getTtlamnt();
				}
				chartModel1 = new PieChartModel();
				chartModel1.set("Dr. B.R. Ambedkar Total Count", scTotCnt);
				chartModel1.set("Tribal Colony Total Count", stTotCnt);
				chartModel1.set("D.Devaraj Urs Total Count", bccTotCnt);
				chartModel1.set("Minorty Total Count", mcTotCnt);

				chartModel2 = new PieChartModel();
				chartModel2.set("Dr.B.R.Ambedkar Total Amonut", scTotAmt);
				chartModel2.set("Tribal Colony Total Amonut", stTotAmt);
				chartModel2.set("D.Devaraj Urs Total Amonut", bccTotAmt);
				chartModel2.set("MinorityTotal Amonut", mcTotAmt);

			} else if (lvl.equals("Circle")) {
				header = "Circle";
				dispReportBeans = dao.circleWise(id);
				for (ReportBean reportBean : dispReportBeans) {
					scTotCnt = scTotCnt + reportBean.getScCnt();
					stTotCnt = stTotCnt + reportBean.getStCnt();
					bccTotCnt = bccTotCnt + reportBean.getBcCnt();
					mcTotCnt = mcTotCnt + reportBean.getMineCnt();
					scTotAmt = scTotAmt + reportBean.getScttlamnt();
					stTotAmt = stTotAmt + reportBean.getStttlamnt();
					bccTotAmt = bccTotAmt + reportBean.getBcttlamnt();
					mcTotAmt = mcTotAmt + reportBean.getMinettlamnt();
					totCnt = totCnt + reportBean.getTtl();
					totAmt = totAmt + reportBean.getTtlamnt();
				}
				chartModel1 = new PieChartModel();
				chartModel1.set("Dr. B.R. Ambedkar Total Count", scTotCnt);
				chartModel1.set("Tribal Colony Total Count", stTotCnt);
				chartModel1.set("D.Devaraj Urs Total Count", bccTotCnt);
				chartModel1.set("Minorty Total Count", mcTotCnt);

				chartModel2 = new PieChartModel();
				chartModel2.set("Dr.B.R.Ambedkar Total Amonut", scTotAmt);
				chartModel2.set("Tribal Colony Total Amonut", stTotAmt);
				chartModel2.set("D.Devaraj Urs Total Amonut", bccTotAmt);
				chartModel2.set("MinorityTotal Amonut", mcTotAmt);

			}

		} else {
			StackBean bean = stackBeans.get(0);
			id = bean.getId();
			lvl = bean.getLvl();
			
			if (lvl.equals("Section")) {
				header = "Section";
				dispReportBeans = dao.secWise(id);
				for (ReportBean reportBean : dispReportBeans) {
					scTotCnt = scTotCnt + reportBean.getScCnt();
					stTotCnt = stTotCnt + reportBean.getStCnt();
					bccTotCnt = bccTotCnt + reportBean.getBcCnt();
					mcTotCnt = mcTotCnt + reportBean.getMineCnt();
					scTotAmt = scTotAmt + reportBean.getScttlamnt();
					stTotAmt = stTotAmt + reportBean.getStttlamnt();
					bccTotAmt = bccTotAmt + reportBean.getBcttlamnt();
					mcTotAmt = mcTotAmt + reportBean.getMinettlamnt();
					totCnt = totCnt + reportBean.getTtl();
					totAmt = totAmt + reportBean.getTtlamnt();
				}
				chartModel1 = new PieChartModel();
				chartModel1.set("Dr. B.R. Ambedkar Total Count", scTotCnt);
				chartModel1.set("Tribal Colony Total Count", stTotCnt);
				chartModel1.set("D.Devaraj Urs Total Count", bccTotCnt);
				chartModel1.set("Minorty Total Count", mcTotCnt);

				chartModel2 = new PieChartModel();
				chartModel2.set("Dr.B.R.Ambedkar Total Amonut", scTotAmt);
				chartModel2.set("Tribal Colony Total Amonut", stTotAmt);
				chartModel2.set("D.Devaraj Urs Total Amonut", bccTotAmt);
				chartModel2.set("MinorityTotal Amonut", mcTotAmt);

			} else if (lvl.equals("Sub Division")) {
				header = "Sub Division";
				dispReportBeans = dao.subDivWise(id);
				for (ReportBean reportBean : dispReportBeans) {
					scTotCnt = scTotCnt + reportBean.getScCnt();
					stTotCnt = stTotCnt + reportBean.getStCnt();
					bccTotCnt = bccTotCnt + reportBean.getBcCnt();
					mcTotCnt = mcTotCnt + reportBean.getMineCnt();
					scTotAmt = scTotAmt + reportBean.getScttlamnt();
					stTotAmt = stTotAmt + reportBean.getStttlamnt();
					bccTotAmt = bccTotAmt + reportBean.getBcttlamnt();
					mcTotAmt = mcTotAmt + reportBean.getMinettlamnt();
					totCnt = totCnt + reportBean.getTtl();
					totAmt = totAmt + reportBean.getTtlamnt();
				}
				chartModel1 = new PieChartModel();
				chartModel1.set("Dr. B.R. Ambedkar Total Count", scTotCnt);
				chartModel1.set("Tribal Colony Total Count", stTotCnt);
				chartModel1.set("D.Devaraj Urs Total Count", bccTotCnt);
				chartModel1.set("Minorty Total Count", mcTotCnt);

				chartModel2 = new PieChartModel();
				chartModel2.set("Dr.B.R.Ambedkar Total Amonut", scTotAmt);
				chartModel2.set("Tribal Colony Total Amonut", stTotAmt);
				chartModel2.set("D.Devaraj Urs Total Amonut", bccTotAmt);
				chartModel2.set("MinorityTotal Amonut", mcTotAmt);

			} else if (lvl.equals("Division")) {
				header = "Division";
				dispReportBeans = dao.divWise(id);
				for (ReportBean reportBean : dispReportBeans) {
					scTotCnt = scTotCnt + reportBean.getScCnt();
					stTotCnt = stTotCnt + reportBean.getStCnt();
					bccTotCnt = bccTotCnt + reportBean.getBcCnt();
					mcTotCnt = mcTotCnt + reportBean.getMineCnt();
					scTotAmt = scTotAmt + reportBean.getScttlamnt();
					stTotAmt = stTotAmt + reportBean.getStttlamnt();
					bccTotAmt = bccTotAmt + reportBean.getBcttlamnt();
					mcTotAmt = mcTotAmt + reportBean.getMinettlamnt();
					totCnt = totCnt + reportBean.getTtl();
					totAmt = totAmt + reportBean.getTtlamnt();
				}
				chartModel1 = new PieChartModel();
				chartModel1.set("Dr. B.R. Ambedkar Total Count", scTotCnt);
				chartModel1.set("Tribal Colony Total Count", stTotCnt);
				chartModel1.set("D.Devaraj Urs Total Count", bccTotCnt);
				chartModel1.set("Minorty Total Count", mcTotCnt);

				chartModel2 = new PieChartModel();
				chartModel2.set("Dr.B.R.Ambedkar Total Amonut", scTotAmt);
				chartModel2.set("Tribal Colony Total Amonut", stTotAmt);
				chartModel2.set("D.Devaraj Urs Total Amonut", bccTotAmt);
				chartModel2.set("MinorityTotal Amonut", mcTotAmt);

			} else if (lvl.equals("Circle")) {
				header = "Circle";
				dispReportBeans = dao.circleWise(id);
				for (ReportBean reportBean : dispReportBeans) {
					scTotCnt = scTotCnt + reportBean.getScCnt();
					stTotCnt = stTotCnt + reportBean.getStCnt();
					bccTotCnt = bccTotCnt + reportBean.getBcCnt();
					mcTotCnt = mcTotCnt + reportBean.getMineCnt();
					scTotAmt = scTotAmt + reportBean.getScttlamnt();
					stTotAmt = stTotAmt + reportBean.getStttlamnt();
					bccTotAmt = bccTotAmt + reportBean.getBcttlamnt();
					mcTotAmt = mcTotAmt + reportBean.getMinettlamnt();
					totCnt = totCnt + reportBean.getTtl();
					totAmt = totAmt + reportBean.getTtlamnt();
				}
				chartModel1 = new PieChartModel();
				chartModel1.set("Dr.B.R.Ambedkar Total Count", scTotCnt);
				chartModel1.set("Tribal Colony Total Count", stTotCnt);
				chartModel1.set("D.Devaraj Urs Total Count", bccTotCnt);
				chartModel1.set("Minorty Total Count", mcTotCnt);

				chartModel2 = new PieChartModel();
				chartModel2.set("Dr.B.R.Ambedkar Total Amonut", scTotAmt);
				chartModel2.set("Tribal Colony Total Amonut", stTotAmt);
				chartModel2.set("D.Devaraj Urs Total Amonut", bccTotAmt);
				chartModel2.set("MinorityTotal Amonut", mcTotAmt);

			}

		}
		return "schemewiseReport";
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

	public int getScTotCnt() {
		return scTotCnt;
	}

	public void setScTotCnt(int scTotCnt) {
		this.scTotCnt = scTotCnt;
	}

	public int getStTotCnt() {
		return stTotCnt;
	}

	public void setStTotCnt(int stTotCnt) {
		this.stTotCnt = stTotCnt;
	}

	public int getBccTotCnt() {
		return bccTotCnt;
	}

	public void setBccTotCnt(int bccTotCnt) {
		this.bccTotCnt = bccTotCnt;
	}

	public int getMcTotCnt() {
		return mcTotCnt;
	}

	public void setMcTotCnt(int mcTotCnt) {
		this.mcTotCnt = mcTotCnt;
	}

	public double getScTotAmt() {
		return scTotAmt;
	}

	public void setScTotAmt(double scTotAmt) {
		this.scTotAmt = scTotAmt;
	}

	public double getStTotAmt() {
		return stTotAmt;
	}

	public void setStTotAmt(double stTotAmt) {
		this.stTotAmt = stTotAmt;
	}

	public double getBccTotAmt() {
		return bccTotAmt;
	}

	public void setBccTotAmt(double bccTotAmt) {
		this.bccTotAmt = bccTotAmt;
	}

	public double getMcTotAmt() {
		return mcTotAmt;
	}

	public void setMcTotAmt(double mcTotAmt) {
		this.mcTotAmt = mcTotAmt;
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

	
	public int getTotCnt() {
		return totCnt;
	}

	public void setTotCnt(int totCnt) {
		this.totCnt = totCnt;
	}

	public double getTotAmt() {
		return totAmt;
	}

	public void setTotAmt(double totAmt) {
		this.totAmt = totAmt;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRoleBelong() {
		return roleBelong;
	}

	public void setRoleBelong(String roleBelong) {
		this.roleBelong = roleBelong;
	}

	public boolean isBackbtn() {
		return backbtn;
	}

	public void setBackbtn(boolean backbtn) {
		this.backbtn = backbtn;
	}
    
}
