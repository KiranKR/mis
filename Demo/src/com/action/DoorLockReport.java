package com.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import antlr.collections.Stack;
import bsh.util.Util;

import com.Dao.DoorLockReportDao;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.DoorLockReportMainBean;
import com.bean.StackBean;

@ManagedBean(name = "pc_doorLockReportss")
@SessionScoped
public class DoorLockReport extends PageCodeBase {

	DoorLockReportDao doorLockReportDao = new DoorLockReportDao();
	DoorLockReportMainBean doorLockReportMainBean = new DoorLockReportMainBean();
	List<DoorLockReportMainBean> doorLockReportMainBeansList = new ArrayList<DoorLockReportMainBean>();
	String role = UserUtil.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION);
	String roleBelong = UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION);

	String yearId;
	String header = "";
	String znId = "";
	String crlId = "";
	String divId = "";
	String subDivId = "";
	String secId = "";
	StackBean stackBean = null;
	List<StackBean> stackBeans = new ArrayList<StackBean>();
	boolean backbtn = false;
	
	
	
	int grnadTotalNoInstance3to6 = 0;
	int grandTotalInstanceGt6 = 0;
	int grandTotalNotice3to6 = 0;
	int grandtotalNoticeGt6 = 0;
	int grandTotalShifted3to6 = 0;
	int grandTotalShiftedGt6 = 0;
	
	

	public DoorLockReport() {
		

	}

	public String doorLockReportList() 
	{
		stackBeans.clear();
		if(validate())
		{
		
		
		
		    if (role.equals("1")) {
				header = "Zone";
				znId = roleBelong;
			 
				
				
			} else if (role.equals("2")) {
				header = "Circle";
				crlId = roleBelong;
		     
				
			} else if (role.equals("3")) {
				header = "Division";
				divId = roleBelong;
			 
				
			} else if (role.equals("4")) {
				header = "SubDivision";
				subDivId = roleBelong;
		      
			}
			else if(role.equals("5"))
			{
				header = "Section";
				secId = roleBelong;
				
				
			}
			
		
		
	
					
		doorLockReportMainBeansList = doorLockReportDao.viewData(role,roleBelong, doorLockReportMainBean.getYearId());
		grandTotal(doorLockReportMainBeansList);

		return "doorLockSumryReport";
		}
		else 
		{
			return "doorLockSumryReport";
		}

	}
	
public String getSchemeWiseRep(String id,String name, String lvl) {
	

		
		if(lvl.equals("6") && stackBeans.size() == 1){
			backbtn = false;
			}else{
			backbtn = true;
			}
		
		if (lvl.equals("4")) {
			header = "Section";
			stackBeans.add(new StackBean("4", id,name,stackBeans.size()+1));
			doorLockReportMainBeansList = doorLockReportDao.sectionWise(id,lvl);
			grandTotal(doorLockReportMainBeansList);
			

		} else if (lvl.equals("3")) {
			header = "SubDivision";
			stackBeans.add(new StackBean("3", id,name,stackBeans.size()+1));
			doorLockReportMainBeansList = doorLockReportDao.subDivisionWise(id,lvl);
			grandTotal(doorLockReportMainBeansList);
			

		} else if (lvl.equals("2")) {
			header = "Division";
			stackBeans.add(new StackBean("2", id,name,stackBeans.size()+1));
			doorLockReportMainBeansList = doorLockReportDao.divisionWise(id,lvl);
			grandTotal(doorLockReportMainBeansList);
			

		} else if (lvl.equals("1")) {
			header = "Circle";
			stackBeans.add(new StackBean("1", id,name,stackBeans.size()+1));
			doorLockReportMainBeansList = doorLockReportDao.circleWise(id,lvl);
			grandTotal(doorLockReportMainBeansList);
			

		}

		return "doorLockSumryReport";
	}
	

	
	public void grandTotal(List<DoorLockReportMainBean> reportMainBeanList)
	{
		 grnadTotalNoInstance3to6 = 0;
		 grandTotalInstanceGt6 = 0;
		 grandTotalNotice3to6 = 0;
		 grandtotalNoticeGt6 = 0;
		 grandTotalShifted3to6 = 0;
		 grandTotalShiftedGt6 = 0;
		 
		 for (DoorLockReportMainBean doorLockReportMainBean : reportMainBeanList) 
		 {
			 grnadTotalNoInstance3to6+=doorLockReportMainBean.getNoofInstance3to6();
			 grandTotalNotice3to6+=doorLockReportMainBean.getNoOfNoticed3to6();
			 grandTotalShifted3to6+=doorLockReportMainBean.getNoOfShifted3to6();
			 
			 
			 grandTotalInstanceGt6+=doorLockReportMainBean.getNoOfInstanceGt6();
			 grandtotalNoticeGt6+=doorLockReportMainBean.getNoOfNoticeGt6();
			 grandTotalShiftedGt6+=doorLockReportMainBean.getNoOfShiftedGt6();
			 
			 
			
		}
		
		
	}
	

	public boolean validate() {

		
		boolean isvalid = true;
		if (nullCheck(doorLockReportMainBean.getYearId())) {
			addMessage("ERRCD539E");
			isvalid = false;

			return isvalid;
		}

		return isvalid;
	}
	
	
	public String bread(String lvl,String name,String id,String slno) 
	{
		int size = stackBeans.size();
		if (size >= 1) {
		
			size = size - 1;
			StackBean bean = stackBeans.get(size);
		
		
		if (stackBeans.size() == 1) {
			backbtn = false;
		}

		
		if (lvl.equals("5")) {
			header = "Section";
	
			doorLockReportMainBeansList = doorLockReportDao.sectionWise(id,lvl);
			grandTotal(doorLockReportMainBeansList);

		} else if (lvl.equals("4")) {
			header = "SubDivision";
			
			doorLockReportMainBeansList = doorLockReportDao
					.subDivisionWise(id,lvl);
			grandTotal(doorLockReportMainBeansList);

		} else if (lvl.equals("3")) {
			header = "Division";
		
			doorLockReportMainBeansList = doorLockReportDao
					.divisionWise(id,lvl);
			grandTotal(doorLockReportMainBeansList);

		} else if (lvl.equals("2")) {
			header = "Circle";
			
			
			
			doorLockReportMainBeansList = doorLockReportDao.circleWise(id,lvl);
			grandTotal(doorLockReportMainBeansList);

		}
		else if (lvl.equals("1")) {
			header = "Zone";
			
			doorLockReportMainBeansList = doorLockReportDao.zoneWise(id,lvl);
			grandTotal(doorLockReportMainBeansList);
			backbtn=false;
			

			
		}
		stackBeans = stackBeans.subList(0, Integer.parseInt(slno)-1);
	
		
		}
		
		return "doorLockSumryReport";
	}



	private void clearSession() {

		

		Map sessionMap = (Map) getBindingValue("#{sessionScope}");
		sessionMap.remove("pc_doorLockReportss");
	}

	public DoorLockReportDao getDoorLockReportDao() {
		return doorLockReportDao;
	}

	public void setDoorLockReportDao(DoorLockReportDao doorLockReportDao) {
		this.doorLockReportDao = doorLockReportDao;
	}

	public DoorLockReportMainBean getDoorLockReportMainBean() {

		return doorLockReportMainBean;
	}

	public void setDoorLockReportMainBean(
			DoorLockReportMainBean doorLockReportMainBean) {
		this.doorLockReportMainBean = doorLockReportMainBean;
	}

	public List<DoorLockReportMainBean> getDoorLockReportMainBeansList() {

		return doorLockReportMainBeansList;
	}

	public void setDoorLockReportMainBeansList(
			List<DoorLockReportMainBean> doorLockReportMainBeansList) {
		this.doorLockReportMainBeansList = doorLockReportMainBeansList;
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

	public String getYearId() {
		return yearId;
	}

	public void setYearId(String yearId) {
		this.yearId = yearId;
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

	public int getGrnadTotalNoInstance3to6() {
		return grnadTotalNoInstance3to6;
	}

	public void setGrnadTotalNoInstance3to6(int grnadTotalNoInstance3to6) {
		this.grnadTotalNoInstance3to6 = grnadTotalNoInstance3to6;
	}

	public int getGrandTotalInstanceGt6() {
		return grandTotalInstanceGt6;
	}

	public void setGrandTotalInstanceGt6(int grandTotalInstanceGt6) {
		this.grandTotalInstanceGt6 = grandTotalInstanceGt6;
	}

	public int getGrandTotalNotice3to6() {
		return grandTotalNotice3to6;
	}

	public void setGrandTotalNotice3to6(int grandTotalNotice3to6) {
		this.grandTotalNotice3to6 = grandTotalNotice3to6;
	}

	public int getGrandtotalNoticeGt6() {
		return grandtotalNoticeGt6;
	}

	public void setGrandtotalNoticeGt6(int grandtotalNoticeGt6) {
		this.grandtotalNoticeGt6 = grandtotalNoticeGt6;
	}

	public int getGrandTotalShifted3to6() {
		return grandTotalShifted3to6;
	}

	public void setGrandTotalShifted3to6(int grandTotalShifted3to6) {
		this.grandTotalShifted3to6 = grandTotalShifted3to6;
	}

	public int getGrandTotalShiftedGt6() {
		return grandTotalShiftedGt6;
	}

	public void setGrandTotalShiftedGt6(int grandTotalShiftedGt6) {
		this.grandTotalShiftedGt6 = grandTotalShiftedGt6;
	}

	
	
	
	
	

}
