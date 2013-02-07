package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Util.MysqlConnectionProvider;
import com.ample.mis.loginCommon.login.bean.User;
import com.bean.PendingRep;

public class LoginDao1 {
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	public User checkUser(User user){
		User users = new User();
		PreparedStatement preparedStatement2 = null;
		try {
			connection =  MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			String query = "SELECT u.USER_NAME, " +
							"um.UMAP_IDEN_FLAG, " +
							"u.USER_ID, " +
							"ur.USROL_ID, " +
							"ur.USROL_ROLE_ID, " +
							"r.ROLE_ID, " +
							"r.ROLE_NAME " +
							"FROM users u " +
							"JOIN user_map um ON um.UMAP_USER_ID = u.USER_ID " +
							"JOIN user_roles ur ON ur.USROL_USER_ID = u.USER_ID " +
							"JOIN role r ON ur.USROL_ROLE_ID = r.ROLE_ID " +
							"WHERE u.USER_NAME = '"+user.getUserName()+"' AND u.USER_PASSWORD = '"+user.getPassword()+"' ";
							

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				
				users.setUserName(resultSet.getString("USER_NAME"));
				users.setuMapIdenFlag(resultSet.getInt("UMAP_IDEN_FLAG"));
				users.setUserId(resultSet.getString("USER_ID"));
				users.setRoleId(resultSet.getString("ROLE_ID"));
				users.setRoleNme(resultSet.getString("ROLE_NAME"));
			}
			String detailsQuery = null;
			
			if(!("".equals(users.getUserName()))){
				
				if(1 == users.getuMapIdenFlag()){
					
					detailsQuery = "SELECT z.ZONE_ID FROM user_map um, users u,zone z WHERE um.UMAP_IDEN_FLAG = 1 " +
									"AND um.UMAP_FK_ID = z.ZONE_ID " +
									"and um.UMAP_USER_ID = u.USER_ID " +
									"AND u.USER_NAME = '"+user.getUserName()+"' " +
									"AND u.USER_PASSWORD = '"+user.getPassword()+"' " +
									"and z.ZONE_ROW_STATUS = 0 ";
					
				}else if(2 == users.getuMapIdenFlag()){
					
					detailsQuery = "SELECT c.CRCL_ID  FROM user_map um, users u,zone z, circle c WHERE um.UMAP_IDEN_FLAG = 2 " +
									"AND um.UMAP_FK_ID = c.CRCL_ID " +
									"AND c.CRCL_ZONE_ID = z.ZONE_ID " +
									"and um.UMAP_USER_ID = u.USER_ID " +
									"AND u.USER_NAME = '"+user.getUserName()+"' " +
									"AND u.USER_PASSWORD = '"+user.getPassword()+"' " +
									"and c.CRCL_ROW_STATUS = 0 "+
									"and z.ZONE_ROW_STATUS = 0 ";
					
				}else if(3 == users.getuMapIdenFlag()){
					
					detailsQuery = "SELECT d.DIV_ID FROM user_map um, users u,zone z, circle c, division d WHERE um.UMAP_IDEN_FLAG = 3 " +
									"AND um.UMAP_FK_ID = d.DIV_ID " +
									"and d.DIV_CIRCLE_ID = c.CRCL_ID " +
									"AND c.CRCL_ZONE_ID = z.ZONE_ID " +
									"and um.UMAP_USER_ID = u.USER_ID " +
									"AND u.USER_NAME = '"+user.getUserName()+"' " +
									"AND u.USER_PASSWORD = '"+user.getPassword()+"' " +
									"and d.DIV_ROW_STATUS = 0 " +
									"and c.CRCL_ROW_STATUS = 0 "+
									"and z.ZONE_ROW_STATUS = 0 ";
					
				}else if(4 == users.getuMapIdenFlag()){
					
					detailsQuery = "SELECT sd.SUBDIV_ID FROM user_map um, users u,zone z, circle c, division d, sub_division sd WHERE um.UMAP_IDEN_FLAG = 4 " +
									"AND um.UMAP_FK_ID = sd.SUBDIV_ID " +
									"and sd.SUBDIV_DIV_ID = d.DIV_ID " +
									"and d.DIV_CIRCLE_ID = c.CRCL_ID " +
									"AND c.CRCL_ZONE_ID = z.ZONE_ID " +
									"and um.UMAP_USER_ID = u.USER_ID " +
									"AND u.USER_NAME = '"+user.getUserName()+"' " +
									"AND u.USER_PASSWORD = '"+user.getPassword()+"' " +
									"and sd.SUBDIV_ROW_STATUS = 0 " +
									"and d.DIV_ROW_STATUS = 0 " +
									"and c.CRCL_ROW_STATUS = 0 "+
									"and z.ZONE_ROW_STATUS = 0 ";
					
				}else if(5 == users.getuMapIdenFlag()){
					detailsQuery = "SELECT s.SECTN_ID FROM user_map um, users u,zone z, circle c, division d, sub_division sd, section s WHERE um.UMAP_IDEN_FLAG = 5 " +
									"AND um.UMAP_FK_ID = s.SECTN_ID " +
									"and s.SECTN_SUBDIV_ID = sd.SUBDIV_ID " +
									"and sd.SUBDIV_DIV_ID = d.DIV_ID " +
									"and d.DIV_CIRCLE_ID = c.CRCL_ID " +
									"AND c.CRCL_ZONE_ID = z.ZONE_ID " +
									"and um.UMAP_USER_ID = u.USER_ID " +
									"AND u.USER_NAME = '"+user.getUserName()+"' " +
									"AND u.USER_PASSWORD = '"+user.getPassword()+"' " +
									"and s.SECTN_ROW_STATUS = 0 " +
									"and sd.SUBDIV_ROW_STATUS = 0 " +
									"and d.DIV_ROW_STATUS = 0 " +
									"and c.CRCL_ROW_STATUS = 0 "+
									"and z.ZONE_ROW_STATUS = 0 ";
				}
				
				preparedStatement2 = connection.prepareStatement(detailsQuery);
				resultSet = preparedStatement2.executeQuery();

				
				while(resultSet.next()){
					users.setLoginId(resultSet.getString(1));
					
				}
				
			}
			
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement, connection);
			MysqlConnectionProvider.releaseConnection(null, null, preparedStatement2, null);
		}
		return users;
	}
	
	
	
public List<PendingRep> viewData(String division){
		List<PendingRep> pendingReps = new ArrayList<PendingRep>();
		PendingRep pendingRep = null;
		try {
			String query = "select t.TALUK_NAME,pr.DIVISION,pr.SUBDIV,sec.Sec_Name,pr.SCHEME,pr.SUB_SCHEME,pr.BENEFICIARY_NAME,pr.VILLAGE,pr.DT_OF_REGTN,pr.ESTIMATE_PREPARED,pr.ESTIMATE_COST,pr.INTIMATN_ISS_DATE,pr.WO_ISS_NUM,pr.WO_ISS_DATE,pr.WRK_YET_TO_BE_TAKEN,pr.WRK_UNDR_PROGRESS,pr.WRK_COMPLETED,pr.PEND_FRM_LOC_BDY from pending_rep pr join section sec on pr.SEC_ID = sec.Sec_ID join taluk t on pr.TALUK = t.TALUK_ID WHERE pr.DIVISION = '"+division+"'";
			connection =  MysqlConnectionProvider.getNewConnection();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				pendingRep = new PendingRep();
				pendingRep.setSlno(i++);
				pendingRep.setTalukId(resultSet.getString(1));
				pendingRep.setDivision(resultSet.getString(2));
				pendingRep.setSubdiv(resultSet.getString(3));
				pendingRep.setSectionId(resultSet.getString(4));
				pendingRep.setSchemeId(resultSet.getString(5));
				pendingRep.setSubSchemeId(resultSet.getString(6));
				pendingRep.setBeneficiaryName(resultSet.getString(7));
				pendingRep.setVillage(resultSet.getString(8));
				pendingRep.setDtOfRegtn(dateFormat(resultSet.getString(9)));
				pendingRep.setEstimatePrepared(resultSet.getString(10));
				pendingRep.setEstimateCost(resultSet.getDouble(11));
				pendingRep.setIntimatnIssDate(dateFormat(resultSet.getString(12)));
				pendingRep.setWoIssNum(resultSet.getString(13));
				pendingRep.setWoIssDate(dateFormat(resultSet.getString(14)));
				pendingRep.setWrkYetToBeTaken(resultSet.getString(15));
				pendingRep.setWrkUndrProgress(resultSet.getString(16));
				pendingRep.setWrkCompleted(resultSet.getString(17));
				pendingRep.setPendFrmLocBdy(resultSet.getString(18));
				pendingReps.add(pendingRep);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement, connection);
		}
		return pendingReps;
	}
public List<PendingRep> viewData(int subdivId){
	List<PendingRep> pendingReps = new ArrayList<PendingRep>();
	PendingRep pendingRep = null;
	try {
		String query = "select t.TALUK_NAME,pr.DIVISION,pr.SUBDIV,sec.Sec_Name,pr.SCHEME,pr.SUB_SCHEME,pr.BENEFICIARY_NAME,pr.VILLAGE,pr.DT_OF_REGTN,pr.ESTIMATE_PREPARED,pr.ESTIMATE_COST,pr.INTIMATN_ISS_DATE,pr.WO_ISS_NUM,pr.WO_ISS_DATE,pr.WRK_YET_TO_BE_TAKEN,pr.WRK_UNDR_PROGRESS,pr.WRK_COMPLETED,pr.PEND_FRM_LOC_BDY from pending_rep pr join section sec on pr.SEC_ID = sec.Sec_ID join taluk t on pr.TALUK = t.TALUK_ID WHERE pr.SUB_DIV_ID = " + subdivId;
		connection =  MysqlConnectionProvider.getNewConnection();
		preparedStatement = connection.prepareStatement(query);
		int i = 1;
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			pendingRep = new PendingRep();
			pendingRep.setSlno(i++);
			pendingRep.setTalukId(resultSet.getString(1));
			pendingRep.setDivision(resultSet.getString(2));
			pendingRep.setSubdiv(resultSet.getString(3));
			pendingRep.setSectionId(resultSet.getString(4));
			pendingRep.setSchemeId(resultSet.getString(5));
			pendingRep.setSubSchemeId(resultSet.getString(6));
			pendingRep.setBeneficiaryName(resultSet.getString(7));
			pendingRep.setVillage(resultSet.getString(8));
			pendingRep.setDtOfRegtn(dateFormat(resultSet.getString(9)));
			pendingRep.setEstimatePrepared(resultSet.getString(10));
			pendingRep.setEstimateCost(resultSet.getDouble(11));
			pendingRep.setIntimatnIssDate(dateFormat(resultSet.getString(12)));
			pendingRep.setWoIssNum(resultSet.getString(13));
			pendingRep.setWoIssDate(dateFormat(resultSet.getString(14)));
			pendingRep.setWrkYetToBeTaken(resultSet.getString(15));
			pendingRep.setWrkUndrProgress(resultSet.getString(16));
			pendingRep.setWrkCompleted(resultSet.getString(17));
			pendingRep.setPendFrmLocBdy(resultSet.getString(18));
			pendingReps.add(pendingRep);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}finally {
		MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement, connection);
	}
	return pendingReps;
}
private String dateFormat(String date) {
	String[] field = date.split("-");
	return field[2]+"/"+field[1]+"/"+field[0];
}

}
