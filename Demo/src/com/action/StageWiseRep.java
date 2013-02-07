package com.action;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.StageWiseRepDao;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.ReportBean;

@ManagedBean(name = "pc_stageWiseRep")
@SessionScoped
public class StageWiseRep extends PageCodeBase implements Serializable {
     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//	public class SchemeWiseRep {
	 StageWiseRepDao dao = new StageWiseRepDao();
	 List<ReportBean> reportBeans = new ArrayList<ReportBean>();
	 List<ReportBean> dispReportBeans = new ArrayList<ReportBean>();
	 
	 
	 List subSchlst = new ArrayList();
	 List znlst = new ArrayList();
	 List cirlst = new ArrayList();
	 List divlst = new ArrayList();
	 List subdivlst = new ArrayList();
	 List seclst = new ArrayList();
	 
	 
	 ReportBean tempBean = null;
	 String role = UserUtil.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION);
	 String roleBelong = UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION);
	 String znId = "0";
	 String crlId = "0";
	 String divId = "0";
	 String subDivId = "0";
	 String secId = "0";
	 
	 String znNm = "";
	 String crlNm = "";
	 String divNm = "";
	 String subDivNm = "";
	 String secNm = "";
	 
	 boolean dZnNm = false;
	 boolean dCrlNm = false;
	 boolean dDivNm = false;
	 boolean dSubDivNm = false;
	 boolean dSecNm = false;
	 
	 
	 String casteId = "0";
	 String stageId = "0";
	 
	 
	 public StageWiseRep() {
		 try {
			subSchlst = dao.getDropDownList("caste_category", "CSTCTG_NAME", "CSTCTG_ID", "CSTCTG_NAME", "", 0);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		reportBeans = dao.viewData(role,roleBelong);
		
		tempBean = reportBeans.get(0);
		
		if(role.equals("1")){
			znId = roleBelong;
			znNm = tempBean.getZoneName();
			dCrlNm = true;
			dDivNm = true;
			dSubDivNm = true;
			dSecNm = true;
			getCircleDropDown();
			
		}else if(role.equals("2")){
			crlId = roleBelong;
			znId = tempBean.getZoneId();
			znNm = tempBean.getZoneName();
			crlNm = tempBean.getCircleName();
			dDivNm = true;
			dSubDivNm = true;
			dSecNm = true;
			getDivDropDown();
			
		}else if(role.equals("3")){
			divId = roleBelong;
			znId = tempBean.getZoneId();
			crlId = tempBean.getCircleId();
			znNm = tempBean.getZoneName();
			crlNm = tempBean.getCircleName();
			divNm = tempBean.getDivName();
			dSubDivNm = true;
			dSecNm = true;
			getSubDivDropDown();
			
		}else if(role.equals("4")){
			subDivId = roleBelong;
			divId = tempBean.getDivId();
			znId = tempBean.getZoneId();
			crlId = tempBean.getCircleId();
			znNm = tempBean.getZoneName();
			crlNm = tempBean.getCircleName();
			divNm = tempBean.getDivName();
			subDivNm = tempBean.getSudDivName(); 
			dSecNm = true;
			getSecDropDown();
		}
		dispReportBeans = reportBeans;
	}
	
	
	public void getZoneDropDown() {
		cirlst = null;
		divlst = null;
		subdivlst = null;
		seclst = null;
		znId = "0";
		try {
			znlst = dao.getDropDownList("zone", "ZONE_NAME", "ZONE_ID", "ZONE_NAME", "", 1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mainFilter();
	}

	public void getCircleDropDown() {
		divlst = null;
		subdivlst = null;
		crlId = "0";
		
		try {
			if(!znId.equals("0")){
				cirlst = dao.getDropDownList("circle", "CRCL_NAME", "CRCL_ID", "CRCL_NAME", "where CRCL_ZONE_ID = "+ znId, 1);
			}else{
				cirlst = null;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mainFilter();
	}

	public void getDivDropDown() {
		subdivlst = null;
		seclst = null;
		divId = "0";
		try {
			if(!crlId.equals("0")){
				divlst = dao.getDropDownList("division", "DIV_NAME", "DIV_ID", "DIV_NAME", "where DIV_CIRCLE_ID = "+ crlId, 1);
			}else{
				divlst = null;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mainFilter();
	}

	public void getSubDivDropDown() {
		seclst = null;
		subDivId = "0";
		try {
			if(!divId.equals("0")){
				subdivlst = dao.getDropDownList("sub_division", "SUBDIV_NAME", "SUBDIV_ID", "SUBDIV_NAME", "where SUBDIV_DIV_ID = "+ divId, 1);
			}else{
				subdivlst = null;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mainFilter();
	}

	public void getSecDropDown() {
		secId = "0";
		try {
			if(!subDivId.equals("0")){
				seclst = dao.getDropDownList("section", "SECTN_NAME", "SECTN_ID", "SECTN_NAME", "where SECTN_SUBDIV_ID = "+ subDivId, 1);
			}else{
				seclst = null;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mainFilter();
	}


	public void mainFilter() {
		dispReportBeans = null;

		List<ReportBean> znWiseRepBean = new ArrayList<ReportBean>();
		if (!znId.equals("0")) {
			for (ReportBean reportBean : reportBeans) {
				if (reportBean.getZoneId().equals(znId)) {
					znWiseRepBean.add(reportBean);
				}
			}
		} else {
			for (ReportBean reportBean : reportBeans) {
				znWiseRepBean.add(reportBean);
			}
		}
		
		List<ReportBean> cirWiseRepBean = new ArrayList<ReportBean>();
		if (!crlId.equals("0")) {
			for (ReportBean reportBean : znWiseRepBean) {
				if (reportBean.getCircleId().equals(crlId)) {
					cirWiseRepBean.add(reportBean);
				}
			}
		} else {
			for (ReportBean reportBean : znWiseRepBean) {
				cirWiseRepBean.add(reportBean);
			}
		}

		List<ReportBean> divWiseRepBean = new ArrayList<ReportBean>();
		if (!divId.equals("0")) {

			for (ReportBean reportBean : cirWiseRepBean) {
				if (reportBean.getDivId().equals(divId)) {
					divWiseRepBean.add(reportBean);
				}
			}
		} else {
			for (ReportBean reportBean : cirWiseRepBean) {
				divWiseRepBean.add(reportBean);
			}
		}

		List<ReportBean> subDivWiseRepBean = new ArrayList<ReportBean>();
		if (!subDivId.equals("0")) {
			for (ReportBean reportBean : divWiseRepBean) {
				if (reportBean.getSudDivId().equals(subDivId)) {
					subDivWiseRepBean.add(reportBean);
				}
			}
		} else {
			for (ReportBean reportBean : divWiseRepBean) {
				subDivWiseRepBean.add(reportBean);
			}
		}

		List<ReportBean> secDivWiseRepBean = new ArrayList<ReportBean>();
		if (!secId.equals("0")) {
			for (ReportBean reportBean : subDivWiseRepBean) {
				if (reportBean.getSecId().equals(secId)) {
					secDivWiseRepBean.add(reportBean);
				}
			}
		} else {
			for (ReportBean reportBean : subDivWiseRepBean) {
				secDivWiseRepBean.add(reportBean);
			}
		}
		
		List<ReportBean> tReportBeans = new ArrayList<ReportBean>();
		
		if (stageId.equals("0")) {
			tReportBeans = secDivWiseRepBean;
		} else {
			for (ReportBean bean : secDivWiseRepBean) {
				if(stageId.equals("1")){
					if (Integer.parseInt(bean.getStageId()) < 4) {
						tReportBeans.add(bean);
					}
				}else if(stageId.equals("2")){
					if (Integer.parseInt(bean.getStageId()) == 4) {
						tReportBeans.add(bean);
					}
				}else if(stageId.equals("3")){
					if (Integer.parseInt(bean.getStageId()) == 5) {
						tReportBeans.add(bean);
					}
				}
				else if(stageId.equals("4")){
					if (Integer.parseInt(bean.getStageId()) == 6) {
						tReportBeans.add(bean);
					}
				}
			}
		}
		dispReportBeans = new ArrayList<ReportBean>();
		if (casteId.equals("0")) {
			dispReportBeans = tReportBeans;
		} else {
			for (ReportBean bean : tReportBeans) {
				if (bean.getCastId().equals(casteId)) {
					dispReportBeans.add(bean);
				}
			}
		}
	}
	
	
	
	public List<ReportBean> getReportBeans() {
		return reportBeans;
	}

	public void setReportBeans(List<ReportBean> reportBeans) {
		this.reportBeans = reportBeans;
	}

	public List<ReportBean> getDispReportBeans() {
		return dispReportBeans;
	}

	public void setDispReportBeans(List<ReportBean> dispReportBeans) {
		this.dispReportBeans = dispReportBeans;
	}

	public ReportBean getTempBean() {
		return tempBean;
	}

	public void setTempBean(ReportBean tempBean) {
		this.tempBean = tempBean;
	}

	public List getSubSchlst() {
		return subSchlst;
	}

	public void setSubSchlst(List subSchlst) {
		this.subSchlst = subSchlst;
	}

	public String getCasteId() {
		return casteId;
	}

	public void setCasteId(String casteId) {
		this.casteId = casteId;
	}

	public String getStageId() {
		return stageId;
	}

	public void setStageId(String stageId) {
		this.stageId = stageId;
	}

	public List getZnlst() {
		return znlst;
	}

	public void setZnlst(List znlst) {
		this.znlst = znlst;
	}

	public List getCirlst() {
		return cirlst;
	}

	public void setCirlst(List cirlst) {
		this.cirlst = cirlst;
	}

	public List getDivlst() {
		return divlst;
	}

	public void setDivlst(List divlst) {
		this.divlst = divlst;
	}

	public List getSubdivlst() {
		return subdivlst;
	}

	public void setSubdivlst(List subdivlst) {
		this.subdivlst = subdivlst;
	}

	public List getSeclst() {
		return seclst;
	}

	public void setSeclst(List seclst) {
		this.seclst = seclst;
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

	public String getZnNm() {
		return znNm;
	}

	public void setZnNm(String znNm) {
		this.znNm = znNm;
	}

	public String getCrlNm() {
		return crlNm;
	}

	public void setCrlNm(String crlNm) {
		this.crlNm = crlNm;
	}

	public String getDivNm() {
		return divNm;
	}

	public void setDivNm(String divNm) {
		this.divNm = divNm;
	}

	public String getSubDivNm() {
		return subDivNm;
	}

	public void setSubDivNm(String subDivNm) {
		this.subDivNm = subDivNm;
	}

	public String getSecNm() {
		return secNm;
	}

	public void setSecNm(String secNm) {
		this.secNm = secNm;
	}

	public boolean isdZnNm() {
		return dZnNm;
	}

	public void setdZnNm(boolean dZnNm) {
		this.dZnNm = dZnNm;
	}

	public boolean isdCrlNm() {
		return dCrlNm;
	}

	public void setdCrlNm(boolean dCrlNm) {
		this.dCrlNm = dCrlNm;
	}

	public boolean isdDivNm() {
		return dDivNm;
	}

	public void setdDivNm(boolean dDivNm) {
		this.dDivNm = dDivNm;
	}

	public boolean isdSubDivNm() {
		return dSubDivNm;
	}

	public void setdSubDivNm(boolean dSubDivNm) {
		this.dSubDivNm = dSubDivNm;
	}

	public boolean isdSecNm() {
		return dSecNm;
	}

	public void setdSecNm(boolean dSecNm) {
		this.dSecNm = dSecNm;
	}

}
