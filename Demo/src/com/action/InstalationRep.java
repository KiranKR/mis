package com.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.InstalationRepDao;
import com.Util.AppUtil;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.InstalationBean;
import com.bean.InstallationMnthWiseBean;
import com.bean.StackBean;

@ManagedBean(name = "pc_instalationRep")
@SessionScoped
public class InstalationRep extends PageCodeBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// public class SchemeWiseRep {
	InstalationRepDao dao = new InstalationRepDao();
	List<InstalationBean> instalationBeans = null;
	List<InstallationMnthWiseBean> dispInstallationMnthWiseBeans = new ArrayList<InstallationMnthWiseBean>();
	String role = UserUtil.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION);
	String roleBelong = UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION);
	String yearId = "";
	String header = "";
	List<StackBean> stackBeans = new ArrayList<StackBean>();
	boolean backbtn = false;

	public InstalationRep() {
		setHeaderString(role);
	}

	public String getInstReport() {
		if(validate()){
		instalationBeans = dao.viewData(role, roleBelong, yearId);
		dispInstallationMnthWiseBeans = dao.monthWise(instalationBeans, role);
		setHeaderString(role);
		stackBeans.clear();
		backbtn = false;
		}
		return "instalationRep";
	}

	public String nextLevel(String role, String name, String id) {
		dispInstallationMnthWiseBeans = null;
		List<InstalationBean> pInstalationBeans = new ArrayList<InstalationBean>();
		if (role.equals("1")) {
			for (InstalationBean instalationBean : instalationBeans) {
				if (instalationBean.getZnId().equals(id)) {
					pInstalationBeans.add(instalationBean);
				}
			}
		} else if (role.equals("2")) {
			for (InstalationBean instalationBean : instalationBeans) {
				if (instalationBean.getCrlId().equals(id)) {
					pInstalationBeans.add(instalationBean);
				}
			}
		} else if (role.equals("3")) {
			for (InstalationBean instalationBean : instalationBeans) {
				if (instalationBean.getDivId().equals(id)) {
					pInstalationBeans.add(instalationBean);
				}
			}
		} else if (role.equals("4")) {
			for (InstalationBean instalationBean : instalationBeans) {
				if (instalationBean.getSubDivId().equals(id)) {
					pInstalationBeans.add(instalationBean);
				}
			}
		} else if (role.equals("5")) {
			for (InstalationBean instalationBean : instalationBeans) {
				if (instalationBean.getSecId().equals(id)) {
					pInstalationBeans.add(instalationBean);
				}
			}
		}

		if (Integer.parseInt(role) == 5) {
			dispInstallationMnthWiseBeans = dao.monthWise(pInstalationBeans, role);
		} else {
			backbtn = true;
			stackBeans.add(new StackBean(role, id, name, stackBeans.size() + 1));
			role = Integer.toString(Integer.parseInt(role) + 1);
			setHeaderString(role);
			dispInstallationMnthWiseBeans = dao.monthWise(pInstalationBeans, role);

		}
		return "instalationRep";
	}

	public String back(String role, String id, String slno) {
		dispInstallationMnthWiseBeans = null;
		List<InstalationBean> pInstalationBeans = new ArrayList<InstalationBean>();
		if (role.equals("1")) {
			for (InstalationBean instalationBean : instalationBeans) {
				if (instalationBean.getZnId().equals(id)) {
					pInstalationBeans.add(instalationBean);
				}
			}
		} else if (role.equals("2")) {
			for (InstalationBean instalationBean : instalationBeans) {
				if (instalationBean.getCrlId().equals(id)) {
					pInstalationBeans.add(instalationBean);
				}
			}
		} else if (role.equals("3")) {
			for (InstalationBean instalationBean : instalationBeans) {
				if (instalationBean.getDivId().equals(id)) {
					pInstalationBeans.add(instalationBean);
				}
			}
		} else if (role.equals("4")) {
			for (InstalationBean instalationBean : instalationBeans) {
				if (instalationBean.getSubDivId().equals(id)) {
					pInstalationBeans.add(instalationBean);
				}
			}
		} else if (role.equals("5")) {
			for (InstalationBean instalationBean : instalationBeans) {
				if (instalationBean.getSecId().equals(id)) {
					pInstalationBeans.add(instalationBean);
				}
			}
		}

		stackBeans = stackBeans.subList(0, Integer.parseInt(slno));

		if (stackBeans.size() == 0) {
			backbtn = false;
			setHeaderString(role);
			dispInstallationMnthWiseBeans = dao.monthWise(pInstalationBeans, role);
		} else {
			backbtn = true;
			setHeaderString(stackBeans.get(stackBeans.size() - 1).getLvl());
			dispInstallationMnthWiseBeans = dao.monthWise(pInstalationBeans, stackBeans.get(stackBeans.size() - 1).getLvl());
			stackBeans.remove(stackBeans.size() - 1);
			if (stackBeans.size() == 0) {
				backbtn = false;
			}
		}
		return "instalationRep";
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
		}
	}
	
	public boolean validate(){
		boolean isValid =true;
		
		if(nullCheck(yearId)){
			addMessage("ERRCD539E");
			isValid =false;
			return isValid;
		}
		return isValid;
	}
	
	
	public List<InstallationMnthWiseBean> getDispInstallationMnthWiseBeans() {
		return dispInstallationMnthWiseBeans;
	}

	public void setDispInstallationMnthWiseBeans(
			List<InstallationMnthWiseBean> dispInstallationMnthWiseBeans) {
		this.dispInstallationMnthWiseBeans = dispInstallationMnthWiseBeans;
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

}
