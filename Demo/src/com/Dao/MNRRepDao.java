package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Util.MysqlConnectionProvider;
import com.bean.MNRRepBean;

public class MNRRepDao {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	MNRRepBean bean = null;
	List<MNRRepBean> mnrlList = null;
	List<MNRRepBean> dispMnrList = new ArrayList<MNRRepBean>();

	public List<MNRRepBean> viewData(String role, String roleBelogs,
			String yearId) {
      
		String wherClause = "";
		if (role.equals("1")) {
			wherClause = " and z.ZONE_ID = " + roleBelogs;
		} else if (role.equals("2")) {
			wherClause = " and c.CRCL_ID = " + roleBelogs;
		} else if (role.equals("3")) {
			wherClause = " and d.DIV_ID = " + roleBelogs;
		} else if (role.equals("4")) {
			wherClause = " and sd.SUBDIV_ID = " + roleBelogs;
		} else if (role.equals("5")) {
			wherClause = " and s.SECTN_ID = " + roleBelogs;
		}

		mnrlList = new ArrayList<MNRRepBean>();
		MNRRepBean mnrRepBean = null;


	
		try {
			String query = "SELECT th.TH_ID, "
					+ "mn.MDET_TH_ID, "
					+ "s.SECTN_ID,s.SECTN_NAME, "
					+ "sd.SUBDIV_ID,sd.SUBDIV_NAME, "
					+ "d.DIV_ID,d.DIV_NAME, "
					+ "c.CRCL_ID,c.CRCL_NAME, "
					+ "z.ZONE_ID,z.ZONE_NAME,"
					+ "mn.MDET_IDEN_FLAG, "
					+ "mn.MDET_OB, "
					+ "mn.MDET_INSTALATION, "
					+ "mn.MDET_REPLACED, "
					+ "mn.MDET_CB "
					+ "FROM mnr_detail mn "
					+ "JOIN tran_head th "
					+ "ON th.TH_ID = mn.MDET_TH_ID "
					+ "JOIN section s "
					+ "ON s.SECTN_ID = th.TH_SECTN_ID "
					+ "JOIN sub_division sd "
					+ "ON sd.SUBDIV_ID = s.SECTN_SUBDIV_ID "
					+ "JOIN division d "
					+ "ON d.DIV_ID = sd.SUBDIV_DIV_ID "
					+ "JOIN circle c "
					+ "ON c.CRCL_ID = d.DIV_CIRCLE_ID "
					+ "JOIN zone z "
					+ "ON z.ZONE_ID = c.CRCL_ZONE_ID where th.TH_YEAR='"+yearId+"' "
					+ wherClause
					+ " order by s.SECTN_ID ,sd.SUBDIV_ID,d.DIV_ID,c.CRCL_ID,z.ZONE_ID ";

			connection = MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			int i=1;
			while (resultSet.next()) {
				
				mnrRepBean = new MNRRepBean();
                    
				mnrRepBean.setSlno(i++);
				mnrRepBean.setSecId(resultSet.getString("SECTN_ID"));
				mnrRepBean.setSecName(resultSet.getString("SECTN_NAME"));
				mnrRepBean.setSdId(resultSet.getString("SUBDIV_ID"));
				mnrRepBean.setSdName(resultSet.getString("SUBDIV_NAME"));
				mnrRepBean.setdId(resultSet.getString("DIV_ID"));
				mnrRepBean.setdName(resultSet.getString("DIV_NAME"));
				mnrRepBean.setcId(resultSet.getString("CRCL_ID"));
				mnrRepBean.setcName(resultSet.getString("CRCL_NAME"));
				mnrRepBean.setzId(resultSet.getString("ZONE_ID"));
				mnrRepBean.setzName(resultSet.getString("ZONE_NAME"));
				mnrRepBean.setIdenFlag(resultSet.getString("MDET_IDEN_FLAG"));
				mnrRepBean.setLevel("5");

					if (resultSet.getString("MDET_IDEN_FLAG").equals("1")) {

						mnrRepBean.setOpbBet1to3(resultSet.getInt("MDET_OB"));
						
						mnrRepBean.setMnrInsbet1to3(resultSet.getInt("MDET_INSTALATION"));
						
						mnrRepBean.setMnrReplbet1to3(resultSet.getInt("MDET_REPLACED"));
						
						mnrRepBean.setClbBet1to3(resultSet.getInt("MDET_CB"));
						

					

					} else if (resultSet.getString("MDET_IDEN_FLAG").equals("2")) {

						mnrRepBean.setOpbBet3to6(resultSet.getInt("MDET_OB"));
						mnrRepBean.setMnrInsbet3to6(resultSet.getInt("MDET_INSTALATION"));
						mnrRepBean.setMnrReplbet3to6(resultSet.getInt("MDET_REPLACED"));
						mnrRepBean.setClbBet3to6(resultSet.getInt("MDET_CB"));
					
						
						

					} else if (resultSet.getString("MDET_IDEN_FLAG").equals("3")) {
						
						mnrRepBean.setOpbGrt6(resultSet.getInt("MDET_OB"));
						mnrRepBean.setMnrInsGrt6(resultSet.getInt("MDET_INSTALATION"));
						mnrRepBean.setMnrReplGrt6(resultSet.getInt("MDET_REPLACED"));
						mnrRepBean.setClbGrt6(resultSet.getInt("MDET_CB"));

						

					}
					mnrlList.add(mnrRepBean);
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
		if(role.equals("1")){
			dispMnrList = zoneWise("first","0");
		}
		else if (role.equals("2")){
			dispMnrList = circleWise("first","0");		
		}
		else if (role.equals("3")){
			dispMnrList = divWise("first","0");		
		}
		else if (role.equals("4")){
			dispMnrList = subDivWise("first","0");		
		}
		else if (role.equals("5")){
			dispMnrList = secWise("first","0");		
		}
		return dispMnrList;
		
		
	}
	
   public List<MNRRepBean> secWise(String id,String lvl){
	    
	   
	    
	    int totOPBbet1to3=0;
		int totMNRInsbet1to3 =0;
		int totMNRReplbet1to3 = 0;
		int totCLBbet1to3 =0;
		
		int totOPBbet3to6=0;
		int totMNRInsbet3to6=0;
		int totMNRReplbet3to6=0;
		int totCLBbet3to6=0;
		
		int totOPBGrt6=0;
		int totMNRInsGrt6=0;
		int totMNRReplGrt6=0;
		int totCLBGrt6=0;
	   
		
		
		List<MNRRepBean> tempList = new ArrayList<MNRRepBean>();
		dispMnrList = new ArrayList<MNRRepBean>();
		
		if (id == "first") {
			tempList = mnrlList;
		} else if(lvl.equals("4")) {
			for (MNRRepBean mnrRepBean : mnrlList) {

				if (mnrRepBean.getSdId().equals(id)) {
					tempList.add(mnrRepBean);
				} 
			}

		}else if (lvl.equals("5")) {
			for (MNRRepBean mnrRepBean : mnrlList) {

			 if (mnrRepBean.getSecId().equals(id)) {
					tempList.add(mnrRepBean);
				}
			}
			
		}
		String sid = "";
		for (MNRRepBean mnrRepBean : tempList) {
			if(sid.equals("")){
				bean = new MNRRepBean();
				sid=mnrRepBean.getSecId();
				bean.setId(mnrRepBean.getSecId());
				bean.setLable(mnrRepBean.getSecName());
				bean.setLevel("5");
			   
				if(mnrRepBean.getIdenFlag().equals("1")){
					
					totOPBbet1to3=totOPBbet1to3+mnrRepBean.getOpbBet1to3();
					totMNRInsbet1to3=totMNRInsbet1to3+mnrRepBean.getMnrInsbet1to3();
					totMNRReplbet1to3=totMNRReplbet1to3+mnrRepBean.getMnrReplbet1to3();
					totCLBbet1to3=totCLBbet1to3+mnrRepBean.getClbBet1to3();
					
					bean.setTotOPBbet1to3(totOPBbet1to3);
					bean.setTotMNRInsbet1to3(totMNRInsbet1to3);
					bean.setTotMNRReplbet1to3(totMNRReplbet1to3);
					bean.setTotCLBbet1to3(totCLBbet1to3);
					
					
			
					dispMnrList.add(bean);
					
					
				 }
				else if (mnrRepBean.getIdenFlag().equals("2")) {
					totOPBbet3to6+= mnrRepBean.getOpbBet3to6();
					totMNRInsbet3to6+=mnrRepBean.getMnrInsbet3to6();
					totMNRReplbet3to6+=mnrRepBean.getMnrReplbet3to6();
					totCLBbet3to6+=mnrRepBean.getClbBet3to6();
					
					
					bean.setTotOPBbet3to6(totOPBbet3to6);
					bean.setTotMNRInsbet3to6(totMNRInsbet3to6);
					bean.setTotMNRReplbet3to6(totMNRReplbet3to6);
					bean.setTotCLBbet3to6(totCLBbet3to6);
					
					
					
					dispMnrList.add(bean);
					
				}
				else if (mnrRepBean.getIdenFlag().equals("3")) {
					totOPBGrt6+=mnrRepBean.getOpbGrt6();
					totMNRInsGrt6+=mnrRepBean.getMnrInsGrt6();
					totMNRReplGrt6+=mnrRepBean.getMnrReplGrt6();
					totCLBGrt6+=mnrRepBean.getClbGrt6();
					
					bean.setTotOPBGrt6(totOPBGrt6);
					bean.setTotMNRInsGrt6(totMNRInsGrt6);
					bean.setTotMNRReplGrt6(totMNRReplGrt6);
					bean.setTotCLBGrt6(totCLBGrt6);
					
				
					
					dispMnrList.add(bean);
				}	
			    
			 }
				
			else if (!sid.equals(mnrRepBean.getSecId())) {
				
				totOPBbet1to3 = 0;
				totMNRInsbet1to3 = 0;
				totMNRReplbet1to3 = 0;
				totCLBbet1to3 = 0;

				totOPBbet3to6 = 0;
				totMNRInsbet3to6 = 0;
				totMNRReplbet3to6 = 0;
				totCLBbet3to6 = 0;

				totOPBGrt6 = 0;
				totMNRInsGrt6 = 0;
				totMNRReplGrt6 = 0;
				totCLBGrt6 = 0;
				
				bean = new MNRRepBean();
				sid=mnrRepBean.getSecId();
				
			
				bean.setId(mnrRepBean.getSecId());
				bean.setLable(mnrRepBean.getSecName());
				bean.setLevel("5");
			   
				if(mnrRepBean.getIdenFlag().equals("1")){
					
					totOPBbet1to3=totOPBbet1to3+mnrRepBean.getOpbBet1to3();
					totMNRInsbet1to3=totMNRInsbet1to3+mnrRepBean.getMnrInsbet1to3();
					totMNRReplbet1to3=totMNRReplbet1to3+mnrRepBean.getMnrReplbet1to3();
					totCLBbet1to3=totCLBbet1to3+mnrRepBean.getClbBet1to3();
					
					bean.setTotOPBbet1to3(totOPBbet1to3);
					bean.setTotMNRInsbet1to3(totMNRInsbet1to3);
					bean.setTotMNRReplbet1to3(totMNRReplbet1to3);
					bean.setTotCLBbet1to3(totCLBbet1to3);
					
					
					
					dispMnrList.add(bean);
					
					
				 }
				else if (mnrRepBean.getIdenFlag().equals("2")) {
					totOPBbet3to6+= mnrRepBean.getOpbBet3to6();
					totMNRInsbet3to6+=mnrRepBean.getMnrInsbet3to6();
					totMNRReplbet3to6+=mnrRepBean.getMnrReplbet3to6();
					totCLBbet3to6+=mnrRepBean.getClbBet3to6();
					
					bean.setTotOPBbet3to6(totOPBbet3to6);
					bean.setTotMNRInsbet3to6(totMNRInsbet3to6);
					bean.setTotMNRReplbet3to6(totMNRReplbet3to6);
					bean.setTotCLBbet3to6(totCLBbet3to6);
					
					
					
					
					
					dispMnrList.add(bean);
					
				}
				else if (mnrRepBean.getIdenFlag().equals("3")) {
					totOPBGrt6+=mnrRepBean.getOpbGrt6();
					totMNRInsGrt6+=mnrRepBean.getMnrInsGrt6();
					totMNRReplGrt6+=mnrRepBean.getMnrReplGrt6();
					totCLBGrt6+=mnrRepBean.getClbGrt6();
					
					bean.setTotOPBGrt6(totOPBGrt6);
					bean.setTotMNRInsGrt6(totMNRInsGrt6);
					bean.setTotMNRReplGrt6(totMNRReplGrt6);
					bean.setTotCLBGrt6(totCLBGrt6);
					
					
					
					dispMnrList.add(bean);
				}	
			    
			 }
				else if(sid.equals(mnrRepBean.getSecId())) { 
					
					
					 if(mnrRepBean.getIdenFlag().equals("1")){
							
							totOPBbet1to3=totOPBbet1to3+mnrRepBean.getOpbBet1to3();
							totMNRInsbet1to3=totMNRInsbet1to3+mnrRepBean.getMnrInsbet1to3();
							totMNRReplbet1to3=totMNRReplbet1to3+mnrRepBean.getMnrReplbet1to3();
							totCLBbet1to3=totCLBbet1to3+mnrRepBean.getClbBet1to3();
							
							bean.setTotOPBbet1to3(totOPBbet1to3);
							bean.setTotMNRInsbet1to3(totMNRInsbet1to3);
							bean.setTotMNRReplbet1to3(totMNRReplbet1to3);
							bean.setTotCLBbet1to3(totCLBbet1to3);
							
							
							
							
							
							
						 }
						else if (mnrRepBean.getIdenFlag().equals("2")) {
							totOPBbet3to6+= mnrRepBean.getOpbBet3to6();
							totMNRInsbet3to6+=mnrRepBean.getMnrInsbet3to6();
							totMNRReplbet3to6+=mnrRepBean.getMnrReplbet3to6();
							totCLBbet3to6+=mnrRepBean.getClbBet3to6();
							
							bean.setTotOPBbet3to6(totOPBbet3to6);
							bean.setTotMNRInsbet3to6(totMNRInsbet3to6);
							bean.setTotMNRReplbet3to6(totMNRReplbet3to6);
							bean.setTotCLBbet3to6(totCLBbet3to6);
							
							
							
							
							
						}
						else if (mnrRepBean.getIdenFlag().equals("3")) {
							totOPBGrt6+=mnrRepBean.getOpbGrt6();
							totMNRInsGrt6+=mnrRepBean.getMnrInsGrt6();
							totMNRReplGrt6+=mnrRepBean.getMnrReplGrt6();
							totCLBGrt6+=mnrRepBean.getClbGrt6();
							
							bean.setTotOPBGrt6(totOPBGrt6);
							bean.setTotMNRInsGrt6(totMNRInsGrt6);
							bean.setTotMNRReplGrt6(totMNRReplGrt6);
							bean.setTotCLBGrt6(totCLBGrt6);
							
							
							
							
						}	
		   
					 
					 
					 
				}
		}
		/*dispMnrList.add(bean);*/
		return dispMnrList;
}

   public List<MNRRepBean> subDivWise(String id,String lvl){

	    
	   
	    
	    int totOPBbet1to3=0;
		int totMNRInsbet1to3 =0;
		int totMNRReplbet1to3 = 0;
		int totCLBbet1to3 =0;
		
		int totOPBbet3to6=0;
		int totMNRInsbet3to6=0;
		int totMNRReplbet3to6=0;
		int totCLBbet3to6=0;
		
		int totOPBGrt6=0;
		int totMNRInsGrt6=0;
		int totMNRReplGrt6=0;
		int totCLBGrt6=0;
	   
		List<MNRRepBean> tempList = new ArrayList<MNRRepBean>();
		dispMnrList = new ArrayList<MNRRepBean>();
		if (id == "first") {
			tempList = mnrlList;
		} else if(lvl.equals("3")) {
			for (MNRRepBean mnrRepBean : mnrlList) {

				if (mnrRepBean.getdId().equals(id)) {
					tempList.add(mnrRepBean);
				} 
			}
		}else if (lvl.equals("4")) {
			for (MNRRepBean mnrRepBean : mnrlList) {

			     if (mnrRepBean.getSdId().equals(id)) {
					tempList.add(mnrRepBean);
				}
			}
			
		}
		String sdid = "";
		for (MNRRepBean mnrRepBean : tempList) {
			if(sdid.equals("")){
				bean = new MNRRepBean();
				sdid=mnrRepBean.getSdId();
				bean.setId(mnrRepBean.getSdId());
				bean.setLable(mnrRepBean.getSdName());
				bean.setLevel("4");
			   
				if(mnrRepBean.getIdenFlag().equals("1")){
					
					totOPBbet1to3=totOPBbet1to3+mnrRepBean.getOpbBet1to3();
					totMNRInsbet1to3=totMNRInsbet1to3+mnrRepBean.getMnrInsbet1to3();
					totMNRReplbet1to3=totMNRReplbet1to3+mnrRepBean.getMnrReplbet1to3();
					totCLBbet1to3=totCLBbet1to3+mnrRepBean.getClbBet1to3();
					
					bean.setTotOPBbet1to3(totOPBbet1to3);
					bean.setTotMNRInsbet1to3(totMNRInsbet1to3);
					bean.setTotMNRReplbet1to3(totMNRReplbet1to3);
					bean.setTotCLBbet1to3(totCLBbet1to3);
					
					dispMnrList.add(bean);
					
					
				 }
				else if (mnrRepBean.getIdenFlag().equals("2")) {
					totOPBbet3to6+= mnrRepBean.getOpbBet3to6();
					totMNRInsbet3to6+=mnrRepBean.getMnrInsbet3to6();
					totMNRReplbet3to6+=mnrRepBean.getMnrReplbet3to6();
					totCLBbet3to6+=mnrRepBean.getClbBet3to6();
					
					
					bean.setTotOPBbet3to6(totOPBbet3to6);
					bean.setTotMNRInsbet3to6(totMNRInsbet3to6);
					bean.setTotMNRReplbet3to6(totMNRReplbet3to6);
					bean.setTotCLBbet3to6(totCLBbet3to6);
					
					dispMnrList.add(bean);
					
				}
				else if (mnrRepBean.getIdenFlag().equals("3")) {
					totOPBGrt6+=mnrRepBean.getOpbGrt6();
					totMNRInsGrt6+=mnrRepBean.getMnrInsGrt6();
					totMNRReplGrt6+=mnrRepBean.getMnrReplGrt6();
					totCLBGrt6+=mnrRepBean.getClbGrt6();
					
					bean.setTotOPBGrt6(totOPBGrt6);
					bean.setTotMNRInsGrt6(totMNRInsGrt6);
					bean.setTotMNRReplGrt6(totMNRReplGrt6);
					bean.setTotCLBGrt6(totCLBGrt6);
					
					dispMnrList.add(bean);
				}	
			    
			 }
				
			else if (!sdid.equals(mnrRepBean.getSdId())) {
				
				totOPBbet1to3 = 0;
				totMNRInsbet1to3 = 0;
				totMNRReplbet1to3 = 0;
				totCLBbet1to3 = 0;

				totOPBbet3to6 = 0;
				totMNRInsbet3to6 = 0;
				totMNRReplbet3to6 = 0;
				totCLBbet3to6 = 0;

				totOPBGrt6 = 0;
				totMNRInsGrt6 = 0;
				totMNRReplGrt6 = 0;
				totCLBGrt6 = 0;
				
				bean = new MNRRepBean();
				sdid=mnrRepBean.getSdId();
				
			
				bean.setId(mnrRepBean.getSdId());
				bean.setLable(mnrRepBean.getSdName());
				bean.setLevel("4");
			   
				if(mnrRepBean.getIdenFlag().equals("1")){
					
					totOPBbet1to3=totOPBbet1to3+mnrRepBean.getOpbBet1to3();
					totMNRInsbet1to3=totMNRInsbet1to3+mnrRepBean.getMnrInsbet1to3();
					totMNRReplbet1to3=totMNRReplbet1to3+mnrRepBean.getMnrReplbet1to3();
					totCLBbet1to3=totCLBbet1to3+mnrRepBean.getClbBet1to3();
					
					bean.setTotOPBbet1to3(totOPBbet1to3);
					bean.setTotMNRInsbet1to3(totMNRInsbet1to3);
					bean.setTotMNRReplbet1to3(totMNRReplbet1to3);
					bean.setTotCLBbet1to3(totCLBbet1to3);
					
					dispMnrList.add(bean);
					
					
				 }
				else if (mnrRepBean.getIdenFlag().equals("2")) {
					totOPBbet3to6+= mnrRepBean.getOpbBet3to6();
					totMNRInsbet3to6+=mnrRepBean.getMnrInsbet3to6();
					totMNRReplbet3to6+=mnrRepBean.getMnrReplbet3to6();
					totCLBbet3to6+=mnrRepBean.getClbBet3to6();
					
					bean.setTotOPBbet3to6(totOPBbet3to6);
					bean.setTotMNRInsbet3to6(totMNRInsbet3to6);
					bean.setTotMNRReplbet3to6(totMNRReplbet3to6);
					bean.setTotCLBbet3to6(totCLBbet3to6);
					
					dispMnrList.add(bean);
					
				}
				else if (mnrRepBean.getIdenFlag().equals("3")) {
					totOPBGrt6+=mnrRepBean.getOpbGrt6();
					totMNRInsGrt6+=mnrRepBean.getMnrInsGrt6();
					totMNRReplGrt6+=mnrRepBean.getMnrReplGrt6();
					totCLBGrt6+=mnrRepBean.getClbGrt6();
					
					bean.setTotOPBGrt6(totOPBGrt6);
					bean.setTotMNRInsGrt6(totMNRInsGrt6);
					bean.setTotMNRReplGrt6(totMNRReplGrt6);
					bean.setTotCLBGrt6(totCLBGrt6);
					
					dispMnrList.add(bean);
				}	
			    
			 }
				else if(sdid.equals(mnrRepBean.getSdId())) { 
					
					
					 if(mnrRepBean.getIdenFlag().equals("1")){
							
							totOPBbet1to3=totOPBbet1to3+mnrRepBean.getOpbBet1to3();
							totMNRInsbet1to3=totMNRInsbet1to3+mnrRepBean.getMnrInsbet1to3();
							totMNRReplbet1to3=totMNRReplbet1to3+mnrRepBean.getMnrReplbet1to3();
							totCLBbet1to3=totCLBbet1to3+mnrRepBean.getClbBet1to3();
							
							bean.setTotOPBbet1to3(totOPBbet1to3);
							bean.setTotMNRInsbet1to3(totMNRInsbet1to3);
							bean.setTotMNRReplbet1to3(totMNRReplbet1to3);
							bean.setTotCLBbet1to3(totCLBbet1to3);
							
							
							
							
						 }
						else if (mnrRepBean.getIdenFlag().equals("2")) {
							totOPBbet3to6+= mnrRepBean.getOpbBet3to6();
							totMNRInsbet3to6+=mnrRepBean.getMnrInsbet3to6();
							totMNRReplbet3to6+=mnrRepBean.getMnrReplbet3to6();
							totCLBbet3to6+=mnrRepBean.getClbBet3to6();
							
							bean.setTotOPBbet3to6(totOPBbet3to6);
							bean.setTotMNRInsbet3to6(totMNRInsbet3to6);
							bean.setTotMNRReplbet3to6(totMNRReplbet3to6);
							bean.setTotCLBbet3to6(totCLBbet3to6);
							
							
							
						}
						else if (mnrRepBean.getIdenFlag().equals("3")) {
							totOPBGrt6+=mnrRepBean.getOpbGrt6();
							totMNRInsGrt6+=mnrRepBean.getMnrInsGrt6();
							totMNRReplGrt6+=mnrRepBean.getMnrReplGrt6();
							totCLBGrt6+=mnrRepBean.getClbGrt6();
							
							bean.setTotOPBGrt6(totOPBGrt6);
							bean.setTotMNRInsGrt6(totMNRInsGrt6);
							bean.setTotMNRReplGrt6(totMNRReplGrt6);
							bean.setTotCLBGrt6(totCLBGrt6);
							
							
						}	
		   
					 
					 
					 
				}
		}
		
		return dispMnrList;

   }
   public List<MNRRepBean> divWise(String id,String lvl){

	    
	   
	    
	    int totOPBbet1to3=0;
		int totMNRInsbet1to3 =0;
		int totMNRReplbet1to3 = 0;
		int totCLBbet1to3 =0;
		
		int totOPBbet3to6=0;
		int totMNRInsbet3to6=0;
		int totMNRReplbet3to6=0;
		int totCLBbet3to6=0;
		
		int totOPBGrt6=0;
		int totMNRInsGrt6=0;
		int totMNRReplGrt6=0;
		int totCLBGrt6=0;
	   
		List<MNRRepBean> tempList = new ArrayList<MNRRepBean>();
		dispMnrList = new ArrayList<MNRRepBean>();
		if (id=="first") {
			tempList=mnrlList;
		}
		else if(lvl.equals("2")) {
			for (MNRRepBean mnrRepBean : mnrlList) {
				
				if(mnrRepBean.getcId().equals(id)){
				  tempList.add(mnrRepBean); 
			}
				
		}
		}else if (lvl.equals("3")) {
      for (MNRRepBean mnrRepBean : mnrlList) {
				
				
				 if(mnrRepBean.getdId().equals(id)){
					  tempList.add(mnrRepBean); 
				}
		}
			
		}
		String did = "";
		for (MNRRepBean mnrRepBean : tempList) {
			if(did.equals("")){
				bean = new MNRRepBean();
				did=mnrRepBean.getdId();
				bean.setId(mnrRepBean.getdId());
				bean.setLable(mnrRepBean.getdName());
				bean.setLevel("3");
			   
				if(mnrRepBean.getIdenFlag().equals("1")){
					
					totOPBbet1to3=totOPBbet1to3+mnrRepBean.getOpbBet1to3();
					totMNRInsbet1to3=totMNRInsbet1to3+mnrRepBean.getMnrInsbet1to3();
					totMNRReplbet1to3=totMNRReplbet1to3+mnrRepBean.getMnrReplbet1to3();
					totCLBbet1to3=totCLBbet1to3+mnrRepBean.getClbBet1to3();
					
					bean.setTotOPBbet1to3(totOPBbet1to3);
					bean.setTotMNRInsbet1to3(totMNRInsbet1to3);
					bean.setTotMNRReplbet1to3(totMNRReplbet1to3);
					bean.setTotCLBbet1to3(totCLBbet1to3);
					
					dispMnrList.add(bean);
					
					
				 }
				else if (mnrRepBean.getIdenFlag().equals("2")) {
					totOPBbet3to6+= mnrRepBean.getOpbBet3to6();
					totMNRInsbet3to6+=mnrRepBean.getMnrInsbet3to6();
					totMNRReplbet3to6+=mnrRepBean.getMnrReplbet3to6();
					totCLBbet3to6+=mnrRepBean.getClbBet3to6();
					
					
					bean.setTotOPBbet3to6(totOPBbet3to6);
					bean.setTotMNRInsbet3to6(totMNRInsbet3to6);
					bean.setTotMNRReplbet3to6(totMNRReplbet3to6);
					bean.setTotCLBbet3to6(totCLBbet3to6);
					
					dispMnrList.add(bean);
					
				}
				else if (mnrRepBean.getIdenFlag().equals("3")) {
					totOPBGrt6+=mnrRepBean.getOpbGrt6();
					totMNRInsGrt6+=mnrRepBean.getMnrInsGrt6();
					totMNRReplGrt6+=mnrRepBean.getMnrReplGrt6();
					totCLBGrt6+=mnrRepBean.getClbGrt6();
					
					bean.setTotOPBGrt6(totOPBGrt6);
					bean.setTotMNRInsGrt6(totMNRInsGrt6);
					bean.setTotMNRReplGrt6(totMNRReplGrt6);
					bean.setTotCLBGrt6(totCLBGrt6);
					
					dispMnrList.add(bean);
				}	
			    
			 }
				
			else if (!did.equals(mnrRepBean.getdId())) {
				
				totOPBbet1to3 = 0;
				totMNRInsbet1to3 = 0;
				totMNRReplbet1to3 = 0;
				totCLBbet1to3 = 0;

				totOPBbet3to6 = 0;
				totMNRInsbet3to6 = 0;
				totMNRReplbet3to6 = 0;
				totCLBbet3to6 = 0;

				totOPBGrt6 = 0;
				totMNRInsGrt6 = 0;
				totMNRReplGrt6 = 0;
				totCLBGrt6 = 0;
				
				bean = new MNRRepBean();
				did=mnrRepBean.getdId();
				
			
				bean.setId(mnrRepBean.getdId());
				bean.setLable(mnrRepBean.getdName());
				bean.setLevel("3");
			   
				if(mnrRepBean.getIdenFlag().equals("1")){
					
					totOPBbet1to3=totOPBbet1to3+mnrRepBean.getOpbBet1to3();
					totMNRInsbet1to3=totMNRInsbet1to3+mnrRepBean.getMnrInsbet1to3();
					totMNRReplbet1to3=totMNRReplbet1to3+mnrRepBean.getMnrReplbet1to3();
					totCLBbet1to3=totCLBbet1to3+mnrRepBean.getClbBet1to3();
					
					bean.setTotOPBbet1to3(totOPBbet1to3);
					bean.setTotMNRInsbet1to3(totMNRInsbet1to3);
					bean.setTotMNRReplbet1to3(totMNRReplbet1to3);
					bean.setTotCLBbet1to3(totCLBbet1to3);
					
					dispMnrList.add(bean);
					
					
				 }
				else if (mnrRepBean.getIdenFlag().equals("2")) {
					totOPBbet3to6+= mnrRepBean.getOpbBet3to6();
					totMNRInsbet3to6+=mnrRepBean.getMnrInsbet3to6();
					totMNRReplbet3to6+=mnrRepBean.getMnrReplbet3to6();
					totCLBbet3to6+=mnrRepBean.getClbBet3to6();
					
					bean.setTotOPBbet3to6(totOPBbet3to6);
					bean.setTotMNRInsbet3to6(totMNRInsbet3to6);
					bean.setTotMNRReplbet3to6(totMNRReplbet3to6);
					bean.setTotCLBbet3to6(totCLBbet3to6);
					
					dispMnrList.add(bean);
					
				}
				else if (mnrRepBean.getIdenFlag().equals("3")) {
					totOPBGrt6+=mnrRepBean.getOpbGrt6();
					totMNRInsGrt6+=mnrRepBean.getMnrInsGrt6();
					totMNRReplGrt6+=mnrRepBean.getMnrReplGrt6();
					totCLBGrt6+=mnrRepBean.getClbGrt6();
					
					bean.setTotOPBGrt6(totOPBGrt6);
					bean.setTotMNRInsGrt6(totMNRInsGrt6);
					bean.setTotMNRReplGrt6(totMNRReplGrt6);
					bean.setTotCLBGrt6(totCLBGrt6);
					
					dispMnrList.add(bean);
				}	
			    
			 }
				else if(did.equals(mnrRepBean.getdId())) { 
					
					
					 if(mnrRepBean.getIdenFlag().equals("1")){
							
							totOPBbet1to3=totOPBbet1to3+mnrRepBean.getOpbBet1to3();
							totMNRInsbet1to3=totMNRInsbet1to3+mnrRepBean.getMnrInsbet1to3();
							totMNRReplbet1to3=totMNRReplbet1to3+mnrRepBean.getMnrReplbet1to3();
							totCLBbet1to3=totCLBbet1to3+mnrRepBean.getClbBet1to3();
							
							bean.setTotOPBbet1to3(totOPBbet1to3);
							bean.setTotMNRInsbet1to3(totMNRInsbet1to3);
							bean.setTotMNRReplbet1to3(totMNRReplbet1to3);
							bean.setTotCLBbet1to3(totCLBbet1to3);
							
							
							
							
						 }
						else if (mnrRepBean.getIdenFlag().equals("2")) {
							totOPBbet3to6+= mnrRepBean.getOpbBet3to6();
							totMNRInsbet3to6+=mnrRepBean.getMnrInsbet3to6();
							totMNRReplbet3to6+=mnrRepBean.getMnrReplbet3to6();
							totCLBbet3to6+=mnrRepBean.getClbBet3to6();
							
							bean.setTotOPBbet3to6(totOPBbet3to6);
							bean.setTotMNRInsbet3to6(totMNRInsbet3to6);
							bean.setTotMNRReplbet3to6(totMNRReplbet3to6);
							bean.setTotCLBbet3to6(totCLBbet3to6);
							
							
							
						}
						else if (mnrRepBean.getIdenFlag().equals("3")) {
							totOPBGrt6+=mnrRepBean.getOpbGrt6();
							totMNRInsGrt6+=mnrRepBean.getMnrInsGrt6();
							totMNRReplGrt6+=mnrRepBean.getMnrReplGrt6();
							totCLBGrt6+=mnrRepBean.getClbGrt6();
							
							bean.setTotOPBGrt6(totOPBGrt6);
							bean.setTotMNRInsGrt6(totMNRInsGrt6);
							bean.setTotMNRReplGrt6(totMNRReplGrt6);
							bean.setTotCLBGrt6(totCLBGrt6);
							
							
						}	
		   
					 
					 
					 
				}
		}
		
		return dispMnrList;
   }
   public List<MNRRepBean> circleWise(String id,String lvl){

	    
	   
	    
	    int totOPBbet1to3=0;
		int totMNRInsbet1to3 =0;
		int totMNRReplbet1to3 = 0;
		int totCLBbet1to3 =0;
		
		int totOPBbet3to6=0;
		int totMNRInsbet3to6=0;
		int totMNRReplbet3to6=0;
		int totCLBbet3to6=0;
		
		int totOPBGrt6=0;
		int totMNRInsGrt6=0;
		int totMNRReplGrt6=0;
		int totCLBGrt6=0;
	   
		List<MNRRepBean> tempList = new ArrayList<MNRRepBean>();
		dispMnrList = new ArrayList<MNRRepBean>();
		if (id=="first") {
			tempList=mnrlList;
		}
		else if(lvl.equals("1")) {
			for (MNRRepBean mnrRepBean : mnrlList) {
				
				if(mnrRepBean.getzId().equals(id)){
				  tempList.add(mnrRepBean); 
			}
				
		}
		}else if (lvl.equals("2")) {
        for (MNRRepBean mnrRepBean : mnrlList) {
				
				 if(mnrRepBean.getcId().equals(id)){
					  tempList.add(mnrRepBean); 
				}
		}
			
		}
		String cid = "";
		for (MNRRepBean mnrRepBean : tempList) {
			if(cid.equals("")){
				bean = new MNRRepBean();
				cid=mnrRepBean.getcId();
				bean.setId(mnrRepBean.getcId());
				bean.setLable(mnrRepBean.getcName());
				bean.setLevel("2");
			   
				if(mnrRepBean.getIdenFlag().equals("1")){
					
					totOPBbet1to3=totOPBbet1to3+mnrRepBean.getOpbBet1to3();
					totMNRInsbet1to3=totMNRInsbet1to3+mnrRepBean.getMnrInsbet1to3();
					totMNRReplbet1to3=totMNRReplbet1to3+mnrRepBean.getMnrReplbet1to3();
					totCLBbet1to3=totCLBbet1to3+mnrRepBean.getClbBet1to3();
					
					bean.setTotOPBbet1to3(totOPBbet1to3);
					bean.setTotMNRInsbet1to3(totMNRInsbet1to3);
					bean.setTotMNRReplbet1to3(totMNRReplbet1to3);
					bean.setTotCLBbet1to3(totCLBbet1to3);
					
					dispMnrList.add(bean);
					
					
				 }
				else if (mnrRepBean.getIdenFlag().equals("2")) {
					totOPBbet3to6+= mnrRepBean.getOpbBet3to6();
					totMNRInsbet3to6+=mnrRepBean.getMnrInsbet3to6();
					totMNRReplbet3to6+=mnrRepBean.getMnrReplbet3to6();
					totCLBbet3to6+=mnrRepBean.getClbBet3to6();
					
					
					bean.setTotOPBbet3to6(totOPBbet3to6);
					bean.setTotMNRInsbet3to6(totMNRInsbet3to6);
					bean.setTotMNRReplbet3to6(totMNRReplbet3to6);
					bean.setTotCLBbet3to6(totCLBbet3to6);
					
					dispMnrList.add(bean);
					
				}
				else if (mnrRepBean.getIdenFlag().equals("3")) {
					totOPBGrt6+=mnrRepBean.getOpbGrt6();
					totMNRInsGrt6+=mnrRepBean.getMnrInsGrt6();
					totMNRReplGrt6+=mnrRepBean.getMnrReplGrt6();
					totCLBGrt6+=mnrRepBean.getClbGrt6();
					
					bean.setTotOPBGrt6(totOPBGrt6);
					bean.setTotMNRInsGrt6(totMNRInsGrt6);
					bean.setTotMNRReplGrt6(totMNRReplGrt6);
					bean.setTotCLBGrt6(totCLBGrt6);
					
					dispMnrList.add(bean);
				}	
			    
			 }
				
			else if (!cid.equals(mnrRepBean.getcId())) {
				
				totOPBbet1to3 = 0;
				totMNRInsbet1to3 = 0;
				totMNRReplbet1to3 = 0;
				totCLBbet1to3 = 0;

				totOPBbet3to6 = 0;
				totMNRInsbet3to6 = 0;
				totMNRReplbet3to6 = 0;
				totCLBbet3to6 = 0;

				totOPBGrt6 = 0;
				totMNRInsGrt6 = 0;
				totMNRReplGrt6 = 0;
				totCLBGrt6 = 0;
				
				bean = new MNRRepBean();
				cid=mnrRepBean.getcId();
				
			
				bean.setId(mnrRepBean.getcId());
				bean.setLable(mnrRepBean.getcName());
				bean.setLevel("2");
			   
				if(mnrRepBean.getIdenFlag().equals("1")){
					
					totOPBbet1to3=totOPBbet1to3+mnrRepBean.getOpbBet1to3();
					totMNRInsbet1to3=totMNRInsbet1to3+mnrRepBean.getMnrInsbet1to3();
					totMNRReplbet1to3=totMNRReplbet1to3+mnrRepBean.getMnrReplbet1to3();
					totCLBbet1to3=totCLBbet1to3+mnrRepBean.getClbBet1to3();
					
					bean.setTotOPBbet1to3(totOPBbet1to3);
					bean.setTotMNRInsbet1to3(totMNRInsbet1to3);
					bean.setTotMNRReplbet1to3(totMNRReplbet1to3);
					bean.setTotCLBbet1to3(totCLBbet1to3);
					
					dispMnrList.add(bean);
					
					
				 }
				else if (mnrRepBean.getIdenFlag().equals("2")) {
					totOPBbet3to6+= mnrRepBean.getOpbBet3to6();
					totMNRInsbet3to6+=mnrRepBean.getMnrInsbet3to6();
					totMNRReplbet3to6+=mnrRepBean.getMnrReplbet3to6();
					totCLBbet3to6+=mnrRepBean.getClbBet3to6();
					
					bean.setTotOPBbet3to6(totOPBbet3to6);
					bean.setTotMNRInsbet3to6(totMNRInsbet3to6);
					bean.setTotMNRReplbet3to6(totMNRReplbet3to6);
					bean.setTotCLBbet3to6(totCLBbet3to6);
					
					dispMnrList.add(bean);
					
				}
				else if (mnrRepBean.getIdenFlag().equals("3")) {
					totOPBGrt6+=mnrRepBean.getOpbGrt6();
					totMNRInsGrt6+=mnrRepBean.getMnrInsGrt6();
					totMNRReplGrt6+=mnrRepBean.getMnrReplGrt6();
					totCLBGrt6+=mnrRepBean.getClbGrt6();
					
					bean.setTotOPBGrt6(totOPBGrt6);
					bean.setTotMNRInsGrt6(totMNRInsGrt6);
					bean.setTotMNRReplGrt6(totMNRReplGrt6);
					bean.setTotCLBGrt6(totCLBGrt6);
					
					dispMnrList.add(bean);
				}	
			    
			 }
				else if(cid.equals(mnrRepBean.getcId())) { 
					
					
					 if(mnrRepBean.getIdenFlag().equals("1")){
							
							totOPBbet1to3=totOPBbet1to3+mnrRepBean.getOpbBet1to3();
							totMNRInsbet1to3=totMNRInsbet1to3+mnrRepBean.getMnrInsbet1to3();
							totMNRReplbet1to3=totMNRReplbet1to3+mnrRepBean.getMnrReplbet1to3();
							totCLBbet1to3=totCLBbet1to3+mnrRepBean.getClbBet1to3();
							
							bean.setTotOPBbet1to3(totOPBbet1to3);
							bean.setTotMNRInsbet1to3(totMNRInsbet1to3);
							bean.setTotMNRReplbet1to3(totMNRReplbet1to3);
							bean.setTotCLBbet1to3(totCLBbet1to3);
							
							
							
							
						 }
						else if (mnrRepBean.getIdenFlag().equals("2")) {
							totOPBbet3to6+= mnrRepBean.getOpbBet3to6();
							totMNRInsbet3to6+=mnrRepBean.getMnrInsbet3to6();
							totMNRReplbet3to6+=mnrRepBean.getMnrReplbet3to6();
							totCLBbet3to6+=mnrRepBean.getClbBet3to6();
							
							bean.setTotOPBbet3to6(totOPBbet3to6);
							bean.setTotMNRInsbet3to6(totMNRInsbet3to6);
							bean.setTotMNRReplbet3to6(totMNRReplbet3to6);
							bean.setTotCLBbet3to6(totCLBbet3to6);
							
							
							
						}
						else if (mnrRepBean.getIdenFlag().equals("3")) {
							totOPBGrt6+=mnrRepBean.getOpbGrt6();
							totMNRInsGrt6+=mnrRepBean.getMnrInsGrt6();
							totMNRReplGrt6+=mnrRepBean.getMnrReplGrt6();
							totCLBGrt6+=mnrRepBean.getClbGrt6();
							
							bean.setTotOPBGrt6(totOPBGrt6);
							bean.setTotMNRInsGrt6(totMNRInsGrt6);
							bean.setTotMNRReplGrt6(totMNRReplGrt6);
							bean.setTotCLBGrt6(totCLBGrt6);
							
							
						}	
		   
					 
					 
					 
				}
		}
		
		return dispMnrList;
  }
   public List<MNRRepBean> zoneWise(String id,String lvl){

	    
	   
	    
	    int totOPBbet1to3=0;
		int totMNRInsbet1to3 =0;
		int totMNRReplbet1to3 = 0;
		int totCLBbet1to3 =0;
		
		int totOPBbet3to6=0;
		int totMNRInsbet3to6=0;
		int totMNRReplbet3to6=0;
		int totCLBbet3to6=0;
		
		int totOPBGrt6=0;
		int totMNRInsGrt6=0;
		int totMNRReplGrt6=0;
		int totCLBGrt6=0;
	   
		List<MNRRepBean> tempList = new ArrayList<MNRRepBean>();
		dispMnrList = new ArrayList<MNRRepBean>();
		if (id=="first") {
			tempList=mnrlList;
		}
		else {
			for (MNRRepBean mnrRepBean : mnrlList) {
				
				if(mnrRepBean.getzId().equals(id)){
				  tempList.add(mnrRepBean); 
			}
				
		}
		}
		String zid = "";
		for (MNRRepBean mnrRepBean : tempList) {
			if(zid.equals("")){
				bean = new MNRRepBean();
				zid=mnrRepBean.getzId();
				bean.setId(mnrRepBean.getzId());
				bean.setLable(mnrRepBean.getzName());
				bean.setLevel("1");
			   
				if(mnrRepBean.getIdenFlag().equals("1")){
					
					totOPBbet1to3=totOPBbet1to3+mnrRepBean.getOpbBet1to3();
					totMNRInsbet1to3=totMNRInsbet1to3+mnrRepBean.getMnrInsbet1to3();
					totMNRReplbet1to3=totMNRReplbet1to3+mnrRepBean.getMnrReplbet1to3();
					totCLBbet1to3=totCLBbet1to3+mnrRepBean.getClbBet1to3();
					
					bean.setTotOPBbet1to3(totOPBbet1to3);
					bean.setTotMNRInsbet1to3(totMNRInsbet1to3);
					bean.setTotMNRReplbet1to3(totMNRReplbet1to3);
					bean.setTotCLBbet1to3(totCLBbet1to3);
					
					dispMnrList.add(bean);
					
					
				 }
				else if (mnrRepBean.getIdenFlag().equals("2")) {
					totOPBbet3to6+= mnrRepBean.getOpbBet3to6();
					totMNRInsbet3to6+=mnrRepBean.getMnrInsbet3to6();
					totMNRReplbet3to6+=mnrRepBean.getMnrReplbet3to6();
					totCLBbet3to6+=mnrRepBean.getClbBet3to6();
					
					
					bean.setTotOPBbet3to6(totOPBbet3to6);
					bean.setTotMNRInsbet3to6(totMNRInsbet3to6);
					bean.setTotMNRReplbet3to6(totMNRReplbet3to6);
					bean.setTotCLBbet3to6(totCLBbet3to6);
					
					dispMnrList.add(bean);
					
				}
				else if (mnrRepBean.getIdenFlag().equals("3")) {
					totOPBGrt6+=mnrRepBean.getOpbGrt6();
					totMNRInsGrt6+=mnrRepBean.getMnrInsGrt6();
					totMNRReplGrt6+=mnrRepBean.getMnrReplGrt6();
					totCLBGrt6+=mnrRepBean.getClbGrt6();
					
					bean.setTotOPBGrt6(totOPBGrt6);
					bean.setTotMNRInsGrt6(totMNRInsGrt6);
					bean.setTotMNRReplGrt6(totMNRReplGrt6);
					bean.setTotCLBGrt6(totCLBGrt6);
					
					dispMnrList.add(bean);
				}	
			    
			 }
				
			else if (!zid.equals(mnrRepBean.getzId())) {
				
				totOPBbet1to3 = 0;
				totMNRInsbet1to3 = 0;
				totMNRReplbet1to3 = 0;
				totCLBbet1to3 = 0;

				totOPBbet3to6 = 0;
				totMNRInsbet3to6 = 0;
				totMNRReplbet3to6 = 0;
				totCLBbet3to6 = 0;

				totOPBGrt6 = 0;
				totMNRInsGrt6 = 0;
				totMNRReplGrt6 = 0;
				totCLBGrt6 = 0;
				
				bean = new MNRRepBean();
				zid=mnrRepBean.getzId();
				
			
				bean.setId(mnrRepBean.getzId());
				bean.setLable(mnrRepBean.getzName());
				bean.setLevel("1");
			   
				if(mnrRepBean.getIdenFlag().equals("1")){
					
					totOPBbet1to3=totOPBbet1to3+mnrRepBean.getOpbBet1to3();
					totMNRInsbet1to3=totMNRInsbet1to3+mnrRepBean.getMnrInsbet1to3();
					totMNRReplbet1to3=totMNRReplbet1to3+mnrRepBean.getMnrReplbet1to3();
					totCLBbet1to3=totCLBbet1to3+mnrRepBean.getClbBet1to3();
					
					bean.setTotOPBbet1to3(totOPBbet1to3);
					bean.setTotMNRInsbet1to3(totMNRInsbet1to3);
					bean.setTotMNRReplbet1to3(totMNRReplbet1to3);
					bean.setTotCLBbet1to3(totCLBbet1to3);
					
					dispMnrList.add(bean);
					
					
				 }
				else if (mnrRepBean.getIdenFlag().equals("2")) {
					totOPBbet3to6+= mnrRepBean.getOpbBet3to6();
					totMNRInsbet3to6+=mnrRepBean.getMnrInsbet3to6();
					totMNRReplbet3to6+=mnrRepBean.getMnrReplbet3to6();
					totCLBbet3to6+=mnrRepBean.getClbBet3to6();
					
					bean.setTotOPBbet3to6(totOPBbet3to6);
					bean.setTotMNRInsbet3to6(totMNRInsbet3to6);
					bean.setTotMNRReplbet3to6(totMNRReplbet3to6);
					bean.setTotCLBbet3to6(totCLBbet3to6);
					
					dispMnrList.add(bean);
					
				}
				else if (mnrRepBean.getIdenFlag().equals("3")) {
					totOPBGrt6+=mnrRepBean.getOpbGrt6();
					totMNRInsGrt6+=mnrRepBean.getMnrInsGrt6();
					totMNRReplGrt6+=mnrRepBean.getMnrReplGrt6();
					totCLBGrt6+=mnrRepBean.getClbGrt6();
					
					bean.setTotOPBGrt6(totOPBGrt6);
					bean.setTotMNRInsGrt6(totMNRInsGrt6);
					bean.setTotMNRReplGrt6(totMNRReplGrt6);
					bean.setTotCLBGrt6(totCLBGrt6);
					
					dispMnrList.add(bean);
				}	
			    
			 }
				else if(zid.equals(mnrRepBean.getzId())) { 
					
					
					 if(mnrRepBean.getIdenFlag().equals("1")){
							
							totOPBbet1to3=totOPBbet1to3+mnrRepBean.getOpbBet1to3();
							totMNRInsbet1to3=totMNRInsbet1to3+mnrRepBean.getMnrInsbet1to3();
							totMNRReplbet1to3=totMNRReplbet1to3+mnrRepBean.getMnrReplbet1to3();
							totCLBbet1to3=totCLBbet1to3+mnrRepBean.getClbBet1to3();
							
							bean.setTotOPBbet1to3(totOPBbet1to3);
							bean.setTotMNRInsbet1to3(totMNRInsbet1to3);
							bean.setTotMNRReplbet1to3(totMNRReplbet1to3);
							bean.setTotCLBbet1to3(totCLBbet1to3);
							
							
							
							
						 }
						else if (mnrRepBean.getIdenFlag().equals("2")) {
							totOPBbet3to6+= mnrRepBean.getOpbBet3to6();
							totMNRInsbet3to6+=mnrRepBean.getMnrInsbet3to6();
							totMNRReplbet3to6+=mnrRepBean.getMnrReplbet3to6();
							totCLBbet3to6+=mnrRepBean.getClbBet3to6();
							
							bean.setTotOPBbet3to6(totOPBbet3to6);
							bean.setTotMNRInsbet3to6(totMNRInsbet3to6);
							bean.setTotMNRReplbet3to6(totMNRReplbet3to6);
							bean.setTotCLBbet3to6(totCLBbet3to6);
							
							
							
						}
						else if (mnrRepBean.getIdenFlag().equals("3")) {
							totOPBGrt6+=mnrRepBean.getOpbGrt6();
							totMNRInsGrt6+=mnrRepBean.getMnrInsGrt6();
							totMNRReplGrt6+=mnrRepBean.getMnrReplGrt6();
							totCLBGrt6+=mnrRepBean.getClbGrt6();
							
							bean.setTotOPBGrt6(totOPBGrt6);
							bean.setTotMNRInsGrt6(totMNRInsGrt6);
							bean.setTotMNRReplGrt6(totMNRReplGrt6);
							bean.setTotCLBGrt6(totCLBGrt6);
							
							
						}	
		   
					 
					 
					 
				}
		}
		/*dispMnrList.add(bean);*/
		return dispMnrList;
 }
   
}
   