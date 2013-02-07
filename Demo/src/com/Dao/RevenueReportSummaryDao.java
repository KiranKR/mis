package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.Util.AppUtil;
import com.Util.MysqlConnectionProvider;
import com.bean.RevenueReportBeanSummary;


public class RevenueReportSummaryDao {

	RevenueReportBeanSummary bean = null;
	List<RevenueReportBeanSummary> revenueReportBeanList = null;
	List<RevenueReportBeanSummary> disprevenueReportBeanList = new ArrayList<RevenueReportBeanSummary>();

	public List<RevenueReportBeanSummary> viewData(String role,
			String roleBelong, String fromYearMnth, String toYearMnth) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		String wherClause = "";
		if (role.equals("1")) {
			wherClause = " and z.ZONE_ID = " + roleBelong;
		} else if (role.equals("2")) {
			wherClause = " and c.CRCL_ID = " + roleBelong;
		} else if (role.equals("3")) {
			wherClause = " and d.DIV_ID = " + roleBelong;
		} else if (role.equals("4")) {
			wherClause = " and sd.SUBDIV_ID = " + roleBelong;
		} else if (role.equals("5")) {
			wherClause = " and s.SECTN_ID = " + roleBelong;
		}

		revenueReportBeanList = new ArrayList<RevenueReportBeanSummary>();
		RevenueReportBeanSummary revenueReportBean = null;

		try {
			con = MysqlConnectionProvider.getNewConnection();
			String query = "SELECT r.REV_TOTAL_INSTALATION AS noOfInstall, "
					+ "r.REV_INPUT_ENERGY AS inEnergy, "
					+ "r.REV_DCB_ENERGY AS dcbEnergy, "
					+ "r.REV_DCB_OB AS openBalance, "
					+ "r.REV_DEMAND AS demand, "
					+ "r.REV_COLLECTION AS collection, "
					+ "r.REV_DCB_CB AS closingBalance, "
					+ "r.REV_REMARKS AS reason, " + "th.TH_MONTH AS month, "
					+ "s.SECTN_ID AS secId, " + "s.SECTN_NAME AS secName, "
					+ "sd.SUBDIV_ID AS subDivId, "
					+ "sd.SUBDIV_NAME AS suDivName, " + "d.DIV_ID AS divId, "
					+ "d.DIV_NAME AS divName, " + "c.CRCL_ID AS cirId, "
					+ "c.CRCL_NAME AS cirName, " + "z.ZONE_ID AS zonId, "
					+ "z.ZONE_NAME AS zonName " + "FROM tran_head th "
					+ "JOIN revenue r " + "ON r.REV_TH_ID = th.TH_ID "
					+ "JOIN section s " + "ON th.TH_SECTN_ID = s.SECTN_ID "
					+ "JOIN sub_division sd "
					+ "ON s.SECTN_SUBDIV_ID = sd.SUBDIV_ID "
					+ "JOIN division d " + "ON sd.SUBDIV_DIV_ID = d.DIV_ID "
					+ "JOIN circle c " + "ON d.DIV_CIRCLE_ID = c.CRCL_ID "
					+ "JOIN zone z " + "ON c.CRCL_ZONE_ID = z.ZONE_ID "
					+ "WHERE th.TH_IDEN_FLAG = 4" + wherClause
					+ " AND concat(th.TH_YEAR, th.TH_MONTH) >= "
					+ fromYearMnth + " "
					+ " AND concat(th.TH_YEAR, th.TH_MONTH) <= " + toYearMnth+ " " 
					+ " ORDER BY s.SECTN_ID,sd.SUBDIV_ID,d.DIV_ID,c.CRCL_ID,z.ZONE_ID ";

			System.out.println(query);
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			int i=1;
			while (rs.next()) {
				

				revenueReportBean = new RevenueReportBeanSummary();
			
				revenueReportBean.setZoneId(rs.getString("zonId"));
				revenueReportBean.setZoneName(rs.getString("zonName"));
				revenueReportBean.setCircleId(rs.getString("cirId"));
				revenueReportBean.setCircleName(rs.getString("cirName"));
				revenueReportBean.setDivId(rs.getString("divId"));
				revenueReportBean.setDivName(rs.getString("divName"));
				revenueReportBean.setSudDivId(rs.getString("subDivId"));
				revenueReportBean.setSudDivName(rs.getString("suDivName"));
				revenueReportBean.setSecId(rs.getString("secId"));
				revenueReportBean.setSecName(rs.getString("secName"));

				revenueReportBean.setTotNoOfInstall(rs.getInt("noOfInstall"));
				revenueReportBean.setInputEnergy(rs.getInt("inEnergy"));
				revenueReportBean.setDcbEnergy(rs.getInt("dcbEnergy"));
				revenueReportBean.setOpenBal(rs.getInt("openBalance"));
				revenueReportBean.setDemand(rs.getDouble("demand"));
				revenueReportBean.setCollection(rs.getDouble("collection"));
				revenueReportBean.setClosingBal(rs.getDouble("closingBalance"));
				revenueReportBean.setReason(rs.getString("reason"));
				revenueReportBean.setMonth(AppUtil.getMonth(rs
						.getString("month")));
				revenueReportBean.setLvl("5");
				revenueReportBeanList.add(revenueReportBean);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			MysqlConnectionProvider.releaseConnection(rs, null, pst, con);
		}



		if (role.equals("1")) {
			disprevenueReportBeanList = zoneWise("first","0");
		
			
		}

		else if (role.equals("2")) {
			disprevenueReportBeanList = circleWise("first","0");
		

		} else if (role.equals("3")) {
			disprevenueReportBeanList = divisionWise("first","0");
			

		} else if (role.equals("4")) {
			disprevenueReportBeanList = subDivisionWise("first","0");
			

		}
		else if(role.equals("5"))
		{
			disprevenueReportBeanList = sectionWise("first","0");
		}

		return disprevenueReportBeanList;
	}

	public List<RevenueReportBeanSummary> sectionWise(String id,String lvl) {
		
		int totalNoOfInstallation = 0;
		int totalInputEnergy = 0;
		int totalEnergySoldDCB = 0;

		double totalOpeningBalance = 0;
		double totalDemand = 0;
		double totalCollection = 0;
		double totalClosingBalance = 0;

		List<RevenueReportBeanSummary> tempRevenueBeanList = new ArrayList<RevenueReportBeanSummary>();
		disprevenueReportBeanList = new ArrayList<RevenueReportBeanSummary>();

		if (id == "first") {
			tempRevenueBeanList = revenueReportBeanList;
		}

		else if(lvl.equals("4")){
			for (RevenueReportBeanSummary revenueReportBeanSummary : revenueReportBeanList) {
				if (revenueReportBeanSummary.getSudDivId().equals(id)) {
					tempRevenueBeanList.add(revenueReportBeanSummary);
				}
				
				

			}
		}
		
		else if(lvl.equals("5"))
		{
			
			for (RevenueReportBeanSummary revenueReportBeanSummary : revenueReportBeanList) {
				if (revenueReportBeanSummary.getSecId().equals(id)) {
					tempRevenueBeanList.add(revenueReportBeanSummary);
				}
				

			}
			
		}

		String sectionId = "";
		for (RevenueReportBeanSummary revenueReportBeanSummary : tempRevenueBeanList) {
			if (sectionId == "") {

				bean = new RevenueReportBeanSummary();
				sectionId = revenueReportBeanSummary.getSecId();

				bean.setId(revenueReportBeanSummary.getSecId());
				bean.setLabel(revenueReportBeanSummary.getSecName());
				bean.setLvl("5");

				totalNoOfInstallation += revenueReportBeanSummary.getTotNoOfInstall();
				totalInputEnergy += revenueReportBeanSummary.getInputEnergy();
				totalEnergySoldDCB += revenueReportBeanSummary.getDcbEnergy();

				totalOpeningBalance += revenueReportBeanSummary.getOpenBal();
				totalDemand += revenueReportBeanSummary.getDemand();
				totalCollection += revenueReportBeanSummary.getCollection();
				totalClosingBalance += revenueReportBeanSummary.getClosingBal();

				bean.setTotalNoOfInstallation(totalNoOfInstallation);
				bean.setTotalInputEnergy(totalInputEnergy);
				bean.setTotalEnergySoldDCB(totalEnergySoldDCB);
				bean.setTotalOpeningBalance(totalOpeningBalance);
				bean.setTotalDemand(totalDemand);
				bean.setTotalCollection(totalCollection);
				bean.setTotalClosingBalance(totalClosingBalance);

				disprevenueReportBeanList.add(bean);

			}

			else if (sectionId.equals(revenueReportBeanSummary.getSecId())) {
				
				totalNoOfInstallation += revenueReportBeanSummary.getTotNoOfInstall();
				totalInputEnergy += revenueReportBeanSummary.getInputEnergy();
				totalEnergySoldDCB += revenueReportBeanSummary.getDcbEnergy();

				totalOpeningBalance += revenueReportBeanSummary.getOpenBal();
				totalDemand += revenueReportBeanSummary.getDemand();
				totalCollection += revenueReportBeanSummary.getCollection();
				totalClosingBalance += revenueReportBeanSummary.getClosingBal();

				bean.setTotalNoOfInstallation(totalNoOfInstallation);
				bean.setTotalInputEnergy(totalInputEnergy);
				bean.setTotalEnergySoldDCB(totalEnergySoldDCB);
				bean.setTotalOpeningBalance(totalOpeningBalance);
				bean.setTotalDemand(totalDemand);
				bean.setTotalCollection(totalCollection);
				bean.setTotalClosingBalance(totalClosingBalance);

			}

			else if (!sectionId.equals(revenueReportBeanSummary.getSecId())) {
				totalNoOfInstallation = 0;
				totalInputEnergy = 0;
				totalEnergySoldDCB = 0;
				totalOpeningBalance = 0;
				totalDemand = 0;
				totalCollection = 0;
				totalClosingBalance = 0;

				bean = new RevenueReportBeanSummary();
				sectionId = revenueReportBeanSummary.getSecId();

				bean.setId(revenueReportBeanSummary.getSecId());
				bean.setLabel(revenueReportBeanSummary.getSecName());
				bean.setLvl("5");

				totalNoOfInstallation += revenueReportBeanSummary.getTotNoOfInstall();
				totalInputEnergy += revenueReportBeanSummary.getInputEnergy();
				totalEnergySoldDCB += revenueReportBeanSummary.getDcbEnergy();

				totalOpeningBalance += revenueReportBeanSummary.getOpenBal();
				totalDemand += revenueReportBeanSummary.getDemand();
				totalCollection += revenueReportBeanSummary.getCollection();
				totalClosingBalance += revenueReportBeanSummary.getClosingBal();

				bean.setTotalNoOfInstallation(totalNoOfInstallation);
				bean.setTotalInputEnergy(totalInputEnergy);
				bean.setTotalEnergySoldDCB(totalEnergySoldDCB);
				bean.setTotalOpeningBalance(totalOpeningBalance);
				bean.setTotalDemand(totalDemand);
				bean.setTotalCollection(totalCollection);
				bean.setTotalClosingBalance(totalClosingBalance);
				disprevenueReportBeanList.add(bean);

			}

		}

		return disprevenueReportBeanList;

	}
	
	
	public List<RevenueReportBeanSummary> subDivisionWise(String id,String lvl)
	{
		
		int totalNoOfInstallation = 0;
		int totalInputEnergy = 0;
		int totalEnergySoldDCB = 0;

		double totalOpeningBalance = 0;
		double totalDemand = 0;
		double totalCollection = 0;
		double totalClosingBalance = 0;

		List<RevenueReportBeanSummary> tempRevenueBeanList = new ArrayList<RevenueReportBeanSummary>();
		disprevenueReportBeanList = new ArrayList<RevenueReportBeanSummary>();

		if (id == "first") {
			tempRevenueBeanList = revenueReportBeanList;
		}

		else if(lvl.equals("3")){
			for (RevenueReportBeanSummary revenueReportBeanSummary : revenueReportBeanList) {
				if (revenueReportBeanSummary.getDivId().equals(id)) {
					tempRevenueBeanList.add(revenueReportBeanSummary);
				}
				

			}
		}
		
		else if(lvl.equals("4"))
		{
			
			for (RevenueReportBeanSummary revenueReportBeanSummary : revenueReportBeanList) {
				if (revenueReportBeanSummary.getSudDivId().equals(id)) {
					tempRevenueBeanList.add(revenueReportBeanSummary);
				}
				

			}
			
		}

		String subDivisionId = "";
		for (RevenueReportBeanSummary revenueReportBeanSummary : tempRevenueBeanList) {
			if (subDivisionId == "") {

				bean = new RevenueReportBeanSummary();
				subDivisionId = revenueReportBeanSummary.getSudDivId();

				bean.setId(revenueReportBeanSummary.getSudDivId());
				bean.setLabel(revenueReportBeanSummary.getSudDivName());
				bean.setLvl("4");

				totalNoOfInstallation += revenueReportBeanSummary.getTotNoOfInstall();
				totalInputEnergy += revenueReportBeanSummary.getInputEnergy();
				totalEnergySoldDCB += revenueReportBeanSummary.getDcbEnergy();

				totalOpeningBalance += revenueReportBeanSummary.getOpenBal();
				totalDemand += revenueReportBeanSummary.getDemand();
				totalCollection += revenueReportBeanSummary.getCollection();
				totalClosingBalance += revenueReportBeanSummary.getClosingBal();

				bean.setTotalNoOfInstallation(totalNoOfInstallation);
				bean.setTotalInputEnergy(totalInputEnergy);
				bean.setTotalEnergySoldDCB(totalEnergySoldDCB);
				bean.setTotalOpeningBalance(totalOpeningBalance);
				bean.setTotalDemand(totalDemand);
				bean.setTotalCollection(totalCollection);
				bean.setTotalClosingBalance(totalClosingBalance);

				disprevenueReportBeanList.add(bean);

			}

			else if (subDivisionId.equals(revenueReportBeanSummary.getSudDivId())) {
				
				totalNoOfInstallation += revenueReportBeanSummary.getTotNoOfInstall();
				totalInputEnergy += revenueReportBeanSummary.getInputEnergy();
				totalEnergySoldDCB += revenueReportBeanSummary.getDcbEnergy();

				totalOpeningBalance += revenueReportBeanSummary.getOpenBal();
				totalDemand += revenueReportBeanSummary.getDemand();
				totalCollection += revenueReportBeanSummary.getCollection();
				totalClosingBalance += revenueReportBeanSummary.getClosingBal();

				bean.setTotalNoOfInstallation(totalNoOfInstallation);
				bean.setTotalInputEnergy(totalInputEnergy);
				bean.setTotalEnergySoldDCB(totalEnergySoldDCB);
				bean.setTotalOpeningBalance(totalOpeningBalance);
				bean.setTotalDemand(totalDemand);
				bean.setTotalCollection(totalCollection);
				bean.setTotalClosingBalance(totalClosingBalance);

			}

			else if (!subDivisionId.equals(revenueReportBeanSummary.getSudDivId())) {
				totalNoOfInstallation = 0;
				totalInputEnergy = 0;
				totalEnergySoldDCB = 0;
				totalOpeningBalance = 0;
				totalDemand = 0;
				totalCollection = 0;
				totalClosingBalance = 0;

				bean = new RevenueReportBeanSummary();
				subDivisionId = revenueReportBeanSummary.getSudDivId();

				bean.setId(revenueReportBeanSummary.getSudDivId());
				bean.setLabel(revenueReportBeanSummary.getSudDivName());
				bean.setLvl("4");

				totalNoOfInstallation += revenueReportBeanSummary.getTotNoOfInstall();
				totalInputEnergy += revenueReportBeanSummary.getInputEnergy();
				totalEnergySoldDCB += revenueReportBeanSummary.getDcbEnergy();

				totalOpeningBalance += revenueReportBeanSummary.getOpenBal();
				totalDemand += revenueReportBeanSummary.getDemand();
				totalCollection += revenueReportBeanSummary.getCollection();
				totalClosingBalance += revenueReportBeanSummary.getClosingBal();

				bean.setTotalNoOfInstallation(totalNoOfInstallation);
				bean.setTotalInputEnergy(totalInputEnergy);
				bean.setTotalEnergySoldDCB(totalEnergySoldDCB);
				bean.setTotalOpeningBalance(totalOpeningBalance);
				bean.setTotalDemand(totalDemand);
				bean.setTotalCollection(totalCollection);
				bean.setTotalClosingBalance(totalClosingBalance);
				disprevenueReportBeanList.add(bean);

			}

		}

		return disprevenueReportBeanList;


	}
	
	
	public List<RevenueReportBeanSummary> divisionWise(String id,String lvl)
	{
		
		int totalNoOfInstallation = 0;
		int totalInputEnergy = 0;
		int totalEnergySoldDCB = 0;

		double totalOpeningBalance = 0;
		double totalDemand = 0;
		double totalCollection = 0;
		double totalClosingBalance = 0;

		List<RevenueReportBeanSummary> tempRevenueBeanList = new ArrayList<RevenueReportBeanSummary>();
		disprevenueReportBeanList = new ArrayList<RevenueReportBeanSummary>();

		if (id == "first") {
			tempRevenueBeanList = revenueReportBeanList;
		}

		else if(lvl.equals("2")) {
			for (RevenueReportBeanSummary revenueReportBeanSummary : revenueReportBeanList) {
				if (revenueReportBeanSummary.getCircleId().equals(id)) {
					tempRevenueBeanList.add(revenueReportBeanSummary);
				}
				

			}
		}
		
		else if(lvl.equals("3"))
		{
			
			for (RevenueReportBeanSummary revenueReportBeanSummary : revenueReportBeanList) {
				if (revenueReportBeanSummary.getDivId().equals(id)) {
					tempRevenueBeanList.add(revenueReportBeanSummary);
				}
				

			}
			
		}

		String divisionId = "";
		for (RevenueReportBeanSummary revenueReportBeanSummary : tempRevenueBeanList) {
			if (divisionId == "") {

				bean = new RevenueReportBeanSummary();
				divisionId = revenueReportBeanSummary.getDivId();

				bean.setId(revenueReportBeanSummary.getDivId());
				bean.setLabel(revenueReportBeanSummary.getDivName());
				bean.setLvl("3");

				totalNoOfInstallation += revenueReportBeanSummary.getTotNoOfInstall();
				totalInputEnergy += revenueReportBeanSummary.getInputEnergy();
				totalEnergySoldDCB += revenueReportBeanSummary.getDcbEnergy();

				totalOpeningBalance += revenueReportBeanSummary.getOpenBal();
				totalDemand += revenueReportBeanSummary.getDemand();
				totalCollection += revenueReportBeanSummary.getCollection();
				totalClosingBalance += revenueReportBeanSummary.getClosingBal();

				bean.setTotalNoOfInstallation(totalNoOfInstallation);
				bean.setTotalInputEnergy(totalInputEnergy);
				bean.setTotalEnergySoldDCB(totalEnergySoldDCB);
				bean.setTotalOpeningBalance(totalOpeningBalance);
				bean.setTotalDemand(totalDemand);
				bean.setTotalCollection(totalCollection);
				bean.setTotalClosingBalance(totalClosingBalance);

				disprevenueReportBeanList.add(bean);

			}

			else if (divisionId.equals(revenueReportBeanSummary.getDivId())) {
				
				totalNoOfInstallation += revenueReportBeanSummary.getTotNoOfInstall();
				totalInputEnergy += revenueReportBeanSummary.getInputEnergy();
				totalEnergySoldDCB += revenueReportBeanSummary.getDcbEnergy();

				totalOpeningBalance += revenueReportBeanSummary.getOpenBal();
				totalDemand += revenueReportBeanSummary.getDemand();
				totalCollection += revenueReportBeanSummary.getCollection();
				totalClosingBalance += revenueReportBeanSummary.getClosingBal();

				bean.setTotalNoOfInstallation(totalNoOfInstallation);
				bean.setTotalInputEnergy(totalInputEnergy);
				bean.setTotalEnergySoldDCB(totalEnergySoldDCB);
				bean.setTotalOpeningBalance(totalOpeningBalance);
				bean.setTotalDemand(totalDemand);
				bean.setTotalCollection(totalCollection);
				bean.setTotalClosingBalance(totalClosingBalance);

			}

			else if (!divisionId.equals(revenueReportBeanSummary.getDivId())) {
				totalNoOfInstallation = 0;
				totalInputEnergy = 0;
				totalEnergySoldDCB = 0;
				totalOpeningBalance = 0;
				totalDemand = 0;
				totalCollection = 0;
				totalClosingBalance = 0;

				bean = new RevenueReportBeanSummary();
				divisionId = revenueReportBeanSummary.getDivId();

				bean.setId(revenueReportBeanSummary.getDivId());
				bean.setLabel(revenueReportBeanSummary.getDivName());
				bean.setLvl("3");

				totalNoOfInstallation += revenueReportBeanSummary.getTotNoOfInstall();
				totalInputEnergy += revenueReportBeanSummary.getInputEnergy();
				totalEnergySoldDCB += revenueReportBeanSummary.getDcbEnergy();

				totalOpeningBalance += revenueReportBeanSummary.getOpenBal();
				totalDemand += revenueReportBeanSummary.getDemand();
				totalCollection += revenueReportBeanSummary.getCollection();
				totalClosingBalance += revenueReportBeanSummary.getClosingBal();

				bean.setTotalNoOfInstallation(totalNoOfInstallation);
				bean.setTotalInputEnergy(totalInputEnergy);
				bean.setTotalEnergySoldDCB(totalEnergySoldDCB);
				bean.setTotalOpeningBalance(totalOpeningBalance);
				bean.setTotalDemand(totalDemand);
				bean.setTotalCollection(totalCollection);
				bean.setTotalClosingBalance(totalClosingBalance);
				disprevenueReportBeanList.add(bean);

			}

		}

		return disprevenueReportBeanList;

    }
	
	
	public List<RevenueReportBeanSummary> circleWise(String id,String lvl)
	{
		
		int totalNoOfInstallation = 0;
		int totalInputEnergy = 0;
		int totalEnergySoldDCB = 0;

		double totalOpeningBalance = 0;
		double totalDemand = 0;
		double totalCollection = 0;
		double totalClosingBalance = 0;

		List<RevenueReportBeanSummary> tempRevenueBeanList = new ArrayList<RevenueReportBeanSummary>();
		disprevenueReportBeanList = new ArrayList<RevenueReportBeanSummary>();

		if (id =="first") {
			tempRevenueBeanList = revenueReportBeanList;
		}

		else if(lvl.equals("1")) {
			for (RevenueReportBeanSummary revenueReportBeanSummary : revenueReportBeanList) {
				if (revenueReportBeanSummary.getZoneId().equals(id)) {
					tempRevenueBeanList.add(revenueReportBeanSummary);
				}
				

			}
		}
		
		else if(lvl.equals("2"))
		{
			
			for (RevenueReportBeanSummary revenueReportBeanSummary : revenueReportBeanList) {
				if (revenueReportBeanSummary.getCircleId().equals(id)) {
					tempRevenueBeanList.add(revenueReportBeanSummary);
				}
				

			}
			
		}

		String circleId = "";
		for (RevenueReportBeanSummary revenueReportBeanSummary : tempRevenueBeanList) {
			if (circleId == "") {

				bean = new RevenueReportBeanSummary();
				circleId = revenueReportBeanSummary.getCircleId();

				bean.setId(revenueReportBeanSummary.getCircleId());
				bean.setLabel(revenueReportBeanSummary.getCircleName());
				bean.setLvl("2");

				totalNoOfInstallation += revenueReportBeanSummary.getTotNoOfInstall();
				totalInputEnergy += revenueReportBeanSummary.getInputEnergy();
				totalEnergySoldDCB += revenueReportBeanSummary.getDcbEnergy();

				totalOpeningBalance += revenueReportBeanSummary.getOpenBal();
				totalDemand += revenueReportBeanSummary.getDemand();
				totalCollection += revenueReportBeanSummary.getCollection();
				totalClosingBalance += revenueReportBeanSummary.getClosingBal();

				bean.setTotalNoOfInstallation(totalNoOfInstallation);
				bean.setTotalInputEnergy(totalInputEnergy);
				bean.setTotalEnergySoldDCB(totalEnergySoldDCB);
				bean.setTotalOpeningBalance(totalOpeningBalance);
				bean.setTotalDemand(totalDemand);
				bean.setTotalCollection(totalCollection);
				bean.setTotalClosingBalance(totalClosingBalance);

				disprevenueReportBeanList.add(bean);

			}

			else if (circleId.equals(revenueReportBeanSummary.getCircleId())) {
				
				totalNoOfInstallation += revenueReportBeanSummary.getTotNoOfInstall();
				totalInputEnergy += revenueReportBeanSummary.getInputEnergy();
				totalEnergySoldDCB += revenueReportBeanSummary.getDcbEnergy();

				totalOpeningBalance += revenueReportBeanSummary.getOpenBal();
				totalDemand += revenueReportBeanSummary.getDemand();
				totalCollection += revenueReportBeanSummary.getCollection();
				totalClosingBalance += revenueReportBeanSummary.getClosingBal();

				bean.setTotalNoOfInstallation(totalNoOfInstallation);
				bean.setTotalInputEnergy(totalInputEnergy);
				bean.setTotalEnergySoldDCB(totalEnergySoldDCB);
				bean.setTotalOpeningBalance(totalOpeningBalance);
				bean.setTotalDemand(totalDemand);
				bean.setTotalCollection(totalCollection);
				bean.setTotalClosingBalance(totalClosingBalance);

			}

			else if (!circleId.equals(revenueReportBeanSummary.getCircleId())) {
				totalNoOfInstallation = 0;
				totalInputEnergy = 0;
				totalEnergySoldDCB = 0;
				totalOpeningBalance = 0;
				totalDemand = 0;
				totalCollection = 0;
				totalClosingBalance = 0;

				bean = new RevenueReportBeanSummary();
				circleId = revenueReportBeanSummary.getCircleId();

				bean.setId(revenueReportBeanSummary.getCircleId());
				bean.setLabel(revenueReportBeanSummary.getCircleName());
				bean.setLvl("2");

				totalNoOfInstallation += revenueReportBeanSummary.getTotNoOfInstall();
				totalInputEnergy += revenueReportBeanSummary.getInputEnergy();
				totalEnergySoldDCB += revenueReportBeanSummary.getDcbEnergy();

				totalOpeningBalance += revenueReportBeanSummary.getOpenBal();
				totalDemand += revenueReportBeanSummary.getDemand();
				totalCollection += revenueReportBeanSummary.getCollection();
				totalClosingBalance += revenueReportBeanSummary.getClosingBal();

				bean.setTotalNoOfInstallation(totalNoOfInstallation);
				bean.setTotalInputEnergy(totalInputEnergy);
				bean.setTotalEnergySoldDCB(totalEnergySoldDCB);
				bean.setTotalOpeningBalance(totalOpeningBalance);
				bean.setTotalDemand(totalDemand);
				bean.setTotalCollection(totalCollection);
				bean.setTotalClosingBalance(totalClosingBalance);
				disprevenueReportBeanList.add(bean);

			}

		}

		return disprevenueReportBeanList;
		
	}
	
	
	public List<RevenueReportBeanSummary> zoneWise(String id,String lvl)
	{
		
		int totalNoOfInstallation = 0;
		int totalInputEnergy = 0;
		int totalEnergySoldDCB = 0;

		double totalOpeningBalance = 0;
		double totalDemand = 0;
		double totalCollection = 0;
		double totalClosingBalance = 0;

		List<RevenueReportBeanSummary> tempRevenueBeanList = new ArrayList<RevenueReportBeanSummary>();
		disprevenueReportBeanList = new ArrayList<RevenueReportBeanSummary>();

		if (id == "first") {
			tempRevenueBeanList = revenueReportBeanList;
		}

		else if(lvl.equals("1")) {
			for (RevenueReportBeanSummary revenueReportBeanSummary : revenueReportBeanList) {
				if (revenueReportBeanSummary.getZoneId().equals(id)) {
					tempRevenueBeanList.add(revenueReportBeanSummary);
				}
				

			}
		}
		

		String zoneId = "";
		for (RevenueReportBeanSummary revenueReportBeanSummary : tempRevenueBeanList) {
			if (zoneId == "") {

				bean = new RevenueReportBeanSummary();
				zoneId = revenueReportBeanSummary.getZoneId();

				bean.setId(revenueReportBeanSummary.getZoneId());
				bean.setLabel(revenueReportBeanSummary.getZoneName());
				bean.setLvl("1");

				totalNoOfInstallation += revenueReportBeanSummary.getTotNoOfInstall();
				totalInputEnergy += revenueReportBeanSummary.getInputEnergy();
				totalEnergySoldDCB += revenueReportBeanSummary.getDcbEnergy();

				totalOpeningBalance += revenueReportBeanSummary.getOpenBal();
				totalDemand += revenueReportBeanSummary.getDemand();
				totalCollection += revenueReportBeanSummary.getCollection();
				totalClosingBalance += revenueReportBeanSummary.getClosingBal();

				bean.setTotalNoOfInstallation(totalNoOfInstallation);
				bean.setTotalInputEnergy(totalInputEnergy);
				bean.setTotalEnergySoldDCB(totalEnergySoldDCB);
				bean.setTotalOpeningBalance(totalOpeningBalance);
				bean.setTotalDemand(totalDemand);
				bean.setTotalCollection(totalCollection);
				bean.setTotalClosingBalance(totalClosingBalance);

				disprevenueReportBeanList.add(bean);

			}

			else if (zoneId.equals(revenueReportBeanSummary.getZoneId())) {
				
				totalNoOfInstallation += revenueReportBeanSummary.getTotNoOfInstall();
				totalInputEnergy += revenueReportBeanSummary.getInputEnergy();
				totalEnergySoldDCB += revenueReportBeanSummary.getDcbEnergy();

				totalOpeningBalance += revenueReportBeanSummary.getOpenBal();
				totalDemand += revenueReportBeanSummary.getDemand();
				totalCollection += revenueReportBeanSummary.getCollection();
				totalClosingBalance += revenueReportBeanSummary.getClosingBal();

				bean.setTotalNoOfInstallation(totalNoOfInstallation);
				bean.setTotalInputEnergy(totalInputEnergy);
				bean.setTotalEnergySoldDCB(totalEnergySoldDCB);
				bean.setTotalOpeningBalance(totalOpeningBalance);
				bean.setTotalDemand(totalDemand);
				bean.setTotalCollection(totalCollection);
				bean.setTotalClosingBalance(totalClosingBalance);

			}

			else if (!zoneId.equals(revenueReportBeanSummary.getZoneId())) {
				totalNoOfInstallation = 0;
				totalInputEnergy = 0;
				totalEnergySoldDCB = 0;
				totalOpeningBalance = 0;
				totalDemand = 0;
				totalCollection = 0;
				totalClosingBalance = 0;

				bean = new RevenueReportBeanSummary();
				zoneId = revenueReportBeanSummary.getZoneId();

				bean.setId(revenueReportBeanSummary.getZoneId());
				bean.setLabel(revenueReportBeanSummary.getZoneName());
				bean.setLvl("1");

				totalNoOfInstallation += revenueReportBeanSummary.getTotNoOfInstall();
				totalInputEnergy += revenueReportBeanSummary.getInputEnergy();
				totalEnergySoldDCB += revenueReportBeanSummary.getDcbEnergy();

				totalOpeningBalance += revenueReportBeanSummary.getOpenBal();
				totalDemand += revenueReportBeanSummary.getDemand();
				totalCollection += revenueReportBeanSummary.getCollection();
				totalClosingBalance += revenueReportBeanSummary.getClosingBal();

				bean.setTotalNoOfInstallation(totalNoOfInstallation);
				bean.setTotalInputEnergy(totalInputEnergy);
				bean.setTotalEnergySoldDCB(totalEnergySoldDCB);
				bean.setTotalOpeningBalance(totalOpeningBalance);
				bean.setTotalDemand(totalDemand);
				bean.setTotalCollection(totalCollection);
				bean.setTotalClosingBalance(totalClosingBalance);
				disprevenueReportBeanList.add(bean);

			}

		}

		return disprevenueReportBeanList;
		
		
	}

}
