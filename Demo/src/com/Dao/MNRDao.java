package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Util.AppUtil;
import com.Util.MysqlConnectionProvider;
import com.bean.MNRBean;

public class MNRDao {
	
	
	
	public MNRBean getOpenCloseBal(MNRBean bean){
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		int mnrNineOpenBal = 0;
		int mnrTenOpenBal = 0;
		int mnrElevenOpenBal = 0;
		
		try {
			String queryNine = "select BLASON_OB_1TO3 , BLASON_OB_3TO6 , BLASON_OB_GRT6   from balance_ason where blason_iden_flag=9 and blason_level_iden=5 and BLASON_LEVEL_FK_ID= "+bean.getSectionId()+" ";
			                                                                       
			connection =  MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(queryNine);
			
			resultSet = preparedStatement.executeQuery();
			bean.setViewOnlyOB(false);
			while(resultSet.next()){
					bean.setViewOnlyOB(true);
					mnrNineOpenBal = resultSet.getInt(1);
					mnrTenOpenBal = resultSet.getInt(2);
					mnrElevenOpenBal = resultSet.getInt(3);
				}
				
				
				bean.setMnrNineOpenBal(mnrNineOpenBal);
				bean.setMnrTenOpenBal(mnrTenOpenBal);
				bean.setMnrElevenOpenBal(mnrElevenOpenBal);
			
			connection.commit();
			connection.setAutoCommit(true);
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement, connection);
		}
		
		return bean;
	}
	
	
	public boolean saveMNR(MNRBean mnrBean){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement3 = null;
		PreparedStatement preparedStatement4 =null;
		PreparedStatement preparedStatement5 =null;
		PreparedStatement preparedStatement6 =null;
		PreparedStatement preparedStatement7 =null;
		PreparedStatement preparedStatement8 =null;
		PreparedStatement preparedStatement9 =null;
		PreparedStatement pst1 = null;
		
		ResultSet resultSet = null;
		boolean isValid = false;
		int stage = 9;
		try {
			
			 
			                                                                       
			connection =  MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			if(0 == mnrBean.getTransHeadId()){
				
			
				String query = "insert into tran_head " +
				"(TH_SECTN_ID, " +
				"TH_MONTH , " +
				"TH_YEAR, " +
				"TH_ROW_STATUS, " +
				"TH_IDEN_FLAG, " +
				"TH_MNR_REASON, " +
				"TH_DATE) " +
				"VALUES ( "+ mnrBean.getSectionId()+", " +
						" "+ AppUtil.getMnthId(mnrBean.getMonthId())+", " +
						" "+ mnrBean.getYearId()+", " +
						" 0 , " +
						" "+stage+", " +
						" '"+ mnrBean.getMnrRsonFrNtReplace()+"' , " +
						" CURRENT_DATE()) ";
				
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.execute();
				
				String query2 ="SELECT ifnull(max(th.TH_ID), 1) FROM tran_head th";
				String thid=null;
				
				preparedStatement1 =connection.prepareStatement(query2);
				resultSet = preparedStatement1.executeQuery();
				
				while(resultSet.next()){
					thid = resultSet.getString(1);
				}
				
				
				String query1 ="INSERT INTO mnr_detail(MDET_TH_ID,MDET_IDEN_FLAG,MDET_OB,MDET_INSTALATION,MDET_REPLACED,MDET_CB,MDET_ROW_STATUS) VALUES ('"+thid+"',1,'"+mnrBean.getMnrNineOpenBal()+"','"+mnrBean.getMnrInstall()+"','"+mnrBean.getMnrReplace()+"','"+mnrBean.getMnrNineCloseBal()+"',0)";
					preparedStatement3 = connection.prepareStatement(query1);
					preparedStatement3.execute();
				
				
				String query3 ="INSERT INTO mnr_detail(MDET_TH_ID,MDET_IDEN_FLAG,MDET_OB,MDET_INSTALATION,MDET_REPLACED,MDET_CB,MDET_ROW_STATUS) VALUES ('"+thid+"',2,'"+mnrBean.getMnrTenOpenBal()+"','"+mnrBean.getGtThrmnrInsall()+"','"+mnrBean.getGtThrmnrReplace()+"','"+mnrBean.getMnrTenCloseBal()+"',0)";
					preparedStatement4 = connection.prepareStatement(query3);
					preparedStatement4.execute();
				
				String query4 ="INSERT INTO mnr_detail(MDET_TH_ID,MDET_IDEN_FLAG,MDET_OB,MDET_INSTALATION,MDET_REPLACED,MDET_CB,MDET_ROW_STATUS) VALUES ('"+thid+"',3,'"+mnrBean.getMnrElevenOpenBal()+"','"+mnrBean.getGtSixmnrInstall()+"','"+mnrBean.getGtSixmnrReplace()+"','"+mnrBean.getMnrElevenCloseBal()+"',0)";
					preparedStatement5 = connection.prepareStatement(query4);
					preparedStatement5.execute();
					
					if(!mnrBean.isViewOnlyOB()){
						
						String balanceAsonInsertNineQuery = "insert into balance_ason ( " +
														" BLASON_LEVEL_IDEN         " +
														" ,BLASON_LEVEL_FK_ID       " +
														" ,BLASON_IDEN_FLAG         " +
														" ,BLASON_OB_1TO3        " +
														" ,BLASON_OB_3TO6         " +
														" ,BLASON_OB_GRT6        " +
														" ,BLASON_LASTUPDATED       " +
														" ) VALUES ( 5 ,"+mnrBean.getSectionId()+" ,"+stage+" ," +
														" "+mnrBean.getMnrNineCloseBal()+" ,"+mnrBean.getMnrTenCloseBal()+" ,"+mnrBean.getMnrElevenCloseBal()+",NOW() ) ";
														
						preparedStatement7 = connection.prepareStatement(balanceAsonInsertNineQuery);
						preparedStatement7.execute();
						
					}
			
			}else{
								String transHeadUpdateQuery = " update tran_head SET " +
																"TH_DATE = NOW()  ,TH_SECTN_ID = "+mnrBean.getSectionId()+"  , " +
																"TH_MONTH = "+AppUtil.getMnthId(mnrBean.getMonthId())+"  ,TH_YEAR = "+mnrBean.getYearId()+"  , " +
																"TH_ROW_STATUS = 0  ," +
																"TH_IDEN_FLAG = "+stage+", " +
																"TH_MNR_REASON = '"+mnrBean.getMnrRsonFrNtReplace()+"' " +
																"WHERE TH_ID = "+mnrBean.getTransHeadId()+" ";
								preparedStatement6 = connection.prepareStatement(transHeadUpdateQuery);
								preparedStatement6.executeUpdate();
							
								
								String mnrGr1UpdateQuery = "update mnr_detail SET   " +
															"  MDET_OB = "+mnrBean.getMnrNineOpenBal()+"         " +
															"  ,MDET_INSTALATION = "+mnrBean.getMnrInstall()+" " +
															"  ,MDET_REPLACED = "+mnrBean.getMnrReplace()+"    " +
															"  ,MDET_CB = "+mnrBean.getMnrNineCloseBal()+"          " +
															"  ,MDET_ROW_STATUS = 0  " +
															" WHERE MDET_TH_ID = "+mnrBean.getTransHeadId()+" and MDET_IDEN_FLAG = 1 " +
															" and MDET_ID = "+mnrBean.getMnrGr1MntId()+" ";	
							preparedStatement7 = connection.prepareStatement(mnrGr1UpdateQuery);
							preparedStatement7.executeUpdate();    
							
							
							String mnrGr3UpdateQuery = "update mnr_detail SET   " +
														"  MDET_OB = "+mnrBean.getMnrTenOpenBal()+"         " +
														"  ,MDET_INSTALATION = "+mnrBean.getGtThrmnrInsall()+" " +
														"  ,MDET_REPLACED = "+mnrBean.getGtThrmnrReplace()+"    " +
														"  ,MDET_CB = "+mnrBean.getMnrTenCloseBal()+"          " +
														"  ,MDET_ROW_STATUS = 0  " +
														" WHERE MDET_TH_ID = "+mnrBean.getTransHeadId()+" and MDET_IDEN_FLAG = 2 " +
														" and MDET_ID = "+mnrBean.getMnrGr3MntId()+" ";	
							preparedStatement8= connection.prepareStatement(mnrGr3UpdateQuery);
							preparedStatement8.executeUpdate();    
		
		
		
							String mnrGr6UpdateQuery = "update mnr_detail SET   " +
														"  MDET_OB = "+mnrBean.getMnrElevenOpenBal()+"         " +
														"  ,MDET_INSTALATION = "+mnrBean.getGtSixmnrInstall()+" " +
														"  ,MDET_REPLACED = "+mnrBean.getGtSixmnrReplace()+"    " +
														"  ,MDET_CB = "+mnrBean.getMnrElevenCloseBal()+"          " +
														"  ,MDET_ROW_STATUS = 0  " +
														" WHERE MDET_TH_ID = "+mnrBean.getTransHeadId()+" and MDET_IDEN_FLAG = 3 " +
														" and MDET_ID = "+mnrBean.getMnrGr6MntId()+" ";	
							preparedStatement9 = connection.prepareStatement(mnrGr6UpdateQuery);
							preparedStatement9.executeUpdate();    
			}
			if(mnrBean.isViewOnlyOB()){
				String query5 ="UPDATE balance_ason ba "
								  + "SET ba.BLASON_OB_GRT6 = "+mnrBean.getMnrElevenCloseBal()+",  ba.BLASON_OB_3TO6 = "+mnrBean.getMnrTenCloseBal()+", " 
								  + "ba.BLASON_OB_1TO3 = "+mnrBean.getMnrNineCloseBal()+", ba.BLASON_LASTUPDATED = CURRENT_DATE() "
								  + "WHERE ba.blason_level_iden=5 AND ba.BLASON_LEVEL_FK_ID = "+mnrBean.getSectionId()+" AND ba.BLASON_IDEN_FLAG = "+stage+" " ;
					pst1 = connection.prepareStatement(query5);
					pst1.execute();
			}
			
			
			
			
			connection.commit();
			connection.setAutoCommit(true);
			isValid = true;
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement, connection);
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement1, connection);
		}
		return isValid;
	}
	
	
	public MNRBean getExistingMNR(MNRBean bean){

		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement1 = null;
		try {
			String existMNRQuery = "SELECT md.MDET_OB, md.MDET_ID, th.TH_MNR_REASON, " +
									" md.MDET_INSTALATION, md.MDET_REPLACED, md.MDET_CB, " +
									" th.TH_SECTN_ID, th.TH_MONTH ,th.TH_YEAR, th.TH_ID" +
									" FROM mnr_detail md JOIN tran_head th ON md.MDET_TH_ID = th.TH_ID" +
									" WHERE  md.MDET_ROW_STATUS = 0 and " +
									" th.TH_MONTH = "+AppUtil.getMnthId(bean.getMonthId())+" and th.TH_YEAR = "+bean.getYearId()+" and th.TH_SECTN_ID = "+bean.getSectionId()+" " +
									" and th.TH_IDEN_FLAG = 9 ORDER BY md.MDET_IDEN_FLAG";
			                                                                       
			connection =  MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(existMNRQuery);
			
			resultSet = preparedStatement.executeQuery();
			
			bean.setMnrNineCloseBal(0);
			bean.setMnrInstall(0);
			bean.setMnrReplace(0);
			bean.setTransHeadId(0);
			bean.setMnrGr1MntId(0);
			bean.setMnrRsonFrNtReplace("");
			
			bean.setMnrGr3MntId(0);
			bean.setMnrTenCloseBal(0);
			bean.setGtThrmnrInsall(0);
			bean.setGtThrmnrReplace(0);
			
			bean.setMnrGr6MntId(0);
			bean.setMnrElevenCloseBal(0);
			bean.setGtSixmnrInstall(0);
			bean.setGtSixmnrReplace(0);
			
			int count = 0;
			while(resultSet.next()){
				if(count==0){
					bean.setMnrGr1MntId(resultSet.getInt("MDET_ID"));
					bean.setMnrNineOpenBal(resultSet.getInt("MDET_OB"));
					bean.setMnrNineCloseBal(resultSet.getInt("MDET_CB"));
					bean.setMnrInstall(resultSet.getInt("MDET_INSTALATION"));
					bean.setMnrReplace(resultSet.getInt("MDET_REPLACED"));
					bean.setSectionId(resultSet.getString("TH_SECTN_ID"));
					bean.setYearId(resultSet.getString("TH_YEAR"));
					bean.setTransHeadId(resultSet.getInt("TH_ID"));
					bean.setMnrRsonFrNtReplace(resultSet.getString("TH_MNR_REASON"));
					count++;
				}else if(count ==1){
					bean.setMnrGr3MntId(resultSet.getInt("MDET_ID"));
					bean.setMnrTenOpenBal(resultSet.getInt("MDET_OB"));
					bean.setMnrTenCloseBal(resultSet.getInt("MDET_CB"));
					bean.setGtThrmnrInsall(resultSet.getInt("MDET_INSTALATION"));
					bean.setGtThrmnrReplace(resultSet.getInt("MDET_REPLACED"));
					count++;
				}else if(count ==2){
					bean.setMnrGr6MntId(resultSet.getInt("MDET_ID"));
					bean.setMnrElevenOpenBal(resultSet.getInt("MDET_OB"));
					bean.setMnrElevenCloseBal(resultSet.getInt("MDET_CB"));
					bean.setGtSixmnrInstall(resultSet.getInt("MDET_INSTALATION"));
					bean.setGtSixmnrReplace(resultSet.getInt("MDET_REPLACED"));
				}
				
			}
			String queryForNxtMnthValue = "select ifnull(max(a),0) as mnyr from " +
										"(select concat(cast(max(t.TH_YEAR) as char),cast(max(t.TH_MONTH) as char)) as a from tran_head t " +
										"where t.TH_IDEN_FLAG=9 and t.TH_ROW_STATUS=0 " +
										"and t.TH_SECTN_ID="+bean.getSectionId()+" group by t.TH_YEAR ) as b ";

			preparedStatement1 = connection.prepareStatement(queryForNxtMnthValue);
			resultSet = preparedStatement1.executeQuery();
			bean.setOnlyView(false);
			
			while(resultSet.next()){
			
				String year = bean.getYearId();
				String mnth =AppUtil.getMnthId(bean.getMonthId());
				String mnthYr = year.concat(mnth);
				int monthYear = Integer.valueOf(mnthYr);
				String rsMntYr = "";
				String rsMntYrString = resultSet.getString(1);
				if(rsMntYrString.length() == 5){
				rsMntYr =rsMntYrString.substring(0, 4) + "0" + rsMntYrString.substring(4, 5);
				
				}else{
					rsMntYr = resultSet.getString(1);
				}
				
				if("0".equals(rsMntYr)){
					bean.setOnlyView(false);	
				}else if(Integer.valueOf(monthYear) >= Integer.valueOf(rsMntYr)){
					bean.setOnlyView(false);
				}else{
					bean.setOnlyView(true);
				}
			}
			connection.commit();
			connection.setAutoCommit(true);
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement, connection);
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement1, connection);
		}
		
		return bean;
	}
	
	
	
}
