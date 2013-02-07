package com.ample.mis.loginCommon.login.delegate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ample.mis.loginCommon.login.bean.User;
import com.ample.mis.loginCommon.login.bean.UserDetailsBean;
import com.ample.mis.loginCommon.login.helper.LoginHelper;
import com.ample.mis.loginCommon.login.service.LoginService;

@Controller("loginDelegate")
public class LoginDelegate {

	@Autowired
	private LoginService loginService;
	@Autowired
	private LoginHelper loginHelper;
	
	
	
	

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}


	public void setLoginHelper(LoginHelper loginHelper) {
		this.loginHelper = loginHelper;
	}


	public User checkUser(User user) throws Exception {
		user = loginHelper.convertUserBOToUserVO(loginService.verifyuser(user.getUserName().trim(), user.getPassword().trim()));
		return user;
	}
	
	public List<UserDetailsBean> fetchUserDetails(int userId)throws Exception {
		List<UserDetailsBean> lstUserDetailsBean = null;
		lstUserDetailsBean = loginHelper.usrDetilsBOToVo(loginService.fetchUserDetails(userId));
		return lstUserDetailsBean;
	}
}
