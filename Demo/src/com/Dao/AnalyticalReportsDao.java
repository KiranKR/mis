package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.Util.MysqlConnectionProvider;
import com.bean.ReportsBean;

public class AnalyticalReportsDao {

	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	DecimalFormat format = new DecimalFormat("#,###,##0.00");
	
	public List<ReportsBean> getReports(){
		List<ReportsBean> beans = new ArrayList<ReportsBean>();
		try {
			String query ="SELECT r.REP_NAME AS repName, r.REP_PATH AS repPath "
	  			+ "FROM reports r, report_group rg, report_map rm "
	  			+ "WHERE  rm.RGM_REPGRP_ID = rg.REPGRP_ID "
	  			+ "AND rm.RGM_REP_ID = r.REP_ID "
	  			+ "AND rg.REPGRP_ID = 1  "
	  			+ "AND r.REP_MOD_ID = 27 "
	  			+ "AND r.REP_ROW_STATUS = 0 "
	  			+ "ORDER BY r.REP_ORDER ";
			connection = MysqlConnectionProvider.getNewConnection();
			preparedStatement = connection.prepareStatement(query);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				beans.add(new ReportsBean(resultSet.getString("repName"),resultSet.getString("repPath")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}finally{
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement, connection);
		}
		return beans;
	}
	
	

	
	
	private String dateFormat(String date) {
		date = date.substring(0,10);
		String[] field = date.split("-");
		return field[2]+"/"+field[1]+"/"+field[0];
		}

public static void main(String[] args) {
    
}

}
