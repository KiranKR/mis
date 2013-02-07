package com.action;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.TariffDCAReportDao;
import com.Util.AppUtil;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.TarifDCABean;
import com.bean.TarifDCAViewReportBean;
import com.bean.TarifDetailsBean;


@ManagedBean(name="pc_tarifDCAReport")
@SessionScoped
public class TariffDCAReport extends PageCodeBase implements Serializable{
	
	TarifDCABean tarifDCABean=new TarifDCABean();
	TariffDCAReportDao tariffDCAReportDao=new TariffDCAReportDao();
	List<TarifDCAViewReportBean> trarifReportBeans =new ArrayList<TarifDCAViewReportBean>();
	
	double ttlDemand =0.0;
	double ttlConsumption=0.0;
	double ttlArr=0.0;
	
	
	
	
	List secList = new ArrayList();
	
	public TariffDCAReport()
	{
		  try {
				secList = AppUtil.getDropDownList("section", "SECTN_NAME", "SECTN_ID", "SECTN_NAME", "where SECTN_SUBDIV_ID = "+UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+" ", 0);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	
	
	
	public String getTarifDetails()
	{
		if(validate()){
		trarifReportBeans = tariffDCAReportDao.getTransformerDetails(tarifDCABean.getSecId(), tarifDCABean.getYearId());
		totalCount(trarifReportBeans);
		return "traiffDetails";
		}else{
			return "traiffDetailsFailure";
		}
	}

	public boolean validate()
	  {
	  	boolean isValid = true;

			if (nullCheck(tarifDCABean.getSecId())) {
				addMessage("ERRCD537E");
				isValid = false;
				return isValid;
			}  else if (nullCheck(tarifDCABean.getYearId())) {
				addMessage("ERRCD539E");
				isValid = false;
				return isValid;
			
			} 
			return isValid;
	  }


	public void totalCount(List<TarifDCAViewReportBean> trarifReportBeans){
		ttlDemand =0.0;
		ttlConsumption =0.0;
		ttlArr =0.0;
		
		for (TarifDCAViewReportBean monthBean : trarifReportBeans) {
			List<TarifDetailsBean> lstTarifDetailsBeans =monthBean.getTarifDetailsBeans(); 
			for (TarifDetailsBean bean : lstTarifDetailsBeans) {
				  ttlDemand = ttlDemand+bean.getDemand();
				  ttlConsumption =ttlConsumption+bean.getConsumption();
				  ttlArr =ttlArr+bean.getArr();
			}
		}
	}
	
	
	
	public TarifDCABean getTarifDCABean() {
		return tarifDCABean;
	}




	public void setTarifDCABean(TarifDCABean tarifDCABean) {
		this.tarifDCABean = tarifDCABean;
	}




	public List<TarifDCAViewReportBean> getTrarifReportBeans() {
		return trarifReportBeans;
	}




	public void setTrarifReportBeans(List<TarifDCAViewReportBean> trarifReportBeans) {
		this.trarifReportBeans = trarifReportBeans;
	}




	public List getSecList() {
		return secList;
	}




	public void setSecList(List secList) {
		this.secList = secList;
	}




	public TariffDCAReportDao getTariffDCAReportDao() {
		return tariffDCAReportDao;
	}




	public void setTariffDCAReportDao(TariffDCAReportDao tariffDCAReportDao) {
		this.tariffDCAReportDao = tariffDCAReportDao;
	}




	public double getTtlDemand() {
		return ttlDemand;
	}




	public void setTtlDemand(double ttlDemand) {
		this.ttlDemand = ttlDemand;
	}




	public double getTtlConsumption() {
		return ttlConsumption;
	}




	public void setTtlConsumption(double ttlConsumption) {
		this.ttlConsumption = ttlConsumption;
	}




	public double getTtlArr() {
		return ttlArr;
	}




	public void setTtlArr(double ttlArr) {
		this.ttlArr = ttlArr;
	}

	
	
}
