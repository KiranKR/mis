package com.ample.mis.loginCommon.login.service;

import java.util.List;

import com.ample.mis.hibernate.bo.UserMapBO;

public interface LoginService {
	UserMapBO verifyuser(String usrNme, String usrPwd)throws Exception;
	List<Object[]> fetchUserDetails(int userId) throws Exception;
}
