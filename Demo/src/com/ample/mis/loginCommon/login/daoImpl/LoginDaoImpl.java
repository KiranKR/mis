package com.ample.mis.loginCommon.login.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ample.mis.hibernate.bo.UserMapBO;
import com.ample.mis.hibernate.daoImpl.BaseHibernateDaoImpl;
import com.ample.mis.loginCommon.login.dao.LoginDao;

@Repository("loginDao")
public class LoginDaoImpl extends BaseHibernateDaoImpl implements LoginDao{
	
	




	public UserMapBO verifyuser(String usrNme, String usrPwd) throws Exception {

		UserMapBO usersMpBO = null;
		String query = " select usrMp from UserMapBO usrMp join usrMp.users usr " +
		" where usr.userName like '"+usrNme+"' and usr.userPassword like '"+usrPwd+"'  and usrMp.umapRowStatus = 0 " ;
		
		List<UserMapBO> usrLst = (List<UserMapBO>) fetchAllData(query);
		for (UserMapBO userMpBO : usrLst) {
			if(usrNme.equals(userMpBO.getUsers().getUserName()) && usrPwd.equals(userMpBO.getUsers().getUserPassword())){
				return userMpBO;
			}
		}
		return usersMpBO;
		
	}

	

	public List<Object[]> fetchUserDetails(int userId) throws Exception {
		String query = "select  distinct rlMdAc.rlmapId,"
			+" usr.userId as UserId,"
			+" usr.userName as UserName,"
			+" usrRl.usrolId as userRoleID,"
			+" rl.roleId as roleID,"
			+" rl.roleName as roleName,"
			+" md.modParentName as ParentName,"
			+" md.modId as modId,"
			+" md.modName as modName,"
			+" ac.actnId as actnId,"
			+" ac.actnName as actionName,"
			+" md.modPath,mg.modgrpId, mg.modgrpName   "
			+" from UsersBO usr "
			+" left join usr.userRoleses usrRl "
			+" left join usrRl.role rl "
			+" join rl.rolModActions rlMdAc "
			+" left join rlMdAc.modAction mdAc "
			+" left join mdAc.module md "
			+" left join md.moduleGroup mg "
			+" left join mdAc.action ac "
			+" where usr.userId = "+userId+"  and  mdAc.macnRowStatus = 0 and  mdAc.macnIden = 0 and rl.roleRowStatus = 0 " 
			+" and usrRl.usrolRowStatus = 0 and mdAc.macnRowStatus = 0 and md.modRowStatus = 0 and rlMdAc.rlmapRowStatus = 0 " 
			+" order by   mg.modgrpId,md.modLevel,md.modParentName,md.modName,ac.actnName,md.modSortOrder,md.modParent  ";

			List<Object[]> objBOs = (List<Object[]>) fetchAllData(query);
			
			return objBOs;
	}
	


}
