package com.action;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.DoorLockDao;
import com.Util.AppUtil;
import com.Util.EnumMonth;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.DoorLockBean;

@ManagedBean(name="pc_doorLock")
@SessionScoped
public class DoorLock  extends PageCodeBase implements Serializable{

	
	List secList = new ArrayList();
	List<EnumMonth> enumMonths = new ArrayList<EnumMonth>();
	DoorLockDao doorLockDao = new DoorLockDao();
	DoorLockBean doorLockBean = new DoorLockBean();
	private boolean displayValue =false;
	String btnCss = "greenBtnDis";
	String btnDisable = "true";
	int count=0;
	
	
	public DoorLock()
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
	
	
	public String getOpeningBalance(){
		if(validate()){
		count =1;	
		int thToSixOB = doorLockDao.getOpeningBalance(doorLockBean);
		int gtSixOB = doorLockDao.getGtSixOpeningBalance(doorLockBean);
		doorLockBean.setOpBalance(thToSixOB);
		doorLockBean.setGtSixOpBalance(gtSixOB);
		getExistingValues();
	    displayValue =true; 	
		}
		return "/pages/doorLock.xhtml";
	}
	
	public String getExistingValues(){
		if(validate()){
		doorLockBean =  doorLockDao.getExistingValues(doorLockBean);
		if(doorLockBean.isOnlyView()){
			addMessage("ERRCDALL004E");
			btnCss = "greenBtnDis";
			btnDisable = "true";
		}else{
			btnCss = "greenBtn";
			btnDisable = "false";
		}
		return "drExist";
		}else{
			return "failureDoorLockDetilas";
		}
	}
	
	public String saveDoorLock(){
		if(validateDrLock()){
		doorLockDao.saveDoorLockDetails(doorLockBean);
		return "saveDoorLockDetilas";
		}else{
			return "failureDoorLockDetilas";
		}
	}
	
	public void closingBalance(){

		doorLockBean.setCloBalance((doorLockBean.getOpBalance()+doorLockBean.getNoticeServed())-(doorLockBean.getMetersShifted()+doorLockBean.getNotShift()));
	}
	
	public void gtSixClosingBalance(){

		doorLockBean.setGtSixCloBalance((doorLockBean.getGtSixOpBalance()+doorLockBean.getGtSixNoticeServed())-(doorLockBean.getGtSixMetersShifted()+doorLockBean.getGtSixNotShift()));
	}

	public void disableSaveButton(){
		doorLockBean.setOnlyView(true);
		btnCss = "greenBtnDis";
		btnDisable = "true";
		if(count > 0){
			addMessage("ERRCDALL002E");
		}
	}
	
	
	
	
	public String okButton(){
		 clearDoorLockDetails();
		return "okButton";
	}
	
	public String cancel(){
		return "cancel";
	}
	  
    private void clearDoorLockDetails() {
		Map sessionMap = (Map) getBindingValue("#{sessionScope}");
		sessionMap.remove("pc_doorLock");
	}
    public String doorLockPage(){
		clearDoorLockDetails();
		return "doorLockPage";
	}
    
    public boolean validate(){
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
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(AppUtil.getMnthId(doorLockBean.getMonthId()));
		stringBuffer.append("/");
		stringBuffer.append(doorLockBean.getYearId());
	
		String monthYrStr = stringBuffer.toString();
		
		
		if(AppUtil.StringToMntYr(AppUtil.currentDateMntYr()).before(AppUtil.StringToMntYr(monthYrStr))){
			addMessage("ERRCDALL001E");
			isValid = false;
			return isValid;
		}
		return isValid;
    }

 
    public boolean validateDrLock(){
    	boolean isValid = true;
		if(!validate()){
			isValid = false;
			return isValid;
		}
		
		 if(nullCheck(doorLockBean.getNoOFDoorLock())&& nullCheck(doorLockBean.getGtSixNoOFDoorLock())){
			addMessage("ERRCDDRL001E");
			isValid = false;
			return isValid;
		}else if(nullCheck(doorLockBean.getNoticeServed())&& nullCheck(doorLockBean.getGtSixNoticeServed())){
			addMessage("ERRCDDRL002E");
			isValid = false;
			return isValid;
		}else if(nullCheck(doorLockBean.getMetersShifted())&& nullCheck(doorLockBean.getGtSixMetersShifted())){
			addMessage("ERRCDDRL003E");
			isValid = false;
			return isValid;
		}else if(doorLockBean.getCloBalance()<0 && doorLockBean.getGtSixCloBalance()<0){
			addMessage("ERRCDALL003E");
			isValid =false;
			return isValid;
		}else if((doorLockBean.getNotShift()!=0)&& (doorLockBean.getGtSixNotShift()!=0)&&(nullCheck(doorLockBean.getActionTaken()))){
			addMessage("ERRCDDRL004E");
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


public DoorLockDao getDoorLockDao() {
	return doorLockDao;
}


public void setDoorLockDao(DoorLockDao doorLockDao) {
	this.doorLockDao = doorLockDao;
}


public DoorLockBean getDoorLockBean() {
	return doorLockBean;
}


public void setDoorLockBean(DoorLockBean doorLockBean) {
	this.doorLockBean = doorLockBean;
}


public boolean isDisplayValue() {
	return displayValue;
}


public void setDisplayValue(boolean displayValue) {
	this.displayValue = displayValue;
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
