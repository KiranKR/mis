package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.Util.AppUtil;
import com.Util.MysqlConnectionProvider;
import com.bean.TarifDCAViewReportBean;
import com.bean.TarifDetailsBean;

public class TariffDCAReportDao {

	public List<TarifDCAViewReportBean> getTransformerDetails(int secId,
			String yearId) {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<TarifDCAViewReportBean> tarifReportBeans = new ArrayList<TarifDCAViewReportBean>();
		TarifDCAViewReportBean tarifDCAViewReportBean = null;
		TarifDetailsBean tarifDetailsBean = null;
		List<TarifDetailsBean> tarifDetailsBeans = null;

		double ttlDemand = 0.0;
		double ttlConsmtn = 0.0;
		double ttlArrs = 0.0;

		double demand = 0.0;
		double consmtn = 0.0;
		double arrs = 0.0;

		try {
			con = MysqlConnectionProvider.getNewConnection();

			String query = "SELECT t.TRF_ID, " + "t.TRF_NAME,  "
					+ "td.TDCA_DEMAND, " + "td.TDCA_CONSUMPTION, "
					+ "td.TDCA_ARRERAS, " + "th.TH_MONTH " + "FROM tariff t "
					+ "JOIN tariff_dca td " + "ON td.TDCA_TRF_ID = t.TRF_ID "
					+ "JOIN tran_head th " + "ON td.TDCA_TH_ID = th.TH_ID "
					+ "WHERE th.TH_SECTN_ID = " + secId + " AND th.TH_YEAR = "
					+ yearId + " AND th.TH_IDEN_FLAG = 5 "
					+ "Order by th.TH_MONTH,t.TRF_ID ";

			pst = con.prepareStatement(query);
			rs = pst.executeQuery();

			String mId = "";

			while (rs.next()) {
				if (mId.equals("")) {

					tarifDCAViewReportBean = new TarifDCAViewReportBean();
					tarifDetailsBean = new TarifDetailsBean();
					tarifDetailsBeans = new ArrayList<TarifDetailsBean>();

					mId = rs.getString("TH_MONTH");

					arrs = rs.getDouble("TDCA_ARRERAS");
					tarifDetailsBean.setArr(arrs);
					consmtn = rs.getDouble("TDCA_CONSUMPTION");
					tarifDetailsBean.setConsumption(consmtn);
					demand = rs.getDouble("TDCA_DEMAND");
					tarifDetailsBean.setDemand(demand);

					tarifDetailsBean.setTarifName(rs.getString("TRF_NAME"));
					tarifDetailsBeans.add(tarifDetailsBean);
					tarifDCAViewReportBean
							.setTarifDetailsBeans(tarifDetailsBeans);

					tarifDCAViewReportBean.setMonthId(AppUtil.getMonth(mId));
					tarifDCAViewReportBean
							.setSize(tarifDetailsBeans.size() + 1);
					tarifReportBeans.add(tarifDCAViewReportBean);
					ttlDemand += demand;
					ttlConsmtn += consmtn;
					ttlArrs += arrs;

				} else if (!mId.equals(rs.getString("TH_MONTH"))) {
					tarifDCAViewReportBean = new TarifDCAViewReportBean();
					tarifDetailsBean = new TarifDetailsBean();
					tarifDetailsBeans = new ArrayList<TarifDetailsBean>();
					mId = rs.getString("TH_MONTH");
					arrs = rs.getDouble("TDCA_ARRERAS");
					tarifDetailsBean.setArr(arrs);
					consmtn = rs.getDouble("TDCA_CONSUMPTION");
					tarifDetailsBean.setConsumption(consmtn);
					demand = rs.getDouble("TDCA_DEMAND");
					tarifDetailsBean.setDemand(demand);

					tarifDetailsBean.setTarifName(rs.getString("TRF_NAME"));
					tarifDetailsBeans.add(tarifDetailsBean);
					tarifDCAViewReportBean
							.setTarifDetailsBeans(tarifDetailsBeans);

					tarifDCAViewReportBean.setMonthId(AppUtil.getMonth(rs
							.getString("TH_MONTH")));
					tarifDCAViewReportBean
							.setSize(tarifDetailsBeans.size() + 1);
					tarifReportBeans.add(tarifDCAViewReportBean);

					ttlDemand += demand;
					ttlConsmtn += consmtn;
					ttlArrs += arrs;
				} else if (mId.equals(rs.getString("TH_MONTH"))) {
					tarifDetailsBean = new TarifDetailsBean();

					arrs = rs.getDouble("TDCA_ARRERAS");
					tarifDetailsBean.setArr(arrs);

					consmtn = rs.getDouble("TDCA_CONSUMPTION");
					tarifDetailsBean.setConsumption(consmtn);

					demand = rs.getDouble("TDCA_DEMAND");
					tarifDetailsBean.setDemand(demand);

					tarifDetailsBean.setTarifName(rs.getString("TRF_NAME"));
					tarifDetailsBeans.add(tarifDetailsBean);
					tarifDCAViewReportBean
							.setTarifDetailsBeans(tarifDetailsBeans);
					tarifDCAViewReportBean
							.setSize(tarifDetailsBeans.size() + 1);

					ttlDemand += demand;
					ttlConsmtn += consmtn;
					ttlArrs += arrs;

				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			MysqlConnectionProvider.releaseConnection(rs, null, pst, con);
		}

		return tarifReportBeans;
	}
}