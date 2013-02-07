package com.ample.mis.loginCommon.login.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.stereotype.Controller;

import com.Util.UserBeanConstants;
import com.ample.mis.hibernate.bo.UserMapBO;
import com.ample.mis.loginCommon.login.bean.LoginActBean;
import com.ample.mis.loginCommon.login.bean.Menu;
import com.ample.mis.loginCommon.login.bean.SubMenus;
import com.ample.mis.loginCommon.login.bean.User;
import com.ample.mis.loginCommon.login.bean.UserDetailsBean;
import com.ample.mis.util.common.UserUtil;

@Controller("loginHelper")
public class LoginHelper {
	
	
	public User convertUserBOToUserVO(UserMapBO userMpBOs) {
		User usrBean = null;
		if(userMpBOs!=null){
			usrBean  = new User();
			usrBean.setUserId(userMpBOs.getUsers().getUserId().toString());
			usrBean.setUserName(userMpBOs.getUsers().getUserName());
			usrBean.setPassword(userMpBOs.getUsers().getUserPassword());
			
			usrBean.setLoginId(Integer.toString(userMpBOs.getUmapFkId()));
			usrBean.setuMapIdenFlag(userMpBOs.getUmapIdenFlag());
		}
		return usrBean;
	}
	
	
	public List<UserDetailsBean> usrDetilsBOToVo(List<Object[]> usrBOs){
		int id = 0;
		String usrId = "";
		String usrNme = "";
		String usrRolID = "";
		List<UserDetailsBean> lstDetailsBeans = new ArrayList<UserDetailsBean>();
		UserDetailsBean usrDetilsBean = null;
		ArrayList<Menu> menuLst = null;
		ArrayList<SubMenus> subMenuLst = null ;
		/*HashMap<String, String> sessionActMap = new HashMap<String, String>();*/
		/*HashMap<String, String> sessionActMap = null;
		ArrayList<HashMap<String, String>> sessActnList = new ArrayList<HashMap<String,String>>();*/ 
		TreeMap<String, List> sessionActMap = new TreeMap<String, List>();
		/*TreeMap<String, List> sessionActMap = new TreeMap<String, List>();*/
		String tempMenu =  "";
		String tempSubMenu =  "";
		
		String tempActNme = "";
		String tempUsrNme = "";
		String tempMap = "";
		String tempMdGpId = "";
		
		Menu menu = null;
		SubMenus subMenu = null;
		String subMenuNme=  "";
		String subMenuId=  "";
		String menuNme=  "";
		String menuId=  "";
		String path=  "";
		String actId=  "";
		String actNme=  "";
		String moduleGpId = "";
		String roleNme ="";
		String moduleGPNme ="";
		int count = 0;
		
		LoginActBean actBean=  null;
		List<LoginActBean>actBeanLst = null;
		for (Object[] usrBO : usrBOs) {
			usrRolID = "";
			usrId = (usrBO[1]==null? null :usrBO[1].toString());
			usrNme = (usrBO[2]==null? null :usrBO[2].toString()); 
			menuNme = (usrBO[6]==null? null :usrBO[6].toString());
			subMenuId = (usrBO[7]==null? null :usrBO[7].toString());
			subMenuNme = (usrBO[8]==null? null :usrBO[8].toString());
			actId = (usrBO[9]==null? null :usrBO[9].toString());
			actNme = (usrBO[10]==null? null :usrBO[10].toString());
			path = (usrBO[11]==null? null :usrBO[11].toString());
			roleNme = (usrBO[5]==null? null :usrBO[5].toString());
			moduleGpId = (usrBO[12]==null? null :usrBO[12].toString());
			moduleGPNme = (usrBO[13]==null? null :usrBO[13].toString());
			if("".equals(tempMdGpId) || !moduleGpId.equals(tempMdGpId)){
				
				if(!"".equals(tempMdGpId) || moduleGpId.equals(tempMdGpId)){
					tempMdGpId = moduleGpId;
					usrDetilsBean.setId(count++);
					usrDetilsBean.setMenus(menuLst);
					lstDetailsBeans.add(usrDetilsBean);
					tempMenu="";
					tempSubMenu="";
					tempActNme="";
				}
				
				tempMdGpId = moduleGpId;
				usrDetilsBean = new UserDetailsBean();
				menuLst = new ArrayList<Menu>();
				
				usrDetilsBean.setUsrId(usrId);
				usrDetilsBean.setUsrName(usrNme);
				usrDetilsBean.setRoleNme(roleNme);
				usrDetilsBean.setMdGpNme(moduleGPNme);
				
			}
			
			
			/*if("".equals(tempUsrNme) || !usrNme.equals(tempUsrNme)){
				tempUsrNme = usrNme;
				usrDetilsBean.setUsrId(usrId);
				usrDetilsBean.setUsrName(usrNme);
			}*/
			
			
			if ("".equals(tempMenu) || !menuNme.equals(tempMenu)) {
				tempMenu = menuNme;
				menu = new Menu();
				menu.setName(menuNme);
				
				menuLst.add(menu);
				
				subMenuLst = new ArrayList<SubMenus>();
			}

			if ("".equals(tempSubMenu) || !subMenuNme.equals(tempSubMenu)) {
				
				if(!"".equals(tempMap) || subMenuNme.equals(tempMap)){
					tempMap = subMenuNme;
					sessionActMap.put(tempSubMenu, actBeanLst);
				}
				
				
				tempSubMenu= subMenuNme;
				tempMap = subMenuNme;
				tempActNme = "";
				subMenu = new SubMenus(subMenuId, subMenuNme, path);
				
				subMenuLst.add(subMenu);
				menu.setSubMenus(subMenuLst);
				
				actBeanLst = new ArrayList<LoginActBean>();
				
				
			}
			
			if("".equals(tempActNme) || !actNme.equals(tempActNme)){
				tempActNme = actNme;
				
				actBean = new LoginActBean();
				actBean.setActionId(actId);
				actBean.setActionName(actNme);
				actBeanLst.add(actBean);
				subMenu.setActBeans(actBeanLst);
				
			}
			
			
				/*sessionActMap = new HashMap<String, String>();
				sessionActMap.put(subMenuNme, actNme);
				sessActnList.add(sessionActMap);*/
			
			/*if (!tempSubMenu.equals(subMenuNme)) {
				subMenuLst.add(subMenu);
			}
			if (!tempMenu.equals(menuNme)) {
				menu.setSubMenus(subMenuLst);
				menuLst.add(menu);

			}*/
		}	
			sessionActMap.put(tempSubMenu, actBeanLst);
			UserUtil.setSessionObj(UserBeanConstants.ACTION_MAP_IN_SESSION, sessionActMap);
			usrDetilsBean.setId(count);
			usrDetilsBean.setMenus(menuLst);
			lstDetailsBeans.add(usrDetilsBean);
			/*UserUtil.setSessionObj("ACTION_MAP", sessActnList);*/
	
		return lstDetailsBeans;
			
	}
	
}
