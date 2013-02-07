package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Util.AppUtil;
import com.Util.MysqlConnectionProvider;
import com.bean.RevenueBean;
import com.bean.RevenueDetailsBean;
import com.bean.RevenueReportBean;

public class RevenueReportDao {

	
	public List<RevenueBean> getRevenueReport(RevenueBean bean)
	{
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs= null;
		
		
		List<RevenueBean> lstRevenueBeans = new ArrayList<RevenueBean>();
		RevenueBean revenueBean = null;
		
		try {
			con = MysqlConnectionProvider.getNewConnection();
			String query ="SELECT r.REV_TOTAL_INSTALATION AS noOfInstall, "
						+ "r.REV_INPUT_ENERGY AS inEnergy, "
						+ "r.REV_DCB_ENERGY AS dcbEnergy, "
						+ "r.REV_DCB_OB AS openBalance, "
						+ "r.REV_DEMAND AS demand, "
						+ "r.REV_COLLECTION AS collection, "
						+ "r.REV_DCB_CB AS closingBalance, " 
						+ "r.REV_REMARKS AS reason, "
						+ "th.TH_MONTH AS month, "
						+ "s.SECTN_ID AS secId, "
						+ "s.SECTN_NAME AS secName, "
						+ "sd.SUBDIV_ID AS subDivId, "
						+ "sd.SUBDIV_NAME AS suDivName, "
						+ "d.DIV_ID AS divId, "
						+ "d.DIV_NAME AS divName, "
						+ "c.CRCL_ID AS cirId, "
						+ "c.CRCL_NAME AS cirName, "
						+ "z.ZONE_ID AS zonId, "
						+ "z.ZONE_NAME AS zonName "
						+ "FROM tran_head th "
						+ "JOIN revenue r "
						+ "ON r.REV_TH_ID = th.TH_ID "
						+ "JOIN section s "
						+ "ON th.TH_SECTN_ID = s.SECTN_ID "
						+ "JOIN sub_division sd "
						+ "ON s.SECTN_SUBDIV_ID = sd.SUBDIV_ID "
						+ "JOIN division d "
						+ "ON sd.SUBDIV_DIV_ID = d.DIV_ID "
						+ "JOIN circle c "
						+ "ON d.DIV_CIRCLE_ID = c.CRCL_ID "
						+ "JOIN zone z "
						+ "ON c.CRCL_ZONE_ID = z.ZONE_ID "
						+ "WHERE th.TH_YEAR = '"+bean.getYearId()+"' and s.SECTN_ID="+bean.getSectionId()+" AND th.TH_IDEN_FLAG = '4' ";

			System.out.println(query);
			String mId ="";
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			while(rs.next()){
					revenueBean = new RevenueBean();
					mId = rs.getString("month");
					revenueBean.setMonthId(AppUtil.getMonth(rs.getString("month")));
					revenueBean.setTotalNoInstall(rs.getInt("noOfInstall"));
					revenueBean.setInputEnergy(rs.getInt("inEnergy"));
					revenueBean.setEngSoldPerDCB(rs.getInt("dcbEnergy"));
					revenueBean.setOpenBal(rs.getInt("openBalance"));
					revenueBean.setDemand(rs.getDouble("demand"));
					revenueBean.setCollection(rs.getDouble("collection"));
					revenueBean.setCloseBal(rs.getDouble("closingBalance"));
					revenueBean.setReason(rs.getString("reason"));
					revenueBean.setSectionId(rs.getString("secId"));
					revenueBean.setSectionNme(rs.getString("secName"));
					
					lstRevenueBeans.add(revenueBean);
				
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return lstRevenueBeans;
	}
	
	
	
}
