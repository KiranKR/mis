package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Util.AppUtil;
import com.Util.MysqlConnectionProvider;
import com.bean.DisconnectionBean;


public class DisconnectionDao {
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	PreparedStatement statement = null;
	PreparedStatement statement2 = null;
	PreparedStatement statement3 = null;
	ResultSet rs = null;
	
	public boolean saveData(DisconnectionBean disconnectionBean) {
		boolean isValid = false;
		String query = "";
		String query1 = "";
		int id = 0;
		try {
			connection =  MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			if(disconnectionBean.isExisting()){
				
					query ="insert into tran_head ( TH_DATE"+
					                              ",TH_SECTN_ID"+
					                              ",TH_MONTH"+
					                              ",TH_YEAR"+
					                              ",TH_ROW_STATUS"+
					                              ",TH_IDEN_FLAG"+
					                              ",TH_TRNSFRMR_FLD_OPBAL"+
					                              ",TH_TRNSFRMR_FLD_CBBAL"+
					                              ",TH_MNR_INSTALLED"+
					                              ",TH_MNR_REPLACED"+
					                              ",TH_MNR_REASON"+
					                              ",TH_CRPDET_INSPECTION"+
					                              ",TH_CRPDET_REMARKS"+
					                              ") VALUES (NOW(),?,?,?,0,3,0,0,0,0,'',0,'')";
					preparedStatement = connection.prepareStatement(query);
					preparedStatement.setString(1, disconnectionBean.getSecId());
					preparedStatement.setString(2, AppUtil.getMnthId(disconnectionBean.getMnthId()));
					preparedStatement.setString(3, disconnectionBean.getYearId());
					isValid = preparedStatement.execute();
				
					statement = connection.prepareStatement("select ifnull(max(th_id),1) from tran_head");
					rs = statement.executeQuery();
					while(rs.next()){
						id = rs.getInt(1);
					}
					
					query1 = "insert into disconnection ("
									+"DISC_TH_ID"
									+",DISC_VISITED"
									+",DISC_DISCONNECT"
									+",DISC_RECONNECT"
									+",DISC_AMNT_INVOLVE"
									+",DISC_AMNT_REALISE"
									+",DISC_CNTOB"
									+",DISC_CNTCB"
									+",DISC_AMNTOB"
									+",DISC_AMOUNTCB"
									+",DISC_REMARKS )"
									+" VALUES ("
									+" ?"
									+",?"
									+",?"
									+",?"
									+",?"
									+",?"
									+",?"
									+",?"
									+",?"
									+",?"
									+",?)";
					
					statement2 = connection.prepareStatement(query1);
					statement2.setInt(1, id);
					statement2.setInt(2,disconnectionBean.getVisitDurMnth());
					statement2.setInt(3,disconnectionBean.getDisconctDurMnth());
					statement2.setInt(4,disconnectionBean.getReconctDurMnth());
					statement2.setDouble(5,disconnectionBean.getAmtInv());
					statement2.setDouble(6,disconnectionBean.getAmtRel());
					statement2.setInt(7,disconnectionBean.getOpenBal());
					statement2.setInt(8,disconnectionBean.getCloseBal());
					statement2.setDouble(9,disconnectionBean.getOpenBalAmt());
					statement2.setDouble(10,disconnectionBean.getClosBalAmt());
					statement2.setString(11, disconnectionBean.getReason());
					isValid = statement2.execute();
			}else{
					query1 = "update disconnection set "
						+"DISC_TH_ID = "+ disconnectionBean.getThId()
						+",DISC_VISITED = "+ disconnectionBean.getVisitDurMnth()
						+",DISC_DISCONNECT = "+ disconnectionBean.getDisconctDurMnth()
						+",DISC_RECONNECT = "+ disconnectionBean.getReconctDurMnth()
						+",DISC_AMNT_INVOLVE = "+ disconnectionBean.getAmtInv()
						+",DISC_AMNT_REALISE = "+ disconnectionBean.getAmtRel()
						+",DISC_CNTOB = "+ disconnectionBean.getOpenBal()
						+",DISC_CNTCB = "+ disconnectionBean.getCloseBal()
						+",DISC_AMNTOB = "+ disconnectionBean.getOpenBalAmt()
						+",DISC_AMOUNTCB = "+ disconnectionBean.getClosBalAmt()
						+",DISC_REMARKS = '"+ disconnectionBean.getReason()
						+"' where DISC_ID = " + disconnectionBean.getDisId();
					statement2 = connection.prepareStatement(query1);
					isValid = statement2.execute();
			}
			
			String query2 = "";
			
			if (!disconnectionBean.isViewOlyOB()) {
				query2 = "insert into balance_ason (BLASON_LEVEL_IDEN "
						+ ",BLASON_LEVEL_FK_ID " + ",BLASON_IDEN_FLAG "
						+ ",BLASON_OB_COUNT " + ",BLASON_OB_AMOUNT "
						+ ",BLASON_LASTUPDATED " + ") VALUES ( " + " 5,"
						+ disconnectionBean.getSecId() + ",1,"
						+ disconnectionBean.getCloseBal() + ","
						+ disconnectionBean.getClosBalAmt()
						+ ",CURRENT_DATE())";
			} else if(disconnectionBean.isViewOlyOB()) {
				query2 = "update balance_ason SET BLASON_OB_COUNT = "
						+ disconnectionBean.getCloseBal()
						+ ", BLASON_OB_AMOUNT = "
						+ disconnectionBean.getClosBalAmt()
						+ ", BLASON_LASTUPDATED = CURRENT_DATE() WHERE BLASON_LEVEL_FK_ID ="+disconnectionBean.getSecId() 
					+ " AND BLASON_IDEN_FLAG = 1";
			}
			
			statement3 = connection.prepareStatement(query2);			
			isValid = statement3.execute(query2);
			connection.commit();
			connection.setAutoCommit(true);
			isValid = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			MysqlConnectionProvider.releaseConnection(rs, null, preparedStatement, connection);
			MysqlConnectionProvider.releaseConnection(null, null, statement, null);
			MysqlConnectionProvider.releaseConnection(null, null, statement2, null);
			MysqlConnectionProvider.releaseConnection(null, null, statement3, null);
		}
		return isValid;
	}

	public DisconnectionBean getDisconctnOB(DisconnectionBean disconnectionBean) {
		try {
			
			String query ="select ba.BLASON_ID," +
							"ba.BLASON_OB_COUNT," +
							"ba.BLASON_OB_AMOUNT " +
							"from balance_ason ba " +
							"where ba.BLASON_IDEN_FLAG = 1 " +
							"and ba.BLASON_LEVEL_IDEN = 5 " +
							"and ba.BLASON_LEVEL_FK_ID = " + disconnectionBean.getSecId();
			connection =  MysqlConnectionProvider.getNewConnection();
			preparedStatement = connection.prepareStatement(query);
			rs = preparedStatement.executeQuery();
			disconnectionBean.setNewOB(false);
			disconnectionBean.setObId(0);
			disconnectionBean.setOpenBal(0);
			disconnectionBean.setOpenBalAmt(0.0);
			disconnectionBean.setViewOlyOB(false);
			while(rs.next()){
				disconnectionBean.setNewOB(true);
				disconnectionBean.setViewOlyOB(true);
				disconnectionBean.setObId(rs.getInt(1));
				disconnectionBean.setOpenBal(rs.getInt(2));
				disconnectionBean.setOpenBalAmt(rs.getDouble(3));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			MysqlConnectionProvider.releaseConnection(rs, null, preparedStatement, connection);
		}
		return disconnectionBean;
	}	
	
	public DisconnectionBean getExistingData(DisconnectionBean disconnectionBean) {
		try {
			
			String query =" SELECT d.DISC_ID,"+
					      " th.TH_ID,"+
					      " d.DISC_VISITED,"+
					      " d.DISC_DISCONNECT,"+
					      " d.DISC_RECONNECT,"+
					      " d.DISC_AMNT_INVOLVE,"+
					      " d.DISC_AMNT_REALISE,"+
					      " th.TH_MONTH,"+
					      " th.TH_YEAR,"+
					      " d.DISC_CNTOB,"+
					      " d.DISC_AMNTOB,"+
					      " d.DISC_CNTCB,"+
					      " d.DISC_AMOUNTCB,"+
					      " d.DISC_REMARKS"+
					      " FROM disconnection d JOIN tran_head th ON d.DISC_TH_ID = th.TH_ID"+
					      " WHERE th.TH_MONTH = "+ AppUtil.getMnthId(disconnectionBean.getMnthId()) +" and"+
					      " th.TH_YEAR = "+ disconnectionBean.getYearId()+" and th.TH_SECTN_ID = " + disconnectionBean.getSecId();
			connection =  MysqlConnectionProvider.getNewConnection();
			preparedStatement = connection.prepareStatement(query);
			rs = preparedStatement.executeQuery();     
			disconnectionBean.setExisting(true); 
			disconnectionBean.setDisId("0");
			disconnectionBean.setThId("0");            
			disconnectionBean.setVisitDurMnth(0);      
			disconnectionBean.setDisconctDurMnth(0);   
			disconnectionBean.setReconctDurMnth(0); 
			disconnectionBean.setAmtInv(0.0);			
			disconnectionBean.setAmtRel(0.0);     
			disconnectionBean.setCloseBal(0);
			disconnectionBean.setClosBalAmt(0.0);
			disconnectionBean.setReason("");
			                                           
			while(rs.next()){                                                
				disconnectionBean.setExisting(false);                           
				disconnectionBean.setDisId(rs.getString(1));               
				disconnectionBean.setThId(rs.getString(2));                
				disconnectionBean.setVisitDurMnth(rs.getInt(3));           
				disconnectionBean.setDisconctDurMnth(rs.getInt(4));        
				disconnectionBean.setReconctDurMnth(rs.getInt(5));         
				disconnectionBean.setAmtInv(rs.getDouble(6));			   
				disconnectionBean.setAmtRel(rs.getDouble(7));
				disconnectionBean.setOpenBal(rs.getInt(10));
				disconnectionBean.setOpenBalAmt(rs.getDouble(11));
				disconnectionBean.setCloseBal(rs.getInt(12));
				disconnectionBean.setClosBalAmt(rs.getDouble(13));
				disconnectionBean.setReason(rs.getString(14)); 
			}                   
			
			/*int nxtMnt = 0;
			int nxtYr = 0;
			if(AppUtil.getMnthId(disconnectionBean.getMnthId()).equals("12")){
				nxtYr = Integer.parseInt(disconnectionBean.getYearId()) + 1;
				nxtMnt = 01;
			}else{
				nxtYr = Integer.parseInt(disconnectionBean.getYearId());
				nxtMnt = Integer.parseInt(AppUtil.getMnthId(disconnectionBean.getMnthId())) + 1;
			}
						query =" SELECT d.DISC_ID,"+
					      " th.TH_ID,"+
					      " d.DISC_VISITED,"+
					      " d.DISC_DISCONNECT,"+
					      " d.DISC_RECONNECT,"+
					      " d.DISC_AMNT_INVOLVE,"+
					      " d.DISC_AMNT_REALISE,"+
					      " th.TH_MONTH,"+
					      " th.TH_YEAR,"+
					      " d.DISC_CNTOB,"+
					      " d.DISC_AMNTOB,"+
					      " d.DISC_CNTCB,"+
					      " d.DISC_AMOUNTCB"+
					      " FROM disconnection d JOIN tran_head th ON d.DISC_TH_ID = th.TH_ID"+
					      " WHERE th.TH_MONTH = "+ nxtMnt +" and"+
					      " th.TH_YEAR = "+ nxtYr+" and th.TH_SECTN_ID = " + disconnectionBean.getSecId();
			preparedStatement = connection.prepareStatement(query);
			rs = preparedStatement.executeQuery();
			disconnectionBean.setOnlyView(false);
			while(rs.next()){
				disconnectionBean.setOnlyView(true);
			}*/
			query = "select ifnull(max(a),0) as mnyr from " +
					"(select concat(cast(max(t.TH_YEAR) as char),cast(max(t.TH_MONTH) as char)) as a from tran_head t " +
					"where t.TH_IDEN_FLAG=3 and t.TH_ROW_STATUS=0 " +
					"and t.TH_SECTN_ID="+ disconnectionBean.getSecId()+" group by t.TH_YEAR ) as b ";
	
			preparedStatement = connection.prepareStatement(query);
			rs = preparedStatement.executeQuery();
			disconnectionBean.setOnlyView(false);
			while(rs.next()){
				String year = disconnectionBean.getYearId();
				String mnth =AppUtil.getMnthId(disconnectionBean.getMnthId());
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
					disconnectionBean.setOnlyView(false);	
				}else if(Integer.valueOf(monthYear) >= Integer.valueOf(rsMntYr)){
					disconnectionBean.setOnlyView(false);
				}else{
					disconnectionBean.setOnlyView(true);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			MysqlConnectionProvider.releaseConnection(rs, null, preparedStatement, connection);
		}
		return disconnectionBean;
	}	
	
}
