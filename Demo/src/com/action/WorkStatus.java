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

import com.Dao.WorkStatusDao;
import com.Util.AppUtil;
import com.Util.PageCodeBase;
import com.bean.DepositeBean;
import com.bean.PendingRep;

@ManagedBean(name = "pc_workStatus")
@SessionScoped
public class WorkStatus extends PageCodeBase implements Serializable {
	private static final long serialVersionUID = 1L;

	public List<PendingRep> pendingReps = new ArrayList<PendingRep>();
	WorkStatusDao dao = new WorkStatusDao();
	PendingRep pendingRep = new PendingRep();
	String search = "";

	private List searchLst = new ArrayList();
	private String criteria = "";
	
	public WorkStatus() {
		try {
			searchLst =AppUtil.getDropDownList("program_search", "PSRCH_NAME", "PSRCH_ID", "PSRCH_NAME", "where PSRCH_IDEN_FLAG <= 3", 0);
			pendingReps =  dao.getAllData(search.trim(),criteria.trim());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getDetails() {
		pendingRep = dao.getDetail(pendingRep.getPrgWrId());
		return "workStatus";
	}
	
	public String update() {
		if(validate()){
			boolean isValid = dao.statusUpdate(pendingRep);
			if(isValid){		
				return "workStatusUpdateSuccess";
			}else{
				return "workStatusUpdateFailure";
			}
		}else{
			return "workStatus";
		}
	}
	
	public String search() {
		pendingReps =  dao.getAllData(search.trim(),criteria.trim());
		clearSession();
		return "workStatusHome";
	}
	
	public String cancelEdit() {
		return "workStatusHome";
	}
	
	private void clearSession() {
		Map sessionMap = (Map) getBindingValue("#{sessionScope}");
		sessionMap.remove("pc_PrgDetailRep");
	}
	
	public List<PendingRep> getPendingReps() {
		return pendingReps;
	}

	public void setPendingReps(List<PendingRep> pendingReps) {
		this.pendingReps = pendingReps;
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
	public boolean validate() {
		boolean isValid = true;
		if (nullCheck(pendingRep.getWrkUndrProgress())) {
			addMessage("ERRCD511E");
			isValid = false;
			return isValid;
		
		}else if (!isValidDate(pendingRep.getWrkUndrProgress())) {
			addMessage("ERRCD512E");
			isValid = false;
			return isValid;

		} else if (!pendingRep.getWrkCompleted().equals("")) {
			if (!isValidDate(pendingRep.getWrkCompleted())) {
				addMessage("ERRCD513E");
				isValid = false;
				return isValid;

			}
		}
	return isValid;
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
