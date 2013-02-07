package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Util.MysqlConnectionProvider;
import com.bean.TarifDetailsBean;
import com.bean.TarifDetailsSumryBean;

public class TariffDCASumryReportDao {

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	public List<TarifDetailsSumryBean> getTarifViewDetails(String from, String to, String role, String whrClause) {

		String qry = "";
		String orderBy = "";
		String column = "";
		List<TarifDetailsSumryBean> sumryBeans = new ArrayList<TarifDetailsSumryBean>();
		TarifDetailsSumryBean reportBean = null;
		try {
			con = MysqlConnectionProvider.getNewConnection();
			if (role.equals("1")) {
				orderBy = " z.ZONE_ID";
				column = " z.ZONE_ID,z.ZONE_NAME";
			} else if (role.equals("2")) {
				orderBy = " c.CRCL_ID";
				column = " c.CRCL_ID,c.CRCL_NAME";
			} else if (role.equals("3")) {
				orderBy = " d.DIV_ID";
				column = " d.DIV_ID, d.DIV_NAME";
			} else if (role.equals("4")) {
				orderBy = " sd.SUBDIV_ID";
				column = " sd.SUBDIV_ID,sd.SUBDIV_NAME";
			} else if (role.equals("5")) {
				orderBy = " s.SECTN_ID";
				column = " s.SECTN_ID,s.SECTN_NAME";
			}

			qry = " SELECT t.TRF_ID,"
						+ " t.TRF_NAME,"
						+ " td.TDCA_ID,"
						+ " td.TDCA_DEMAND,"
						+ " td.TDCA_CONSUMPTION,"
						+ " td.TDCA_ARRERAS,"
						+ " th.TH_MONTH,"
						+ " th.TH_YEAR,"
						+ column
						+ " FROM tariff_dca td"
						+ " JOIN tariff t"
						+ " ON td.TDCA_TRF_ID = t.TRF_ID"
						+ " JOIN tran_head th"
						+ " ON td.TDCA_TH_ID = th.TH_ID"
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
						+ " WHERE concat(th.TH_YEAR, th.TH_MONTH) >= " + from
						+ " AND concat(th.TH_YEAR, th.TH_MONTH) <= " + to + " " + whrClause
						+ " ORDER BY " + orderBy + ",t.TRF_ID";

			pst = con.prepareStatement(qry);
			rs = pst.executeQuery();
			TarifDetailsBean bean = null;
			List<TarifDetailsBean> detailsBeans = null;
			String flag = "";
			while (rs.next()) {
				if (flag.equals("")) {
					flag = rs.getString(9);
					reportBean = new TarifDetailsSumryBean();
					detailsBeans = new ArrayList<TarifDetailsBean>();
					bean = new TarifDetailsBean();
					reportBean.setLable(rs.getString(10));
					reportBean.setId(rs.getString(9));

					bean.setTarifName(rs.getString(2));
					bean.setDemand(rs.getDouble(4));
					bean.setConsumption(rs.getDouble(5));
					bean.setArr(rs.getDouble(6));
					detailsBeans.add(bean);

				} else {
					if (!flag.equals(rs.getString(9))) {
						reportBean.setDetailsBeans(detailsBeans);
						reportBean.setSize(detailsBeans.size() + 1);
						sumryBeans.add(reportBean);
						flag = rs.getString(9);
						reportBean = new TarifDetailsSumryBean();
						detailsBeans = new ArrayList<TarifDetailsBean>();

						reportBean.setLable(rs.getString(10));
						reportBean.setId(rs.getString(9));
					}
					bean = new TarifDetailsBean();
					bean.setTarifName(rs.getString(2));
					bean.setDemand(rs.getDouble(4));
					bean.setConsumption(rs.getDouble(5));
					bean.setArr(rs.getDouble(6));
					detailsBeans.add(bean);
				}
			}
			reportBean.setDetailsBeans(detailsBeans);
			reportBean.setSize(detailsBeans.size() + 1);
			sumryBeans.add(reportBean);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ddfd(sumryBeans);
	}

	public List<TarifDetailsSumryBean> ddfd(List<TarifDetailsSumryBean> sumryBeans) {
		for (TarifDetailsSumryBean detailsSumryBean : sumryBeans) {

			List<TarifDetailsBean> beans = detailsSumryBean.getDetailsBeans();
			String name = "";
			TarifDetailsBean bean = null;
			List<TarifDetailsBean> newBeans = new ArrayList<TarifDetailsBean>();
			for (TarifDetailsBean tarifDetailsBean : beans) {

				if (name.equals("")) {
					bean = new TarifDetailsBean();
					name = tarifDetailsBean.getTarifName();
					bean.setTarifName(tarifDetailsBean.getTarifName());
					bean.setDemand(bean.getDemand() + tarifDetailsBean.getDemand());
					bean.setConsumption(bean.getConsumption() + tarifDetailsBean.getConsumption());
					bean.setArr(bean.getArr() + tarifDetailsBean.getArr());
				} else {
					if (!name.equals(tarifDetailsBean.getTarifName())) {
						newBeans.add(bean);
						bean = new TarifDetailsBean();
					}
					name = tarifDetailsBean.getTarifName();
					bean.setTarifName(tarifDetailsBean.getTarifName());
					bean.setDemand(bean.getDemand() + tarifDetailsBean.getDemand());
					bean.setConsumption(bean.getConsumption() + tarifDetailsBean.getConsumption());
					bean.setArr(bean.getArr() + tarifDetailsBean.getArr());
				}

			}
			newBeans.add(bean);
			detailsSumryBean.setDetailsBeans(newBeans);
			detailsSumryBean.setSize(newBeans.size() + 1);
		}
		return sumryBeans;
	}
}