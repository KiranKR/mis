package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Util.AppUtil;
import com.Util.MysqlConnectionProvider;
import com.bean.DoorLockBean;

public class DoorLockDao {
	
	
	Connection con = null;
	
	PreparedStatement pst1 =null;
	PreparedStatement pst2 =null;
	PreparedStatement pst3 =null;
	PreparedStatement pst4 =null;
	PreparedStatement pst5 =null;
	PreparedStatement pst6 =null;
	
	
	ResultSet rs = null;
	
	
	public int getOpeningBalance(DoorLockBean doorLockBean)
	{
		int opBal=0;
		try {
			String query ="SELECT ba.BLASON_OB_3TO6 "
						+ "FROM balance_ason ba "
						+ "WHERE ba.blason_level_iden=5 AND ba.BLASON_LEVEL_FK_ID = '"+doorLockBean.getSecId()+"' AND ba.BLASON_IDEN_FLAG = '8'";
			con=MysqlConnectionProvider.getNewConnection();
			pst1 = con.prepareStatement(query);
			rs = pst1.executeQuery();
			doorLockBean.setViewOlyOB(false);
			
			while(rs.next())
			{
		        doorLockBean.setViewOlyOB(true);  
				opBal = rs.getInt(1);
			}
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			MysqlConnectionProvider.releaseConnection(rs, null, pst1, con);
		}
		return opBal;
	}
	public int getGtSixOpeningBalance(DoorLockBean doorLockBean)
	{
		int gtSixopBal=0;
		try {
			String query ="SELECT ba.BLASON_OB_GRT6 "
						+ "FROM balance_ason ba "
						+ "WHERE ba.blason_level_iden=5 AND ba.BLASON_LEVEL_FK_ID = '"+doorLockBean.getSecId()+"' AND ba.BLASON_IDEN_FLAG = '8'";
			con=MysqlConnectionProvider.getNewConnection();
			pst1 = con.prepareStatement(query);
			rs = pst1.executeQuery();
			
			doorLockBean.setViewOlyOB(false);
			while(rs.next())
			{
				doorLockBean.setViewOlyOB(true);
				gtSixopBal = rs.getInt(1);
			}
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			MysqlConnectionProvider.releaseConnection(rs, null, pst1, con);
		}
		return gtSixopBal;
	}
	
	public DoorLockBean getExistingValues(DoorLockBean doorLockBean){
		
		Connection con =null;
		PreparedStatement pst1=null;
		ResultSet rs=null;
		
		String query1 ="SELECT d.DRLCK_NOOF_INSTANCE, "
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
			con = MysqlConnectionProvider.getNewConnection();
			pst1 = con.prepareStatement(query1);
			rs = pst1.executeQuery();
			
			doorLockBean.setNoOFDoorLock(0);
			
			doorLockBean.setNoticeServed(0);
			doorLockBean.setMetersShifted(0);
			doorLockBean.setNotShift(0);
			doorLockBean.setCloBalance(0);
			
			doorLockBean.setGtSixNoOFDoorLock(0);
			
			doorLockBean.setGtSixNoticeServed(0);
			doorLockBean.setGtSixMetersShifted(0);
			doorLockBean.setGtSixNotShift(0);
			doorLockBean.setGtSixCloBalance(0);
			doorLockBean.setActionTaken("");
			doorLockBean.setThId(0);
			doorLockBean.setDrThId("");
			
			
			while(rs.next()){
				if(rs.getInt("DRLCK_IDEN_FLAG")==1){
					doorLockBean.setNoOFDoorLock(rs.getInt("DRLCK_NOOF_INSTANCE"));
					doorLockBean.setOpBalance(rs.getInt("DRLCK_SHIFT_OB"));
					doorLockBean.setNoticeServed(rs.getInt("DRLCK_NOOF_NOTICE"));
					doorLockBean.setMetersShifted(rs.getInt("DRLCK_NOOF_SHIFTED"));
					doorLockBean.setNotShift(rs.getInt("DRLCK_NOT_SHIFTED"));
					doorLockBean.setCloBalance(rs.getInt("DRLCK_CB"));
					
				}else if(rs.getInt("DRLCK_IDEN_FLAG")==2){
					doorLockBean.setGtSixNoOFDoorLock(rs.getInt("DRLCK_NOOF_INSTANCE"));
					doorLockBean.setGtSixOpBalance(rs.getInt("DRLCK_SHIFT_OB"));
					doorLockBean.setGtSixNoticeServed(rs.getInt("DRLCK_NOOF_NOTICE"));
					doorLockBean.setGtSixMetersShifted(rs.getInt("DRLCK_NOOF_SHIFTED"));
					doorLockBean.setGtSixNotShift(rs.getInt("DRLCK_NOT_SHIFTED"));
					doorLockBean.setGtSixCloBalance(rs.getInt("DRLCK_CB"));
				}
				doorLockBean.setActionTaken(rs.getString("DRLCK_REASON"));
				doorLockBean.setThId(rs.getInt("TH_ID"));
				doorLockBean.setDrThId(rs.getString("DRLCK_TH_ID"));
			}
			
			String queryForNxtMnthValue = "select ifnull(max(a),0) as mnyr from ( "
										+ "select concat(cast(max(t.TH_YEAR) as char) ,cast(max(t.TH_MONTH) as char)) as a from tran_head t "
										+ "where t.TH_IDEN_FLAG = 8 and t.TH_ROW_STATUS=0 "
										+ "and t.TH_SECTN_ID='"+doorLockBean.getSecId()+"' group by t.TH_YEAR ) as b ";

			PreparedStatement preparedStatement2 = con.prepareStatement(queryForNxtMnthValue);
			rs = preparedStatement2.executeQuery();
			doorLockBean.setOnlyView(false);

			while(rs.next()){

			String year = doorLockBean.getYearId();
			String mnth =AppUtil.getMnthId(doorLockBean.getMonthId());
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
				doorLockBean.setOnlyView(false);
			}else if(Integer.valueOf(monthYear) >= Integer.valueOf(rsMntYr)){
				doorLockBean.setOnlyView(false);
			}else{
				doorLockBean.setOnlyView(true);
			}
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			MysqlConnectionProvider.releaseConnection(rs, null, pst1, con);
		}
		
		
		return doorLockBean;
	}
	
	
	
	
	public boolean saveDoorLockDetails(DoorLockBean doorLockBean)
	{    boolean isValid = false;
	       try {
			con = MysqlConnectionProvider.getNewConnection();
			con.setAutoCommit(false);
			con.setTransactionIsolation(8);
			
           
            int idenFlag =8;
            
            if(doorLockBean.isExisting()){ 		
			doorLockBean.setIdenFlag(idenFlag);
			String query1 ="INSERT INTO tran_head(TH_DATE,TH_SECTN_ID,TH_MONTH,TH_YEAR,TH_ROW_STATUS,TH_IDEN_FLAG) VALUES (NOW(),'"+doorLockBean.getSecId()+"','"+AppUtil.getMnthId(doorLockBean.getMonthId())+"','"+doorLockBean.getYearId()+"',0,'"+doorLockBean.getIdenFlag()+"')";
			pst1 = con.prepareStatement(query1);
			isValid = pst1.execute();
			String query2 ="SELECT ifnull(max(th.TH_ID), 1) FROM tran_head th";
			pst2 =con.prepareStatement(query2);
			rs = pst2.executeQuery();
			
			while(rs.next()){
			 doorLockBean.setThId(rs.getInt(1));
			}
			
			String query3="INSERT INTO doorlock(DRLCK_TH_ID,DRLCK_NOOF_INSTANCE,DRLCK_SHIFT_OB,DRLCK_NOOF_NOTICE,DRLCK_NOOF_SHIFTED,DRLCK_NOT_SHIFTED,DRLCK_CB,DRLCK_REASON,DRLCK_ROW_STATUS,DRLCK_IDEN_FLAG) VALUES ('"+doorLockBean.getThId()+"','"+doorLockBean.getNoOFDoorLock()+"','"+doorLockBean.getOpBalance()+"','"+doorLockBean.getNoticeServed()+"','"+doorLockBean.getMetersShifted()+"','"+doorLockBean.getNotShift()+"','"+doorLockBean.getCloBalance()+"','"+doorLockBean.getActionTaken()+"',0,1)";
			pst3 = con.prepareStatement(query3);
			isValid = pst3.execute();
			String query4 ="INSERT INTO doorlock(DRLCK_TH_ID,DRLCK_NOOF_INSTANCE,DRLCK_SHIFT_OB,DRLCK_NOOF_NOTICE,DRLCK_NOOF_SHIFTED,DRLCK_NOT_SHIFTED,DRLCK_CB,DRLCK_REASON,DRLCK_ROW_STATUS,DRLCK_IDEN_FLAG) VALUES ('"+doorLockBean.getThId()+"','"+doorLockBean.getGtSixNoOFDoorLock()+"','"+doorLockBean.getGtSixOpBalance()+"','"+doorLockBean.getGtSixNoticeServed()+"','"+doorLockBean.getGtSixMetersShifted()+"','"+doorLockBean.getGtSixNoticeServed()+"','"+doorLockBean.getGtSixCloBalance()+"','"+doorLockBean.getActionTaken()+"',0,2)"; 
			pst4 = con.prepareStatement(query4);
			isValid = pst4.execute();
			
			if(!doorLockBean.isViewOlyOB()){
				
				String balanceAsonInsertQuery = "insert into balance_ason ( " +
												" BLASON_LEVEL_IDEN         " +
												" ,BLASON_LEVEL_FK_ID       " +
												" ,BLASON_IDEN_FLAG         " +
												" ,BLASON_OB_3TO6           " +
												" ,BLASON_OB_GRT6           " +
												" ,BLASON_LASTUPDATED       " +
												" ) VALUES ( 5 ,"+doorLockBean.getSecId()+" ,"+8+" ,"+doorLockBean.getCloBalance()+" ,"+doorLockBean.getGtSixCloBalance()+", NOW() ) ";
												
				PreparedStatement pst5 = con.prepareStatement(balanceAsonInsertQuery);
				pst5.execute();
				
				
			}
			
			
            }else{
                  String trQuery =" update tran_head SET " +
					"TH_DATE = NOW()  ,TH_SECTN_ID = "+doorLockBean.getSecId()+"  , " +
					"TH_MONTH = "+AppUtil.getMnthId(doorLockBean.getMonthId())+"  ,TH_YEAR = "+doorLockBean.getYearId()+"  , " +
					"TH_ROW_STATUS = 0  ,TH_IDEN_FLAG = "+idenFlag+" " +
					"WHERE TH_ID = "+doorLockBean.getThId()+" ";
                 PreparedStatement pst7 = con.prepareStatement(trQuery);
                 isValid = pst7.execute();
                 
                 String drQry1 = "UPDATE doorlock dr "
                	           + "SET DRLCK_NOOF_INSTANCE = "+doorLockBean.getNoOFDoorLock()+", "
                	           + "DRLCK_SHIFT_OB = "+doorLockBean.getOpBalance()+", "
                	           + "DRLCK_NOOF_NOTICE = "+doorLockBean.getNoticeServed()+", "
                	           + "DRLCK_NOOF_SHIFTED = "+doorLockBean.getMetersShifted()+", "
                	           + "DRLCK_NOT_SHIFTED = "+doorLockBean.getNotShift()+", "
                	           + "DRLCK_CB = "+doorLockBean.getCloBalance()+", "
                	           + "DRLCK_REASON = '"+doorLockBean.getActionTaken()+"' "
                	           + "WHERE dr.DRLCK_TH_ID = '"+doorLockBean.getThId()+"' AND dr.DRLCK_IDEN_FLAG=1";
                 PreparedStatement pst8 = con.prepareStatement(drQry1);
                 isValid = pst8.execute();
                 
                 String drQry2 ="UPDATE doorlock dr "
      	           + "SET DRLCK_NOOF_INSTANCE = "+doorLockBean.getGtSixNoOFDoorLock()+", "
    	           + "DRLCK_SHIFT_OB = "+doorLockBean.getGtSixOpBalance()+", "
    	           + "DRLCK_NOOF_NOTICE = "+doorLockBean.getGtSixNoticeServed()+", "
    	           + "DRLCK_NOOF_SHIFTED = "+doorLockBean.getGtSixMetersShifted()+", "
    	           + "DRLCK_NOT_SHIFTED = "+doorLockBean.getGtSixNotShift()+", "
    	           + "DRLCK_CB = "+doorLockBean.getGtSixCloBalance()+", "
    	           + "DRLCK_REASON = '"+doorLockBean.getActionTaken()+"' "
    	           + "WHERE dr.DRLCK_TH_ID = '"+doorLockBean.getThId()+"' AND dr.DRLCK_IDEN_FLAG=2";
                PreparedStatement pst9 = con.prepareStatement(drQry2);
                isValid = pst9.execute();
                 
            }
            if(doorLockBean.isViewOlyOB()){
			String query5 = "UPDATE balance_ason ba "
						  + "SET ba.BLASON_OB_3TO6 = '"+doorLockBean.getCloBalance()+"', ba.BLASON_OB_GRT6 = '"+doorLockBean.getGtSixCloBalance()+"',  ba.BLASON_LASTUPDATED = CURRENT_DATE() "
						  + "WHERE ba.blason_level_iden=5 AND ba.BLASON_LEVEL_FK_ID = '"+doorLockBean.getSecId()+"' AND ba.BLASON_IDEN_FLAG = '8'" ;
			
			pst5 = con.prepareStatement(query5);
			isValid = pst5.execute();
			
			
            }
			con.commit();
			con.setAutoCommit(true);
			isValid = true;
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			MysqlConnectionProvider.releaseConnection(rs, null, pst1, con);
			MysqlConnectionProvider.releaseConnection(rs, null, pst2, con);
			MysqlConnectionProvider.releaseConnection(rs, null, pst3, con);
			MysqlConnectionProvider.releaseConnection(rs, null, pst4, con);
			MysqlConnectionProvider.releaseConnection(rs, null, pst5, con);
			MysqlConnectionProvider.releaseConnection(rs, null, pst6, con);
		}
		return isValid;
	}
	
	
}
