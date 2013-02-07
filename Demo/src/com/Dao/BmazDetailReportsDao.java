package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.Util.MysqlConnectionProvider;
import com.bean.ReportsBean;

public class BmazDetailReportsDao {
	
	public List<ReportsBean> getReports(){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<ReportsBean> beans = new ArrayList<ReportsBean>();
		try {
			String query = "SELECT r.REP_NAME as repName,r.REP_PATH as repPath "
						+ "FROM reports r, report_group rg, report_map rm "
						+ "WHERE  rm.RGM_REPGRP_ID = rg.REPGRP_ID "
						+ "AND rm.RGM_REP_ID = r.REP_ID "
						+ "AND rg.REPGRP_ID = 2 "
						+ "AND r.REP_MOD_ID = 25 "
						+ "AND r.REP_ROW_STATUS = 0"
						+ " ORDER BY r.REP_ORDER ";
			connection = MysqlConnectionProvider.getNewConnection();
			preparedStatement = connection.prepareStatement(query);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				beans.add(new ReportsBean(resultSet.getString("repName"),resultSet.getString("repPath")));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}finally{
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement, connection);
		}
		return beans;
	}
}
