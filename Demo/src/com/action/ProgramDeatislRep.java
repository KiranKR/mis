package com.action;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.ProgramDetailsRepDao;
import com.Util.AppUtil;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.PendingRep;
import com.bean.ProgramDetailsRepBean;

@ManagedBean(name = "pc_PrgDetailRep")
@SessionScoped
public class ProgramDeatislRep extends PageCodeBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProgramDetailsRepDao detailsRepDao = new ProgramDetailsRepDao();
	private ProgramDetailsRepBean detailsRepBean = new ProgramDetailsRepBean();
	private PendingRep pendingRep = new PendingRep();
	private List prgDetailsLst = new ArrayList();
	private List secLst = new ArrayList();

	public ProgramDeatislRep() {
		super();
		try {
			String concatWhere = "";
			final String queryWhere = "JOIN sub_division ON section.SECTN_SUBDIV_ID = sub_division.SUBDIV_ID "
					+ "JOIN division ON sub_division.SUBDIV_DIV_ID = division.DIV_ID "
					+ "JOIN circle ON division.DIV_CIRCLE_ID = circle.CRCL_ID "
					+ "JOIN zone ON circle.CRCL_ZONE_ID = zone.ZONE_ID where ";
			final int idenFlag = Integer.valueOf(UserUtil.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION));
			if (idenFlag == 1) {
				concatWhere = "zone.ZONE_ID = "	+ UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+ " ";
			} else if (idenFlag == 2) {
				concatWhere = "circle.CRCL_ID = "+ UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+ " ";
			} else if (idenFlag == 3) {
				concatWhere = "division.DIV_ID = "+ UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+ " ";
			} else if (idenFlag == 4) {
				concatWhere = "sub_division.SUBDIV_ID = "+ UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+ " ";
			}
			final String whrClause = queryWhere.concat(concatWhere);

			secLst = AppUtil.getDropDownList("section", "SECTN_NAME","SECTN_ID", "SECTN_NAME", whrClause, 0);
			if(UserUtil.getSessionObj(UserBeanConstants.PRG_DETAIL_REP_IN_SESSION) != null){
				detailsRepBean = (ProgramDetailsRepBean) UserUtil.getSessionObj(UserBeanConstants.PRG_DETAIL_REP_IN_SESSION);
				prgDetailsLst = detailsRepDao.prgDetails(detailsRepBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String fetchPrgDetailsRep() {
		if (validate()) {
			UserUtil.setSessionObj(UserBeanConstants.PRG_DETAIL_REP_IN_SESSION, detailsRepBean);
			prgDetailsLst = detailsRepDao.prgDetails(detailsRepBean);
			return "prgDetailsLst";
		}
		return "prgDetalFailure";
	}

	public String dispPrgDetailPg() {
		return "prgDetailsDisp";
	}

	public String backButn() {
		return "back";
	}
	
	/*public void setFromToDateNull(){
		detailsRepBean.setFromDate(null);
		detailsRepBean.setGrpByDateNme("0");
		detailsRepBean.setToDate(null);
		detailsRepBean.setSectionId("1");
	}*/
	
	
	public boolean validate() {
		boolean isValid = true;

		if (nullCheck(detailsRepBean.getFromDate())) {
			addMessage("ERRCD532E");
			isValid = false;
			return isValid;
		}else if (!isValidDate(detailsRepBean.getFromDate())) {
			addMessage("ERRCD533E");
			isValid = false;
			detailsRepBean.setFromDate(null);
			return isValid;
		} else if (nullCheck(detailsRepBean.getToDate())) {
			addMessage("ERRCD534E");
			isValid = false;
			return isValid;
		}else if (!isValidDate(detailsRepBean.getToDate())) {
			addMessage("ERRCD535E");
			isValid = false;
			detailsRepBean.setToDate(null);
			return isValid;
		}else if (stringToDate(detailsRepBean.getFromDate()).after(stringToDate(detailsRepBean.getToDate()))) {
			addMessage("ERRCD536E");
			isValid = false;
			detailsRepBean.setFromDate(null);
			detailsRepBean.setToDate(null);
			return isValid;
		}else if (detailsRepBean.getSectionId().equals("0")) {
			addMessage("ERRCD531E");
			isValid = false;
			return isValid;
		} else if (detailsRepBean.getGrpByDateNme().equals("0")) {
			addMessage("ERRCD530E");
			isValid = false;
			return isValid;
		} 
		return isValid;
	}

	
	

	public static Date stringToDate(String pDate) {
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date = (Date) formatter.parse(pDate);
			
			return date;
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public PendingRep getPendingRep() {
		return pendingRep;
	}

	public void setPendingRep(PendingRep pendingRep) {
		this.pendingRep = pendingRep;
	}

	public List getPrgDetailsLst() {
		return prgDetailsLst;
	}

	public void setPrgDetailsLst(List prgDetailsLst) {
		this.prgDetailsLst = prgDetailsLst;
	}

	public List getSecLst() {
		return secLst;
	}

	public void setSecLst(List secLst) {
		this.secLst = secLst;
	}

	public ProgramDetailsRepBean getDetailsRepBean() {
		return detailsRepBean;
	}

	public void setDetailsRepBean(ProgramDetailsRepBean detailsRepBean) {
		this.detailsRepBean = detailsRepBean;
	}

}
