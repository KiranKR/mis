package com.action;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.DisconnectionDao;
import com.Util.AppUtil;
import com.Util.EnumMonth;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.DisconnectionBean;


@ManagedBean(name="pc_disConnection")
@SessionScoped
public class Disconnection extends PageCodeBase implements Serializable{

	private static final long serialVersionUID = 1L;
	List secList = new ArrayList();
	List<EnumMonth> enumMonths = new ArrayList<EnumMonth>();
	DisconnectionBean bean = new DisconnectionBean();
	DisconnectionDao dao = new DisconnectionDao();
	String btnCss = "greenBtn";
	String btnDisable = "false";
	
	public Disconnection()
	{
		try {
			secList =AppUtil.getDropDownList("section", "SECTN_NAME", "SECTN_ID", "SECTN_NAME", "where SECTN_SUBDIV_ID = "+UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+" ", 0);
			enumMonths = Arrays.asList(EnumMonth.values());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

	public String saveData() {
		if(validate()){
		dao.saveData(bean);
		return "success";
		}else{
			return "/pages/disConnection.xhtml";
		}
	}

	public String okHome() {
		clearDisconnectionDetails();
		return "Home";
	}

	public String cancelHome() {
		return "cancelHomePage";
	}

	public void getExistingDetails(){
		if(!bean.getMnthId().equals("") && !bean.getYearId().equals("")){
			bean = dao.getExistingData(bean);
		}
		if(bean.isOnlyView()){
			btnCss = "greenBtnDis";
			btnDisable = "true";
		}else{
			btnCss = "greenBtn";
			btnDisable = "false";
		}
		
	}
	
	
	private void clearDisconnectionDetails() {
		Map sessionMap = (Map) getBindingValue("#{sessionScope}");
		sessionMap.remove("pc_disConnection");
	}

	public void getDisconctnCB() {
		bean.setCloseBal(bean.getOpenBal() - bean.getDisconctDurMnth()
				- bean.getReconctDurMnth());
	}
	
	public String getDisconctnOB() {
		if (!bean.getMnthId().equals("") && !bean.getYearId().equals("")) {
		if (curMntYrValidation()) {
		bean = dao.getDisconctnOB(bean);
		getExistingDetails();
		}
		}
		return "/pages/disConnection.xhtml";
		}

	
	public void getDisconctnCBAmnt(){
		bean.setClosBalAmt((bean.getOpenBalAmt() + bean.getAmtInv()) - bean.getAmtRel());
	}
	
	public boolean curMntYrValidation(){
		boolean isValid =true;
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(AppUtil.getMnthId(bean.getMnthId()));
		stringBuffer.append("/");
		stringBuffer.append(bean.getYearId());
	
		String monthYrStr = stringBuffer.toString();
		
		
		if(AppUtil.StringToMntYr(AppUtil.currentDateMntYr()).before(AppUtil.StringToMntYr(monthYrStr))){
			addMessage("ERRCDALL001E");
			isValid = false;
			return isValid;
		}
		return isValid;
	}
	
	public boolean validate(){
		boolean isValid =true;
		
		if(nullCheck(bean.getSecId())){
			addMessage("ERRCD537E");
			isValid = false;
			return isValid;
		}else if(nullCheck(bean.getMnthId())){
			addMessage("ERRCD538E");
			isValid = false;
			return isValid;
		}else if(nullCheck(bean.getYearId())){
			addMessage("ERRCD539E");
			isValid = false;
			return isValid;
		}else if(nullCheck(bean.getVisitDurMnth())){
			addMessage("ERRCDDIS001E");
			isValid =false;
			return isValid;
			
		}else if(nullCheck(bean.getAmtInv())){
			addMessage("ERRCDDIS002E");
			isValid =false;
			return isValid;
		}else if(bean.getCloseBal()<0 && bean.getClosBalAmt()<0.0){
			addMessage("ERRCDALL003E");
			isValid =false;
			return isValid;
		}
		
		return isValid;
	}
	
	
	public DisconnectionBean getBean() {
		return bean;
	}

	public void setBean(DisconnectionBean bean) {
		this.bean = bean;
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

}
