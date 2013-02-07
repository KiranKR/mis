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
import com.bean.DisconnectionBean;


public class DisconnectionRepDao {
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	
	double ttlOB = 0.0;
	double ttlAmtInv = 0.0;
	double ttlAmtRel = 0.0;
	double ttlCB = 0.0;
	
	public List<DisconnectionBean> getRep(String secId, String year) {
		boolean isValid = false;
		List<DisconnectionBean> beans = new ArrayList<DisconnectionBean>();
		try {
			String query ="SELECT d.DISC_ID,"+
					       " d.DISC_CNTOB,"+
					       " d.DISC_AMNTOB,"+
					       " d.DISC_VISITED,"+
					       " d.DISC_DISCONNECT,"+
					       " d.DISC_AMNT_INVOLVE,"+
					       " d.DISC_RECONNECT,"+
					       " d.DISC_AMNT_REALISE,"+
					       " d.DISC_CNTCB,"+
					       " d.DISC_AMOUNTCB,"+
					       " d.DISC_REMARKS,"+
					       " s.SECTN_ID,"+
					       " s.SECTN_NAME,"+
					       " th.TH_ID,"+
					       " th.TH_MONTH,"+
					       " th.TH_YEAR"+
					       " FROM disconnection d"+
					       " JOIN tran_head th"+
					       " ON d.DISC_TH_ID = th.TH_ID"+
					       " JOIN section s"+
					       " ON th.TH_SECTN_ID = s.SECTN_ID"+
					       " WHERE th.TH_IDEN_FLAG = 3 and th.TH_SECTN_ID = "+ secId +" and th.TH_YEAR = "+ year;
            
			connection =  MysqlConnectionProvider.getNewConnection();
			preparedStatement = connection.prepareStatement(query);
			rs = preparedStatement.executeQuery();
			while(rs.next()){
				DisconnectionBean bean = new DisconnectionBean();
				bean.setDisId(rs.getString(1));
				bean.setOpenBal(rs.getInt(2));             
				bean.setOpenBalAmt(rs.getDouble(3));       
				bean.setVisitDurMnth(rs.getInt(4));        
				bean.setDisconctDurMnth(rs.getInt(5));      
				bean.setAmtInv(rs.getDouble(6));           
				bean.setReconctDurMnth(rs.getInt(7));      
				bean.setAmtRel(rs.getDouble(8));           
				bean.setCloseBal(rs.getInt(9));            
				bean.setClosBalAmt(rs.getDouble(10));      
				bean.setReason(rs.getString(11));        
				bean.setSecId(rs.getString(12));         
				bean.setSecName(rs.getString(13));       
				bean.setThId(rs.getString(14));          
				bean.setMnthId(AppUtil.getMonth(rs.getString(15)));
				bean.setYearId(rs.getString(16));
				ttlOB = ttlOB + rs.getInt(3);
				ttlAmtInv = ttlAmtInv + rs.getDouble(6);
				ttlAmtRel = ttlAmtRel + rs.getDouble(8);
				ttlCB = ttlCB + rs.getDouble(10);
				bean.setTtlOB(ttlOB);
				bean.setTtlAmtInv(ttlAmtInv);
				bean.setTtlAmtRel(ttlAmtRel);
				bean.setTtlCB(ttlCB);
				beans.add(bean);
			}

		
		} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		return beans;
	}

public static List getDropDownList(String tableName, String orderById,String dbFldId, String dbFldValue, String whrClause,int skpDefaultVal) throws SQLException, ClassNotFoundException {
		
		String qry = "SELECT * FROM  " + tableName + 
		  " JOIN sub_division"+
          " ON section.SECTN_SUBDIV_ID = sub_division.SUBDIV_ID"+
          " JOIN division"+
          " ON sub_division.SUBDIV_DIV_ID = division.DIV_ID"+
          " JOIN circle"+
          " ON division.DIV_CIRCLE_ID = circle.CRCL_ID"+
          " JOIN zone"+
          " ON circle.CRCL_ZONE_ID = zone.ZONE_ID "
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

}
