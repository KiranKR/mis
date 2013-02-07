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

import com.Dao.ServiceDetailsDao;
import com.Util.AppUtil;
import com.Util.PageCodeBase;
import com.bean.PendingRep;

@ManagedBean(name = "pc_serviceDetails")
@SessionScoped
public class ServiceDetails extends PageCodeBase implements Serializable {

	private List<PendingRep> pendingReps = new ArrayList<PendingRep>();
	private ServiceDetailsDao serviceDetailsDao = new ServiceDetailsDao();
	private PendingRep pendingRep = new PendingRep();
	String search = "";

	private List searchLst = new ArrayList();
	private String criteria = "";
	
	
	public ServiceDetails() {
		try {
			searchLst =AppUtil.getDropDownList("program_search", "PSRCH_NAME", "PSRCH_ID", "PSRCH_NAME", "where PSRCH_IDEN_FLAG <= 6", 0);
			pendingReps = serviceDetailsDao.getDisplayDetilas(search.trim(),criteria.trim());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String displayServiceDetails() {
		return "displayData";
	}

	public String getServiceDetilasEdit() {
		pendingRep = serviceDetailsDao.getDetail(pendingRep.getPrgWrId());
		System.out.println("abc");
		return "serviceDetails";
	}

	public String searchData() {
		pendingReps = serviceDetailsDao.getDisplayDetilas(search.trim(),criteria.trim());
		search = "";
		return "searchSuccess";
	}

	public String getServiceDetailsUpadted() {

		if (validate()) {
			serviceDetailsDao.updateServiceDetails(pendingRep);
			return "serviceDetailsUpdated";
		} else {
			return "validatefail";
		}
	}

	public String cancelEdit() {
		clearSessionServiceDetails();
		return "cancelEdit";
	}

	public String seviceEdit() {
		clearSession();
		return "seviceEdit";
	}
	
	private void clearSessionServiceDetails() {
		Map sessionMap = (Map) getBindingValue("#{sessionScope}");
		sessionMap.remove("pc_serviceDetails");
	}

	private void clearSession() {
		Map sessionMap = (Map) getBindingValue("#{sessionScope}");
		sessionMap.remove("pc_serviceDetails");
		sessionMap.remove("pc_PrgDetailRep");
	}
	public boolean validate() {
		boolean isValid = true;

		if (nullCheck(pendingRep.getRrNumber().trim())) {
			addMessage("ERRCD507E");
			isValid = false;
			return isValid;
		} else if (nullCheck(pendingRep.getDateofService())) {
			addMessage("ERRCDGK009E");
			isValid = false;
			return isValid;

		} else if (!isValidDate(pendingRep.getDateofService())) {
			addMessage("ERRCDGK010E");
			isValid = false;
			return isValid;
		}
		return isValid;
	}
	
	
	public List<PendingRep> getPendingReps() {
		return pendingReps;
	}

	public void setPendingReps(List<PendingRep> pendingReps) {
		this.pendingReps = pendingReps;
	}

	public ServiceDetailsDao getServiceDetailsDao() {
		return serviceDetailsDao;
	}

	public void setServiceDetailsDao(ServiceDetailsDao serviceDetailsDao) {
		this.serviceDetailsDao = serviceDetailsDao;
	}

	public PendingRep getPendingRep() {
		return pendingRep;
	}

	public void setPendingRep(PendingRep pendingRep) {
		this.pendingRep = pendingRep;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	

	public List getSearchLst() {
		return searchLst;
	}

	public void setSearchLst(List searchLst) {
		this.searchLst = searchLst;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
   
	
}
