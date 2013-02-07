package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Util.MysqlConnectionProvider;
import com.bean.PendingRep;

public class PendingReportDao {
	
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	public boolean saveDetails(PendingRep pendingRep){
		boolean isValid = false;
		try {
			
			String query = "insert into pending_rep (TALUK,DIVISION,SUBDIV,SECTION,SCHEME," +
						" SUB_SCHEME,BENEFICIARY_NAME,VILLAGE,DT_OF_REGTN,ESTIMATE_PREPARED," +
						" ESTIMATE_COST,INTIMATN_ISS_DATE,WO_ISS_NUM,WO_ISS_DATE,WRK_YET_TO_BE_TAKEN," +
						" WRK_UNDR_PROGRESS,WRK_COMPLETED,PEND_FRM_LOC_BDY,SEC_ID,SUB_DIV_ID) " +
						" VALUES (?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),?,?,STR_TO_DATE(?,'%d/%m/%Y'),?,STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?);";
			connection =  MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, pendingRep.getTalukId());
			preparedStatement.setString(2, pendingRep.getDivision());
			preparedStatement.setString(3, pendingRep.getSubdiv());
			preparedStatement.setString(4, pendingRep.getSectionId());
			preparedStatement.setString(5, pendingRep.getSchemeId());
			preparedStatement.setString(6, pendingRep.getSubSchemeId());
			preparedStatement.setString(7, pendingRep.getBeneficiaryName());
			preparedStatement.setString(8, pendingRep.getVillage());
			preparedStatement.setString(9, pendingRep.getDtOfRegtn());
			preparedStatement.setString(10, pendingRep.getEstimatePrepared());
			preparedStatement.setDouble(11, pendingRep.getEstimateCost());
			preparedStatement.setString(12, pendingRep.getIntimatnIssDate());
			preparedStatement.setString(13, pendingRep.getWoIssNum());
			preparedStatement.setString(14, pendingRep.getWoIssDate());
			preparedStatement.setString(15, pendingRep.getWrkYetToBeTaken());
			preparedStatement.setString(16, pendingRep.getWrkUndrProgress());
			preparedStatement.setString(17, pendingRep.getWrkCompleted());
			preparedStatement.setString(18, pendingRep.getPendFrmLocBdy());
			preparedStatement.setInt(19, Integer.parseInt(pendingRep.getSectionId()));
			preparedStatement.setInt(20, Integer.parseInt(pendingRep.getSubDivId()));
			isValid = preparedStatement.execute();
			isValid = true;
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement, connection);
		}
		return isValid;
	}
}
