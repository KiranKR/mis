package com.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;

import com.Dao.MNRRepDao;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;

import com.bean.MNRRepBean;

import com.bean.StackBean;

@ManagedBean(name="pc_MnrRep")
@SessionScoped
public class MNRReprot extends PageCodeBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private MNRRepBean mnrRepBean = new MNRRepBean();
	private List<MNRRepBean> mnrRepList = new ArrayList<MNRRepBean>();
	private List<MNRRepBean> dispMnrList = new ArrayList<MNRRepBean>();
	private MNRRepDao mnrRepDao = new MNRRepDao();
	
	MNRRepBean bean = null;
	String role = UserUtil.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION);
	String roleBelong = UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION);
	String header = "";
	String znId = "";
	String crlId = "";
	String divId = "";
	String subDivId = "";
	String secId = "";
	StackBean stackBean = null;
	List<StackBean> stackBeans = new ArrayList<StackBean>();
	boolean backbtn = false;
    
	int graTotOPBbet1to3=0;
	int graTotMNRInsbet1to3 =0;
	int graTotMNRReplbet1to3 = 0;
	int graTotCLBbet1to3 =0;
	
	int graTotOPBbet3to6=0;
	int graTotMNRInsbet3to6=0;
	int graTotMNRReplbet3to6=0;
	int graTotCLBbet3to6=0;
	
	int graTotOPBGrt6=0;
	int graTotMNRInsGrt6=0;
	int graTotMNRReplGrt6=0;
	int graTotCLBGrt6=0;
	
	
	
	public String MnrReportList() {
		
		stackBeans.clear();
		if(validate())
		{
		if (role.equals("1")) {
			header = "Zone";
			znId = roleBelong;
		}
		else if (role.equals("2")) {
			header = "Circle";
			crlId = roleBelong;
		}
		else if (role.equals("3")) {
			header = "Division";
			divId = roleBelong;
		}
		else if (role.equals("4")) {
			header = "SubDivision";
			subDivId = roleBelong;
		}
		else if (role.equals("5")) {
			header = "Section";
			secId = roleBelong;
		}
		
		dispMnrList = mnrRepDao.viewData(role, roleBelong,mnrRepBean.getYearId());
		grandTotal(dispMnrList);
		backbtn=false;
       
		return "mnrReport";
		}
		else {
			return "mnrReport";	
		}
	}
	
	
public String getSchemeWiseRep(String id, String lvl,String name) {
		
	    if(lvl.equals("6") && stackBeans.size() == 1){
	    	
			backbtn = false;
			}
	    else{
			backbtn = true;
			}
		
		if (lvl.equals("4")) {
			header = "Section";
			stackBeans.add(new StackBean("4",id,name,stackBeans.size()+1));
			dispMnrList = mnrRepDao.secWise(id,lvl);
			grandTotal(dispMnrList);

		} else if (lvl.equals("3")) {
			header = "SubDivision";
			stackBeans.add(new StackBean("3",id,name,stackBeans.size()+1));
			dispMnrList = mnrRepDao.subDivWise(id,lvl);
			grandTotal(dispMnrList);

		} else if (lvl.equals("2")) {
			header = "Division";
			stackBeans.add(new StackBean("2", id,name,stackBeans.size()+1));
			dispMnrList=mnrRepDao.divWise(id,lvl);
			grandTotal(dispMnrList);
			
		} else if (lvl.equals("1")) {
			header = "Circle";
			stackBeans.add(new StackBean("1", id,name,stackBeans.size()+1));
			dispMnrList=mnrRepDao.circleWise(id,lvl);
			grandTotal(dispMnrList);
		}
		
		
		
		return "mnrReport";
	}
	
public String back(String id,String lvl,String name,String slno) {
		
	 graTotOPBbet1to3=0;
	 graTotMNRInsbet1to3 =0;
	 graTotMNRReplbet1to3 = 0;
	 graTotCLBbet1to3 =0;
	
	 graTotOPBbet3to6=0;
	 graTotMNRInsbet3to6=0;
	 graTotMNRReplbet3to6=0;
	 graTotCLBbet3to6=0;
	
	 graTotOPBGrt6=0;
	 graTotMNRInsGrt6=0;
	 graTotMNRReplGrt6=0;
	 graTotCLBGrt6=0;
	 
	  if(slno.equals("1")){
	    	
			backbtn = false;
			}
	    else{
			backbtn = true;
			}
	
	 
	     if (lvl.equals("5")) {
			header = "Section";
			dispMnrList = mnrRepDao.secWise(id,lvl);
			grandTotal(dispMnrList);

		} else if (lvl.equals("4")) {
			header = "SubDivision";
			dispMnrList = mnrRepDao.subDivWise(id,lvl);
			grandTotal(dispMnrList);

		} else if (lvl.equals("3")) {
			header = "Division";
			dispMnrList=mnrRepDao.divWise(id,lvl);
			grandTotal(dispMnrList);
			
		} else if (lvl.equals("2")) {
			header = "Circle";
			dispMnrList=mnrRepDao.circleWise(id,lvl);
			grandTotal(dispMnrList);
		}
		else if (lvl.equals("1")) {
			header = "Zone";
			dispMnrList=mnrRepDao.zoneWise(id,lvl);
			grandTotal(dispMnrList);

		}
	stackBeans=stackBeans.subList(0,Integer.parseInt(slno)-1);
	 return "mnrReport";
 }

 public void grandTotal(List<MNRRepBean> beans){
	 
	     graTotOPBbet1to3=0;
		 graTotMNRInsbet1to3 =0;
		 graTotMNRReplbet1to3 = 0;
		 graTotCLBbet1to3 =0;
		
		 graTotOPBbet3to6=0;
		 graTotMNRInsbet3to6=0;
		 graTotMNRReplbet3to6=0;
		 graTotCLBbet3to6=0;
		
		 graTotOPBGrt6=0;
		 graTotMNRInsGrt6=0;
		 graTotMNRReplGrt6=0;
		 graTotCLBGrt6=0;
		 
	 for (MNRRepBean mnrRepBean : beans) {
		 graTotOPBbet1to3+=mnrRepBean.getTotOPBbet1to3();
		 graTotMNRInsbet1to3+=mnrRepBean.getTotMNRInsbet1to3();
		 graTotMNRReplbet1to3+=mnrRepBean.getTotMNRReplbet1to3();
		 graTotCLBbet1to3+=mnrRepBean.getTotCLBbet1to3();
		
		 graTotOPBbet3to6+=mnrRepBean.getTotOPBbet3to6();
		 graTotMNRInsbet3to6+=mnrRepBean.getTotMNRInsbet3to6();
		 graTotMNRReplbet3to6+=mnrRepBean.getTotMNRReplbet3to6();
		 graTotCLBbet3to6+=mnrRepBean.getTotCLBbet3to6();
		
		 graTotOPBGrt6+=mnrRepBean.getTotOPBGrt6();
		 graTotMNRInsGrt6+=mnrRepBean.getTotMNRInsGrt6();
		 graTotMNRReplGrt6+=mnrRepBean.getTotMNRReplGrt6();
		 graTotCLBGrt6+=mnrRepBean.getTotCLBGrt6();
		 
		
	}
	 
 }
	
	
	public boolean validate() {
		boolean isValid = true;

		if (nullCheck(mnrRepBean.getYearId())) {
			addMessage("ERRCD539E");
			isValid = false;
			

		}
		return isValid;
	}
	public void setMnrRepBean(MNRRepBean mnrRepBean) {
		this.mnrRepBean = mnrRepBean;
	}

	public List<MNRRepBean> getMnrRepList() {
		return mnrRepList;
	}

	public void setMnrRepList(List<MNRRepBean> mnrRepList) {
		this.mnrRepList = mnrRepList;
	}

	public List<MNRRepBean> getDispMnrList() {
		return dispMnrList;
	}

	public void setDispMnrList(List<MNRRepBean> dispMnrList) {
		this.dispMnrList = dispMnrList;
	}

	public MNRRepDao getMnrRepDao() {
		return mnrRepDao;
	}

	public void setMnrRepDao(MNRRepDao mnrRepDao) {
		this.mnrRepDao = mnrRepDao;
	}

	public MNRRepBean getBean() {
		return bean;
	}

	public void setBean(MNRRepBean bean) {
		this.bean = bean;
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

	public String getZnId() {
		return znId;
	}

	public int getGraTotOPBbet1to3() {
		return graTotOPBbet1to3;
	}

	public void setGraTotOPBbet1to3(int graTotOPBbet1to3) {
		this.graTotOPBbet1to3 = graTotOPBbet1to3;
	}

	public int getGraTotMNRInsbet1to3() {
		return graTotMNRInsbet1to3;
	}

	public void setGraTotMNRInsbet1to3(int graTotMNRInsbet1to3) {
		this.graTotMNRInsbet1to3 = graTotMNRInsbet1to3;
	}

	public int getGraTotMNRReplbet1to3() {
		return graTotMNRReplbet1to3;
	}

	public void setGraTotMNRReplbet1to3(int graTotMNRReplbet1to3) {
		this.graTotMNRReplbet1to3 = graTotMNRReplbet1to3;
	}

	public int getGraTotCLBbet1to3() {
		return graTotCLBbet1to3;
	}

	public void setGraTotCLBbet1to3(int graTotCLBbet1to3) {
		this.graTotCLBbet1to3 = graTotCLBbet1to3;
	}

	public int getGraTotOPBbet3to6() {
		return graTotOPBbet3to6;
	}

	public void setGraTotOPBbet3to6(int graTotOPBbet3to6) {
		this.graTotOPBbet3to6 = graTotOPBbet3to6;
	}

	public int getGraTotMNRInsbet3to6() {
		return graTotMNRInsbet3to6;
	}

	public void setGraTotMNRInsbet3to6(int graTotMNRInsbet3to6) {
		this.graTotMNRInsbet3to6 = graTotMNRInsbet3to6;
	}

	public int getGraTotMNRReplbet3to6() {
		return graTotMNRReplbet3to6;
	}

	public void setGraTotMNRReplbet3to6(int graTotMNRReplbet3to6) {
		this.graTotMNRReplbet3to6 = graTotMNRReplbet3to6;
	}

	public int getGraTotCLBbet3to6() {
		return graTotCLBbet3to6;
	}

	public void setGraTotCLBbet3to6(int graTotCLBbet3to6) {
		this.graTotCLBbet3to6 = graTotCLBbet3to6;
	}

	public int getGraTotOPBGrt6() {
		return graTotOPBGrt6;
	}

	public void setGraTotOPBGrt6(int graTotOPBGrt6) {
		this.graTotOPBGrt6 = graTotOPBGrt6;
	}

	public int getGraTotMNRInsGrt6() {
		return graTotMNRInsGrt6;
	}

	public void setGraTotMNRInsGrt6(int graTotMNRInsGrt6) {
		this.graTotMNRInsGrt6 = graTotMNRInsGrt6;
	}

	public int getGraTotMNRReplGrt6() {
		return graTotMNRReplGrt6;
	}

	public void setGraTotMNRReplGrt6(int graTotMNRReplGrt6) {
		this.graTotMNRReplGrt6 = graTotMNRReplGrt6;
	}

	public int getGraTotCLBGrt6() {
		return graTotCLBGrt6;
	}

	public void setGraTotCLBGrt6(int graTotCLBGrt6) {
		this.graTotCLBGrt6 = graTotCLBGrt6;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public MNRRepBean getMnrRepBean() {
		return mnrRepBean;
	}
    
   

	
	

	/*
	 * public String getmnrReport(){ if(validate()){
	 * UserUtil.setSessionObj(UserBeanConstants.BMAZ_MNR_REP_IN_SESSION,
	 * mnrBean); lstMnr = mnrRepDao.getMnrRep(mnrBean);
	 * 
	 * return "mnrRep"; }else{ return "mnrRepFailure"; } }
	 */

}
