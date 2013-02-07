package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.Util.AppUtil;
import com.Util.MysqlConnectionProvider;
import com.bean.TarifDCABean;
import com.bean.TarifDCAViewBean;
import com.bean.Tariff;

public class TariffDCADao {
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	
	
	public List getSesnDropDown(String role, String roleBelogs) {
		
		
		// TODO Auto-generated method stub
		List ssnList = new ArrayList();
	 	Connection con = null;
	 	PreparedStatement pst = null;
	 	ResultSet rs = null;
	 	
	 	
	 	
	 
		
		 String wherClause = "";
		 String query="";
		 
		
		
			if(role.equals("1")){
				wherClause = " where  z.ZONE_ID = "+ roleBelogs;
				
				 query= "select * from zone z join circle c join division d join sub_division " +
	 		       "join section s "+wherClause+" group by s.SECTN_NAME ";
			}else if(role.equals("2")){
				wherClause = "  c.CRCL_ID = "+ roleBelogs;
				 query="select * from  circle c join division d join sub_division join section s  "+
				 wherClause+" group by s.SECTN_NAME";
			}else if(role.equals("3")){
				wherClause = "  d.DIV_ID = "+ roleBelogs;
				query="select * from  division d join sub_division join section s "+ wherClause +"group by s.SECTN_NAME";
				
			}else if(role.equals("4")){
				wherClause = "  sd.SUBDIV_ID = "+ roleBelogs;
				query="select * from   sub_division sd join section s "+ wherClause +" group by s.SECTN_NAME";
			}else if(role.equals("5")){
				wherClause = " s.SECTN_ID = "+ roleBelogs;
				query="select * from   section s "+ wherClause +" group by s.SECTN_NAME";
			}
		
		
		
			

			try {
				con = MysqlConnectionProvider.getNewConnection();
				pst = con.prepareStatement(query);
				rs = pst.executeQuery();
				SelectItem noneItem = new SelectItem("0", "Select");

				
					ssnList.add(noneItem);
				
				while (rs.next()) {
					noneItem = new SelectItem(rs.getString("SECTN_ID"),
							rs.getString("SECTN_NAME"));
					ssnList.add(noneItem);
				}
			} catch (SQLException sqle) {
				try {
					throw sqle;
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				try {
					throw e;
				} catch (ClassNotFoundException e1) {
					
					e1.printStackTrace();
				}
			} finally {
				MysqlConnectionProvider.releaseConnection(rs, null, pst, con);
			}
			return ssnList;
	}
	

	
public List<Tariff> getTariff(TarifDCABean dcaBean) {
		
		
		
		List<Tariff> tarifList = new ArrayList<Tariff>();
	 	Connection con = null;
	 	PreparedStatement pst = null;
	 	ResultSet rs = null;
	 	
	 
		
		 String wherClause = "";
		 String query1="select * from tariff t ";
		 String query="select * from tariff t  left   join tariff_dca tf on tf.TDCA_TRF_ID = t.TRF_ID ";
		
			
		

		try{
	 	con = MysqlConnectionProvider.getNewConnection();
 		pst = con.prepareStatement(query1);
 		rs = pst.executeQuery();
 		String tarif="";
 		while(rs.next())
 		{ Tariff tariff=new Tariff();
 		tariff.setTariffId(rs.getInt("TRF_ID"));
 		tariff.setTarifName(rs.getString("TRF_NAME"));
 		tariff.setTarifRWStatus(rs.getInt("TRF_ROW_STATUS"));
 	
 		
 			tarifList.add(tariff);
 		}
		}catch (Exception e) {
			
			e.printStackTrace();
		}
	
	
 		return tarifList;
	}


public static List getDropDownList(String tableName, String orderById,String dbFldId, String dbFldValue, String whrClause,int skpDefaultVal) throws SQLException, ClassNotFoundException {
		
		String qry = "SELECT * FROM  " + tableName + " "
				+ whrClause + " ORDER BY " + orderById + "";
		List lsDDList = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = MysqlConnectionProvider.getNewConnection();
			pst = con.prepareStatement(qry);
			rs = pst.executeQuery();
			SelectItem noneItem = new SelectItem("0", "Select");

			if (skpDefaultVal != 1) {
				lsDDList.add(noneItem);
			}
			while (rs.next()) {
				noneItem = new SelectItem(rs.getString(dbFldId),
						rs.getString(dbFldValue));
				lsDDList.add(noneItem);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			MysqlConnectionProvider.releaseConnection(rs, null, pst, con);
		}
		return lsDDList;
	}



public List<TarifDCAViewBean>viewTarif(TarifDCAViewBean viewBean)
{
	
	Connection con = null;
	PreparedStatement pst = null;
	List<TarifDCAViewBean> arlFinalTarifDCAViewBean=new ArrayList<TarifDCAViewBean>();
	
	String query3="select * from tariff t  left   join tariff_dca tf on tf.TDCA_TRF_ID = t.TRF_ID left join tran_head ON tf.TDCA_TH_ID = tran_head.TH_ID   where tran_head.TH_YEAR='"+viewBean.getYearId()+"' and  tran_head.TH_SECTN_ID='"+viewBean.getSecId()+"'";
	try{
	 	con = MysqlConnectionProvider.getNewConnection();
		
 		pst = con.prepareStatement(query3);
 		rs = pst.executeQuery();
 		
 		while(rs.next())
 		{ String month=intMnt2Strng(rs.getInt("TH_MONTH"));
 			TarifDCAViewBean tarifDCAViewBean=new TarifDCAViewBean();
 			tarifDCAViewBean.setMonthId(month);
 			tarifDCAViewBean.setTarifName(rs.getString("TRF_NAME"));
 			tarifDCAViewBean.setArr(rs.getDouble("TDCA_ARRERAS"));
 			tarifDCAViewBean.setConsumption(rs.getDouble("TDCA_CONSUMPTION"));
 			tarifDCAViewBean.setDemand(rs.getDouble("TDCA_DEMAND"));
 			arlFinalTarifDCAViewBean.add(tarifDCAViewBean);
 			
 		}
 		
 		
 		
	}catch (Exception e) {
		e.printStackTrace();
	}
	return arlFinalTarifDCAViewBean;
	
}


String intMnt2Strng(int mnth)
{
	String month="";
	if(mnth==01){
		month="Jan";
		}else if(mnth==02){
			month="Feb";
		}else if(mnth==03){
			month="Mar";
		}else if(mnth==04){
			month="Apr";
		}else if(mnth==05){
		month="May";
		}else if(mnth==06){
			month="Jun";
		}else if(mnth==07){
			month="Jul";
		}else if(mnth==8){
			month="Aug";
		}else if(mnth==9){
			month="Sep";
		}else if(mnth==10){
			month="Oct";
		}else if(mnth==11){
			month="Nov";
		}else if(mnth==12){
			month="Dec";
		}
	return month;
	
}

  public List<Tariff> getTariffDetails(int secId,String monthId,String yearId){
	  
	  
	  Tariff tariff = new Tariff();
	  List<Tariff> lstTariffs = new ArrayList<Tariff>();
	  double ttlDemand=0.0;
	  double ttlConsumption=0.0;
	  double ttlArr =0.0;
	  
	  Connection con =null;
	  PreparedStatement pst=null;
	  ResultSet rs=null;
	  String qry ="SELECT t.TRF_ID, "
		  		+ "t.TRF_NAME, "
		  		+ "td.TDCA_DEMAND, "
		  		+ "td.TDCA_CONSUMPTION, "
		  		+ "td.TDCA_ARRERAS, "
		  		+ "th.TH_ID, "
		  		+ "th.TH_SECTN_ID, "
		  		+ "th.TH_MONTH, "
		  		+ "th.TH_YEAR "
		  		+ "FROM tariff_dca td "
		  		+ "INNER JOIN tran_head th "
		  		+ "ON td.TDCA_TH_ID = th.TH_ID "
		  		+ "LEFT OUTER JOIN tariff t "
		  		+ "ON td.TDCA_TRF_ID = t.TRF_ID "
		  		+ "JOIN section s "
		  		+ "ON th.TH_SECTN_ID = s.SECTN_ID "
		  		+ "WHERE  th.TH_MONTH = "+monthId+" "
		  		+ "AND th.TH_YEAR = "+yearId+" "
		  		+ "AND s.SECTN_ID = "+secId+" "
		  		+ "AND th.TH_IDEN_FLAG = '5' "
		  		+ "UNION "
		  		+ "SELECT t1.TRF_ID, "
		  		+ "t1.TRF_NAME, "
		  		+ "0 AS TDCA_DEMAND, "
		  		+ "0 AS TDCA_CONSUMPTION, "
		  		+ "0 As TDCA_ARRERAS, "
		  		+ "0 AS TH_ID, "
		  		+ ""+secId+" AS TH_SECTN_ID, "
		  		+ ""+monthId+"  AS TH_MONTH, "
		  		+ ""+yearId+" AS TH_YEAR "
		  		+ "FROM tariff t1 "
		  		+ "WHERE t1.TRF_ID NOT IN "
		  		+ "(SELECT td1.TDCA_TRF_ID "
		  		+ "FROM  tariff_dca td1 "
                + "INNER JOIN "
                + "tran_head th1 "
                + "ON td1.TDCA_TH_ID = th1.TH_ID "
                + "WHERE  th1.TH_MONTH = "+monthId+" "
                + "AND th1.TH_YEAR = "+yearId+" "
                + "AND th1.TH_SECTN_ID = "+secId+" "
                + "AND th1.TH_IDEN_FLAG = '5') "
                + "ORDER BY 1 ";
	  try {
		 con = MysqlConnectionProvider.getNewConnection();
		 pst = con.prepareStatement(qry);
		 rs =pst.executeQuery();
		 while(rs.next()){
			 
			
			 tariff = new Tariff();
			 tariff.setTariffId(rs.getInt("TRF_ID"));
			 tariff.setTarifName(rs.getString("TRF_NAME"));
			 tariff.setThId(rs.getInt("TH_ID"));
			 tariff.setDemand(rs.getDouble("TDCA_DEMAND"));
			 tariff.setConsumption(rs.getDouble("TDCA_CONSUMPTION"));
			 tariff.setArr(rs.getDouble("TDCA_ARRERAS"));
			 ttlDemand +=rs.getDouble("TDCA_DEMAND");
			 ttlConsumption +=rs.getDouble("TDCA_CONSUMPTION");
			 ttlArr +=rs.getDouble("TDCA_ARRERAS");
			 tariff.setTtlDemand(ttlDemand);
			 tariff.setTtlConsumption(ttlConsumption);
			 tariff.setTtlArr(ttlArr);
			 lstTariffs.add(tariff);
			 
		 }
		 
		
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}finally{
		MysqlConnectionProvider.releaseConnection(rs, null, pst, con);
	}
	  
	  
	  return lstTariffs;
  }


public void saveDetails(TarifDCABean dcaBean) {
	
	
	   
		Connection con = null;
		
		PreparedStatement pst1 = null;
		PreparedStatement pst2 =null;
		PreparedStatement pst3 =null;
		PreparedStatement pst4 =null;
		PreparedStatement pst5 =null;
		
		ResultSet rs = null;
		int thId = dcaBean.getThId();
		
	
		
		
		
		try {
		    con = MysqlConnectionProvider.getNewConnection(); 
		    con.setAutoCommit(false);
			con.setTransactionIsolation(con.TRANSACTION_SERIALIZABLE);

			int idenFlag = 5;
			dcaBean.setIdenFlag(idenFlag);
			if(thId==0){
			String query1 = "INSERT INTO tran_head(TH_DATE,TH_SECTN_ID,TH_MONTH,TH_YEAR,TH_ROW_STATUS,TH_IDEN_FLAG) VALUES (NOW(),'"+dcaBean.getSecId()+"','"+AppUtil.getMnthId(dcaBean.getMonthId())+"','"+dcaBean.getYearId()+"',0,'"+dcaBean.getIdenFlag()+"')";

			pst1 = con.prepareStatement(query1);
			pst1.execute();
			
			String query2 = "SELECT ifnull(max(th.TH_ID), 1) FROM tran_head th";
			pst2 = con.prepareStatement(query2);
			rs = pst2.executeQuery();

			while (rs.next()) {
				dcaBean.setThId(rs.getInt(1));
			}
			}else if(thId!=0){
				String query3 = "update tran_head SET TH_DATE = NOW(),TH_SECTN_ID = '"+dcaBean.getSecId()+"',TH_MONTH = '"+AppUtil.getMnthId(dcaBean.getMonthId())+"',TH_YEAR = '"+dcaBean.getYearId()+"',TH_ROW_STATUS = 0,TH_IDEN_FLAG = 5 WHERE TH_ID = '"+dcaBean.getThId()+"'";
			    pst3 = con.prepareStatement(query3);
			    pst3.execute();
			    String query4="delete from tariff_dca WHERE TDCA_TH_ID= '"+thId+"' ";
				pst4 = con.prepareStatement(query4);
				pst4.execute();
			} 
			

			List<Tariff> lstTariffs =null;
			lstTariffs = dcaBean.getArlTarif();
			
			for (Tariff bean : lstTariffs) {
				if(bean.getDemand()!=0.00||bean.getConsumption()!=0.00||bean.getArr()!=0.00){
				  String query5="INSERT INTO tariff_dca(TDCA_TH_ID,TDCA_TRF_ID,TDCA_DEMAND,TDCA_CONSUMPTION,TDCA_ARRERAS,TDCA_ROW_STATUS) VALUES ('"+dcaBean.getThId()+"','"+bean.getTariffId()+"','"+bean.getDemand()+"','"+bean.getConsumption()+"','"+bean.getArr()+"',0)";
				  pst5 =con.prepareStatement(query5);
				  pst5.execute();
				}
			}
						
			

			con.commit();
			con.setAutoCommit(true);
		
		}	catch (Exception e) {
			e.printStackTrace();
		}finally{
			MysqlConnectionProvider.releaseConnection(rs, null, pst1, con);
			MysqlConnectionProvider.releaseConnection(rs, null, pst2, con);
			MysqlConnectionProvider.releaseConnection(rs, null, pst3, con);
			MysqlConnectionProvider.releaseConnection(rs, null, pst4, con);
			MysqlConnectionProvider.releaseConnection(rs, null, pst5, con);
		}
	
    
    
}




}

