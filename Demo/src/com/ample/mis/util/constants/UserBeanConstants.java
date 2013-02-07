package com.ample.mis.util.constants;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserBeanConstants implements Serializable {
	
	public final static String USER_ID_IN_SESSION = "USER_ID";
	public final static String USER_NAME_IN_SESSION = "USER_NAME";
	public final static String BPR_ID_IN_SESSION = "BPR_ID";
	public final static String USER_ROLE_IN_SESSION = "USER_ROLE";
	public final static String ACTION_MAP_IN_SESSION= "ACTION_MAP";
	
	//Ids-->zone-->circle-->division-->subdivision-->section
	public final static String LOGIN_ID_IN_SESSION= "LOGIN_ID";
	
	//UserMapIdentity Flag
	public final static String USER_MP_IDEN_FLAG_IN_SESSION= "FLAG_ID";
	
	//Program Details Report
	public final static String PRG_DETAIL_REP_IN_SESSION = "PRG_DETAIL_REP";
	
	//BMAZ MNR Report
	public final static String BMAZ_MNR_REP_IN_SESSION = "BMAZ_MNR_REP";
}
