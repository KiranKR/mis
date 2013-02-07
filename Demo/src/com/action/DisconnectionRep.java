package com.action;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.Dao.DisconnectionRepDao;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.DisconnectionBean;

@ManagedBean(name = "pc_disconnectionRep")
@RequestScoped
public class DisconnectionRep extends PageCodeBase implements Serializable {
	private static final long serialVersionUID = 1L;
	
	String role = "";
	String roleBelong = "";
	private List secLst = new ArrayList();
	String secId = "";
	String yearId = "";
	double ttlOB = 0.0;
	double ttlAmtInv = 0.0;
	double ttlAmtRel = 0.0;
	double ttlCB = 0.0;
	
	
	
	DisconnectionRepDao dao = new DisconnectionRepDao();
	List<DisconnectionBean> disconnectionBeans = new ArrayList<DisconnectionBean>();

	public DisconnectionRep() {
		role = UserUtil.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION);
		roleBelong = UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION);
		String whrClause = "";
		if (role.equals("1")) {
			whrClause = "where zone.ZONE_ID = " + roleBelong;
		} else if (role.equals("2")) {
			whrClause = "where circle.CRCL_ID = " + roleBelong;
		} else if (role.equals("3")) {
			whrClause = "where division.DIV_ID = " + roleBelong;
		} else if (role.equals("4")) {
			whrClause = "where sub_division.SUBDIV_ID = " + roleBelong;
		} else if (role.equals("5")) {
			whrClause = "where section.SECTN_ID = " + roleBelong;
		}
		try {
			secLst = dao.getDropDownList("section", "SECTN_NAME", "SECTN_ID", "SECTN_NAME", whrClause, 0);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getDisReport() {
		if(validate()){
		disconnectionBeans = dao.getRep(secId, yearId);
		if(disconnectionBeans.size() != 0){
			DisconnectionBean bean = disconnectionBeans.get(disconnectionBeans.size() - 1);
			ttlOB = bean.getTtlOB();
			ttlAmtInv = bean.getTtlAmtInv();
			ttlAmtRel = bean.getTtlAmtRel();
			ttlCB = bean.getTtlCB();
		}
		return "disconnectionReport";
		}else
		{
			return "disconnectionReportFailure";
		}
	}
	
	
	public String  getDisDetReport(List<DisconnectionBean> lstDisconnectionReps){
		disconnectionBeans =lstDisconnectionReps;
		return "disconnectionReport";
	}
	
	 public boolean validate()
	  {
	  	boolean isValid = true;

			if (nullCheck(secId)) {
				addMessage("ERRCD537E");
				isValid = false;
				return isValid;
			}  else if (nullCheck(yearId)) {
				addMessage("ERRCD539E");
				isValid = false;
				return isValid;
			
			} 
			return isValid;
	  }


		
	public List getSecLst() {
		return secLst;
	}

	public void setSecLst(List secLst) {
		this.secLst = secLst;
	}

	public String getSecId() {
		return secId;
	}

	public void setSecId(String secId) {
		this.secId = secId;
	}

	public String getYearId() {
		return yearId;
	}

	public void setYearId(String yearId) {
		this.yearId = yearId;
	}

	public List<DisconnectionBean> getDisconnectionBeans() {
		return disconnectionBeans;
	}

	public void setDisconnectionBeans(List<DisconnectionBean> disconnectionBeans) {
		this.disconnectionBeans = disconnectionBeans;
	}

	public double getTtlOB() {
		return ttlOB;
	}

	public void setTtlOB(double ttlOB) {
		this.ttlOB = ttlOB;
	}

	public double getTtlAmtInv() {
		return ttlAmtInv;
	}

	public void setTtlAmtInv(double ttlAmtInv) {
		this.ttlAmtInv = ttlAmtInv;
	}

	public double getTtlAmtRel() {
		return ttlAmtRel;
	}

	public void setTtlAmtRel(double ttlAmtRel) {
		this.ttlAmtRel = ttlAmtRel;
	}

	public double getTtlCB() {
		return ttlCB;
	}

	public void setTtlCB(double ttlCB) {
		this.ttlCB = ttlCB;
	}
	
}
