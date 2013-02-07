package com.ample.mis.loginCommon.login.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.TabChangeEvent;

import com.Util.AppUtil;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.ample.mis.loginCommon.login.bean.Menu;
import com.ample.mis.loginCommon.login.bean.User;
import com.ample.mis.loginCommon.login.bean.UserDetailsBean;
import com.ample.mis.loginCommon.login.delegate.LoginDelegate;

@ManagedBean(name="pc_Login")
@SessionScoped
public class Login extends PageCodeBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user = new User();
	private ArrayList<Menu> menus = new ArrayList<Menu>();
	
	@ManagedProperty(value = "#{loginDelegate}")
	LoginDelegate loginDelegate;
	
	
	private List<UserDetailsBean> lstUserDetailsBean= new ArrayList<UserDetailsBean>();
	private int actIndx;
	private int actIndxMenu;
	
	public Login() {
		super();
	}
	
	public String loginGuest(String role,String repID){
		if("2".equals(role)){
			user.setUserName("GK Guest");
			UserUtil.setSession(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION, "1");
			UserUtil.setSession(UserBeanConstants.LOGIN_ID_IN_SESSION, "3");
			return "olyReports";
		}else if("3".equals(role)){
			user.setUserName("BMAZ Guest");
			UserUtil.setSession(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION, "1");
			UserUtil.setSession(UserBeanConstants.LOGIN_ID_IN_SESSION, "1");
			return "olyBMAZReports";
		}
		return "noReports";
	}
	
	public String checkUser(){
		int count = 0;
		if(validate()){
				 try {
					User usr = loginDelegate.checkUser(user);
					
					if(usr!=null){
						UserUtil.setSession(UserBeanConstants.USER_ID_IN_SESSION, usr.getUserId());
						UserUtil.setSession(UserBeanConstants.USER_NAME_IN_SESSION, usr.getUserName());
						UserUtil.setSession(UserBeanConstants.LOGIN_ID_IN_SESSION, usr.getLoginId());
						UserUtil.setSession(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION, String.valueOf(usr.getuMapIdenFlag()));
						
						menus.clear();
						lstUserDetailsBean = loginDelegate.fetchUserDetails(Integer.parseInt(usr.getUserId()));
						if(lstUserDetailsBean != null){
							for (UserDetailsBean userDetailsBean : lstUserDetailsBean) {
								if(count == 0){
									count = 1;
									menus = userDetailsBean.getMenus();
									actIndx = userDetailsBean.getId();
								}
							}
						}

						return "loginSuccess";
						}else{
							addMessage("ERRCD526E");
							user.setUserName("");
							user.setPassword("");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			
		return "loginfailure";
	}
	
	public void onTabChange(String tabNme) {
        List<UserDetailsBean> lst = lstUserDetailsBean;
        for (UserDetailsBean userDetailsBean : lst) {
			if(tabNme.equals(userDetailsBean.getMdGpNme())){
				actIndx = userDetailsBean.getId();
				menus = userDetailsBean.getMenus();
			}
		}
    }  
	
	
	public void onMenuChange(TabChangeEvent event){
		/*String []id = event.getTab().getId().split("[^0-9]");*/
		try{
			actIndxMenu = Integer.parseInt(event.getTab().getId());
		}catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	public String logout(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "logout";
	}
	
	
	
	
	public boolean validate() {
		boolean isValid = true;

		if (nullCheck(user.getUserName().trim())) {
			addMessage("ERRCD527E");
			isValid = false;
			return isValid;
		}else if (nullCheck(user.getPassword().trim())) {
			addMessage("ERRCD528E");
			isValid = false;
			return isValid;
		}
		return isValid;
	}
	
	
	
	
	

	

	


	public int getActIndxMenu() {
		return actIndxMenu;
	}

	public void setActIndxMenu(int actIndxMenu) {
		this.actIndxMenu = actIndxMenu;
	}

	public int getActIndx() {
		return actIndx;
	}

	public void setActIndx(int actIndx) {
		this.actIndx = actIndx;
	}

	public List<UserDetailsBean> getLstUserDetailsBean() {
		return lstUserDetailsBean;
	}

	public void setLstUserDetailsBean(List<UserDetailsBean> lstUserDetailsBean) {
		this.lstUserDetailsBean = lstUserDetailsBean;
	}

	public ArrayList<Menu> getMenus() {
		return menus;
	}

	public void setMenus(ArrayList<Menu> menus) {
		this.menus = menus;
	}

	public void setLoginDelegate(LoginDelegate loginDelegate) {
		this.loginDelegate = loginDelegate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
