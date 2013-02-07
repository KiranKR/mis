package com.action;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.InstalationDao;
import com.Util.AppUtil;
import com.Util.EnumMonth;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.InstalationBean;


@ManagedBean(name="pc_instalation")
@SessionScoped
public class Instalation extends PageCodeBase implements Serializable{

	private static final long serialVersionUID = 1L;
	List secList = new ArrayList();
	List<EnumMonth> enumMonths = new ArrayList<EnumMonth>();
	InstalationBean bean = new InstalationBean();
	InstalationDao dao = new InstalationDao();
	String btnCss = "greenBtn";
	String btnDisable = "false";
	
	public Instalation()
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
	
	public String saveData() {
		if(validate()){
		dao.saveData(bean);
		return "success";
		}
		return "/pages/instalation.xhtml";
	}

	public String okHome() {
		clearInstalation();
		return "Home";
	}

	public String cancelHome() {
		return "cancelHomePage";
	}

	public String getInstalationOB() {
		if(curMntYrValidation()){
			bean = dao.getDisconctnOB(bean);
			getExistingDetails();
			}
			return "/pages/instalation.xhtml";	
	}

	private void clearInstalation() {
		Map sessionMap = (Map) getBindingValue("#{sessionScope}");
		sessionMap.remove("pc_instalation");
	}

	public void getTotal() {
		bean.setTotal(bean.getMtrWtrSply() + bean.getUnMtrWtrSply());
	}
	
	public void getOPT(){
		bean.setOpenBalTtlAmt(bean.getOpenBalPrinAmt() + bean.getOpenBalIntAmt());
		bean.setClosBalTtlAmt(bean.getOpenBalTtlAmt()+ bean.getDemdBalTtlAmt()+ bean.getClctnBalTtlAmt());
		bean.setClosBalPrinAmt(bean.getOpenBalPrinAmt() + bean.getDemdBalPrinAmt() + bean.getClctnBalPrinAmt());
		bean.setClosBalIntAmt(bean.getOpenBalIntAmt() + bean.getDemdBalIntAmt() + bean.getClctnBalIntAmt());
		bean.setClosBalTtlAmt(bean.getOpenBalTtlAmt() + bean.getDemdBalTtlAmt() + bean.getClctnBalTtlAmt());
	}
	
	public void getDT(){
		bean.setDemdBalTtlAmt(bean.getDemdBalPrinAmt() + bean.getDemdBalIntAmt());
		bean.setClosBalTtlAmt(bean.getOpenBalTtlAmt() + bean.getDemdBalTtlAmt() + bean.getClctnBalTtlAmt());
		bean.setClosBalPrinAmt(bean.getOpenBalPrinAmt() + bean.getDemdBalPrinAmt() + bean.getClctnBalPrinAmt());
		bean.setClosBalIntAmt(bean.getOpenBalIntAmt() + bean.getDemdBalIntAmt() + bean.getClctnBalIntAmt());
		bean.setClosBalTtlAmt(bean.getOpenBalTtlAmt() + bean.getDemdBalTtlAmt() + bean.getClctnBalTtlAmt());
	}
	
	public void getCT(){
		bean.setClctnBalTtlAmt(bean.getClctnBalPrinAmt() + bean.getClctnBalIntAmt());
		bean.setClosBalTtlAmt(bean.getOpenBalTtlAmt() + bean.getDemdBalTtlAmt() + bean.getClctnBalTtlAmt());
		bean.setClosBalPrinAmt(bean.getOpenBalPrinAmt() + bean.getDemdBalPrinAmt() + bean.getClctnBalPrinAmt());
		bean.setClosBalIntAmt(bean.getOpenBalIntAmt() + bean.getDemdBalIntAmt() + bean.getClctnBalIntAmt());
		bean.setClosBalTtlAmt(bean.getOpenBalTtlAmt() + bean.getDemdBalTtlAmt() + bean.getClctnBalTtlAmt());
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
		}else if(nullCheck(bean.getMtrWtrSply())){
			addMessage("ERRCDINS001E");
			isValid = false;
			return isValid;
		}else if(nullCheck(bean.getUnMtrWtrSply())){
			addMessage("ERRCDINS002E");
			isValid = false;
			return isValid;
		}else if(bean.getClosBalIntAmt()<0&&bean.getClosBalPrinAmt()<0&&bean.getClosBalTtlAmt()<=0){
			addMessage("ERRCDALL003E");
			isValid =false;
	        return isValid;
		}
		
		return  isValid;
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

	public InstalationBean getBean() {
		return bean;
	}

	public void setBean(InstalationBean bean) {
		this.bean = bean;
	}

	public InstalationDao getDao() {
		return dao;
	}

	public void setDao(InstalationDao dao) {
		this.dao = dao;
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
