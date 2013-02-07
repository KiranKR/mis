package com.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.Dao.SummaryZeroConsumptionRepDao;
import com.Util.AppUtil;
import com.Util.EnumMonth;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.DiscrepancyBean;
import com.bean.StackBean;
import com.bean.ZeroConsumptionBean;

@ManagedBean(name = "pc_summarZeroConsumRep")
@SessionScoped
public class SummaryZeroConsumRep extends PageCodeBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private SummaryZeroConsumptionRepDao summaryZeroConsumRepDao = new SummaryZeroConsumptionRepDao();
	private ZeroConsumptionBean zeroConsumBean = new ZeroConsumptionBean();
	private List<ZeroConsumptionBean> dispLstZCbean = new ArrayList<ZeroConsumptionBean>();
	private List<DiscrepancyBean> lsrDispBeans = new ArrayList<DiscrepancyBean>();
	
	private List<EnumMonth> enumMonthlist = new ArrayList<EnumMonth>();
	private List<StackBean> breadCrumb = new ArrayList<StackBean>();
	
	boolean backbtn = false;
	String header ="";
	String role = UserUtil.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION);
	String roleBelong = UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION);
	
	String frYrMnt = "";
	String toYrMnt ="";
	String frMnt ="";
	String toMnt ="";
	String errorMsg ="";
	
	public SummaryZeroConsumRep() {
		enumMonthlist = Arrays.asList(EnumMonth.values());
	}


	public void dateConvertor(){
		String fr = AppUtil.getMnthId(zeroConsumBean.getMonthId());
		String to = AppUtil.getMnthId(zeroConsumBean.getToMonthId());

		System.out.println(fr.charAt(0));
		System.out.println(to.charAt(0));

		if(Integer.parseInt(fr)<10){
		frMnt = fr.substring(1);
		}else{
		frMnt = fr;
		}

		if(Integer.parseInt(to)<10){
		toMnt =to.substring(1);
		}else{
		toMnt =to;
		}


		String frYear =zeroConsumBean.getYearId();
		String toYear =zeroConsumBean.getToYear();


		frYrMnt = frYear+frMnt;
		toYrMnt = toYear+toMnt;
	}
	
	public String getZeroConsum() {
		dispLstZCbean=null;
		header="";
		if (validate()) {
			dateConvertor();
			if(Integer.parseInt(role)==4){
				role = Integer.toString(Integer.parseInt(role) + 1);
			}
				dispLstZCbean = summaryZeroConsumRepDao.getZeroConRep(frYrMnt, toYrMnt, role, roleBelong);
				setHeaderString(role);
		}
			backbtn = false;
			breadCrumb.clear();
			return "sumZCSuces";
		
	}

	public String drillDown(String role, String roleBngs, String dispNme, String idCurrent){
		if (validate()) {
				if(Integer.parseInt(roleBngs) != 0){
					roleBngs =Integer.toString(Integer.parseInt(roleBngs));
				}
				if(Integer.parseInt(role) != 5){
					dispLstZCbean=null;
					
					breadCrumb.add(new StackBean(role, idCurrent, dispNme, breadCrumb.size()+1));
					role = Integer.toString(Integer.parseInt(role) + 1);
					setHeaderString(role);
					dispLstZCbean = summaryZeroConsumRepDao.getZeroConRep(frYrMnt, toYrMnt, role, roleBngs);
						if(dispLstZCbean.size() !=0){
							backbtn = true;
							return "sumZCSuces";
						}
				}
			}
			return "sumZCSuces";
		}
	
	public String back(String role, String idCurrent,String slno){
		breadCrumb = breadCrumb.subList(0, Integer.valueOf(slno));
		
		if(Integer.parseInt(idCurrent) != 0){
			idCurrent =Integer.toString(Integer.parseInt(idCurrent));
		}
		if (breadCrumb.size() == 0) {
			dispLstZCbean=null;
			backbtn = false;
			setHeaderString(role);
			dispLstZCbean = summaryZeroConsumRepDao.getZeroConRep(frYrMnt, toYrMnt, role, idCurrent);
		} else {
			dispLstZCbean=null;
			backbtn = true;
			setHeaderString(breadCrumb.get(breadCrumb.size() - 1).getLvl());
			dispLstZCbean = summaryZeroConsumRepDao.getZeroConRep(frYrMnt, toYrMnt, role, idCurrent);
			breadCrumb.remove(breadCrumb.size() - 1);
			if (breadCrumb.size() == 0) {
				
				backbtn = false;
			}
		}
		return "sumZCSuces";
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

	public String cancelOk() {
		clearSession();
		return "cancel";
	}

	public boolean validate() {
		boolean isValid = true;

		if (nullCheck(zeroConsumBean.getMonthId())) {
			addMessage("ERRCDALLSUM002E");
			isValid = false;
			return isValid;
		} else if (nullCheck(zeroConsumBean.getYearId())) {
			addMessage("ERRCDALLSUM001E");
			isValid = false;
			return isValid;

		}else if (nullCheck(zeroConsumBean.getToMonthId())) {
			addMessage("ERRCDALLSUM004E");
			isValid = false;
			return isValid;
		} else if (nullCheck(zeroConsumBean.getToYear())) {
			addMessage("ERRCDALLSUM003E");
			isValid = false;
			return isValid;

		}else if(fromDateLessToDate(zeroConsumBean.getYearId(),zeroConsumBean.getMonthId(),zeroConsumBean.getToYear(),zeroConsumBean.getToMonthId())) {
			addMessage("ERRCDALLSUM005E");
			isValid = false;
			return isValid;
		}
		return isValid;
	}

	

	private void clearSession() {
		Map sessionMap = (Map) getBindingValue("#{sessionScope}");
		sessionMap.remove("pc_zeroConsumRep");
	}

	
	
	

	public String getErrorMsg() {
		return errorMsg;
	}


	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}


	public String getFrYrMnt() {
		return frYrMnt;
	}


	public void setFrYrMnt(String frYrMnt) {
		this.frYrMnt = frYrMnt;
	}


	public String getToYrMnt() {
		return toYrMnt;
	}


	public void setToYrMnt(String toYrMnt) {
		this.toYrMnt = toYrMnt;
	}


	public String getFrMnt() {
		return frMnt;
	}


	public void setFrMnt(String frMnt) {
		this.frMnt = frMnt;
	}


	public String getToMnt() {
		return toMnt;
	}


	public void setToMnt(String toMnt) {
		this.toMnt = toMnt;
	}


	public List<ZeroConsumptionBean> getDispLstZCbean() {
		return dispLstZCbean;
	}

	public void setDispLstZCbean(List<ZeroConsumptionBean> dispLstZCbean) {
		this.dispLstZCbean = dispLstZCbean;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public List<DiscrepancyBean> getLsrDispBeans() {
		return lsrDispBeans;
	}

	public void setLsrDispBeans(List<DiscrepancyBean> lsrDispBeans) {
		this.lsrDispBeans = lsrDispBeans;
	}

	public List<StackBean> getBreadCrumb() {
		return breadCrumb;
	}

	public void setBreadCrumb(List<StackBean> breadCrumb) {
		this.breadCrumb = breadCrumb;
	}

	public boolean isBackbtn() {
		return backbtn;
	}

	public void setBackbtn(boolean backbtn) {
		this.backbtn = backbtn;
	}

	public List<EnumMonth> getEnumMonthlist() {
		return enumMonthlist;
	}

	public void setEnumMonthlist(List<EnumMonth> enumMonthlist) {
		this.enumMonthlist = enumMonthlist;
	}

	public List<ZeroConsumptionBean> getLstZCbean() {
		return dispLstZCbean;
	}

	public void setLstZCbean(List<ZeroConsumptionBean> lstZCbean) {
		this.dispLstZCbean = lstZCbean;
	}

	public ZeroConsumptionBean getZeroConsumBean() {
		return zeroConsumBean;
	}

	public void setZeroConsumBean(ZeroConsumptionBean zeroConsumBean) {
		this.zeroConsumBean = zeroConsumBean;
	}

}
