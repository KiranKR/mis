package com.action;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.SchemaStatusWiseReportDao;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.ReportBean;


@ManagedBean (name="pc_SchemaStatusWiseReport")
@SessionScoped
public class SchemaStatusWiseReport extends PageCodeBase {
	
	private ReportBean reportBean = new ReportBean();
	private SchemaStatusWiseReportDao reportDao = new SchemaStatusWiseReportDao();  
	
	String role = UserUtil.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION);
	String roleBelong = UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION);
	
	public SchemaStatusWiseReport() {
    reportBean=reportDao.getReportBean(role,roleBelong);
	
	}

	public ReportBean getReportBean() {
		return reportBean;
	}

	public void setReportBean(ReportBean reportBean) {
		this.reportBean = reportBean;
	}

	public SchemaStatusWiseReportDao getReportDao() {
		return reportDao;
	}

	public void setReportDao(SchemaStatusWiseReportDao reportDao) {
		this.reportDao = reportDao;
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
	
	
	

}
