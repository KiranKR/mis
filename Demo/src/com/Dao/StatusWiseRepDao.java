package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Util.MysqlConnectionProvider;
import com.bean.ReportBean;

public class StatusWiseRepDao {
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	List<ReportBean> reportBeans = null;
	 List<ReportBean> dispReportBeans = new ArrayList<ReportBean>();
	 ReportBean bean = new ReportBean();
	 
public List<ReportBean> getData(String role, String roleBelogs){
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
			   " pw.PWRK_VILLAGE,"+
			   " pw.PWRK_REG_DATE,"+
		       " cc.CSTCTG_ID,"+
		       " cc.CSTCTG_NAME,"+
		       " pw.PWRK_ESTIMATE_COST,"+
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
		       " pw.PWRK_STAGE_IDEN "+
			   " FROM program_works pw"+
			   " JOIN caste_category cc"+
			   " ON pw.PWRK_CSTCTG_ID = cc.CSTCTG_ID"+
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
			   " ON pw.PWRK_TALUK_ID = t.TALUK_ID"+
			   " WHERE pw.PWRK_STAGE_IDEN > 1"+ wherClause +" order by s.SECTN_ID, sd.SUBDIV_ID, d.DIV_ID, c.CRCL_ID, z.ZONE_ID ";
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
				reportBean.setVillage(rs.getString(4));             
				reportBean.setDtOfRegtn(dateFormat(rs.getString(5)));           
				reportBean.setCastId(rs.getString(6));              
				reportBean.setCastName(rs.getString(7));            
				reportBean.setEstAmount(rs.getDouble(8));           
				reportBean.setSecId(rs.getString(9));               
				reportBean.setSecName(rs.getString(10));            
				reportBean.setSudDivId(rs.getString(11));           
				reportBean.setSudDivName(rs.getString(12));	        
				reportBean.setDivId(rs.getString(13));              
				reportBean.setDivName(rs.getString(14));            
				reportBean.setCircleId(rs.getString(15));           
				reportBean.setCircleName(rs.getString(16));         
				reportBean.setZoneId(rs.getString(17));             
				reportBean.setZoneName(rs.getString(18));           
				reportBean.setStageId(rs.getString(19));                                               
				reportBeans.add(reportBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(role.equals("1")){
			dispReportBeans = circleWise("first");
		}else if(role.equals("2")){
			dispReportBeans = divWise("first");
		}else if(role.equals("3")){
			dispReportBeans = subDivWise("first");
		}else if(role.equals("4")){
			dispReportBeans = secWise("first");
		}
		return dispReportBeans;
	}


public  List<ReportBean> secWise(String id) {

	 int wrkYetCnt =0;
	 int wrkTknCnt =0;
	 int wrkComCnt =0;
	 int serviceCnt =0;
	 double wrkYetAmt =0;
     double wrkTknAmt =0;
	 double wrkComAmt =0;
	 double serviceAmt =0;
	 
	 
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
					wrkYetCnt = 0;
					wrkTknCnt = 0;
					wrkComCnt = 0;
					serviceCnt = 0;
					wrkYetAmt = 0;
					wrkTknAmt = 0;
					wrkComAmt = 0;
					serviceAmt = 0;
					bean.setLable(repBean.getSecName());
					bean.setId(repBean.getSecId());
					bean.setLvl("5");
					if (Integer.parseInt(repBean.getStageId())<4) {
						wrkYetCnt = wrkYetCnt + 1;
						wrkYetAmt = wrkYetAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("4")) {
						wrkTknCnt = wrkTknCnt + 1;
						wrkTknAmt = wrkTknAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("5")) {
						wrkComCnt = wrkComCnt + 1;
						wrkComAmt = wrkComAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("6")) {
						serviceCnt = serviceCnt + 1;
						serviceAmt = serviceAmt + repBean.getEstAmount();
					}
					bean.setWrkYetCnt(wrkYetCnt);
					bean.setWrkTknCnt(wrkTknCnt);
					bean.setWrkComCnt(wrkComCnt);
					bean.setServiceCnt(serviceCnt);
					bean.setWrkYetAmt(wrkYetAmt);
					bean.setWrkTknAmt(wrkTknAmt);
					bean.setWrkComAmt(wrkComAmt);
					bean.setServiceAmt(serviceAmt);
				}else{
					if(!sId.equals(repBean.getSecId())){
						int ttl = wrkYetCnt+wrkTknCnt+wrkComCnt+serviceCnt;
						double ttlAmnt = wrkYetAmt+wrkTknAmt+wrkComAmt+serviceAmt;
						bean.setTtl(ttl);
						bean.setTtlamnt(ttlAmnt);
						dispReportBeans.add(bean);
						sId = repBean.getSecId();
						bean = new ReportBean();
						wrkYetCnt = 0;
						wrkTknCnt = 0;
						wrkComCnt = 0;
						serviceCnt = 0;
						wrkYetAmt = 0;
						wrkTknAmt = 0;
						wrkComAmt = 0;
						serviceAmt = 0;
						bean.setLable(repBean.getSecName());
						bean.setId(repBean.getSecId());
						bean.setLvl("5");
					}
					if (Integer.parseInt(repBean.getStageId())<4) {
						wrkYetCnt = wrkYetCnt + 1;
						wrkYetAmt = wrkYetAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("4")) {
						wrkTknCnt = wrkTknCnt + 1;
						wrkTknAmt = wrkTknAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("5")) {
						wrkComCnt = wrkComCnt + 1;
						wrkComAmt = wrkComAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("6")) {
						serviceCnt = serviceCnt + 1;
						serviceAmt = serviceAmt + repBean.getEstAmount();
					}
					bean.setWrkYetCnt(wrkYetCnt);
					bean.setWrkTknCnt(wrkTknCnt);
					bean.setWrkComCnt(wrkComCnt);
					bean.setServiceCnt(serviceCnt);
					bean.setWrkYetAmt(wrkYetAmt);
					bean.setWrkTknAmt(wrkTknAmt);
					bean.setWrkComAmt(wrkComAmt);
					bean.setServiceAmt(serviceAmt);				}
			}
			int ttl = wrkYetCnt+wrkTknCnt+wrkComCnt+serviceCnt;
			double ttlAmnt = wrkYetAmt+wrkTknAmt+wrkComAmt+serviceAmt;
			
			bean.setTtl(ttl);
			bean.setTtlamnt(ttlAmnt);
			
			dispReportBeans.add(bean);
			
			
			
			return dispReportBeans;
		}

public  List<ReportBean> subDivWise(String id) {

	int wrkYetCnt = 0;
	int wrkTknCnt = 0;
	int wrkComCnt = 0;
	int serviceCnt = 0;
	double wrkYetAmt = 0;
	double wrkTknAmt = 0;
	double wrkComAmt = 0;
	double serviceAmt = 0;
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
					wrkYetCnt = 0;
					wrkTknCnt = 0;
					wrkComCnt = 0;
					serviceCnt = 0;
					wrkYetAmt = 0;
					wrkTknAmt = 0;
					wrkComAmt = 0;
					serviceAmt = 0;
					bean.setLable(repBean.getSudDivName());
					bean.setId(repBean.getSudDivId());
					bean.setLvl("4");
					if (Integer.parseInt(repBean.getStageId())<4) {
						wrkYetCnt = wrkYetCnt + 1;
						wrkYetAmt = wrkYetAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("4")) {
						wrkTknCnt = wrkTknCnt + 1;
						wrkTknAmt = wrkTknAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("5")) {
						wrkComCnt = wrkComCnt + 1;
						wrkComAmt = wrkComAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("6")) {
						serviceCnt = serviceCnt + 1;
						serviceAmt = serviceAmt + repBean.getEstAmount();
					}
					bean.setWrkYetCnt(wrkYetCnt);
					bean.setWrkTknCnt(wrkTknCnt);
					bean.setWrkComCnt(wrkComCnt);
					bean.setServiceCnt(serviceCnt);
					bean.setWrkYetAmt(wrkYetAmt);
					bean.setWrkTknAmt(wrkTknAmt);
					bean.setWrkComAmt(wrkComAmt);
					bean.setServiceAmt(serviceAmt);
				}else{
					if(!sdId.equals(repBean.getSudDivId())){
						int ttl = wrkYetCnt+wrkTknCnt+wrkComCnt+serviceCnt;
						double ttlAmnt = wrkYetAmt+wrkTknAmt+wrkComAmt+serviceAmt;
						bean.setTtl(ttl);
						bean.setTtlamnt(ttlAmnt);
						dispReportBeans.add(bean);
						sdId = repBean.getSudDivId();
						bean = new ReportBean();
						wrkYetCnt = 0;
						wrkTknCnt = 0;
						wrkComCnt = 0;
						serviceCnt = 0;
						wrkYetAmt = 0;
						wrkTknAmt = 0;
						wrkComAmt = 0;
						serviceAmt = 0;
						bean.setLable(repBean.getSudDivName());
						bean.setId(repBean.getSudDivId());
						bean.setLvl("4");
					}
					if (Integer.parseInt(repBean.getStageId())<4) {
						wrkYetCnt = wrkYetCnt + 1;
						wrkYetAmt = wrkYetAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("4")) {
						wrkTknCnt = wrkTknCnt + 1;
						wrkTknAmt = wrkTknAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("5")) {
						wrkComCnt = wrkComCnt + 1;
						wrkComAmt = wrkComAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("6")) {
						serviceCnt = serviceCnt + 1;
						serviceAmt = serviceAmt + repBean.getEstAmount();
					}
					bean.setWrkYetCnt(wrkYetCnt);
					bean.setWrkTknCnt(wrkTknCnt);
					bean.setWrkComCnt(wrkComCnt);
					bean.setServiceCnt(serviceCnt);
					bean.setWrkYetAmt(wrkYetAmt);
					bean.setWrkTknAmt(wrkTknAmt);
					bean.setWrkComAmt(wrkComAmt);
					bean.setServiceAmt(serviceAmt);
				}
			}
			int ttl = wrkYetCnt+wrkTknCnt+wrkComCnt+serviceCnt;
			double ttlAmnt = wrkYetAmt+wrkTknAmt+wrkComAmt+serviceAmt;
			bean.setTtl(ttl);
			bean.setTtlamnt(ttlAmnt);
			dispReportBeans.add(bean);
			
			
			return dispReportBeans;
		}

public  List<ReportBean> divWise(String id) {
	int wrkYetCnt = 0;
	int wrkTknCnt = 0;
	int wrkComCnt = 0;
	int serviceCnt = 0;
	double wrkYetAmt = 0;
	double wrkTknAmt = 0;
	double wrkComAmt = 0;
	double serviceAmt = 0;
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
					wrkYetCnt = 0;
					wrkTknCnt = 0;
					wrkComCnt = 0;
					serviceCnt = 0;
					wrkYetAmt = 0;
					wrkTknAmt = 0;
					wrkComAmt = 0;
					serviceAmt = 0;
					bean.setLable(repBean.getDivName());
					bean.setId(repBean.getDivId());
					bean.setLvl("3");
					if (Integer.parseInt(repBean.getStageId())<4) {
						wrkYetCnt = wrkYetCnt + 1;
						wrkYetAmt = wrkYetAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("4")) {
						wrkTknCnt = wrkTknCnt + 1;
						wrkTknAmt = wrkTknAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("5")) {
						wrkComCnt = wrkComCnt + 1;
						wrkComAmt = wrkComAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("6")) {
						serviceCnt = serviceCnt + 1;
						serviceAmt = serviceAmt + repBean.getEstAmount();
					}
					bean.setWrkYetCnt(wrkYetCnt);
					bean.setWrkTknCnt(wrkTknCnt);
					bean.setWrkComCnt(wrkComCnt);
					bean.setServiceCnt(serviceCnt);
					bean.setWrkYetAmt(wrkYetAmt);
					bean.setWrkTknAmt(wrkTknAmt);
					bean.setWrkComAmt(wrkComAmt);
					bean.setServiceAmt(serviceAmt);
				}else{
					if(!dId.equals(repBean.getDivId())){
						int ttl = wrkYetCnt+wrkTknCnt+wrkComCnt+serviceCnt;
						double ttlAmnt = wrkYetAmt+wrkTknAmt+wrkComAmt+serviceAmt;
						bean.setTtl(ttl);
						bean.setTtlamnt(ttlAmnt);
						dispReportBeans.add(bean);
						dId = repBean.getDivId();
						bean = new ReportBean();
						wrkYetCnt = 0;
						wrkTknCnt = 0;
						wrkComCnt = 0;
						serviceCnt = 0;
						wrkYetAmt = 0;
						wrkTknAmt = 0;
						wrkComAmt = 0;
						serviceAmt = 0;
						bean.setLable(repBean.getDivName());
						bean.setId(repBean.getDivId());
						bean.setLvl("3");
					}
					if (Integer.parseInt(repBean.getStageId())<4) {
						wrkYetCnt = wrkYetCnt + 1;
						wrkYetAmt = wrkYetAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("4")) {
						wrkTknCnt = wrkTknCnt + 1;
						wrkTknAmt = wrkTknAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("5")) {
						wrkComCnt = wrkComCnt + 1;
						wrkComAmt = wrkComAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("6")) {
						serviceCnt = serviceCnt + 1;
						serviceAmt = serviceAmt + repBean.getEstAmount();
					}
					bean.setWrkYetCnt(wrkYetCnt);
					bean.setWrkTknCnt(wrkTknCnt);
					bean.setWrkComCnt(wrkComCnt);
					bean.setServiceCnt(serviceCnt);
					bean.setWrkYetAmt(wrkYetAmt);
					bean.setWrkTknAmt(wrkTknAmt);
					bean.setWrkComAmt(wrkComAmt);
					bean.setServiceAmt(serviceAmt);
				}
			}
			int ttl = wrkYetCnt+wrkTknCnt+wrkComCnt+serviceCnt;
			double ttlAmnt = wrkYetAmt+wrkTknAmt+wrkComAmt+serviceAmt;
			bean.setTtl(ttl);
			bean.setTtlamnt(ttlAmnt);
			dispReportBeans.add(bean);
			
			
			return dispReportBeans;
		}

public  List<ReportBean> circleWise(String id) {
	int wrkYetCnt = 0;
	int wrkTknCnt = 0;
	int wrkComCnt = 0;
	int serviceCnt = 0;
	double wrkYetAmt = 0;
	double wrkTknAmt = 0;
	double wrkComAmt = 0;
	double serviceAmt = 0;
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
					wrkYetCnt = 0;
					wrkTknCnt = 0;
					wrkComCnt = 0;
					serviceCnt = 0;
					wrkYetAmt = 0;
					wrkTknAmt = 0;
					wrkComAmt = 0;
					serviceAmt = 0;
					bean.setLable(repBean.getCircleName());
					bean.setId(repBean.getCircleId());
					bean.setLvl("2");
					if (Integer.parseInt(repBean.getStageId())<4) {
						wrkYetCnt = wrkYetCnt + 1;
						wrkYetAmt = wrkYetAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("4")) {
						wrkTknCnt = wrkTknCnt + 1;
						wrkTknAmt = wrkTknAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("5")) {
						wrkComCnt = wrkComCnt + 1;
						wrkComAmt = wrkComAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("6")) {
						serviceCnt = serviceCnt + 1;
						serviceAmt = serviceAmt + repBean.getEstAmount();
					}
					bean.setWrkYetCnt(wrkYetCnt);
					bean.setWrkTknCnt(wrkTknCnt);
					bean.setWrkComCnt(wrkComCnt);
					bean.setServiceCnt(serviceCnt);
					bean.setWrkYetAmt(wrkYetAmt);
					bean.setWrkTknAmt(wrkTknAmt);
					bean.setWrkComAmt(wrkComAmt);
					bean.setServiceAmt(serviceAmt);
				}else{
					if(!cId.equals(repBean.getCircleId())){
						int ttl = wrkYetCnt+wrkTknCnt+wrkComCnt+serviceCnt;
						double ttlAmnt = wrkYetAmt+wrkTknAmt+wrkComAmt+serviceAmt;
						bean.setTtl(ttl);
						bean.setTtlamnt(ttlAmnt);
						dispReportBeans.add(bean);
						cId = repBean.getCircleId();
						bean = new ReportBean();
						wrkYetCnt = 0;
						wrkTknCnt = 0;
						wrkComCnt = 0;
						serviceCnt = 0;
						wrkYetAmt = 0;
						wrkTknAmt = 0;
						wrkComAmt = 0;
						serviceAmt = 0;
						bean.setLable(repBean.getCircleName());
						bean.setId(repBean.getCircleId());
						bean.setLvl("2");
					}
					if (Integer.parseInt(repBean.getStageId())<4) {
						wrkYetCnt = wrkYetCnt + 1;
						wrkYetAmt = wrkYetAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("4")) {
						wrkTknCnt = wrkTknCnt + 1;
						wrkTknAmt = wrkTknAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("5")) {
						wrkComCnt = wrkComCnt + 1;
						wrkComAmt = wrkComAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("6")) {
						serviceCnt = serviceCnt + 1;
						serviceAmt = serviceAmt + repBean.getEstAmount();
					}
					bean.setWrkYetCnt(wrkYetCnt);
					bean.setWrkTknCnt(wrkTknCnt);
					bean.setWrkComCnt(wrkComCnt);
					bean.setServiceCnt(serviceCnt);
					bean.setWrkYetAmt(wrkYetAmt);
					bean.setWrkTknAmt(wrkTknAmt);
					bean.setWrkComAmt(wrkComAmt);
					bean.setServiceAmt(serviceAmt);
				}
			}
			int ttl = wrkYetCnt+wrkTknCnt+wrkComCnt+serviceCnt;
			double ttlAmnt = wrkYetAmt+wrkTknAmt+wrkComAmt+serviceAmt;
			bean.setTtl(ttl);
			bean.setTtlamnt(ttlAmnt);
			dispReportBeans.add(bean);
			
			
			return dispReportBeans;
		}
public  List<ReportBean> zoneWise(String id) {
	int wrkYetCnt = 0;
	int wrkTknCnt = 0;
	int wrkComCnt = 0;
	int serviceCnt = 0;
	double wrkYetAmt = 0;
	double wrkTknAmt = 0;
	double wrkComAmt = 0;
	double serviceAmt = 0;
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
					wrkYetCnt = 0;
					wrkTknCnt = 0;
					wrkComCnt = 0;
					serviceCnt = 0;
					wrkYetAmt = 0;
					wrkTknAmt = 0;
					wrkComAmt = 0;
					serviceAmt = 0;
					bean.setLable(repBean.getZoneName());
					bean.setId(repBean.getZoneId());
					bean.setLvl("1");
					if (Integer.parseInt(repBean.getStageId())<4) {
						wrkYetCnt = wrkYetCnt + 1;
						wrkYetAmt = wrkYetAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("4")) {
						wrkTknCnt = wrkTknCnt + 1;
						wrkTknAmt = wrkTknAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("5")) {
						wrkComCnt = wrkComCnt + 1;
						wrkComAmt = wrkComAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("6")) {
						serviceCnt = serviceCnt + 1;
						serviceAmt = serviceAmt + repBean.getEstAmount();
					}
					bean.setWrkYetCnt(wrkYetCnt);
					bean.setWrkTknCnt(wrkTknCnt);
					bean.setWrkComCnt(wrkComCnt);
					bean.setServiceCnt(serviceCnt);
					bean.setWrkYetAmt(wrkYetAmt);
					bean.setWrkTknAmt(wrkTknAmt);
					bean.setWrkComAmt(wrkComAmt);
					bean.setServiceAmt(serviceAmt);
				}else{
					if(!zId.equals(repBean.getZoneId())){
						int ttl = wrkYetCnt+wrkTknCnt+wrkComCnt+serviceCnt;
						double ttlAmnt = wrkYetAmt+wrkTknAmt+wrkComAmt+serviceAmt;
						bean.setTtl(ttl);
						bean.setTtlamnt(ttlAmnt);
						dispReportBeans.add(bean);
						zId = repBean.getZoneId();
						bean = new ReportBean();
						wrkYetCnt = 0;
						wrkTknCnt = 0;
						wrkComCnt = 0;
						serviceCnt = 0;
						wrkYetAmt = 0;
						wrkTknAmt = 0;
						wrkComAmt = 0;
						serviceAmt = 0;
						bean.setLable(repBean.getCircleName());
						bean.setId(repBean.getCircleId());
						bean.setLvl("1");
					}
					if (Integer.parseInt(repBean.getStageId())<4) {
						wrkYetCnt = wrkYetCnt + 1;
						wrkYetAmt = wrkYetAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("4")) {
						wrkTknCnt = wrkTknCnt + 1;
						wrkTknAmt = wrkTknAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("5")) {
						wrkComCnt = wrkComCnt + 1;
						wrkComAmt = wrkComAmt + repBean.getEstAmount();
					} else if (repBean.getStageId().equals("6")) {
						serviceCnt = serviceCnt + 1;
						serviceAmt = serviceAmt + repBean.getEstAmount();
					}
					bean.setWrkYetCnt(wrkYetCnt);
					bean.setWrkTknCnt(wrkTknCnt);
					bean.setWrkComCnt(wrkComCnt);
					bean.setServiceCnt(serviceCnt);
					bean.setWrkYetAmt(wrkYetAmt);
					bean.setWrkTknAmt(wrkTknAmt);
					bean.setWrkComAmt(wrkComAmt);
					bean.setServiceAmt(serviceAmt);
				}
			}
			int ttl = wrkYetCnt+wrkTknCnt+wrkComCnt+serviceCnt;
			double ttlAmnt = wrkYetAmt+wrkTknAmt+wrkComAmt+serviceAmt;
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
