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

import javax.faces.model.SelectItem;

import com.Util.MysqlConnectionProvider;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.DepositeBean;
import com.bean.PendingRep;
import com.bean.ProgramDetailsRepBean;
import com.bean.StatusDateBean;

public class ProgramDetailsRepDao {
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	public List prgDetails(ProgramDetailsRepBean repBean){
		List prgDetailsLst = new ArrayList();
		PendingRep rep = null;
		
		
		try {
			String idenId = repBean.getGrpByDateNme();
			String dateConstraint = "";
			if("1".equals(idenId)){
				dateConstraint = "PWRK_REG_DATE";
			}else if("2".equals(idenId)){
				dateConstraint = "PWRK_ESTIMATE_DATE";
			}else if("3".equals(idenId)){
				dateConstraint = "PWRK_WO_ISSUE_DATE";
			}else if("4".equals(idenId)){
				dateConstraint = "PWRK_DATE_OF_SERVICE";
			}
			
			int idenFlag = Integer.valueOf(UserUtil.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION));
			String filterOnFlag = "";
			if(idenFlag == 1){
				filterOnFlag = " z.ZONE_ID = "+UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+" ";
			}else if(idenFlag == 2){
				filterOnFlag = " c.CRCL_ID = "+UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+" ";
			}else if(idenFlag == 3){
				filterOnFlag = " d.DIV_ID = "+UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+" ";
			}else if(idenFlag == 4){
				filterOnFlag = " sd.SUBDIV_ID  = "+UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+" ";
			}
			
			String sectionConstraint = "";
			if(!("All".equals(repBean.getSectionId()))){
				sectionConstraint = " and s.SECTN_ID = "+repBean.getSectionId()+" ";
			}
			String query = " SELECT " +
					"pw.PWRK_ID," +
					"pw.PWRK_UID," +
					" t.TALUK_ID," +
					" t.TALUK_NAME," +
					" s.SECTN_ID," +
					" s.SECTN_NAME," +
					" cc.CSTCTG_ID," +
					" cc.CSTCTG_NAME," +
					" pw.PWRK_CSTCTG_IDEN_FLAG," +
					" pw.PWRK_BENEFICIARY," +
					" pw.PWRK_REG_DATE," +
					" pw.PWRK_VILLAGE," +
					" pw.PWRK_ESTIMATE_NO," +
					" pw.PWRK_ESTIMATE_COST," +
					" pw.PWRK_ESTIMATE_DATE," +
					" pw.PWRK_INTIMATE_DATE, " +
					" pw.PWRK_STAGE_IDEN," +
					"pw.PWRK_RR_NO," +
					"pw.PWRK_DATE_OF_SERVICE" + " FROM program_works pw"
					+ " JOIN taluk t ON pw.PWRK_TALUK_ID = t.TALUK_ID"
					+ " JOIN section s ON pw.PWRK_SECTN_ID = s.SECTN_ID "
					+"JOIN sub_division sd ON s.SECTN_SUBDIV_ID = sd.SUBDIV_ID "
					+"JOIN division d ON sd.SUBDIV_DIV_ID = d.DIV_ID "
					+"JOIN circle c ON d.DIV_CIRCLE_ID = c.CRCL_ID "
					+"JOIN zone z ON c.CRCL_ZONE_ID = z.ZONE_ID"
					+ " JOIN caste_category cc ON pw.PWRK_CSTCTG_ID = cc.CSTCTG_ID"
					+ " WHERE pw.PWRK_ROW_STATUS = 0 "+sectionConstraint+" " 
					+ " AND pw."+dateConstraint+" " 
					+ " BETWEEN STR_TO_DATE('"+repBean.getFromDate()+"','%d/%m/%Y') " 
					+ "AND STR_TO_DATE('"+repBean.getToDate()+"','%d/%m/%Y') " 
					+ "and "+filterOnFlag+" ";    
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
				if(2 <= Integer.valueOf(resultSet.getString("PWRK_STAGE_IDEN"))){
					rep.setEstiNum(resultSet.getString(13));
					rep.setEstimateCost(resultSet.getDouble(14));
					rep.setEstiDate(dateFormat(resultSet.getString(15)));
				}
				
				if(3 <= Integer.valueOf(resultSet.getString("PWRK_STAGE_IDEN"))){
					rep.setIntimatnIssDate(dateFormat(resultSet.getString(16)));
					query = " SELECT d.DEPST_ID, d.DEPST_NAME, pd.PDPST_AMOUNT"
						+ " FROM deposit d" + " JOIN program_deposit pd"
						+ " ON pd.PDPST_DEPST_ID = d.DEPST_ID"
						+ " JOIN program_works pw"
						+ " ON pd.PDPST_PWRK_ID = pw.PWRK_ID"
						+ " WHERE pw.PWRK_ID = "+rep.getPrgWrId()+" " ;
					preparedStatement = connection.prepareStatement(query);
					ResultSet resultSet1 = null;
					resultSet1 = preparedStatement.executeQuery();
					List<DepositeBean> beans = new ArrayList<DepositeBean>();
					while (resultSet1.next()) {
						DepositeBean bean = new DepositeBean();
						bean.setId(resultSet1.getString(1));
						bean.setDepParticulars(resultSet1.getString(2));
						bean.setAmnt(resultSet1.getDouble(3));
						beans.add(bean);
					}
					rep.setDepositeBeans(beans);
				}
				if(4 <= Integer.valueOf(resultSet.getString("PWRK_STAGE_IDEN"))){
					List<StatusDateBean> sdBeans = new ArrayList<StatusDateBean>();
					
					query = "select ps.PSTS_STS_ID,ps.PSTS_STS_DATE " +
							"from program_status ps join program_works pw " +
							"ON ps.PSTS_PWRK_ID = pw.PWRK_ID join status s " +
							"ON ps.PSTS_STS_ID = s.STS_ID where ps.PSTS_PWRK_ID = "+rep.getPrgWrId()+" order by ps.PSTS_STS_ID";
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
							if(5 <= Integer.valueOf(resultSet.getString("PWRK_STAGE_IDEN"))){
								if(statusDateBean.getId().equals("4")){
									rep.setWrkCompleted(statusDateBean.getDate());
								}
							}
						}
					
					rep.setStatusDateBeans(sdBeans);
				}
				
				if(6 <= Integer.valueOf(resultSet.getString("PWRK_STAGE_IDEN"))){
					rep.setRrNumber(resultSet.getString(18));
					rep.setDateofService(dateFormat(resultSet.getString(19)));
				}
					
				rep.setPwStageIden(resultSet.getString(17));
				prgDetailsLst.add(rep);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			MysqlConnectionProvider.releaseConnection(resultSet, null, preparedStatement, connection);
		}
		return prgDetailsLst;
	}	
	
	
	
	
	
	public static String dateToString(Date pDate){
		DateFormat formatter ;
		formatter = new SimpleDateFormat("dd/MM/yyyy");
		String s = formatter.format(pDate);
		return s;

	}
	
	
	private String dateFormat(String date) {
		date = date.substring(0,10);
		String[] field = date.split("-");
		return field[2]+"/"+field[1]+"/"+field[0];
		}
}
