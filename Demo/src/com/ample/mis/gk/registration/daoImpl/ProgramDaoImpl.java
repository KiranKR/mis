package com.ample.mis.gk.registration.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.Util.MysqlConnectionProvider;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.ample.mis.gk.registration.bean.PendingRep;
import com.ample.mis.gk.registration.dao.ProgramDao;

@Repository("programDao")
public class ProgramDaoImpl implements ProgramDao{
	
	
	
	public List<PendingRep> search(String uniqueId,String criteria){
		String condition = "";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<PendingRep> pRepLst = new ArrayList<PendingRep>();
		
		try {
			String query= "";
			if(uniqueId.equals("") || criteria.equals("")){
				query = "SELECT " +
				"pw.PWRK_ID," +
				"pw.PWRK_UID," +
				"p.PROGRM_ID," +
				"s.SECTN_ID," +
				"cc.CSTCTG_ID," +
				"pw.PWRK_CSTCTG_IDEN_FLAG," +
				"pw.PWRK_VILLAGE," +
				"pw.PWRK_BENEFICIARY," +
				"t.TALUK_ID," +
				"pw.PWRK_REG_DATE, pw.PWRK_REG_FEE, pw.PWRK_RTNO " +
				"FROM program_works pw  " +
				"JOIN program p ON pw.PWRK_PROGRM_ID = p.PROGRM_ID  " +
				"JOIN caste_category cc ON pw.PWRK_CSTCTG_ID = cc.CSTCTG_ID " +
				"JOIN section s ON pw.PWRK_SECTN_ID = s.SECTN_ID " +
				"join taluk t ON pw.PWRK_TALUK_ID = t.TALUK_ID WHERE pw.PWRK_STAGE_IDEN >= 1 and pw.PWRK_ROW_STATUS = 0 " +
				"and s.SECTN_SUBDIV_ID = "+UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+" "; 
			}
			else{
				if(criteria.equals("1")){
					condition = "pw.PWRK_UID like '%"+uniqueId+"%'";
				}else if (criteria.equals("2")) {
					condition = "pw.PWRK_BENEFICIARY like '%"+uniqueId+"%'";
				}
				query = "SELECT " +
				"pw.PWRK_ID," +
				"pw.PWRK_UID," +
				"p.PROGRM_ID," +
				"s.SECTN_ID," +
				"cc.CSTCTG_ID," +
				"pw.PWRK_CSTCTG_IDEN_FLAG," +
				"pw.PWRK_VILLAGE," +
				"pw.PWRK_BENEFICIARY," +
				"t.TALUK_ID," +
				"pw.PWRK_REG_DATE, pw.PWRK_REG_FEE, pw.PWRK_RTNO " +
				"FROM program_works pw  " +
				"JOIN program p ON pw.PWRK_PROGRM_ID = p.PROGRM_ID  " +
				"JOIN caste_category cc ON pw.PWRK_CSTCTG_ID = cc.CSTCTG_ID " +
				"JOIN section s ON pw.PWRK_SECTN_ID = s.SECTN_ID " +
				"join taluk t ON pw.PWRK_TALUK_ID = t.TALUK_ID " +
				" WHERE "+ condition +" and pw.PWRK_STAGE_IDEN >= 1 and pw.PWRK_ROW_STATUS = 0 " +
				"and s.SECTN_SUBDIV_ID = "+UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+" "; 
			}
			                               
			                                                                        
			                                                                       
			connection =  MysqlConnectionProvider.getNewConnection();              
			preparedStatement = connection.prepareStatement(query);
			
			 resultSet = preparedStatement.executeQuery();
			 while(resultSet.next()){
				 
				 pRepLst.add(new PendingRep(resultSet.getString("TALUK_ID"),resultSet.getString("SECTN_ID"),
						 	resultSet.getString("CSTCTG_ID"),resultSet.getString("PWRK_CSTCTG_IDEN_FLAG"),
						 	resultSet.getString("PWRK_BENEFICIARY"), resultSet.getString("PWRK_VILLAGE"), 
						 	dateToString(resultSet.getDate("PWRK_REG_DATE")), resultSet.getString("PROGRM_ID"), 
						 	resultSet.getString("PWRK_UID"),resultSet.getString("PWRK_ID"), resultSet.getDouble("PWRK_REG_FEE"), resultSet.getString("PWRK_RTNO")));
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement, connection);
		}
		return pRepLst;
	}
	
	public boolean addProgram(PendingRep pendingRep, String stage){
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		PreparedStatement preparedStatement4 = null;
		PreparedStatement preparedStatement5 = null;
		boolean isValid = false;
		int digit = 10000;
		String number = "";
		String uniqueId="";
		StringBuffer buffer = new StringBuffer();
		
		try {
			connection =  MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			
			
			String uniqueIdQuery = "SELECT z.ZONE_SHORT_CODE," +
							"c.CRCL_SHORT_CODE," +
							"d.DIV_SHORT_CODE," +
							"sd.SUBDIV_SHORT_CODE, s.SECTN_SHORT_CODE " +
							"FROM section s " +
							"JOIN sub_division sd ON s.SECTN_SUBDIV_ID = sd.SUBDIV_ID " +
							"JOIN division d ON sd.SUBDIV_DIV_ID = d.DIV_ID " +
							"JOIN circle c ON d.DIV_CIRCLE_ID = c.CRCL_ID " +
							"JOIN zone z ON c.CRCL_ZONE_ID = z.ZONE_ID " +
							"WHERE s.SECTN_ID = "+pendingRep.getSectionId()+" ";                                  	        
					                                                                        
					                                                                       
					preparedStatement3 = connection.prepareStatement(uniqueIdQuery);
					
					resultSet = preparedStatement3.executeQuery();
					while(resultSet.next()){
						buffer.append("GK"); //get from session after login
						buffer.append(resultSet.getString("ZONE_SHORT_CODE"));
						buffer.append(resultSet.getString("CRCL_SHORT_CODE"));
						buffer.append(resultSet.getString("DIV_SHORT_CODE"));
						buffer.append(resultSet.getString("SUBDIV_SHORT_CODE"));
						buffer.append(resultSet.getString("SECTN_SHORT_CODE"));
						uniqueId = buffer.toString();
					}
			
			String uniqueQuery = "select ifnull(max(substring(p.PWRK_UID,13,4)) +1,1) from  program_works p " +
								"where substring(p.PWRK_UID,1,12) ='"+uniqueId+"' ";
			
			preparedStatement2 = connection.prepareStatement(uniqueQuery);
			resultSet = preparedStatement2.executeQuery();
				while(resultSet.next()){
					digit =digit+ resultSet.getInt(1);
					
					number = (String) String.valueOf(digit).substring(1,5);
				}
				
			String uniqueNumber = uniqueId.concat(number);
			pendingRep.setUniqueId(uniqueNumber);
			
			String woIssDateCondition =null;
			if(!"".equals(pendingRep.getWoIssDate())){
				woIssDateCondition = "STR_TO_DATE('"+pendingRep.getWoIssDate()+"','%d/%m/%Y')";
			}
			String dtOfRegCondition=null;
			if(!"".equals(pendingRep.getDtOfRegtn())){
				dtOfRegCondition= "STR_TO_DATE('"+pendingRep.getDtOfRegtn()+"','%d/%m/%Y')";
			}
			String query = "insert into program_works " +
								"(PWRK_DATE ,PWRK_PROGRM_ID ,PWRK_SECTN_ID ,PWRK_UID ,PWRK_RTNO ,PWRK_REG_DATE ," +
								"PWRK_REG_FEE ,PWRK_WO_ISSUE_DATE,PWRK_WO_NO ,PWRK_STAGE_IDEN ) " +
							"VALUES " +
								"(NOW(), 1, "+pendingRep.getSectionId()+" ,'"+uniqueNumber+"', " +
								" '"+pendingRep.getRegstrNo()+"', "+dtOfRegCondition+", '"+pendingRep.getRegstrFee()+"', " +
								" "+woIssDateCondition+", '"+pendingRep.getWoIssNum()+"', "+stage+" )";
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
			
			
			String prgWrkIdQuery = "select ifnull(max(pw.PWRK_ID),1) from program_works pw";
			preparedStatement4 = connection.prepareStatement(prgWrkIdQuery);
			resultSet = preparedStatement4.executeQuery();
			
			while(resultSet.next()){
				pendingRep.setPrgWrId(resultSet.getString(1));
			}
			
			String dateOfSerCondtion = null;
			if(!"".equals(pendingRep.getDateofService())){
				dateOfSerCondtion = "STR_TO_DATE('"+pendingRep.getDateofService()+"','%d/%m/%Y')";
			}
			String benRRInsertQuery = "insert into program_benificary " +
										"(PBS_PWRK_ID ,PBS_BENEFICIARY ,PBS_RR_NUMBER ,PBS_DATE_OF_SERVICE ,PBS_SORT_ORDER ,PBS_ROW_STATUS) " +
										" VALUES ('"+pendingRep.getPrgWrId()+"' ," +
										" '"+pendingRep.getBeneficiaryName()+"' ," +
										" '"+pendingRep.getRrNumber()+"'  ," +
										" "+dateOfSerCondtion+"  ,1  ,0) ";
			
			
			preparedStatement5 = connection.prepareStatement(benRRInsertQuery);
			preparedStatement5.execute();
			
			connection.commit();
			connection.setAutoCommit(true);
			isValid = true;
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException sql) {
				sql.printStackTrace();
			}
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement, connection);
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement2, connection);
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement3, connection);
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement4, connection);
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement5, connection);
		}
		
		
		return isValid;
	}
	
	public int updateProgram(PendingRep pendingRep){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int updated = 0;
		
		try {
			String query = "update program_works SET " +
							"PWRK_PROGRM_ID = "+pendingRep.getProgramId()+"," +
							"PWRK_SECTN_ID = "+pendingRep.getSectionId()+" ," +
							"PWRK_UID = '"+pendingRep.getUniqueId()+"'," +
							"PWRK_CSTCTG_ID = "+pendingRep.getSchemeId()+" ," +
							"PWRK_CSTCTG_IDEN_FLAG = "+pendingRep.getSubSchemeId()+" ," +
							"PWRK_BENEFICIARY = '"+pendingRep.getBeneficiaryName()+"' ," +
							"PWRK_REG_DATE = STR_TO_DATE('"+pendingRep.getDtOfRegtn()+"','%d/%m/%Y') ," +
							"PWRK_VILLAGE = '"+pendingRep.getVillage()+"'," +
							"PWRK_TALUK_ID = "+pendingRep.getTalukId()+"," +
							
							"PWRK_REG_FEE = "+pendingRep.getRegstrFee()+"," +
							"PWRK_RTNO = "+pendingRep.getRegstrNo()+" " +
							"WHERE PWRK_ID = "+pendingRep.getPrgWrId()+" ";                                  	        
			                                                                        
			                                                                       
			connection =  MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(query);
			
			updated = preparedStatement.executeUpdate();
			connection.setAutoCommit(true);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) { 
			e.printStackTrace();
		}finally {
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement, connection);
		}
		return updated;
		
	}
	
	
public static String dateToString(Date pDate){
	DateFormat formatter ;
	formatter = new SimpleDateFormat("dd/MM/yyyy");
	String s = formatter.format(pDate);
	return s;

	}
}
