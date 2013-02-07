package com.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.TariffDCASumryReportDao;
import com.Util.EnumMonth;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.StackBean;
import com.bean.TarifDetailsSumryBean;

@ManagedBean(name = "pc_tariffDCASumryReport")
@SessionScoped
public class TariffDCASumryReport extends PageCodeBase implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fromMonthID;
	private String fromYearID;
	private String toMonthID;
	private String toYearId;
	private String whrClause = "";
	String header = "";
	List<EnumMonth> enumMonths = new ArrayList<EnumMonth>();
	TariffDCASumryReportDao dao = new TariffDCASumryReportDao();
	List<TarifDetailsSumryBean> sumryBeans = new ArrayList<TarifDetailsSumryBean>();
	List<StackBean> stackBeans = new ArrayList<StackBean>();
	String role = UserUtil.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION);
	String roleBelong = UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION);

	public TariffDCASumryReport() {
		enumMonths = Arrays.asList(EnumMonth.values());
	}

	public String getTarifDetails() {
		if(validate()){
		stackBeans.clear();
		role = UserUtil.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION);
		setHeaderString(role);
		if (role.equals("1")) {
			whrClause = " and z.ZONE_ID = " + roleBelong;
		} else if (role.equals("2")) {
			whrClause = " and c.CRCL_ID = " + roleBelong;
		} else if (role.equals("3")) {
			whrClause = " and d.DIV_ID = " + roleBelong;
		} else if (role.equals("4")) {
			whrClause = " and sd.SUBDIV_ID = " + roleBelong;
		} else if (role.equals("5")) {
			whrClause = " and s.SECTN_ID = " + roleBelong;
		}
		stackBeans.add(new StackBean(role, roleBelong, "", stackBeans.size() + 1));
		sumryBeans = dao.getTarifViewDetails(fromYearID + getMnthId(fromMonthID), toYearId + getMnthId(toMonthID), role, whrClause);
		}   
		return "tarifDCASumryReport";

	}

	public String nextLvL(String id, String name) {
		if (role.equals("1")) {
			whrClause = " and z.ZONE_ID = " + id;
		} else if (role.equals("2")) {
			whrClause = " and c.CRCL_ID = " + id;
		} else if (role.equals("3")) {
			whrClause = " and d.DIV_ID = " + id;
		} else if (role.equals("4")) {
			whrClause = " and sd.SUBDIV_ID = " + id;
		} else if (role.equals("5")) {
			whrClause = " and s.SECTN_ID = " + id;
		}
		role = (Integer.parseInt(role) + 1) + "";
		setHeaderString(role);
		if (Integer.parseInt(role) <= 5) {
			stackBeans.add(new StackBean(role, id, name, stackBeans.size() + 1));
			sumryBeans = dao.getTarifViewDetails(fromYearID + getMnthId(fromMonthID), toYearId + getMnthId(toMonthID), role, whrClause);
		}
		return "tarifDCASumryReport";
	}

	public String back(String lvl, String id, String slno) {
		StackBean bean = new StackBean();
		if (Integer.parseInt(slno) > 1) {
			bean = stackBeans.get(Integer.parseInt(slno) - 2);
			stackBeans = stackBeans.subList(0, Integer.parseInt(slno) - 1);
			if (bean.getLvl().equals("1")) {
				whrClause = " and z.ZONE_ID = " + id;
			} else if (bean.getLvl().equals("2")) {
				whrClause = " and c.CRCL_ID = " + id;
			} else if (bean.getLvl().equals("3")) {
				whrClause = " and d.DIV_ID = " + id;
			} else if (bean.getLvl().equals("4")) {
				whrClause = " and sd.SUBDIV_ID = " + id;
			} else if (bean.getLvl().equals("5")) {
				whrClause = " and s.SECTN_ID = " + id;
			}
			role = bean.getLvl();
			setHeaderString(role);
			sumryBeans = dao.getTarifViewDetails(fromYearID + getMnthId(fromMonthID), toYearId + getMnthId(toMonthID), role, whrClause);
		}

		return "tarifDCASumryReport";

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
	
	
	



public boolean validate(){
boolean isValid = true;
    
	
    
 	if(nullCheck(fromMonthID)) {
	addMessage("ERRCDALLSUM002E");
	isValid = false;
 	return isValid;
 }else if (nullCheck(fromYearID)) {
		addMessage("ERRCDALLSUM001E");
		isValid = false;
		return isValid;
	}else if(nullCheck(toMonthID)) {
		addMessage("ERRCDALLSUM004E");
		isValid = false;
		return isValid;
	}else if(nullCheck(toYearId)) {
		addMessage("ERRCDALLSUM003E");
		isValid = false;
		return isValid;
	}else if(fromDateLessToDate(fromYearID,fromMonthID,toYearId,toMonthID)) {
		addMessage("ERRCDALLSUM005E");
		isValid = false;
		return isValid;
	} 
	return isValid;
}


	public String getFromMonthID() {
		return fromMonthID;
	}

	public void setFromMonthID(String fromMonthID) {
		this.fromMonthID = fromMonthID;
	}

	public String getFromYearID() {
		return fromYearID;
	}

	public void setFromYearID(String fromYearID) {
		this.fromYearID = fromYearID;
	}

	public String getToMonthID() {
		return toMonthID;
	}

	public void setToMonthID(String toMonthID) {
		this.toMonthID = toMonthID;
	}

	public String getToYearId() {
		return toYearId;
	}

	public void setToYearId(String toYearId) {
		this.toYearId = toYearId;
	}

	public List<EnumMonth> getEnumMonths() {
		return enumMonths;
	}

	public void setEnumMonths(List<EnumMonth> enumMonths) {
		this.enumMonths = enumMonths;
	}

	public List<TarifDetailsSumryBean> getSumryBeans() {
		return sumryBeans;
	}

	public void setSumryBeans(List<TarifDetailsSumryBean> sumryBeans) {
		this.sumryBeans = sumryBeans;
	}

	public List<StackBean> getStackBeans() {
		return stackBeans;
	}

	public void setStackBeans(List<StackBean> stackBeans) {
		this.stackBeans = stackBeans;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public static String getMnthId(String month) {
		if (month.equals("JANUARY")) {
			return "1";
		} else if (month.equals("FEBRUARY")) {
			return "2";
		} else if (month.equals("MARCH")) {
			return "3";
		} else if (month.equals("APRIL")) {
			return "4";
		} else if (month.equals("MAY")) {
			return "5";
		} else if (month.equals("JUNE")) {
			return "6";
		} else if (month.equals("JULY")) {
			return "7";
		} else if (month.equals("AUGUST")) {
			return "8";
		} else if (month.equals("SEPTEMBER")) {
			return "9";
		} else if (month.equals("OCTOBER")) {
			return "10";
		} else if (month.equals("NOVEMBER")) {
			return "11";
		} else if (month.equals("DECEMBER")) {
			return "12";
		}
		return "";
	}
}
