package com.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.TariffDCADao;
import com.Util.AppUtil;
import com.Util.EnumMonth;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.TarifDCABean;
import com.bean.Tariff;



@ManagedBean(name = "pc_tariffDCA")
@SessionScoped
public class TariffDCA extends PageCodeBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 String znId = "0";
	 String crlId = "0";
	 String divId = "0";
	 String subDivId = "0";
	 String secId = "0";
	 String secName="0";
	 String month="0";
	 String year="0";
	 
	 List<String> seclst = new ArrayList();
	 List<Tariff> tarifList = new ArrayList<Tariff>();
	 TariffDCADao dao=new TariffDCADao();
	 TarifDCABean dcaBean=new TarifDCABean();
	 List<Tariff>insertedTarifArl=new ArrayList<Tariff>();

	 double ttlDemand=0.0;
	 double ttlConsum=0.0;
	 double ttArr=0.0;
	 boolean displayLst =false;

	 String btnCss = "greenBtnDis";
	 String btnDisable = "true";
	 int count =0;   
	 
	 
	
	 public  List<EnumMonth> enums = new ArrayList<EnumMonth>();
	
	 String role = UserUtil.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION);
	 String roleBelong = UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION);

	 
	 
	
	public TariffDCA()
	{
		try{
		enums= Arrays.asList(EnumMonth.values());
		seclst = AppUtil.getDropDownList("section", "SECTN_NAME", "SECTN_ID", "SECTN_NAME", "where SECTN_SUBDIV_ID = "+UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+" ", 0);
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getTariffDetails(){
		if(validate()){
		count =1;	
		insertedTarifArl=dao.getTariffDetails(dcaBean.getSecId(), AppUtil.getMnthId(dcaBean.getMonthId()), dcaBean.getYearId());
		dcaBean.setArlTarif(insertedTarifArl);
		List<Tariff> lstTariffs = insertedTarifArl;
		for (Tariff tariff : lstTariffs) {
			dcaBean.setTtlDemand(tariff.getTtlDemand());
			dcaBean.setTtlConsumption(tariff.getTtlConsumption());
			dcaBean.setTtlArr(tariff.getTtlArr());
		}
		displayLst =true;
		btnCss = "greenBtn";
		btnDisable = "false";
		
		}
		return "failTariff";
	}
	
	

    public String saveDetails(){ 
    	
    	if(validateTariff()){
    	
    	List<Tariff> lstTariffs = dcaBean.getArlTarif();
		for (Tariff tariff : lstTariffs) {
			if(tariff.getThId()!=0){
				dcaBean.setThId(tariff.getThId());
			}
		}
		dao.saveDetails(dcaBean);
		clearTariffDetails();
        return "saveTariff";
    	}else{
    	return "failTariff"; 
    	}
    }
    
    public String disableSaveButton(){
    	dcaBean.setOnlyView(true);
		btnCss = "greenBtnDis";
		btnDisable = "true";
		if(count > 0){
			addMessage("ERRCDALL002E");
		}
		return "/pages/TariffDCA.xhtml";
	}
    
    private void clearTariffDetails() {
		Map sessionMap = (Map) getBindingValue("#{sessionScope}");
		sessionMap.remove("pc_tariffDCA");
	}
    
    public String cancelOk()
    {
    	clearTariffDetails();
    	return "cancelTariff";
    }
    
    public String tariffDcaPage(){
    	clearTariffDetails();
    	return "tariffDcaPage";
    }
 
    
    public boolean validate(){
    	boolean isValid = true;

		if (dcaBean.getSecId()== 0) {
			addMessage("ERRCD537E");
			isValid = false;
			return isValid;
		} else if (dcaBean.getMonthId().equals("0")) {
			addMessage("ERRCD538E");
			isValid = false;
			return isValid;
		
		} else if (dcaBean.getYearId().equals("0")) {
			addMessage("ERRCD539E");
			isValid = false;
			return isValid;
		
		}
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(AppUtil.getMnthId(dcaBean.getMonthId()));
		stringBuffer.append("/");
		stringBuffer.append(dcaBean.getYearId());
	
		String monthYrStr = stringBuffer.toString();
		
		
		if(AppUtil.StringToMntYr(AppUtil.currentDateMntYr()).before(AppUtil.StringToMntYr(monthYrStr))){
			addMessage("ERRCDALL001E");
			isValid = false;
			return isValid;
		}
		return isValid;
    }
    
    
    public boolean validateTariff()
    {
    	boolean isValid = true;
		if(!validate()){
			isValid = false;
			return isValid;
		}

		
		boolean isEmpty = true;
		List<Tariff> lstTariffs = dcaBean.getArlTarif();
		for (Tariff tariff : lstTariffs) {
			if (!nullCheck(tariff.getDemand()) && !nullCheck(tariff.getConsumption())) {
				isEmpty = false;
			}
		}
		if (isEmpty) {
			addMessage("ERRCD552E");
			isValid = false;
			return isValid;
		}
		return isValid;
		
 }
    
    
    
   

	public void ttlDemandM() {
		
	   List<Tariff> lstTariffs = dcaBean.getArlTarif();
	   double demand =0.00;
	   double arr=0.00;
	   double ttlArr=0.0;
	   for (Tariff tariff : lstTariffs) {
		demand = demand +tariff.getDemand();
		if(tariff.getDemand() != 0.0 && tariff.getConsumption() != 0.0){
			arr = tariff.getDemand()/tariff.getConsumption();
			ttlArr = ttlArr+arr;
			tariff.setArr(arr);  
		}else{
			tariff.setArr(0.0);
		}
		
	}
   dcaBean.setTtlDemand(demand);
   dcaBean.setTtlArr(ttlArr);
  
	}

	public void ttlConsumTn() {
		

		 List<Tariff> lstTariffs = dcaBean.getArlTarif();
		   double consumption =0.00;
		   double arr =0.00;
		   double ttlArr=0.0;
		   for (Tariff tariff : lstTariffs) {
			   consumption = consumption +tariff.getConsumption();
			   
			   if(tariff.getDemand() != 0.0 && tariff.getConsumption() != 0.0){
					arr = tariff.getDemand()/tariff.getConsumption();
					ttlArr = ttlArr+arr;
					tariff.setArr(arr);  
				}else{
					tariff.setArr(0.0);
				}
		
		}
	   dcaBean.setTtlConsumption(consumption);
	   dcaBean.setTtlArr(ttlArr);
	 

	}

	public void ttlArr() {
		
		   
		   double arr =0.00;
		    if(dcaBean.getTtlDemand()!=0.0 && dcaBean.getTtlConsumption()!=0.00){
		    	arr = dcaBean.getTtlDemand()/dcaBean.getTtlConsumption();
		    } 
		   
		   dcaBean.setTtlArr(arr);
	 
		
		
		
	}

	

	public List<EnumMonth> getEnums() {

		return enums;
	}

	public void setEnums(List<EnumMonth> enums) {
		this.enums = enums;
	}
	public String getZnId() {
		return znId;
	}
	public void setZnId(String znId) {
		this.znId = znId;
	}


	public String getCrlId() {
		return crlId;
	}


	public void setCrlId(String crlId) {
		this.crlId = crlId;
	}


	public String getDivId() {
		return divId;
	}


	public void setDivId(String divId) {
		this.divId = divId;
	}


	public String getSubDivId() {
		return subDivId;
	}


	public void setSubDivId(String subDivId) {
		this.subDivId = subDivId;
	}


	public String getSecId() {
		return secId;
	}


	public void setSecId(String secId) {
		this.secId = secId;
	}


	public String getSecName() {
		return secName;
	}


	public void setSecName(String secName) {
		this.secName = secName;
	}


	public List<String> getSeclst() {
		return seclst;
	}


	public void setSeclst(List<String> seclst) {
		this.seclst = seclst;
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


	public List<Tariff> getTarifList() {
		return tarifList;
	}


	public void setTarifList(List<Tariff> tarifList) {
		this.tarifList = tarifList;
	}

	public String getMonth() {
		
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}

	public TarifDCABean getDcaBean() {
		return dcaBean;
	}


	public void setDcaBean(TarifDCABean dcaBean) {
		this.dcaBean = dcaBean;
	}



	public List<Tariff> getInsertTarifArl() {
		return insertedTarifArl;
	}



	public void setInsertTarifArl(List<Tariff> insertTarifArl) {
		this.insertedTarifArl = insertTarifArl;
	}



	public double getTtlDemand() {
		return ttlDemand;
	}



	public void setTtlDemand(double ttlDemand) {
		this.ttlDemand = ttlDemand;
	}



	public double getTtlConsum() {
		return ttlConsum;
	}



	public void setTtlConsum(double ttlConsum) {
		this.ttlConsum = ttlConsum;
	}



	public double getTtArr() {
		return ttArr;
	}



	public void setTtArr(double ttArr) {
		this.ttArr = ttArr;
	}

	public boolean isDisplayLst() {
		return displayLst;
	}

	public void setDisplayLst(boolean displayLst) {
		this.displayLst = displayLst;
	}

	public List<Tariff> getInsertedTarifArl() {
		return insertedTarifArl;
	}

	public void setInsertedTarifArl(List<Tariff> insertedTarifArl) {
		this.insertedTarifArl = insertedTarifArl;
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
