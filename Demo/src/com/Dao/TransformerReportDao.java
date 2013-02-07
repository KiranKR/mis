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
import com.bean.TransformerDetailsBean;
import com.bean.TransformerReportBean;

public class TransformerReportDao {

	
	
	public List<TransformerReportBean> getTransformerDetails(int secId,String yearId,int idenFlag)
	{
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		String whrClause ="";
		if(idenFlag == 1){
		   whrClause = "AND  t.TF_UR_FLAG ='"+1+"'";
		}else if(idenFlag == 2){
			whrClause = "AND  t.TF_UR_FLAG ='"+2+"'";
		}else if(idenFlag == 3){
			whrClause = "";
		}
		
		
		
		List<TransformerReportBean> transformerReportBeans = new ArrayList<TransformerReportBean>();
		
		TransformerReportBean transformerReportBean = null;
		TransformerDetailsBean transformerDetailsBean =null;
		List<TransformerDetailsBean> transformerDetailsBeans =null;
		int totAdditionCnt =0;
		int totFailureCnt =0;
		int totReplacedCnt =0;
		int totMaintainceCnt =0;
		int grandTotAdditionCnt =0;
		int grandTotFailureCnt =0;
		int grandTotReplacedCnt =0;
		int grandTotMaintainceCnt =0;
		
		
		try {
			con = MysqlConnectionProvider.getNewConnection();
			String query = " SELECT t.TF_ADD AS addCnt, "
						+ "t.TF_FAILED AS failedCnt, " 
						+ "t.TF_REPLACED AS replacedCnt, "
						+ "t.TF_MAINTENANCE AS manintanceCnt, "
						+ "c.CPTY_NAME AS capName, "
						+ "s.SECTN_ID AS secId, "
						+ "th.TH_YEAR AS yearId, "
						+ "th.TH_MONTH AS Month "
						+ "FROM tran_head th "
						+ "JOIN section s "
						+ "ON th.TH_SECTN_ID = s.SECTN_ID "
						+ "JOIN transformer t "
						+ "ON t.TF_TH_ID = th.TH_ID "
						+ "JOIN capacity c "
						+ "ON t.TF_CAPTY_ID = c.CPTY_ID " 
						+ "WHERE  th.TH_MONTH >= 4 "
						+ "AND th.TH_MONTH <= MONTH(CURDATE())and s.SECTN_ID = '"+secId+"' AND th.TH_YEAR = '"+yearId+"' "+ whrClause +" "
						+ "order by th.TH_MONTH,c.CPTY_ID ";
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			String mId="";
			
			while(rs.next())
			{
				if(mId.equals("") )
				{
					transformerReportBean = new TransformerReportBean();
					transformerDetailsBean = new TransformerDetailsBean();
					transformerDetailsBeans = new ArrayList<TransformerDetailsBean>();
					mId = rs.getString("Month");
					transformerDetailsBean.setAdditionCount(rs.getInt("addCnt"));
					transformerDetailsBean.setFailedCount(rs.getInt("failedCnt"));
					transformerDetailsBean.setReplacedCount(rs.getInt("replacedCnt"));
					transformerDetailsBean.setMaintanceCount(rs.getInt("manintanceCnt"));
					transformerDetailsBean.setCapName(rs.getString("capName"));
					
					
					
					transformerDetailsBeans.add(transformerDetailsBean);
					transformerReportBean.setTransformerDetailsBeans(transformerDetailsBeans);
					transformerReportBean.setMonthId(AppUtil.getMonth(rs.getString("Month")));
					
					totAdditionCnt +=rs.getInt("addCnt");
					totFailureCnt +=rs.getInt("failedCnt");
					totReplacedCnt +=rs.getInt("replacedCnt");
					totMaintainceCnt +=rs.getInt("manintanceCnt");
					
					
					totAdditionCnt +=rs.getInt("addCnt");
					totFailureCnt +=rs.getInt("failedCnt");
					totReplacedCnt +=rs.getInt("replacedCnt");
					totMaintainceCnt +=rs.getInt("manintanceCnt");
					
					
					transformerReportBean.setTotAdditionCnt(totAdditionCnt);
					transformerReportBean.setTotFailureCnt(totFailureCnt);
					transformerReportBean.setTotReplacedCnt(totReplacedCnt);
					transformerReportBean.setTotMaintainceCnt(totMaintainceCnt);
					transformerReportBean.setSize(transformerDetailsBeans.size()+1);
					
					
					grandTotAdditionCnt +=rs.getInt("addCnt");
					grandTotFailureCnt +=rs.getInt("failedCnt");
					grandTotReplacedCnt +=rs.getInt("replacedCnt");
					grandTotMaintainceCnt +=rs.getInt("manintanceCnt");
					
					transformerReportBeans.add(transformerReportBean);
					
					
				}else if(!mId.equals(rs.getString("Month")))
				{
					transformerReportBean = new TransformerReportBean();
					transformerDetailsBean = new TransformerDetailsBean();
					transformerDetailsBeans = new ArrayList<TransformerDetailsBean>();
					mId = rs.getString("Month");
					transformerDetailsBean.setAdditionCount(rs.getInt("addCnt"));
					transformerDetailsBean.setFailedCount(rs.getInt("failedCnt"));
					transformerDetailsBean.setReplacedCount(rs.getInt("replacedCnt"));
					transformerDetailsBean.setMaintanceCount(rs.getInt("manintanceCnt"));
					transformerDetailsBean.setCapName(rs.getString("capName"));
					transformerDetailsBeans.add(transformerDetailsBean);
					transformerReportBean.setTransformerDetailsBeans(transformerDetailsBeans);
					transformerReportBean.setMonthId(AppUtil.getMonth(rs.getString("Month")));
					
					 totAdditionCnt =0;
					 totFailureCnt =0;
					 totReplacedCnt =0;
				     totMaintainceCnt =0;
					 
					
					
					totAdditionCnt +=rs.getInt("addCnt");
					totFailureCnt +=rs.getInt("failedCnt");
					totReplacedCnt +=rs.getInt("replacedCnt");
					totMaintainceCnt +=rs.getInt("manintanceCnt");
					
					
					transformerReportBean.setTotAdditionCnt(totAdditionCnt);
					transformerReportBean.setTotFailureCnt(totFailureCnt);
					transformerReportBean.setTotReplacedCnt(totReplacedCnt);
					transformerReportBean.setTotMaintainceCnt(totMaintainceCnt);
					
					grandTotAdditionCnt +=rs.getInt("addCnt");
					grandTotFailureCnt +=rs.getInt("failedCnt");
					grandTotReplacedCnt +=rs.getInt("replacedCnt");
					grandTotMaintainceCnt +=rs.getInt("manintanceCnt");
					transformerReportBean.setSize(transformerDetailsBeans.size()+1);
					transformerReportBeans.add(transformerReportBean);
				}else if(mId.equals(rs.getString("Month")))
				{
					transformerDetailsBean = new TransformerDetailsBean();
					transformerDetailsBean.setAdditionCount(rs.getInt("addCnt"));
					transformerDetailsBean.setFailedCount(rs.getInt("failedCnt"));
					transformerDetailsBean.setReplacedCount(rs.getInt("replacedCnt"));
					transformerDetailsBean.setMaintanceCount(rs.getInt("manintanceCnt"));
					transformerDetailsBean.setCapName(rs.getString("capName"));
					transformerDetailsBeans.add(transformerDetailsBean);
					transformerReportBean.setTransformerDetailsBeans(transformerDetailsBeans);
					
					totAdditionCnt +=rs.getInt("addCnt");
					totFailureCnt +=rs.getInt("failedCnt");
					totReplacedCnt +=rs.getInt("replacedCnt");
					totMaintainceCnt +=rs.getInt("manintanceCnt");
					
					
					transformerReportBean.setTotAdditionCnt(totAdditionCnt);
					transformerReportBean.setTotFailureCnt(totFailureCnt);
					transformerReportBean.setTotReplacedCnt(totReplacedCnt);
					transformerReportBean.setTotMaintainceCnt(totMaintainceCnt);
					transformerReportBean.setSize(transformerDetailsBeans.size()+1);
					grandTotAdditionCnt +=rs.getInt("addCnt");
					grandTotFailureCnt +=rs.getInt("failedCnt");
					grandTotReplacedCnt +=rs.getInt("replacedCnt");
					grandTotMaintainceCnt +=rs.getInt("manintanceCnt");
					
				}if(rs.isLast()){
					transformerReportBean.setGrandTotAdditionCnt(grandTotAdditionCnt);
					transformerReportBean.setGrandTotFailureCnt(grandTotFailureCnt);
					transformerReportBean.setGrandTotReplacedCnt(grandTotReplacedCnt);
					transformerReportBean.setGrandTotMaintainceCnt(grandTotMaintainceCnt);
				}
			}
			
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			MysqlConnectionProvider.releaseConnection(rs, null, pst, con);
		}
		
		
		
		
		
		return transformerReportBeans;
	}
	

}
