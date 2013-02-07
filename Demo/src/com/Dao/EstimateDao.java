package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.Util.MysqlConnectionProvider;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.PendingRep;

public class EstimateDao {

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	
	
	private PendingRep pendingRep;
	
	public List<PendingRep> getDisplayDetilas(String search,String criteria)
	{
		List<PendingRep> pendingReps = new ArrayList<PendingRep>();
		String condition = "";
		String query;
		try {
			/*String query = "SELECT pw.PWRK_UID AS UID, "
						 + "pw.PWRK_BENEFICIARY AS BeneficiaryName, "
						 + "pw.PWRK_VILLAGE AS Villeage, "
						 + "pw.PWRK_REG_DATE AS RegDate "
						 + "FROM program_works pw";*/
			
			/*String query="select pw.PWRK_ID as UID , pw.PWRK_BENEFICIARY as BeneficiaryName  ,pw.PWRK_VILLAGE as Villeage,pw.PWRK_REG_DATE as RegDate from program_works pw where PWRK_STAGE_IDEN >=1";*/
			
			/*String query=" select pw.PWRK_ID as UID , pw.PWRK_BENEFICIARY as BeneficiaryName, "+
                          " pw.PWRK_VILLAGE as Villeage, "+
                           " pw.PWRK_REG_DATE as RegDate, "+
                           " t.TALUK_NAME as taluk ,s.SECTN_NAME as session , "+
                              " c.CSTCTG_NAME as scheme "+
                              "pw.PWRK_CSTCTG_IDEN_FLAG AS subSceheme "+
                           "  from program_works pw JOIN taluk t ON pw.PWRK_TALUK_ID = t.TALUK_ID "+
                           "  JOIN section s  ON pw.PWRK_SECTN_ID = s.SECTN_ID "+
                            " JOIN caste_category c  ON pw.PWRK_CSTCTG_ID = c.CSTCTG_ID "+

                            "where PWRK_STAGE_IDEN >=1 "; */
			if (search.equals("") || criteria.equals("")) {

				query = " select pw.PWRK_UID as UID,pw.PWRK_ID as ID ,"
						+ " pw.PWRK_BENEFICIARY as BeneficiaryName, "
						+ " pw.PWRK_VILLAGE as Villeage, "
						+ "pw.PWRK_REG_DATE as RegDate, "
						+ "pw.PWRK_ESTIMATE_COST AS estimateCost ,"
						+ "t.TALUK_NAME,s.SECTN_NAME, "
						+ "c.CSTCTG_AKA as scheme, "
						+ "pw.PWRK_CSTCTG_IDEN_FLAG AS subSceheme, pw.PWRK_ESTIMATE_DATE AS estDate, pw.PWRK_ESTIMATE_NO AS estNo,pw.PWRK_STAGE_IDEN as iden, pw.PWRK_REG_FEE AS regFee, pw.PWRK_RTNO AS regNo"
						+ " from program_works pw JOIN taluk t ON pw.PWRK_TALUK_ID = t.TALUK_ID "
						+ " JOIN section s  ON pw.PWRK_SECTN_ID = s.SECTN_ID "
						+ " JOIN caste_category c  ON pw.PWRK_CSTCTG_ID = c.CSTCTG_ID "
						+ " WHERE  pw.PWRK_STAGE_IDEN >= 1 and pw.PWRK_ROW_STATUS = 0 " 
						+ "and s.SECTN_SUBDIV_ID = "+UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+" ";
			} else

			{
				if(criteria.equals("1")){
					condition = "pw.PWRK_UID like '%"+search+"%'";
				}else if (criteria.equals("2")) {
					condition = "pw.PWRK_BENEFICIARY like '%"+search+"%'";
				}
				query = " select pw.PWRK_UID as UID,pw.PWRK_ID as ID ,"
						+ " pw.PWRK_BENEFICIARY as BeneficiaryName, "
						+ " pw.PWRK_VILLAGE as Villeage, "
						+ "pw.PWRK_REG_DATE as RegDate, "
						+ "pw.PWRK_ESTIMATE_COST AS estimateCost ,"
						+ "t.TALUK_NAME,s.SECTN_NAME, "
						+ "c.CSTCTG_NAME as scheme, "
						+ "pw.PWRK_CSTCTG_IDEN_FLAG AS subSceheme, pw.PWRK_ESTIMATE_DATE AS estDate, pw.PWRK_ESTIMATE_NO AS estNo,pw.PWRK_STAGE_IDEN as iden, pw.PWRK_REG_FEE AS regFee, pw.PWRK_RTNO AS regNo"
						+ " from program_works pw JOIN taluk t ON pw.PWRK_TALUK_ID = t.TALUK_ID "
						+ " JOIN section s  ON pw.PWRK_SECTN_ID = s.SECTN_ID "
						+ " JOIN caste_category c  ON pw.PWRK_CSTCTG_ID = c.CSTCTG_ID "
						+ " WHERE "+ condition +" " 
						+ " AND pw.PWRK_STAGE_IDEN >= 1 and pw.PWRK_ROW_STATUS = 0 " 
						+ "and s.SECTN_SUBDIV_ID = "+UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+" ";
			}
			connection = MysqlConnectionProvider.getNewConnection();
			preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
            {
            	pendingRep = new PendingRep();
            	pendingRep.setUniqueId(resultSet.getString("UID"));
            	pendingRep.setPwStageIden(resultSet.getString("iden"));
            	pendingRep.setPrgWrId(resultSet.getString("ID"));
            	pendingRep.setBeneficiaryName(resultSet.getString("BeneficiaryName"));
            	pendingRep.setVillage(resultSet.getString("Villeage"));
            	pendingRep.setDtOfRegtn(dateToString(resultSet.getDate("RegDate")));
            	pendingRep.setTalukId(resultSet.getString("TALUK_NAME"));
            	pendingRep.setSectionId(resultSet.getString("SECTN_NAME"));
            	pendingRep.setSchemeId(resultSet.getString("scheme"));
            	
            	if(resultSet.getString("subSceheme").equals("1")){
                   pendingRep.setSubScheme("Individual");
            	}else {
                    pendingRep.setSubScheme("Group");
            	}
            	pendingRep.setRegstrFee(resultSet.getDouble("regFee"));
            	pendingRep.setRegstrNo(resultSet.getString("regNo"));
            	if(!pendingRep.getPwStageIden().equals("1")){
	            	pendingRep.setEstimateDate(dateToString(resultSet.getDate("estDate")));
	            	pendingRep.setEstimateNo(resultSet.getString("estNo"));
	            	pendingRep.setEstimateCost(resultSet.getDouble("estimateCost"));
            	}
            	pendingReps.add(pendingRep);
            }
            }catch (Exception e) {
            	e.printStackTrace();
			}
			
		
		return pendingReps;
	}
	
	
	
	public static String StringToDate(String pDate) {
		try {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = (Date) formatter.parse(pDate);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date);
		} catch (java.text.ParseException e) {
		e.printStackTrace();
		}
		return null;
		}

	private String dateFormat(String date) {
		String[] field = date.split("-");
		return field[2] + "/" + field[1] + "/" + field[0];
	}
	public static String dateToString(Date pDate){
		DateFormat formatter ;
		formatter = new SimpleDateFormat("dd/MM/yyyy");
		String s = formatter.format(pDate);
		return s;

		}

	

	public int updateEstimate(PendingRep pendingRep)
	{
		String query="UPDATE program_works SET PWRK_ESTIMATE_NO='"+pendingRep.getEstimateNo()+"', " +
						"PWRK_ESTIMATE_COST="+pendingRep.getEstimateCost()+"," +
						"PWRK_ESTIMATE_DATE= str_to_date('"+pendingRep.getEstimateDate()+"','%d/%m/%Y') " +
						"WHERE PWRK_ID="+pendingRep.getPrgWrId();
		
		 int i = 0;
		try {
			String thisDate=pendingRep.getEstimateDate();
			connection = MysqlConnectionProvider.getNewConnection();
			preparedStatement = connection.prepareStatement(query);
	         i = preparedStatement.executeUpdate(query);
	        if(i==1)
	        {
	   String query2="UPDATE program_works SET PWRK_STAGE_IDEN=2 WHERE PWRK_ID="+pendingRep.getPrgWrId();
	   int j = preparedStatement.executeUpdate(query2);
	        }
	        
	      /*  while(resultSet.next())
            {
            	pendingRep = new PendingRep();
            	pendingRep.setuId(resultSet.getString("UID"));
            	pendingRep.setBeneficiaryName(resultSet.getString("BeneficiaryName"));
            	pendingRep.setVillage(resultSet.getString("Villeage"));
            	pendingRep.setDtOfRegtn(dateToString(resultSet.getDate("RegDate")));
            	pendingReps.add(pendingRep);
            }*/
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return i;
	}


 



	
	
	
}
