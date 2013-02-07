package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Util.AppUtil;
import com.Util.MysqlConnectionProvider;

import com.bean.MNRRepBean;


public class MNRDetailReportDao {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	MNRRepBean mnrRepBean = new MNRRepBean();
	


public MNRRepBean getMNRDetailList(MNRRepBean mnrRepBean){
	
	   String query="SELECT th.TH_ID,"
					+"th.TH_MONTH, "
					+"th.TH_YEAR, "
					+"s.SECTN_ID, "
					+"s.SECTN_NAME, "
					+"sd.SUBDIV_ID, "
					+"sd.SUBDIV_NAME, "
					+"d.DIV_ID, "
					+"d.DIV_NAME, "
					+"c.CRCL_ID, "
					+"c.CRCL_NAME, "
					+"z.ZONE_ID, "
					+"z.ZONE_NAME, "
					+"mn.MDET_TH_ID, "
					+"mn.MDET_IDEN_FLAG, "
					+"mn.MDET_OB, "
					+"mn.MDET_INSTALATION, "
					+"mn.MDET_REPLACED, "
					+"mn.MDET_CB, "
					+"th.TH_MNR_REASON "
					+"FROM mnr_detail mn "
					+"JOIN tran_head th "
					+"ON th.TH_ID = mn.MDET_TH_ID "
					+"JOIN section s "
					+"ON s.SECTN_ID = th.TH_SECTN_ID "
					+"JOIN sub_division sd "
					+"ON sd.SUBDIV_ID = s.SECTN_SUBDIV_ID "
					+"JOIN division d "
					+"ON d.DIV_ID = sd.SUBDIV_DIV_ID "
					+"JOIN circle c "
					+"ON c.CRCL_ID = d.DIV_CIRCLE_ID "
					+"JOIN zone z "
					+"ON z.ZONE_ID = c.CRCL_ZONE_ID "
					+"where s.SECTN_ID='"+mnrRepBean.getSecId()+"' and th.TH_MONTH='"+AppUtil.getMnthId(mnrRepBean.getMonthId())+"' and th.TH_YEAR='"+mnrRepBean.getYearId()+"' " ;
	   
	   try {
		    connection = MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			 int totalOpBal=0;
		     int totalMnrInstall=0;
		     int totalMnrReolaced=0;
		     int totalClBal=0;
			
			
			while(resultSet.next())
			{
			     
				
				if(resultSet.getString("MDET_IDEN_FLAG").equals("1")){
					mnrRepBean.setOpbBet1to3(resultSet.getInt("MDET_OB"));
					mnrRepBean.setMnrInsbet1to3(resultSet.getInt("MDET_INSTALATION"));
					mnrRepBean.setMnrReplbet1to3(resultSet.getInt("MDET_REPLACED"));
					mnrRepBean.setClbBet1to3(resultSet.getInt("MDET_CB"));
					mnrRepBean.setMnrReason(resultSet.getString("TH_MNR_REASON"));
					
					totalOpBal+=resultSet.getInt("MDET_OB");
					totalMnrInstall+=resultSet.getInt("MDET_INSTALATION");
					totalMnrReolaced+=resultSet.getInt("MDET_REPLACED");
					totalClBal+=resultSet.getInt("MDET_CB");
					
				}
				else if (resultSet.getString("MDET_IDEN_FLAG").equals("2")) {
					mnrRepBean.setOpbBet3to6(resultSet.getInt("MDET_OB"));
					mnrRepBean.setMnrInsbet3to6(resultSet.getInt("MDET_INSTALATION"));
					mnrRepBean.setMnrReplbet3to6(resultSet.getInt("MDET_REPLACED"));
					mnrRepBean.setClbBet3to6(resultSet.getInt("MDET_CB"));	
					
					totalOpBal+=resultSet.getInt("MDET_OB");
					totalMnrInstall+=resultSet.getInt("MDET_INSTALATION");
					totalMnrReolaced+=resultSet.getInt("MDET_REPLACED");
					totalClBal+=resultSet.getInt("MDET_CB");
				}
				else if (resultSet.getString("MDET_IDEN_FLAG").equals("3")) {
					mnrRepBean.setOpbGrt6(resultSet.getInt("MDET_OB"));
					mnrRepBean.setMnrInsGrt6(resultSet.getInt("MDET_INSTALATION"));
					mnrRepBean.setMnrReplGrt6(resultSet.getInt("MDET_REPLACED"));
					mnrRepBean.setClbGrt6(resultSet.getInt("MDET_CB"));
					
					totalOpBal+=resultSet.getInt("MDET_OB");
					totalMnrInstall+=resultSet.getInt("MDET_INSTALATION");
					totalMnrReolaced+=resultSet.getInt("MDET_REPLACED");
					totalClBal+=resultSet.getInt("MDET_CB");
		
				}
			}
			mnrRepBean.setTotalOpBal(totalOpBal);
			mnrRepBean.setTotalMnrReolaced(totalMnrReolaced);
			mnrRepBean.setTotalMnrInstall(totalMnrInstall);
			mnrRepBean.setTotalClBal(totalClBal);
			
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
	}
	finally {
		MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement, connection);
		
	}
	   
	
	return mnrRepBean;
	
}
 	
}
