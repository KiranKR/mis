package com.Util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;





public class UserUtil {
	
	public static String applicationLogPath ;
	
	public static void setUserID(String uid) {
		setSession(UserBeanConstants.USER_ID_IN_SESSION, uid);
	}
	
	public static String getUserID() {
		return getSessionValue(UserBeanConstants.USER_ID_IN_SESSION);
	}	
	
	public static void setUserName(String usrNme) {
		setSession(UserBeanConstants.USER_NAME_IN_SESSION, usrNme);
	}
	
	public static String getUserName() {
		return getSessionValue(UserBeanConstants.USER_NAME_IN_SESSION);
	}	
	
	public static void setRole(String role) {
		setSession(UserBeanConstants.USER_ROLE_IN_SESSION, role);
	}
	
	public static String getRole() {
		return getSessionValue(UserBeanConstants.USER_ROLE_IN_SESSION);
	}	
	
	public static void setSession(String key, String value) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put(key, value);
	}
	
	public static String getSessionValue(String key) {
		FacesContext context = FacesContext.getCurrentInstance();
		return (String) context.getExternalContext().getSessionMap().get(key);
	}
	
	public static void setSessionObj(String key, Object obj) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put(key, obj);
	}
	
	public static Object getSessionObj(String key) {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getExternalContext().getSessionMap().get(key);
	}
	
	public static String getIP() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return request.getRemoteAddr();
	}
	
	// Yogesh. 	
	@SuppressWarnings("deprecation")
	public static String getApplicationFullPath(){
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return request.getRealPath(getApplicationPath());
	}
	
	public static String getApplicationPath(){
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return request.getServletPath();
	}
	
	public static String getRequestObject(String key){
		FacesContext context = FacesContext.getCurrentInstance();
		return (String) context.getExternalContext().getRequestParameterMap().get(key);
	}
	
	public static String getLoggerpath(){
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		UserUtil.applicationLogPath = request.getRealPath("/");
		return getApplicationLogPath();
	}
	public static String getApplicationLogPath() {
		return applicationLogPath;
	}
}
