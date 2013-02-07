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
import com.bean.DepositeBean;
import com.bean.PendingRep;
import com.bean.StatusDateBean;

public class ServiceDetailsDao {

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	
	
	
	public List<PendingRep> getDisplayDetilas(String search,String criteria)
	{
		String condition = "";
		List<PendingRep> pendingReps = new ArrayList<PendingRep>();
		PendingRep pendingRep;
		String query =null;
		try {
		 
			if(search.equals("")|| criteria.equals("")){
			 query = "SELECT pw.PWRK_UID AS UID, "
						 +  "pw.PWRK_ID AS ID, "
						 + "pw.PWRK_BENEFICIARY AS BeneficiaryName, "
						 + "pw.PWRK_VILLAGE AS Villeage, "
						 + "pw.PWRK_REG_DATE AS RegDate "
						 + "FROM program_works pw "
						 + "JOIN section s ON pw.PWRK_SECTN_ID = s.SECTN_ID " 
						 + "WHERE  pw.PWRK_STAGE_IDEN >= 6 and pw.PWRK_ROW_STATUS = 0 " 
						 + "and s.SECTN_SUBDIV_ID = "+UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+" ";
			}else{
				if(criteria.equals("1")){
					condition = "pw.PWRK_UID like '%"+search+"%'";
				}else if (criteria.equals("2")) {
					condition = "pw.PWRK_BENEFICIARY like '%"+search+"%'";
				}else if(criteria.equals("3")){
					condition = "pw.PWRK_WO_NO like '%"+search+"%'";
				}else if(criteria.equals("4")){
					condition = "pw.PWRK_RR_NO like '%"+search+"%'";
				}
				 query = "SELECT pw.PWRK_UID AS UID, "
					 +  "pw.PWRK_ID AS ID, "
					 + "pw.PWRK_BENEFICIARY AS BeneficiaryName, "
					 + "pw.PWRK_VILLAGE AS Villeage, "
					 + "pw.PWRK_REG_DATE AS RegDate "
					 + "FROM program_works pw "
					 + "JOIN section s ON pw.PWRK_SECTN_ID = s.SECTN_ID " 
					 + "WHERE  pw.PWRK_STAGE_IDEN >= 6 and pw.PWRK_ROW_STATUS = 0 " 
					 + "and s.SECTN_SUBDIV_ID = "+UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+" "
					 + "and "+ condition +" ";
	
			}
			connection = MysqlConnectionProvider.getNewConnection();
			preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
            {
            	pendingRep = new PendingRep();
            	pendingRep.setUniqueId(resultSet.getString("UID"));
            	pendingRep.setPrgWrId((resultSet.getString("ID")));
            	pendingRep.setBeneficiaryName(resultSet.getString("BeneficiaryName"));
            	pendingRep.setVillage(resultSet.getString("Villeage"));
            	pendingRep.setDtOfRegtn(dateToString(resultSet.getDate("RegDate")));
            	pendingReps.add(pendingRep);
            }
            }catch (Exception e) {
            	e.printStackTrace();
			}
			
		
		return pendingReps;
	}
	
	
	
	public void updateServiceDetails(PendingRep pendingRep)
	{
		try {
		String query ="UPDATE program_works pw "
				    + "SET pw.PWRK_RR_NO = '"+pendingRep.getRrNumber()+"', "
				    + "pw.PWRK_DATE_OF_SERVICE = STR_TO_DATE('"+pendingRep.getDateofService()+"', '%d/%m/%Y'), "
				    + "pw.PWRK_STAGE_IDEN = 7 "
				    + "WHERE PWRK_ID = '"+pendingRep.getPrgWrId()+"'";
		
		connection = MysqlConnectionProvider.getNewConnection();
		preparedStatement = connection.prepareStatement(query);
        boolean b = preparedStatement.execute();
		}catch (Exception e) {
		   e.printStackTrace();
		}	
}
	
	public PendingRep getDetail(String pwId) {

		PendingRep rep = null;
		try {
			String query = " SELECT pw.PWRK_ID,pw.PWRK_UID," + " t.TALUK_ID,"
					+ " t.TALUK_NAME," + " s.SECTN_ID," + " s.SECTN_NAME,"
					+ " cc.CSTCTG_ID," + " cc.CSTCTG_AKA,"
					+ " pw.PWRK_CSTCTG_IDEN_FLAG," + " pw.PWRK_BENEFICIARY,"
					+ " pw.PWRK_REG_DATE," + " pw.PWRK_VILLAGE,"
					+ " pw.PWRK_ESTIMATE_NO," + " pw.PWRK_ESTIMATE_COST,"
					+ " pw.PWRK_ESTIMATE_DATE," + " pw.PWRK_INTIMATE_DATE, "
					+ " pw.PWRK_STAGE_IDEN,pw.PWRK_RR_NO,pw.PWRK_DATE_OF_SERVICE,pw.PWRK_WO_NO,pw.PWRK_WO_ISSUE_DATE,pw.PWRK_WORK_AWARD_DATE,pw.PWRK_HT_LINE,pw.PWRK_LT_LINE,pw.PWRK_TF_CAPACITY,pw.PWRK_TF_LOAD, pw.PWRK_REG_FEE, pw.PWRK_RTNO FROM program_works pw"
					+ " JOIN taluk t" + " ON pw.PWRK_TALUK_ID = t.TALUK_ID"
					+ " JOIN section s" + " ON pw.PWRK_SECTN_ID = s.SECTN_ID"
					+ " JOIN caste_category cc"
					+ " ON pw.PWRK_CSTCTG_ID = cc.CSTCTG_ID"
					+ " where  pw.PWRK_STAGE_IDEN >= 6 and pw.PWRK_ID = " + pwId;
			connection = MysqlConnectionProvider.getNewConnection();
			preparedStatement = connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				rep = new PendingRep();
				rep.setPrgWrId(resultSet.getString(1));
				rep.setUniqueId(resultSet.getString(2));
				rep.setTaluk(resultSet.getString(4));
				rep.setSectionId(resultSet.getString(5));
				rep.setSection(resultSet.getString(6));
				rep.setScheme(resultSet.getString(8));
				if (resultSet.getString(9).equals("1")) {
					rep.setSubScheme("Individual");
				} else {
					rep.setSubScheme("Group");
				}
				rep.setBeneficiaryName(resultSet.getString(10));
				rep.setDtOfRegtn(dateFormat(resultSet.getString(11)));
				rep.setVillage(resultSet.getString(12));
				rep.setEstiNum(resultSet.getString(13));
				rep.setEstimateCost(resultSet.getDouble(14));
				rep.setEstiDate(dateFormat(resultSet.getString(15)));
				rep.setIntimatnIssDate(dateFormat(resultSet.getString(16)));
				rep.setPwStageIden(resultSet.getString(17));
				rep.setWorkOrderNum(resultSet.getString(20));
				rep.setWrkOrderIssDt(dateFormat(resultSet.getString(21)));
				rep.setWrkAwrdDt(dateFormat(resultSet.getString(22)));
				rep.setHtLine(resultSet.getString(23));
				rep.setLtLine(resultSet.getString(24));
				rep.setTfCap(resultSet.getString(25));
				rep.setTfLoad(resultSet.getString(26));
				rep.setRegstrFee(resultSet.getDouble(27));
				rep.setRegstrNo(resultSet.getString(28));
				if(!rep.getPwStageIden().equals("6")){
					rep.setRrNumber(resultSet.getString(18));
					rep.setDateofService(dateFormat(resultSet.getString(19)));
				}
					query = " SELECT d.DEPST_ID, d.DEPST_NAME, pd.PDPST_AMOUNT, pd.PDPST_APLICABLE_YN"
							+ " FROM deposit d" + " JOIN program_deposit pd"
							+ " ON pd.PDPST_DEPST_ID = d.DEPST_ID"
							+ " JOIN program_works pw"
							+ " ON pd.PDPST_PWRK_ID = pw.PWRK_ID"
							+ " WHERE pw.PWRK_ID = " + pwId;
					preparedStatement = connection.prepareStatement(query);
					ResultSet resultSet1 = null;
					resultSet1 = preparedStatement.executeQuery();
					List<DepositeBean> beans = new ArrayList<DepositeBean>();
					while (resultSet1.next()) {
						DepositeBean bean = new DepositeBean();
						bean.setId(resultSet1.getString(1));
						bean.setDepParticulars(resultSet1.getString(2));
						bean.setAmnt(resultSet1.getDouble(3));
						if(resultSet1.getInt("PDPST_APLICABLE_YN")==0){
							bean.setAmtApplicable("No");
						}else if(resultSet1.getInt("PDPST_APLICABLE_YN")==1){
							bean.setAmtApplicable("Yes");
						}
						beans.add(bean);
					}
					rep.setDepositeBeans(beans);
					List<StatusDateBean> sdBeans = new ArrayList<StatusDateBean>();
					
						query = "select ps.PSTS_STS_ID,ps.PSTS_STS_DATE " +
								"from program_status ps join program_works pw " +
								"ON ps.PSTS_PWRK_ID = pw.PWRK_ID join status s " +
								"ON ps.PSTS_STS_ID = s.STS_ID where ps.PSTS_PWRK_ID = "+ pwId +" order by ps.PSTS_STS_ID";
					preparedStatement = connection.prepareStatement(query);
					ResultSet resultSet2 = null;
					resultSet2 = preparedStatement.executeQuery();
					while (resultSet2.next()) {
						StatusDateBean bean = new StatusDateBean();
						bean.setId(resultSet2.getString(1));
						bean.setDate(dateFormat(resultSet2.getString(2)));
						sdBeans.add(bean);
					}
					for (StatusDateBean statusDateBean : sdBeans) {
						if(statusDateBean.getId().equals("3")){
							rep.setWrkUndrProgress(statusDateBean.getDate());
						}
						if(statusDateBean.getId().equals("4")){
							rep.setWrkCompleted(statusDateBean.getDate());
						}
					}
				
				rep.setStatusDateBeans(sdBeans);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
		  MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement, connection);
		}
		return rep;
	}	
	private String dateFormat(String date) {
		if(date != null){
		date = date.substring(0,10);
		String[] field = date.split("-");
		return field[2]+"/"+field[1]+"/"+field[0];
		}
		return "";
	}
	
	
	
	
	public static String dateToString(Date pDate){
		DateFormat formatter ;
		formatter = new SimpleDateFormat("dd/MM/yyyy");
		String s = formatter.format(pDate);
		return s;

		}

	
}
