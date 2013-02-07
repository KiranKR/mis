package com.ample.mis.loginCommon.login.dao;

import java.util.List;

import com.ample.mis.hibernate.bo.UserMapBO;
import com.ample.mis.hibernate.dao.BaseHibernateDAO;

public interface LoginDao extends BaseHibernateDAO{
	UserMapBO verifyuser(String usrNme, String usrPwd)throws Exception;
	List<Object[]> fetchUserDetails(int userId) throws Exception;
}
