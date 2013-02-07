package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.Util.AppUtil;
import com.Util.MysqlConnectionProvider;
import com.bean.DoorLockBean;

public class DoorLockDetailReportDao {
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	
public DoorLockBean getDoorLockDeatils(DoorLockBean doorLockBean)
{
	 int totalNoOfInstances = 0;
	 int totalOpeningBalance=0;
	int totalNoticesServed=0;
	 int totalMeterShifted=0;
	 int totalNotPossibleShift=0;
	 int totalClosingBalance=0;
	String query="SELECT d.DRLCK_NOOF_INSTANCE, "
	     + "d.DRLCK_IDEN_FLAG, "
	     + "d.DRLCK_SHIFT_OB, "
	     + "d.DRLCK_NOOF_NOTICE, "
	     + "d.DRLCK_NOOF_SHIFTED, "
	     + "d.DRLCK_NOT_SHIFTED, "
	     + "d.DRLCK_CB, "
	     + "d.DRLCK_REASON, "
	     + "th.TH_SECTN_ID, "
	     + "th.TH_MONTH, "
	     + "th.TH_YEAR, "
	     + "th.TH_ID, "
	     + "d.DRLCK_TH_ID "
	     + "FROM doorlock d "
	     + "JOIN tran_head th "
	     + "ON d.DRLCK_TH_ID = th.TH_ID "
	     + "JOIN section s "
	     + "ON th.TH_SECTN_ID = s.SECTN_ID "
	     + "WHERE th.TH_SECTN_ID = '"+doorLockBean.getSecId()+"' AND th.TH_MONTH = '"+AppUtil.getMnthId(doorLockBean.getMonthId())+"' AND th.TH_YEAR = '"+doorLockBean.getYearId()+"' ";
	
	try {
		connection=MysqlConnectionProvider.getNewConnection();
		preparedStatement=connection.prepareStatement(query);
		rs=preparedStatement.executeQuery();
		System.out.println(query);
		
		while(rs.next()){
			if(rs.getInt("DRLCK_IDEN_FLAG")==1){
				doorLockBean.setNoOFDoorLock(rs.getInt("DRLCK_NOOF_INSTANCE"));
				doorLockBean.setOpBalance(rs.getInt("DRLCK_SHIFT_OB"));
				doorLockBean.setNoticeServed(rs.getInt("DRLCK_NOOF_NOTICE"));
				doorLockBean.setMetersShifted(rs.getInt("DRLCK_NOOF_SHIFTED"));
				doorLockBean.setNotShift(rs.getInt("DRLCK_NOT_SHIFTED"));
				doorLockBean.setCloBalance(rs.getInt("DRLCK_CB"));
				
				totalNoOfInstances+=rs.getInt("DRLCK_NOOF_INSTANCE");
				totalOpeningBalance+=rs.getInt("DRLCK_SHIFT_OB");
				totalNoticesServed+=rs.getInt("DRLCK_NOOF_NOTICE");
				totalMeterShifted+=rs.getInt("DRLCK_NOOF_SHIFTED");
				totalNotPossibleShift+=rs.getInt("DRLCK_NOT_SHIFTED");
				totalClosingBalance+=rs.getInt("DRLCK_CB");
				
				
			}else if(rs.getInt("DRLCK_IDEN_FLAG")==2){
				doorLockBean.setGtSixNoOFDoorLock(rs.getInt("DRLCK_NOOF_INSTANCE"));
				doorLockBean.setGtSixOpBalance(rs.getInt("DRLCK_SHIFT_OB"));
				doorLockBean.setGtSixNoticeServed(rs.getInt("DRLCK_NOOF_NOTICE"));
				doorLockBean.setGtSixMetersShifted(rs.getInt("DRLCK_NOOF_SHIFTED"));
				doorLockBean.setGtSixNotShift(rs.getInt("DRLCK_NOT_SHIFTED"));
				doorLockBean.setGtSixCloBalance(rs.getInt("DRLCK_CB"));
				
				totalNoOfInstances+=rs.getInt("DRLCK_NOOF_INSTANCE");
				totalOpeningBalance+=rs.getInt("DRLCK_SHIFT_OB");
				totalNoticesServed+=rs.getInt("DRLCK_NOOF_NOTICE");
				totalMeterShifted+=rs.getInt("DRLCK_NOOF_SHIFTED");
				totalNotPossibleShift+=rs.getInt("DRLCK_NOT_SHIFTED");
				totalClosingBalance+=rs.getInt("DRLCK_CB");
			}
			
			doorLockBean.setActionTaken(rs.getString("DRLCK_REASON"));
			doorLockBean.setThId(rs.getInt("TH_ID"));
			doorLockBean.setDrThId(rs.getString("DRLCK_TH_ID"));
			
			
			
		}
		doorLockBean.setTotalNoOfInstances(totalNoOfInstances);
		doorLockBean.setTotalOpeningBalance(totalOpeningBalance);
		doorLockBean.setTotalNoticesServed(totalNoticesServed);
		doorLockBean.setTotalMeterShifted(totalMeterShifted);
		doorLockBean.setTotalNotPossibleShift(totalNotPossibleShift);
		doorLockBean.setTotalClosingBalance(totalClosingBalance);
		
		
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally{
		MysqlConnectionProvider.releaseConnection(rs, null, preparedStatement, connection);
	}
	
	return doorLockBean;
}

}
