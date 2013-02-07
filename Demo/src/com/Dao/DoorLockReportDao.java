package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Util.MysqlConnectionProvider;
import com.bean.DoorLockReportMainBean;

public class DoorLockReportDao {

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;

	DoorLockReportMainBean bean = null;
	List<DoorLockReportMainBean> doorlLockBeanList = null;
	List<DoorLockReportMainBean> displayDoorLockBeanList = new ArrayList<DoorLockReportMainBean>();

	public List<DoorLockReportMainBean> viewData(String role,String roleBelongs, String yearId) {
		String whereClause = "";
		if (role.equals("1")) {
			whereClause = " and z.ZONE_ID = " + roleBelongs;
		} else if (role.equals("2")) {
			whereClause = " and c.CRCL_ID = " + roleBelongs;
		} else if (role.equals("3")) {
			whereClause = " and d.DIV_ID = " + roleBelongs;
		} else if (role.equals("4")) {
			whereClause = " and sd.SUBDIV_ID = " + roleBelongs;
		} else if (role.equals("5")) {
			whereClause = "  and s.SECTN_ID = " + roleBelongs;

		}

		doorlLockBeanList = new ArrayList<DoorLockReportMainBean>();
		DoorLockReportMainBean reportMainBean = null;

		try {
			connection = MysqlConnectionProvider.getNewConnection();
			String query = "SELECT dr.DRLCK_IDEN_FLAG, th.TH_ID, "
					+ "dr.DRLCK_SHIFT_OB, " + "dr.DRLCK_NOOF_INSTANCE, "
					+ "dr.DRLCK_NOOF_NOTICE, " + "dr.DRLCK_NOOF_SHIFTED, "
					+ "dr.DRLCK_NOT_SHIFTED, " + "dr.DRLCK_CB, "
					+ "dr.DRLCK_REASON, " + "s.SECTN_ID, " + "s.SECTN_NAME, "
					+ "sd.SUBDIV_ID, " + "sd.SUBDIV_NAME, " + "d.DIV_ID, "
					+ "d.DIV_NAME, " + "c.CRCL_ID, " + "c.CRCL_NAME, "
					+ "z.ZONE_ID, " + "z.ZONE_NAME " + "FROM doorlock dr "
					+ "JOIN tran_head th " + "ON dr.DRLCK_TH_ID = th.TH_ID "
					+ "JOIN section s " + "ON th.TH_SECTN_ID = s.SECTN_ID "
					+ "JOIN sub_division sd "
					+ "ON s.SECTN_SUBDIV_ID = sd.SUBDIV_ID "
					+ "JOIN division d " + "ON sd.SUBDIV_DIV_ID = d.DIV_ID "
					+ "JOIN circle c " + "ON d.DIV_CIRCLE_ID = c.CRCL_ID "
					+ "JOIN zone z " + "ON c.CRCL_ZONE_ID = z.ZONE_ID "
					+ "WHERE th.TH_YEAR = '" + yearId + "' " + whereClause 
					+ " order by s.SECTN_ID ,sd.SUBDIV_ID,d.DIV_ID,c.CRCL_ID,z.ZONE_ID" ;


			preparedStatement = connection.prepareStatement(query);
			rs = preparedStatement.executeQuery();
		
			

			while (rs.next()) {
				reportMainBean = new DoorLockReportMainBean();
				reportMainBean.setSectionId(rs.getString("SECTN_ID"));
				reportMainBean.setSectionName(rs.getString("SECTN_NAME"));
				reportMainBean.setSubDivisionId(rs.getString("SUBDIV_ID"));
				reportMainBean.setSubDivisionName(rs.getString("SUBDIV_NAME"));
				reportMainBean.setDivisionId(rs.getString("DIV_ID"));
				reportMainBean.setDivisionName(rs.getString("DIV_NAME"));
				reportMainBean.setCircleId(rs.getString("CRCL_ID"));
				reportMainBean.setCircleName(rs.getString("CRCL_NAME"));
				reportMainBean.setZoneId(rs.getString("ZONE_ID"));
				reportMainBean.setZoneName(rs.getString("ZONE_NAME"));

				reportMainBean.setDoorLockFlag(rs.getInt("DRLCK_IDEN_FLAG"));
				reportMainBean.setLvl("5");

				if (rs.getString("DRLCK_IDEN_FLAG").equals("1")) {
					reportMainBean.setNoofInstance3to6(rs
							.getInt("DRLCK_NOOF_INSTANCE"));
					reportMainBean.setNoOfNoticed3to6(rs
							.getInt("DRLCK_NOOF_NOTICE"));
					reportMainBean.setNoOfShifted3to6(rs
							.getInt("DRLCK_NOOF_SHIFTED"));
				}

				else if (rs.getString("DRLCK_IDEN_FLAG").equals("2")) {
					reportMainBean.setNoOfInstanceGt6(rs
							.getInt("DRLCK_NOOF_INSTANCE"));
					reportMainBean.setNoOfNoticeGt6(rs
							.getInt("DRLCK_NOOF_NOTICE"));
					reportMainBean.setNoOfShiftedGt6(rs
							.getInt("DRLCK_NOOF_SHIFTED"));

				}

				doorlLockBeanList.add(reportMainBean);            

			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {   
			MysqlConnectionProvider.releaseConnection(rs, null, preparedStatement, connection);
		}

		if (role.equals("1")) {
			displayDoorLockBeanList = zoneWise("first","0");
		
			
		}
		else if (role.equals("2")) {
			displayDoorLockBeanList = circleWise("first","0");
		}

		else if (role.equals("3")) {
			displayDoorLockBeanList = divisionWise("first","0");
			

		} else if (role.equals("4")) {
			displayDoorLockBeanList = subDivisionWise("first","0");
			

		} else if (role.equals("5")) {
			displayDoorLockBeanList = sectionWise("first","0");
			
		}
		
		return displayDoorLockBeanList;

	}

	public List<DoorLockReportMainBean> sectionWise(String id,String lvl) {
		
		int noofInstance3to6 = 0;
		int noOfShifted3to6 = 0;
		int noOfNoticed3to6 = 0;

		int noOfInstanceGt6 = 0;
		int noOfShiftedGt6 = 0;
		int noOfNoticeGt6 = 0;

		List<DoorLockReportMainBean> tempMethodBeanLIst = new ArrayList<DoorLockReportMainBean>();
		displayDoorLockBeanList = new ArrayList<DoorLockReportMainBean>();

		if (id == "first") {
			tempMethodBeanLIst = doorlLockBeanList;

		} else  if(lvl.equals("4")){
			for (DoorLockReportMainBean doorLockReportMainBean : doorlLockBeanList) {

				if (doorLockReportMainBean.getSubDivisionId().equals(id)) {
					tempMethodBeanLIst.add(doorLockReportMainBean);
				}
				

			}
			
		}
		
		else if(lvl.equals("5"))
		{
			
			for (DoorLockReportMainBean doorLockReportMainBean : doorlLockBeanList) {

				if (doorLockReportMainBean.getSectionId().equals(id)) {
					tempMethodBeanLIst.add(doorLockReportMainBean);
				}
				

			}
			
		}
		
		

		String sid = "";
		for (DoorLockReportMainBean doorLockReportMainBean : tempMethodBeanLIst) {
			if (sid == "") {
				bean = new DoorLockReportMainBean();
				sid = doorLockReportMainBean.getSectionId();

				bean.setId(doorLockReportMainBean.getSectionId());
				bean.setLabel(doorLockReportMainBean.getSectionName());
				bean.setLvl("5");

				if (doorLockReportMainBean.getDoorLockFlag() == 1) {
					noofInstance3to6 += doorLockReportMainBean
							.getNoofInstance3to6();
					noOfNoticed3to6 += doorLockReportMainBean
							.getNoOfNoticed3to6();
					noOfShifted3to6 += doorLockReportMainBean
							.getNoOfShifted3to6();

					bean.setNoofInstance3to6(noofInstance3to6);
					bean.setNoOfNoticed3to6(noOfNoticed3to6);
					bean.setNoOfShifted3to6(noOfShifted3to6);

					displayDoorLockBeanList.add(bean);
					
				} else if (doorLockReportMainBean.getDoorLockFlag() == 2) {
					noOfInstanceGt6 += doorLockReportMainBean
							.getNoOfInstanceGt6();
					noOfNoticeGt6 += doorLockReportMainBean.getNoOfNoticeGt6();
					noOfShiftedGt6 += doorLockReportMainBean
							.getNoOfShiftedGt6();

					bean.setNoOfInstanceGt6(noOfInstanceGt6);
					bean.setNoOfNoticeGt6(noOfNoticeGt6);
					bean.setNoOfShiftedGt6(noOfShiftedGt6);

					displayDoorLockBeanList.add(bean);
				}

			}

			else if (!sid.equals(doorLockReportMainBean.getSectionId())) {
				noofInstance3to6 = 0;
				noOfShifted3to6 = 0;
				noOfNoticed3to6 = 0;

				noOfInstanceGt6 = 0;
				noOfShiftedGt6 = 0;
				noOfNoticeGt6 = 0;

				bean = new DoorLockReportMainBean();
				sid = doorLockReportMainBean.getSectionId();

				bean.setId(doorLockReportMainBean.getSectionId());
				bean.setLabel(doorLockReportMainBean.getSectionName());
				bean.setLvl("5");
				if (doorLockReportMainBean.getDoorLockFlag() == 1) {
					noofInstance3to6 += doorLockReportMainBean
							.getNoofInstance3to6();
					noOfNoticed3to6 += doorLockReportMainBean
							.getNoOfNoticed3to6();
					noOfShifted3to6 += doorLockReportMainBean
							.getNoOfShifted3to6();

					bean.setNoofInstance3to6(noofInstance3to6);
					bean.setNoOfNoticed3to6(noOfNoticed3to6);
					bean.setNoOfShifted3to6(noOfShifted3to6);

					displayDoorLockBeanList.add(bean);
				}

				else if (doorLockReportMainBean.getDoorLockFlag() == 2) {
					noOfInstanceGt6 += doorLockReportMainBean
							.getNoOfInstanceGt6();
					noOfNoticeGt6 += doorLockReportMainBean.getNoOfNoticeGt6();
					noOfShiftedGt6 += doorLockReportMainBean
							.getNoOfShiftedGt6();

					bean.setNoOfInstanceGt6(noOfInstanceGt6);
					bean.setNoOfNoticeGt6(noOfNoticeGt6);
					bean.setNoOfShiftedGt6(noOfShiftedGt6);

					displayDoorLockBeanList.add(bean);
				}

			}

			else if (sid.equals(doorLockReportMainBean.getSectionId()))

			{
				if (doorLockReportMainBean.getDoorLockFlag() == 1) {
					noofInstance3to6 += doorLockReportMainBean
							.getNoofInstance3to6();
					noOfNoticed3to6 += doorLockReportMainBean
							.getNoOfNoticed3to6();
					noOfShifted3to6 += doorLockReportMainBean
							.getNoOfShifted3to6();

					bean.setNoofInstance3to6(noofInstance3to6);
					bean.setNoOfNoticed3to6(noOfNoticed3to6);
					bean.setNoOfShifted3to6(noOfShifted3to6);

				}

				else if (doorLockReportMainBean.getDoorLockFlag() == 2) {
					noOfInstanceGt6 += doorLockReportMainBean
							.getNoOfInstanceGt6();
					noOfNoticeGt6 += doorLockReportMainBean.getNoOfNoticeGt6();
					noOfShiftedGt6 += doorLockReportMainBean
							.getNoOfShiftedGt6();

					bean.setNoOfInstanceGt6(noOfInstanceGt6);
					bean.setNoOfNoticeGt6(noOfNoticeGt6);
					bean.setNoOfShiftedGt6(noOfShiftedGt6);

				}

			}
		}
		return displayDoorLockBeanList;

	}

	public List<DoorLockReportMainBean> subDivisionWise(String id,String lvl) {
		
		int noofInstance3to6 = 0;
		int noOfShifted3to6 = 0;
		int noOfNoticed3to6 = 0;

		int noOfInstanceGt6 = 0;
		int noOfShiftedGt6 = 0;
		int noOfNoticeGt6 = 0;

		List<DoorLockReportMainBean> tempMethodBeanLIst = new ArrayList<DoorLockReportMainBean>();
		displayDoorLockBeanList = new ArrayList<DoorLockReportMainBean>();

		if (id == "first") {
			tempMethodBeanLIst = doorlLockBeanList;

		}
		
		
		else if(lvl.equals("3")) {
			for (DoorLockReportMainBean doorLockReportMainBean : doorlLockBeanList) {

				if (doorLockReportMainBean.getDivisionId().equals(id)) {
					tempMethodBeanLIst.add(doorLockReportMainBean);
				}
				

			}
		}
		
		else if(lvl.equals("4"))
		{
			for (DoorLockReportMainBean doorLockReportMainBean : doorlLockBeanList) {

				if (doorLockReportMainBean.getSubDivisionId().equals(id)) {
					tempMethodBeanLIst.add(doorLockReportMainBean);
				}
				

			}
			
		}

		String subdid = "";
		for (DoorLockReportMainBean doorLockReportMainBean : tempMethodBeanLIst) {
			if (subdid == "") {
				bean = new DoorLockReportMainBean();
				subdid = doorLockReportMainBean.getSubDivisionId();

				bean.setId(doorLockReportMainBean.getSubDivisionId());
				bean.setLabel(doorLockReportMainBean.getSubDivisionName());
				bean.setLvl("4");

				if (doorLockReportMainBean.getDoorLockFlag() == 1) {
					noofInstance3to6 += doorLockReportMainBean
							.getNoofInstance3to6();
					noOfNoticed3to6 += doorLockReportMainBean
							.getNoOfNoticed3to6();
					noOfShifted3to6 += doorLockReportMainBean
							.getNoOfShifted3to6();

					bean.setNoofInstance3to6(noofInstance3to6);
					bean.setNoOfNoticed3to6(noOfNoticed3to6);
					bean.setNoOfShifted3to6(noOfShifted3to6);

					displayDoorLockBeanList.add(bean);
				} else if (doorLockReportMainBean.getDoorLockFlag() == 2) {
					noOfInstanceGt6 += doorLockReportMainBean
							.getNoOfInstanceGt6();
					noOfNoticeGt6 += doorLockReportMainBean.getNoOfNoticeGt6();
					noOfShiftedGt6 += doorLockReportMainBean
							.getNoOfShiftedGt6();

					bean.setNoOfInstanceGt6(noOfInstanceGt6);
					bean.setNoOfNoticeGt6(noOfNoticeGt6);
					bean.setNoOfShiftedGt6(noOfShiftedGt6);

					displayDoorLockBeanList.add(bean);
				}

			}

			else if (!subdid.equals(doorLockReportMainBean.getSubDivisionId())) {
				noofInstance3to6 = 0;
				noOfShifted3to6 = 0;
				noOfNoticed3to6 = 0;

				noOfInstanceGt6 = 0;
				noOfShiftedGt6 = 0;
				noOfNoticeGt6 = 0;

				bean = new DoorLockReportMainBean();
				subdid = doorLockReportMainBean.getSubDivisionId();

				bean.setId(doorLockReportMainBean.getSubDivisionId());
				bean.setLabel(doorLockReportMainBean.getSubDivisionName());
				bean.setLvl("4");
				if (doorLockReportMainBean.getDoorLockFlag() == 1) {
					noofInstance3to6 += doorLockReportMainBean
							.getNoofInstance3to6();
					noOfNoticed3to6 += doorLockReportMainBean
							.getNoOfNoticed3to6();
					noOfShifted3to6 += doorLockReportMainBean
							.getNoOfShifted3to6();

					bean.setNoofInstance3to6(noofInstance3to6);
					bean.setNoOfNoticed3to6(noOfNoticed3to6);
					bean.setNoOfShifted3to6(noOfShifted3to6);

					displayDoorLockBeanList.add(bean);
				}

				else if (doorLockReportMainBean.getDoorLockFlag() == 2) {
					noOfInstanceGt6 += doorLockReportMainBean
							.getNoOfInstanceGt6();
					noOfNoticeGt6 += doorLockReportMainBean.getNoOfNoticeGt6();
					noOfShiftedGt6 += doorLockReportMainBean
							.getNoOfShiftedGt6();

					bean.setNoOfInstanceGt6(noOfInstanceGt6);
					bean.setNoOfNoticeGt6(noOfNoticeGt6);
					bean.setNoOfShiftedGt6(noOfShiftedGt6);

					displayDoorLockBeanList.add(bean);
				}

			}

			else if (subdid.equals(doorLockReportMainBean.getSubDivisionId()))

			
			{
				if (doorLockReportMainBean.getDoorLockFlag() == 1) {
					noofInstance3to6 += doorLockReportMainBean
							.getNoofInstance3to6();
					noOfNoticed3to6 += doorLockReportMainBean
							.getNoOfNoticed3to6();
					noOfShifted3to6 += doorLockReportMainBean
							.getNoOfShifted3to6();

					bean.setNoofInstance3to6(noofInstance3to6);
					bean.setNoOfNoticed3to6(noOfNoticed3to6);
					bean.setNoOfShifted3to6(noOfShifted3to6);
					

				}

				else if (doorLockReportMainBean.getDoorLockFlag() == 2) {
					noOfInstanceGt6 += doorLockReportMainBean
							.getNoOfInstanceGt6();
					noOfNoticeGt6 += doorLockReportMainBean.getNoOfNoticeGt6();
					noOfShiftedGt6 += doorLockReportMainBean
							.getNoOfShiftedGt6();

					bean.setNoOfInstanceGt6(noOfInstanceGt6);
					bean.setNoOfNoticeGt6(noOfNoticeGt6);
					bean.setNoOfShiftedGt6(noOfShiftedGt6);
				

				}

			}
		}
		return displayDoorLockBeanList;

	}
	
	
	public List<DoorLockReportMainBean> divisionWise(String id,String lvl) {
		int noofInstance3to6 = 0;
		int noOfShifted3to6 = 0;
		int noOfNoticed3to6 = 0;

		int noOfInstanceGt6 = 0;
		int noOfShiftedGt6 = 0;
		int noOfNoticeGt6 = 0;

		List<DoorLockReportMainBean> tempMethodBeanLIst = new ArrayList<DoorLockReportMainBean>();
		displayDoorLockBeanList = new ArrayList<DoorLockReportMainBean>();

		if (id == "first") {
			tempMethodBeanLIst = doorlLockBeanList;

		} else if(lvl.equals("2")) {
			for (DoorLockReportMainBean doorLockReportMainBean : doorlLockBeanList) {

				if (doorLockReportMainBean.getCircleId().equals(id)) {
					tempMethodBeanLIst.add(doorLockReportMainBean);
				}
				
				

			}
		}
		
		else if(lvl.equals("3"))
		{
			for (DoorLockReportMainBean doorLockReportMainBean : doorlLockBeanList) {

				if (doorLockReportMainBean.getDivisionId().equals(id)) {
					tempMethodBeanLIst.add(doorLockReportMainBean);
				}
				
				

			}
			
		}

		String divId = "";
		for (DoorLockReportMainBean doorLockReportMainBean : tempMethodBeanLIst) {
			if (divId == "") {
				bean = new DoorLockReportMainBean();
				divId = doorLockReportMainBean.getDivisionId();

				bean.setId(doorLockReportMainBean.getDivisionId());
				bean.setLabel(doorLockReportMainBean.getDivisionName());
				bean.setLvl("3");

				if (doorLockReportMainBean.getDoorLockFlag() == 1) {
					noofInstance3to6 += doorLockReportMainBean
							.getNoofInstance3to6();
					noOfNoticed3to6 += doorLockReportMainBean
							.getNoOfNoticed3to6();
					noOfShifted3to6 += doorLockReportMainBean
							.getNoOfShifted3to6();

					bean.setNoofInstance3to6(noofInstance3to6);
					bean.setNoOfNoticed3to6(noOfNoticed3to6);
					bean.setNoOfShifted3to6(noOfShifted3to6);

					displayDoorLockBeanList.add(bean);
				} else if (doorLockReportMainBean.getDoorLockFlag() == 2) {
					noOfInstanceGt6 += doorLockReportMainBean
							.getNoOfInstanceGt6();
					noOfNoticeGt6 += doorLockReportMainBean.getNoOfNoticeGt6();
					noOfShiftedGt6 += doorLockReportMainBean
							.getNoOfShiftedGt6();

					bean.setNoOfInstanceGt6(noOfInstanceGt6);
					bean.setNoOfNoticeGt6(noOfNoticeGt6);
					bean.setNoOfShiftedGt6(noOfShiftedGt6);

					displayDoorLockBeanList.add(bean);
				}

			}

			else if (!divId.equals(doorLockReportMainBean.getDivisionId())) {
				noofInstance3to6 = 0;
				noOfShifted3to6 = 0;
				noOfNoticed3to6 = 0;

				noOfInstanceGt6 = 0;
				noOfShiftedGt6 = 0;
				noOfNoticeGt6 = 0;

				bean = new DoorLockReportMainBean();
				divId = doorLockReportMainBean.getDivisionId();

				bean.setId(doorLockReportMainBean.getDivisionId());
				bean.setLabel(doorLockReportMainBean.getDivisionName());
				bean.setLvl("3");
				if (doorLockReportMainBean.getDoorLockFlag() == 1) {
					noofInstance3to6 += doorLockReportMainBean
							.getNoofInstance3to6();
					noOfNoticed3to6 += doorLockReportMainBean
							.getNoOfNoticed3to6();
					noOfShifted3to6 += doorLockReportMainBean
							.getNoOfShifted3to6();

					bean.setNoofInstance3to6(noofInstance3to6);
					bean.setNoOfNoticed3to6(noOfNoticed3to6);
					bean.setNoOfShifted3to6(noOfShifted3to6);

					displayDoorLockBeanList.add(bean);
				}

				else if (doorLockReportMainBean.getDoorLockFlag() == 2) {
					noOfInstanceGt6 += doorLockReportMainBean
							.getNoOfInstanceGt6();
					noOfNoticeGt6 += doorLockReportMainBean.getNoOfNoticeGt6();
					noOfShiftedGt6 += doorLockReportMainBean
							.getNoOfShiftedGt6();

					bean.setNoOfInstanceGt6(noOfInstanceGt6);
					bean.setNoOfNoticeGt6(noOfNoticeGt6);
					bean.setNoOfShiftedGt6(noOfShiftedGt6);

					displayDoorLockBeanList.add(bean);
				}

			}

			else if (divId.equals(doorLockReportMainBean.getDivisionId()))

			{
				if (doorLockReportMainBean.getDoorLockFlag() == 1) {
					noofInstance3to6 += doorLockReportMainBean
							.getNoofInstance3to6();
					noOfNoticed3to6 += doorLockReportMainBean
							.getNoOfNoticed3to6();
					noOfShifted3to6 += doorLockReportMainBean
							.getNoOfShifted3to6();

					bean.setNoofInstance3to6(noofInstance3to6);
					bean.setNoOfNoticed3to6(noOfNoticed3to6);
					bean.setNoOfShifted3to6(noOfShifted3to6);

				}

				else if (doorLockReportMainBean.getDoorLockFlag() == 2) {
					noOfInstanceGt6 += doorLockReportMainBean
							.getNoOfInstanceGt6();
					noOfNoticeGt6 += doorLockReportMainBean.getNoOfNoticeGt6();
					noOfShiftedGt6 += doorLockReportMainBean
							.getNoOfShiftedGt6();

					bean.setNoOfInstanceGt6(noOfInstanceGt6);
					bean.setNoOfNoticeGt6(noOfNoticeGt6);
					bean.setNoOfShiftedGt6(noOfShiftedGt6);

				}

			}
		}
		return displayDoorLockBeanList;

	}
	
	public List<DoorLockReportMainBean> circleWise(String id,String lvl) {
		int noofInstance3to6 = 0;
		int noOfShifted3to6 = 0;
		int noOfNoticed3to6 = 0;

		int noOfInstanceGt6 = 0;
		int noOfShiftedGt6 = 0;
		int noOfNoticeGt6 = 0;

		List<DoorLockReportMainBean> tempMethodBeanLIst = new ArrayList<DoorLockReportMainBean>();
		displayDoorLockBeanList = new ArrayList<DoorLockReportMainBean>();

		if (id == "first") {
			tempMethodBeanLIst = doorlLockBeanList;

		} else if(lvl.equals("1")) {
			for (DoorLockReportMainBean doorLockReportMainBean : doorlLockBeanList) {

				if (doorLockReportMainBean.getZoneId().equals(id)) {
					tempMethodBeanLIst.add(doorLockReportMainBean);
				}
			

			}
		}
		
		else if(lvl.equals("2"))
		{
			for (DoorLockReportMainBean doorLockReportMainBean : doorlLockBeanList) {

				if (doorLockReportMainBean.getCircleId().equals(id)) {
					tempMethodBeanLIst.add(doorLockReportMainBean);
				}
			

			}
			
		}

		String cirId = "";
		for (DoorLockReportMainBean doorLockReportMainBean : tempMethodBeanLIst) {
			if (cirId == "") {
				bean = new DoorLockReportMainBean();
				cirId = doorLockReportMainBean.getCircleId();

				bean.setId(doorLockReportMainBean.getCircleId());
				bean.setLabel(doorLockReportMainBean.getCircleName());
				bean.setLvl("2");

				if (doorLockReportMainBean.getDoorLockFlag() == 1) {
					noofInstance3to6 += doorLockReportMainBean
							.getNoofInstance3to6();
					noOfNoticed3to6 += doorLockReportMainBean
							.getNoOfNoticed3to6();
					noOfShifted3to6 += doorLockReportMainBean
							.getNoOfShifted3to6();

					bean.setNoofInstance3to6(noofInstance3to6);
					bean.setNoOfNoticed3to6(noOfNoticed3to6);
					bean.setNoOfShifted3to6(noOfShifted3to6);

					displayDoorLockBeanList.add(bean);
				} else if (doorLockReportMainBean.getDoorLockFlag() == 2) {
					noOfInstanceGt6 += doorLockReportMainBean
							.getNoOfInstanceGt6();
					noOfNoticeGt6 += doorLockReportMainBean.getNoOfNoticeGt6();
					noOfShiftedGt6 += doorLockReportMainBean
							.getNoOfShiftedGt6();

					bean.setNoOfInstanceGt6(noOfInstanceGt6);
					bean.setNoOfNoticeGt6(noOfNoticeGt6);
					bean.setNoOfShiftedGt6(noOfShiftedGt6);

					displayDoorLockBeanList.add(bean);
				}

			}

			else if (!cirId.equals(doorLockReportMainBean.getCircleId())) {
				noofInstance3to6 = 0;
				noOfShifted3to6 = 0;
				noOfNoticed3to6 = 0;

				noOfInstanceGt6 = 0;
				noOfShiftedGt6 = 0;
				noOfNoticeGt6 = 0;

				bean = new DoorLockReportMainBean();
				cirId = doorLockReportMainBean.getCircleId();

				bean.setId(doorLockReportMainBean.getCircleId());
				bean.setLabel(doorLockReportMainBean.getCircleName());
				bean.setLvl("2");
				
				if (doorLockReportMainBean.getDoorLockFlag() == 1) {
					noofInstance3to6 += doorLockReportMainBean
							.getNoofInstance3to6();
					noOfNoticed3to6 += doorLockReportMainBean
							.getNoOfNoticed3to6();
					noOfShifted3to6 += doorLockReportMainBean
							.getNoOfShifted3to6();

					bean.setNoofInstance3to6(noofInstance3to6);
					bean.setNoOfNoticed3to6(noOfNoticed3to6);
					bean.setNoOfShifted3to6(noOfShifted3to6);

					displayDoorLockBeanList.add(bean);
				}

				else if (doorLockReportMainBean.getDoorLockFlag() == 2) {
					noOfInstanceGt6 += doorLockReportMainBean
							.getNoOfInstanceGt6();
					noOfNoticeGt6 += doorLockReportMainBean.getNoOfNoticeGt6();
					noOfShiftedGt6 += doorLockReportMainBean
							.getNoOfShiftedGt6();

					bean.setNoOfInstanceGt6(noOfInstanceGt6);
					bean.setNoOfNoticeGt6(noOfNoticeGt6);
					bean.setNoOfShiftedGt6(noOfShiftedGt6);

					displayDoorLockBeanList.add(bean);
				}

			}

			else if (cirId.equals(doorLockReportMainBean.getCircleId()))

			{
				if (doorLockReportMainBean.getDoorLockFlag() == 1) {
					noofInstance3to6 += doorLockReportMainBean
							.getNoofInstance3to6();
					noOfNoticed3to6 += doorLockReportMainBean
							.getNoOfNoticed3to6();
					noOfShifted3to6 += doorLockReportMainBean
							.getNoOfShifted3to6();

					bean.setNoofInstance3to6(noofInstance3to6);
					bean.setNoOfNoticed3to6(noOfNoticed3to6);
					bean.setNoOfShifted3to6(noOfShifted3to6);

				}

				else if (doorLockReportMainBean.getDoorLockFlag() == 2) {
					noOfInstanceGt6 += doorLockReportMainBean
							.getNoOfInstanceGt6();
					noOfNoticeGt6 += doorLockReportMainBean.getNoOfNoticeGt6();
					noOfShiftedGt6 += doorLockReportMainBean
							.getNoOfShiftedGt6();

					bean.setNoOfInstanceGt6(noOfInstanceGt6);
					bean.setNoOfNoticeGt6(noOfNoticeGt6);
					bean.setNoOfShiftedGt6(noOfShiftedGt6);

				}

			}
		}
		return displayDoorLockBeanList;

	}


	
	public List<DoorLockReportMainBean> zoneWise(String id,String lvl) {
		
		int noofInstance3to6 = 0;
		int noOfShifted3to6 = 0;
		int noOfNoticed3to6 = 0;

		int noOfInstanceGt6 = 0;
		int noOfShiftedGt6 = 0;
		int noOfNoticeGt6 = 0;

		List<DoorLockReportMainBean> tempMethodBeanLIst = new ArrayList<DoorLockReportMainBean>();
		displayDoorLockBeanList = new ArrayList<DoorLockReportMainBean>();

		if (id == "first") {
			tempMethodBeanLIst = doorlLockBeanList;

		} else if(lvl.equals("1")){
			for (DoorLockReportMainBean doorLockReportMainBean : doorlLockBeanList) {

				if (doorLockReportMainBean.getZoneId().equals(id)) {
					tempMethodBeanLIst.add(doorLockReportMainBean);
				}
				
			

			}
		}

		String zoneId = "";
		for (DoorLockReportMainBean doorLockReportMainBean : tempMethodBeanLIst) {
			if (zoneId == "") {
				bean = new DoorLockReportMainBean();
				zoneId = doorLockReportMainBean.getZoneId();

				bean.setId(doorLockReportMainBean.getZoneId());
				bean.setLabel(doorLockReportMainBean.getZoneName());
				bean.setLvl("1");

				if (doorLockReportMainBean.getDoorLockFlag() == 1) {
					noofInstance3to6 += doorLockReportMainBean
							.getNoofInstance3to6();
					noOfNoticed3to6 += doorLockReportMainBean
							.getNoOfNoticed3to6();
					noOfShifted3to6 += doorLockReportMainBean
							.getNoOfShifted3to6();

					bean.setNoofInstance3to6(noofInstance3to6);
					bean.setNoOfNoticed3to6(noOfNoticed3to6);
					bean.setNoOfShifted3to6(noOfShifted3to6);

					displayDoorLockBeanList.add(bean);
				} else if (doorLockReportMainBean.getDoorLockFlag() == 2) {
					noOfInstanceGt6 += doorLockReportMainBean
							.getNoOfInstanceGt6();
					noOfNoticeGt6 += doorLockReportMainBean.getNoOfNoticeGt6();
					noOfShiftedGt6 += doorLockReportMainBean
							.getNoOfShiftedGt6();

					bean.setNoOfInstanceGt6(noOfInstanceGt6);
					bean.setNoOfNoticeGt6(noOfNoticeGt6);
					bean.setNoOfShiftedGt6(noOfShiftedGt6);

					displayDoorLockBeanList.add(bean);
				}

			}

			else if (!zoneId.equals(doorLockReportMainBean.getZoneId())) {
				noofInstance3to6 = 0;
				noOfShifted3to6 = 0;
				noOfNoticed3to6 = 0;

				noOfInstanceGt6 = 0;
				noOfShiftedGt6 = 0;
				noOfNoticeGt6 = 0;

				bean = new DoorLockReportMainBean();
				zoneId = doorLockReportMainBean.getZoneId();

				bean.setId(doorLockReportMainBean.getZoneId());
				bean.setLabel(doorLockReportMainBean.getZoneName());
				bean.setLvl("1");
				if (doorLockReportMainBean.getDoorLockFlag() == 1) {
					noofInstance3to6 += doorLockReportMainBean
							.getNoofInstance3to6();
					noOfNoticed3to6 += doorLockReportMainBean
							.getNoOfNoticed3to6();
					noOfShifted3to6 += doorLockReportMainBean
							.getNoOfShifted3to6();

					bean.setNoofInstance3to6(noofInstance3to6);
					bean.setNoOfNoticed3to6(noOfNoticed3to6);
					bean.setNoOfShifted3to6(noOfShifted3to6);

					displayDoorLockBeanList.add(bean);
				}

				else if (doorLockReportMainBean.getDoorLockFlag() == 2) {
					noOfInstanceGt6 += doorLockReportMainBean
							.getNoOfInstanceGt6();
					noOfNoticeGt6 += doorLockReportMainBean.getNoOfNoticeGt6();
					noOfShiftedGt6 += doorLockReportMainBean
							.getNoOfShiftedGt6();

					bean.setNoOfInstanceGt6(noOfInstanceGt6);
					bean.setNoOfNoticeGt6(noOfNoticeGt6);
					bean.setNoOfShiftedGt6(noOfShiftedGt6);

					displayDoorLockBeanList.add(bean);
				}

			}

			else if (zoneId.equals(doorLockReportMainBean.getZoneId()))

			{
				if (doorLockReportMainBean.getDoorLockFlag() == 1) {
					noofInstance3to6 += doorLockReportMainBean
							.getNoofInstance3to6();
					noOfNoticed3to6 += doorLockReportMainBean
							.getNoOfNoticed3to6();
					noOfShifted3to6 += doorLockReportMainBean
							.getNoOfShifted3to6();

					bean.setNoofInstance3to6(noofInstance3to6);
					bean.setNoOfNoticed3to6(noOfNoticed3to6);
					bean.setNoOfShifted3to6(noOfShifted3to6);

				}

				else if (doorLockReportMainBean.getDoorLockFlag() == 2) {
					noOfInstanceGt6 += doorLockReportMainBean
							.getNoOfInstanceGt6();
					noOfNoticeGt6 += doorLockReportMainBean.getNoOfNoticeGt6();
					noOfShiftedGt6 += doorLockReportMainBean
							.getNoOfShiftedGt6();

					bean.setNoOfInstanceGt6(noOfInstanceGt6);
					bean.setNoOfNoticeGt6(noOfNoticeGt6);
					bean.setNoOfShiftedGt6(noOfShiftedGt6);

				}

			}
		}
		return displayDoorLockBeanList;

	}
	
	
	
	
	

	
	
	
	
	
}