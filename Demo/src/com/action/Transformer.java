package com.action;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.TransformerDao;
import com.Util.AppUtil;
import com.Util.EnumMonth;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.Capacity;
import com.bean.TransformerBean;

@ManagedBean(name="pc_transformer")
@SessionScoped
public class Transformer extends PageCodeBase implements Serializable{
	
	TransformerDao transformerDao = new TransformerDao();
	TransformerBean transformer = new TransformerBean();
	
	 
	List<String> secList = new ArrayList<String>();
	List<Capacity> capacityList = new ArrayList<Capacity>();
	List<EnumMonth> enumMonths = new ArrayList<EnumMonth>();
    boolean displayList = false;
   
	String btnCss = "greenBtnDis";
	String btnDisable = "true";
    int count =0;
    

	
    public Transformer() {
	  try {
		  secList = AppUtil.getDropDownList("section", "SECTN_NAME", "SECTN_ID", "SECTN_NAME", "where SECTN_SUBDIV_ID = "+UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+" ", 0);
		  enumMonths = Arrays.asList(EnumMonth.values());
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
    }

    public String getTansformerDetails(){
    	  if(validate()){
    	   count =1;	  
    	  capacityList = transformerDao.getCapacityValuesList(transformer.getSecId(),AppUtil.getMnthId(transformer.getMonthId()),transformer.getYearId());
    	  transformer.setCapacities(capacityList);
    	  displayList = true;
    	  btnCss = "greenBtn";
		  btnDisable = "false";
    	  }
    	  return "valdateTransformer";
    }
    
    public String saveDetails()
    {
    	if(validateTrans()){
    		 List<Capacity> capacities = transformer.getCapacities();
 			
 			for (Capacity bean : capacities) {
 				if(bean.getThId()!=0){
 					transformer.setThId(bean.getThId());
 				}
 				if(bean.gettId()!=0){
 					transformer.settId(bean.gettId());
 				}
 			}	
    	transformerDao.saveDetails(transformer);
    	
    	clearTransformerDetails();
    	return "saveTransformer";
    	}
    	return "valdateTransformer";
    }

    public String cancelOk()
    {
    	clearTransformerDetails();
    	return "cancelTrans";
    }
    
    public String transPage()
    {
    	clearTransformerDetails();
    	return "transPage";
    }
    
    private void clearTransformerDetails() {
		Map sessionMap = (Map) getBindingValue("#{sessionScope}");
		sessionMap.remove("pc_transformer");
	}
    
    public String disableSaveButton(){
    	transformer.setOnlyView(true);
		btnCss = "greenBtnDis";
		btnDisable = "true";
		if(count > 0){
			addMessage("ERRCDALL002E");
		}
	   return "/pages/transformer.xhtml";
    }
    
    public String cancel()
    {
    	clearTransformerDetails();
    	return "cancelTransData";
    }

    
    public boolean validate(){
    	boolean isValid = true;

		if (transformer.getSecId()== 0) {
			addMessage("ERRCD537E");
			isValid = false;
			return isValid;
		} else if (transformer.getMonthId().equals("0")) {
			addMessage("ERRCD538E");
			isValid = false;
			return isValid;
		
		} else if (transformer.getYearId().equals("0")) {
			addMessage("ERRCD539E");
			isValid = false;
			return isValid;
		
		}
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(AppUtil.getMnthId(transformer.getMonthId()));
		stringBuffer.append("/");
		stringBuffer.append(transformer.getYearId());
	
		String monthYrStr = stringBuffer.toString();
		
		
		if(AppUtil.StringToMntYr(AppUtil.currentDateMntYr()).before(AppUtil.StringToMntYr(monthYrStr))){
			addMessage("ERRCDALL001E");
			isValid = false;
			return isValid;
		}
		return isValid;
    }
    
    
    public boolean validateTrans()
    {
    	boolean isValid = true;
		if(!validate()){
			isValid = false;
			return isValid;
		}

		
		boolean isEmpty = true;
		List<Capacity> lstCapacity = transformer.getCapacities();
		for (Capacity capacity : lstCapacity) {
		if(!nullCheck(capacity.getAdditionCnt()) || !nullCheck(capacity.getFailedCnt())|| !nullCheck(capacity.getReplacedCnt()) || !nullCheck(capacity.getMaintanenceCnt())
		   || !nullCheck(capacity.getRuAdditionCnt()) || !nullCheck(capacity.getRuFailedCnt())|| !nullCheck(capacity.getRuReplacedCnt()) || !nullCheck(capacity.getRuMaintanenceCnt())	){
		isEmpty = false;
		}
		}if(isEmpty){
		addMessage("ERRCD551E");
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

public TransformerDao getTransformerDao() {
	return transformerDao;
}

public void setTransformerDao(TransformerDao transformerDao) {
	this.transformerDao = transformerDao;
}

public List<Capacity> getCapacityList() {
	return capacityList;
}


public void setCapacityList(List<Capacity> capacityList) {
	this.capacityList = capacityList;
}


public List<EnumMonth> getEnumMonths() {
	return enumMonths;
}


public void setEnumMonths(List<EnumMonth> enumMonths) {
	this.enumMonths = enumMonths;
}


public TransformerBean getTransformer() {
	return transformer;
}


public void setTransformer(TransformerBean transformer) {
	this.transformer = transformer;
}

public boolean isDisplayList() {
	return displayList;
}

public void setDisplayList(boolean displayList) {
	this.displayList = displayList;
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
