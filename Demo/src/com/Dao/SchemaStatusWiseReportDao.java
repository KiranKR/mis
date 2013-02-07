package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Util.MysqlConnectionProvider;
import com.bean.ReportBean;

public class SchemaStatusWiseReportDao {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	ReportBean reportBean = new ReportBean();
	
	public ReportBean getReportBean (String role, String roleBelogs)
	{
		String wherClause = "";
		if (role.equals("1")) {
			wherClause = " z.ZONE_ID = " + roleBelogs;
		} else if (role.equals("2")) {
			wherClause = " c.CRCL_ID = " + roleBelogs;
		} else if (role.equals("3")) {
			wherClause = " d.DIV_ID = " + roleBelogs;
		} else if (role.equals("4")) {
			wherClause = " sd.SUBDIV_ID = " + roleBelogs;
		} else if (role.equals("5")) {
			wherClause = " s.SECTN_ID = " + roleBelogs;
		}

		
	String query ="SELECT s.SECTN_ID, "
				+"s.SECTN_NAME, "
				+"sd.SUBDIV_ID, " 
				+"sd.SUBDIV_NAME, "
				+"d.DIV_ID, "
				+"d.DIV_NAME, "
				+"c.CRCL_ID, "
				+"c.CRCL_NAME, "
				+"z.ZONE_ID, "
				+"z.ZONE_NAME, "
				+"pw.PWRK_CSTCTG_ID, "
				+"pw.PWRK_STAGE_IDEN, "
				+"pw.PWRK_ESTIMATE_COST "
				+"FROM program_works pw "
				+"JOIN section s "
				+"ON s.SECTN_ID = pw.PWRK_SECTN_ID "
				+"JOIN sub_division sd "
				+"ON sd.SUBDIV_ID = s.SECTN_SUBDIV_ID "
				+"JOIN division d "
				+"ON d.DIV_ID = sd.SUBDIV_DIV_ID "
				+"JOIN circle c "
				+"ON c.CRCL_ID = d.DIV_CIRCLE_ID "
				+"JOIN zone z "
				+"ON z.ZONE_ID = c.CRCL_ZONE_ID "
				+"WHERE "+wherClause+" "
				+"ORDER BY PWRK_CSTCTG_ID ";	
		
		try {
			connection = MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			int scWrkYetCnt =0;
		    int scWrkTknCnt =0;
		    int scWrkComCnt =0;
		    int scServiceCnt =0;
		    double scWrkYetAmt =0.0;
			double scWrkTknAmt =0.0;
			double scWrkComAmt =0.0;
			double scServiceAmt =0.0;
			
			int stWrkYetCnt =0;
		    int stWrkTknCnt =0;
		    int stWrkComCnt =0;
		    int stServiceCnt =0;
		    double stWrkYetAmt =0.0;
			double stWrkTknAmt =0.0;
			double stWrkComAmt =0.0;
			double stServiceAmt =0.0;
			
			int bckWrkYetCnt =0;
		    int bckWrkTknCnt =0;
		    int bckWrkComCnt =0;
		    int bckServiceCnt =0;
		    double bckWrkYetAmt =0.0;
			double bckWrkTknAmt =0.0;
			double bckWrkComAmt =0.0;
			double bckServiceAmt =0.0;
			
			int mnWrkYetCnt =0;
		    int mnWrkTknCnt =0;
		    int mnWrkComCnt =0;
		    int mnServiceCnt =0;
		    double mnWrkYetAmt =0.0;
			double mnWrkTknAmt =0.0;
			double mnWrkComAmt =0.0;
			double mnServiceAmt =0.0;
			
			int totWrkYetCnt =0;
			int totWrkTknCnt =0;
			int totWrkComCnt =0;
			int totServiceCnt =0;
			double totWrkYetAmt =0.0;
			double totWrkTknAmt =0.0;
			double totWrkComAmt =0.0;
			double totServiceAmt =0.0;
			
			int scCountTotal=0;
			int stCountTotal =0;
			int bckCountTotal =0;
			int mnCountTotal =0;
			double scAmountTotal =0.0;
			double stAmountTotal =0.0;
			double bckAmountTotal =0.0;
			double mnAmountTotal =0.0;
			
			int totalCount =0;
			double totalAmount =0.0;
			
			
			
			
			
			while (resultSet.next()) {
				
				if(resultSet.getInt("PWRK_CSTCTG_ID")==1){
					
					if(resultSet.getInt("PWRK_STAGE_IDEN")<4){
						
						scWrkYetCnt++;
						scWrkYetAmt+=resultSet.getInt("PWRK_ESTIMATE_COST");
						reportBean.setScWrkYetCnt(scWrkYetCnt);
						reportBean.setScWrkYetAmt(scWrkYetAmt);
						
					}
					else if (resultSet.getInt("PWRK_STAGE_IDEN")==4) {
						scWrkTknCnt++;
						scWrkTknAmt+=resultSet.getInt("PWRK_ESTIMATE_COST");
						reportBean.setScWrkTknCnt(scWrkTknCnt);
						reportBean.setScWrkTknAmt(scWrkTknAmt);
						
					}
					else if ((resultSet.getInt("PWRK_STAGE_IDEN")==5 || resultSet.getInt("PWRK_STAGE_IDEN")==6)) {
						
						 scWrkComCnt++;
						 scWrkComAmt+=resultSet.getInt("PWRK_ESTIMATE_COST");
						 reportBean.setScWrkComCnt(scWrkComCnt);
						 reportBean.setScWrkComAmt(scWrkComAmt);
					}
					else if (resultSet.getInt("PWRK_STAGE_IDEN")==7) {
						
						scServiceCnt++;
						scServiceAmt+=resultSet.getInt("PWRK_ESTIMATE_COST");
						reportBean.setScServiceCnt(scServiceCnt);
						reportBean.setScServiceAmt(scServiceAmt);
					}
					
					
				}
				else if (resultSet.getInt("PWRK_CSTCTG_ID")==2) {
					
                    if(resultSet.getInt("PWRK_STAGE_IDEN")<4){
						
						stWrkYetCnt++;
						stWrkYetAmt+=resultSet.getInt("PWRK_ESTIMATE_COST");
						reportBean.setStWrkYetCnt(stWrkYetCnt);
						reportBean.setStWrkYetAmt(stWrkYetAmt);
						
					}
					else if (resultSet.getInt("PWRK_STAGE_IDEN")==4) {
						stWrkTknCnt++;
						stWrkTknAmt+=resultSet.getInt("PWRK_ESTIMATE_COST");
						reportBean.setStWrkTknCnt(stWrkTknCnt);
						reportBean.setStWrkTknAmt(stWrkTknAmt);
						
					}
					else if ((resultSet.getInt("PWRK_STAGE_IDEN")==5 || resultSet.getInt("PWRK_STAGE_IDEN")==6)) {
						
						 stWrkComCnt++;
						 stWrkComAmt+=resultSet.getInt("PWRK_ESTIMATE_COST");
						 reportBean.setStWrkComCnt(stWrkComCnt);
						 reportBean.setStWrkComAmt(stWrkComAmt);
					}
					else if (resultSet.getInt("PWRK_STAGE_IDEN")==7) {
						
						stServiceCnt++;
						stServiceAmt+=resultSet.getInt("PWRK_ESTIMATE_COST");
						reportBean.setStServiceCnt(stServiceCnt);
						reportBean.setStServiceAmt(stServiceAmt);
					}
					
					
				}
				else if (resultSet.getInt("PWRK_CSTCTG_ID")==3) {
					
                    if(resultSet.getInt("PWRK_STAGE_IDEN")<4){
						
						bckWrkYetCnt++;
						bckWrkYetAmt+=resultSet.getInt("PWRK_ESTIMATE_COST");
						reportBean.setBckWrkYetCnt(bckWrkYetCnt);
						reportBean.setBckWrkYetAmt(bckWrkYetAmt);
						
					}
					else if (resultSet.getInt("PWRK_STAGE_IDEN")==4) {
						bckWrkTknCnt++;
						bckWrkTknAmt+=resultSet.getInt("PWRK_ESTIMATE_COST");
						reportBean.setBckWrkTknCnt(bckWrkTknCnt);
						reportBean.setBckWrkTknAmt(bckWrkTknAmt);
						
					}
					else if ((resultSet.getInt("PWRK_STAGE_IDEN")==5 || resultSet.getInt("PWRK_STAGE_IDEN")==6)) {
						
						 bckWrkComCnt++;
						 bckWrkComAmt+=resultSet.getInt("PWRK_ESTIMATE_COST");
						 reportBean.setBckWrkComCnt(bckWrkComCnt);
						 reportBean.setBckWrkComAmt(bckWrkComAmt);
					}
					else if (resultSet.getInt("PWRK_STAGE_IDEN")==7) {
						
						bckServiceCnt++;
						bckServiceAmt+=resultSet.getInt("PWRK_ESTIMATE_COST");
						reportBean.setBckServiceCnt(bckServiceCnt);
						reportBean.setBckServiceAmt(bckServiceAmt);
					}
					
					
				}
				else if (resultSet.getInt("PWRK_CSTCTG_ID")==4) {
					
                   if(resultSet.getInt("PWRK_STAGE_IDEN")<4){
						
						mnWrkYetCnt++;
						mnWrkYetAmt+=resultSet.getInt("PWRK_ESTIMATE_COST");
						reportBean.setMnWrkYetCnt(mnWrkYetCnt);
						reportBean.setMnWrkYetAmt(mnWrkYetAmt);
						
					}
					else if (resultSet.getInt("PWRK_STAGE_IDEN")==4) {
						mnWrkTknCnt++;
						mnWrkTknAmt+=resultSet.getInt("PWRK_ESTIMATE_COST");
						reportBean.setMnWrkTknCnt(mnWrkTknCnt);
						reportBean.setMnWrkTknAmt(mnWrkTknAmt);
						
					}
					else if ((resultSet.getInt("PWRK_STAGE_IDEN")==5 || resultSet.getInt("PWRK_STAGE_IDEN")==6)) {
						
						 mnWrkComCnt++;
						 mnWrkComAmt+=resultSet.getInt("PWRK_ESTIMATE_COST");
						 reportBean.setMnWrkComCnt(mnWrkComCnt);
						 reportBean.setMnWrkComAmt(mnWrkComAmt);
					}
					else if (resultSet.getInt("PWRK_STAGE_IDEN")==7) {
						
						mnServiceCnt++;
						mnServiceAmt+=resultSet.getInt("PWRK_ESTIMATE_COST");
						reportBean.setMnServiceCnt(mnServiceCnt);
						reportBean.setMnServiceAmt(mnServiceAmt);
					}
					
					
				}
				
				totWrkYetCnt = scWrkYetCnt+stWrkYetCnt+bckWrkYetCnt+mnWrkYetCnt;
				totWrkTknCnt = scWrkTknCnt+stWrkTknCnt+bckWrkTknCnt+mnWrkTknCnt;
				totWrkComCnt = scWrkComCnt+stWrkComCnt+bckWrkComCnt+mnWrkComCnt;
				totServiceCnt = scServiceCnt+stServiceCnt+bckServiceCnt+mnServiceCnt;
				
				totWrkYetAmt = scWrkYetAmt+stWrkYetAmt+bckWrkYetAmt+mnWrkYetAmt;
				totWrkTknAmt = scWrkTknAmt+stWrkTknAmt+bckWrkTknAmt+mnWrkTknAmt;
				totWrkComAmt = scWrkComAmt+stWrkComAmt+bckWrkComAmt+mnWrkComAmt;
				totServiceAmt = scServiceAmt+stServiceAmt+bckServiceAmt+mnServiceAmt;
				
				reportBean.setTotWrkYetCnt(totWrkYetCnt);
				reportBean.setTotWrkTknCnt(totWrkTknCnt);
				reportBean.setTotWrkComCnt(totWrkComCnt);
				reportBean.setTotServiceCnt(totServiceCnt);
				
				reportBean.setTotWrkYetAmt(totWrkYetAmt);
				reportBean.setTotWrkTknAmt(totWrkTknAmt);
				reportBean.setTotWrkComAmt(totWrkComAmt);
				reportBean.setTotServiceAmt(totServiceAmt);
				
				

				scCountTotal = scWrkYetCnt+scWrkTknCnt+scWrkComCnt+scServiceCnt;
				stCountTotal = stWrkYetCnt+stWrkTknCnt+stWrkComCnt+stServiceCnt;
				bckCountTotal = bckWrkYetCnt+bckWrkTknCnt+bckWrkComCnt+bckServiceCnt;
				mnCountTotal = mnWrkYetCnt+mnWrkTknCnt+mnWrkComCnt+mnServiceCnt;
				totalCount=totWrkYetCnt+totWrkTknCnt+totWrkComCnt+totServiceCnt;
				
				scAmountTotal = scWrkYetAmt+scWrkTknAmt+scWrkComAmt+scServiceAmt;
				stAmountTotal = stWrkYetAmt+stWrkTknAmt+stWrkComAmt+stServiceAmt;
				bckAmountTotal = bckWrkYetAmt+bckWrkTknAmt+bckWrkComAmt+bckServiceAmt;
				mnAmountTotal = mnWrkYetAmt+mnWrkTknAmt+mnWrkComAmt+mnServiceAmt;
				totalAmount=totWrkYetAmt+totWrkTknAmt+totWrkComAmt+totServiceAmt;
				
				reportBean.setScCountTotal(scCountTotal);
				reportBean.setScAmountTotal(scAmountTotal);
				reportBean.setStCountTotal(stCountTotal);
				reportBean.setStAmountTotal(stAmountTotal);
				reportBean.setBckCountTotal(bckCountTotal);
				reportBean.setBckAmountTotal(bckAmountTotal);
				reportBean.setMnCountTotal(mnCountTotal);
				reportBean.setMnAmountTotal(mnAmountTotal);
				
				reportBean.setTotalCount(totalCount);
				reportBean.setTotalAmount(totalAmount);
				
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
		}
		finally {
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement, connection);
			
		}
	
 		return reportBean;
		
 		
	}

}
