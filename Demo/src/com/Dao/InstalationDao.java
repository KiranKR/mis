package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Util.AppUtil;
import com.Util.MysqlConnectionProvider;
import com.bean.InstalationBean;


public class InstalationDao {
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	
	public boolean saveData(InstalationBean instalationBean) {
		boolean isValid = false;
		try {
			String query = "";
			connection =  MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			if(instalationBean.isExisting()){
			
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
						                              ",TH_METRD_WSUPPLY"+
						                              ",TH_UNMETRD_WSUPPLY"+
						                              ") VALUES (NOW(),?,?,?,0,6,0,0,0,0,'',0,'',?,?)";
				
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, instalationBean.getSecId());
				preparedStatement.setString(2, AppUtil.getMnthId(instalationBean.getMnthId()));
				preparedStatement.setString(3, instalationBean.getYearId());
				preparedStatement.setInt(4,instalationBean.getMtrWtrSply());
				preparedStatement.setInt(5,instalationBean.getUnMtrWtrSply());
				isValid = preparedStatement.execute();
				PreparedStatement statement = connection.prepareStatement("select ifnull(max(th_id),1) from tran_head");
				rs = statement.executeQuery();
				int id = 0;
				while(rs.next()){
					id = rs.getInt(1);
				}
				
				String query1 = "insert into instalation ("
								+ "  INSTL_TH_ID"
								+ " ,INSTL_IDEN_FLAG"
								+ " ,INSTL_PRINCIPAL"
								+ " ,INSTL_INTEREST"
								+ " ,INSTL_ROW_STATUS"
								+ " ) VALUES ("
								+ "  ?"
								+ " ,?"
								+ " ,?"
								+ " ,?"
								+ " ,0"
								+ " )";
				
				PreparedStatement statement2 = connection.prepareStatement(query1);
				statement2.setInt(1, id);
				statement2.setInt(2, 1);
				statement2.setDouble(3,instalationBean.getOpenBalPrinAmt());
				statement2.setDouble(4,instalationBean.getOpenBalIntAmt());
				isValid = statement2.execute();
				
				PreparedStatement statement3 = connection.prepareStatement(query1);
				statement3.setInt(1, id);
				statement3.setInt(2, 2);
				statement3.setDouble(3,instalationBean.getDemdBalPrinAmt());
				statement3.setDouble(4,instalationBean.getDemdBalIntAmt());
				isValid = statement3.execute();
				
				PreparedStatement statement4 = connection.prepareStatement(query1);
				statement4.setInt(1, id);
				statement4.setInt(2, 3);
				statement4.setDouble(3,instalationBean.getClctnBalPrinAmt());
				statement4.setDouble(4,instalationBean.getClctnBalIntAmt());
				isValid = statement4.execute();
				
				PreparedStatement statement5 = connection.prepareStatement(query1);
				statement5.setInt(1, id);
				statement5.setInt(2, 4);
				statement5.setDouble(3,instalationBean.getClosBalPrinAmt());
				statement5.setDouble(4,instalationBean.getClosBalIntAmt());
				isValid = statement5.execute();
				
			}else{
				
				query ="update tran_head set TH_DATE = NOW()"+
                ",TH_SECTN_ID = "+instalationBean.getSecId()+
                ",TH_MONTH = "+AppUtil.getMnthId(instalationBean.getMnthId())+
                ",TH_YEAR = "+instalationBean.getYearId()+
                ",TH_ROW_STATUS = 0" +
                ",TH_IDEN_FLAG = 6" +
                ",TH_TRNSFRMR_FLD_OPBAL = 0" +
                ",TH_TRNSFRMR_FLD_CBBAL = 0" +
                ",TH_MNR_INSTALLED = 0" +
                ",TH_MNR_REPLACED = 0" +
                ",TH_MNR_REASON = ''" +
                ",TH_CRPDET_INSPECTION = 0" +
                ",TH_CRPDET_REMARKS = ''"+
                ",TH_METRD_WSUPPLY = " + instalationBean.getMtrWtrSply()+
                ",TH_UNMETRD_WSUPPLY = " + instalationBean.getUnMtrWtrSply()+
                " where TH_ID = " + instalationBean.getThId();
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.execute();
								
				String query1 = "update instalation set"
					+ "  INSTL_TH_ID = "+instalationBean.getThId()
					+ " ,INSTL_IDEN_FLAG = 1"
					+ " ,INSTL_PRINCIPAL = "+ instalationBean.getOpenBalPrinAmt()
					+ " ,INSTL_INTEREST = "+instalationBean.getOpenBalIntAmt()
					+ " ,INSTL_ROW_STATUS = 0"
					+ " where INSTL_TH_ID = "+instalationBean.getThId()+" and INSTL_IDEN_FLAG = 1";
				PreparedStatement statement2 = connection.prepareStatement(query1);
				isValid = statement2.execute();
				
				
				String query2 = "update instalation set"
					+ "  INSTL_TH_ID = "+instalationBean.getThId()
					+ " ,INSTL_IDEN_FLAG = 2"
					+ " ,INSTL_PRINCIPAL = "+instalationBean.getDemdBalPrinAmt()
					+ " ,INSTL_INTEREST = "+instalationBean.getDemdBalIntAmt()
					+ " ,INSTL_ROW_STATUS = 0"
					+ " where INSTL_TH_ID = "+instalationBean.getThId()+" and INSTL_IDEN_FLAG = 2";
				PreparedStatement statement3 = connection.prepareStatement(query2);
				isValid = statement3.execute();
				
				
				String query3 = "update instalation set"
					+ "  INSTL_TH_ID = "+instalationBean.getThId()
					+ " ,INSTL_IDEN_FLAG = 3"
					+ " ,INSTL_PRINCIPAL = "+instalationBean.getClctnBalPrinAmt()
					+ " ,INSTL_INTEREST = "+instalationBean.getClctnBalIntAmt()
					+ " ,INSTL_ROW_STATUS = 0"
					+ " where INSTL_TH_ID = "+instalationBean.getThId()+" and INSTL_IDEN_FLAG = 3";
				PreparedStatement statement4 = connection.prepareStatement(query3);
				isValid = statement4.execute();
					
			
				String query4 = "update instalation set"
					+ "  INSTL_TH_ID = "+instalationBean.getThId()
					+ " ,INSTL_IDEN_FLAG = 4"
					+ " ,INSTL_PRINCIPAL = "+instalationBean.getClosBalPrinAmt()
					+ " ,INSTL_INTEREST = "+instalationBean.getClosBalIntAmt()
					+ " ,INSTL_ROW_STATUS = 0"
					+ " where INSTL_TH_ID = "+instalationBean.getThId()+" and INSTL_IDEN_FLAG = 4";
				PreparedStatement statement5 = connection.prepareStatement(query4);
				isValid = statement5.execute();
				
			}

			String query2 = "";
			
			if (!instalationBean.isViewOlyOB()) {
				query2 = "insert into balance_ason (BLASON_LEVEL_IDEN "
					+ ",BLASON_LEVEL_FK_ID " 
					+ ",BLASON_IDEN_FLAG "
					+ ",BLASON_OB_AMOUNT " 
					+ ",BLASON_OB_INTEREST "
					+ ",BLASON_LASTUPDATED " + ") VALUES ( " 
					+ " 5,"
					+ instalationBean.getSecId() 
					+ ",6,"
					+ instalationBean.getClosBalPrinAmt() + ","
					+ instalationBean.getClosBalIntAmt()
					+ ",now())";
			} else if(instalationBean.isViewOlyOB()) {
				query2 = "update balance_ason SET BLASON_OB_AMOUNT = "
					+ instalationBean.getClosBalPrinAmt()
					+ ", BLASON_OB_INTEREST = "
					+ instalationBean.getClosBalIntAmt()
					+ ", BLASON_LASTUPDATED = NOW() WHERE BLASON_LEVEL_FK_ID ="+instalationBean.getSecId() 
					+ " and BLASON_IDEN_FLAG = 6";
			}
			PreparedStatement statement6 = connection.prepareStatement(query2);			
			isValid = statement6.execute(query2);
			
			
			connection.commit();
			connection.setAutoCommit(true);
			isValid = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			MysqlConnectionProvider.releaseConnection(rs, null, preparedStatement, connection);
		}
		return isValid;
	}

	
	public InstalationBean getExistingData(InstalationBean instalationBean) {
		String query ="";
		try {
			query ="SELECT i.INSTL_ID,"
					      + " th.TH_ID,"
					      + " th.TH_METRD_WSUPPLY,"
					      + " th.TH_UNMETRD_WSUPPLY,"
					      + " (th.TH_METRD_WSUPPLY + th.TH_UNMETRD_WSUPPLY) AS TOTAL,"
					      + " i.INSTL_IDEN_FLAG,"
					      + " i.INSTL_PRINCIPAL,"
					      + " i.INSTL_INTEREST,"
					      + " (i.INSTL_PRINCIPAL + i.INSTL_INTEREST) AS TOTAL_AMOUNT,"
					      + " th.TH_MONTH,"
					      + " th.TH_YEAR,"
					      + " th.TH_SECTN_ID"
						  + " FROM instalation i JOIN tran_head th ON i.INSTL_TH_ID = th.TH_ID"
						  + " WHERE th.TH_MONTH = "+AppUtil.getMnthId(instalationBean.getMnthId())+" AND th.TH_YEAR = "+instalationBean.getYearId()+" AND th.TH_SECTN_ID = " + instalationBean.getSecId();
			connection =  MysqlConnectionProvider.getNewConnection();
			preparedStatement = connection.prepareStatement(query);
			rs = preparedStatement.executeQuery();
			int i = 0;
			instalationBean.setInstlId("");    
			instalationBean.setThId("");                                                  
			instalationBean.setMtrWtrSply(0);                                       
			instalationBean.setUnMtrWtrSply(0);  
			instalationBean.setTotal(0);
			/*instalationBean.setOpenBalPrinAmt(0);   
			instalationBean.setOpenBalIntAmt(0);    
			instalationBean.setOpenBalTtlAmt(0);*/ 
			instalationBean.setDemdBalPrinAmt(0);   
			instalationBean.setDemdBalIntAmt(0);    
			instalationBean.setDemdBalTtlAmt(0); 
			instalationBean.setClctnBalPrinAmt(0);   
			instalationBean.setClctnBalIntAmt(0);    
			instalationBean.setClctnBalTtlAmt(0); 
			instalationBean.setClosBalPrinAmt(0);   
			instalationBean.setClosBalIntAmt(0);    
			instalationBean.setClosBalTtlAmt(0);
			instalationBean.setExisting(true);
			while(rs.next()){
				instalationBean.setExisting(false);
				if(i == 0){
					instalationBean.setInstlId(rs.getString(1));    
					instalationBean.setThId(rs.getString(2));                                                  
					instalationBean.setMtrWtrSply(rs.getInt(3));                                       
					instalationBean.setUnMtrWtrSply(rs.getInt(4));  
					instalationBean.setTotal(rs.getInt(5));     
				}
				i++;
				if(rs.getInt(6) == 1){
					instalationBean.setOpenBalPrinAmt(rs.getDouble(7));   
					instalationBean.setOpenBalIntAmt(rs.getDouble(8));    
					instalationBean.setOpenBalTtlAmt(rs.getDouble(9));    
				}else if(rs.getInt(6) == 2){
					instalationBean.setDemdBalPrinAmt(rs.getDouble(7));   
					instalationBean.setDemdBalIntAmt(rs.getDouble(8));    
					instalationBean.setDemdBalTtlAmt(rs.getDouble(9)); 
				}else if(rs.getInt(6) == 3){
					instalationBean.setClctnBalPrinAmt(rs.getDouble(7));   
					instalationBean.setClctnBalIntAmt(rs.getDouble(8));    
					instalationBean.setClctnBalTtlAmt(rs.getDouble(9)); 
				}else if(rs.getInt(6) == 4){
					instalationBean.setClosBalPrinAmt(rs.getDouble(7));   
					instalationBean.setClosBalIntAmt(rs.getDouble(8));    
					instalationBean.setClosBalTtlAmt(rs.getDouble(9));
				}
			}
			/*int nxtMnt = 0;
			int nxtYr = 0;
			if(AppUtil.getMnthId(instalationBean.getMnthId()).equals("12")){
				nxtYr = Integer.parseInt(instalationBean.getYearId()) + 1;
				nxtMnt = 01;
			}else{
				nxtYr = Integer.parseInt(instalationBean.getYearId());
				nxtMnt = Integer.parseInt(AppUtil.getMnthId(instalationBean.getMnthId())) + 1;
			}
			query ="SELECT i.INSTL_ID,"
			      + " th.TH_ID,"
			      + " th.TH_METRD_WSUPPLY,"
			      + " th.TH_UNMETRD_WSUPPLY,"
			      + " (th.TH_METRD_WSUPPLY + th.TH_UNMETRD_WSUPPLY) AS TOTAL,"
			      + " i.INSTL_IDEN_FLAG,"
			      + " i.INSTL_PRINCIPAL,"
			      + " i.INSTL_INTEREST,"
			      + " (i.INSTL_PRINCIPAL + i.INSTL_INTEREST) AS TOTAL_AMOUNT,"
			      + " th.TH_MONTH,"
			      + " th.TH_YEAR,"
			      + " th.TH_SECTN_ID"
				  + " FROM instalation i JOIN tran_head th ON i.INSTL_TH_ID = th.TH_ID"
				  + " WHERE th.TH_MONTH = "+nxtMnt+" AND th.TH_YEAR = "+nxtYr+" AND th.TH_SECTN_ID = " + instalationBean.getSecId();*/
			
			query = "select ifnull(max(a),0) as mnyr from " +
							"(select concat(cast(max(t.TH_YEAR) as char),cast(max(t.TH_MONTH) as char)) as a from tran_head t " +
							"where t.TH_IDEN_FLAG=6 and t.TH_ROW_STATUS=0 " +
							"and t.TH_SECTN_ID="+instalationBean.getSecId()+" group by t.TH_YEAR ) as b ";
					
			preparedStatement = connection.prepareStatement(query);
			rs = preparedStatement.executeQuery();
			instalationBean.setOnlyView(false);
			while(rs.next()){
				String year = instalationBean.getYearId();
				String mnth =AppUtil.getMnthId(instalationBean.getMnthId());
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
					instalationBean.setOnlyView(false);	
				}else if(Integer.valueOf(monthYear) >= Integer.valueOf(rsMntYr)){
					instalationBean.setOnlyView(false);
				}else{
					instalationBean.setOnlyView(true);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			MysqlConnectionProvider.releaseConnection(rs, null, preparedStatement, connection);
		}
	return instalationBean;	
	}

	
	
	
	public InstalationBean getDisconctnOB(InstalationBean instalationBean) {
		try {
			
			String query ="select ba.BLASON_ID," +
							"ba.BLASON_OB_AMOUNT, " +
							"ba.BLASON_OB_INTEREST " +
							"from balance_ason ba " +
							"where ba.BLASON_IDEN_FLAG = 6 " +
							"and ba.BLASON_LEVEL_IDEN = 5 " +
							"and ba.BLASON_LEVEL_FK_ID = " + instalationBean.getSecId();
			
			connection =  MysqlConnectionProvider.getNewConnection();
			preparedStatement = connection.prepareStatement(query);
			rs = preparedStatement.executeQuery();
			
			instalationBean.setNewOB(false);
			
			instalationBean.setOpenBalPrinAmt(0);
			instalationBean.setOpenBalIntAmt(0);
			instalationBean.setOpenBalTtlAmt(0.0);
			
			instalationBean.setClosBalPrinAmt(0.0);
			instalationBean.setClosBalIntAmt(0.0);
			instalationBean.setClosBalTtlAmt(0.0);
			
			instalationBean.setClctnBalPrinAmt(0.0);
			instalationBean.setClctnBalIntAmt(0.0);
			instalationBean.setClctnBalTtlAmt(0.0);
			
			instalationBean.setDemdBalPrinAmt(0.0);
			instalationBean.setDemdBalIntAmt(0.0);
			instalationBean.setDemdBalTtlAmt(0.0);
			
			instalationBean.setInstlId("");
			instalationBean.setViewOlyOB(false);
			while(rs.next()){
				instalationBean.setNewOB(true);
				instalationBean.setViewOlyOB(true);
				instalationBean.setInstlId(rs.getString(1));
				instalationBean.setOpenBalPrinAmt(rs.getDouble(2));
				instalationBean.setOpenBalIntAmt(rs.getDouble(3));
				instalationBean.setOpenBalTtlAmt((rs.getDouble(2) + rs.getDouble(3)));
				instalationBean.setClosBalPrinAmt(rs.getDouble(2));
				instalationBean.setClosBalIntAmt(rs.getDouble(3));
				instalationBean.setClosBalTtlAmt((rs.getDouble(2) + rs.getDouble(3)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			MysqlConnectionProvider.releaseConnection(rs, null, preparedStatement, connection);
		}
		return instalationBean;
	}	
	
}