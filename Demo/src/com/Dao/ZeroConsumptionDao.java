package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Util.AppUtil;
import com.Util.MysqlConnectionProvider;
import com.bean.DiscrepancyBean;
import com.bean.ZeroConsumptionBean;

public class ZeroConsumptionDao {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	public List<DiscrepancyBean> getDiscrepancyTable(){
		DiscrepancyBean consumBean = null;
		List<DiscrepancyBean> lstDiscrepancy = null;
		try {
			String query = "select dis.DCREP_ID, dis.DCREP_NAME from discrepancy dis where dis.DCREP_ROW_STATUS = 0";
                               	        
			                                                                        
			                                                                       
			connection =  MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(query);
			
			resultSet = preparedStatement.executeQuery();
			lstDiscrepancy = new ArrayList<DiscrepancyBean>();
			while(resultSet.next()){
				consumBean = new DiscrepancyBean();
				consumBean.setDiscrepancyId(resultSet.getString("DCREP_ID"));
				consumBean.setDiscrepancyNme(resultSet.getString("DCREP_NAME"));
				lstDiscrepancy.add(consumBean);
				
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
		return lstDiscrepancy;
	}

	public ZeroConsumptionBean getZeroConList(ZeroConsumptionBean bean){
		
		DiscrepancyBean discBean = null;
		List<DiscrepancyBean> discBeanList = new ArrayList<DiscrepancyBean>();
		int grandTotlZeroCon = 0;
		double grandTotlDemaRaised = 0.0;
		
		String query1="SELECT d.DCREP_ID, "
						+ "d.DCREP_NAME, "
						+ "dt.CRPDET_TH_ID, "
						+ "dt.CRPDET_DMND_RAISED, "
						+ "dt.CRPDET_NOOF_CONSMPN, "
						+ "th.TH_ID, "
						+ "th.TH_SECTN_ID, "
						+ "th.TH_MONTH, "
						+ "th.TH_YEAR, "
						+ "th.TH_CRPDET_INSPECTION, "
						+ "th.TH_CRPDET_REMARKS "
						+ "FROM discrepancy_detail dt "
						+ "INNER JOIN tran_head th "
						+ "ON dt.CRPDET_TH_ID = th.TH_ID "
						+ "LEFT OUTER JOIN discrepancy d "
						+ "ON dt.CRPDET_DCREP_ID = d.DCREP_ID "
						+ "JOIN section s "
						+ "ON th.TH_SECTN_ID = s.SECTN_ID "
						+ "WHERE th.TH_SECTN_ID = '"+bean.getSecId()+"' "
						+ "AND th.TH_MONTH = '"+AppUtil.getMnthId(bean.getMonthId())+"' "
						+ "AND th.TH_YEAR = '"+bean.getYearId()+"' "
						+ "AND th.TH_IDEN_FLAG = 7 "
						+ "UNION "
						+ "SELECT d1.DCREP_ID, "
						+ "d1.DCREP_NAME, "
						+ "0 AS CRPDET_TH_ID,"
						+ "0 AS CRPDET_DMND_RAISED, "
						+ "0 AS CRPDET_NOOF_CONSMPN, "
						+ "0 AS TH_ID, "
						+ "'"+bean.getSecId()+"' AS TH_SECTN_ID , "
						+ "'"+AppUtil.getMnthId(bean.getMonthId())+"' AS TH_MONTH, "
						+ "'"+bean.getYearId()+"' AS TH_YEAR, "
						+ "0 AS TH_CRPDET_INSPECTION, "
						+ ""+null+" AS TH_CRPDET_REMARKS "
						+ "FROM discrepancy d1 "
						+ "WHERE d1.DCREP_ID NOT IN "
						+ "(SELECT dt1.CRPDET_DCREP_ID "
						+ "FROM discrepancy_detail dt1 "
						+ "INNER JOIN "
						+ "tran_head th1 "
						+ "ON dt1.CRPDET_TH_ID = th1.TH_ID "
						+ "WHERE th1.TH_SECTN_ID = '"+bean.getSecId()+"' "
						+ "AND th1.TH_MONTH = '"+AppUtil.getMnthId(bean.getMonthId())+"' "
						+ "AND th1.TH_YEAR = '"+bean.getYearId()+"' "
						+ "AND th1.TH_IDEN_FLAG = 7) "
						+ "ORDER BY 1 ";
		
		
		try {
			connection =  MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(query1);
			resultSet = preparedStatement.executeQuery();
			
			
			
			while (resultSet.next()) {
				discBean = new DiscrepancyBean();
				discBean.setDiscrepancyId(resultSet.getString("DCREP_ID"));
				discBean.setDiscThId(resultSet.getString("CRPDET_TH_ID"));
				discBean.setDiscrepancyNme(resultSet.getString("DCREP_NAME"));
				discBean.setNoOfZeroConsum(resultSet.getInt("CRPDET_NOOF_CONSMPN"));
				discBean.setDemandRasied(resultSet.getInt("CRPDET_DMND_RAISED"));
				
				grandTotlDemaRaised=grandTotlDemaRaised + resultSet.getInt("CRPDET_DMND_RAISED");
				grandTotlZeroCon =grandTotlZeroCon + resultSet.getInt("CRPDET_NOOF_CONSMPN");
				
				discBeanList.add(discBean);
				bean.setList(discBeanList);
				
				if(resultSet.isLast()){
					
					bean.setNoInspection(resultSet.getInt("TH_CRPDET_INSPECTION"));
					bean.setReason(resultSet.getString("TH_CRPDET_REMARKS"));
					bean.setGrandTotlZeroCon(grandTotlZeroCon);
				    bean.setGrandTotlDemaRaised(grandTotlDemaRaised);
				}
				
				
				
			}
			
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
	
	public boolean saveZeroConsum(ZeroConsumptionBean bean){
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		PreparedStatement preparedStatement4 = null;
		boolean isValid  = false;
		try {
			connection =  MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(8);
			
			if(bean.getTransHeadId().equals("")){
				
			String insertQueryTH = "insert into tran_head " +
					"(TH_SECTN_ID, TH_MONTH , TH_YEAR, TH_ROW_STATUS, TH_IDEN_FLAG, TH_CRPDET_REMARKS, TH_DATE, TH_CRPDET_INSPECTION) " +
					"VALUES ( "+bean.getSecId()+", " +
							" "+ AppUtil.getMnthId(bean.getMonthId())+", " +
							" "+ bean.getYearId()+", " +
							" 0 , " +
							" 7, " +
							" '"+ bean.getReason()+"' , " +
							" NOW(), "+bean.getNoInspection()+" ) ";
			
			preparedStatement = connection.prepareStatement(insertQueryTH);
			preparedStatement.execute();
			
			
			
			String maxQuery = "select ifnull(max(th.TH_ID),1)from tran_head th";
			preparedStatement2 = connection.prepareStatement(maxQuery);
			resultSet = preparedStatement2.executeQuery();
			
			while(resultSet.next()){
				bean.setTransHeadId(resultSet.getString(1));
			}
			}
			else if (!bean.getTransHeadId().equals("0")) {
				
				 String updateQueryTH ="update tran_head SET " +
									   "TH_DATE = NOW()  ,TH_SECTN_ID = "+bean.getSecId()+"  , " +
									   "TH_MONTH = "+AppUtil.getMnthId(bean.getMonthId())+"  ,TH_YEAR = "+bean.getYearId()+"  , " +
									   "TH_CRPDET_REMARKS='"+bean.getReason()+"' , TH_CRPDET_INSPECTION ='"+bean.getNoInspection()+"' , " +
									   "TH_ROW_STATUS = 0  ,TH_IDEN_FLAG = 7 " +
									   "WHERE TH_ID = "+bean.getTransHeadId()+" "; 
				
				preparedStatement = connection.prepareStatement(updateQueryTH);
				preparedStatement.execute();
				
				String DeleteQueryTh = " delete from discrepancy_detail WHERE CRPDET_TH_ID = "+bean.getTransHeadId()+" ";
				
				preparedStatement4 = connection.prepareStatement(DeleteQueryTh);
				preparedStatement4.execute();
			}
			List<DiscrepancyBean> lstDiscreBeans = bean.getList();
			int count = 0;
			for (DiscrepancyBean discrepancyBean : lstDiscreBeans) {
				if(!(0 == discrepancyBean.getNoOfZeroConsum()) || ! (0.0 == discrepancyBean.getDemandRasied())){
					count = 1;
					String insertQueryDD = "insert into discrepancy_detail " +
					" (CRPDET_TH_ID ,CRPDET_DCREP_ID ,CRPDET_NOOF_CONSMPN ,CRPDET_DMND_RAISED ,CRPDET_ROW_STATUS) " +
					" VALUES ("+bean.getTransHeadId()+" ,"+discrepancyBean.getDiscrepancyId()+","+discrepancyBean.getNoOfZeroConsum()+","+discrepancyBean.getDemandRasied()+" ,0 ) ";
					preparedStatement3 = connection.prepareStatement(insertQueryDD);
					preparedStatement3.execute();
				}
				
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
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement, connection);
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement2, connection);
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement3, connection);
		}
		
		return isValid;
	}
	
	
	
	
	
	
}
