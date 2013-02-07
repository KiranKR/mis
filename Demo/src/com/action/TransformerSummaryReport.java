package com.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.TransformerReportDao;
import com.Dao.TransformerSummaryReportDao;
import com.Util.EnumMonth;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.StackBean;
import com.bean.TransformerBean;
import com.bean.TransformerDetailsBean;
import com.bean.TransformerReportBean;

@ManagedBean(name="pc_tranformerSumReport")
@SessionScoped 
public class TransformerSummaryReport extends PageCodeBase implements Serializable { 

	
	String role =  UserUtil.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION);;
	String roleBelong = UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION);
	TransformerBean transformerBean = new TransformerBean();
	TransformerSummaryReportDao transformerSumReportDao = new TransformerSummaryReportDao();
	List<TransformerReportBean> lstTransformerReportBeans = new ArrayList<TransformerReportBean>();
	List<TransformerDetailsBean> lstDetailsBeans = new ArrayList<TransformerDetailsBean>();
	List<EnumMonth> enumMonths = new ArrayList<EnumMonth>();
	TransformerReportDao transRepDao =new TransformerReportDao();
	
	private String fromMonthID;
	private String fromYearID;
	private String toMonthID;
	private String toYearId;
	private String whrClause = "";
	
	String header = "";
	String znId = "";
	String crlId = "";
	String divId = "";
	String subDivId = "";
	String secId = "";
	StackBean stackBean = null;
	List<StackBean> stackBeans = new ArrayList<StackBean>();
	boolean backbtn = false;
	private int idenFlag=0;
	private boolean dispPara=true;
	
	
	public TransformerSummaryReport() {
		   enumMonths = Arrays.asList(EnumMonth.values());
		   dispPara =true;
	}
	
	
    public String getTransData(){
    	
    	if(validate()){
    		
    		
    		role = UserUtil.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION);
    		setHeaderString(role);
    		if (role.equals("1")) {
    			whrClause = " and z.ZONE_ID = " + roleBelong ;
    		} else if (role.equals("2")) {
    			whrClause = " and c.CRCL_ID = " + roleBelong ;
    		} else if (role.equals("3")) {
    			whrClause = " and d.DIV_ID = " + roleBelong ;
    		} else if (role.equals("4")) {
    			whrClause = " and sd.SUBDIV_ID = " + roleBelong ;
    		} else if (role.equals("5")) {
    			whrClause = " and s.SECTN_ID = " + roleBelong ;
    		}
    		stackBeans.add(new StackBean(role, roleBelong, "", stackBeans.size()+1));
    		lstTransformerReportBeans  = transformerSumReportDao.getTransDetails(fromYearID + getMnthId(fromMonthID), toYearId + getMnthId(toMonthID), role, whrClause,idenFlag);
    		dispPara =false;	
    	} 
    	
    	return "transformerSummary";
    }
	
	
    public String nextLevel(String id, String name){
    	backbtn =true;
    	if (role.equals("1")) {
			whrClause = " and z.ZONE_ID = " + id;
		} else if (role.equals("2")) {
			whrClause = " and c.CRCL_ID = " + id;
		} else if (role.equals("3")) {
			whrClause = " and d.DIV_ID = " + id;
		} else if (role.equals("4")) {
			whrClause = " and sd.SUBDIV_ID = " + id;
		} else if (role.equals("5")) {
			whrClause = " and s.SECTN_ID = " + id;
		}
		role = (Integer.parseInt(role) + 1) + "";
		setHeaderString(role);
		if (Integer.parseInt(role) <= 5) {
			stackBeans.add(new StackBean(role, id, name, stackBeans.size() + 1));
			lstTransformerReportBeans  = transformerSumReportDao.getTransDetails(fromYearID + getMnthId(fromMonthID), toYearId + getMnthId(toMonthID), role, whrClause,idenFlag);
		}
		return "transformerSummary";
    	
    }
	
    public String back(String lvl, String id, String slno){
    	StackBean bean =new StackBean();
		if (Integer.parseInt(slno) > 1) {
			bean = stackBeans.get(Integer.parseInt(slno) - 2);
			stackBeans = stackBeans.subList(0, Integer.parseInt(slno) - 1);
			if (bean.getLvl().equals("1")) {
				whrClause = " and z.ZONE_ID = " + id;
			} else if (bean.getLvl().equals("2")) {
				whrClause = " and c.CRCL_ID = " + id;
			} else if (bean.getLvl().equals("3")) {
				whrClause = " and d.DIV_ID = " + id;
			} else if (bean.getLvl().equals("4")) {
				whrClause = " and sd.SUBDIV_ID = " + id;
			} else if (bean.getLvl().equals("5")) {
				whrClause = " and s.SECTN_ID = " + id;
			}
			role = bean.getLvl();
			setHeaderString(role);
			lstTransformerReportBeans  = transformerSumReportDao.getTransDetails(fromYearID + getMnthId(fromMonthID), toYearId + getMnthId(toMonthID), role, whrClause,idenFlag);
		    if(bean.getLvl().equals("1")){
		    	backbtn =false;
		    } 
		}
		return "transformerSummary";
    }
	
    public boolean validate(){
    	boolean isValid = true;
    	    
    		
    	    
    	 	if(nullCheck(fromMonthID)) {
    		addMessage("ERRCDALLSUM002E");
    		isValid = false;
    	 	return isValid;
    	 }else if (nullCheck(fromYearID)) {
    			addMessage("ERRCDALLSUM001E");
    			isValid = false;
    			return isValid;
    		}else if(nullCheck(toMonthID)) {
    			addMessage("ERRCDALLSUM004E");
    			isValid = false;
    			return isValid;
    		}else if(nullCheck(toYearId)) {
    			addMessage("ERRCDALLSUM003E");
    			isValid = false;
    			return isValid;
    		}else if(fromDateLessToDate(fromYearID,fromMonthID,toYearId,toMonthID)) {
    			addMessage("ERRCDALLSUM005E");
    			isValid = false;
    			return isValid;
    		} 
    		return isValid;
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


	public boolean isBackbtn() {
		return backbtn;
	}


	public void setBackbtn(boolean backbtn) {
		this.backbtn = backbtn;
	}


	public TransformerBean getTransformerBean() {
		return transformerBean;
	}


	public void setTransformerBean(TransformerBean transformerBean) {
		this.transformerBean = transformerBean;
	}
	public TransformerSummaryReportDao getTransformerSumReportDao() {
		return transformerSumReportDao;
	}


	public void setTransformerSumReportDao(
			TransformerSummaryReportDao transformerSumReportDao) {
		this.transformerSumReportDao = transformerSumReportDao;
	}


	public List<TransformerReportBean> getLstTransformerReportBeans() {
		return lstTransformerReportBeans;
	}


	public void setLstTransformerReportBeans(
			List<TransformerReportBean> lstTransformerReportBeans) {
		this.lstTransformerReportBeans = lstTransformerReportBeans;
	}


	public List<TransformerDetailsBean> getLstDetailsBeans() {
		return lstDetailsBeans;
	}


	public void setLstDetailsBeans(List<TransformerDetailsBean> lstDetailsBeans) {
		this.lstDetailsBeans = lstDetailsBeans;
	}


	public List<StackBean> getStackBeans() {
		return stackBeans;
	}


	public void setStackBeans(List<StackBean> stackBeans) {
		this.stackBeans = stackBeans;
	}
	
    public List<EnumMonth> getEnumMonths() {
		return enumMonths;
	}


	public void setEnumMonths(List<EnumMonth> enumMonths) {
		this.enumMonths = enumMonths;
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


	public TransformerReportDao getTransRepDao() {
		return transRepDao;
	}


	public void setTransRepDao(TransformerReportDao transRepDao) {
		this.transRepDao = transRepDao;
	}
	
	
    
	public String getFromMonthID() {
		return fromMonthID;
	}


	public void setFromMonthID(String fromMonthID) {
		this.fromMonthID = fromMonthID;
	}


	public String getFromYearID() {
		return fromYearID;
	}


	public void setFromYearID(String fromYearID) {
		this.fromYearID = fromYearID;
	}


	public String getToMonthID() {
		return toMonthID;
	}


	public void setToMonthID(String toMonthID) {
		this.toMonthID = toMonthID;
	}


	public String getToYearId() {
		return toYearId;
	}


	public void setToYearId(String toYearId) {
		this.toYearId = toYearId;
	}


	public String getWhrClause() {
		return whrClause;
	}


	public void setWhrClause(String whrClause) {
		this.whrClause = whrClause;
	}
	
	public int getIdenFlag() {
		return idenFlag;
	}


	public void setIdenFlag(int idenFlag) {
		this.idenFlag = idenFlag;
	}
	
	public boolean isDispPara() {
		return dispPara;
	}


	public void setDispPara(boolean dispPara) {
		this.dispPara = dispPara;
	}


	private void setHeaderString(String pRole) {
		if (pRole.equals("1")) {
			header = "Zone";
		} else if (pRole.equals("2")) {
			header = "Circle";
		} else if (pRole.equals("3")) {
			header = "Division";
		} else if (pRole.equals("4")) {
			header = "SubDivision";
		} else if (pRole.equals("5")) {
			header = "Section";
		}
	}
	
	public static String getMnthId(String month) {
		if (month.equals("JANUARY")) {
			return "1";
		} else if (month.equals("FEBRUARY")) {
			return "2";
		} else if (month.equals("MARCH")) {
			return "3";
		} else if (month.equals("APRIL")) {
			return "4";
		} else if (month.equals("MAY")) {
			return "5";
		} else if (month.equals("JUNE")) {
			return "6";
		} else if (month.equals("JULY")) {
			return "7";
		} else if (month.equals("AUGUST")) {
			return "8";
		} else if (month.equals("SEPTEMBER")) {
			return "9";
		} else if (month.equals("OCTOBER")) {
			return "10";
		} else if (month.equals("NOVEMBER")) {
			return "11";
		} else if (month.equals("DECEMBER")) {
			return "12";
		}
		return "";
	}

	
}
