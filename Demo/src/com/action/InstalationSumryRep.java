package com.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.Dao.InstalationSumryRepDao;
import com.Util.AppUtil;
import com.Util.EnumMonth;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.InstalationBean;
import com.bean.StackBean;

@ManagedBean(name = "pc_instalationSumryRep")
@SessionScoped
public class InstalationSumryRep extends PageCodeBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	String role = UserUtil.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION);
	String roleBelong = UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION);
	
	String yearId = "";
	String monthId="";
	String header = "";
	List<StackBean> stackBeans = new ArrayList<StackBean>();
	boolean backbtn = false;
	InstalationSumryRepDao dao=new InstalationSumryRepDao();
	List<InstalationBean> instalationBeans = null;
	List<EnumMonth> enumMonths = new ArrayList<EnumMonth>();

	public InstalationSumryRep() {
		setHeaderString(role);
		enumMonths = Arrays.asList(EnumMonth.values());
	}

	public String getInstReport() {
		if(validate()){
		instalationBeans = dao.viewData(role, roleBelong, yearId,getMnthId(monthId));
		setHeaderString(role);
		stackBeans.clear();
		backbtn = false;
		}
		return "instalationSumryRep";
	}

	public String nextLevel(String role, String name, String id) {
		
		if (role.equals("1")) {
			header ="Circle";
			instalationBeans = dao.circleWise(id,role);
		} else if (role.equals("2")) {
			header ="Division";
			instalationBeans = dao.divWise(id,role);
		} else if (role.equals("3")) {
			header ="Sun Division";
			instalationBeans = dao.subDivWise(id,role);
		} else if (role.equals("4")) {
			header ="Section";
			instalationBeans = dao.secWise(id,role);
		} 

		if (Integer.parseInt(role) == 5) {
			instalationBeans = dao.secWise(id,role);
		}else{ 	
			backbtn = true;
			stackBeans.add(new StackBean(role, id, name, stackBeans.size() + 1));
			role = Integer.toString(Integer.parseInt(role) + 1);
			setHeaderString(role);
		}
		return "instalationSumryRep";
	}

	public String back(String role, String id, String slno) {
		
		if (role.equals("1")) {
			header ="Zone";
			instalationBeans = dao.zoneWise(id,role);
		} else if (role.equals("2")) {
			header ="Circle";
			instalationBeans = dao.circleWise(id,role);
		} else if (role.equals("3")) {
			header ="Division";
			instalationBeans = dao.divWise(id,role);
		} else if (role.equals("4")) {
			header ="Sub Division";
			instalationBeans = dao.subDivWise(id,role);
		}else if (role.equals("5")) {
			header ="Section";
			instalationBeans = dao.secWise(id,role);
		}  
		

		stackBeans = stackBeans.subList(0, Integer.parseInt(slno));

		if (stackBeans.size() == 0) {
			backbtn = false;
			setHeaderString(role);
			
		} else {
			backbtn = true;
			setHeaderString(stackBeans.get(stackBeans.size() - 1).getLvl());
			stackBeans.remove(stackBeans.size() - 1);
			if (stackBeans.size() == 0) {
				backbtn = false;
			}
		}
		return "instalationSumryRep";
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
		boolean isValid =true;
		  
		if(nullCheck(AppUtil.getMnthId(monthId))){
			addMessage("ERRCD538E");
			isValid =true;
			return isValid;
		}else if(nullCheck(yearId)){
			addMessage("ERRCD539E");
			isValid =true;
			return isValid;
		}
		return isValid;
	}

	public String getYearId() {
		return yearId;
	}

	public void setYearId(String yearId) {
		this.yearId = yearId;
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

	public List<StackBean> getStackBeans() {
		return stackBeans;
	}

	public void setStackBeans(List<StackBean> stackBeans) {
		this.stackBeans = stackBeans;
	}

	public List<EnumMonth> getEnumMonths() {
		return enumMonths;
	}
   
	public List<InstalationBean> getInstalationBeans() {
		return instalationBeans;
	}

	public void setInstalationBeans(List<InstalationBean> instalationBeans) {
		this.instalationBeans = instalationBeans;
	}

	public void setEnumMonths(List<EnumMonth> enumMonths) {
		this.enumMonths = enumMonths;
	}
	
	public String getMonthId() {
		return monthId;
	}

	public void setMonthId(String monthId) {
		this.monthId = monthId;
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
