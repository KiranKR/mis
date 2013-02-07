package com.action;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.Dao.FeederInterruptionReportsDao;
import com.Util.AppUtil;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.FeederInterruptionBean;
import com.bean.FeederInterruptionReportMainBean;
import com.bean.FeederInterruptionReportsBean;

@ManagedBean(name = "pc_FeederInterruptionReports")
@RequestScoped
public class FeederInterruptionReports extends PageCodeBase implements
		Serializable {

	private static final long serialVersionUID = 1L;
	public List secList;

	FeederInterruptionReportsDao finterruptionReportsDao;
	public List<FeederInterruptionReportMainBean> fdiReports;

	private FeederInterruptionBean feederInterruptionBean;
	private FeederInterruptionReportsBean feederInterruptionReportsBean;
	private FeederInterruptionReportMainBean feederInterruptionReportMainBean;

	int count = 0;

	public FeederInterruptionReports() {
		secList = new ArrayList();
		finterruptionReportsDao = new FeederInterruptionReportsDao();
		fdiReports = new ArrayList<FeederInterruptionReportMainBean>();
		feederInterruptionBean = new FeederInterruptionBean();
		try {
			String concatWhere = "";
			final String queryWhere = "JOIN sub_division ON section.SECTN_SUBDIV_ID = sub_division.SUBDIV_ID "
					+ "JOIN division ON sub_division.SUBDIV_DIV_ID = division.DIV_ID "
					+ "JOIN circle ON division.DIV_CIRCLE_ID = circle.CRCL_ID "
					+ "JOIN zone ON circle.CRCL_ZONE_ID = zone.ZONE_ID where ";
			final int idenFlag = Integer
					.valueOf(UserUtil
							.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION));
			if (idenFlag == 1) {
				concatWhere = "zone.ZONE_ID = "
						+ UserUtil
								.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)
						+ " ";
			} else if (idenFlag == 2) {
				concatWhere = "circle.CRCL_ID = "
						+ UserUtil
								.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)
						+ " ";
			} else if (idenFlag == 3) {
				concatWhere = "division.DIV_ID = "
						+ UserUtil
								.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)
						+ " ";
			} else if (idenFlag == 4) {
				concatWhere = "sub_division.SUBDIV_ID = "
						+ UserUtil
								.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)
						+ " ";
			}
			final String whrClause = queryWhere.concat(concatWhere);

			secList = AppUtil.getDropDownList("section", "SECTN_NAME",
					"SECTN_ID", "SECTN_NAME", whrClause, 0);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String FdrReport() {
		if (validate()) {
			fdiReports = finterruptionReportsDao.getFeederReportDetails(
					feederInterruptionBean.getSectionId(),
					feederInterruptionBean.getYearId());
			totalFdrCount(fdiReports);
			return "success";
		} else {
			return "fdrReportFailure";
		}
	}

	public void totalFdrCount(List<FeederInterruptionReportMainBean> fdiReports) {

		List<FeederInterruptionReportsBean> feederInterruptionReportsBeans = null;

		for (FeederInterruptionReportMainBean bean : fdiReports) {
			feederInterruptionReportsBeans = bean.getFedintrputionDetails();
			for (FeederInterruptionReportsBean reportBean : feederInterruptionReportsBeans) {
				count = count + reportBean.getFedCount();
			}

		}

	}

	public boolean validate() {
		boolean isValid = true;

		if (nullCheck(feederInterruptionBean.getSectionId())) {
			addMessage("ERRCD537E");
			isValid = false;
			return isValid;
		} else if (nullCheck(feederInterruptionBean.getYearId())) {
			addMessage("ERRCD539E");
			isValid = false;
			return isValid;

		}
		return isValid;
	}

	

	public List getSecList() {
		return secList;
	}

	public void setSecList(List secList) {
		this.secList = secList;
	}

	public FeederInterruptionReportsDao getFinterruptionReportsDao() {
		return finterruptionReportsDao;
	}

	public void setFinterruptionReportsDao(
			FeederInterruptionReportsDao finterruptionReportsDao) {
		this.finterruptionReportsDao = finterruptionReportsDao;
	}

	public List<FeederInterruptionReportMainBean> getFdiReports() {
		return fdiReports;
	}

	public void setFdiReports(List<FeederInterruptionReportMainBean> fdiReports) {
		this.fdiReports = fdiReports;
	}

	public FeederInterruptionBean getFeederInterruptionBean() {
		return feederInterruptionBean;
	}

	public void setFeederInterruptionBean(
			FeederInterruptionBean feederInterruptionBean) {
		this.feederInterruptionBean = feederInterruptionBean;
	}

	public FeederInterruptionReportsBean getFeederInterruptionReportsBean() {
		return feederInterruptionReportsBean;
	}

	public void setFeederInterruptionReportsBean(
			FeederInterruptionReportsBean feederInterruptionReportsBean) {
		this.feederInterruptionReportsBean = feederInterruptionReportsBean;
	}

	public FeederInterruptionReportMainBean getFeederInterruptionReportMainBean() {
		return feederInterruptionReportMainBean;
	}

	public void setFeederInterruptionReportMainBean(
			FeederInterruptionReportMainBean feederInterruptionReportMainBean) {
		this.feederInterruptionReportMainBean = feederInterruptionReportMainBean;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
