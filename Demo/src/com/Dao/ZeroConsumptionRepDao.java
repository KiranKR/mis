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

public class ZeroConsumptionRepDao {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	
	public List<ZeroConsumptionBean> getZeroConRep(ZeroConsumptionBean bean){
		String tempMonthID = "";
		ZeroConsumptionBean consBean = null;
		DiscrepancyBean discrepancyBean = null;
		List<DiscrepancyBean> lstDbean = null;
		List<ZeroConsumptionBean> lstZCBean = new ArrayList<ZeroConsumptionBean>();
		
		double totalRaised = 0.00;
		int totalConsumption = 0;
		
		double grandTotalRaised = 0.00;
		int grandTotalConsum = 0;
		
		try {
			String query = "SELECT th.TH_MONTH, " +
					"dd.CRPDET_NOOF_CONSMPN, d.DCREP_NAME, dd.CRPDET_DMND_RAISED, th.TH_CRPDET_INSPECTION " +
					"FROM discrepancy_detail dd JOIN tran_head th ON dd.CRPDET_TH_ID = th.TH_ID " +
					"JOIN discrepancy d ON dd.CRPDET_DCREP_ID = d.DCREP_ID " +
					"WHERE th.TH_MONTH >=4 " +
					"and th.TH_MONTH <= MONTH(CURDATE()) AND th.TH_YEAR = "+bean.getYearId()+" " +
					"AND th.TH_SECTN_ID = "+bean.getSecId()+" and th.TH_IDEN_FLAG = 7 " +
					"ORDER BY th.TH_MONTH , d.DCREP_ID ";
                               	        
			connection =  MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(query);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				if("".equals(tempMonthID)){
					tempMonthID = resultSet.getString("TH_MONTH");
					consBean = new ZeroConsumptionBean();
					consBean.setMonthId(AppUtil.getMonth(resultSet.getString("TH_MONTH")));
					consBean.setNoInspection(resultSet.getInt("TH_CRPDET_INSPECTION"));
					
					lstDbean = new ArrayList<DiscrepancyBean>();
					discrepancyBean = new DiscrepancyBean();
					discrepancyBean.setDiscrepancyNme(resultSet.getString("DCREP_NAME"));
					
					discrepancyBean.setNoOfZeroConsum(resultSet.getInt("CRPDET_NOOF_CONSMPN"));
					totalConsumption = resultSet.getInt("CRPDET_NOOF_CONSMPN");
					grandTotalConsum = resultSet.getInt("CRPDET_NOOF_CONSMPN");
					
					discrepancyBean.setDemandRasied(resultSet.getDouble("CRPDET_DMND_RAISED"));
					totalRaised = resultSet.getDouble("CRPDET_DMND_RAISED");
					grandTotalRaised = resultSet.getDouble("CRPDET_DMND_RAISED");
					
					consBean.setTotalDemandRaised(totalRaised);
					consBean.setTotalNoZeroCon(totalConsumption);
					lstDbean.add(discrepancyBean);
					consBean.setList(lstDbean);
				}else if(!tempMonthID.equals(resultSet.getString("TH_MONTH"))){
					
					lstZCBean.add(consBean);
					tempMonthID = resultSet.getString("TH_MONTH");
					consBean = new ZeroConsumptionBean();
					consBean.setMonthId(AppUtil.getMonth(resultSet.getString("TH_MONTH")));
					consBean.setNoInspection(resultSet.getInt("TH_CRPDET_INSPECTION"));
					
					totalRaised = 0.00;
					totalConsumption = 0;
					lstDbean = new ArrayList<DiscrepancyBean>();
					discrepancyBean = new DiscrepancyBean();
					discrepancyBean.setDiscrepancyNme(resultSet.getString("DCREP_NAME"));
					
					discrepancyBean.setNoOfZeroConsum(resultSet.getInt("CRPDET_NOOF_CONSMPN"));
					totalConsumption =totalConsumption+ resultSet.getInt("CRPDET_NOOF_CONSMPN");
					grandTotalConsum = grandTotalConsum + resultSet.getInt("CRPDET_NOOF_CONSMPN");
					
					discrepancyBean.setDemandRasied(resultSet.getDouble("CRPDET_DMND_RAISED"));
					totalRaised = totalRaised + resultSet.getDouble("CRPDET_DMND_RAISED");
					grandTotalRaised = grandTotalRaised + resultSet.getDouble("CRPDET_DMND_RAISED");
					
					lstDbean.add(discrepancyBean);
					consBean.setTotalDemandRaised(totalRaised);
					consBean.setTotalNoZeroCon(totalConsumption);
					consBean.setList(lstDbean);
				}else {
					discrepancyBean = new DiscrepancyBean();
					discrepancyBean.setDiscrepancyNme(resultSet.getString("DCREP_NAME"));
					
					discrepancyBean.setNoOfZeroConsum(resultSet.getInt("CRPDET_NOOF_CONSMPN"));
					totalConsumption = totalConsumption + resultSet.getInt("CRPDET_NOOF_CONSMPN");
					grandTotalConsum = grandTotalConsum + resultSet.getInt("CRPDET_NOOF_CONSMPN");
					
					
					discrepancyBean.setDemandRasied(resultSet.getDouble("CRPDET_DMND_RAISED"));
					totalRaised = totalRaised + resultSet.getDouble("CRPDET_DMND_RAISED");
					grandTotalRaised =grandTotalRaised + resultSet.getDouble("CRPDET_DMND_RAISED");
					
					consBean.setTotalDemandRaised(totalRaised);
					consBean.setTotalNoZeroCon(totalConsumption);
					lstDbean.add(discrepancyBean);
					consBean.setList(lstDbean);
				}
				if(resultSet.isLast()){
					consBean.setGrandTotlZeroCon(grandTotalConsum);
					consBean.setGrandTotlDemaRaised(grandTotalRaised);
				}
			}
			lstZCBean.add(consBean);
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
		return lstZCBean;
	}
}
