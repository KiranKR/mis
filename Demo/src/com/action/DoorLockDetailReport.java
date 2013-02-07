package com.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.Dao.DoorLockDetailReportDao;
import com.Util.AppUtil;
import com.Util.EnumMonth;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.DoorLockBean;

@ManagedBean(name="pc_doorLockDetailReport")
@RequestScoped
public class DoorLockDetailReport extends PageCodeBase {
	
	List secList = new ArrayList();
	List<EnumMonth> enumMonths = new ArrayList<EnumMonth>();
	DoorLockBean doorLockBean = new DoorLockBean();
	DoorLockDetailReportDao dao=new DoorLockDetailReportDao();
	private boolean displayValue =false;
	
	public DoorLockDetailReport() {
		
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

				secList = AppUtil.getDropDownList("section", "SECTN_NAME","SECTN_ID", "SECTN_NAME", whrClause, 0);
				enumMonths = Arrays.asList(EnumMonth.values());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
}
	
	public String getDoorLockDetails()
	{
		if(validate())
		{		
		doorLockBean=dao.getDoorLockDeatils(doorLockBean);
		System.out.println("!!!!!!!!"+doorLockBean.getOpBalance());
		System.out.println("@@@@@@" +doorLockBean.getGtSixOpBalance());
		displayValue =true;
		}
		
		return "doorLockDetailReport";
		
	}
	
	
	public boolean validate()
	{
		boolean isValid = true;
		if (nullCheck(doorLockBean.getSecId())) {
			addMessage("ERRCD537E");
			isValid = false;
			return isValid;
		} else if (nullCheck(doorLockBean.getMonthId())) {
			addMessage("ERRCD538E");
			isValid = false;
			return isValid;
		
		} else if (nullCheck(doorLockBean.getYearId())) {
			addMessage("ERRCD539E");
			isValid = false;
			return isValid;
		} 
		return isValid;
	}

	public List getSecList() {
		return secList;
	}

	public void setSecList(List secList) {
		this.secList = secList;
	}

	public List<EnumMonth> getEnumMonths() {
		return enumMonths;
	}

	public void setEnumMonths(List<EnumMonth> enumMonths) {
		this.enumMonths = enumMonths;
	}

	public DoorLockBean getDoorLockBean() {
		return doorLockBean;
	}

	public void setDoorLockBean(DoorLockBean doorLockBean) {
		this.doorLockBean = doorLockBean;
	}

	public DoorLockDetailReportDao getDao() {
		return dao;
	}

	public void setDao(DoorLockDetailReportDao dao) {
		this.dao = dao;
	}

	public boolean isDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(boolean displayValue) {
		this.displayValue = displayValue;
	}
	
	
	
	
	
	

}
