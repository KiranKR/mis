package com.ample.mis.gk.registration.action;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.Util.AppUtil;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.ample.mis.gk.registration.bean.PendingRep;
import com.ample.mis.gk.registration.delegate.ProgramDelegate;



@ManagedBean(name="pc_Program")
@SessionScoped
public class Program extends PageCodeBase implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5299381036014928599L;
	private PendingRep pendingRep = new PendingRep();
	HttpSession session = null;
	
	@ManagedProperty(value = "#{delegate}")
	ProgramDelegate delegate;

	private ProgramDelegate programDelegate = new ProgramDelegate();
	
	private List<PendingRep> pnRpLst = new ArrayList<PendingRep>();
	private List talkLst = new ArrayList();
	private List secLst = new ArrayList();
	private List schemLst = new ArrayList();
	private List searchLst = new ArrayList();
	private String search = "";
	private String criteria = "";
	private String stage = "";
	
	public Program() {
		super();
		try {
			pnRpLst = programDelegate.search(search.trim(),criteria.trim());
			talkLst = AppUtil.getDropDownList("taluk", "TALUK_NAME", "TALUK_ID", "TALUK_NAME", "", 0);
			session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			/*secLst = loginDao.getDropDownList("section", "Sec_Name", "Sec_ID", "Sec_Name", "where Sub_Div_ID = "+session.getAttribute("SubDivId"), 0);*/
			/*Get the subDivId from Session*/
			
			secLst = AppUtil.getDropDownList("section", "SECTN_NAME", "SECTN_ID", "SECTN_NAME", "where SECTN_SUBDIV_ID = "+UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+" ", 0);
			schemLst = AppUtil.getDropDownList("caste_category", "CSTCTG_AKA", "CSTCTG_ID", "CSTCTG_AKA", "", 0);
			searchLst =  AppUtil.getDropDownList("program_search", "PSRCH_NAME", "PSRCH_ID", "PSRCH_NAME", "where PSRCH_IDEN_FLAG = 0", 0);
			search = "";
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String addProgramPg(){
		clearSession();
		return "addProgramPg";
	}
	
	public String editProgramPg(){
		return "editProgramPg";
	}
	
	public String cancelProgram(){
		clearSession();
		return "cancel";
	}
	
	public String OkProgram(){
		clearSessionPrgDetailsRep();
		return "okProgram";
	}
	
	public String searchProgram(){
		pnRpLst = programDelegate.search(search.trim(),criteria.trim());
		search = "";
		return "srchSuccess";
	}
	
	public String addNewProgram(){
		if(validate()){
			if(nullCheck(pendingRep.getWoIssDate()) && nullCheck(pendingRep.getWoIssNum()) && nullCheck(pendingRep.getDateofService()) && nullCheck(pendingRep.getRrNumber())){
				stage = "1";
			}else if(nullCheck(pendingRep.getDateofService()) && nullCheck(pendingRep.getRrNumber())){
				stage = "4";
			}else if(nullCheck(pendingRep.getRrNumber())){
				stage = "6";
			}else if(!nullCheck(pendingRep.getRrNumber())){
				stage = "7";
			}
			boolean isValid = programDelegate.addProgram(pendingRep,stage);
			if(isValid){
				return "addSuccess";
			}
		}
		return "addFailure";
	}
	
	public String updateProgram(){
		if(validate()){
			int updated = programDelegate.updateProgram(pendingRep);
			String updat = String.valueOf(updated);
			if(updat.equals("1")){
				return "updateSuccess";
			}
		}
		return "updateFaliure";
	}

	private void clearSession() {
		Map sessionMap = (Map) getBindingValue("#{sessionScope}");
		sessionMap.remove("pc_Program");
	}
	
	private void clearSessionPrgDetailsRep() {
		Map sessionMap = (Map) getBindingValue("#{sessionScope}");
		sessionMap.remove("pc_Program");
		sessionMap.remove("pc_PrgDetailRep");
	}
	public boolean validate() {
		boolean isValid = true;

		if (nullCheck(pendingRep.getSectionId())) {
			addMessage("ERRCD502E");
			isValid = false;
			return isValid;
		}else if (nullCheck(pendingRep.getDtOfRegtn())) {
			addMessage("ERRCD514E");
			isValid = false;
			return isValid;
		
		}else if (!isValidDate(pendingRep.getDtOfRegtn())) {
			addMessage("ERRCD517E");
			isValid = false;
			pendingRep.setDtOfRegtn("");
			return isValid;
		
		}else if (nullCheck(pendingRep.getRegstrNo())) {
			addMessage("ERRCD505E");
			isValid = false;
			return isValid;
		
		}else if (nullCheck(pendingRep.getBeneficiaryName())) {
			addMessage("ERRCD505E");
			isValid = false;
			return isValid;
		
		}
		return isValid;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	public ProgramDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(ProgramDelegate delegate) {
		this.delegate = delegate;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}


	public List<PendingRep> getPnRpLst() {
		return pnRpLst;
	}

	public void setPnRpLst(List<PendingRep> pnRpLst) {
		this.pnRpLst = pnRpLst;
	}

	public List getSchemLst() {
		return schemLst;
	}

	public void setSchemLst(List schemLst) {
		this.schemLst = schemLst;
	}

	public List getSecLst() {
		return secLst;
	}

	public void setSecLst(List secLst) {
		this.secLst = secLst;
	}

	public List getTalkLst() {
		return talkLst;
	}

	public void setTalkLst(List talkLst) {
		this.talkLst = talkLst;
	}

	public PendingRep getPendingRep() {
		return pendingRep;
	}

	public void setPendingRep(PendingRep pendingRep) {
		this.pendingRep = pendingRep;
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
