package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Util.AppUtil;
import com.Util.MysqlConnectionProvider;
import com.bean.InstalationBean;
import com.bean.InstallationMnthWiseBean;

public class InstalationRepDao {

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	List<InstalationBean> instalationBeans = null;
	List<InstalationBean> dispInstalationBeans = new ArrayList<InstalationBean>();
	List<InstallationMnthWiseBean> dispInstallationMnthWiseBeans = new ArrayList<InstallationMnthWiseBean>();
	InstalationBean bean = new InstalationBean();
	List<InstallationMnthWiseBean> mnthWiseBeans = new ArrayList<InstallationMnthWiseBean>();
	InstallationMnthWiseBean mnthWiseBean = null;

	public List<InstalationBean> viewData(String role, String roleBelogs, String year) {
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

		instalationBeans = new ArrayList<InstalationBean>();
		// ReportBean reportBean = null;
		try {
			String query = "SELECT "
							+ "i.INSTL_ID,"
							+ " th.TH_ID,"
							+ " i.INSTL_IDEN_FLAG,"
							+ " i.INSTL_PRINCIPAL,"
							+ " i.INSTL_INTEREST,"
							+ " (i.INSTL_PRINCIPAL + i.INSTL_INTEREST) AS TOTAL,"
							+ " th.TH_METRD_WSUPPLY,"
							+ " th.TH_UNMETRD_WSUPPLY,"
							+ " (th.TH_METRD_WSUPPLY + th.TH_UNMETRD_WSUPPLY) AS TOTAL,"
							+ " th.TH_MONTH,"
							+ " th.TH_YEAR,"
							+ " th.TH_SECTN_ID,"
							+ " s.SECTN_NAME,"
							+ " sd.SUBDIV_ID,"
							+ " sd.SUBDIV_NAME,"
							+ " d.DIV_ID,"
							+ " d.DIV_NAME,"
							+ " c.CRCL_ID,"
							+ " c.CRCL_NAME,"
							+ " z.ZONE_ID,"
							+ " z.ZONE_NAME"
							+ " FROM instalation i"
							+ " JOIN tran_head th"
							+ " ON i.INSTL_TH_ID = th.TH_ID"
							+ " JOIN section s"
							+ " ON th.TH_SECTN_ID = s.SECTN_ID"
							+ " JOIN sub_division sd"
							+ " ON s.SECTN_SUBDIV_ID = sd.SUBDIV_ID"
							+ " JOIN division d"
							+ " ON sd.SUBDIV_DIV_ID = d.DIV_ID"
							+ " JOIN circle c"
							+ " ON d.DIV_CIRCLE_ID = c.CRCL_ID"
							+ " JOIN zone z"
							+ " ON c.CRCL_ZONE_ID = z.ZONE_ID"
							+ " WHERE th.TH_YEAR = " + year + " " + wherClause
							+ " ORDER BY th.TH_MONTH, th.TH_SECTN_ID, i.INSTL_IDEN_FLAG";
			connection = MysqlConnectionProvider.getNewConnection();
			preparedStatement = connection.prepareStatement(query);

			// int i = 1;
            System.out.println(query);
			rs = preparedStatement.executeQuery();
			int thId = 0;
			boolean isRsEmpty = true;
			while (rs.next()) {
				isRsEmpty = false;
				if (thId == 0) {
					thId = rs.getInt(2);
					bean = new InstalationBean();
					bean.setMtrWtrSply(rs.getInt(7));
					bean.setUnMtrWtrSply(rs.getInt(8));
					bean.setTotal(rs.getInt(9));
					bean.setMnthId(rs.getString(10));
					bean.setMnthNm(AppUtil.getMonth(rs.getString(10)));
					bean.setYearId(rs.getString(11));
					bean.setSecId(rs.getString(12));
					bean.setSecNme(rs.getString(13));
					bean.setSubDivId(rs.getString(14));
					bean.setSubDivNme(rs.getString(15));
					bean.setDivId(rs.getString(16));
					bean.setDivNme(rs.getString(17));
					bean.setCrlId(rs.getString(18));
					bean.setCrlNme(rs.getString(19));
					bean.setZnId(rs.getString(20));
					bean.setZnNme(rs.getString(21));
					bean.setLvl("5");

					if (rs.getInt(3) == 1) {
						bean.setOpenBalPrinAmt(rs.getDouble(4));
						bean.setOpenBalIntAmt(rs.getDouble(5));
						bean.setOpenBalTtlAmt(rs.getDouble(6));
					} else if (rs.getInt(3) == 2) {
						bean.setDemdBalPrinAmt(rs.getDouble(4));
						bean.setDemdBalIntAmt(rs.getDouble(5));
						bean.setDemdBalTtlAmt(rs.getDouble(6));
					} else if (rs.getInt(3) == 3) {
						bean.setClctnBalPrinAmt(rs.getDouble(4));
						bean.setClctnBalIntAmt(rs.getDouble(5));
						bean.setClctnBalTtlAmt(rs.getDouble(6));
					} else if (rs.getInt(3) == 4) {
						bean.setClosBalPrinAmt(rs.getDouble(4));
						bean.setClosBalIntAmt(rs.getDouble(5));
						bean.setClosBalTtlAmt(rs.getDouble(6));
					}

				} else {
					if (thId != rs.getInt(2)) {
						thId = rs.getInt(2);
						instalationBeans.add(bean);
						bean = new InstalationBean();
						bean.setMtrWtrSply(rs.getInt(7));
						bean.setUnMtrWtrSply(rs.getInt(8));
						bean.setTotal(rs.getInt(9));
						bean.setMnthId(rs.getString(10));
						bean.setMnthNm(AppUtil.getMonth(rs.getString(10)));
						bean.setYearId(rs.getString(11));
						bean.setSecId(rs.getString(12));
						bean.setSecNme(rs.getString(13));
						bean.setSubDivId(rs.getString(14));
						bean.setSubDivNme(rs.getString(15));
						bean.setDivId(rs.getString(16));
						bean.setDivNme(rs.getString(17));
						bean.setCrlId(rs.getString(18));
						bean.setCrlNme(rs.getString(19));
						bean.setZnId(rs.getString(20));
						bean.setZnNme(rs.getString(21));
						bean.setLvl("5");

					}
					if (rs.getInt(3) == 1) {
						bean.setOpenBalPrinAmt(rs.getDouble(4));
						bean.setOpenBalIntAmt(rs.getDouble(5));
						bean.setOpenBalTtlAmt(rs.getDouble(6));
					} else if (rs.getInt(3) == 2) {
						bean.setDemdBalPrinAmt(rs.getDouble(4));
						bean.setDemdBalIntAmt(rs.getDouble(5));
						bean.setDemdBalTtlAmt(rs.getDouble(6));
					} else if (rs.getInt(3) == 3) {
						bean.setClctnBalPrinAmt(rs.getDouble(4));
						bean.setClctnBalIntAmt(rs.getDouble(5));
						bean.setClctnBalTtlAmt(rs.getDouble(6));
					} else if (rs.getInt(3) == 4) {
						bean.setClosBalPrinAmt(rs.getDouble(4));
						bean.setClosBalIntAmt(rs.getDouble(5));
						bean.setClosBalTtlAmt(rs.getDouble(6));
					}

				}
			}
			if (!isRsEmpty) {
				instalationBeans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return instalationBeans;
	}

	public List<InstalationBean> secWise(List<InstalationBean> beans) {

		int secId = 0;
		dispInstalationBeans = null;
		dispInstalationBeans = new ArrayList<InstalationBean>();
		InstalationBean instlBean = null;
		for (InstalationBean instalationBean : beans) {
			if (secId == 0) {
				secId = Integer.parseInt(instalationBean.getSecId());
				instlBean = new InstalationBean();
				instlBean.setMnthId(instalationBean.getMnthId());
				instlBean.setMnthNm(instalationBean.getMnthNm());
				instlBean.setYearId(instalationBean.getYearId());

				instlBean.setId(instalationBean.getSecId());
				instlBean.setLable(instalationBean.getSecNme());

				instlBean.setMtrWtrSply(instlBean.getMtrWtrSply() + instalationBean.getMtrWtrSply());
				instlBean.setUnMtrWtrSply(instlBean.getUnMtrWtrSply() + instalationBean.getUnMtrWtrSply());
				instlBean.setTotal(instlBean.getTotal() + instalationBean.getTotal());
				instlBean.setLvl("5");

				instlBean.setOpenBalPrinAmt(instlBean.getOpenBalPrinAmt()
						+ instalationBean.getOpenBalPrinAmt());
				instlBean.setOpenBalIntAmt(instlBean.getOpenBalIntAmt()
						+ instalationBean.getOpenBalIntAmt());
				instlBean.setOpenBalTtlAmt(instlBean.getOpenBalTtlAmt()
						+ instalationBean.getOpenBalTtlAmt());

				instlBean.setDemdBalPrinAmt(instlBean.getDemdBalPrinAmt()
						+ instalationBean.getDemdBalPrinAmt());
				instlBean.setDemdBalIntAmt(instlBean.getDemdBalIntAmt()
						+ instalationBean.getDemdBalIntAmt());
				instlBean.setDemdBalTtlAmt(instlBean.getDemdBalTtlAmt()
						+ instalationBean.getDemdBalTtlAmt());

				instlBean.setClctnBalPrinAmt(instlBean.getClctnBalPrinAmt()
						+ instalationBean.getClctnBalPrinAmt());
				instlBean.setClctnBalIntAmt(instlBean.getClctnBalIntAmt()
						+ instalationBean.getClctnBalIntAmt());
				instlBean.setClctnBalTtlAmt(instlBean.getClctnBalTtlAmt()
						+ instalationBean.getClctnBalTtlAmt());

				instlBean.setClosBalPrinAmt(instlBean.getClosBalPrinAmt()
						+ instalationBean.getClosBalPrinAmt());
				instlBean.setClosBalIntAmt(instlBean.getClosBalIntAmt()
						+ instalationBean.getClosBalIntAmt());
				instlBean.setClosBalTtlAmt(instlBean.getClosBalTtlAmt()
						+ instalationBean.getClosBalTtlAmt());
			} else {
				if (secId != Integer.parseInt(instalationBean.getSecId())) {
					secId = Integer.parseInt(instalationBean.getSecId());
					dispInstalationBeans.add(instlBean);
					instlBean = new InstalationBean();
					instlBean.setMnthId(instalationBean.getMnthId());
					instlBean.setMnthNm(instalationBean.getMnthNm());
					instlBean.setYearId(instalationBean.getYearId());
					instlBean.setId(instalationBean.getSecId());
					instlBean.setLable(instalationBean.getSecNme());
					instlBean.setLvl("5");
				}

				instlBean.setMtrWtrSply(instlBean.getMtrWtrSply() + instalationBean.getMtrWtrSply());
				instlBean.setUnMtrWtrSply(instlBean.getUnMtrWtrSply() + instalationBean.getUnMtrWtrSply());
				instlBean.setTotal(instlBean.getTotal() + instalationBean.getTotal());
				instlBean.setOpenBalPrinAmt(instlBean.getOpenBalPrinAmt()
						+ instalationBean.getOpenBalPrinAmt());
				instlBean.setOpenBalIntAmt(instlBean.getOpenBalIntAmt()
						+ instalationBean.getOpenBalIntAmt());
				instlBean.setOpenBalTtlAmt(instlBean.getOpenBalTtlAmt()
						+ instalationBean.getOpenBalTtlAmt());

				instlBean.setDemdBalPrinAmt(instlBean.getDemdBalPrinAmt()
						+ instalationBean.getDemdBalPrinAmt());
				instlBean.setDemdBalIntAmt(instlBean.getDemdBalIntAmt()
						+ instalationBean.getDemdBalIntAmt());
				instlBean.setDemdBalTtlAmt(instlBean.getDemdBalTtlAmt()
						+ instalationBean.getDemdBalTtlAmt());

				instlBean.setClctnBalPrinAmt(instlBean.getClctnBalPrinAmt()
						+ instalationBean.getClctnBalPrinAmt());
				instlBean.setClctnBalIntAmt(instlBean.getClctnBalIntAmt()
						+ instalationBean.getClctnBalIntAmt());
				instlBean.setClctnBalTtlAmt(instlBean.getClctnBalTtlAmt()
						+ instalationBean.getClctnBalTtlAmt());

				instlBean.setClosBalPrinAmt(instlBean.getClosBalPrinAmt()
						+ instalationBean.getClosBalPrinAmt());
				instlBean.setClosBalIntAmt(instlBean.getClosBalIntAmt()
						+ instalationBean.getClosBalIntAmt());
				instlBean.setClosBalTtlAmt(instlBean.getClosBalTtlAmt()
						+ instalationBean.getClosBalTtlAmt());

			}
		}
		dispInstalationBeans.add(instlBean);

		return dispInstalationBeans;
	}

	public List<InstalationBean> subDivWise(List<InstalationBean> beans) {
		int subDivId = 0;
		dispInstalationBeans = null;
		dispInstalationBeans = new ArrayList<InstalationBean>();
		InstalationBean instlBean = null;
		for (InstalationBean instalationBean : beans) {
			if (subDivId == 0) {
				subDivId = Integer.parseInt(instalationBean.getSubDivId());
				instlBean = new InstalationBean();
				instlBean.setMnthId(instalationBean.getMnthId());
				instlBean.setMnthNm(instalationBean.getMnthNm());
				instlBean.setYearId(instalationBean.getYearId());
				instlBean.setId(instalationBean.getSubDivId());
				instlBean.setLable(instalationBean.getSubDivNme());
				instlBean.setMtrWtrSply(instlBean.getMtrWtrSply() + instalationBean.getMtrWtrSply());
				instlBean.setUnMtrWtrSply(instlBean.getUnMtrWtrSply() + instalationBean.getUnMtrWtrSply());
				instlBean.setTotal(instlBean.getTotal() + instalationBean.getTotal());
				instlBean.setLvl("4");

				instlBean.setOpenBalPrinAmt(instlBean.getOpenBalPrinAmt()
						+ instalationBean.getOpenBalPrinAmt());
				instlBean.setOpenBalIntAmt(instlBean.getOpenBalIntAmt()
						+ instalationBean.getOpenBalIntAmt());
				instlBean.setOpenBalTtlAmt(instlBean.getOpenBalTtlAmt()
						+ instalationBean.getOpenBalTtlAmt());

				instlBean.setDemdBalPrinAmt(instlBean.getDemdBalPrinAmt()
						+ instalationBean.getDemdBalPrinAmt());
				instlBean.setDemdBalIntAmt(instlBean.getDemdBalIntAmt()
						+ instalationBean.getDemdBalIntAmt());
				instlBean.setDemdBalTtlAmt(instlBean.getDemdBalTtlAmt()
						+ instalationBean.getDemdBalTtlAmt());

				instlBean.setClctnBalPrinAmt(instlBean.getClctnBalPrinAmt()
						+ instalationBean.getClctnBalPrinAmt());
				instlBean.setClctnBalIntAmt(instlBean.getClctnBalIntAmt()
						+ instalationBean.getClctnBalIntAmt());
				instlBean.setClctnBalTtlAmt(instlBean.getClctnBalTtlAmt()
						+ instalationBean.getClctnBalTtlAmt());

				instlBean.setClosBalPrinAmt(instlBean.getClosBalPrinAmt()
						+ instalationBean.getClosBalPrinAmt());
				instlBean.setClosBalIntAmt(instlBean.getClosBalIntAmt()
						+ instalationBean.getClosBalIntAmt());
				instlBean.setClosBalTtlAmt(instlBean.getClosBalTtlAmt()
						+ instalationBean.getClosBalTtlAmt());
			} else {
				if (subDivId != Integer.parseInt(instalationBean.getSubDivId())) {
					subDivId = Integer.parseInt(instalationBean.getSubDivId());
					dispInstalationBeans.add(instlBean);
					instlBean = new InstalationBean();
					instlBean.setMnthId(instalationBean.getMnthId());
					instlBean.setMnthNm(instalationBean.getMnthNm());
					instlBean.setYearId(instalationBean.getYearId());
					instlBean.setId(instalationBean.getSubDivId());
					instlBean.setLable(instalationBean.getSubDivNme());
					instlBean.setLvl("4");
				}
				instlBean.setMtrWtrSply(instlBean.getMtrWtrSply() + instalationBean.getMtrWtrSply());
				instlBean.setUnMtrWtrSply(instlBean.getUnMtrWtrSply() + instalationBean.getUnMtrWtrSply());
				instlBean.setTotal(instlBean.getTotal() + instalationBean.getTotal());

				instlBean.setOpenBalPrinAmt(instlBean.getOpenBalPrinAmt()
						+ instalationBean.getOpenBalPrinAmt());
				instlBean.setOpenBalIntAmt(instlBean.getOpenBalIntAmt()
						+ instalationBean.getOpenBalIntAmt());
				instlBean.setOpenBalTtlAmt(instlBean.getOpenBalTtlAmt()
						+ instalationBean.getOpenBalTtlAmt());

				instlBean.setDemdBalPrinAmt(instlBean.getDemdBalPrinAmt()
						+ instalationBean.getDemdBalPrinAmt());
				instlBean.setDemdBalIntAmt(instlBean.getDemdBalIntAmt()
						+ instalationBean.getDemdBalIntAmt());
				instlBean.setDemdBalTtlAmt(instlBean.getDemdBalTtlAmt()
						+ instalationBean.getDemdBalTtlAmt());

				instlBean.setClctnBalPrinAmt(instlBean.getClctnBalPrinAmt()
						+ instalationBean.getClctnBalPrinAmt());
				instlBean.setClctnBalIntAmt(instlBean.getClctnBalIntAmt()
						+ instalationBean.getClctnBalIntAmt());
				instlBean.setClctnBalTtlAmt(instlBean.getClctnBalTtlAmt()
						+ instalationBean.getClctnBalTtlAmt());

				instlBean.setClosBalPrinAmt(instlBean.getClosBalPrinAmt()
						+ instalationBean.getClosBalPrinAmt());
				instlBean.setClosBalIntAmt(instlBean.getClosBalIntAmt()
						+ instalationBean.getClosBalIntAmt());
				instlBean.setClosBalTtlAmt(instlBean.getClosBalTtlAmt()
						+ instalationBean.getClosBalTtlAmt());

			}
		}
		dispInstalationBeans.add(instlBean);

		return dispInstalationBeans;
	}

	public List<InstalationBean> divWise(List<InstalationBean> beans) {
		int divId = 0;
		dispInstalationBeans = null;
		dispInstalationBeans = new ArrayList<InstalationBean>();
		InstalationBean instlBean = null;
		for (InstalationBean instalationBean : beans) {
			if (divId == 0) {
				divId = Integer.parseInt(instalationBean.getDivId());
				instlBean = new InstalationBean();
				instlBean.setMnthId(instalationBean.getMnthId());
				instlBean.setMnthNm(instalationBean.getMnthNm());
				instlBean.setYearId(instalationBean.getYearId());
				instlBean.setId(instalationBean.getDivId());
				instlBean.setLable(instalationBean.getDivNme());
				instlBean.setMtrWtrSply(instlBean.getMtrWtrSply() + instalationBean.getMtrWtrSply());
				instlBean.setUnMtrWtrSply(instlBean.getUnMtrWtrSply() + instalationBean.getUnMtrWtrSply());
				instlBean.setTotal(instlBean.getTotal() + instalationBean.getTotal());
				instlBean.setLvl("3");

				instlBean.setOpenBalPrinAmt(instlBean.getOpenBalPrinAmt()
						+ instalationBean.getOpenBalPrinAmt());
				instlBean.setOpenBalIntAmt(instlBean.getOpenBalIntAmt()
						+ instalationBean.getOpenBalIntAmt());
				instlBean.setOpenBalTtlAmt(instlBean.getOpenBalTtlAmt()
						+ instalationBean.getOpenBalTtlAmt());

				instlBean.setDemdBalPrinAmt(instlBean.getDemdBalPrinAmt()
						+ instalationBean.getDemdBalPrinAmt());
				instlBean.setDemdBalIntAmt(instlBean.getDemdBalIntAmt()
						+ instalationBean.getDemdBalIntAmt());
				instlBean.setDemdBalTtlAmt(instlBean.getDemdBalTtlAmt()
						+ instalationBean.getDemdBalTtlAmt());

				instlBean.setClctnBalPrinAmt(instlBean.getClctnBalPrinAmt()
						+ instalationBean.getClctnBalPrinAmt());
				instlBean.setClctnBalIntAmt(instlBean.getClctnBalIntAmt()
						+ instalationBean.getClctnBalIntAmt());
				instlBean.setClctnBalTtlAmt(instlBean.getClctnBalTtlAmt()
						+ instalationBean.getClctnBalTtlAmt());

				instlBean.setClosBalPrinAmt(instlBean.getClosBalPrinAmt()
						+ instalationBean.getClosBalPrinAmt());
				instlBean.setClosBalIntAmt(instlBean.getClosBalIntAmt()
						+ instalationBean.getClosBalIntAmt());
				instlBean.setClosBalTtlAmt(instlBean.getClosBalTtlAmt()
						+ instalationBean.getClosBalTtlAmt());
			} else {
				if (divId != Integer.parseInt(instalationBean.getDivId())) {
					divId = Integer.parseInt(instalationBean.getDivId());
					dispInstalationBeans.add(instlBean);
					instlBean = new InstalationBean();
					instlBean.setMnthId(instalationBean.getMnthId());
					instlBean.setMnthNm(instalationBean.getMnthNm());
					instlBean.setYearId(instalationBean.getYearId());
					instlBean.setId(instalationBean.getDivId());
					instlBean.setLable(instalationBean.getDivNme());
					instlBean.setLvl("3");

				}
				instlBean.setMtrWtrSply(instlBean.getMtrWtrSply() + instalationBean.getMtrWtrSply());
				instlBean.setUnMtrWtrSply(instlBean.getUnMtrWtrSply() + instalationBean.getUnMtrWtrSply());
				instlBean.setTotal(instlBean.getTotal() + instalationBean.getTotal());

				instlBean.setOpenBalPrinAmt(instlBean.getOpenBalPrinAmt()
						+ instalationBean.getOpenBalPrinAmt());
				instlBean.setOpenBalIntAmt(instlBean.getOpenBalIntAmt()
						+ instalationBean.getOpenBalIntAmt());
				instlBean.setOpenBalTtlAmt(instlBean.getOpenBalTtlAmt()
						+ instalationBean.getOpenBalTtlAmt());

				instlBean.setDemdBalPrinAmt(instlBean.getDemdBalPrinAmt()
						+ instalationBean.getDemdBalPrinAmt());
				instlBean.setDemdBalIntAmt(instlBean.getDemdBalIntAmt()
						+ instalationBean.getDemdBalIntAmt());
				instlBean.setDemdBalTtlAmt(instlBean.getDemdBalTtlAmt()
						+ instalationBean.getDemdBalTtlAmt());

				instlBean.setClctnBalPrinAmt(instlBean.getClctnBalPrinAmt()
						+ instalationBean.getClctnBalPrinAmt());
				instlBean.setClctnBalIntAmt(instlBean.getClctnBalIntAmt()
						+ instalationBean.getClctnBalIntAmt());
				instlBean.setClctnBalTtlAmt(instlBean.getClctnBalTtlAmt()
						+ instalationBean.getClctnBalTtlAmt());

				instlBean.setClosBalPrinAmt(instlBean.getClosBalPrinAmt()
						+ instalationBean.getClosBalPrinAmt());
				instlBean.setClosBalIntAmt(instlBean.getClosBalIntAmt()
						+ instalationBean.getClosBalIntAmt());
				instlBean.setClosBalTtlAmt(instlBean.getClosBalTtlAmt()
						+ instalationBean.getClosBalTtlAmt());

			}
		}
		dispInstalationBeans.add(instlBean);

		return dispInstalationBeans;
	}

	public List<InstalationBean> circleWise(List<InstalationBean> beans) {
		int crlId = 0;
		dispInstalationBeans = null;
		dispInstalationBeans = new ArrayList<InstalationBean>();
		InstalationBean instlBean = null;
		for (InstalationBean instalationBean : beans) {
			if (crlId == 0) {
				crlId = Integer.parseInt(instalationBean.getCrlId());
				instlBean = new InstalationBean();
				instlBean.setMnthId(instalationBean.getMnthId());
				instlBean.setMnthNm(instalationBean.getMnthNm());
				instlBean.setYearId(instalationBean.getYearId());
				instlBean.setId(instalationBean.getCrlId());
				instlBean.setLable(instalationBean.getCrlNme());
				instlBean.setMtrWtrSply(instlBean.getMtrWtrSply() + instalationBean.getMtrWtrSply());
				instlBean.setUnMtrWtrSply(instlBean.getUnMtrWtrSply() + instalationBean.getUnMtrWtrSply());
				instlBean.setTotal(instlBean.getTotal() + instalationBean.getTotal());
				instlBean.setLvl("2");

				instlBean.setOpenBalPrinAmt(instlBean.getOpenBalPrinAmt()
						+ instalationBean.getOpenBalPrinAmt());
				instlBean.setOpenBalIntAmt(instlBean.getOpenBalIntAmt()
						+ instalationBean.getOpenBalIntAmt());
				instlBean.setOpenBalTtlAmt(instlBean.getOpenBalTtlAmt()
						+ instalationBean.getOpenBalTtlAmt());

				instlBean.setDemdBalPrinAmt(instlBean.getDemdBalPrinAmt()
						+ instalationBean.getDemdBalPrinAmt());
				instlBean.setDemdBalIntAmt(instlBean.getDemdBalIntAmt()
						+ instalationBean.getDemdBalIntAmt());
				instlBean.setDemdBalTtlAmt(instlBean.getDemdBalTtlAmt()
						+ instalationBean.getDemdBalTtlAmt());

				instlBean.setClctnBalPrinAmt(instlBean.getClctnBalPrinAmt()
						+ instalationBean.getClctnBalPrinAmt());
				instlBean.setClctnBalIntAmt(instlBean.getClctnBalIntAmt()
						+ instalationBean.getClctnBalIntAmt());
				instlBean.setClctnBalTtlAmt(instlBean.getClctnBalTtlAmt()
						+ instalationBean.getClctnBalTtlAmt());

				instlBean.setClosBalPrinAmt(instlBean.getClosBalPrinAmt()
						+ instalationBean.getClosBalPrinAmt());
				instlBean.setClosBalIntAmt(instlBean.getClosBalIntAmt()
						+ instalationBean.getClosBalIntAmt());
				instlBean.setClosBalTtlAmt(instlBean.getClosBalTtlAmt()
						+ instalationBean.getClosBalTtlAmt());
			} else {
				if (crlId != Integer.parseInt(instalationBean.getCrlId())) {
					crlId = Integer.parseInt(instalationBean.getCrlId());
					dispInstalationBeans.add(instlBean);
					instlBean = new InstalationBean();
					instlBean.setMnthId(instalationBean.getMnthId());
					instlBean.setMnthNm(instalationBean.getMnthNm());
					instlBean.setYearId(instalationBean.getYearId());
					instlBean.setId(instalationBean.getCrlId());
					instlBean.setLable(instalationBean.getCrlNme());
					instlBean.setLvl("2");
				}
				instlBean.setMtrWtrSply(instlBean.getMtrWtrSply() + instalationBean.getMtrWtrSply());
				instlBean.setUnMtrWtrSply(instlBean.getUnMtrWtrSply() + instalationBean.getUnMtrWtrSply());
				instlBean.setTotal(instlBean.getTotal() + instalationBean.getTotal());

				instlBean.setOpenBalPrinAmt(instlBean.getOpenBalPrinAmt()
						+ instalationBean.getOpenBalPrinAmt());
				instlBean.setOpenBalIntAmt(instlBean.getOpenBalIntAmt()
						+ instalationBean.getOpenBalIntAmt());
				instlBean.setOpenBalTtlAmt(instlBean.getOpenBalTtlAmt()
						+ instalationBean.getOpenBalTtlAmt());

				instlBean.setDemdBalPrinAmt(instlBean.getDemdBalPrinAmt()
						+ instalationBean.getDemdBalPrinAmt());
				instlBean.setDemdBalIntAmt(instlBean.getDemdBalIntAmt()
						+ instalationBean.getDemdBalIntAmt());
				instlBean.setDemdBalTtlAmt(instlBean.getDemdBalTtlAmt()
						+ instalationBean.getDemdBalTtlAmt());

				instlBean.setClctnBalPrinAmt(instlBean.getClctnBalPrinAmt()
						+ instalationBean.getClctnBalPrinAmt());
				instlBean.setClctnBalIntAmt(instlBean.getClctnBalIntAmt()
						+ instalationBean.getClctnBalIntAmt());
				instlBean.setClctnBalTtlAmt(instlBean.getClctnBalTtlAmt()
						+ instalationBean.getClctnBalTtlAmt());

				instlBean.setClosBalPrinAmt(instlBean.getClosBalPrinAmt()
						+ instalationBean.getClosBalPrinAmt());
				instlBean.setClosBalIntAmt(instlBean.getClosBalIntAmt()
						+ instalationBean.getClosBalIntAmt());
				instlBean.setClosBalTtlAmt(instlBean.getClosBalTtlAmt()
						+ instalationBean.getClosBalTtlAmt());

			}
		}
		dispInstalationBeans.add(instlBean);

		return dispInstalationBeans;
	}

	public List<InstalationBean> zoneWise(List<InstalationBean> beans) {
		int znId = 0;
		dispInstalationBeans = null;
		dispInstalationBeans = new ArrayList<InstalationBean>();
		InstalationBean instlBean = null;
		for (InstalationBean instalationBean : beans) {
			if (znId == 0) {
				znId = Integer.parseInt(instalationBean.getZnId());
				instlBean = new InstalationBean();
				instlBean.setMnthId(instalationBean.getMnthId());
				instlBean.setMnthNm(instalationBean.getMnthNm());
				instlBean.setYearId(instalationBean.getYearId());
				instlBean.setId(instalationBean.getZnId());
				instlBean.setLable(instalationBean.getZnNme());
				instlBean.setMtrWtrSply(instlBean.getMtrWtrSply() + instalationBean.getMtrWtrSply());
				instlBean.setUnMtrWtrSply(instlBean.getUnMtrWtrSply() + instalationBean.getUnMtrWtrSply());
				instlBean.setTotal(instlBean.getTotal() + instalationBean.getTotal());
				instlBean.setLvl("1");

				instlBean.setOpenBalPrinAmt(instlBean.getOpenBalPrinAmt()
						+ instalationBean.getOpenBalPrinAmt());
				instlBean.setOpenBalIntAmt(instlBean.getOpenBalIntAmt()
						+ instalationBean.getOpenBalIntAmt());
				instlBean.setOpenBalTtlAmt(instlBean.getOpenBalTtlAmt()
						+ instalationBean.getOpenBalTtlAmt());

				instlBean.setDemdBalPrinAmt(instlBean.getDemdBalPrinAmt()
						+ instalationBean.getDemdBalPrinAmt());
				instlBean.setDemdBalIntAmt(instlBean.getDemdBalIntAmt()
						+ instalationBean.getDemdBalIntAmt());
				instlBean.setDemdBalTtlAmt(instlBean.getDemdBalTtlAmt()
						+ instalationBean.getDemdBalTtlAmt());

				instlBean.setClctnBalPrinAmt(instlBean.getClctnBalPrinAmt()
						+ instalationBean.getClctnBalPrinAmt());
				instlBean.setClctnBalIntAmt(instlBean.getClctnBalIntAmt()
						+ instalationBean.getClctnBalIntAmt());
				instlBean.setClctnBalTtlAmt(instlBean.getClctnBalTtlAmt()
						+ instalationBean.getClctnBalTtlAmt());

				instlBean.setClosBalPrinAmt(instlBean.getClosBalPrinAmt()
						+ instalationBean.getClosBalPrinAmt());
				instlBean.setClosBalIntAmt(instlBean.getClosBalIntAmt()
						+ instalationBean.getClosBalIntAmt());
				instlBean.setClosBalTtlAmt(instlBean.getClosBalTtlAmt()
						+ instalationBean.getClosBalTtlAmt());
			} else {
				if (znId != Integer.parseInt(instalationBean.getZnId())) {
					znId = Integer.parseInt(instalationBean.getZnId());
					dispInstalationBeans.add(instlBean);
					instlBean = new InstalationBean();
					instlBean.setMnthId(instalationBean.getMnthId());
					instlBean.setMnthNm(instalationBean.getMnthNm());
					instlBean.setYearId(instalationBean.getYearId());
					instlBean.setId(instalationBean.getZnId());
					instlBean.setLable(instalationBean.getZnNme());
					instlBean.setLvl("1");

				}
				instlBean.setMtrWtrSply(instlBean.getMtrWtrSply() + instalationBean.getMtrWtrSply());
				instlBean.setUnMtrWtrSply(instlBean.getUnMtrWtrSply() + instalationBean.getUnMtrWtrSply());
				instlBean.setTotal(instlBean.getTotal() + instalationBean.getTotal());

				instlBean.setOpenBalPrinAmt(instlBean.getOpenBalPrinAmt()
						+ instalationBean.getOpenBalPrinAmt());
				instlBean.setOpenBalIntAmt(instlBean.getOpenBalIntAmt()
						+ instalationBean.getOpenBalIntAmt());
				instlBean.setOpenBalTtlAmt(instlBean.getOpenBalTtlAmt()
						+ instalationBean.getOpenBalTtlAmt());

				instlBean.setDemdBalPrinAmt(instlBean.getDemdBalPrinAmt()
						+ instalationBean.getDemdBalPrinAmt());
				instlBean.setDemdBalIntAmt(instlBean.getDemdBalIntAmt()
						+ instalationBean.getDemdBalIntAmt());
				instlBean.setDemdBalTtlAmt(instlBean.getDemdBalTtlAmt()
						+ instalationBean.getDemdBalTtlAmt());

				instlBean.setClctnBalPrinAmt(instlBean.getClctnBalPrinAmt()
						+ instalationBean.getClctnBalPrinAmt());
				instlBean.setClctnBalIntAmt(instlBean.getClctnBalIntAmt()
						+ instalationBean.getClctnBalIntAmt());
				instlBean.setClctnBalTtlAmt(instlBean.getClctnBalTtlAmt()
						+ instalationBean.getClctnBalTtlAmt());

				instlBean.setClosBalPrinAmt(instlBean.getClosBalPrinAmt()
						+ instalationBean.getClosBalPrinAmt());
				instlBean.setClosBalIntAmt(instlBean.getClosBalIntAmt()
						+ instalationBean.getClosBalIntAmt());
				instlBean.setClosBalTtlAmt(instlBean.getClosBalTtlAmt()
						+ instalationBean.getClosBalTtlAmt());

			}
		}
		dispInstalationBeans.add(instlBean);

		return dispInstalationBeans;
	}

	public List<InstallationMnthWiseBean> monthWise(List<InstalationBean> beans, String role) {
		int mntId = 0;
		dispInstallationMnthWiseBeans = null;
		dispInstallationMnthWiseBeans = new ArrayList<InstallationMnthWiseBean>();
		InstallationMnthWiseBean instlMtnhBean = null;
		List<InstalationBean> lstInstalationBeans = null;
		for (InstalationBean instalationBean : beans) {
			if (mntId == 0) {
				mntId = Integer.parseInt(instalationBean.getMnthId());
				instlMtnhBean = new InstallationMnthWiseBean();
				instlMtnhBean.setMnthNm(instalationBean.getMnthNm());
				lstInstalationBeans = new ArrayList<InstalationBean>();

				lstInstalationBeans.add(instalationBean);

			} else {
				if (mntId != Integer.parseInt(instalationBean.getMnthId())) {
					mntId = Integer.parseInt(instalationBean.getMnthId());
					instlMtnhBean.setInstalationBeans(lstInstalationBeans);
					instlMtnhBean.setSize(lstInstalationBeans.size() + 1);
					dispInstallationMnthWiseBeans.add(instlMtnhBean);

					instlMtnhBean = new InstallationMnthWiseBean();
					instlMtnhBean.setMnthNm(instalationBean.getMnthNm());
					lstInstalationBeans = new ArrayList<InstalationBean>();
				}
				lstInstalationBeans.add(instalationBean);

			}
		}
		instlMtnhBean.setInstalationBeans(lstInstalationBeans);
		instlMtnhBean.setSize(lstInstalationBeans.size() + 1);
		dispInstallationMnthWiseBeans.add(instlMtnhBean);

		for (InstallationMnthWiseBean installationMnthWiseBean : dispInstallationMnthWiseBeans) {
			if (role.equals("1")) {
				installationMnthWiseBean.setInstalationBeans(zoneWise(installationMnthWiseBean.getInstalationBeans()));
			} else if (role.equals("2")) {
				installationMnthWiseBean.setInstalationBeans(circleWise(installationMnthWiseBean.getInstalationBeans()));
			} else if (role.equals("3")) {
				installationMnthWiseBean.setInstalationBeans(divWise(installationMnthWiseBean.getInstalationBeans()));
			} else if (role.equals("4")) {
				installationMnthWiseBean.setInstalationBeans(subDivWise(installationMnthWiseBean.getInstalationBeans()));
			} else if (role.equals("5")) {
				installationMnthWiseBean.setInstalationBeans(secWise(installationMnthWiseBean.getInstalationBeans()));
			}

			installationMnthWiseBean.setSize(installationMnthWiseBean.getInstalationBeans().size() + 1);
		}

		return dispInstallationMnthWiseBeans;
	}
	// public static void main(String[] args) {
	// viewData("1", "1", "2012");
	// }
}
