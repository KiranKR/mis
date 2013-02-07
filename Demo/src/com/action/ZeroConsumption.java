package com.action;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import com.Dao.ZeroConsumptionDao;
import com.Util.AppUtil;
import com.Util.EnumMonth;
import com.Util.MysqlConnectionProvider;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.Capacity;
import com.bean.DiscrepancyBean;
import com.bean.ZeroConsumptionBean;

@ManagedBean(name = "pc_zeroConsumption")
@SessionScoped
public class ZeroConsumption extends PageCodeBase implements Serializable {

	private static final long serialVersionUID = 1L;

	private ZeroConsumptionDao zeroConsumDao = new ZeroConsumptionDao();

	private List<DiscrepancyBean> lstDiscrepancy = new ArrayList<DiscrepancyBean>();
	private ZeroConsumptionBean zeroConsumBean = new ZeroConsumptionBean();

	List<EnumMonth> enumMonths = new ArrayList<EnumMonth>();
	List secList = new ArrayList();
	boolean displayList = false;
   
	String btnCss = "greenBtnDis";
	String btnDisable = "true";
	int count=0;
	
	
	public ZeroConsumption() {
		super();
		try {
			secList = AppUtil
					.getDropDownList(
							"section",
							"SECTN_NAME",
							"SECTN_ID",
							"SECTN_NAME",
							"where SECTN_SUBDIV_ID = "
									+ UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)
									+ " ", 0);
			enumMonths = Arrays.asList(EnumMonth.values());
			lstDiscrepancy = zeroConsumDao.getDiscrepancyTable();
			zeroConsumBean.setList(lstDiscrepancy);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getZeroConDetails() {
		if (validate()) {
			zeroConsumBean = zeroConsumDao.getZeroConList(zeroConsumBean);
			displayList = true;
			count =1;
			btnCss = "greenBtn";
			btnDisable = "false";

		}
		return "zeroConSuces";
	}

	public String zeroConPg() {
		return "zeroConPg";

	}
	
	 public String disableSaveButton(){
		   zeroConsumBean.setOnlyView(true);
			btnCss = "greenBtnDis";
			btnDisable = "true";
			if(count > 0){
				addMessage("ERRCDALL002E");
			}
			return "/pages/zeroConsumption.xhtml";
		}

	public String saveZeroConsum() {
		if (validateZeroCon()) {
			List<DiscrepancyBean> discList = zeroConsumBean.getList();
			for (DiscrepancyBean discBean : discList) {
				if (!discBean.getDiscThId().equals("0")) {
					zeroConsumBean.setTransHeadId(discBean.getDiscThId());
				}
			}
			boolean isValid = zeroConsumDao.saveZeroConsum(zeroConsumBean);
			if (isValid) {
				clearSession();
				return "zeroConSavedSuces";
			}
		}
		return "zeroConSavedFail";
	}

	public List<DiscrepancyBean> getLstDiscrepancy() {
		return lstDiscrepancy;
	}

	public void setLstDiscrepancy(List<DiscrepancyBean> lstDiscrepancy) {
		this.lstDiscrepancy = lstDiscrepancy;
	}

	public void totalCountForZeroConsumption() {
		List<DiscrepancyBean> lstDiscrepancy = zeroConsumBean.getList();
		int count = 0;
		for (DiscrepancyBean discrepancyBean : lstDiscrepancy) {
			count = count + discrepancyBean.getNoOfZeroConsum();
		}
		zeroConsumBean.setGrandTotlZeroCon(count);
	}

	public void totalAmntRaised() {
		List<DiscrepancyBean> lstDiscrepancy = zeroConsumBean.getList();
		double amount = 0.00;
		for (DiscrepancyBean discrepancyBean : lstDiscrepancy) {
			amount = amount + discrepancyBean.getDemandRasied();
		}
		zeroConsumBean.setGrandTotlDemaRaised(amount);
	}

	public String cancelOk() {
		clearSession();
		return "cancel";
	}

	private void clearSession() {
		Map sessionMap = (Map) getBindingValue("#{sessionScope}");
		sessionMap.remove("pc_zeroConsumption");
	}

	public boolean validate() {
		boolean isValid = true;

		if (nullCheck(zeroConsumBean.getSecId())) {
			addMessage("ERRCD537E");
			isValid = false;
			return isValid;
		} else if (nullCheck(zeroConsumBean.getMonthId())) {
			addMessage("ERRCD538E");
			isValid = false;
			return isValid;

		} else if (nullCheck(zeroConsumBean.getYearId())) {
			addMessage("ERRCD539E");
			isValid = false;
			return isValid;

		}
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(AppUtil.getMnthId(zeroConsumBean.getMonthId()));
		stringBuffer.append("/");
		stringBuffer.append(zeroConsumBean.getYearId());
	
		String monthYrStr = stringBuffer.toString();
		
		
		if(AppUtil.StringToMntYr(AppUtil.currentDateMntYr()).before(AppUtil.StringToMntYr(monthYrStr))){
			addMessage("ERRCDALL001E");
			isValid = false;
			return isValid;
		}
		return isValid;
	}

	public boolean validateZeroCon() {
		boolean isValid = true;
		boolean isEmpty = true;
		
		if(!validate()){
			isValid = false;
			return isValid;
		}
		List<DiscrepancyBean> lstDiscrepancy = zeroConsumBean.getList();
		for (DiscrepancyBean discrepancyBean : lstDiscrepancy) {
			
			if (!nullCheck(discrepancyBean.getNoOfZeroConsum())&& !nullCheck(discrepancyBean.getDemandRasied())) {
				isEmpty = false;
			}
		}
		if (isEmpty) {
			addMessage("ERRCDGK011E");
			isValid = false;
			return isValid;
		}

		return isValid;

	}

	

	public boolean isDisplayList() {
		return displayList;
	}

	public void setDisplayList(boolean displayList) {
		this.displayList = displayList;
	}

	public ZeroConsumptionBean getZeroConsumBean() {
		return zeroConsumBean;
	}

	public void setZeroConsumBean(ZeroConsumptionBean zeroConsumBean) {
		this.zeroConsumBean = zeroConsumBean;
	}

	public List<DiscrepancyBean> getLstConsBean() {
		return lstDiscrepancy;
	}

	public void setLstConsBean(List<DiscrepancyBean> lstConsBean) {
		this.lstDiscrepancy = lstConsBean;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
    
}
