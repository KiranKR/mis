package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Util.AppUtil;
import com.Util.MysqlConnectionProvider;
import com.bean.DisconnectionSummaryBean;



public class DisconnectionSummaryDao {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	DisconnectionSummaryBean bean = null;
	List<DisconnectionSummaryBean> discSummaryList = null;
	List<DisconnectionSummaryBean> dispDiscSummaryList = new ArrayList<DisconnectionSummaryBean>();
	
	public List<DisconnectionSummaryBean> viewData(String role, String roleBelogs,
			String fromYearMnth,String toYearMnth ){
		
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

		discSummaryList = new ArrayList<DisconnectionSummaryBean>();
		DisconnectionSummaryBean summaryBean = null;
		
		try {
			String query ="SELECT ds.DISC_ID, "
                           +"th.TH_ID,ds.DISC_TH_ID, "
                           +"z.ZONE_ID, "
                           +"z.ZONE_NAME, "
                           +"c.CRCL_ID, "
                           +"c.CRCL_NAME, "
                           +"d.DIV_ID, "
                           +"d.DIV_NAME, "
                           +"sd.SUBDIV_ID, "
                           +"sd.SUBDIV_NAME, "
                           +"s.SECTN_ID, "
                           +"s.SECTN_NAME, "
                           +"th.TH_YEAR, "
                           +"th.TH_MONTH, "
					       +"ds.DISC_CNTOB, "
					       +"ds.DISC_AMNTOB, "
					       +"ds.DISC_VISITED, "
					       +"ds.DISC_DISCONNECT, "
					       +"ds.DISC_AMNT_INVOLVE, "
					       +"ds.DISC_RECONNECT, "
					       +"ds.DISC_AMNT_REALISE, "
					       +"ds.DISC_CNTCB, "
					       +"ds.DISC_AMOUNTCB, "
					       +"ds.DISC_REMARKS "
					       +"FROM section s "
					       +"JOIN sub_division sd "
					       +"ON sd.SUBDIV_ID = s.SECTN_SUBDIV_ID "
					       +"JOIN division d "
					       +"ON d.DIV_ID = sd.SUBDIV_DIV_ID "
					       +"JOIN circle c "
					       +"ON c.CRCL_ID = d.DIV_CIRCLE_ID "
					       +"JOIN zone z "
					       +"ON z.ZONE_ID = c.CRCL_ZONE_ID "
					       +"JOIN tran_head th "
					       +"ON th.TH_SECTN_ID = s.SECTN_ID "
					       +"JOIN disconnection ds "
					       +"ON ds.DISC_TH_ID = th.TH_ID "
					       +"WHERE th.TH_IDEN_FLAG = 3 and th.TH_YEAR=2012  "
					       + wherClause
					       +" AND concat(th.TH_YEAR, th.TH_MONTH) >= " + fromYearMnth + " "
					       +" AND concat(th.TH_YEAR, th.TH_MONTH) <= " + toYearMnth + " "
					       +" order by s.SECTN_ID,sd.SUBDIV_ID,d.DIV_ID,c.CRCL_ID,z.ZONE_ID ";
			
			connection = MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			int i=1;
			while(resultSet.next()){
				summaryBean = new DisconnectionSummaryBean();
				
				summaryBean.setSlno(i++);
				summaryBean.setDisId(resultSet.getString("DISC_ID"));
				summaryBean.setOpenBal(resultSet.getInt("DISC_CNTOB"));             
				summaryBean.setOpenBalAmt(resultSet.getDouble("DISC_AMNTOB"));       
				summaryBean.setVisitDurMnth(resultSet.getInt("DISC_VISITED"));        
				summaryBean.setDisconctDurMnth(resultSet.getInt("DISC_DISCONNECT"));      
				summaryBean.setAmtInv(resultSet.getDouble("DISC_AMNT_INVOLVE"));           
				summaryBean.setReconctDurMnth(resultSet.getInt("DISC_RECONNECT"));      
				summaryBean.setAmtRel(resultSet.getDouble("DISC_AMNT_REALISE"));           
				summaryBean.setCloseBal(resultSet.getInt("DISC_CNTCB"));            
				summaryBean.setClosBalAmt(resultSet.getDouble("DISC_AMOUNTCB"));      
				summaryBean.setReason(resultSet.getString("DISC_REMARKS"));        
				summaryBean.setSecId(resultSet.getString("SECTN_ID"));         
				summaryBean.setSecName(resultSet.getString("SECTN_NAME"));  
				summaryBean.setSdId(resultSet.getString("SUBDIV_ID"));
				summaryBean.setSdName(resultSet.getString("SUBDIV_NAME"));
				summaryBean.setdId(resultSet.getString("DIV_ID"));
				summaryBean.setdName(resultSet.getString("DIV_NAME"));
				summaryBean.setcId(resultSet.getString("CRCL_ID"));
				summaryBean.setcName(resultSet.getString("CRCL_NAME"));
				summaryBean.setzId(resultSet.getString("ZONE_ID"));
				summaryBean.setzName(resultSet.getString("ZONE_NAME"));
				summaryBean.setThId(resultSet.getString("TH_ID"));          
				summaryBean.setMnthId(AppUtil.getMonth(resultSet.getString("TH_MONTH")));
				
				discSummaryList.add(summaryBean);
				
				
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
		finally{
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement, connection);
		}
		if(role.equals("1")){
			dispDiscSummaryList = zoneWise("first","0");
		}else if(role.equals("2")){
			dispDiscSummaryList = circleWise("first","0");
		}else if(role.equals("3")){
			dispDiscSummaryList = divWise("first","0");
		}else if(role.equals("4")){
			dispDiscSummaryList = subDivWise("first","0");
		}else if(role.equals("5")){
			dispDiscSummaryList = secWise("first","0");
		}
		
		return dispDiscSummaryList;
		
	}
	public List<DisconnectionSummaryBean> secWise (String id,String lvl){
		
		int visiDuringMonth=0;
		int discDuringMonth=0;
		double ttlOB = 0.0;
		double ttlAmtInv = 0.0;
		double ttlAmtRel = 0.0;
		double ttlCB = 0.0;
		
		List<DisconnectionSummaryBean> tempList = new ArrayList<DisconnectionSummaryBean>();
		dispDiscSummaryList = new ArrayList<DisconnectionSummaryBean>();
		
		if (id=="first") {
			tempList=discSummaryList;
		}
        else if (lvl.equals("4")) {

			for (DisconnectionSummaryBean disconnectionSummaryBean : discSummaryList) {

				if (disconnectionSummaryBean.getSdId().equals(id)) {
					tempList.add(disconnectionSummaryBean);
				}

			}

		}
		else if (lvl.equals("5")) {
			
			for (DisconnectionSummaryBean disconnectionSummaryBean : discSummaryList) {

				if (disconnectionSummaryBean.getSecId().equals(id)) {
					tempList.add(disconnectionSummaryBean);
				}

			}
			
		}
		String sid = "";
		for (DisconnectionSummaryBean summaryBean : tempList) {
			if(sid.equals("")){
				bean = new DisconnectionSummaryBean();
				sid=summaryBean.getSecId();
				bean.setId(summaryBean.getSecId());
				bean.setLable(summaryBean.getSecName());
				bean.setLevel("5");
				
				visiDuringMonth = visiDuringMonth + summaryBean.getVisitDurMnth();
				discDuringMonth = discDuringMonth + summaryBean.getDisconctDurMnth();
				ttlOB = ttlOB + summaryBean.getOpenBal();
				ttlAmtInv = ttlAmtInv + summaryBean.getAmtInv();
				ttlAmtRel = ttlAmtRel + summaryBean.getAmtRel();
				ttlCB = ttlCB + summaryBean.getCloseBal();

				bean.setVisitDurMnth(visiDuringMonth);
				bean.setDisconctDurMnth(discDuringMonth);
				bean.setTtlOB(ttlOB);
				bean.setTtlAmtInv(ttlAmtInv);
				bean.setTtlAmtRel(ttlAmtRel);
				bean.setTtlCB(ttlCB);
				
				dispDiscSummaryList.add(bean);
			}
			else if (!sid.equals(summaryBean.getSecId())) {
				
				visiDuringMonth=0;
				discDuringMonth=0;
				ttlOB = 0.0;
				ttlAmtInv = 0.0;
				ttlAmtRel = 0.0;
				ttlCB = 0.0;
				
				bean = new DisconnectionSummaryBean();
				sid=summaryBean.getSecId();
				bean.setId(summaryBean.getSecId());
				bean.setLable(summaryBean.getSecName());
				bean.setLevel("5");
			
				visiDuringMonth = visiDuringMonth + summaryBean.getVisitDurMnth();
				discDuringMonth = discDuringMonth + summaryBean.getDisconctDurMnth();
				ttlOB = ttlOB + summaryBean.getOpenBal();
				ttlAmtInv = ttlAmtInv + summaryBean.getAmtInv();
				ttlAmtRel = ttlAmtRel + summaryBean.getAmtRel();
				ttlCB = ttlCB + summaryBean.getCloseBal();
				
				bean.setVisitDurMnth(visiDuringMonth);
				bean.setDisconctDurMnth(discDuringMonth);
				bean.setTtlOB(ttlOB);
				bean.setTtlAmtInv(ttlAmtInv);
				bean.setTtlAmtRel(ttlAmtRel);
				bean.setTtlCB(ttlCB);
				
				dispDiscSummaryList.add(bean);
			}
			else if (sid.equals(summaryBean.getSecId())) {
				
				
				visiDuringMonth = visiDuringMonth + summaryBean.getVisitDurMnth();
				discDuringMonth = discDuringMonth + summaryBean.getDisconctDurMnth();
				ttlOB = ttlOB + summaryBean.getOpenBal();
				ttlAmtInv = ttlAmtInv + summaryBean.getAmtInv();
				ttlAmtRel = ttlAmtRel + summaryBean.getAmtRel();
				ttlCB = ttlCB + summaryBean.getCloseBal();
					
				
				bean.setVisitDurMnth(visiDuringMonth);
				bean.setDisconctDurMnth(discDuringMonth);
				bean.setTtlOB(ttlOB);
				bean.setTtlAmtInv(ttlAmtInv);
				bean.setTtlAmtRel(ttlAmtRel);
				bean.setTtlCB(ttlCB);
				
				
			}
			
		}
		
		return dispDiscSummaryList;
		
	}
	public List<DisconnectionSummaryBean> subDivWise (String id,String lvl){
		
		int visiDuringMonth=0;
		int discDuringMonth=0;
		double ttlOB = 0.0;
		double ttlAmtInv = 0.0;
		double ttlAmtRel = 0.0;
		double ttlCB = 0.0;
		
		List<DisconnectionSummaryBean> tempList = new ArrayList<DisconnectionSummaryBean>();
		dispDiscSummaryList = new ArrayList<DisconnectionSummaryBean>();
		
		if (id=="first") {
			tempList=discSummaryList;
		}
		else if(lvl.equals("3")) {
			for (DisconnectionSummaryBean disconnectionSummaryBean : discSummaryList) {
				
				if(disconnectionSummaryBean.getdId().equals(id)){
				  tempList.add(disconnectionSummaryBean); 
			}
		}
	
	     
      }
		else if (lvl.equals("4")) {
       for (DisconnectionSummaryBean disconnectionSummaryBean : discSummaryList) {
				
				if(disconnectionSummaryBean.getSdId().equals(id)){
				  tempList.add(disconnectionSummaryBean); 
			}
			
		}
		}
		String sdid = "";
		for (DisconnectionSummaryBean summaryBean : tempList) {
			if(sdid.equals("")){
				bean = new DisconnectionSummaryBean();
				sdid=summaryBean.getSdId();
				bean.setId(summaryBean.getSdId());
				bean.setLable(summaryBean.getSdName());
				bean.setLevel("4");
				
				visiDuringMonth = visiDuringMonth + summaryBean.getVisitDurMnth();
				discDuringMonth = discDuringMonth + summaryBean.getDisconctDurMnth();
				ttlOB = ttlOB + summaryBean.getOpenBal();
				ttlAmtInv = ttlAmtInv + summaryBean.getAmtInv();
				ttlAmtRel = ttlAmtRel + summaryBean.getAmtRel();
				ttlCB = ttlCB + summaryBean.getCloseBal();

				bean.setVisitDurMnth(visiDuringMonth);
				bean.setDisconctDurMnth(discDuringMonth);
				bean.setTtlOB(ttlOB);
				bean.setTtlAmtInv(ttlAmtInv);
				bean.setTtlAmtRel(ttlAmtRel);
				bean.setTtlCB(ttlCB);
				
				dispDiscSummaryList.add(bean);
			}
			else if (!sdid.equals(summaryBean.getSdId())) {
				
				visiDuringMonth=0;
				discDuringMonth=0;
				ttlOB = 0.0;
				ttlAmtInv = 0.0;
				ttlAmtRel = 0.0;
				ttlCB = 0.0;
				
				bean = new DisconnectionSummaryBean();
				sdid=summaryBean.getSdId();
				bean.setId(summaryBean.getSdId());
				bean.setLable(summaryBean.getSdName());
				bean.setLevel("4");
			
				visiDuringMonth = visiDuringMonth + summaryBean.getVisitDurMnth();
				discDuringMonth = discDuringMonth + summaryBean.getDisconctDurMnth();
				ttlOB = ttlOB + summaryBean.getOpenBal();
				ttlAmtInv = ttlAmtInv + summaryBean.getAmtInv();
				ttlAmtRel = ttlAmtRel + summaryBean.getAmtRel();
				ttlCB = ttlCB + summaryBean.getCloseBal();
				
				bean.setVisitDurMnth(visiDuringMonth);
				bean.setDisconctDurMnth(discDuringMonth);
				bean.setTtlOB(ttlOB);
				bean.setTtlAmtInv(ttlAmtInv);
				bean.setTtlAmtRel(ttlAmtRel);
				bean.setTtlCB(ttlCB);
				
				dispDiscSummaryList.add(bean);
			}
			else if (sdid.equals(summaryBean.getSdId())) {
				
				
				visiDuringMonth = visiDuringMonth + summaryBean.getVisitDurMnth();
				discDuringMonth = discDuringMonth + summaryBean.getDisconctDurMnth();
				ttlOB = ttlOB + summaryBean.getOpenBal();
				ttlAmtInv = ttlAmtInv + summaryBean.getAmtInv();
				ttlAmtRel = ttlAmtRel + summaryBean.getAmtRel();
				ttlCB = ttlCB + summaryBean.getCloseBal();
					
				
				bean.setVisitDurMnth(visiDuringMonth);
				bean.setDisconctDurMnth(discDuringMonth);
				bean.setTtlOB(ttlOB);
				bean.setTtlAmtInv(ttlAmtInv);
				bean.setTtlAmtRel(ttlAmtRel);
				bean.setTtlCB(ttlCB);
				
				
			}
			
		}
		
		return dispDiscSummaryList;
		
	}
	public List<DisconnectionSummaryBean> divWise (String id,String lvl){
		
		int visiDuringMonth=0;
		int discDuringMonth=0;
		double ttlOB = 0.0;
		double ttlAmtInv = 0.0;
		double ttlAmtRel = 0.0;
		double ttlCB = 0.0;
		
		List<DisconnectionSummaryBean> tempList = new ArrayList<DisconnectionSummaryBean>();
		dispDiscSummaryList = new ArrayList<DisconnectionSummaryBean>();
		
		if (id=="first") {
			tempList=discSummaryList;
		}
		else if(lvl.equals("2")) {
			for (DisconnectionSummaryBean disconnectionSummaryBean : discSummaryList) {
				
				if(disconnectionSummaryBean.getcId().equals(id)){
				  tempList.add(disconnectionSummaryBean); 
			}
		}
	
	   
   }else if (lvl.equals("3")) {
	   for (DisconnectionSummaryBean disconnectionSummaryBean : discSummaryList) {
			
			if(disconnectionSummaryBean.getdId().equals(id)){
			  tempList.add(disconnectionSummaryBean); 
		}
	}
	
}
		String did = "";
		for (DisconnectionSummaryBean summaryBean : tempList) {
			if(did.equals("")){
				bean = new DisconnectionSummaryBean();
				did=summaryBean.getdId();
				bean.setId(summaryBean.getdId());
				bean.setLable(summaryBean.getdName());
				bean.setLevel("3");
				
				visiDuringMonth = visiDuringMonth + summaryBean.getVisitDurMnth();
				discDuringMonth = discDuringMonth + summaryBean.getDisconctDurMnth();
				ttlOB = ttlOB + summaryBean.getOpenBal();
				ttlAmtInv = ttlAmtInv + summaryBean.getAmtInv();
				ttlAmtRel = ttlAmtRel + summaryBean.getAmtRel();
				ttlCB = ttlCB + summaryBean.getCloseBal();

				bean.setVisitDurMnth(visiDuringMonth);
				bean.setDisconctDurMnth(discDuringMonth);
				bean.setTtlOB(ttlOB);
				bean.setTtlAmtInv(ttlAmtInv);
				bean.setTtlAmtRel(ttlAmtRel);
				bean.setTtlCB(ttlCB);
				
				dispDiscSummaryList.add(bean);
			}
			else if (!did.equals(summaryBean.getdId())) {
				
				visiDuringMonth=0;
				discDuringMonth=0;
				ttlOB = 0.0;
				ttlAmtInv = 0.0;
				ttlAmtRel = 0.0;
				ttlCB = 0.0;
				
				bean = new DisconnectionSummaryBean();
				did=summaryBean.getdId();
				bean.setId(summaryBean.getdId());
				bean.setLable(summaryBean.getdName());
				bean.setLevel("3");
			
				visiDuringMonth = visiDuringMonth + summaryBean.getVisitDurMnth();
				discDuringMonth = discDuringMonth + summaryBean.getDisconctDurMnth();
				ttlOB = ttlOB + summaryBean.getOpenBal();
				ttlAmtInv = ttlAmtInv + summaryBean.getAmtInv();
				ttlAmtRel = ttlAmtRel + summaryBean.getAmtRel();
				ttlCB = ttlCB + summaryBean.getCloseBal();
				
				bean.setVisitDurMnth(visiDuringMonth);
				bean.setDisconctDurMnth(discDuringMonth);
				bean.setTtlOB(ttlOB);
				bean.setTtlAmtInv(ttlAmtInv);
				bean.setTtlAmtRel(ttlAmtRel);
				bean.setTtlCB(ttlCB);
				
				dispDiscSummaryList.add(bean);
			}
			else if (did.equals(summaryBean.getdId())) {
				
				
				visiDuringMonth = visiDuringMonth + summaryBean.getVisitDurMnth();
				discDuringMonth = discDuringMonth + summaryBean.getDisconctDurMnth();
				ttlOB = ttlOB + summaryBean.getOpenBal();
				ttlAmtInv = ttlAmtInv + summaryBean.getAmtInv();
				ttlAmtRel = ttlAmtRel + summaryBean.getAmtRel();
				ttlCB = ttlCB + summaryBean.getCloseBal();
					
				
				bean.setVisitDurMnth(visiDuringMonth);
				bean.setDisconctDurMnth(discDuringMonth);
				bean.setTtlOB(ttlOB);
				bean.setTtlAmtInv(ttlAmtInv);
				bean.setTtlAmtRel(ttlAmtRel);
				bean.setTtlCB(ttlCB);
				
				
			}
			
		}
		
		return dispDiscSummaryList;
		
	}
	public List<DisconnectionSummaryBean> circleWise (String id,String lvl){
		
		int visiDuringMonth=0;
		int discDuringMonth=0;
		double ttlOB = 0.0;
		double ttlAmtInv = 0.0;
		double ttlAmtRel = 0.0;
		double ttlCB = 0.0;
		
		List<DisconnectionSummaryBean> tempList = new ArrayList<DisconnectionSummaryBean>();
		dispDiscSummaryList = new ArrayList<DisconnectionSummaryBean>();
		
		if (id=="first") {
			tempList=discSummaryList;
		}
		else if(lvl.equals("1")) {
			for (DisconnectionSummaryBean disconnectionSummaryBean : discSummaryList) {
				
				if(disconnectionSummaryBean.getzId().equals(id)){
				  tempList.add(disconnectionSummaryBean); 
			}
		}
	
	   
   }else if (lvl.equals("2")) {for (DisconnectionSummaryBean disconnectionSummaryBean : discSummaryList) {
		
		if(disconnectionSummaryBean.getcId().equals(id)){
		  tempList.add(disconnectionSummaryBean); 
	}
}
   }
		String cid = "";
		for (DisconnectionSummaryBean summaryBean : tempList) {
			if(cid.equals("")){
				bean = new DisconnectionSummaryBean();
				cid=summaryBean.getcId();
				bean.setId(summaryBean.getcId());
				bean.setLable(summaryBean.getcName());
				bean.setLevel("2");
				
				visiDuringMonth = visiDuringMonth + summaryBean.getVisitDurMnth();
				discDuringMonth = discDuringMonth + summaryBean.getDisconctDurMnth();
				ttlOB = ttlOB + summaryBean.getOpenBal();
				ttlAmtInv = ttlAmtInv + summaryBean.getAmtInv();
				ttlAmtRel = ttlAmtRel + summaryBean.getAmtRel();
				ttlCB = ttlCB + summaryBean.getCloseBal();

				bean.setVisitDurMnth(visiDuringMonth);
				bean.setDisconctDurMnth(discDuringMonth);
				bean.setTtlOB(ttlOB);
				bean.setTtlAmtInv(ttlAmtInv);
				bean.setTtlAmtRel(ttlAmtRel);
				bean.setTtlCB(ttlCB);
				
				dispDiscSummaryList.add(bean);
			}
			else if (!cid.equals(summaryBean.getcId())) {
				
				visiDuringMonth=0;
				discDuringMonth=0;
				ttlOB = 0.0;
				ttlAmtInv = 0.0;
				ttlAmtRel = 0.0;
				ttlCB = 0.0;
				
				bean = new DisconnectionSummaryBean();
				cid=summaryBean.getcId();
				bean.setId(summaryBean.getcId());
				bean.setLable(summaryBean.getcName());
				bean.setLevel("2");
			
				visiDuringMonth = visiDuringMonth + summaryBean.getVisitDurMnth();
				discDuringMonth = discDuringMonth + summaryBean.getDisconctDurMnth();
				ttlOB = ttlOB + summaryBean.getOpenBal();
				ttlAmtInv = ttlAmtInv + summaryBean.getAmtInv();
				ttlAmtRel = ttlAmtRel + summaryBean.getAmtRel();
				ttlCB = ttlCB + summaryBean.getCloseBal();
				
				bean.setVisitDurMnth(visiDuringMonth);
				bean.setDisconctDurMnth(discDuringMonth);
				bean.setTtlOB(ttlOB);
				bean.setTtlAmtInv(ttlAmtInv);
				bean.setTtlAmtRel(ttlAmtRel);
				bean.setTtlCB(ttlCB);
				
				dispDiscSummaryList.add(bean);
			}
			else if (cid.equals(summaryBean.getcId())) {
				
				
				visiDuringMonth = visiDuringMonth + summaryBean.getVisitDurMnth();
				discDuringMonth = discDuringMonth + summaryBean.getDisconctDurMnth();
				ttlOB = ttlOB + summaryBean.getOpenBal();
				ttlAmtInv = ttlAmtInv + summaryBean.getAmtInv();
				ttlAmtRel = ttlAmtRel + summaryBean.getAmtRel();
				ttlCB = ttlCB + summaryBean.getCloseBal();
					
				
				bean.setVisitDurMnth(visiDuringMonth);
				bean.setDisconctDurMnth(discDuringMonth);
				bean.setTtlOB(ttlOB);
				bean.setTtlAmtInv(ttlAmtInv);
				bean.setTtlAmtRel(ttlAmtRel);
				bean.setTtlCB(ttlCB);
				
				
			}
			
		}
		
		return dispDiscSummaryList;
		
	}
	
	public List<DisconnectionSummaryBean> zoneWise (String id,String lvl){
		
		int visiDuringMonth=0;
		int discDuringMonth=0;
		double ttlOB = 0.0;
		double ttlAmtInv = 0.0;
		double ttlAmtRel = 0.0;
		double ttlCB = 0.0;
		
		List<DisconnectionSummaryBean> tempList = new ArrayList<DisconnectionSummaryBean>();
		dispDiscSummaryList = new ArrayList<DisconnectionSummaryBean>();
		
		if (id=="first") {
			tempList=discSummaryList;
		}
		else {
			for (DisconnectionSummaryBean disconnectionSummaryBean : discSummaryList) {
				
				if(disconnectionSummaryBean.getzId().equals(id)){
				  tempList.add(disconnectionSummaryBean); 
			}
				
		}
	
	   
   }
		String zid = "";
		for (DisconnectionSummaryBean summaryBean : tempList) {
			if(zid.equals("")){
				bean = new DisconnectionSummaryBean();
				zid=summaryBean.getzId();
				bean.setId(summaryBean.getzId());
				bean.setLable(summaryBean.getzName());
				bean.setLevel("1");
				
				visiDuringMonth = visiDuringMonth + summaryBean.getVisitDurMnth();
				discDuringMonth = discDuringMonth + summaryBean.getDisconctDurMnth();
				ttlOB = ttlOB + summaryBean.getOpenBal();
				ttlAmtInv = ttlAmtInv + summaryBean.getAmtInv();
				ttlAmtRel = ttlAmtRel + summaryBean.getAmtRel();
				ttlCB = ttlCB + summaryBean.getCloseBal();

				bean.setVisitDurMnth(visiDuringMonth);
				bean.setDisconctDurMnth(discDuringMonth);
				bean.setTtlOB(ttlOB);
				bean.setTtlAmtInv(ttlAmtInv);
				bean.setTtlAmtRel(ttlAmtRel);
				bean.setTtlCB(ttlCB);
				
				dispDiscSummaryList.add(bean);
			}
			else if (!zid.equals(summaryBean.getzId())) {
				
				visiDuringMonth=0;
				discDuringMonth=0;
				ttlOB = 0.0;
				ttlAmtInv = 0.0;
				ttlAmtRel = 0.0;
				ttlCB = 0.0;
				
				bean = new DisconnectionSummaryBean();
				zid=summaryBean.getzId();
				bean.setId(summaryBean.getzId());
				bean.setLable(summaryBean.getzName());
				bean.setLevel("1");
			
				visiDuringMonth = visiDuringMonth + summaryBean.getVisitDurMnth();
				discDuringMonth = discDuringMonth + summaryBean.getDisconctDurMnth();
				ttlOB = ttlOB + summaryBean.getOpenBal();
				ttlAmtInv = ttlAmtInv + summaryBean.getAmtInv();
				ttlAmtRel = ttlAmtRel + summaryBean.getAmtRel();
				ttlCB = ttlCB + summaryBean.getCloseBal();
				
				bean.setVisitDurMnth(visiDuringMonth);
				bean.setDisconctDurMnth(discDuringMonth);
				bean.setTtlOB(ttlOB);
				bean.setTtlAmtInv(ttlAmtInv);
				bean.setTtlAmtRel(ttlAmtRel);
				bean.setTtlCB(ttlCB);
				
				dispDiscSummaryList.add(bean);
			}
			else if (zid.equals(summaryBean.getzId())) {
				
				
				visiDuringMonth = visiDuringMonth + summaryBean.getVisitDurMnth();
				discDuringMonth = discDuringMonth + summaryBean.getDisconctDurMnth();
				ttlOB = ttlOB + summaryBean.getOpenBal();
				ttlAmtInv = ttlAmtInv + summaryBean.getAmtInv();
				ttlAmtRel = ttlAmtRel + summaryBean.getAmtRel();
				ttlCB = ttlCB + summaryBean.getCloseBal();
					
				
				bean.setVisitDurMnth(visiDuringMonth);
				bean.setDisconctDurMnth(discDuringMonth);
				bean.setTtlOB(ttlOB);
				bean.setTtlAmtInv(ttlAmtInv);
				bean.setTtlAmtRel(ttlAmtRel);
				bean.setTtlCB(ttlCB);
				
				
			}
			
		}
		
		return dispDiscSummaryList;
		
	}
	
	
	
	

}
