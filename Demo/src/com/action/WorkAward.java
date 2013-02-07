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


import com.Dao.WorkAwardDao;
import com.Util.AppUtil;
import com.Util.PageCodeBase;
import com.bean.PendingRep;

@ManagedBean(name = "pc_workAward")
@SessionScoped
public class WorkAward extends PageCodeBase implements Serializable {
	private static final long serialVersionUID = 1L;

	public List<PendingRep> pendingReps = new ArrayList<PendingRep>();
	WorkAwardDao dao = new WorkAwardDao();
	PendingRep pendingRep = new PendingRep();
	String search = "";
  
	private List searchLst = new ArrayList();
	private String criteria = "";
	
	public WorkAward() {
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
		return "workAward";
	}
	
	public String update() {
		if(validate()){
			boolean isValid = dao.awardUpdate(pendingRep);
			if(isValid){		
				return "workAwardUpdateSuccess";
			}else{
				return "workAwardUpdateFailure";
			}
		}else{
			return "workAward";
		}
	}
	
	public String search() {
		pendingReps =  dao.getAllData(search.trim(),criteria.trim());
		clearSession();
		return "workAwardHome";
	}
	
	public String cancelEdit() {
		return "workAwardHome";
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
		if (nullCheck(pendingRep.getWrkAwrdDt())) {
			addMessage("ERRCDGK003E");
			isValid = false;
			return isValid;
		
		}else if (!isValidDate(pendingRep.getWrkAwrdDt())) {
			addMessage("ERRCDGK004E");
			isValid = false;
			pendingRep.setWrkAwrdDt("");
			return isValid;
		
		}
		
	return isValid;
	}
	
	
	
	public boolean isInteger( String input )  
	{  
	   try  
	   {  
	      Double.parseDouble(input);
	      return true;  
	   }  
	   catch( Exception e)  
	   {  
	      return false;  
	   }  
	} 
	public static boolean isValidDate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date testDate = null;
		try
		{
		testDate = sdf.parse(date);
		}
		catch (ParseException e)
		{
		//System.out.println("the date you provided is in an invalid date format.");
		return false;
		}
		if (!sdf.format(testDate).equals(date))
		{
		//System.out.println("The date that you provided is invalid");
		return false;
		}
		return true;
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
