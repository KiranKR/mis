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
import com.bean.FeederInterruptionReportMainBean;
import com.bean.FeederInterruptionReportsBean;

public class FeederInterruptionReportsDao {
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	
	public static List getDropDownList(String tableName, String orderById,String dbFldId, String dbFldValue, String whrClause,int skpDefaultVal) throws SQLException, ClassNotFoundException {

		String qry = "SELECT * FROM " + tableName + " "
		+ whrClause + " ORDER BY " + orderById + "";
		
		List lsDDList = new ArrayList();
		Connection con2 = null;
		PreparedStatement pst2 = null;
		ResultSet rs2 = null;

		try {
		con2 = MysqlConnectionProvider.getNewConnection();
		pst2 = con2.prepareStatement(qry);
		rs2 = pst2.executeQuery();
		SelectItem noneItem = new SelectItem("0", "Select");

		if (skpDefaultVal != 1) {
		lsDDList.add(noneItem);
		}
		while (rs2.next()) {
		noneItem = new SelectItem(rs2.getString(dbFldId),
		rs2.getString(dbFldValue));
		lsDDList.add(noneItem);
		}
		} catch (SQLException sqle) {
		throw sqle;
		} catch (ClassNotFoundException e) {
		e.printStackTrace();
		throw e;
		} 
		finally {
		MysqlConnectionProvider.releaseConnection(rs2, null, pst2, con2);
		}
		return lsDDList;
		}


	public List<FeederInterruptionReportMainBean> getFeederReportDetails(String secid,String yearid)
 	{
		List<FeederInterruptionReportMainBean> fdreports = new ArrayList<FeederInterruptionReportMainBean>();
		
		FeederInterruptionReportsBean feederInterruptionReportsBean = null;
		FeederInterruptionReportMainBean feederInterruptionReportMainBean = null;
		List<FeederInterruptionReportsBean> feederInterruptionReportsBeansList = null;
		
		
		String sql_str1="SELECT f.FDR_NAME AS fedName, "
                        + "fl.FL_FDR_TRIPPED_COUNT AS fedCount, "
                        +   "fl.FL_FDR_REASON AS reason, "
                        +	"th.TH_MONTH As month_id "
                        +	"FROM tran_head th "
                        +	"JOIN feeder_line fl "
                        +	"ON fl.FL_TH_ID = th.TH_ID "
                        +	"JOIN feeder f "
                        +	"ON fl.FL_FDR_ID = f.FDR_ID "
                        +	"WHERE th.TH_SECTN_ID ='"+secid+"' AND th.TH_YEAR = '"+yearid+"' order by th.TH_MONTH,f.FDR_NAME" ;
		
		
		try {
			connection = MysqlConnectionProvider.getNewConnection();
			preparedStatement  = connection.prepareStatement(sql_str1);
			rs = preparedStatement.executeQuery();
			String mId="";
			
			while(rs.next())
			{
				if(mId.equals("")){
				feederInterruptionReportsBean = new FeederInterruptionReportsBean();
				feederInterruptionReportMainBean = new FeederInterruptionReportMainBean();
				feederInterruptionReportsBeansList = new ArrayList<FeederInterruptionReportsBean>();
				mId=rs.getString("month_id");
							
				feederInterruptionReportsBean.setFedName(rs.getString("fedName"));
				
				feederInterruptionReportsBean.setFedCount(rs.getInt("fedCount"));
				feederInterruptionReportsBean.setFedReason(rs.getString("reason"));
				feederInterruptionReportsBeansList.add(feederInterruptionReportsBean);
				feederInterruptionReportMainBean.setMonthId(AppUtil.getMonth(rs.getString("month_id")));
				
				
			}else if(!mId.equals(rs.getString("month_id")))
			{
				feederInterruptionReportMainBean.setSize(feederInterruptionReportsBeansList.size());
				feederInterruptionReportMainBean.setFedintrputionDetails(feederInterruptionReportsBeansList);
				fdreports.add(feederInterruptionReportMainBean);
				
				feederInterruptionReportsBean = new FeederInterruptionReportsBean();
				feederInterruptionReportMainBean = new FeederInterruptionReportMainBean();
				feederInterruptionReportsBeansList = new ArrayList<FeederInterruptionReportsBean>();
				mId=rs.getString("month_id");
							
				feederInterruptionReportsBean.setFedName(rs.getString("fedName"));
				
				feederInterruptionReportsBean.setFedCount(rs.getInt("fedCount"));
				feederInterruptionReportsBean.setFedReason(rs.getString("reason"));
				feederInterruptionReportsBeansList.add(feederInterruptionReportsBean);
				feederInterruptionReportMainBean.setMonthId(AppUtil.getMonth(rs.getString("month_id")));
			
			}else if(mId.equals(rs.getString("month_id")))
			{   
				feederInterruptionReportsBean = new FeederInterruptionReportsBean();
				feederInterruptionReportsBean.setFedName(rs.getString("fedName"));
				
				feederInterruptionReportsBean.setFedCount(rs.getInt("fedCount"));
				feederInterruptionReportsBean.setFedReason(rs.getString("reason"));
				feederInterruptionReportsBeansList.add(feederInterruptionReportsBean);
				
			}
				
			}if(rs.isAfterLast()){
				feederInterruptionReportMainBean.setSize(feederInterruptionReportsBeansList.size());
				feederInterruptionReportMainBean.setFedintrputionDetails(feederInterruptionReportsBeansList);
				fdreports.add(feederInterruptionReportMainBean);
			}
		} catch (Exception e) {
		
		}
		
		return fdreports;
		
	}
}
