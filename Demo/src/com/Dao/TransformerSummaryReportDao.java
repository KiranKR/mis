package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.Util.MysqlConnectionProvider;
import com.bean.TransformerDetailsBean;
import com.bean.TransformerReportBean;


public class TransformerSummaryReportDao {

    public List<TransformerReportBean> getTransDetails(String from, String to, String role, String whrClause,int idenFlag) {

    	Connection con = null;
    	PreparedStatement pst = null;
    	ResultSet rs = null;
    	
    	
    	String qry = "";
		String orderBy = "";
		String column = "";
		List<TransformerReportBean> lstRepBeans = new ArrayList<TransformerReportBean>();
		TransformerReportBean reportBean =null;
		try {
			con = MysqlConnectionProvider.getNewConnection();
			if (role.equals("1")) {
				orderBy = " z.ZONE_ID";
				column = " z.ZONE_ID,z.ZONE_NAME";
			} else if (role.equals("2")) {
				orderBy = " c.CRCL_ID";
				column = " c.CRCL_ID,c.CRCL_NAME";
			} else if (role.equals("3")) {
				orderBy = " d.DIV_ID";
				column = " d.DIV_ID, d.DIV_NAME";
			} else if (role.equals("4")) {
				orderBy = " sd.SUBDIV_ID";
				column = " sd.SUBDIV_ID,sd.SUBDIV_NAME";
			} else if (role.equals("5")) {
				orderBy = " s.SECTN_ID";
				column = " s.SECTN_ID,s.SECTN_NAME";
			}

			qry = " SELECT ca.CPTY_ID,"
						+ " ca.CPTY_NAME,"
						+ " t.TF_ADD,"
						+ " t.TF_FAILED,"
						+ " t.TF_REPLACED,"
						+ " t.TF_MAINTENANCE,"
						+ " th.TH_MONTH,"
						+ " th.TH_YEAR,"
						+ column
						+ " FROM tran_head th "
						+ " JOIN transformer t "
						+ " ON t.TF_TH_ID = th.TH_ID "
						+ " JOIN capacity ca "
						+ " ON t.TF_CAPTY_ID = ca.CPTY_ID "
						+ " JOIN section s"
						+ " ON th.TH_SECTN_ID = s.SECTN_ID"
						+ " JOIN sub_division sd"
						+ " ON s.SECTN_SUBDIV_ID = sd.SUBDIV_ID"
						+ " JOIN division d"
						+ " ON sd.SUBDIV_DIV_ID = d.DIV_ID"
						+ " JOIN circle c"
						+ " ON d.DIV_CIRCLE_ID = c.CRCL_ID"
						+ " JOIN zone z"
						+ " ON c.CRCL_ZONE_ID = z.ZONE_ID"
						+ " WHERE th.TH_IDEN_FLAG=2 AND t.TF_UR_FLAG = "+idenFlag+" and concat(th.TH_YEAR, th.TH_MONTH) >= " + from
						+ " AND concat(th.TH_YEAR, th.TH_MONTH) <= " + to + " " + whrClause
						+ " ORDER BY " + orderBy + ",ca.CPTY_ID";
			System.out.println(qry);
			pst = con.prepareStatement(qry);
			rs = pst.executeQuery();
			TransformerDetailsBean bean =null;
			List<TransformerDetailsBean> lstDetilBeans =null;
			
			
			String flag = "";
			while (rs.next()) {
				if (flag.equals("")) {
					flag = rs.getString(9);
					
					reportBean = new TransformerReportBean();
					lstDetilBeans =new ArrayList<TransformerDetailsBean>();
					bean = new TransformerDetailsBean();
					
					reportBean.setLabel(rs.getString(10));
					reportBean.setId(rs.getString(9));

					bean.setCapName(rs.getString("CPTY_NAME"));
					bean.setAdditionCount(rs.getInt("TF_ADD"));
					bean.setFailedCount(rs.getInt("TF_FAILED"));
					bean.setReplacedCount(rs.getInt("TF_REPLACED"));
					bean.setMaintanceCount(rs.getInt("TF_MAINTENANCE"));
					lstDetilBeans.add(bean);
					
					

				} else {
					if (!flag.equals(rs.getString(9))) {
						reportBean.setTransformerDetailsBeans(lstDetilBeans);
						reportBean.setSize(lstDetilBeans.size() + 1);
						lstRepBeans.add(reportBean);
						flag = rs.getString(9);
						reportBean = new TransformerReportBean();
	                    lstDetilBeans =new ArrayList<TransformerDetailsBean>();

	                    reportBean.setLabel(rs.getString(10));
						reportBean.setId(rs.getString(9));
					}
					bean = new TransformerDetailsBean();
					bean.setCapName(rs.getString("CPTY_NAME"));
					bean.setAdditionCount(rs.getInt("TF_ADD"));
					bean.setFailedCount(rs.getInt("TF_FAILED"));
					bean.setReplacedCount(rs.getInt("TF_REPLACED"));
					bean.setMaintanceCount(rs.getInt("TF_MAINTENANCE"));
					lstDetilBeans.add(bean);
				}
			}
			reportBean.setTransformerDetailsBeans(lstDetilBeans);
			reportBean.setSize(lstDetilBeans.size()+1);
			lstRepBeans.add(reportBean);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			MysqlConnectionProvider.releaseConnection(rs, null, pst, con);
		}
		return ddfd(lstRepBeans);
	}
    
    public List<TransformerReportBean> ddfd(List<TransformerReportBean> lstRepBeans) {
		for (TransformerReportBean detailsRepBean : lstRepBeans) {

			List<TransformerDetailsBean> beans = detailsRepBean.getTransformerDetailsBeans();
			String name = "";
			TransformerDetailsBean bean = null;
			List<TransformerDetailsBean> newBeans = new ArrayList<TransformerDetailsBean>();
			for (TransformerDetailsBean transDetailsBean : beans) {

				if (name.equals("")) {
					bean =new TransformerDetailsBean();
					name = transDetailsBean.getCapName();
					bean.setCapName(transDetailsBean.getCapName());
					bean.setAdditionCount(bean.getAdditionCount()+transDetailsBean.getAdditionCount());
					bean.setFailedCount(bean.getFailedCount()+transDetailsBean.getFailedCount());
					bean.setReplacedCount(bean.getReplacedCount()+transDetailsBean.getReplacedCount());
					bean.setMaintanceCount(bean.getMaintanceCount()+transDetailsBean.getMaintanceCount());
				} else {
					if (!name.equals(transDetailsBean.getCapName())) {
						newBeans.add(bean);
						bean = new TransformerDetailsBean();
					}
					name = transDetailsBean.getCapName();
					bean.setCapName(transDetailsBean.getCapName());
					bean.setAdditionCount(bean.getAdditionCount()+transDetailsBean.getAdditionCount());
					bean.setFailedCount(bean.getFailedCount()+transDetailsBean.getFailedCount());
					bean.setReplacedCount(bean.getReplacedCount()+transDetailsBean.getReplacedCount());
					bean.setMaintanceCount(bean.getMaintanceCount()+transDetailsBean.getMaintanceCount());
				}
			}
			newBeans.add(bean);
			detailsRepBean.setTransformerDetailsBeans(newBeans);
			detailsRepBean.setSize(newBeans.size()+1);
		}
		return lstRepBeans;
	}
}
