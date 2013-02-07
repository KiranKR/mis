package com.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.DisconnectionRepDao;
import com.Dao.DisconnectionSummaryDao;
import com.Util.AppUtil;
import com.Util.EnumMonth;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.DisconnectionBean;
import com.bean.DisconnectionSummaryBean;
import com.bean.StackBean;

@ManagedBean(name = "pc_DisconnectionSummary")
@SessionScoped
public class DisconnectionSummary extends PageCodeBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private DisconnectionSummaryBean disconnectionSummaryBean = new DisconnectionSummaryBean();
	private List<DisconnectionSummaryBean> discSumList = new ArrayList<DisconnectionSummaryBean>();
	private List<DisconnectionSummaryBean> dispDiscSumList = new ArrayList<DisconnectionSummaryBean>();
	private DisconnectionSummaryDao dao = new DisconnectionSummaryDao();

	private DisconnectionRepDao disRepDao = new DisconnectionRepDao();
	private List<DisconnectionBean> lstDisBeans = new ArrayList<DisconnectionBean>();
	private DisconnectionRep disRep = new DisconnectionRep();

	DisconnectionSummaryBean bean = null;

	String role = UserUtil
			.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION);
	String roleBelong = UserUtil
			.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION);
	String header = "";
	String znId = "";
	String crlId = "";
	String divId = "";
	String subDivId = "";
	String secId = "";
	StackBean stackBean = null;
	List<EnumMonth> enumMonths = new ArrayList<EnumMonth>();
	List<StackBean> stackBeans = new ArrayList<StackBean>();
	boolean backbtn = false;

	int grandVisiDuringMonth = 0;
	int grandDiscDuringMonth = 0;
	double grandTtlOB = 0.0;
	double grandTtlAmtInv = 0.0;
	double grandTtlAmtRel = 0.0;
	double grandTtlCB = 0.0;

	String fromYearID;
	String toYearId;

	String fromMonthID;
	String toMonthID;

	public DisconnectionSummary() {

		enumMonths = Arrays.asList(EnumMonth.values());
	}

	public String getDisconnectionSummaryList() {
		stackBeans.clear();

		if (validate()) {

			if (role.equals("1")) {
				header = "Zone";
				znId = roleBelong;

			} else if (role.equals("2")) {
				header = "Circle";
				crlId = roleBelong;

			} else if (role.equals("3")) {
				header = "Division";
				divId = roleBelong;

			} else if (role.equals("4")) {
				header = "SubDivision";
				subDivId = roleBelong;

			} else if (role.equals("5")) {
				header = "Section";
				secId = roleBelong;

			}

			String from = AppUtil.getMnthId(fromMonthID);
			String to = AppUtil.getMnthId(toMonthID);

			int fromMonth = Integer.parseInt(from);
			int toMonth = Integer.parseInt(to);

			String fromYearMonth = fromYearID + fromMonth;
			String toYearMonth = toYearId + toMonth;

			dispDiscSumList = dao.viewData(role, roleBelong, fromYearMonth,
					toYearMonth);
			grandTotal(dispDiscSumList);
			backbtn = false;

			return "summaryDisconnectionReports";

		} else {
			return "summaryDisconnectionReports1";
		}

	}

	public String getSchemeWiseRep(String id, String lvl, String name) {

		if (lvl.equals("6") && stackBeans.size() == 1) {

			backbtn = false;
		} else {
			backbtn = true;
		}

		if (lvl.equals("4")) {
			header = "Section";
			stackBeans.add(new StackBean("4", id, name, stackBeans.size() + 1));
			dispDiscSumList = dao.secWise(id,lvl);
			grandTotal(dispDiscSumList);

		} else if (lvl.equals("3")) {
			header = "SubDivision";
			stackBeans.add(new StackBean("3", id, name, stackBeans.size() + 1));
			dispDiscSumList = dao.subDivWise(id,lvl);
			grandTotal(dispDiscSumList);

		} else if (lvl.equals("2")) {
			header = "Division";
			stackBeans.add(new StackBean("2", id, name, stackBeans.size() + 1));
			dispDiscSumList = dao.divWise(id,lvl);
			grandTotal(dispDiscSumList);

		} else if (lvl.equals("1")) {
			header = "Circle";
			stackBeans.add(new StackBean("1", id, name, stackBeans.size() + 1));
			dispDiscSumList = dao.circleWise(id,lvl);
			grandTotal(dispDiscSumList);
		}
		
		if(lvl.equals("5")){
			lstDisBeans = disRepDao.getRep(id, fromYearID);
			disRep.getDisDetReport(lstDisBeans);
			return "disconnectionReport";
		}
		

		return "summaryDisconnectionReports";
	}

	public String back(String id, String lvl, String name, String slno) {

		grandVisiDuringMonth = 0;
		grandDiscDuringMonth = 0;
		grandTtlOB = 0.0;
		grandTtlAmtInv = 0.0;
		grandTtlAmtRel = 0.0;
		grandTtlCB = 0.0;

		if (slno.equals("1")) {

			backbtn = false;
		} else {
			backbtn = true;
		}

		if (lvl.equals("5")) {
			header = "Section";
			dispDiscSumList = dao.secWise(id,lvl);
			grandTotal(dispDiscSumList);

		} else if (lvl.equals("4")) {
			header = "SubDivision";
			dispDiscSumList = dao.subDivWise(id,lvl);
			grandTotal(dispDiscSumList);

		} else if (lvl.equals("3")) {
			header = "Division";
			dispDiscSumList = dao.divWise(id,lvl);
			grandTotal(dispDiscSumList);

		} else if (lvl.equals("2")) {
			header = "Circle";
			dispDiscSumList = dao.circleWise(id,lvl);
			grandTotal(dispDiscSumList);
		} else if (lvl.equals("1")) {
			header = "Zone";
			dispDiscSumList = dao.zoneWise(id,lvl);
			grandTotal(dispDiscSumList);

		}
		stackBeans = stackBeans.subList(0, Integer.parseInt(slno) - 1);
		return "summaryDisconnectionReports";
	}

	public void grandTotal(List<DisconnectionSummaryBean> beans) {

		grandVisiDuringMonth = 0;
		grandDiscDuringMonth = 0;
		grandTtlOB = 0.0;
		grandTtlAmtInv = 0.0;
		grandTtlAmtRel = 0.0;
		grandTtlCB = 0.0;

		for (DisconnectionSummaryBean disconnectionSummaryBean : beans) {
			grandVisiDuringMonth += disconnectionSummaryBean.getVisitDurMnth();
			grandDiscDuringMonth += disconnectionSummaryBean
					.getDisconctDurMnth();
			grandTtlOB += disconnectionSummaryBean.getTtlOB();
			grandTtlAmtInv += disconnectionSummaryBean.getTtlAmtInv();

			grandTtlAmtRel += disconnectionSummaryBean.getTtlAmtRel();
			grandTtlCB += disconnectionSummaryBean.getTtlCB();

		}

	}

	public boolean validate() {
		boolean isValid = true;

		if (nullCheck(fromMonthID)) {
			addMessage("ERRCDALLSUM002E");
			isValid = false;
		} else if (nullCheck(fromYearID)) {
			addMessage("ERRCDALLSUM001E");
			isValid = false;
		} else if (nullCheck(toMonthID)) {
			addMessage("ERRCDALLSUM004E");
			isValid = false;
		} else if (nullCheck(toYearId)) {
			addMessage("ERRCDALLSUM003E");
			isValid = false;
		} else if (fromDateLessToDate(fromYearID, fromMonthID, toYearId,
				toMonthID)) {
			addMessage("ERRCDALLSUM005E");
			isValid = false;
			return isValid;
		}
		return isValid;
	}

	public int getGrandVisiDuringMonth() {
		return grandVisiDuringMonth;
	}

	public void setGrandVisiDuringMonth(int grandVisiDuringMonth) {
		this.grandVisiDuringMonth = grandVisiDuringMonth;
	}

	public int getGrandDiscDuringMonth() {
		return grandDiscDuringMonth;
	}

	public void setGrandDiscDuringMonth(int grandDiscDuringMonth) {
		this.grandDiscDuringMonth = grandDiscDuringMonth;
	}

	public double getGrandTtlOB() {
		return grandTtlOB;
	}

	public void setGrandTtlOB(double grandTtlOB) {
		this.grandTtlOB = grandTtlOB;
	}

	public double getGrandTtlAmtInv() {
		return grandTtlAmtInv;
	}

	public void setGrandTtlAmtInv(double grandTtlAmtInv) {
		this.grandTtlAmtInv = grandTtlAmtInv;
	}

	public double getGrandTtlAmtRel() {
		return grandTtlAmtRel;
	}

	public void setGrandTtlAmtRel(double grandTtlAmtRel) {
		this.grandTtlAmtRel = grandTtlAmtRel;
	}

	public double getGrandTtlCB() {
		return grandTtlCB;
	}

	public void setGrandTtlCB(double grandTtlCB) {
		this.grandTtlCB = grandTtlCB;
	}

	public DisconnectionSummaryBean getDisconnectionSummaryBean() {
		return disconnectionSummaryBean;
	}

	public void setDisconnectionSummaryBean(
			DisconnectionSummaryBean disconnectionSummaryBean) {
		this.disconnectionSummaryBean = disconnectionSummaryBean;
	}

	public List<DisconnectionSummaryBean> getDiscSumList() {
		return discSumList;
	}

	public void setDiscSumList(List<DisconnectionSummaryBean> discSumList) {
		this.discSumList = discSumList;
	}

	public List<DisconnectionSummaryBean> getDispDiscSumList() {
		return dispDiscSumList;
	}

	public void setDispDiscSumList(
			List<DisconnectionSummaryBean> dispDiscSumList) {
		this.dispDiscSumList = dispDiscSumList;
	}

	public DisconnectionSummaryDao getDao() {
		return dao;
	}

	public void setDao(DisconnectionSummaryDao dao) {
		this.dao = dao;
	}

	public DisconnectionSummaryBean getBean() {
		return bean;
	}

	public void setBean(DisconnectionSummaryBean bean) {
		this.bean = bean;
	}

	public List<EnumMonth> getEnumMonths() {
		return enumMonths;
	}

	public void setEnumMonths(List<EnumMonth> enumMonths) {
		this.enumMonths = enumMonths;
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

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getZnId() {
		return znId;
	}

	public void setZnId(String znId) {
		this.znId = znId;
	}

	public String getCrlId() {
		return crlId;
	}

	public void setCrlId(String crlId) {
		this.crlId = crlId;
	}

	public String getDivId() {
		return divId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
	}

	public String getSubDivId() {
		return subDivId;
	}

	public void setSubDivId(String subDivId) {
		this.subDivId = subDivId;
	}

	public String getSecId() {
		return secId;
	}

	public void setSecId(String secId) {
		this.secId = secId;
	}

	public StackBean getStackBean() {
		return stackBean;
	}

	public void setStackBean(StackBean stackBean) {
		this.stackBean = stackBean;
	}

	public List<StackBean> getStackBeans() {
		return stackBeans;
	}

	public void setStackBeans(List<StackBean> stackBeans) {
		this.stackBeans = stackBeans;
	}

	public boolean isBackbtn() {
		return backbtn;
	}

	public void setBackbtn(boolean backbtn) {
		this.backbtn = backbtn;
	}

	public String getFromYearID() {
		return fromYearID;
	}

	public void setFromYearID(String fromYearID) {
		this.fromYearID = fromYearID;
	}

	public String getToYearId() {
		return toYearId;
	}

	public void setToYearId(String toYearId) {
		this.toYearId = toYearId;
	}

	public String getFromMonthID() {
		return fromMonthID;
	}

	public void setFromMonthID(String fromMonthID) {
		this.fromMonthID = fromMonthID;
	}

	public String getToMonthID() {
		return toMonthID;
	}

	public void setToMonthID(String toMonthID) {
		this.toMonthID = toMonthID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public DisconnectionRepDao getDisRepDao() {
		return disRepDao;
	}

	public void setDisRepDao(DisconnectionRepDao disRepDao) {
		this.disRepDao = disRepDao;
	}

	public List<DisconnectionBean> getLstDisBeans() {
		return lstDisBeans;
	}

	public void setLstDisBeans(List<DisconnectionBean> lstDisBeans) {
		this.lstDisBeans = lstDisBeans;
	}

	public DisconnectionRep getDisRep() {
		return disRep;
	}

	public void setDisRep(DisconnectionRep disRep) {
		this.disRep = disRep;
	}
}
