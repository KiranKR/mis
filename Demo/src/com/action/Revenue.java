package com.action;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.RevenueDao;
import com.Util.AppUtil;
import com.Util.EnumMonth;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.RevenueBean;

@ManagedBean(name = "pc_revenue")
@SessionScoped
public class Revenue  extends PageCodeBase implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<EnumMonth> enumMonths = new ArrayList<EnumMonth>();
	private List secList = new ArrayList();
	
	private RevenueBean revenueBean = new RevenueBean();
	private RevenueDao revenueDao = new RevenueDao();
	
	private boolean dispTable = false;
	private String btnCss = "greenBtnDis";
	private String btnDisable = "true";
	private int count = 0;
	
	public Revenue() {
		super();
		try {
			secList = AppUtil.getDropDownList("section","SECTN_NAME","SECTN_ID","SECTN_NAME","where SECTN_SUBDIV_ID = "
									+ UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+ " ", 0);
			enumMonths = Arrays.asList(EnumMonth.values());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String revenuePg(){
		return "revenueEntryPg";
	}
	
	public void getExistingRevenue(){
			revenueBean = revenueDao.getExistingRevenue(revenueBean);
		if(revenueBean.isOnlyView()){
			addMessage("ERRCDALL004E");
			btnCss = "greenBtnDis";
			btnDisable = "true";
		}else{
			btnCss = "greenBtn";
			btnDisable = "false";
		}
	}
	
	
	public String saveRevenue(){
		if( vaildateDemColl()){
			boolean isValid = revenueDao.saveRevenue(revenueBean);
			if(isValid){
				return "revenueSaveSuccess";
			}else{
				addMessage("ERRCDREV003E");
			}
		}
		return "revenueEntryPg";
	}
	
	public String getOpenBal(){
		if(validate()){
			count=1;
			revenueBean = revenueDao.getOpenCloseBal(revenueBean);
			getExistingRevenue();
			dispTable = true;
		}
		return "revenueEntryPg";
	}
	
	public void setCloseBal(){
		revenueBean.setCloseBal((revenueBean.getOpenBal()+revenueBean.getDemand())-revenueBean.getCollection());
	}
	
	public String disableSaveButton(){
		revenueBean.setOnlyView(true);
		btnCss = "greenBtnDis";
		btnDisable = "true";
		if(count > 0){
			addMessage("ERRCDALL002E");
		}
		return "revenueEntryPg";
	}
	
	public String okCancel(){
		clearSession();
		return "cancel";
	}
	
	
	private void clearSession() {
		Map sessionMap = (Map) getBindingValue("#{sessionScope}");
		sessionMap.remove("pc_revenue");
	}
	
	
	
	
	 public boolean validate()
	    {
	    	boolean isValid = true;

			if (nullCheck(revenueBean.getSectionId())) {
				addMessage("ERRCD537E");
				isValid = false;
				return isValid;
			} else if (nullCheck(revenueBean.getMonthId())) {
				addMessage("ERRCD538E");
				isValid = false;
				return isValid;
			
			} else if (nullCheck(revenueBean.getYearId())) {
				addMessage("ERRCD539E");
				isValid = false;
				return isValid;
			
			}
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(AppUtil.getMnthId(revenueBean.getMonthId()));
			stringBuffer.append("/");
			stringBuffer.append(revenueBean.getYearId());
		
			String monthYrStr = stringBuffer.toString();
			
			
			if(AppUtil.StringToMntYr(AppUtil.currentDateMntYr()).before(AppUtil.StringToMntYr(monthYrStr))){
				addMessage("ERRCDALL001E");
				isValid = false;
				return isValid;
			}
			return isValid;
	    }
	public boolean vaildateDemColl(){
		boolean isValid = true;
		if(!validate()){
			isValid = false;
			return isValid;
		}
		else if (nullCheck(revenueBean.getDemand())) {
			addMessage("ERRCDREV001E");
			isValid = false;
			return isValid;
		} else if (nullCheck(revenueBean.getCollection())) {
			addMessage("ERRCDREV002E");
			isValid = false;
			return isValid;
		} else if (revenueBean.getCloseBal() < 0.0) {
			addMessage("ERRCDALL003E");
			isValid = false;
			return isValid;
		} 			
		return isValid;

	}
	 
	
	 
	
	public String getBtnCss() {
		return btnCss;
	}

	public void setBtnCss(String btnCss) {
		this.btnCss = btnCss;
	}

	public String getBtnDisable() {
		return btnDisable;
	}

	public void setBtnDisable(String btnDisable) {
		this.btnDisable = btnDisable;
	}

	public boolean isDispTable() {
		return dispTable;
	}

	public void setDispTable(boolean dispTable) {
		this.dispTable = dispTable;
	}

	public RevenueBean getRevenueBean() {
		return revenueBean;
	}
	public void setRevenueBean(RevenueBean revenueBean) {
		this.revenueBean = revenueBean;
	}
	public List<EnumMonth> getEnumMonths() {
		return enumMonths;
	}

	public void setEnumMonths(List<EnumMonth> enumMonths) {
		this.enumMonths = enumMonths;
	}

	public List getSecList() {
		return secList;
	}

	public void setSecList(List secList) {
		this.secList = secList;
	}

	
	
}
