package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.Util.MysqlConnectionProvider;
import com.bean.ReportBean;

public class StageWiseRepDao {
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	List<ReportBean> reportBeans = null;
	 List<ReportBean> dispReportBeans = new ArrayList<ReportBean>();
	 ReportBean bean = new ReportBean();
	 
public List<ReportBean> viewData(String role, String roleBelogs){
		String wherClause = "";
		if(role.equals("1")){
			wherClause = " and z.ZONE_ID = "+ roleBelogs;
		}else if(role.equals("2")){
			wherClause = " and c.CRCL_ID = "+ roleBelogs;
		}else if(role.equals("3")){
			wherClause = " and d.DIV_ID = "+ roleBelogs;
		}else if(role.equals("4")){
			wherClause = " and sd.SUBDIV_ID = "+ roleBelogs;
		}else if(role.equals("5")){
			wherClause = " and s.SECTN_ID = "+ roleBelogs;
		}
		
	
		reportBeans = new ArrayList<ReportBean>();
		ReportBean reportBean = null;
		try {
			String query = "SELECT pw.PWRK_ID,"+
					       " pw.PWRK_UID,"+
					       " pw.PWRK_BENEFICIARY,"+
					       " pw.PWRK_REG_DATE,"+
					       " pw.PWRK_VILLAGE,"+
					       " pw.PWRK_ESTIMATE_COST,"+
					       " pw.PWRK_STAGE_IDEN,"+
					       " s.SECTN_ID,"+
					       " s.SECTN_NAME,"+
					       " sd.SUBDIV_ID,"+
					       " sd.SUBDIV_NAME,"+
					       " d.DIV_ID,"+
					       " d.DIV_NAME,"+
					       " c.CRCL_ID,"+
					       " c.CRCL_NAME,"+
					       " z.ZONE_ID,"+
					       " z.ZONE_NAME,"+
					       " pw.PWRK_CSTCTG_ID"+
						   " FROM program_works pw"+
						   " JOIN section s"+
						   " ON pw.PWRK_SECTN_ID = s.SECTN_ID"+
						   " JOIN sub_division sd"+
						   " ON s.SECTN_SUBDIV_ID = sd.SUBDIV_ID"+
						   " JOIN division d"+
						   " ON sd.SUBDIV_DIV_ID = d.DIV_ID"+
						   " JOIN circle c"+
						   " ON d.DIV_CIRCLE_ID = c.CRCL_ID"+
						   " JOIN zone z"+
						   " ON c.CRCL_ZONE_ID = z.ZONE_ID"+
						   " JOIN taluk t"+
						   " ON pw.PWRK_TALUK_ID = t.TALUK_ID "+ wherClause +" order by pw.PWRK_ID,s.SECTN_ID,sd.SUBDIV_ID,d.DIV_ID,c.CRCL_ID,z.ZONE_ID";
			connection =  MysqlConnectionProvider.getNewConnection();
			preparedStatement = connection.prepareStatement(query);
			
			int i = 1;
			
			rs = preparedStatement.executeQuery();
			
			while(rs.next()){
				
				reportBean = new ReportBean();
				reportBean.setSlno(i++);
				reportBean.setPrgWrId(rs.getString(1));             
				reportBean.setUniqueId(rs.getString(2));            
				reportBean.setBeneficiaryName(rs.getString(3));     
				reportBean.setDtOfRegtn(dateFormat(rs.getString(4)));           
				reportBean.setVillage(rs.getString(5));             
				reportBean.setEstAmount(rs.getDouble(6));     
				reportBean.setStageId(rs.getString(7));
				int stage = Integer.parseInt(reportBean.getStageId());
				if(stage < 4){
					reportBean.setWrkStatus("Work Yet to be Taken");
				}else if(stage == 4){
					reportBean.setWrkStatus("Work under progress");
				}else if(stage == 5){
					reportBean.setWrkStatus("Work completed");
				}else if(stage == 6){
					reportBean.setWrkStatus("Service provided");
				}
				
				reportBean.setSecId(rs.getString(8));               
				reportBean.setSecName(rs.getString(9));            
				reportBean.setSudDivId(rs.getString(10));           
				reportBean.setSudDivName(rs.getString(11));	        
				reportBean.setDivId(rs.getString(12));              
				reportBean.setDivName(rs.getString(13));            
				reportBean.setCircleId(rs.getString(14));           
				reportBean.setCircleName(rs.getString(15));         
				reportBean.setZoneId(rs.getString(16));             
				reportBean.setZoneName(rs.getString(17));
				reportBean.setCastId(rs.getString(18));
				                                               
				reportBeans.add(reportBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		/*if(role.equals("1")){
			dispReportBeans = circleWise("first");
		}else if(role.equals("2")){
			dispReportBeans = divWise("first");
		}else if(role.equals("3")){
			dispReportBeans = subDivWise("first");
		}else if(role.equals("4")){
			dispReportBeans = secWise("first");
		}*/
		return reportBeans;
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
		SelectItem noneItem = new SelectItem("0", "All");

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
		MysqlConnectionProvider.releaseConnection(rs, null, pst, con);
	}
	return lsDDList;
}










public  List<ReportBean> secWise(String id) {

	int scCnt = 0;
	int stCnt = 0;
	int bcCnt = 0;
	int mineCnt = 0;
	double scttlamnt = 0;
	double stttlamnt = 0;
	double bcttlamnt = 0;
	double minettlamnt = 0;
	List<ReportBean> tempRepBean = new ArrayList<ReportBean>();
	dispReportBeans = new ArrayList<ReportBean>();
	if(id.equals("first")){
		tempRepBean = reportBeans;
	}else{
		for (ReportBean reportBean : reportBeans) {
			if(reportBean.getSudDivId().equals(id)){
				tempRepBean.add(reportBean);
			}
		}
	}
	//reportBeans = dao.viewData(" ");
	
	
		String sId = "";
			for (ReportBean repBean : tempRepBean){
				if(sId.equals("")){
					sId = repBean.getSecId();
					bean = new ReportBean();
					scCnt = 0;
					stCnt = 0;
					bcCnt = 0;
					mineCnt = 0;
					scttlamnt = 0;
					stttlamnt = 0;
					bcttlamnt = 0;
					minettlamnt = 0;
					bean.setLable(repBean.getSecName());
					bean.setId(repBean.getSecId());
					bean.setLvl("5");
					if (repBean.getCastId().equals("1")) {
						scCnt = scCnt + 1;
						scttlamnt = scttlamnt + repBean.getEstAmount();
					} else if (repBean.getCastId().equals("2")) {
						stCnt = stCnt + 1;
						stttlamnt = stttlamnt + repBean.getEstAmount();
					} else if (repBean.getCastId().equals("3")) {
						bcCnt = bcCnt + 1;
						bcttlamnt = bcttlamnt + repBean.getEstAmount();
					} else if (repBean.getCastId().equals("4")) {
						mineCnt = mineCnt + 1;
						minettlamnt = minettlamnt + repBean.getEstAmount();
					}
					bean.setScCnt(scCnt);
					bean.setStCnt(stCnt);
					bean.setBcCnt(bcCnt);
					bean.setMineCnt(mineCnt);
					bean.setScttlamnt(scttlamnt);
					bean.setStttlamnt(stttlamnt);
					bean.setBcttlamnt(bcttlamnt);
					bean.setMinettlamnt(minettlamnt);
				}else{
					if(!sId.equals(repBean.getSecId())){
						int ttl = scCnt+stCnt+bcCnt+mineCnt;
						double ttlAmnt = scttlamnt+stttlamnt+bcttlamnt+minettlamnt;
						bean.setTtl(ttl);
						bean.setTtlamnt(ttlAmnt);
						dispReportBeans.add(bean);
						sId = repBean.getSecId();
						bean = new ReportBean();
						scCnt = 0;
						stCnt = 0;
						bcCnt = 0;
						mineCnt = 0;
						scttlamnt = 0;
						stttlamnt = 0;
						bcttlamnt = 0;
						minettlamnt = 0;
						bean.setLable(repBean.getSecName());
						bean.setId(repBean.getSecId());
						bean.setLvl("5");
					}
						if (repBean.getCastId().equals("1")) {
							scCnt = scCnt + 1;
							scttlamnt = scttlamnt + repBean.getEstAmount();
						} else if (repBean.getCastId().equals("2")) {
							stCnt = stCnt + 1;
							stttlamnt = stttlamnt + repBean.getEstAmount();
						} else if (repBean.getCastId().equals("3")) {
							bcCnt = bcCnt + 1;
							bcttlamnt = bcttlamnt + repBean.getEstAmount();
						} else if (repBean.getCastId().equals("4")) {
							mineCnt = mineCnt + 1;
							minettlamnt = minettlamnt + repBean.getEstAmount();
						}
						bean.setScCnt(scCnt);
						bean.setStCnt(stCnt);
						bean.setBcCnt(bcCnt);
						bean.setMineCnt(mineCnt);
						bean.setScttlamnt(scttlamnt);
						bean.setStttlamnt(stttlamnt);
						bean.setBcttlamnt(bcttlamnt);
						bean.setMinettlamnt(minettlamnt);
				}
			}
			int ttl = scCnt+stCnt+bcCnt+mineCnt;
			double ttlAmnt = scttlamnt+stttlamnt+bcttlamnt+minettlamnt;
			bean.setTtl(ttl);
			bean.setTtlamnt(ttlAmnt);
			dispReportBeans.add(bean);
			
			
			
			return dispReportBeans;
		}

public  List<ReportBean> subDivWise(String id) {

	int scCnt = 0;
	int stCnt = 0;
	int bcCnt = 0;
	int mineCnt = 0;
	double scttlamnt = 0;
	double stttlamnt = 0;
	double bcttlamnt = 0;
	double minettlamnt = 0;
	List<ReportBean> tempRepBean = new ArrayList<ReportBean>();
	dispReportBeans = new ArrayList<ReportBean>();
	if(id.equals("first")){
		tempRepBean = reportBeans;
	}else{
		for (ReportBean reportBean : reportBeans) {
			if(reportBean.getDivId().equals(id)){
				tempRepBean.add(reportBean);
			}
		}
	}
	//reportBeans = dao.viewData(" ");
	
	
		String sdId = "";
			for (ReportBean repBean : tempRepBean){
				if(sdId.equals("")){
					sdId = repBean.getSudDivId();
					bean = new ReportBean();
					scCnt = 0;
					stCnt = 0;
					bcCnt = 0;
					mineCnt = 0;
					scttlamnt = 0;
					stttlamnt = 0;
					bcttlamnt = 0;
					minettlamnt = 0;
					bean.setLable(repBean.getSudDivName());
					bean.setId(repBean.getSudDivId());
					bean.setLvl("4");
					if (repBean.getCastId().equals("1")) {
						scCnt = scCnt + 1;
						scttlamnt = scttlamnt + repBean.getEstAmount();
					} else if (repBean.getCastId().equals("2")) {
						stCnt = stCnt + 1;
						stttlamnt = stttlamnt + repBean.getEstAmount();
					} else if (repBean.getCastId().equals("3")) {
						bcCnt = bcCnt + 1;
						bcttlamnt = bcttlamnt + repBean.getEstAmount();
					} else if (repBean.getCastId().equals("4")) {
						mineCnt = mineCnt + 1;
						minettlamnt = minettlamnt + repBean.getEstAmount();
					}
					bean.setScCnt(scCnt);
					bean.setStCnt(stCnt);
					bean.setBcCnt(bcCnt);
					bean.setMineCnt(mineCnt);
					bean.setScttlamnt(scttlamnt);
					bean.setStttlamnt(stttlamnt);
					bean.setBcttlamnt(bcttlamnt);
					bean.setMinettlamnt(minettlamnt);
				}else{
					if(!sdId.equals(repBean.getSudDivId())){
						int ttl = scCnt+stCnt+bcCnt+mineCnt;
						double ttlAmnt = scttlamnt+stttlamnt+bcttlamnt+minettlamnt;
						bean.setTtl(ttl);
						bean.setTtlamnt(ttlAmnt);
						dispReportBeans.add(bean);
						sdId = repBean.getSudDivId();
						bean = new ReportBean();
						scCnt = 0;
						stCnt = 0;
						bcCnt = 0;
						mineCnt = 0;
						scttlamnt = 0;
						stttlamnt = 0;
						bcttlamnt = 0;
						minettlamnt = 0;
						bean.setLable(repBean.getSudDivName());
						bean.setId(repBean.getSudDivId());
						bean.setLvl("4");
					}
						if (repBean.getCastId().equals("1")) {
							scCnt = scCnt + 1;
							scttlamnt = scttlamnt + repBean.getEstAmount();
						} else if (repBean.getCastId().equals("2")) {
							stCnt = stCnt + 1;
							stttlamnt = stttlamnt + repBean.getEstAmount();
						} else if (repBean.getCastId().equals("3")) {
							bcCnt = bcCnt + 1;
							bcttlamnt = bcttlamnt + repBean.getEstAmount();
						} else if (repBean.getCastId().equals("4")) {
							mineCnt = mineCnt + 1;
							minettlamnt = minettlamnt + repBean.getEstAmount();
						}
						bean.setScCnt(scCnt);
						bean.setStCnt(stCnt);
						bean.setBcCnt(bcCnt);
						bean.setMineCnt(mineCnt);
						bean.setScttlamnt(scttlamnt);
						bean.setStttlamnt(stttlamnt);
						bean.setBcttlamnt(bcttlamnt);
						bean.setMinettlamnt(minettlamnt);
				}
			}
			int ttl = scCnt+stCnt+bcCnt+mineCnt;
			double ttlAmnt = scttlamnt+stttlamnt+bcttlamnt+minettlamnt;
			bean.setTtl(ttl);
			bean.setTtlamnt(ttlAmnt);
			dispReportBeans.add(bean);
			return dispReportBeans;
		}

public  List<ReportBean> divWise(String id) {
	int scCnt = 0;
	int stCnt = 0;
	int bcCnt = 0;
	int mineCnt = 0;
	double scttlamnt = 0;
	double stttlamnt = 0;
	double bcttlamnt = 0;
	double minettlamnt = 0;
	List<ReportBean> tempRepBean = new ArrayList<ReportBean>();
	dispReportBeans = new ArrayList<ReportBean>();
	if(id.equals("first")){
		tempRepBean = reportBeans;
	}else{
		for (ReportBean reportBean : reportBeans) {
			if(reportBean.getCircleId().equals(id)){
				tempRepBean.add(reportBean);
			}
		}
	}
	//reportBeans = dao.viewData(" ");
	
	
		String dId = "";
			for (ReportBean repBean : tempRepBean){
				if(dId.equals("")){
					dId = repBean.getDivId();
					bean = new ReportBean();
					scCnt = 0;
					stCnt = 0;
					bcCnt = 0;
					mineCnt = 0;
					scttlamnt = 0;
					stttlamnt = 0;
					bcttlamnt = 0;
					minettlamnt = 0;
					bean.setLable(repBean.getDivName());
					bean.setId(repBean.getDivId());
					bean.setLvl("3");
					if (repBean.getCastId().equals("1")) {
						scCnt = scCnt + 1;
						scttlamnt = scttlamnt + repBean.getEstAmount();
					} else if (repBean.getCastId().equals("2")) {
						stCnt = stCnt + 1;
						stttlamnt = stttlamnt + repBean.getEstAmount();
					} else if (repBean.getCastId().equals("3")) {
						bcCnt = bcCnt + 1;
						bcttlamnt = bcttlamnt + repBean.getEstAmount();
					} else if (repBean.getCastId().equals("4")) {
						mineCnt = mineCnt + 1;
						minettlamnt = minettlamnt + repBean.getEstAmount();
					}
					bean.setScCnt(scCnt);
					bean.setStCnt(stCnt);
					bean.setBcCnt(bcCnt);
					bean.setMineCnt(mineCnt);
					bean.setScttlamnt(scttlamnt);
					bean.setStttlamnt(stttlamnt);
					bean.setBcttlamnt(bcttlamnt);
					bean.setMinettlamnt(minettlamnt);
				}else{
					if(!dId.equals(repBean.getDivId())){
						int ttl = scCnt+stCnt+bcCnt+mineCnt;
						double ttlAmnt = scttlamnt+stttlamnt+bcttlamnt+minettlamnt;
						bean.setTtl(ttl);
						bean.setTtlamnt(ttlAmnt);
						dispReportBeans.add(bean);
						dId = repBean.getDivId();
						bean = new ReportBean();
						scCnt = 0;
						stCnt = 0;
						bcCnt = 0;
						mineCnt = 0;
						scttlamnt = 0;
						stttlamnt = 0;
						bcttlamnt = 0;
						minettlamnt = 0;
						bean.setLable(repBean.getDivName());
						bean.setId(repBean.getDivId());
						bean.setLvl("3");
					}
						if (repBean.getCastId().equals("1")) {
							scCnt = scCnt + 1;
							scttlamnt = scttlamnt + repBean.getEstAmount();
						} else if (repBean.getCastId().equals("2")) {
							stCnt = stCnt + 1;
							stttlamnt = stttlamnt + repBean.getEstAmount();
						} else if (repBean.getCastId().equals("3")) {
							bcCnt = bcCnt + 1;
							bcttlamnt = bcttlamnt + repBean.getEstAmount();
						} else if (repBean.getCastId().equals("4")) {
							mineCnt = mineCnt + 1;
							minettlamnt = minettlamnt + repBean.getEstAmount();
						}
						bean.setScCnt(scCnt);
						bean.setStCnt(stCnt);
						bean.setBcCnt(bcCnt);
						bean.setMineCnt(mineCnt);
						bean.setScttlamnt(scttlamnt);
						bean.setStttlamnt(stttlamnt);
						bean.setBcttlamnt(bcttlamnt);
						bean.setMinettlamnt(minettlamnt);
				}
			}
			int ttl = scCnt+stCnt+bcCnt+mineCnt;
			double ttlAmnt = scttlamnt+stttlamnt+bcttlamnt+minettlamnt;
			bean.setTtl(ttl);
			bean.setTtlamnt(ttlAmnt);
			dispReportBeans.add(bean);
			
			
			return dispReportBeans;
		}

public  List<ReportBean> circleWise(String id) {
	int scCnt = 0;
	int stCnt = 0;
	int bcCnt = 0;
	int mineCnt = 0;
	double scttlamnt = 0;
	double stttlamnt = 0;
	double bcttlamnt = 0;
	double minettlamnt = 0;
	List<ReportBean> tempRepBean = new ArrayList<ReportBean>();
	dispReportBeans = new ArrayList<ReportBean>();
	if(id.equals("first")){
		tempRepBean = reportBeans;
	}else{
		for (ReportBean reportBean : reportBeans) {
			if(reportBean.getZoneId().equals(id)){
				tempRepBean.add(reportBean);
			}
		}
	}
	//reportBeans = dao.viewData(" ");
	
	
		String cId = "";
			for (ReportBean repBean : tempRepBean){
				if(cId.equals("")){
					cId = repBean.getCircleId();
					bean = new ReportBean();
					scCnt = 0;
					stCnt = 0;
					bcCnt = 0;
					mineCnt = 0;
					scttlamnt = 0;
					stttlamnt = 0;
					bcttlamnt = 0;
					minettlamnt = 0;
					bean.setLable(repBean.getCircleName());
					bean.setId(repBean.getCircleId());
					bean.setLvl("2");
					if (repBean.getCastId().equals("1")) {
						scCnt = scCnt + 1;
						scttlamnt = scttlamnt + repBean.getEstAmount();
					} else if (repBean.getCastId().equals("2")) {
						stCnt = stCnt + 1;
						stttlamnt = stttlamnt + repBean.getEstAmount();
					} else if (repBean.getCastId().equals("3")) {
						bcCnt = bcCnt + 1;
						bcttlamnt = bcttlamnt + repBean.getEstAmount();
					} else if (repBean.getCastId().equals("4")) {
						mineCnt = mineCnt + 1;
						minettlamnt = minettlamnt + repBean.getEstAmount();
					}
					bean.setScCnt(scCnt);
					bean.setStCnt(stCnt);
					bean.setBcCnt(bcCnt);
					bean.setMineCnt(mineCnt);
					bean.setScttlamnt(scttlamnt);
					bean.setStttlamnt(stttlamnt);
					bean.setBcttlamnt(bcttlamnt);
					bean.setMinettlamnt(minettlamnt);
				}else{
					if(!cId.equals(repBean.getCircleId())){
						int ttl = scCnt+stCnt+bcCnt+mineCnt;
						double ttlAmnt = scttlamnt+stttlamnt+bcttlamnt+minettlamnt;
						bean.setTtl(ttl);
						bean.setTtlamnt(ttlAmnt);
						dispReportBeans.add(bean);
						cId = repBean.getCircleId();
						bean = new ReportBean();
						scCnt = 0;
						stCnt = 0;
						bcCnt = 0;
						mineCnt = 0;
						scttlamnt = 0;
						stttlamnt = 0;
						bcttlamnt = 0;
						minettlamnt = 0;
						bean.setLable(repBean.getCircleName());
						bean.setId(repBean.getCircleId());
						bean.setLvl("2");
					}
						if (repBean.getCastId().equals("1")) {
							scCnt = scCnt + 1;
							scttlamnt = scttlamnt + repBean.getEstAmount();
						} else if (repBean.getCastId().equals("2")) {
							stCnt = stCnt + 1;
							stttlamnt = stttlamnt + repBean.getEstAmount();
						} else if (repBean.getCastId().equals("3")) {
							bcCnt = bcCnt + 1;
							bcttlamnt = bcttlamnt + repBean.getEstAmount();
						} else if (repBean.getCastId().equals("4")) {
							mineCnt = mineCnt + 1;
							minettlamnt = minettlamnt + repBean.getEstAmount();
						}
						bean.setScCnt(scCnt);
						bean.setStCnt(stCnt);
						bean.setBcCnt(bcCnt);
						bean.setMineCnt(mineCnt);
						bean.setScttlamnt(scttlamnt);
						bean.setStttlamnt(stttlamnt);
						bean.setBcttlamnt(bcttlamnt);
						bean.setMinettlamnt(minettlamnt);
				}
			}
			int ttl = scCnt+stCnt+bcCnt+mineCnt;
			double ttlAmnt = scttlamnt+stttlamnt+bcttlamnt+minettlamnt;
			bean.setTtl(ttl);
			bean.setTtlamnt(ttlAmnt);
			dispReportBeans.add(bean);
			
			
			return dispReportBeans;
		}
public  List<ReportBean> zoneWise(String id) {
	int scCnt = 0;
	int stCnt = 0;
	int bcCnt = 0;
	int mineCnt = 0;
	double scttlamnt = 0;
	double stttlamnt = 0;
	double bcttlamnt = 0;
	double minettlamnt = 0;
	List<ReportBean> tempRepBean = new ArrayList<ReportBean>();
	dispReportBeans = new ArrayList<ReportBean>();
	if(id.equals("first")){
		tempRepBean = reportBeans;
	}else{
		for (ReportBean reportBean : reportBeans) {
			if(reportBean.getZoneId().equals(id)){
				tempRepBean.add(reportBean);
			}
		}
	}
	//reportBeans = dao.viewData(" ");
	
	
		String zId = "";
			for (ReportBean repBean : tempRepBean){
				if(zId.equals("")){
					zId = repBean.getZoneId();
					bean = new ReportBean();
					scCnt = 0;
					stCnt = 0;
					bcCnt = 0;
					mineCnt = 0;
					scttlamnt = 0;
					stttlamnt = 0;
					bcttlamnt = 0;
					minettlamnt = 0;
					bean.setLable(repBean.getZoneName());
					bean.setId(repBean.getZoneId());
					bean.setLvl("1");
					if (repBean.getCastId().equals("1")) {
						scCnt = scCnt + 1;
						scttlamnt = scttlamnt + repBean.getEstAmount();
					} else if (repBean.getCastId().equals("2")) {
						stCnt = stCnt + 1;
						stttlamnt = stttlamnt + repBean.getEstAmount();
					} else if (repBean.getCastId().equals("3")) {
						bcCnt = bcCnt + 1;
						bcttlamnt = bcttlamnt + repBean.getEstAmount();
					} else if (repBean.getCastId().equals("4")) {
						mineCnt = mineCnt + 1;
						minettlamnt = minettlamnt + repBean.getEstAmount();
					}
					bean.setScCnt(scCnt);
					bean.setStCnt(stCnt);
					bean.setBcCnt(bcCnt);
					bean.setMineCnt(mineCnt);
					bean.setScttlamnt(scttlamnt);
					bean.setStttlamnt(stttlamnt);
					bean.setBcttlamnt(bcttlamnt);
					bean.setMinettlamnt(minettlamnt);
				}else{
					if(!zId.equals(repBean.getZoneId())){
						int ttl = scCnt+stCnt+bcCnt+mineCnt;
						double ttlAmnt = scttlamnt+stttlamnt+bcttlamnt+minettlamnt;
						bean.setTtl(ttl);
						bean.setTtlamnt(ttlAmnt);
						dispReportBeans.add(bean);
						zId = repBean.getZoneId();
						bean = new ReportBean();
						scCnt = 0;
						stCnt = 0;
						bcCnt = 0;
						mineCnt = 0;
						scttlamnt = 0;
						stttlamnt = 0;
						bcttlamnt = 0;
						minettlamnt = 0;
						bean.setLable(repBean.getCircleName());
						bean.setId(repBean.getCircleId());
						bean.setLvl("1");
					}
						if (repBean.getCastId().equals("1")) {
							scCnt = scCnt + 1;
							scttlamnt = scttlamnt + repBean.getEstAmount();
						} else if (repBean.getCastId().equals("2")) {
							stCnt = stCnt + 1;
							stttlamnt = stttlamnt + repBean.getEstAmount();
						} else if (repBean.getCastId().equals("3")) {
							bcCnt = bcCnt + 1;
							bcttlamnt = bcttlamnt + repBean.getEstAmount();
						} else if (repBean.getCastId().equals("4")) {
							mineCnt = mineCnt + 1;
							minettlamnt = minettlamnt + repBean.getEstAmount();
						}
						bean.setScCnt(scCnt);
						bean.setStCnt(stCnt);
						bean.setBcCnt(bcCnt);
						bean.setMineCnt(mineCnt);
						bean.setScttlamnt(scttlamnt);
						bean.setStttlamnt(stttlamnt);
						bean.setBcttlamnt(bcttlamnt);
						bean.setMinettlamnt(minettlamnt);
				}
			}
			int ttl = scCnt+stCnt+bcCnt+mineCnt;
			double ttlAmnt = scttlamnt+stttlamnt+bcttlamnt+minettlamnt;
			bean.setTtl(ttl);
			bean.setTtlamnt(ttlAmnt);
			dispReportBeans.add(bean);
			
			
			return dispReportBeans;
		}



private static String dateFormat(String date) {
	date = date.substring(0,10);
	String[] field = date.split("-");
	return field[2]+"/"+field[1]+"/"+field[0];
}

}
