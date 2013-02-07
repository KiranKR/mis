package com.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import com.Dao.MNRDetailReportDao;
import com.Util.AppUtil;
import com.Util.EnumMonth;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.MNRRepBean;

@ManagedBean (name="pc_MNRDetailReport")
@RequestScoped
public class MNRDetailReport extends PageCodeBase  {
	
	private List<EnumMonth> enumMonthlist = new ArrayList<EnumMonth>();
	private List secList = new ArrayList();
	private MNRRepBean mnrBean = new MNRRepBean();
	private List<MNRRepBean> mnrDetailList = new ArrayList<MNRRepBean>();
	private MNRDetailReportDao mnrDetailReportDao=new MNRDetailReportDao();
	private boolean dispTable = false;
	
	public MNRDetailReport() {
		
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
			enumMonthlist = Arrays.asList(EnumMonth.values());
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}

	public String getMNRDetailRep() {
		if (validate()) {
			dispTable = true;
			mnrBean = mnrDetailReportDao.getMNRDetailList(mnrBean);

			return "mnrDetailReport";
		} else {
			return "mnrDetailReport";
		}

	}
  
  public boolean validate() {
		boolean isValid = true;

		if (nullCheck(mnrBean.getSecId())) {
			addMessage("ERRCD537E");
			isValid = false;
		}
		else if (nullCheck(mnrBean.getMonthId())) {
			addMessage("ERRCD538E");
			isValid = false;
		}
		else if (nullCheck(mnrBean.getYearId())) {
			addMessage("ERRCD539E");
			isValid = false;
		}
		return isValid;
	} 
  
public List<EnumMonth> getEnumMonthlist() {
	return enumMonthlist;
}
public void setEnumMonthlist(List<EnumMonth> enumMonthlist) {
	this.enumMonthlist = enumMonthlist;
}
public List getSecList() {
	return secList;
}
public void setSecList(List secList) {
	this.secList = secList;
}

public MNRRepBean getMnrBean() {
	return mnrBean;
}
public void setMnrBean(MNRRepBean mnrBean) {
	this.mnrBean = mnrBean;
}
public List<MNRRepBean> getMnrDetailList() {
	return mnrDetailList;
}
public void setMnrDetailList(List<MNRRepBean> mnrDetailList) {
	this.mnrDetailList = mnrDetailList;
}
public MNRDetailReportDao getMnrDetailReportDao() {
	return mnrDetailReportDao;
}
public void setMnrDetailReportDao(MNRDetailReportDao mnrDetailReportDao) {
	this.mnrDetailReportDao = mnrDetailReportDao;
}
public boolean isDispTable() {
	return dispTable;
}
public void setDispTable(boolean dispTable) {
	this.dispTable = dispTable;
}
  

}
