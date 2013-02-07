package com.action;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.EstimateDao;
import com.Util.AppUtil;
import com.Util.PageCodeBase;
import com.bean.PendingRep;

@ManagedBean(name = "pc_estimate")
@SessionScoped
public class Estimate extends PageCodeBase implements Serializable {

	String search = "";
	private PendingRep pendingRep = new PendingRep();
	private List<PendingRep> pendingReps = new ArrayList<PendingRep>();
	private EstimateDao estimateDao = new EstimateDao();
	private List searchLst = new ArrayList();
	private String criteria = "";
	
	
	public Estimate() {
		try {
			searchLst =  AppUtil.getDropDownList("program_search", "PSRCH_NAME", "PSRCH_ID", "PSRCH_NAME", "where PSRCH_IDEN_FLAG = 0", 0);
			pendingReps = estimateDao.getDisplayDetilas(search.trim(),criteria.trim());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String sndEstView() {

		return "sndEstmtView";
	}

	public String searchEstimate() {
		pendingReps = estimateDao.getDisplayDetilas(search.trim(),criteria.trim());
		search = "";
		return "seachEstimate";

	}

	public String cancelEstimate() {
		clearSession();
		return "cancel";
	}

	
	public String updateEstimate() {
		String status;
		if (validate()) {
			int i = estimateDao.updateEstimate(pendingRep);

			if (i == 1) {
				status = "updateEstimateSuccess";
			} else {
				status = "updateEstimateFailure";
			}
		} else {
			status = "updateEstimateFailure";
		}
		return status;
	}

	public String sndEstimtEditJsf() {
		return "sndEstimateEdit";
	}

	private void clearSession() {
		Map sessionMap = (Map) getBindingValue("#{sessionScope}");
		sessionMap.remove("pc_estimate");
		sessionMap.remove("pc_schemeWiseRep");
		sessionMap.remove("pc_PrgDetailRep");
	}
	
	public boolean validate() {
		boolean isValid = true;

		if (nullCheck(pendingRep.getEstimateNo())) {
			addMessage("ERRCD525E");
			isValid = false;
			return isValid;

		} else if (pendingRep.getEstimateCost() == 0.00) {
			addMessage("ERRCD510E");
			isValid = false;
			return isValid;
		}

		else if (nullCheck(pendingRep.getEstimateCost())) {
			addMessage("ERRCD510E");
			isValid = false;
			return isValid;

		}

		else if (nullCheck(pendingRep.getEstimateDate())) {
			addMessage("ERRCD523E");
			isValid = false;
			return isValid;

		} else if (!isValidDate(pendingRep.getEstimateDate())) {
			addMessage("ERRCD524E");
			isValid = false;
			return isValid;

		}
		return isValid;
	}

	

	

	public boolean isInteger(String input) {
		try {
			Double.parseDouble(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public List<PendingRep> getPendingReps() {
		return pendingReps;
	}

	public void setPendingReps(List<PendingRep> pendingReps) {
		this.pendingReps = pendingReps;
	}

	public EstimateDao getEstimateDao() {
		return estimateDao;
	}

	public void setEstimateDao(EstimateDao estimateDao) {
		this.estimateDao = estimateDao;
	}

	public PendingRep getPendingRep() {
		return pendingRep;
	}

	public void setPendingRep(PendingRep pendingRep) {
		this.pendingRep = pendingRep;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public List getSearchLst() {
		return searchLst;
	}

	public void setSearchLst(List searchLst) {
		this.searchLst = searchLst;
	}
	
}
