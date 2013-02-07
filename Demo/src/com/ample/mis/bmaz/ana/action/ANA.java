package com.ample.mis.bmaz.ana.action;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Util.AppUtil;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;



@ManagedBean(name="pc_ANA")
@SessionScoped
public class ANA extends PageCodeBase implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5299381036014928599L;
	
	private Date sysDate = new Date();
	private String sysDateStr = AppUtil.dateToString(sysDate);
	
	private List secLst = new ArrayList();
	
	public ANA() {
		super();
		try {
			secLst = AppUtil.getDropDownList("section", "SECTN_NAME", "SECTN_ID", "SECTN_NAME", "where SECTN_SUBDIV_ID = "+UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+" ", 0);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String getSysDateStr() {
		return sysDateStr;
	}

	public void setSysDateStr(String sysDateStr) {
		this.sysDateStr = sysDateStr;
	}
	
	
	
}
