package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Util.AppUtil;
import com.Util.MysqlConnectionProvider;
import com.bean.FeederInterruptionBean;
import com.bean.FeederInterruptionReportMainBean;
import com.bean.FeederInterruptionReportsBean;

public class SummaryRepFeederInterruptionDao {
	
	
	public List<FeederInterruptionReportsBean> getFeederReportDetails(String role, String roleBelogs,String yearId)	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		
		String wherClause = "";
		if("1".equals(role)){
			wherClause = " and z.ZONE_ID = "+ roleBelogs;
		}else if("2".equals(role)){
			wherClause = " and c.CRCL_ID = "+ roleBelogs;
		}else if("3".equals(role)){
			wherClause = " and d.DIV_ID = "+ roleBelogs;
		}else if("4".equals(role)){
			wherClause = " and sd.SUBDIV_ID = "+ roleBelogs;
		}else if("5".equals(role)){
			wherClause = " and s.SECTN_ID = "+ roleBelogs;
		}
		
		
		
		FeederInterruptionReportsBean fedIntrptionRepBean = null;
		List<FeederInterruptionReportsBean> lstFedIntrRepBeans = new ArrayList<FeederInterruptionReportsBean>();

		
		
		
		String sql_str1=     "SELECT   f.FDR_NAME, fl.FL_FDR_TRIPPED_COUNT, fl.FL_FDR_REASON, th.TH_YEAR, " +
										"th.TH_MONTH, th.TH_ID, th.TH_SECTN_ID, s.SECTN_NAME," +
										"sd.SUBDIV_ID, sd.SUBDIV_NAME,d.DIV_ID, " +
										"d.DIV_NAME, c.CRCL_ID, c.CRCL_NAME, z.ZONE_ID, z.ZONE_NAME " +
								" FROM     tran_head th                            " +
								"         JOIN feeder_line fl                      " +
								"           ON fl.FL_TH_ID = th.TH_ID              " +
								"         JOIN feeder f                            " +
								"           ON fl.FL_FDR_ID = f.FDR_ID             " +
								"         JOIN section s                           " +
								"           ON th.TH_SECTN_ID = s.SECTN_ID         " +
								"         JOIN sub_division sd                     " +
								"           ON s.SECTN_SUBDIV_ID = sd.SUBDIV_ID    " +
								"         JOIN division d                          " +
								"           ON sd.SUBDIV_DIV_ID = d.DIV_ID         " +
								"         JOIN circle c                            " +
								"           ON d.DIV_CIRCLE_ID = c.CRCL_ID         " +
								"         JOIN zone z                              " +
								"           ON c.CRCL_ZONE_ID = z.ZONE_ID          " +
								"WHERE th.TH_IDEN_FLAG = 1 and  th.TH_YEAR = "+yearId+"  "+wherClause+"  " +
								"ORDER BY th.TH_MONTH, th.TH_SECTN_ID, sd.SUBDIV_ID,d.DIV_ID,c.CRCL_ID,z.ZONE_ID, f.FDR_NAME  ";
				
		try {
			connection = MysqlConnectionProvider.getNewConnection(); 
			preparedStatement  = connection.prepareStatement(sql_str1);
			rs = preparedStatement.executeQuery();
			int transHead=0;
			int feederCount = 0;
			while(rs.next()){
				if(transHead==0){
				transHead=rs.getInt("TH_ID");
				fedIntrptionRepBean = new FeederInterruptionReportsBean();
				fedIntrptionRepBean.setMnthId(rs.getString("TH_MONTH"));
				fedIntrptionRepBean.setFedName(rs.getString("FDR_NAME"));
				fedIntrptionRepBean.setFedTrippedCount(rs.getInt("FL_FDR_TRIPPED_COUNT"));
				fedIntrptionRepBean.setFedReason(rs.getString("FL_FDR_REASON"));
				
				fedIntrptionRepBean.setZoneId(rs.getInt("ZONE_ID"));
				fedIntrptionRepBean.setZoneNme(rs.getString("ZONE_NAME"));
				fedIntrptionRepBean.setCircleId(rs.getInt("CRCL_ID"));
				fedIntrptionRepBean.setCircleNme(rs.getString("CRCL_NAME"));
				fedIntrptionRepBean.setDivId(rs.getInt("DIV_ID"));
				fedIntrptionRepBean.setDivNme(rs.getString("DIV_NAME"));
				fedIntrptionRepBean.setSubDivId(rs.getInt("SUBDIV_ID"));
				fedIntrptionRepBean.setSubDivNme(rs.getString("SUBDIV_NAME"));
				fedIntrptionRepBean.setSectionId(rs.getInt("TH_SECTN_ID"));
				fedIntrptionRepBean.setSectionNme(rs.getString("SECTN_NAME"));
				
				fedIntrptionRepBean.setYear(rs.getString("TH_YEAR"));
				fedIntrptionRepBean.setTransHeadID(rs.getInt("TH_ID"));
				
				++feederCount;		
				fedIntrptionRepBean.setFedTrippedCount(rs.getInt("FL_FDR_TRIPPED_COUNT"));
				
				}else if(transHead != rs.getInt("TH_ID"))	{
					fedIntrptionRepBean.setFedCount(feederCount);
					lstFedIntrRepBeans.add(fedIntrptionRepBean);
					transHead=rs.getInt("TH_ID");
					 feederCount = 0;			
					fedIntrptionRepBean = new FeederInterruptionReportsBean();
					fedIntrptionRepBean.setMnthId(rs.getString("TH_MONTH"));
					fedIntrptionRepBean.setFedName(rs.getString("FDR_NAME"));
					fedIntrptionRepBean.setFedTrippedCount(rs.getInt("FL_FDR_TRIPPED_COUNT"));
					fedIntrptionRepBean.setFedReason(rs.getString("FL_FDR_REASON"));
					
					
					fedIntrptionRepBean.setZoneId(rs.getInt("ZONE_ID"));
					fedIntrptionRepBean.setZoneNme(rs.getString("ZONE_NAME"));
					fedIntrptionRepBean.setCircleId(rs.getInt("CRCL_ID"));
					fedIntrptionRepBean.setCircleNme(rs.getString("CRCL_NAME"));
					fedIntrptionRepBean.setDivId(rs.getInt("DIV_ID"));
					fedIntrptionRepBean.setDivNme(rs.getString("DIV_NAME"));
					fedIntrptionRepBean.setSubDivId(rs.getInt("SUBDIV_ID"));
					fedIntrptionRepBean.setSubDivNme(rs.getString("SUBDIV_NAME"));
					fedIntrptionRepBean.setSectionId(rs.getInt("TH_SECTN_ID"));
					fedIntrptionRepBean.setSectionNme(rs.getString("SECTN_NAME"));
					
					fedIntrptionRepBean.setYear(rs.getString("TH_YEAR"));
					fedIntrptionRepBean.setTransHeadID(rs.getInt("TH_ID"));
					
					++feederCount;		
					fedIntrptionRepBean.setFedTrippedCount(rs.getInt("FL_FDR_TRIPPED_COUNT"));
					
				
				}else if(transHead == rs.getInt("TH_ID")){
					++feederCount;		
					fedIntrptionRepBean.setFedTrippedCount(fedIntrptionRepBean.getFedTrippedCount() + rs.getInt("FL_FDR_TRIPPED_COUNT"));
				}
				
			}
			if(rs.isAfterLast()){
				fedIntrptionRepBean.setFedCount(feederCount);
				lstFedIntrRepBeans.add(fedIntrptionRepBean);
			}
			
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			MysqlConnectionProvider.releaseConnection(rs, null, preparedStatement, connection);
			
		}
		return lstFedIntrRepBeans;
	}
	
	
	
	public List<FeederInterruptionReportMainBean> drillDown(List<FeederInterruptionReportsBean> lstFedIntr, String role){
		
		int monthId = 0;
		FeederInterruptionReportMainBean reportMainBean = null;
		
		List<FeederInterruptionReportsBean> lstReportBean = null;
		List<FeederInterruptionReportMainBean> lstReportMainBean = new ArrayList<FeederInterruptionReportMainBean>();
		
		for (FeederInterruptionReportsBean feederInterruptionReportsBean : lstFedIntr) {
			if(monthId==0){
				monthId = Integer.valueOf(feederInterruptionReportsBean.getMnthId());
				reportMainBean = new FeederInterruptionReportMainBean();
				reportMainBean.setMonthId(AppUtil.getMonth(feederInterruptionReportsBean.getMnthId()));
				
				
				lstReportBean = new ArrayList<FeederInterruptionReportsBean>();
				lstReportBean.add(feederInterruptionReportsBean);
				
			}else if(monthId != Integer.parseInt(feederInterruptionReportsBean.getMnthId())){
				
				reportMainBean.setFedintrputionDetails(lstReportBean);
				lstReportMainBean.add(reportMainBean);
				
				monthId = Integer.valueOf(feederInterruptionReportsBean.getMnthId());
				reportMainBean = new FeederInterruptionReportMainBean();
				reportMainBean.setMonthId(AppUtil.getMonth(feederInterruptionReportsBean.getMnthId()));
				
				
				lstReportBean = new ArrayList<FeederInterruptionReportsBean>();
				lstReportBean.add(feederInterruptionReportsBean);
			}else{
				lstReportBean.add(feederInterruptionReportsBean);
			}
		}
		reportMainBean.setFedintrputionDetails(lstReportBean);
		lstReportMainBean.add(reportMainBean);
		
		for (FeederInterruptionReportMainBean fedIntrRepMainBean : lstReportMainBean) {
			if("1".equals(role)){
				fedIntrRepMainBean.setFedintrputionDetails(zoneWise(fedIntrRepMainBean.getFedintrputionDetails()));
			}else if("2".equals(role)){
				fedIntrRepMainBean.setFedintrputionDetails(circleWise(fedIntrRepMainBean.getFedintrputionDetails()));
			}else if("3".equals(role)){
				fedIntrRepMainBean.setFedintrputionDetails(divWise(fedIntrRepMainBean.getFedintrputionDetails()));
			}else if("4".equals(role)){
				fedIntrRepMainBean.setFedintrputionDetails(subDivWise(fedIntrRepMainBean.getFedintrputionDetails()));
			}else if("5".equals(role)){
				fedIntrRepMainBean.setFedintrputionDetails(secWise(fedIntrRepMainBean.getFedintrputionDetails()));
			}
			
			fedIntrRepMainBean.setSize(fedIntrRepMainBean.getFedintrputionDetails().size() + 1);
		}
		return lstReportMainBean;
	}
	
	
	
	public List<FeederInterruptionReportsBean> zoneWise(List<FeederInterruptionReportsBean> lstFedIntrRepBean) {

		int zoneId = 0;
		int totalTripped = 0;
		List<FeederInterruptionReportsBean> displstFedIntrRepBean = new ArrayList<FeederInterruptionReportsBean>();
		FeederInterruptionReportsBean fedIntrRepBean = null;
		for (FeederInterruptionReportsBean fedIntr : lstFedIntrRepBean) {
			if (zoneId == 0) {
				zoneId = fedIntr.getZoneId();
				
				
				fedIntrRepBean = new FeederInterruptionReportsBean();
				fedIntrRepBean.setMnthId(fedIntr.getMnthId());
				fedIntrRepBean.setMnthNme(AppUtil.getMonth(fedIntr.getMnthId()));
				fedIntrRepBean.setId(fedIntr.getZoneId());
				fedIntrRepBean.setDispLabel(fedIntr.getZoneNme());
				fedIntrRepBean.setFedTrippedCount(fedIntr.getFedTrippedCount() + fedIntrRepBean.getFedTrippedCount());
				fedIntrRepBean.setLevel("1");
				fedIntrRepBean.setFedCount(fedIntr.getFedCount()+fedIntrRepBean.getFedCount());
				
				totalTripped = totalTripped + fedIntrRepBean.getFedTrippedCount();
				fedIntrRepBean.setTotalTrippedCount(totalTripped);
				
				} else if (zoneId != fedIntr.getZoneId()) {
					
					zoneId = fedIntr.getZoneId();
					displstFedIntrRepBean.add(fedIntrRepBean);
					fedIntrRepBean = new FeederInterruptionReportsBean();
					fedIntrRepBean.setFedTrippedCount(0);
					fedIntrRepBean.setMnthId(fedIntr.getMnthId());
					fedIntrRepBean.setMnthNme(AppUtil.getMonth(fedIntr.getMnthId()));
					fedIntrRepBean.setId(fedIntr.getZoneId());
					fedIntrRepBean.setDispLabel(fedIntr.getZoneNme());
					fedIntrRepBean.setLevel("1");
					fedIntrRepBean.setFedTrippedCount(fedIntr.getFedTrippedCount() + fedIntrRepBean.getFedTrippedCount());
					fedIntrRepBean.setFedCount(fedIntr.getFedCount()+fedIntrRepBean.getFedCount());
					
					totalTripped = totalTripped + fedIntrRepBean.getFedTrippedCount();
					fedIntrRepBean.setTotalTrippedCount(totalTripped);
					
				}else{
					fedIntrRepBean.setFedTrippedCount(fedIntr.getFedTrippedCount() + fedIntrRepBean.getFedTrippedCount());
					fedIntrRepBean.setFedCount(fedIntr.getFedCount()+fedIntrRepBean.getFedCount());
					
					totalTripped = totalTripped + fedIntrRepBean.getFedTrippedCount();
					fedIntrRepBean.setTotalTrippedCount(totalTripped);
				}
		}
		displstFedIntrRepBean.add(fedIntrRepBean);

		return displstFedIntrRepBean;
	}
	
	public List<FeederInterruptionReportsBean> circleWise(List<FeederInterruptionReportsBean> lstFedIntrRepBean) {

		int circleId = 0;
		int totalTripped = 0;
		List<FeederInterruptionReportsBean> displstFedIntrRepBean = new ArrayList<FeederInterruptionReportsBean>();
		FeederInterruptionReportsBean fedIntrRepBean = null;
		for (FeederInterruptionReportsBean fedIntr : lstFedIntrRepBean) {
			if (circleId == 0) {
				circleId = fedIntr.getCircleId();
				
				fedIntrRepBean = new FeederInterruptionReportsBean();
				fedIntrRepBean.setMnthId(fedIntr.getMnthId());
				fedIntrRepBean.setMnthNme(AppUtil.getMonth(fedIntr.getMnthId()));
				fedIntrRepBean.setDispLabel(fedIntr.getCircleNme());
				fedIntrRepBean.setId(fedIntr.getCircleId());
				fedIntrRepBean.setFedTrippedCount(fedIntr.getFedTrippedCount() + fedIntrRepBean.getFedTrippedCount());
				fedIntrRepBean.setLevel("2");
				fedIntrRepBean.setFedCount(fedIntr.getFedCount()+fedIntrRepBean.getFedCount());
				
				totalTripped = totalTripped + fedIntrRepBean.getFedTrippedCount();
				fedIntrRepBean.setTotalTrippedCount(totalTripped);
				
				
			}else if (circleId != fedIntr.getCircleId()) {
				
				circleId = fedIntr.getCircleId();
				displstFedIntrRepBean.add(fedIntrRepBean);
				fedIntrRepBean = new FeederInterruptionReportsBean();
				fedIntrRepBean.setFedTrippedCount(0);
				fedIntrRepBean.setMnthId(fedIntr.getMnthId());
				fedIntrRepBean.setMnthNme(AppUtil.getMonth(fedIntr.getMnthId()));
				fedIntrRepBean.setDispLabel(fedIntr.getCircleNme());
				fedIntrRepBean.setId(fedIntr.getCircleId());
				fedIntrRepBean.setLevel("2");
				fedIntrRepBean.setFedTrippedCount(fedIntr.getFedTrippedCount() + fedIntrRepBean.getFedTrippedCount());
				fedIntrRepBean.setFedCount(fedIntr.getFedCount()+fedIntrRepBean.getFedCount());
				
				totalTripped = totalTripped + fedIntrRepBean.getFedTrippedCount();
				fedIntrRepBean.setTotalTrippedCount(totalTripped);
			}else{
				fedIntrRepBean.setFedTrippedCount(fedIntr.getFedTrippedCount() + fedIntrRepBean.getFedTrippedCount());
				fedIntrRepBean.setFedCount(fedIntr.getFedCount()+fedIntrRepBean.getFedCount());
				
				totalTripped = totalTripped + fedIntrRepBean.getFedTrippedCount();
				fedIntrRepBean.setTotalTrippedCount(totalTripped);
			}
		}
		displstFedIntrRepBean.add(fedIntrRepBean);

		return displstFedIntrRepBean;
	}
	
	
	public List<FeederInterruptionReportsBean> divWise(List<FeederInterruptionReportsBean> lstFedIntrRepBean) {

		int divId = 0;
		int totalTripped = 0;
		List<FeederInterruptionReportsBean> displstFedIntrRepBean = new ArrayList<FeederInterruptionReportsBean>();
		FeederInterruptionReportsBean fedIntrRepBean = null;
		for (FeederInterruptionReportsBean fedIntr : lstFedIntrRepBean) {
			if (divId == 0) {
				divId = fedIntr.getDivId();
				
				fedIntrRepBean = new FeederInterruptionReportsBean();
				fedIntrRepBean.setMnthId(fedIntr.getMnthId());
				fedIntrRepBean.setMnthNme(AppUtil.getMonth(fedIntr.getMnthId()));
				fedIntrRepBean.setDispLabel(fedIntr.getDivNme());
				fedIntrRepBean.setId(fedIntr.getDivId());
				fedIntrRepBean.setFedTrippedCount(fedIntr.getFedTrippedCount() + fedIntrRepBean.getFedTrippedCount());
				fedIntrRepBean.setLevel("3");
				fedIntrRepBean.setFedCount(fedIntr.getFedCount()+fedIntrRepBean.getFedCount());
				
				totalTripped = totalTripped + fedIntrRepBean.getFedTrippedCount();
				fedIntrRepBean.setTotalTrippedCount(totalTripped);

			}else if (divId != fedIntr.getDivId()) {
				
				divId = fedIntr.getDivId();
				displstFedIntrRepBean.add(fedIntrRepBean);
				fedIntrRepBean = new FeederInterruptionReportsBean();
				fedIntrRepBean.setFedTrippedCount(0);
				fedIntrRepBean.setMnthId(fedIntr.getMnthId());
				fedIntrRepBean.setMnthNme(AppUtil.getMonth(fedIntr.getMnthId()));
				fedIntrRepBean.setId(fedIntr.getDivId());
				fedIntrRepBean.setDispLabel(fedIntr.getDivNme());
				fedIntrRepBean.setLevel("3");
				fedIntrRepBean.setFedTrippedCount(fedIntr.getFedTrippedCount() + fedIntrRepBean.getFedTrippedCount());
				fedIntrRepBean.setFedCount(fedIntr.getFedCount()+fedIntrRepBean.getFedCount());
				
				
				totalTripped = totalTripped + fedIntrRepBean.getFedTrippedCount();
				fedIntrRepBean.setTotalTrippedCount(totalTripped);
			}else{
				fedIntrRepBean.setFedTrippedCount(fedIntr.getFedTrippedCount() + fedIntrRepBean.getFedTrippedCount());
				fedIntrRepBean.setFedCount(fedIntr.getFedCount()+fedIntrRepBean.getFedCount());
				
				totalTripped = totalTripped + fedIntrRepBean.getFedTrippedCount();
				fedIntrRepBean.setTotalTrippedCount(totalTripped);
			}
		}
		displstFedIntrRepBean.add(fedIntrRepBean);

		return displstFedIntrRepBean;
	}
	
	public List<FeederInterruptionReportsBean> subDivWise(List<FeederInterruptionReportsBean> lstFedIntrRepBean) {

		int subDivId = 0;
		int totalTripped = 0;
		List<FeederInterruptionReportsBean> displstFedIntrRepBean = new ArrayList<FeederInterruptionReportsBean>();
		FeederInterruptionReportsBean fedIntrRepBean = null;
		
		for (FeederInterruptionReportsBean fedIntr : lstFedIntrRepBean) {
			if (subDivId == 0) {
				subDivId = fedIntr.getSubDivId();
				
				fedIntrRepBean = new FeederInterruptionReportsBean();
				fedIntrRepBean.setMnthId(fedIntr.getMnthId());
				fedIntrRepBean.setMnthNme(AppUtil.getMonth(fedIntr.getMnthId()));
				fedIntrRepBean.setDispLabel(fedIntr.getSubDivNme());
				fedIntrRepBean.setId(fedIntr.getSubDivId());
				
				fedIntrRepBean.setFedTrippedCount(fedIntr.getFedTrippedCount() + fedIntrRepBean.getFedTrippedCount());
				fedIntrRepBean.setLevel("4");

				fedIntrRepBean.setFedCount(fedIntr.getFedCount()+fedIntrRepBean.getFedCount());
				
				totalTripped = totalTripped + fedIntrRepBean.getFedTrippedCount();
				fedIntrRepBean.setTotalTrippedCount(totalTripped);
			} else if (subDivId != fedIntr.getSubDivId()) {
					
					subDivId = fedIntr.getSubDivId();
					displstFedIntrRepBean.add(fedIntrRepBean);
					fedIntrRepBean = new FeederInterruptionReportsBean();
					fedIntrRepBean.setFedTrippedCount(0);
					fedIntrRepBean.setMnthId(fedIntr.getMnthId());
					fedIntrRepBean.setMnthNme(AppUtil.getMonth(fedIntr.getMnthId()));
					fedIntrRepBean.setDispLabel(fedIntr.getSubDivNme());
					fedIntrRepBean.setId(fedIntr.getSubDivId());
					fedIntrRepBean.setLevel("4");
					fedIntrRepBean.setFedTrippedCount(fedIntr.getFedTrippedCount() + fedIntrRepBean.getFedTrippedCount());
					
					fedIntrRepBean.setFedCount(fedIntr.getFedCount()+fedIntrRepBean.getFedCount());
					
					totalTripped = totalTripped + fedIntrRepBean.getFedTrippedCount();
					fedIntrRepBean.setTotalTrippedCount(totalTripped);
			}else{
				fedIntrRepBean.setFedTrippedCount(fedIntr.getFedTrippedCount() + fedIntrRepBean.getFedTrippedCount());
				fedIntrRepBean.setFedCount(fedIntr.getFedCount()+fedIntrRepBean.getFedCount());
				
				
				totalTripped = totalTripped + fedIntrRepBean.getFedTrippedCount();
				fedIntrRepBean.setTotalTrippedCount(totalTripped);
			}
		}
		displstFedIntrRepBean.add(fedIntrRepBean);

		return displstFedIntrRepBean;
	}
	
	
	
	
	public List<FeederInterruptionReportsBean> secWise(List<FeederInterruptionReportsBean> lstFedIntrRepBean) {

		int secId = 0;
		int totalTripped = 0;
		List<FeederInterruptionReportsBean> displstFedIntrRepBean = new ArrayList<FeederInterruptionReportsBean>();
		FeederInterruptionReportsBean fedIntrRepBean = null;
		for (FeederInterruptionReportsBean fedIntr : lstFedIntrRepBean) {
			if (secId == 0) {
				secId = fedIntr.getSectionId();
				
				fedIntrRepBean = new FeederInterruptionReportsBean();
				fedIntrRepBean.setMnthId(fedIntr.getMnthId());
				fedIntrRepBean.setMnthNme(AppUtil.getMonth(fedIntr.getMnthId()));
				fedIntrRepBean.setDispLabel(fedIntr.getSectionNme());
				fedIntrRepBean.setId(fedIntr.getSectionId());
				fedIntrRepBean.setFedTrippedCount(fedIntr.getFedTrippedCount() + fedIntrRepBean.getFedTrippedCount());
				fedIntrRepBean.setLevel("5");
				fedIntrRepBean.setFedCount(fedIntr.getFedCount()+fedIntrRepBean.getFedCount());
				
				totalTripped = totalTripped + fedIntrRepBean.getFedTrippedCount();
				fedIntrRepBean.setTotalTrippedCount(totalTripped);

			} else if (secId != fedIntr.getSectionId()) {
				
				secId = fedIntr.getSectionId();
				displstFedIntrRepBean.add(fedIntrRepBean);
				fedIntrRepBean = new FeederInterruptionReportsBean();
				fedIntrRepBean.setFedTrippedCount(0);
				fedIntrRepBean.setMnthId(fedIntr.getMnthId());
				fedIntrRepBean.setMnthNme(AppUtil.getMonth(fedIntr.getMnthId()));
				fedIntrRepBean.setDispLabel(fedIntr.getSectionNme());
				fedIntrRepBean.setId(fedIntr.getSectionId());
				fedIntrRepBean.setLevel("5");
				fedIntrRepBean.setFedTrippedCount(fedIntr.getFedTrippedCount() + fedIntrRepBean.getFedTrippedCount());
				fedIntrRepBean.setFedCount(fedIntr.getFedCount()+fedIntrRepBean.getFedCount());
				
				totalTripped = totalTripped + fedIntrRepBean.getFedTrippedCount();
				fedIntrRepBean.setTotalTrippedCount(totalTripped);
			}else{
				fedIntrRepBean.setFedTrippedCount(fedIntr.getFedTrippedCount() + fedIntrRepBean.getFedTrippedCount());
				fedIntrRepBean.setFedCount(fedIntr.getFedCount()+fedIntrRepBean.getFedCount());
				
				totalTripped = totalTripped + fedIntrRepBean.getFedTrippedCount();
				fedIntrRepBean.setTotalTrippedCount(totalTripped);
			}
		}
		displstFedIntrRepBean.add(fedIntrRepBean);

		return displstFedIntrRepBean;
	}
	
	
	
	
	
	
	
}
