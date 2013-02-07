package com.action;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.RevenueReportDao;
import com.Util.AppUtil;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.RevenueBean;

@ManagedBean(name="pc_revenueReport")
@SessionScoped
public class RevenueReport extends PageCodeBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List secLst = new ArrayList();
	private RevenueReportDao revenueReportDao = new RevenueReportDao();
	/*private RevenueReportBean revReportBean = new RevenueReportBean();
	List<RevenueReportBean> revenueReportBeans = new ArrayList<RevenueReportBean>();*/
	
	private RevenueBean revenueBean = new RevenueBean();
	private List<RevenueBean> lstRevBean = new ArrayList<RevenueBean>();
	
	
	public RevenueReport() {
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
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public String getRevenueReport(){
		if(validate()){
			lstRevBean = revenueReportDao.getRevenueReport(revenueBean);
		return "revenueReport";
		}else{
			return "revenueFailureReport";
		}
	}

	public boolean validate()
	  {
	  	boolean isValid = true;

		 if (nullCheck(revenueBean.getSectionId())) {
			addMessage("ERRCD537E");
			isValid = false;
			return isValid;
		} else if (nullCheck(revenueBean.getYearId())) {
				addMessage("ERRCD539E");
				isValid = false;
				return isValid;
			}  
			return isValid;
	  }


	

	public List<RevenueBean> getLstRevBean() {
		return lstRevBean;
	}

	public void setLstRevBean(List<RevenueBean> lstRevBean) {
		this.lstRevBean = lstRevBean;
	}

	public RevenueBean getRevenueBean() {
		return revenueBean;
	}


	public void setRevenueBean(RevenueBean revenueBean) {
		this.revenueBean = revenueBean;
	}


	public RevenueReportDao getRevenueReportDao() {
		return revenueReportDao;
	}


	public void setRevenueReportDao(RevenueReportDao revenueReportDao) {
		this.revenueReportDao = revenueReportDao;
	}
	public List getSecLst() {
		return secLst;
	}
	public void setSecLst(List secLst) {
		this.secLst = secLst;
	}


	

	
    
}
