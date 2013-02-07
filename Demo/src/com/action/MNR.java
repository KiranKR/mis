package com.action;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.MNRDao;
import com.Util.AppUtil;
import com.Util.EnumMonth;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.MNRBean;

@ManagedBean(name="pc_MNR")
@SessionScoped
public class MNR extends PageCodeBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MNRBean mnrBean = new MNRBean();
	private List secLst = new ArrayList();
	private List<EnumMonth> enumMonth = new ArrayList<EnumMonth>();
	private MNRDao mnrDao = new MNRDao();
	private boolean dispTable = false;
	private String btnCss = "greenBtnDis";
	private String btnDisable = "true";
	private int count = 0;
	public MNR() {
		super();
		try {
			enumMonth = Arrays.asList(EnumMonth.values());
			secLst = AppUtil.getDropDownList("section", "SECTN_NAME", "SECTN_ID", "SECTN_NAME", "where SECTN_SUBDIV_ID = "+UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+" ", 0);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public String mnrPg(){
		return "mnrPg";
	}
	
	public void getExistingMNR(){
		mnrBean = mnrDao.getExistingMNR(mnrBean);
		if(mnrBean.isOnlyView()){
			addMessage("ERRCDALL004E");
			btnCss = "greenBtnDis";
			btnDisable = "true";
		}else{
			btnCss = "greenBtn";
			btnDisable = "false";
		}
	}
	
	
	public String saveMNR(){
		if(validateMNR()){
			boolean isValid = mnrDao.saveMNR(mnrBean);
			if(isValid){
				return "mnrSavedSuces";
			}
		}
		return "mnrFailed2Save";
	}
	
	public String getOpenBal(){
		if(validate()){
			count=1;
			mnrBean = mnrDao.getOpenCloseBal(mnrBean);
			getExistingMNR();
			dispTable = true;
		}
		return "/pages/mnr.xhtml";
	}
	public void clsBalance(){
		mnrBean.setMnrNineCloseBal((mnrBean.getMnrNineOpenBal()+mnrBean.getMnrReplace())-mnrBean.getMnrInstall());
		mnrBean.setMnrTenCloseBal((mnrBean.getMnrTenOpenBal()+mnrBean.getGtThrmnrReplace())-mnrBean.getGtThrmnrInsall());
		mnrBean.setMnrElevenCloseBal((mnrBean.getMnrElevenOpenBal()+mnrBean.getGtSixmnrReplace())-mnrBean.getGtSixmnrInstall());
	}
	
	public String disableSaveButton(){
		mnrBean.setOnlyView(true);
		btnCss = "greenBtnDis";
		btnDisable = "true";
		if(count > 0){
			addMessage("ERRCDALL002E");
		}
		return "/pages/mnr.xhtml";
	}
	
	public String cancelOk(){
		clearSession();
		return "cancel";
	}
	
	private void clearSession() {
		Map sessionMap = (Map) getBindingValue("#{sessionScope}");
		sessionMap.remove("pc_MNR");
	}
	public boolean validate()
    {
    	boolean isValid = true;

		if (nullCheck(mnrBean.getSectionId())) {
			addMessage("ERRCD537E");
			isValid = false;
			return isValid;
		} else if (nullCheck(mnrBean.getMonthId())) {
			addMessage("ERRCD538E");
			isValid = false;
			return isValid;
		
		} else if (nullCheck(mnrBean.getYearId())) {
			addMessage("ERRCD539E");
			isValid = false;
			return isValid;
		
		} 
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(AppUtil.getMnthId(mnrBean.getMonthId()));
		stringBuffer.append("/");
		stringBuffer.append(mnrBean.getYearId());
	
		String monthYrStr = stringBuffer.toString();
		
		
		if(AppUtil.StringToMntYr(AppUtil.currentDateMntYr()).before(AppUtil.StringToMntYr(monthYrStr))){
			addMessage("ERRCDALL001E");
			isValid = false;
			return isValid;
		}
		return isValid;
    }

	public boolean validateMNR(){
		boolean isValid = true;
		
		if(!validate()){
			isValid = false;
			return isValid;
		}/*else if (nullCheck(mnrBean.getMnrNineOpenBal())) {
			addMessage("ERRCDMNR005E");
			isValid = false;
			return isValid;
		}else if (nullCheck(mnrBean.getMnrTenOpenBal())) {
			addMessage("ERRCDMNR006E");
			isValid = false;
			return isValid;
		}else if (nullCheck(mnrBean.getMnrElevenOpenBal())) {
			addMessage("ERRCDMNR007E");
			isValid = false;
			return isValid;
		}*/else if (nullCheck(mnrBean.getMnrInstall()) && nullCheck(mnrBean.getGtThrmnrInsall()) && nullCheck(mnrBean.getGtSixmnrInstall())) {
			addMessage("ERRCDMNR001E");
			isValid = false;
			return isValid;
		}else if(nullCheck(mnrBean.getMnrReplace()) && nullCheck(mnrBean.getGtThrmnrReplace()) && nullCheck(mnrBean.getGtSixmnrReplace())){
			addMessage("ERRCDMNR002E");
			isValid = false;
			return isValid;
		}else if(mnrBean.getMnrNineCloseBal() < 0 || mnrBean.getMnrElevenCloseBal() < 0 || mnrBean.getMnrElevenCloseBal() < 0){
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
	public void setEnumMonth(List<EnumMonth> enumMonth) {
		this.enumMonth = enumMonth;
	}
	public List<EnumMonth> getEnumMonth() {
		return enumMonth;
	}
	public List getSecLst() {
		return secLst;
	}
	public void setSecLst(List secLst) {
		this.secLst = secLst;
	}
	public MNRBean getMnrBean() {
		return mnrBean;
	}
	public void setMnrBean(MNRBean mnrBean) {
		this.mnrBean = mnrBean;
	}
	
	
}
