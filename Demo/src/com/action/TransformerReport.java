package com.action;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import com.Dao.TransformerReportDao;
import com.Util.AppUtil;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.TransformerBean;
import com.bean.TransformerDetailsBean;
import com.bean.TransformerReportBean;

@ManagedBean(name="pc_tranformerReport")
@RequestScoped
public class TransformerReport extends PageCodeBase implements Serializable { 

	
	String role = "";
	String roleBelong = "";
	TransformerBean transformerBean = new TransformerBean();
	TransformerReportDao transformerReportDao = new TransformerReportDao();
	List<TransformerReportBean> transformerReportBeans = new ArrayList<TransformerReportBean>();
	List secList = new ArrayList();

	private int grTotAdd=0;
	private int grTotFail=0;
	private int grTotRepCnt=0;
	private int grTotMainCnt=0;
	
	
	public TransformerReport() {
		 	
       try {
    	   String concatWhere = "";
			final String queryWhere = "JOIN sub_division ON section.SECTN_SUBDIV_ID = sub_division.SUBDIV_ID "
					+ "JOIN division ON sub_division.SUBDIV_DIV_ID = division.DIV_ID "
					+ "JOIN circle ON division.DIV_CIRCLE_ID = circle.CRCL_ID "
					+ "JOIN zone ON circle.CRCL_ZONE_ID = zone.ZONE_ID where ";
			final int idenFlag = Integer.valueOf(UserUtil.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION));
			if (idenFlag == 1) {
				concatWhere = "zone.ZONE_ID = "	+ UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+ " ";
			} else if (idenFlag == 2) {
				concatWhere = "circle.CRCL_ID = "+ UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+ " ";
			} else if (idenFlag == 3) {
				concatWhere = "division.DIV_ID = "+ UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+ " ";
			} else if (idenFlag == 4) {
				concatWhere = "sub_division.SUBDIV_ID = "+ UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+ " ";
			}
			final String whrClause = queryWhere.concat(concatWhere);

			secList = AppUtil.getDropDownList("section", "SECTN_NAME","SECTN_ID", "SECTN_NAME", whrClause, 0);
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	}
	
	
	public String getTransformerDetails()
	{
		if(validate()){
		transformerReportBeans = transformerReportDao.getTransformerDetails(transformerBean.getSecId(), transformerBean.getYearId(),transformerBean.getIdenFlag());
		totCount(transformerReportBeans);
		return "transformerDetails";
		}
		else{
			return "transformerFailure";
		}
	}

	
	 public boolean validate()
	  {
	  	boolean isValid = true;

			if (nullCheck(transformerBean.getSecId())) {
				addMessage("ERRCD537E");
				isValid = false;
				return isValid;
			}else if (nullCheck(transformerBean.getYearId())) {
				addMessage("ERRCD539E");
				isValid = false;
				return isValid;
			}else if (nullCheck(transformerBean.getIdenFlag())) {
				addMessage("ERRCDTRS005E");
				isValid = false;
				return isValid;
			}  
			return isValid;
	  }


	 public void totCount(List<TransformerReportBean> transformerReportBeans){
		 List<TransformerDetailsBean> lstDetailsBeans =new ArrayList<TransformerDetailsBean>();
		  grTotAdd=0;
		  grTotAdd=0;
		  grTotRepCnt=0;
		  grTotMainCnt=0;
		 for (TransformerReportBean bean : transformerReportBeans) { 
			
			     grTotAdd = bean.getGrandTotAdditionCnt();
			     grTotFail =bean.getGrandTotFailureCnt();
			     grTotRepCnt =bean.getGrandTotReplacedCnt();
			     grTotMainCnt =bean.getGrandTotMaintainceCnt();
			
		}
	 }
	 
	 
	
	public TransformerBean getTransformerBean() {
		return transformerBean;
	}



	public void setTransformerBean(TransformerBean transformerBean) {
		this.transformerBean = transformerBean;
	}



	public TransformerReportDao getTransformerReportDao() {
		return transformerReportDao;
	}



	public void setTransformerReportDao(TransformerReportDao transformerReportDao) {
		this.transformerReportDao = transformerReportDao;
	}


	public List getSecList() {
		return secList;
	}


	public void setSecList(List secList) {
		this.secList = secList;
	}


	public List<TransformerReportBean> getTransformerReportBeans() {
		return transformerReportBeans;
	}


	public void setTransformerReportBeans(
			List<TransformerReportBean> transformerReportBeans) {
		this.transformerReportBeans = transformerReportBeans;
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


	public int getGrTotAdd() {
		return grTotAdd;
	}


	public void setGrTotAdd(int grTotAdd) {
		this.grTotAdd = grTotAdd;
	}


	public int getGrTotFail() {
		return grTotFail;
	}


	public void setGrTotFail(int grTotFail) {
		this.grTotFail = grTotFail;
	}


	public int getGrTotRepCnt() {
		return grTotRepCnt;
	}


	public void setGrTotRepCnt(int grTotRepCnt) {
		this.grTotRepCnt = grTotRepCnt;
	}


	public int getGrTotMainCnt() {
		return grTotMainCnt;
	}


	public void setGrTotMainCnt(int grTotMainCnt) {
		this.grTotMainCnt = grTotMainCnt;
	}
	
}
