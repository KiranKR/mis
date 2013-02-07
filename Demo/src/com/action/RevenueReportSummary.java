package com.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.RevenueReportSummaryDao;
import com.Util.AppUtil;
import com.Util.EnumMonth;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.RevenueReportBeanSummary;
import com.bean.StackBean;

@ManagedBean(name = "pc_revenueReportSummary")
@SessionScoped
public class RevenueReportSummary extends PageCodeBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	String fromYearID;
	String toYearId;

	String fromMonthID;
	String toMonthID;

	List<EnumMonth> enumMonths = new ArrayList<EnumMonth>();
	List<StackBean> stackBeans = new ArrayList<StackBean>();
	boolean backbtn = false;

	RevenueReportBeanSummary revenuebeanSummary = new RevenueReportBeanSummary();
	List<RevenueReportBeanSummary> revenueReportBeanList = new ArrayList<RevenueReportBeanSummary>();
	RevenueReportSummaryDao revenueReportSummaryDao = new RevenueReportSummaryDao();

	public RevenueReportSummary() {

		enumMonths = Arrays.asList(EnumMonth.values());

	}

	public String getRevenueReportList() {
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
			revenueReportBeanList = revenueReportSummaryDao.viewData(role,
					roleBelong, fromYearMonth, toYearMonth);

		}

		return "revenueReportSummary";
	}

	public String getSchemeWiseRep(String id, String name, String lvl) {

		if (lvl.equals("6") && stackBeans.size() == 1) {
			backbtn = false;
		} else {
			backbtn = true;
		}

		if (lvl.equals("4")) {
			header = "Section";
			stackBeans.add(new StackBean("4", id, name, stackBeans.size() + 1));
			revenueReportBeanList = revenueReportSummaryDao.sectionWise(id,lvl);

		} else if (lvl.equals("3")) {
			header = "SubDivision";
			stackBeans.add(new StackBean("3", id, name, stackBeans.size() + 1));

			revenueReportBeanList = revenueReportSummaryDao.subDivisionWise(id,lvl);

		} else if (lvl.equals("2")) {
			header = "Division";
			stackBeans.add(new StackBean("2", id, name, stackBeans.size() + 1));

			revenueReportBeanList = revenueReportSummaryDao.divisionWise(id,lvl);

		} else if (lvl.equals("1")) {
			header = "Circle";
			stackBeans.add(new StackBean("1", id, name, stackBeans.size() + 1));

			revenueReportBeanList = revenueReportSummaryDao.circleWise(id,lvl);

		}

		return "revenueReportSummary";
	}

	public String bread(String lvl, String name, String id, String slno) {
		int size = stackBeans.size();
		if (size >= 1) {

			size = size - 1;
			StackBean bean = stackBeans.get(size);

			if (stackBeans.size() == 1) {
				backbtn = false;
			}

			if (lvl.equals("5")) {
				header = "Section";

				revenueReportBeanList = revenueReportSummaryDao.sectionWise(id,lvl);

			} else if (lvl.equals("4")) {
				header = "SubDivision";

				revenueReportBeanList = revenueReportSummaryDao
						.subDivisionWise(id,lvl);

			} else if (lvl.equals("3")) {
				header = "Division";

				revenueReportBeanList = revenueReportSummaryDao
						.divisionWise(id,lvl);

			} else if (lvl.equals("2")) {
				header = "Circle";

				revenueReportBeanList = revenueReportSummaryDao.circleWise(id,lvl);

			} else if (lvl.equals("1")) {
				header = "Zone";

				revenueReportBeanList = revenueReportSummaryDao.zoneWise(id,lvl);
				backbtn = false;

			}
			stackBeans = stackBeans.subList(0, Integer.parseInt(slno) - 1);

		}

		return "revenueReportSummary.xhtml";
	}

	public boolean validate() {
		boolean isvalid = true;

		if (nullCheck(fromMonthID)) {
			addMessage("ERRCDALLSUM002E");

			isvalid = false;
		} else if (nullCheck(toMonthID)) {
			addMessage("ERRCDALLSUM004E");
			isvalid = false;
		} else if (nullCheck(fromYearID)) {
			addMessage("ERRCDALLSUM001E");
			isvalid = false;
		}

		else if (nullCheck(toYearId)) {
			addMessage("ERRCDALLSUM003E");
			isvalid = false;
		} else if (fromDateLessToDate(fromYearID, fromMonthID, toYearId,
				toMonthID)) {
			addMessage("ERRCDALLSUM005E");
			isvalid = false;
			return isvalid;
		}

		return isvalid;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public List<EnumMonth> getEnumMonths() {
		return enumMonths;
	}

	public void setEnumMonths(List<EnumMonth> enumMonths) {
		this.enumMonths = enumMonths;
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

	public RevenueReportBeanSummary getRevenuebeanSummary() {
		return revenuebeanSummary;
	}

	public void setRevenuebeanSummary(
			RevenueReportBeanSummary revenuebeanSummary) {
		this.revenuebeanSummary = revenuebeanSummary;
	}

	public List<RevenueReportBeanSummary> getRevenueReportBeanList() {
		return revenueReportBeanList;
	}

	public void setRevenueReportBeanList(
			List<RevenueReportBeanSummary> revenueReportBeanList) {
		this.revenueReportBeanList = revenueReportBeanList;
	}

	public RevenueReportSummaryDao getRevenueReportSummaryDao() {
		return revenueReportSummaryDao;
	}

	public void setRevenueReportSummaryDao(
			RevenueReportSummaryDao revenueReportSummaryDao) {
		this.revenueReportSummaryDao = revenueReportSummaryDao;
	}

}
