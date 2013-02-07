package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Util.AppUtil;
import com.Util.MysqlConnectionProvider;
import com.bean.RevenueBean;

public class RevenueDao {
	

	
	public RevenueBean getOpenCloseBal(RevenueBean revenueBean){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		double openBal = 0;
		try {
			String query = "select BLASON_OB_AMOUNT, BLASON_ID  from balance_ason where blason_iden_flag=4 and blason_level_iden=5 and BLASON_LEVEL_FK_ID= "+revenueBean.getSectionId()+" ";
                               	        
			                                                                        
			                                                                       
			connection =  MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(query);
			
			resultSet = preparedStatement.executeQuery();
			revenueBean.setViewOlyOB(false);
			while(resultSet.next()){
				revenueBean.setViewOlyOB(true);
				openBal = resultSet.getDouble("BLASON_OB_AMOUNT");
				revenueBean.setBalanceAsonID(resultSet.getInt("BLASON_ID"));
			}
			revenueBean.setOpenBal(openBal);
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
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement, connection);
		}
		
		return revenueBean;
	}
	
	
	
	
	public boolean saveRevenue(RevenueBean bean){
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		PreparedStatement preparedStatement4 = null;
		PreparedStatement preparedStatement5 = null;
		PreparedStatement preparedStatement6 = null;
		PreparedStatement preparedStatement7 = null;
		int stage = 4;
		boolean isValid = false;
		try {
					connection =  MysqlConnectionProvider.getNewConnection();
			
					connection.setAutoCommit(false);
					connection.setTransactionIsolation(8);
					if("".equals(bean.getTransHeadId())){
						
						String query = "insert into tran_head " +
								"(TH_SECTN_ID, TH_MONTH , TH_YEAR, TH_ROW_STATUS, TH_IDEN_FLAG, TH_DATE) " +
								"VALUES ( "+bean.getSectionId()+", " +
										" "+AppUtil.getMnthId(bean.getMonthId())+", " +
										" "+bean.getYearId()+", " +
										" 0 , " +
										" "+stage+", " +
										" NOW() ) ";
						preparedStatement = connection.prepareStatement(query);
						preparedStatement.execute();
						
						String maxQuery = "select ifnull(max(th.TH_ID),1)from tran_head th";
						preparedStatement2 = connection.prepareStatement(maxQuery);
						resultSet = preparedStatement2.executeQuery();
						
						while(resultSet.next()){
							bean.setTransHeadId(resultSet.getString(1));
						}
						
						
						String revenueQuery = "insert into " +
											"revenue (REV_TH_ID  ,REV_TOTAL_INSTALATION  ,REV_INPUT_ENERGY,  " +
											"REV_DCB_ENERGY ,REV_DCB_OB ,REV_DEMAND ,REV_COLLECTION ,REV_DCB_CB , " +
											"REV_REMARKS ,REV_ROW_STATUS) " +
											"VALUES ( " +
											" "+bean.getTransHeadId()+", "+bean.getTotalNoInstall()+" , "+bean.getInputEnergy()+", " +
											" "+bean.getEngSoldPerDCB()+" , "+bean.getOpenBal()+", "+bean.getDemand()+", " +
											" "+bean.getCollection()+" , "+bean.getCloseBal()+" , '"+bean.getReason()+"' , 0) ";
						preparedStatement3 = connection.prepareStatement(revenueQuery);
						preparedStatement3.execute();
						
						if(!bean.isViewOlyOB()){
							
							String balanceAsonInsertQuery = "insert into balance_ason ( " +
															" BLASON_LEVEL_IDEN         " +
															" ,BLASON_LEVEL_FK_ID       " +
															" ,BLASON_IDEN_FLAG         " +
															" ,BLASON_OB_AMOUNT         " +
															" ,BLASON_LASTUPDATED       " +
															" ) VALUES ( 5 ,"+bean.getSectionId()+" ,"+stage+" ,"+bean.getCloseBal()+" ,NOW() ) ";
															
							preparedStatement7 = connection.prepareStatement(balanceAsonInsertQuery);
							preparedStatement7.execute();
						}
						
					}else {
						String transHeadUpdateQuery = " update tran_head SET " +
														"TH_DATE = NOW()  ,TH_SECTN_ID = "+bean.getSectionId()+"  , " +
														"TH_MONTH = "+AppUtil.getMnthId(bean.getMonthId())+"  ,TH_YEAR = "+bean.getYearId()+"  , " +
														"TH_ROW_STATUS = 0  ,TH_IDEN_FLAG = "+stage+" " +
														"WHERE TH_ID = "+bean.getTransHeadId()+" ";
						preparedStatement4 = connection.prepareStatement(transHeadUpdateQuery);
						preparedStatement4.executeUpdate();
						
						                                                       
						String revenueUpdateQuery = " update revenue SET "    
												+ "  REV_TOTAL_INSTALATION = "+bean.getTotalNoInstall()+"  "
												+ "  ,REV_INPUT_ENERGY = "+bean.getInputEnergy()+"  "
												+ "  ,REV_DCB_ENERGY = "+bean.getEngSoldPerDCB()+"  "
												+ "  ,REV_DCB_OB = "+bean.getOpenBal()+"  "
												+ "  ,REV_DEMAND = "+bean.getDemand()+"   "
												+ "  ,REV_COLLECTION = "+bean.getCollection()+" "
												+ "  ,REV_DCB_CB = "+bean.getCloseBal()+" "
												+ "  ,REV_REMARKS = '"+bean.getReason()+" ' "
												+ "  ,REV_ROW_STATUS = 0 "
												+ "  WHERE REV_ID = "+bean.getRevenueID()+"  and REV_TH_ID = "+bean.getTransHeadId()+" ";       
						preparedStatement5 = connection.prepareStatement(revenueUpdateQuery);
						preparedStatement5.executeUpdate();
						
					}
					if(bean.isViewOlyOB()){
						
						String balanceQuery = "update balance_ason SET " +
												"BLASON_OB_AMOUNT = "+bean.getCloseBal()+" , " +
												"BLASON_LASTUPDATED = NOW() " +
												"WHERE BLASON_ID = "+bean.getBalanceAsonID()+" ";
						
						
						preparedStatement6 = connection.prepareStatement(balanceQuery);
						preparedStatement6.executeUpdate();
						
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
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			MysqlConnectionProvider.releaseConnection(null, null, preparedStatement, connection);
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement2, connection);
			MysqlConnectionProvider.releaseConnection(null, null, preparedStatement3, connection);
			MysqlConnectionProvider.releaseConnection(null, null, preparedStatement4, connection);
		}
		return isValid;
	}
	
	
	public RevenueBean getExistingRevenue(RevenueBean bean){
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		ResultSet rs = null;
		try {
			String queryExistingRevenue = " SELECT " +
												" r.REV_ID, r.REV_DCB_OB, r.REV_DCB_CB, r.REV_DEMAND, r.REV_COLLECTION , " +
												" r.REV_REMARKS, r.REV_INPUT_ENERGY, r.REV_TOTAL_INSTALATION , r.REV_DCB_ENERGY, th.TH_ID , " +
												" th.TH_SECTN_ID, th.TH_MONTH ,th.TH_YEAR " +
										" FROM " +
												" revenue r JOIN tran_head th ON r.REV_TH_ID = th.TH_ID " +
										" WHERE " +
												" th.TH_IDEN_FLAG = 4 and th.TH_SECTN_ID = "+bean.getSectionId()+" AND th.TH_MONTH = "+AppUtil.getMnthId(bean.getMonthId())+" AND th.TH_YEAR = "+bean.getYearId()+" ";
                               	        
			                                                                        
			                                                                       
			connection =  MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(queryExistingRevenue);
			
			resultSet = preparedStatement.executeQuery();
			
			bean.setRevenueID(0);
			bean.setTransHeadId("");
			
			bean.setTotalNoInstall(0);
			bean.setInputEnergy(0);
			bean.setEngSoldPerDCB(0);
			bean.setDemand(0.00);
			bean.setCollection(0.00);
			bean.setCloseBal(0.00);
			bean.setReason("");
			
			while(resultSet.next()){
				bean.setRevenueID(resultSet.getInt("REV_ID"));
				bean.setTransHeadId(resultSet.getString("TH_ID"));
				
				bean.setSectionId(resultSet.getString("TH_SECTN_ID"));
				bean.setYearId(resultSet.getString("TH_YEAR"));
				
				bean.setTotalNoInstall(resultSet.getInt("REV_TOTAL_INSTALATION"));
				bean.setInputEnergy(resultSet.getInt("REV_INPUT_ENERGY"));
				bean.setEngSoldPerDCB(resultSet.getInt("REV_DCB_ENERGY"));
				bean.setOpenBal(resultSet.getDouble("REV_DCB_OB"));
				bean.setDemand(resultSet.getDouble("REV_DEMAND"));
				bean.setCollection(resultSet.getDouble("REV_COLLECTION"));
				bean.setCloseBal(resultSet.getDouble("REV_DCB_CB"));
				bean.setReason(resultSet.getString("REV_REMARKS"));
			}
			
			/*int nxtMnt = 0;
			int nxtYr = 0;
			if(AppUtil.getMnthId(bean.getMonthId()).equals("12")){
				nxtYr = Integer.parseInt(bean.getYearId()) + 1;
				nxtMnt = 01;
			}else{
				nxtYr = Integer.parseInt(bean.getYearId());
				nxtMnt = Integer.parseInt(AppUtil.getMnthId(bean.getMonthId())) + 1;
			}
			
				String queryForNxtMnthValue = " SELECT " +
												" r.REV_ID, r.REV_DCB_OB, r.REV_DCB_CB, r.REV_DEMAND, r.REV_COLLECTION , " +
												" r.REV_REMARKS, r.REV_INPUT_ENERGY, r.REV_TOTAL_INSTALATION , r.REV_DCB_ENERGY, th.TH_ID , " +
												" th.TH_SECTN_ID, th.TH_MONTH ,th.TH_YEAR " +
											" FROM " +
												" revenue r JOIN tran_head th ON r.REV_TH_ID = th.TH_ID " +
											" WHERE " +
												" th.TH_IDEN_FLAG = 4 and th.TH_SECTN_ID = "+bean.getSectionId()+" AND th.TH_MONTH = "+nxtMnt+" AND th.TH_YEAR = "+nxtYr+" ";*/
			
			String queryForNxtMnthValue = "select ifnull(max(a),0) as mnyr from " +
											"(select concat(cast(max(t.TH_YEAR) as char),cast(max(t.TH_MONTH) as char)) as a from tran_head t " +
											"where t.TH_IDEN_FLAG=4 and t.TH_ROW_STATUS=0 " +
											"and t.TH_SECTN_ID="+bean.getSectionId()+" group by t.TH_YEAR ) as b ";
				
				preparedStatement2 = connection.prepareStatement(queryForNxtMnthValue);
				rs = preparedStatement2.executeQuery();
				bean.setOnlyView(false);
				
				while(rs.next()){
					
					String year = bean.getYearId();
					String mnth =AppUtil.getMnthId(bean.getMonthId());
					String mnthYr = year.concat(mnth);
					int monthYear = Integer.valueOf(mnthYr);
					String rsMntYr = "";
					String rsMntYrString = rs.getString(1);
					if(rsMntYrString.length() == 5){
						rsMntYr =rsMntYrString.substring(0, 4) + "0" + rsMntYrString.substring(4, 5);
						
					}else{
						rsMntYr = rs.getString(1);
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
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement, connection);
		}
		
		return bean;
	}
}
