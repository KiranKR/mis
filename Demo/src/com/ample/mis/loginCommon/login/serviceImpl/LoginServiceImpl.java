package com.ample.mis.loginCommon.login.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ample.mis.hibernate.bo.UserMapBO;
import com.ample.mis.loginCommon.login.dao.LoginDao;
import com.ample.mis.loginCommon.login.service.LoginService;

@Service("loginService")
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private LoginDao loginDao;
	
	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}
	
	@Transactional(readOnly=true)
	public UserMapBO verifyuser(String usrNme, String usrPwd) throws Exception {
		UserMapBO usersMpBO = loginDao.verifyuser(usrNme, usrPwd);
		return usersMpBO;
	}
	
	@Transactional(readOnly=true)
	public List<Object[]> fetchUserDetails(int userId) throws Exception {
		List<Object[]> objBOs= loginDao.fetchUserDetails(userId);
		return objBOs;
	}

}
