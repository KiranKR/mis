package com.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.New;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.SummaryRepFeederInterruptionDao;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.FeederInterruptionReportMainBean;
import com.bean.FeederInterruptionReportsBean;
import com.bean.StackBean;

@ManagedBean(name = "pc_SummaryRepFeederInterruptionReports")
@SessionScoped
public class SummaryRepFeederInterruption extends PageCodeBase implements
		Serializable {

	private static final long serialVersionUID = 1L;

	SummaryRepFeederInterruptionDao sumRepFedIntrptionDao = new SummaryRepFeederInterruptionDao();
	
	private List<FeederInterruptionReportMainBean> dispLstFedIntrReportMainBeans = new ArrayList<FeederInterruptionReportMainBean>();
	public List<FeederInterruptionReportsBean> lstFedintrptionRep  = new ArrayList<FeederInterruptionReportsBean>();

	private FeederInterruptionReportsBean fedIntrptionRepBean = new FeederInterruptionReportsBean();
	private FeederInterruptionReportMainBean fedrIntrptionRepMainBean = new FeederInterruptionReportMainBean();
	int count = 0;
	boolean backbtn = false;
	
	String role = UserUtil.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION);
	String roleBelong = UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION);
	private List<StackBean> breadCrumb = new ArrayList<StackBean>();
	
	
	String header = "";
	
	public SummaryRepFeederInterruption() {
		setHeaderString(role);
	}

	public String fdrReport() {
 		lstFedintrptionRep=null;
		dispLstFedIntrReportMainBeans=null;
		header="";
		if (validate()) {
				lstFedintrptionRep = sumRepFedIntrptionDao.getFeederReportDetails(role, roleBelong, fedIntrptionRepBean.getYear());
				if(lstFedintrptionRep.size() != 0){
					dispLstFedIntrReportMainBeans = sumRepFedIntrptionDao.drillDown(lstFedintrptionRep, role);
					setHeaderString(role);
				}
		}
			
			 backbtn = false;
			 breadCrumb.clear();
			return "sumFedInterRep";
	}

	public String nextLevel(String role, String dispNme, String ids) {
		dispLstFedIntrReportMainBeans = null;
		int id = Integer.parseInt(ids);
		List<FeederInterruptionReportsBean> lstFedRep = new ArrayList<FeederInterruptionReportsBean>();
		if ("1".equals(role)) {
			for (FeederInterruptionReportsBean fedIntrBean : lstFedintrptionRep) {
				if (id==fedIntrBean.getZoneId()) {
					lstFedRep.add(fedIntrBean);
				}
			}
		} else if ("2".equals(role)) {
			for (FeederInterruptionReportsBean fedIntrBean : lstFedintrptionRep) {
				if (id == fedIntrBean.getCircleId()) {
					lstFedRep.add(fedIntrBean);
				}
			}
		} else if ("3".equals(role)) {
			for (FeederInterruptionReportsBean fedIntrBean : lstFedintrptionRep) {
				if (id == fedIntrBean.getDivId()) {
					lstFedRep.add(fedIntrBean);
				}
			}
		} else if ("4".equals(role)) {
			for (FeederInterruptionReportsBean fedIntrBean : lstFedintrptionRep) {
				if (id==fedIntrBean.getSubDivId()) {
					lstFedRep.add(fedIntrBean);
				}
			}
		} else if ("5".equals(role)) {
			for (FeederInterruptionReportsBean fedIntrBean : lstFedintrptionRep) {
				if (id == fedIntrBean.getSectionId()) {
					lstFedRep.add(fedIntrBean);
				}
			}
		}
		
		
		
		if(Integer.parseInt(role) == 5){
			dispLstFedIntrReportMainBeans = sumRepFedIntrptionDao.drillDown(lstFedRep, role);
		}else{
			backbtn = true;
			breadCrumb.add(new StackBean(role, ids, dispNme, breadCrumb.size()+1));
			role = Integer.toString(Integer.parseInt(role) + 1);
			setHeaderString(role);
			dispLstFedIntrReportMainBeans = sumRepFedIntrptionDao.drillDown(lstFedRep, role);
		}
		return "sumFedInterRep";
	}
	
	public String back(String role, String ids, String slno) {
		dispLstFedIntrReportMainBeans=null;
		int id = Integer.parseInt(ids);
		List<FeederInterruptionReportsBean> lstFedRep = new ArrayList<FeederInterruptionReportsBean>();
		if ("1".equals(role)) {
			for (FeederInterruptionReportsBean fedIntrBean : lstFedintrptionRep) {
				if (id==fedIntrBean.getZoneId()) {
					lstFedRep.add(fedIntrBean);
				}
			}
		} else if ("2".equals(role)) {
			for (FeederInterruptionReportsBean fedIntrBean : lstFedintrptionRep) {
				if (id == fedIntrBean.getCircleId()) {
					lstFedRep.add(fedIntrBean);
				}
			}
		} else if ("3".equals(role)) {
			for (FeederInterruptionReportsBean fedIntrBean : lstFedintrptionRep) {
				if (id == fedIntrBean.getDivId()) {
					lstFedRep.add(fedIntrBean);
				}
			}
		} else if ("4".equals(role)) {
			for (FeederInterruptionReportsBean fedIntrBean : lstFedintrptionRep) {
				if (id==fedIntrBean.getSubDivId()) {
					lstFedRep.add(fedIntrBean);
				}
			}
		} else if ("5".equals(role)) {
			for (FeederInterruptionReportsBean fedIntrBean : lstFedintrptionRep) {
				if (id == fedIntrBean.getSectionId()) {
					lstFedRep.add(fedIntrBean);
				}
			}
		}
		
		
		breadCrumb = breadCrumb.subList(0, Integer.valueOf(slno));

		if (breadCrumb.size() == 0) {
			backbtn = false;
			setHeaderString(role);
			dispLstFedIntrReportMainBeans = sumRepFedIntrptionDao.drillDown(lstFedRep, role);
		} else {
			backbtn = true;
			setHeaderString(breadCrumb.get(breadCrumb.size() - 1).getLvl());
			dispLstFedIntrReportMainBeans = sumRepFedIntrptionDao.drillDown(lstFedRep, breadCrumb.get(breadCrumb.size() - 1).getLvl());
			breadCrumb.remove(breadCrumb.size() - 1);
			if (breadCrumb.size() == 0) {
				
				backbtn = false;
			}
		}
		
		/*int roleInt = Integer.parseInt(role);
		if (breadCrumb.size() == 0) {
			backbtn = false;
			dispLstFedIntrReportMainBeans = sumRepFedIntrptionDao.drillDown(lstFedintrptionRep,role);
		} else {
			backbtn = true;
			dispLstFedIntrReportMainBeans = sumRepFedIntrptionDao.drillDown(lstFedintrptionRep,role);
			if("4".equals(role)){
				roleInt=1;
			}else if("5".equals(role)){
				roleInt=0;
			}
			for (int i = breadCrumb.size(); i >= roleInt; i--) {
					breadCrumb.remove(i-1);
			}
			if (breadCrumb.size() == 0) {
				backbtn = false;
			}
		}*/
		
		
		
		/*if (stackBeans.size() == 0) {
			backbtn = false;
			dispLstFedIntrReportMainBeans = sumRepFedIntrptionDao.drillDown(lstFedintrptionRep,role);
		} else {
			backbtn = true;
			dispLstFedIntrReportMainBeans = sumRepFedIntrptionDao.drillDown(lstFedintrptionRep,stackBeans.get(stackBeans.size() - 1).getLvl());
			stackBeans.remove(stackBeans.size() - 1);
			if (stackBeans.size() == 0) {
				backbtn = false;
			}
		}*/
		return "sumFedInterRep";
	}

	private void setHeaderString(String pRole) {
		if (pRole.equals("1")) {
			header = "Zone";
		} else if (pRole.equals("2")) {
			header = "Circle";
		} else if (pRole.equals("3")) {
			header = "Division";
		} else if (pRole.equals("4")) {
			header = "SubDivision";
		} else if (pRole.equals("5")) {
			header = "Section";
		}
	}
	
	public boolean validate() {
		boolean isValid = true;

		if (nullCheck(fedIntrptionRepBean.getYear())) {
			addMessage("ERRCD539E");
			isValid = false;
			return isValid;

		}
		return isValid;
	}

	
	


	public List<StackBean> getBreadCrumb() {
		return breadCrumb;
	}

	public void setBreadCrumb(List<StackBean> breadCrumb) {
		this.breadCrumb = breadCrumb;
	}
	public List<FeederInterruptionReportMainBean> getDispLstFedIntrReportMainBeans() {
		return dispLstFedIntrReportMainBeans;
	}

	public void setDispLstFedIntrReportMainBeans(
			List<FeederInterruptionReportMainBean> dispLstFedIntrReportMainBeans) {
		this.dispLstFedIntrReportMainBeans = dispLstFedIntrReportMainBeans;
	}
	public FeederInterruptionReportsBean getFedIntrptionRepBean() {
		return fedIntrptionRepBean;
	}

	public void setFedIntrptionRepBean(
			FeederInterruptionReportsBean fedIntrptionRepBean) {
		this.fedIntrptionRepBean = fedIntrptionRepBean;
	}

	public FeederInterruptionReportMainBean getFedrIntrptionRepMainBean() {
		return fedrIntrptionRepMainBean;
	}

	public void setFedrIntrptionRepMainBean(
			FeederInterruptionReportMainBean fedrIntrptionRepMainBean) {
		this.fedrIntrptionRepMainBean = fedrIntrptionRepMainBean;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isBackbtn() {
		return backbtn;
	}

	public void setBackbtn(boolean backbtn) {
		this.backbtn = backbtn;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
}
