package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Util.MysqlConnectionProvider;
import com.bean.DiscrepancyBean;
import com.bean.ZeroConsumptionBean;

public class SummaryZeroConsumptionRepDao {
	
	
	public List<ZeroConsumptionBean>  getZeroConRep(String fromMnthYrConcat,String toMnthYrConcat, String role , String roleBelongs){
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		DiscrepancyBean discrepBean = null;
		ZeroConsumptionBean zcBean = null;
		List<DiscrepancyBean> lstDiscrepBean = null;
		List<ZeroConsumptionBean> lstZCBean = new ArrayList<ZeroConsumptionBean>();
		try {
			
			String wherClause = "";
			String groupBy = "";
			String nmeStr = "";
			
			String idNxt = "";
			String idCurrent = "";
			if("2".equals(role)){
				nmeStr ="CRCL_NAME";
				idNxt ="CRCL_ID";
				
				idCurrent = "ZONE_ID";
				wherClause = " and z.ZONE_ID = "+ roleBelongs +" " ;
				groupBy ="c.CRCL_ID";
			}else if("3".equals(role)){
				idNxt ="DIV_ID";
				nmeStr ="DIV_NAME";
				
				idCurrent = "CRCL_ID";
				wherClause = " and c.CRCL_ID = "+ roleBelongs +" " ;
				groupBy ="divi.DIV_ID";
			}else if("4".equals(role)){
				idNxt ="SUBDIV_ID";
				nmeStr ="SUBDIV_NAME";
				
				idCurrent = "DIV_ID";
				wherClause = " and divi.DIV_ID = "+ roleBelongs +" " ;
				groupBy ="sd.SUBDIV_ID";
			}else if("5".equals(role)){
				idNxt ="SECTN_ID";
				nmeStr ="SECTN_NAME";
				
				idCurrent = "SUBDIV_ID";
				wherClause = " and sd.SUBDIV_ID = "+ roleBelongs +" " ;
				groupBy ="s.SECTN_ID";
			}else if("1".equals(role)){
				idNxt ="ZONE_ID";
				nmeStr ="ZONE_NAME";
				
				idCurrent = "ZONE_ID";
				wherClause = " and z.ZONE_ID = "+ roleBelongs +" " ;
				groupBy ="z.ZONE_ID";
			}
			
			
			String query = "SELECT   th.TH_ID, th.TH_MONTH, th.TH_YEAR, z.ZONE_NAME,z.ZONE_ID, c.CRCL_NAME,c.CRCL_ID, divi.DIV_NAME,divi.DIV_ID, " +
						" 			sd.SUBDIV_NAME,sd.SUBDIV_ID, s.SECTN_NAME,s.SECTN_ID, d.DCREP_ID,d.DCREP_NAME, 								 " +
						"          sum(dd.CRPDET_DMND_RAISED) as CRPDET_DMND_RAISED , sum(dd.CRPDET_NOOF_CONSMPN) as CRPDET_NOOF_CONSMPN, sum(th.TH_CRPDET_INSPECTION) as TH_CRPDET_INSPECTION              " +
						" FROM     discrepancy_detail dd                                                                                 " +
						"          JOIN discrepancy d                                                                                    " +
						"            ON dd.CRPDET_DCREP_ID = d.DCREP_ID                                                                  " +
						"          JOIN tran_head th                                                                                     " +
						"            ON dd.CRPDET_TH_ID = th.TH_ID                                                                       " +
						"          JOIN section s                                                                                        " +
						"            ON th.TH_SECTN_ID = s.SECTN_ID                                                                      " +
						"          JOIN sub_division sd                                                                                  " +
						"            ON s.SECTN_SUBDIV_ID = sd.SUBDIV_ID                                                                 " +
						"          JOIN division divi                                                                                    " +
						"            ON sd.SUBDIV_DIV_ID = divi.DIV_ID                                                                   " +
						"          JOIN circle c                                                                                         " +
						"            ON divi.DIV_CIRCLE_ID = c.CRCL_ID                                                                   " +
						"          JOIN zone z                                                                                           " +
						"            ON c.CRCL_ZONE_ID = z.ZONE_ID                                                                       " +
						" WHERE      concat(th.TH_YEAR, th.TH_MONTH) >= "+fromMnthYrConcat+" AND concat(th.TH_YEAR, th.TH_MONTH) <= "+toMnthYrConcat+" " +
						" 		AND th.TH_IDEN_FLAG = 7 and d.DCREP_ROW_STATUS = 0 and th.TH_ROW_STATUS =0 and dd.CRPDET_ROW_STATUS=0   "+wherClause+" " +
						"       group by d.DCREP_ID, "+groupBy+"           " +
						" ORDER BY th.TH_ID,z.ZONE_ID, c.CRCL_ID,divi.DIV_ID, sd.SUBDIV_ID,th.TH_SECTN_ID,   d.DCREP_ID ";
                               	        
			connection =  MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(query);
			
			resultSet = preparedStatement.executeQuery();
			int id=0;
			while(resultSet.next()){
				if(id==0){
					id = resultSet.getInt(idNxt);
				
					zcBean = new ZeroConsumptionBean();
					zcBean.setGroupNmeString(resultSet.getString(nmeStr));
					zcBean.setNoInspection(resultSet.getInt("TH_CRPDET_INSPECTION"));
					

					zcBean.setRole(Integer.parseInt(role));
					zcBean.setIdNxt(resultSet.getInt(idNxt));
					zcBean.setIdCurrent(resultSet.getInt(idCurrent));
					
					discrepBean = new DiscrepancyBean();
					discrepBean.setZoneId(resultSet.getInt("ZONE_ID"));
					discrepBean.setCircleId(resultSet.getInt("CRCL_ID"));
					discrepBean.setDivisionId(resultSet.getInt("DIV_ID"));
					discrepBean.setSubDivisionId(resultSet.getInt("SUBDIV_ID"));
					discrepBean.setSectionId(resultSet.getInt("SECTN_ID"));
					
					discrepBean.setZoneNme(resultSet.getString("ZONE_NAME"));
					discrepBean.setCircleNme(resultSet.getString("CRCL_NAME"));
					discrepBean.setDivNme(resultSet.getString("DIV_NAME"));
					discrepBean.setSubDivNme(resultSet.getString("SUBDIV_NAME"));
					discrepBean.setSecNme(resultSet.getString("SECTN_NAME"));
					
					
					discrepBean.setDiscThId(resultSet.getString("TH_ID"));
					discrepBean.setDiscrepancyId(resultSet.getString("DCREP_ID"));
					discrepBean.setDiscrepancyNme(resultSet.getString("DCREP_NAME"));
					discrepBean.setDemandRasied(resultSet.getDouble("CRPDET_DMND_RAISED"));
					discrepBean.setNoOfZeroConsum(resultSet.getInt("CRPDET_NOOF_CONSMPN"));
					discrepBean.setNoInspection(resultSet.getInt("TH_CRPDET_INSPECTION"));
					
					lstDiscrepBean = new ArrayList<DiscrepancyBean>();
					lstDiscrepBean.add(discrepBean);
					
					
				}else if(id != resultSet.getInt(idNxt)){
					zcBean.setRowSpanSize(lstDiscrepBean.size()+1);
					zcBean.setList(lstDiscrepBean);
					lstZCBean.add(zcBean);
					
					id = resultSet.getInt(idNxt);
					
					zcBean = new ZeroConsumptionBean();
					zcBean.setGroupNmeString(resultSet.getString(nmeStr));
					zcBean.setNoInspection(resultSet.getInt("TH_CRPDET_INSPECTION"));

					zcBean.setRole(Integer.parseInt(role));
					zcBean.setIdNxt(resultSet.getInt(idNxt));
					zcBean.setIdCurrent(resultSet.getInt(idCurrent));
					
					discrepBean = new DiscrepancyBean();
					discrepBean.setZoneId(resultSet.getInt("ZONE_ID"));
					discrepBean.setCircleId(resultSet.getInt("CRCL_ID"));
					discrepBean.setDivisionId(resultSet.getInt("DIV_ID"));
					discrepBean.setSubDivisionId(resultSet.getInt("SUBDIV_ID"));
					discrepBean.setSectionId(resultSet.getInt("SECTN_ID"));
					
					discrepBean.setZoneNme(resultSet.getString("ZONE_NAME"));
					discrepBean.setCircleNme(resultSet.getString("CRCL_NAME"));
					discrepBean.setDivNme(resultSet.getString("DIV_NAME"));
					discrepBean.setSubDivNme(resultSet.getString("SUBDIV_NAME"));
					discrepBean.setSecNme(resultSet.getString("SECTN_NAME"));
					
					
					discrepBean.setDiscThId(resultSet.getString("TH_ID"));
					discrepBean.setDiscrepancyId(resultSet.getString("DCREP_ID"));
					discrepBean.setDiscrepancyNme(resultSet.getString("DCREP_NAME"));
					discrepBean.setDemandRasied(resultSet.getDouble("CRPDET_DMND_RAISED"));
					discrepBean.setNoOfZeroConsum(resultSet.getInt("CRPDET_NOOF_CONSMPN"));
					discrepBean.setNoInspection(resultSet.getInt("TH_CRPDET_INSPECTION"));
					
					lstDiscrepBean = new ArrayList<DiscrepancyBean>();
					lstDiscrepBean.add(discrepBean);
					
				}else{
					discrepBean = new DiscrepancyBean();
					discrepBean.setZoneId(resultSet.getInt("ZONE_ID"));
					discrepBean.setCircleId(resultSet.getInt("CRCL_ID"));
					discrepBean.setDivisionId(resultSet.getInt("DIV_ID"));
					discrepBean.setSubDivisionId(resultSet.getInt("SUBDIV_ID"));
					discrepBean.setSectionId(resultSet.getInt("SECTN_ID"));
					
					discrepBean.setZoneNme(resultSet.getString("ZONE_NAME"));
					discrepBean.setCircleNme(resultSet.getString("CRCL_NAME"));
					discrepBean.setDivNme(resultSet.getString("DIV_NAME"));
					discrepBean.setSubDivNme(resultSet.getString("SUBDIV_NAME"));
					discrepBean.setSecNme(resultSet.getString("SECTN_NAME"));
					
					
					discrepBean.setDiscThId(resultSet.getString("TH_ID"));
					discrepBean.setDiscrepancyId(resultSet.getString("DCREP_ID"));
					discrepBean.setDiscrepancyNme(resultSet.getString("DCREP_NAME"));
					discrepBean.setDemandRasied(resultSet.getDouble("CRPDET_DMND_RAISED"));
					discrepBean.setNoOfZeroConsum(resultSet.getInt("CRPDET_NOOF_CONSMPN"));
					discrepBean.setNoInspection(resultSet.getInt("TH_CRPDET_INSPECTION"));
					
					lstDiscrepBean.add(discrepBean);
				}
			}if(resultSet.isAfterLast()){
				zcBean.setRowSpanSize(lstDiscrepBean.size()+1);
				zcBean.setList(lstDiscrepBean);
				lstZCBean.add(zcBean);
				
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
		}
		
		
		return lstZCBean;
	}
	
	
	
	
	
	    
	
	
	
	
	
	
	
	
	
	
	
	
}
