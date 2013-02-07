package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Util.AppUtil;
import com.Util.MysqlConnectionProvider;
import com.bean.Feeder;
import com.bean.FeederInterruptionBean;

public class FeederInterruptionDao {
	
	
	/*public List<Feeder> getFeederListCons()	{
		Feeder feeder = null;
		Connection	con1 = null;
		PreparedStatement pst1 = null;
		ResultSet resultSet = null;
		
		List<Feeder> feeders = new ArrayList<Feeder>();
		List<Feeder> lstFeedDrpDwn = new ArrayList<Feeder>();
		String sql_str1="SELECT f.FDR_ID As fdr_id, f.FDR_NAME As fdr_name FROM feeder f WHERE FDR_ROW_STATUS = 0 " ;
		
	
		try {
	        con1 = MysqlConnectionProvider.getNewConnection();
			pst1 = con1.prepareStatement(sql_str1);
			resultSet = pst1.executeQuery();
			while(resultSet.next()){
				feeder = new Feeder();
				feeder.setFdr_id(resultSet.getInt("FDR_ID"));
				feeder.setFdr_Nmae(resultSet.getString("FDR_NAME"));
				feeders.add(feeder);
				}
	        }catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}finally {   
				MysqlConnectionProvider.releaseConnection(resultSet, null, pst1, con1);
			}
			return feeders;
	}*/

	public List<Feeder> getFeederList(FeederInterruptionBean bean){
		
		Feeder feeder = null;
		Connection	con1 = null;
		PreparedStatement pst1 = null;
		ResultSet resultSet = null;
		int totalTrippedCount = 0;
		int stage = 1;
		
		List<Feeder> feeders = new ArrayList<Feeder>();
		List<Feeder> lstFeedDrpDwn = new ArrayList<Feeder>();
		
		
		String sql_str1="SELECT " +
							"th.TH_MONTH, th.TH_YEAR, th.TH_SECTN_ID,  " +
							"fd.FDR_ID, fd.FDR_NAME, " +
							"fl.FL_FDR_TRIPPED_COUNT AS TRIPP_COUNT, fl.FL_FDR_REASON AS REASON , th.TH_ID " +
							"FROM  feeder_line fl  " +
							"INNER JOIN tran_head th ON fl.FL_TH_ID = th.TH_ID  " +
							"LEFT OUTER JOIN feeder fd ON fl.FL_FDR_ID = fd.FDR_ID" +
							" JOIN section s ON th.TH_SECTN_ID = s.SECTN_ID " +
					"WHERE " +
							"s.SECTN_ID = "+bean.getSectionId()+" AND th.TH_MONTH = "+AppUtil.getMnthId(bean.getMonthId())+" " +
							"AND th.TH_YEAR = "+bean.getYearId()+" and th.TH_IDEN_FLAG = "+stage+" " +
					"UNION " +
					"SELECT  " +
							" "+AppUtil.getMnthId(bean.getMonthId())+", "+bean.getYearId()+",  "+bean.getSectionId()+",  " +
							" fd1.FDR_ID,  " +
							" fd1.FDR_NAME,  " +
							" 0 AS FL_FDR_TRIPPED_COUNT, " +
							" "+null+" AS FL_FDR_REASON, " +
							" 0 as TH_ID " +
									"FROM  feeder fd1 " +
									"WHERE fd1.FDR_ID NOT IN " +
										"(SELECT fl1.FL_FDR_ID  " +
											"FROM feeder_line fl1 " +
												"INNER JOIN tran_head th1 ON fl1.FL_TH_ID = th1.TH_ID " +
												" WHERE  th1.TH_MONTH = "+AppUtil.getMnthId(bean.getMonthId())+" AND th1.TH_YEAR = "+bean.getYearId()+" " +
														"AND  th1.TH_SECTN_ID = "+bean.getSectionId()+" and th1.TH_IDEN_FLAG = "+stage+") ORDER BY 4 " ;
		
	
		try {
	        con1 = MysqlConnectionProvider.getNewConnection();
			pst1 = con1.prepareStatement(sql_str1);
			resultSet = pst1.executeQuery();
			while(resultSet.next()){
				feeder = new Feeder();
				feeder.setFdr_id(resultSet.getInt("FDR_ID"));
				feeder.setTransHeadFdr(resultSet.getInt("TH_ID"));
				feeder.setFdr_Nmae(resultSet.getString("FDR_NAME"));
				feeder.setFdr_rsn(resultSet.getString("REASON"));
				feeder.setFdrtripped_count(resultSet.getInt("TRIPP_COUNT"));
				totalTrippedCount +=  resultSet.getInt("TRIPP_COUNT");
				feeder.setTotalcount(totalTrippedCount);
				feeders.add(feeder);
				}
			
	        }catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}finally {   
				MysqlConnectionProvider.releaseConnection(resultSet, null, pst1, con1);
			}
			return feeders;
		}

	

	public boolean saveFDI(FeederInterruptionBean fibean) {
		
		boolean isValid = false;
		int stage = 1;
		String th_id=null;
		
		Connection con3 = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		PreparedStatement pst4 = null;
		PreparedStatement pst5 = null;
		PreparedStatement pst6 = null;
		ResultSet rs3 = null;
		
		
		try {
			
            
		    con3 =  MysqlConnectionProvider.getNewConnection();
		    con3.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			con3.setAutoCommit(false);
			if(fibean.getTransHead() == 0){
				
				String queryTransHeadInsert = " INSERT INTO tran_head(TH_SECTN_ID,TH_MONTH,TH_YEAR,TH_ROW_STATUS,TH_IDEN_FLAG,TH_DATE) " +
				" VALUES ("+fibean.getSectionId()+","+AppUtil.getMnthId(fibean.getMonthId())+", "+fibean.getYearId()+", " +
				" 0, "+stage+", NOW()) ";
				
				pst = con3.prepareStatement(queryTransHeadInsert);
				pst.execute();
				
				String queryMaxID="SELECT ifnull(max(th.TH_ID), 1) FROM tran_head th";
				pst2 = con3.prepareStatement(queryMaxID);
				rs3=pst2.executeQuery();
				
				while(rs3.next()){
					fibean.setTransHead(rs3.getInt(1)); 			
					}
				
				
				
			}else if(fibean.getTransHead() != 0){
				String queryTransHeadUpdate = " update tran_head SET " +
												"TH_DATE = NOW()  ,TH_SECTN_ID = "+fibean.getSectionId()+"  , " +
												"TH_MONTH = "+AppUtil.getMnthId(fibean.getMonthId())+"  ,TH_YEAR = "+fibean.getYearId()+"  , " +
												"TH_ROW_STATUS = 0  ,TH_IDEN_FLAG = "+stage+" " +
												"WHERE TH_ID = "+fibean.getTransHead()+" ";
				
				pst3 = con3.prepareStatement(queryTransHeadUpdate);
				pst3.executeUpdate();
				
				String queryDeleteFderLin = "delete  from feeder_line WHERE FL_TH_ID = "+fibean.getTransHead()+" ";
				
				pst4 = con3.prepareStatement(queryDeleteFderLin);
				pst4.execute();
			}
			
				
		
			
			List<Feeder> feederBeenValues= null;
			feederBeenValues=fibean.getFeedList();
			
			for(Feeder feedBean : feederBeenValues){
				if(feedBean.getFdrtripped_count() > 0){
					String query2="INSERT INTO feeder_line(FL_TH_ID, FL_FDR_ID,FL_FDR_TRIPPED_COUNT,FL_FDR_REASON,FL_ROW_STATUS)VALUES ("+fibean.getTransHead()+" ,"+feedBean.getFdr_id()+","+feedBean.getFdrtripped_count()+",'"+feedBean.getFdr_rsn()+"',0)";
					pst5 = con3.prepareStatement(query2);
					pst5.execute();
				
				}
				
			}
			con3.commit();
			con3.setAutoCommit(true);
			isValid = true;
		} 
		catch (SQLException e) {
			try {
				con3.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			try {
				con3.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally {   
			MysqlConnectionProvider.releaseConnection(rs3, null, pst, con3);
			MysqlConnectionProvider.releaseConnection(null, null, pst2, null);
			MysqlConnectionProvider.releaseConnection(null, null, pst3, null);
			MysqlConnectionProvider.releaseConnection(null, null, pst4, null);
			MysqlConnectionProvider.releaseConnection(null, null, pst5, null);
		}
		return isValid;
	}
	
	
	
}

