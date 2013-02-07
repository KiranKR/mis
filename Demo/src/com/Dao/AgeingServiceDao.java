package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.Util.MysqlConnectionProvider;
import com.bean.ReportBean;

public class AgeingServiceDao {

	List<ReportBean> reportBeans = null;
	List<ReportBean> dispReportBeans = new ArrayList<ReportBean>();
	ReportBean bean = new ReportBean();

	public List<ReportBean> getData(String role, String roleBelogs) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		String wherClause = "";
		if (role.equals("1")) {
			wherClause = " and z.ZONE_ID = " + roleBelogs;
		} else if (role.equals("2")) {
			wherClause = " and c.CRCL_ID = " + roleBelogs;
		} else if (role.equals("3")) {
			wherClause = " and d.DIV_ID = " + roleBelogs;
		} else if (role.equals("4")) {
			wherClause = " and sd.SUBDIV_ID = " + roleBelogs;
		} else if (role.equals("5")) {
			wherClause = " and s.SECTN_ID = " + roleBelogs;
		}

		reportBeans = new ArrayList<ReportBean>();
		ReportBean reportBean = null;
		try {
			String query = "SELECT pw.PWRK_ID,"
					+ " pw.PWRK_UID,"
					+ " pw.PWRK_BENEFICIARY,"
					+ " pw.PWRK_VILLAGE,"
					+ " pw.PWRK_REG_DATE,"
					+ " cc.CSTCTG_ID,"
					+ " cc.CSTCTG_NAME,"
					+ " pw.PWRK_ESTIMATE_COST,"
					+ " s.SECTN_ID,"
					+ " s.SECTN_NAME,"
					+ " sd.SUBDIV_ID,"
					+ " sd.SUBDIV_NAME,"
					+ " d.DIV_ID,"
					+ " d.DIV_NAME,"
					+ " c.CRCL_ID,"
					+ " c.CRCL_NAME,"
					+ " z.ZONE_ID,"
					+ " z.ZONE_NAME,"
					+ " pw.PWRK_STAGE_IDEN, "
					+ " ps.PSTS_STS_DATE "
					+ " FROM program_works pw"
					+ " JOIN caste_category cc"
					+ " ON pw.PWRK_CSTCTG_ID = cc.CSTCTG_ID"
					+ " JOIN program_status ps "
					+ " on ps.PSTS_PWRK_ID = pw.PWRK_ID "
					+ " JOIN section s"
					+ " ON pw.PWRK_SECTN_ID = s.SECTN_ID"
					+ " JOIN sub_division sd"
					+ " ON s.SECTN_SUBDIV_ID = sd.SUBDIV_ID"
					+ " JOIN division d"
					+ " ON sd.SUBDIV_DIV_ID = d.DIV_ID"
					+ " JOIN circle c"
					+ " ON d.DIV_CIRCLE_ID = c.CRCL_ID"
					+ " JOIN zone z"
					+ " ON c.CRCL_ZONE_ID = z.ZONE_ID"
					+ " JOIN taluk t"
					+ " ON pw.PWRK_TALUK_ID = t.TALUK_ID"
					+ " WHERE pw.PWRK_STAGE_IDEN = 6 and ps.PSTS_STS_ID=4"
					+ wherClause
					+ " order by s.SECTN_ID, sd.SUBDIV_ID, d.DIV_ID, c.CRCL_ID, z.ZONE_ID ";
			connection = MysqlConnectionProvider.getNewConnection();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
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
				reportBean.setWrkComplDate(dateToString(rs.getDate(20)));
				reportBeans.add(reportBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			MysqlConnectionProvider.releaseConnection(rs, null,
					preparedStatement, connection);
		}
		if (role.equals("1")) {
			dispReportBeans = circleWise("first");
		} else if (role.equals("2")) {
			dispReportBeans = divWise("first");
		} else if (role.equals("3")) {
			dispReportBeans = subDivWise("first");
		} else if (role.equals("4")) {
			dispReportBeans = secWise("first");
		}
		return dispReportBeans;
	}

	public List<ReportBean> secWise(String id) {

		int btn30to60daysCnt = 0;
		int btn60to90daysCnt = 0;
		int btn90to120daysCnt = 0;
		int btn120to150daysCnt = 0;
		int btn150to180daysCnt = 0;
		int grt180daysCnt = 0;

		List<ReportBean> tempRepBean = new ArrayList<ReportBean>();
		dispReportBeans = new ArrayList<ReportBean>();
		if (id.equals("first")) {
			tempRepBean = reportBeans;
		} else {
			for (ReportBean reportBean : reportBeans) {
				if (reportBean.getSudDivId().equals(id)) {
					tempRepBean.add(reportBean);
				}
			}
		}
		// reportBeans = dao.viewData(" ");

		String sId = "";
		for (ReportBean repBean : tempRepBean) {
			if (sId.equals("")) {
				sId = repBean.getSecId();
				bean = new ReportBean();
				btn30to60daysCnt = 0;
				btn60to90daysCnt = 0;
				btn90to120daysCnt = 0;
				btn120to150daysCnt = 0;
				btn150to180daysCnt = 0;
				grt180daysCnt = 0;
				bean.setLable(repBean.getSecName());
				bean.setId(repBean.getSecId());
				bean.setLvl("5");
				DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				Date sysDtae = new Date();
				Date wrkComDate;
				try {
					wrkComDate = dateFormat.parse(repBean.getWrkComplDate());

					int noofDays = subtractDays(sysDtae, wrkComDate);

					if (30 > noofDays && noofDays <= 60) {
						btn30to60daysCnt = btn30to60daysCnt + 1;
					} else if (60 > noofDays && noofDays <= 90) {
						btn60to90daysCnt = btn60to90daysCnt + 1;
					} else if (90 > noofDays && noofDays <= 120) {
						btn90to120daysCnt = btn90to120daysCnt + 1;
					} else if (120 > noofDays && noofDays <= 150) {
						btn120to150daysCnt = btn120to150daysCnt + 1;
					} else if (150 > noofDays && noofDays <= 180) {
						btn150to180daysCnt = btn150to180daysCnt + 1;
					} else if (180 < noofDays) {
						grt180daysCnt = grt180daysCnt + 1;
					}
					bean.setBtn30to60daysCnt(btn30to60daysCnt);
					bean.setBtn60to90daysCnt(btn60to90daysCnt);
					bean.setBtn90to120daysCnt(btn90to120daysCnt);
					bean.setBtn120to150daysCnt(btn120to150daysCnt);
					bean.setBtn150to180daysCnt(btn150to180daysCnt);
					bean.setGrt180daysCnt(grt180daysCnt);
				} catch (ParseException e) {
					e.printStackTrace();
				}

			} else {
				if (!sId.equals(repBean.getSecId())) {
					int ttl = btn30to60daysCnt + btn60to90daysCnt
							+ btn90to120daysCnt + btn120to150daysCnt
							+ btn150to180daysCnt + grt180daysCnt;

					bean.setTtl(ttl);

					dispReportBeans.add(bean);
					sId = repBean.getSecId();
					bean = new ReportBean();
					btn30to60daysCnt = 0;
					btn60to90daysCnt = 0;
					btn90to120daysCnt = 0;
					btn120to150daysCnt = 0;
					btn150to180daysCnt = 0;
					grt180daysCnt = 0;
					bean.setLable(repBean.getSecName());
					bean.setId(repBean.getSecId());
					bean.setLvl("5");
				}
				DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				Date sysDtae = new Date();
				Date wrkComDate;
				try {
					wrkComDate = dateFormat.parse(repBean.getWrkComplDate());
					int noofDays = subtractDays(sysDtae, wrkComDate);

					if (30 > noofDays && noofDays <= 60) {
						btn30to60daysCnt = btn30to60daysCnt + 1;
					} else if (60 > noofDays && noofDays <= 90) {
						btn60to90daysCnt = btn60to90daysCnt + 1;
					} else if (90 > noofDays && noofDays <= 120) {
						btn90to120daysCnt = btn90to120daysCnt + 1;
					} else if (120 > noofDays && noofDays <= 150) {
						btn120to150daysCnt = btn120to150daysCnt + 1;
					} else if (150 > noofDays && noofDays <= 180) {
						btn150to180daysCnt = btn150to180daysCnt + 1;
					} else if (180 < noofDays) {
						grt180daysCnt = grt180daysCnt + 1;
					}
					bean.setBtn30to60daysCnt(btn30to60daysCnt);
					bean.setBtn60to90daysCnt(btn60to90daysCnt);
					bean.setBtn90to120daysCnt(btn90to120daysCnt);
					bean.setBtn120to150daysCnt(btn120to150daysCnt);
					bean.setBtn150to180daysCnt(btn150to180daysCnt);
					bean.setGrt180daysCnt(grt180daysCnt);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

		}
		int ttl = btn30to60daysCnt + btn60to90daysCnt + btn90to120daysCnt
				+ btn120to150daysCnt + btn150to180daysCnt + grt180daysCnt;

		bean.setTtl(ttl);

		dispReportBeans.add(bean);

		return dispReportBeans;
	}

	public List<ReportBean> subDivWise(String id) {

		int btn30to60daysCnt = 0;
		int btn60to90daysCnt = 0;
		int btn90to120daysCnt = 0;
		int btn120to150daysCnt = 0;
		int btn150to180daysCnt = 0;
		int grt180daysCnt = 0;
		List<ReportBean> tempRepBean = new ArrayList<ReportBean>();
		dispReportBeans = new ArrayList<ReportBean>();
		if (id.equals("first")) {
			tempRepBean = reportBeans;
		} else {
			for (ReportBean reportBean : reportBeans) {
				if (reportBean.getDivId().equals(id)) {
					tempRepBean.add(reportBean);
				}
			}
		}
		// reportBeans = dao.viewData(" ");

		String sdId = "";
		for (ReportBean repBean : tempRepBean) {
			if (sdId.equals("")) {
				sdId = repBean.getSudDivId();
				bean = new ReportBean();
				btn30to60daysCnt = 0;
				btn60to90daysCnt = 0;
				btn90to120daysCnt = 0;
				btn120to150daysCnt = 0;
				btn150to180daysCnt = 0;
				grt180daysCnt = 0;
				bean.setLable(repBean.getSudDivName());
				bean.setId(repBean.getSudDivId());
				bean.setLvl("4");
				DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				Date sysDtae = new Date();
				Date wrkComDate;
				try {
					wrkComDate = dateFormat.parse(repBean.getWrkComplDate());
					int noofDays = subtractDays(sysDtae, wrkComDate);

					if (30 > noofDays && noofDays <= 60) {
						btn30to60daysCnt = btn30to60daysCnt + 1;
					} else if (60 > noofDays && noofDays <= 90) {
						btn60to90daysCnt = btn60to90daysCnt + 1;
					} else if (90 > noofDays && noofDays <= 120) {
						btn90to120daysCnt = btn90to120daysCnt + 1;
					} else if (120 > noofDays && noofDays <= 150) {
						btn120to150daysCnt = btn120to150daysCnt + 1;
					} else if (150 > noofDays && noofDays <= 180) {
						btn150to180daysCnt = btn150to180daysCnt + 1;
					} else if (180 < noofDays) {
						grt180daysCnt = grt180daysCnt + 1;
					}
					bean.setBtn30to60daysCnt(btn30to60daysCnt);
					bean.setBtn60to90daysCnt(btn60to90daysCnt);
					bean.setBtn90to120daysCnt(btn90to120daysCnt);
					bean.setBtn120to150daysCnt(btn120to150daysCnt);
					bean.setBtn150to180daysCnt(btn150to180daysCnt);
					bean.setGrt180daysCnt(grt180daysCnt);
				} catch (ParseException e) {
					e.printStackTrace();
				}

			} else {
				if (!sdId.equals(repBean.getSudDivId())) {
					int ttl = btn30to60daysCnt + btn60to90daysCnt
							+ btn90to120daysCnt + btn120to150daysCnt
							+ btn150to180daysCnt + grt180daysCnt;

					bean.setTtl(ttl);

					dispReportBeans.add(bean);
					sdId = repBean.getSudDivId();
					bean = new ReportBean();
					btn30to60daysCnt = 0;
					btn60to90daysCnt = 0;
					btn90to120daysCnt = 0;
					btn120to150daysCnt = 0;
					btn150to180daysCnt = 0;
					grt180daysCnt = 0;
					bean.setLable(repBean.getSudDivName());
					bean.setId(repBean.getSudDivId());
					bean.setLvl("4");
				}
				DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				Date sysDtae = new Date();
				Date wrkComDate;
				try {
					wrkComDate = dateFormat.parse(repBean.getWrkComplDate());

					int noofDays = subtractDays(sysDtae, wrkComDate);

					if (30 > noofDays && noofDays <= 60) {
						btn30to60daysCnt = btn30to60daysCnt + 1;
					} else if (60 > noofDays && noofDays <= 90) {
						btn60to90daysCnt = btn60to90daysCnt + 1;
					} else if (90 > noofDays && noofDays <= 120) {
						btn90to120daysCnt = btn90to120daysCnt + 1;
					} else if (120 > noofDays && noofDays <= 150) {
						btn120to150daysCnt = btn120to150daysCnt + 1;
					} else if (150 > noofDays && noofDays <= 180) {
						btn150to180daysCnt = btn150to180daysCnt + 1;
					} else if (180 < noofDays) {
						grt180daysCnt = grt180daysCnt + 1;
					}
					bean.setBtn30to60daysCnt(btn30to60daysCnt);
					bean.setBtn60to90daysCnt(btn60to90daysCnt);
					bean.setBtn90to120daysCnt(btn90to120daysCnt);
					bean.setBtn120to150daysCnt(btn120to150daysCnt);
					bean.setBtn150to180daysCnt(btn150to180daysCnt);
					bean.setGrt180daysCnt(grt180daysCnt);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		int ttl = btn30to60daysCnt + btn60to90daysCnt + btn90to120daysCnt
				+ btn120to150daysCnt + btn150to180daysCnt + grt180daysCnt;

		bean.setTtl(ttl);
		dispReportBeans.add(bean);
		return dispReportBeans;
	}

	public List<ReportBean> divWise(String id) {
		int btn30to60daysCnt = 0;
		int btn60to90daysCnt = 0;
		int btn90to120daysCnt = 0;
		int btn120to150daysCnt = 0;
		int btn150to180daysCnt = 0;
		int grt180daysCnt = 0;
		List<ReportBean> tempRepBean = new ArrayList<ReportBean>();
		dispReportBeans = new ArrayList<ReportBean>();
		if (id.equals("first")) {
			tempRepBean = reportBeans;
		} else {
			for (ReportBean reportBean : reportBeans) {
				if (reportBean.getCircleId().equals(id)) {
					tempRepBean.add(reportBean);
				}
			}
		}
		// reportBeans = dao.viewData(" ");

		String dId = "";
		for (ReportBean repBean : tempRepBean) {
			if (dId.equals("")) {
				dId = repBean.getDivId();
				bean = new ReportBean();
				btn30to60daysCnt = 0;
				btn60to90daysCnt = 0;
				btn90to120daysCnt = 0;
				btn120to150daysCnt = 0;
				btn150to180daysCnt = 0;
				grt180daysCnt = 0;
				bean.setLable(repBean.getDivName());
				bean.setId(repBean.getDivId());
				bean.setLvl("3");
				DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				Date sysDtae = new Date();
				Date wrkComDate;
				try {
					wrkComDate = dateFormat.parse(repBean.getWrkComplDate());

					int noofDays = subtractDays(sysDtae, wrkComDate);

					if (30 > noofDays && noofDays <= 60) {
						btn30to60daysCnt = btn30to60daysCnt + 1;
					} else if (60 > noofDays && noofDays <= 90) {
						btn60to90daysCnt = btn60to90daysCnt + 1;
					} else if (90 > noofDays && noofDays <= 120) {
						btn90to120daysCnt = btn90to120daysCnt + 1;
					} else if (120 > noofDays && noofDays <= 150) {
						btn120to150daysCnt = btn120to150daysCnt + 1;
					} else if (150 > noofDays && noofDays <= 180) {
						btn150to180daysCnt = btn150to180daysCnt + 1;
					} else if (180 < noofDays) {
						grt180daysCnt = grt180daysCnt + 1;
					}
					bean.setBtn30to60daysCnt(btn30to60daysCnt);
					bean.setBtn60to90daysCnt(btn60to90daysCnt);
					bean.setBtn90to120daysCnt(btn90to120daysCnt);
					bean.setBtn120to150daysCnt(btn120to150daysCnt);
					bean.setBtn150to180daysCnt(btn150to180daysCnt);
					bean.setGrt180daysCnt(grt180daysCnt);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else {
				if (!dId.equals(repBean.getDivId())) {
					int ttl = btn30to60daysCnt + btn60to90daysCnt
							+ btn90to120daysCnt + btn120to150daysCnt
							+ btn150to180daysCnt + grt180daysCnt;

					bean.setTtl(ttl);
					dispReportBeans.add(bean);
					dId = repBean.getDivId();
					bean = new ReportBean();
					btn30to60daysCnt = 0;
					btn60to90daysCnt = 0;
					btn90to120daysCnt = 0;
					btn120to150daysCnt = 0;
					btn150to180daysCnt = 0;
					grt180daysCnt = 0;
					bean.setLable(repBean.getDivName());
					bean.setId(repBean.getDivId());
					bean.setLvl("3");
				}
				DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				Date sysDtae = new Date();
				Date wrkComDate;
				try {
					wrkComDate = dateFormat.parse(repBean.getWrkComplDate());

					int noofDays = subtractDays(sysDtae, wrkComDate);

					if (30 > noofDays && noofDays <= 60) {
						btn30to60daysCnt = btn30to60daysCnt + 1;
					} else if (60 > noofDays && noofDays <= 90) {
						btn60to90daysCnt = btn60to90daysCnt + 1;
					} else if (90 > noofDays && noofDays <= 120) {
						btn90to120daysCnt = btn90to120daysCnt + 1;
					} else if (120 > noofDays && noofDays <= 150) {
						btn120to150daysCnt = btn120to150daysCnt + 1;
					} else if (150 > noofDays && noofDays <= 180) {
						btn150to180daysCnt = btn150to180daysCnt + 1;
					} else if (180 < noofDays) {
						grt180daysCnt = grt180daysCnt + 1;
					}
					bean.setBtn30to60daysCnt(btn30to60daysCnt);
					bean.setBtn60to90daysCnt(btn60to90daysCnt);
					bean.setBtn90to120daysCnt(btn90to120daysCnt);
					bean.setBtn120to150daysCnt(btn120to150daysCnt);
					bean.setBtn150to180daysCnt(btn150to180daysCnt);
					bean.setGrt180daysCnt(grt180daysCnt);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		int ttl = btn30to60daysCnt + btn60to90daysCnt + btn90to120daysCnt
				+ btn120to150daysCnt + btn150to180daysCnt + grt180daysCnt;
		bean.setTtl(ttl);
		dispReportBeans.add(bean);

		return dispReportBeans;
	}

	public List<ReportBean> circleWise(String id) {
		int btn30to60daysCnt = 0;
		int btn60to90daysCnt = 0;
		int btn90to120daysCnt = 0;
		int btn120to150daysCnt = 0;
		int btn150to180daysCnt = 0;
		int grt180daysCnt = 0;
		List<ReportBean> tempRepBean = new ArrayList<ReportBean>();
		dispReportBeans = new ArrayList<ReportBean>();
		if (id.equals("first")) {
			tempRepBean = reportBeans;
		} else {
			for (ReportBean reportBean : reportBeans) {
				if (reportBean.getZoneId().equals(id)) {
					tempRepBean.add(reportBean);
				}
			}
		}
		// reportBeans = dao.viewData(" ");

		String cId = "";
		for (ReportBean repBean : tempRepBean) {
			if (cId.equals("")) {
				cId = repBean.getCircleId();
				bean = new ReportBean();
				btn30to60daysCnt = 0;
				btn60to90daysCnt = 0;
				btn90to120daysCnt = 0;
				btn120to150daysCnt = 0;
				btn150to180daysCnt = 0;
				grt180daysCnt = 0;
				bean.setLable(repBean.getCircleName());
				bean.setId(repBean.getCircleId());
				bean.setLvl("2");
				DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				Date sysDtae = new Date();
				Date wrkComDate;
				try {
					wrkComDate = dateFormat.parse(repBean.getWrkComplDate());

					int noofDays = subtractDays(sysDtae, wrkComDate);

					if (30 > noofDays && noofDays <= 60) {
						btn30to60daysCnt = btn30to60daysCnt + 1;
					} else if (60 > noofDays && noofDays <= 90) {
						btn60to90daysCnt = btn60to90daysCnt + 1;
					} else if (90 > noofDays && noofDays <= 120) {
						btn90to120daysCnt = btn90to120daysCnt + 1;
					} else if (120 > noofDays && noofDays <= 150) {
						btn120to150daysCnt = btn120to150daysCnt + 1;
					} else if (150 > noofDays && noofDays <= 180) {
						btn150to180daysCnt = btn150to180daysCnt + 1;
					} else if (180 < noofDays) {
						grt180daysCnt = grt180daysCnt + 1;
					}
					bean.setBtn30to60daysCnt(btn30to60daysCnt);
					bean.setBtn60to90daysCnt(btn60to90daysCnt);
					bean.setBtn90to120daysCnt(btn90to120daysCnt);
					bean.setBtn120to150daysCnt(btn120to150daysCnt);
					bean.setBtn150to180daysCnt(btn150to180daysCnt);
					bean.setGrt180daysCnt(grt180daysCnt);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else {
				if (!cId.equals(repBean.getCircleId())) {
					int ttl = btn30to60daysCnt + btn60to90daysCnt
							+ btn90to120daysCnt + btn120to150daysCnt
							+ btn150to180daysCnt + grt180daysCnt;

					bean.setTtl(ttl);
					dispReportBeans.add(bean);
					cId = repBean.getCircleId();
					bean = new ReportBean();
					btn30to60daysCnt = 0;
					btn60to90daysCnt = 0;
					btn90to120daysCnt = 0;
					btn120to150daysCnt = 0;
					btn150to180daysCnt = 0;
					grt180daysCnt = 0;
					bean.setLable(repBean.getCircleName());
					bean.setId(repBean.getCircleId());
					bean.setLvl("2");
				}
				DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				Date sysDtae = new Date();
				Date wrkComDate;
				try {
					wrkComDate = dateFormat.parse(repBean.getWrkComplDate());

					int noofDays = subtractDays(sysDtae, wrkComDate);

					if (30 > noofDays && noofDays <= 60) {
						btn30to60daysCnt = btn30to60daysCnt + 1;
					} else if (60 > noofDays && noofDays <= 90) {
						btn60to90daysCnt = btn60to90daysCnt + 1;
					} else if (90 > noofDays && noofDays <= 120) {
						btn90to120daysCnt = btn90to120daysCnt + 1;
					} else if (120 > noofDays && noofDays <= 150) {
						btn120to150daysCnt = btn120to150daysCnt + 1;
					} else if (150 > noofDays && noofDays <= 180) {
						btn150to180daysCnt = btn150to180daysCnt + 1;
					} else if (180 < noofDays) {
						grt180daysCnt = grt180daysCnt + 1;
					}
					bean.setBtn30to60daysCnt(btn30to60daysCnt);
					bean.setBtn60to90daysCnt(btn60to90daysCnt);
					bean.setBtn90to120daysCnt(btn90to120daysCnt);
					bean.setBtn120to150daysCnt(btn120to150daysCnt);
					bean.setBtn150to180daysCnt(btn150to180daysCnt);
					bean.setGrt180daysCnt(grt180daysCnt);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		int ttl = btn30to60daysCnt + btn60to90daysCnt + btn90to120daysCnt
				+ btn120to150daysCnt + btn150to180daysCnt + grt180daysCnt;
		bean.setTtl(ttl);
		dispReportBeans.add(bean);

		return dispReportBeans;
	}

	public List<ReportBean> zoneWise(String id) {
		int btn30to60daysCnt = 0;
		int btn60to90daysCnt = 0;
		int btn90to120daysCnt = 0;
		int btn120to150daysCnt = 0;
		int btn150to180daysCnt = 0;
		int grt180daysCnt = 0;
		List<ReportBean> tempRepBean = new ArrayList<ReportBean>();
		dispReportBeans = new ArrayList<ReportBean>();
		if (id.equals("first")) {
			tempRepBean = reportBeans;
		} else {
			for (ReportBean reportBean : reportBeans) {
				if (reportBean.getZoneId().equals(id)) {
					tempRepBean.add(reportBean);
				}
			}
		}
		// reportBeans = dao.viewData(" ");

		String zId = "";
		for (ReportBean repBean : tempRepBean) {
			if (zId.equals("")) {
				zId = repBean.getZoneId();
				bean = new ReportBean();
				btn30to60daysCnt = 0;
				btn60to90daysCnt = 0;
				btn90to120daysCnt = 0;
				btn120to150daysCnt = 0;
				btn150to180daysCnt = 0;
				grt180daysCnt = 0;
				bean.setLable(repBean.getZoneName());
				bean.setId(repBean.getZoneId());
				bean.setLvl("1");
				DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				Date sysDtae = new Date();
				Date wrkComDate;
				try {
					wrkComDate = dateFormat.parse(repBean.getWrkComplDate());

					int noofDays = subtractDays(sysDtae, wrkComDate);

					if (30 > noofDays && noofDays <= 60) {
						btn30to60daysCnt = btn30to60daysCnt + 1;
					} else if (60 > noofDays && noofDays <= 90) {
						btn60to90daysCnt = btn60to90daysCnt + 1;
					} else if (90 > noofDays && noofDays <= 120) {
						btn90to120daysCnt = btn90to120daysCnt + 1;
					} else if (120 > noofDays && noofDays <= 150) {
						btn120to150daysCnt = btn120to150daysCnt + 1;
					} else if (150 > noofDays && noofDays <= 180) {
						btn150to180daysCnt = btn150to180daysCnt + 1;
					} else if (180 < noofDays) {
						grt180daysCnt = grt180daysCnt + 1;
					}
					bean.setBtn30to60daysCnt(btn30to60daysCnt);
					bean.setBtn60to90daysCnt(btn60to90daysCnt);
					bean.setBtn90to120daysCnt(btn90to120daysCnt);
					bean.setBtn120to150daysCnt(btn120to150daysCnt);
					bean.setBtn150to180daysCnt(btn150to180daysCnt);
					bean.setGrt180daysCnt(grt180daysCnt);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else {
				if (!zId.equals(repBean.getZoneId())) {
					int ttl = btn30to60daysCnt + btn60to90daysCnt
							+ btn90to120daysCnt + btn120to150daysCnt
							+ btn150to180daysCnt + grt180daysCnt;
					bean.setTtl(ttl);
					dispReportBeans.add(bean);
					zId = repBean.getZoneId();
					bean = new ReportBean();
					btn30to60daysCnt = 0;
					btn60to90daysCnt = 0;
					btn90to120daysCnt = 0;
					btn120to150daysCnt = 0;
					btn150to180daysCnt = 0;
					grt180daysCnt = 0;
					bean.setLable(repBean.getCircleName());
					bean.setId(repBean.getCircleId());
					bean.setLvl("1");
				}
				DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				Date sysDtae = new Date();
				Date wrkComDate;
				try {
					wrkComDate = dateFormat.parse(repBean.getWrkComplDate());

					int noofDays = subtractDays(sysDtae, wrkComDate);

					if (30 > noofDays && noofDays <= 60) {
						btn30to60daysCnt = btn30to60daysCnt + 1;
					} else if (60 > noofDays && noofDays <= 90) {
						btn60to90daysCnt = btn60to90daysCnt + 1;
					} else if (90 > noofDays && noofDays <= 120) {
						btn90to120daysCnt = btn90to120daysCnt + 1;
					} else if (120 > noofDays && noofDays <= 150) {
						btn120to150daysCnt = btn120to150daysCnt + 1;
					} else if (150 > noofDays && noofDays <= 180) {
						btn150to180daysCnt = btn150to180daysCnt + 1;
					} else if (180 < noofDays) {
						grt180daysCnt = grt180daysCnt + 1;
					}
					bean.setBtn30to60daysCnt(btn30to60daysCnt);
					bean.setBtn60to90daysCnt(btn60to90daysCnt);
					bean.setBtn90to120daysCnt(btn90to120daysCnt);
					bean.setBtn120to150daysCnt(btn120to150daysCnt);
					bean.setBtn150to180daysCnt(btn150to180daysCnt);
					bean.setGrt180daysCnt(grt180daysCnt);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		int ttl = btn30to60daysCnt + btn60to90daysCnt + btn90to120daysCnt
				+ btn120to150daysCnt + btn150to180daysCnt + grt180daysCnt;

		bean.setTtl(ttl);

		dispReportBeans.add(bean);

		return dispReportBeans;
	}

	private static String dateFormat(String date) {
		date = date.substring(0, 10);
		String[] field = date.split("-");
		return field[2] + "/" + field[1] + "/" + field[0];
	}

	public static int subtractDays(Date date1, Date date2) {
		GregorianCalendar gc1 = new GregorianCalendar();
		gc1.setTime(date1);
		GregorianCalendar gc2 = new GregorianCalendar();
		gc2.setTime(date2);

		int days1 = 0;
		int days2 = 0;
		int maxYear = Math.max(gc1.get(Calendar.YEAR), gc2.get(Calendar.YEAR));

		GregorianCalendar gctmp = (GregorianCalendar) gc1.clone();
		for (int f = gctmp.get(Calendar.YEAR); f < maxYear; f++) {
			days1 += gctmp.getActualMaximum(Calendar.DAY_OF_YEAR);
			gctmp.add(Calendar.YEAR, 1);
		}

		gctmp = (GregorianCalendar) gc2.clone();
		for (int f = gctmp.get(Calendar.YEAR); f < maxYear; f++) {
			days2 += gctmp.getActualMaximum(Calendar.DAY_OF_YEAR);
			gctmp.add(Calendar.YEAR, 1);
		}

		days1 += gc1.get(Calendar.DAY_OF_YEAR) - 1;
		days2 += gc2.get(Calendar.DAY_OF_YEAR) - 1;

		return (days1 - days2);
	}

	public static String dateToString(Date pDate) {
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd.MM.yyyy");
		String s = formatter.format(pDate);
		return s;

	}
}
