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
import com.bean.Capacity;
import com.bean.TransformerBean;

public class TransformerDao {
	Connection con = null;
	PreparedStatement pst = null;
	PreparedStatement pst1 = null;
	PreparedStatement pst2 = null;
	PreparedStatement pst3 = null;
	PreparedStatement pst4 = null;
	PreparedStatement pst5 = null;
	PreparedStatement pst6 = null;
	
	ResultSet rs = null;

	public List<Capacity> getCapacityList()
	{
	List<Capacity> capacities = new ArrayList<Capacity>();
	Capacity capacity;

	String qry ="SELECT c.CPTY_ID AS capId, c.CPTY_NAME AS capName "
	+ "FROM capacity c "
	+ "where c.CPTY_ROW_STATUS = 0 ";

	try {
	con = MysqlConnectionProvider.getNewConnection();
	pst = con.prepareStatement(qry);
	rs = pst.executeQuery();
	while(rs.next())
	{
	capacity = new Capacity();
	capacity.setCapId(rs.getInt("capId"));
	capacity.setCapName(rs.getString("capName"));
	capacities.add(capacity);
	}



	}catch (Exception e) {
	}




	return capacities;
	}

	
	
	public List<Capacity> getCapacityValuesList(int secId, String mnthId,String yearId) {
		List<Capacity> capacities = new ArrayList<Capacity>();
		Capacity capacity = null;

		

		String query = "SELECT c.CPTY_ID, " + "c.CPTY_NAME, " + "t.TF_ADD, "
				+ "t.TF_FAILED, " + "t.TF_MAINTENANCE, " + "t.TF_REPLACED, "
				+ "t.TF_UR_FLAG, " + "th.TH_MONTH, " + "th.TH_SECTN_ID, "
				+ "th.TH_YEAR, " + "th.TH_ID, " + "t.TF_TH_ID " + "FROM transformer t "
				+ "INNER JOIN tran_head th " + "ON t.TF_TH_ID = th.TH_ID "
				+ "LEFT OUTER JOIN capacity c "
				+ "ON t.TF_CAPTY_ID = c.CPTY_ID " + "JOIN section s "
				+ "ON th.TH_SECTN_ID = s.SECTN_ID " + "WHERE th.TH_MONTH = "
				+ mnthId
				+ " AND th.TH_YEAR = "
				+ yearId
				+ " AND s.SECTN_ID = "
				+ secId
				+ " "
				+ "AND th.TH_IDEN_FLAG = 2 "
				+ "UNION "
				+ "SELECT c1.CPTY_ID, "
				+ "c1.CPTY_NAME, "
				+ "0 AS TF_ADD, "
				+ "0 AS TF_FAILED, "
				+ "0 AS TF_MAINTENANCE, "
				+ "0 AS TF_REPLACED, "
				+ "1 AS TF_UR_FLAG, "
				+ ""
				+ mnthId
				+ ", "
				+ ""
				+ secId
				+ ", "
				+ ""
				+ yearId
				+ ", "
				+ ""
				+ "0 AS TH_ID, "
				+ "0 AS TF_TH_ID "
				+ "FROM capacity c1 "
				+ "WHERE c1.CPTY_ID NOT IN "
				+ "(SELECT t1.TF_CAPTY_ID "
				+ "FROM transformer t1 "
				+ "INNER JOIN tran_head th1 "
				+ "ON t1.TF_TH_ID = th1.TH_ID "
				+ "WHERE     th1.TH_MONTH = "
				+ mnthId
				+ " "
				+ "AND th1.TH_YEAR = "
				+ yearId
				+ " "
				+ "AND th1.TH_SECTN_ID = '" + secId + "' "
				+ "AND th1.TH_IDEN_FLAG = 2)" + "ORDER BY 1";


		try {
			con = MysqlConnectionProvider.getNewConnection();
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
		    String capId ="";
			while (rs.next()) {
				if(capId.equals("")){
					capId = rs.getString("CPTY_NAME");
					capacity = new Capacity();
				}else if(!capId.equals(rs.getString("CPTY_NAME"))){
					capacities.add(capacity);
					capId = rs.getString("CPTY_NAME");
					capacity = new Capacity();
				}
				capacity.setCapId(rs.getInt("CPTY_ID"));
				capacity.setCapName(rs.getString("CPTY_NAME"));
				capacity.setIdenFlag(rs.getInt("TF_UR_FLAG"));
				capacity.setThId(rs.getInt("TH_ID"));
				capacity.settId(rs.getInt("TF_TH_ID"));
				if(capacity.getIdenFlag()==1){
					capacity.setAdditionCnt(rs.getInt("TF_ADD"));
					capacity.setFailedCnt(rs.getInt("TF_FAILED"));
					capacity.setReplacedCnt(rs.getInt("TF_REPLACED"));
					capacity.setMaintanenceCnt(rs.getInt("TF_MAINTENANCE"));
				}else if(capacity.getIdenFlag()==2){
					capacity.setRuAdditionCnt(rs.getInt("TF_ADD"));
					capacity.setRuFailedCnt(rs.getInt("TF_FAILED"));
					capacity.setRuReplacedCnt(rs.getInt("TF_REPLACED"));
					capacity.setRuMaintanenceCnt(rs.getInt("TF_MAINTENANCE"));
				}
				
			
				
				
			}capacities.add(capacity);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MysqlConnectionProvider.releaseConnection(rs, null, pst, con);
		}

		return capacities;
	}

	public void saveDetails(TransformerBean transformerBean) {

		try {
			con = MysqlConnectionProvider.getNewConnection();
			con.setAutoCommit(false);
			con.setTransactionIsolation(con.TRANSACTION_SERIALIZABLE);

			int thId =transformerBean.getThId();
			int tId=transformerBean.gettId();
			
			
			
			int idenFlag = 2;
			transformerBean.setIdenFlag(idenFlag);
			if(thId==0){
			String query1 = "INSERT INTO tran_head(TH_DATE,TH_SECTN_ID,TH_MONTH,TH_YEAR,TH_ROW_STATUS,TH_IDEN_FLAG) VALUES (NOW(),'"
					+ transformerBean.getSecId()
					+ "','"
					+ AppUtil.getMnthId(transformerBean.getMonthId())
					+ "','"
					+ transformerBean.getYearId()
					+ "',0,'"
					+ transformerBean.getIdenFlag() + "')";

			pst1 = con.prepareStatement(query1);
			pst1.execute();
			
			String query2 = "SELECT ifnull(max(th.TH_ID), 1) FROM tran_head th";
			pst2 = con.prepareStatement(query2);
			rs = pst2.executeQuery();

			while (rs.next()) {
				transformerBean.setThId(rs.getInt(1));
			}
			}else if(tId!=0){
				String qry1 = "update tran_head SET TH_DATE = NOW(),TH_SECTN_ID = '"+transformerBean.getSecId()+"',TH_MONTH = '"+AppUtil.getMnthId(transformerBean.getMonthId())+"',TH_YEAR = '"+transformerBean.getYearId()+"',TH_ROW_STATUS = 0,TH_IDEN_FLAG = 2 WHERE TH_ID = '"+transformerBean.getThId()+"'";
			    pst4 = con.prepareStatement(qry1);
			    pst4.execute();
			    String qry2="delete from transformer  WHERE TF_TH_ID = '"+tId+"' ";
				pst5 = con.prepareStatement(qry2);
				pst5.execute();
			} 
			

						
			List<Capacity> capacityDetailsBeans = null;
			capacityDetailsBeans = transformerBean.getCapacities();

			for (Capacity bean : capacityDetailsBeans) {
				if (bean.getAdditionCnt() != 0 || bean.getFailedCnt() != 0 || bean.getReplacedCnt() != 0|| bean.getMaintanenceCnt() != 0) {
					String query3 = "INSERT INTO transformer(TF_TH_ID,TF_CAPTY_ID,TF_UR_FLAG,TF_ADD,TF_FAILED,TF_REPLACED,TF_MAINTENANCE,TF_ROW_STATUS) VALUES ('"
							+ transformerBean.getThId()
							+ "','"
							+ bean.getCapId()
							+ "','1','"
							+ bean.getAdditionCnt()
							+ "','"
							+ bean.getFailedCnt()
							+ "','"
							+ bean.getReplacedCnt()
							+ "','"
							+ bean.getMaintanenceCnt() + "',0)";
					pst = con.prepareStatement(query3);
					pst.execute();
				}
			}

			for (Capacity bean : capacityDetailsBeans) {
				if (bean.getRuAdditionCnt() != 0 || bean.getRuFailedCnt() != 0
						|| bean.getRuReplacedCnt() != 0
						|| bean.getRuMaintanenceCnt() != 0) {
					String query4 = "INSERT INTO transformer(TF_TH_ID,TF_CAPTY_ID,TF_UR_FLAG,TF_ADD,TF_FAILED,TF_REPLACED,TF_MAINTENANCE,TF_ROW_STATUS) VALUES ('"
							+ transformerBean.getThId()
							+ "','"
							+ bean.getCapId()
							+ "','2','"
							+ bean.getRuAdditionCnt()
							+ "','"
							+ bean.getRuFailedCnt()
							+ "','"
							+ bean.getRuReplacedCnt()
							+ "','"
							+ bean.getRuMaintanenceCnt() + "',0)";
					pst3 = con.prepareStatement(query4);
					pst3.execute();
				}
			}

			con.commit();
			con.setAutoCommit(true);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MysqlConnectionProvider.releaseConnection(rs, null, pst, con);
			MysqlConnectionProvider.releaseConnection(rs, null, pst1, con);
			MysqlConnectionProvider.releaseConnection(rs, null, pst2, con);
			MysqlConnectionProvider.releaseConnection(rs, null, pst3, con);
			MysqlConnectionProvider.releaseConnection(rs, null, pst4, con);
			MysqlConnectionProvider.releaseConnection(rs, null, pst5, con);
			MysqlConnectionProvider.releaseConnection(rs, null, pst6, con);
		}

	}

}
