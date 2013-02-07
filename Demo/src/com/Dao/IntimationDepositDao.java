package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.Util.MysqlConnectionProvider;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.DepositeBean;
import com.bean.PendingRep;

public class IntimationDepositDao {
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	
	public List<PendingRep> getAllData(String search,String criteria){
		String condition = "";
		List<PendingRep> pendingReps = new ArrayList<PendingRep>();
		PendingRep rep = null;
		String query = "";
		try {
		if(search.equals("") || criteria.equals("")){
			query = " SELECT pw.PWRK_ID,pw.PWRK_UID,"+
            " t.TALUK_ID,"+
            " t.TALUK_NAME,"+
            " s.SECTN_ID,"+
            " s.SECTN_NAME,"+
            " cc.CSTCTG_ID,"+
            " cc.CSTCTG_AKA,"+
            " pw.PWRK_CSTCTG_IDEN_FLAG,"+
            " pw.PWRK_BENEFICIARY,"+
            " pw.PWRK_REG_DATE,"+
            " pw.PWRK_VILLAGE,"+
            " pw.PWRK_ESTIMATE_NO,"+
            " pw.PWRK_ESTIMATE_COST,"+
            " pw.PWRK_ESTIMATE_DATE,"+
            " pw.PWRK_STAGE_IDEN "+
            " FROM program_works pw"+
            " JOIN taluk t"+
            " ON pw.PWRK_TALUK_ID = t.TALUK_ID"+
            " JOIN section s"+
            " ON pw.PWRK_SECTN_ID = s.SECTN_ID"+
            " JOIN caste_category cc"+
            " ON pw.PWRK_CSTCTG_ID = cc.CSTCTG_ID"+
            " where pw.PWRK_STAGE_IDEN >= 2 and pw.PWRK_ROW_STATUS = 0 " 
			+ "and s.SECTN_SUBDIV_ID = "+UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+" ";
		}else{
			if(criteria.equals("1")){
				condition = "pw.PWRK_UID like '%"+search+"%'";
			}else if (criteria.equals("2")) {
				condition = "pw.PWRK_BENEFICIARY like '%"+search+"%'";
			}
				   query = " SELECT pw.PWRK_ID,pw.PWRK_UID,"+
                           " t.TALUK_ID,"+
                           " t.TALUK_NAME,"+
                           " s.SECTN_ID,"+
                           " s.SECTN_NAME,"+
                           " cc.CSTCTG_ID,"+
                           " cc.CSTCTG_AKA,"+
                           " pw.PWRK_CSTCTG_IDEN_FLAG,"+
                           " pw.PWRK_BENEFICIARY,"+
                           " pw.PWRK_REG_DATE,"+
                           " pw.PWRK_VILLAGE,"+
                           " pw.PWRK_ESTIMATE_NO,"+
                           " pw.PWRK_ESTIMATE_COST,"+
                           " pw.PWRK_ESTIMATE_DATE,"+
                           " pw.PWRK_STAGE_IDEN "+
                           " FROM program_works pw"+
                           " JOIN taluk t"+
                           " ON pw.PWRK_TALUK_ID = t.TALUK_ID"+
                           " JOIN section s"+
                           " ON pw.PWRK_SECTN_ID = s.SECTN_ID"+
                           " JOIN caste_category cc"+
                           " ON pw.PWRK_CSTCTG_ID = cc.CSTCTG_ID"+
                           " where "+ condition +" and pw.PWRK_STAGE_IDEN >= 2 and pw.PWRK_ROW_STATUS = 0 " 
						 + "and s.SECTN_SUBDIV_ID = "+UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+" ";
			}
			connection =  MysqlConnectionProvider.getNewConnection();
			preparedStatement = connection.prepareStatement(query);
			
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				rep = new PendingRep();
				rep.setPrgWrId(rs.getString(1));
				rep.setUniqueId(rs.getString(2));
				rep.setTaluk(rs.getString(4));
				rep.setSectionId(rs.getString(5));
				rep.setSection(rs.getString(6));
				rep.setScheme(rs.getString(8));
				if(rs.getString(9).equals("1")){
					rep.setSubScheme("Individual");
				}else{
					rep.setSubScheme("Group");
				}
				rep.setBeneficiaryName(rs.getString(10));
				rep.setDtOfRegtn(dateFormat(rs.getString(11)));
				rep.setVillage(rs.getString(12));
				rep.setEstiNum(rs.getString(13));
				rep.setEstimateCost(rs.getDouble(14));
				rep.setEstiDate(rs.getString(15));
				rep.setPwStageIden(rs.getString(16));
				
				pendingReps.add(rep);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return pendingReps;
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
					+ " pw.PWRK_STAGE_IDEN,"+ " pw.PWRK_REG_FEE," + "pw.PWRK_RTNO " + " FROM program_works pw"
					+ " JOIN taluk t" + " ON pw.PWRK_TALUK_ID = t.TALUK_ID"
					+ " JOIN section s" + " ON pw.PWRK_SECTN_ID = s.SECTN_ID"
					+ " JOIN caste_category cc"
					+ " ON pw.PWRK_CSTCTG_ID = cc.CSTCTG_ID"
					+ " where pw.PWRK_STAGE_IDEN >= 2 and pw.PWRK_ID = " + pwId;
			connection = MysqlConnectionProvider.getNewConnection();
			preparedStatement = connection.prepareStatement(query);

			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				rep = new PendingRep();
				rep.setPrgWrId(rs.getString(1));
				rep.setUniqueId(rs.getString(2));
				rep.setTaluk(rs.getString(4));
				rep.setSectionId(rs.getString(5));
				rep.setSection(rs.getString(6));
				rep.setScheme(rs.getString(8));
				if (rs.getString(9).equals("1")) {
					rep.setSubScheme("Individual");
				} else {
					rep.setSubScheme("Group");
				}
				rep.setBeneficiaryName(rs.getString(10));
				rep.setDtOfRegtn(dateFormat(rs.getString(11)));
				rep.setVillage(rs.getString(12));
				rep.setEstiNum(rs.getString(13));
				rep.setEstimateCost(rs.getDouble(14));
				rep.setEstiDate(dateFormat(rs.getString(15)));
				rep.setPwStageIden(rs.getString(17));
				rep.setRegstrFee(rs.getDouble("PWRK_REG_FEE"));
				rep.setRegstrNo(rs.getString("PWRK_RTNO"));

				if (rs.getString(17).equals("2")) {
					rep.setDepositeBeans(getDepositLst());
				} else {
					rep.setIntimatnIssDate(dateFormat(rs.getString(16)));
					query = " SELECT d.DEPST_ID, d.DEPST_NAME, pd.PDPST_AMOUNT, pd.PDPST_APLICABLE_YN"
							+ " FROM deposit d" + " JOIN program_deposit pd"
							+ " ON pd.PDPST_DEPST_ID = d.DEPST_ID"
							+ " JOIN program_works pw"
							+ " ON pd.PDPST_PWRK_ID = pw.PWRK_ID"
							+ " WHERE pw.PWRK_ID = " + pwId;
					preparedStatement = connection.prepareStatement(query);
					ResultSet rs1 = null;
					rs1 = preparedStatement.executeQuery();
					List<DepositeBean> beans = new ArrayList<DepositeBean>();
					while (rs1.next()) {
						DepositeBean bean = new DepositeBean();
						bean.setId(rs1.getString(1));
						bean.setDepParticulars(rs1.getString(2));
						bean.setAmnt(rs1.getDouble(3));
						if(rs1.getInt("PDPST_APLICABLE_YN")==0){
							bean.setAmtChecked(false);
						}else if(rs1.getInt("PDPST_APLICABLE_YN")==1){
							bean.setAmtChecked(true);
						}
						beans.add(bean);
					}
					rep.setDepositeBeans(beans);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
		   MysqlConnectionProvider.releaseConnection(rs, null, preparedStatement, connection);
		}
		return rep;
	}
	
	public boolean depositUpdate(PendingRep rep){
		boolean updated = true;
		List<DepositeBean> beans = null;
		beans = rep.getDepositeBeans();
		String query = "";
		int amtChecked =0;
		try {
			connection =  MysqlConnectionProvider.getNewConnection();
			connection.setAutoCommit(false);
			if(Integer.parseInt(rep.getPwStageIden())>=3){
				for (DepositeBean bean : beans) {
					if(bean.isAmtChecked()==false){
						amtChecked =0;
					}else{
						amtChecked =1;
					}
					query = "update program_deposit SET PDPST_AMOUNT = "+bean.getAmnt()+", PDPST_APLICABLE_YN = "+amtChecked+"  WHERE PDPST_PWRK_ID = "+rep.getPrgWrId()+" and PDPST_DEPST_ID = "+bean.getId()+" ";
					preparedStatement = connection.prepareStatement(query);
					preparedStatement.execute();
				}
			}else{
			for (DepositeBean bean : beans) {
				if(bean.isAmtChecked()==false){
					amtChecked =0;
				}else{
					amtChecked =1;
				}
				query = "insert into program_deposit (PDPST_PWRK_ID,PDPST_DEPST_ID,PDPST_AMOUNT,PDPST_ROW_STATUS,PDPST_APLICABLE_YN) VALUES ("+rep.getPrgWrId()+","+bean.getId()+","+bean.getAmnt()+",0,"+amtChecked+")";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.execute();
			}
			}
			query = "update program_works SET PWRK_STAGE_IDEN = 3,PWRK_INTIMATE_DATE = STR_TO_DATE('"+rep.getIntimatnIssDate()+"', '%d/%m/%Y') WHERE PWRK_ID = " + rep.getPrgWrId();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
		 MysqlConnectionProvider.releaseConnection(null, null, preparedStatement, connection);
		}
		return updated;
	}

	public List<DepositeBean> getDepositLst(){
		List<DepositeBean> depositeBeans = new ArrayList<DepositeBean>();
		DepositeBean bean = null;
		ResultSet rs2 = null;
		try {
			String query = " select d.DEPST_ID,d.DEPST_NAME from deposit d where d.DEPST_ROW_STATUS = 0";
			connection =  MysqlConnectionProvider.getNewConnection();
			preparedStatement = connection.prepareStatement(query);
			
			rs2 = preparedStatement.executeQuery();
			while (rs2.next()) {
				bean = new DepositeBean();
				bean.setId(rs2.getString(1));
				bean.setDepParticulars(rs2.getString(2));
				depositeBeans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
		 MysqlConnectionProvider.releaseConnection(rs2, null, preparedStatement, connection);
		}
		return depositeBeans;
	}
	
	public static List getDropDownList(String tableName, String orderById,String dbFldId, String dbFldValue, String whrClause,int skpDefaultVal) throws SQLException, ClassNotFoundException {
		
		String qry = "SELECT * FROM  " + tableName + " "
				+ whrClause + " ORDER BY " + orderById + "";
		// System.out.println(qry);
		List lsDDList = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = MysqlConnectionProvider.getNewConnection();
			pst = con.prepareStatement(qry);
			rs = pst.executeQuery();
			SelectItem noneItem = new SelectItem("0", "Select");

			if (skpDefaultVal != 1) {
				lsDDList.add(noneItem);
			}
			while (rs.next()) {
				noneItem = new SelectItem(rs.getString(dbFldId),
						rs.getString(dbFldValue));
				lsDDList.add(noneItem);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			new MysqlConnectionProvider().releaseConnection(rs, null, pst, con);
		}
		return lsDDList;
	}
	


private String dateFormat(String date) {
	date = date.substring(0,10);
	String[] field = date.split("-");
	return field[2]+"/"+field[1]+"/"+field[0];
}

}
