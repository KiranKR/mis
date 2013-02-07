package com.action;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.ZeroConsumptionRepDao;
import com.Util.AppUtil;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.ZeroConsumptionBean;

@ManagedBean(name = "pc_zeroConsumRep")
@SessionScoped
public class ZeroConsumRep extends PageCodeBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List secLst = new ArrayList();

	private ZeroConsumptionRepDao zeroConsumRepDao = new ZeroConsumptionRepDao();
	private ZeroConsumptionBean zeroConsumBean = new ZeroConsumptionBean();
	private List<ZeroConsumptionBean> lstZCbean = new ArrayList<ZeroConsumptionBean>();

	public ZeroConsumRep() {
		super();
		try {
			String concatWhere = "";
			final String queryWhere = "JOIN sub_division ON section.SECTN_SUBDIV_ID = sub_division.SUBDIV_ID "
					+ "JOIN division ON sub_division.SUBDIV_DIV_ID = division.DIV_ID "
					+ "JOIN circle ON division.DIV_CIRCLE_ID = circle.CRCL_ID "
					+ "JOIN zone ON circle.CRCL_ZONE_ID = zone.ZONE_ID where ";
			final int idenFlag = Integer.valueOf(UserUtil.getSessionValue(UserBeanConstants.USER_MP_IDEN_FLAG_IN_SESSION));
			if (idenFlag == 1) {
				concatWhere = "zone.ZONE_ID = "	+ UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+ " ";
			} else if (idenFlag == 2) {
				concatWhere = "circle.CRCL_ID = "+ UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+ " ";
			} else if (idenFlag == 3) {
				concatWhere = "division.DIV_ID = "+ UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+ " ";
			} else if (idenFlag == 4) {
				concatWhere = "sub_division.SUBDIV_ID = "+ UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+ " ";
			}
			final String whrClause = queryWhere.concat(concatWhere);

			secLst = AppUtil.getDropDownList("section", "SECTN_NAME","SECTN_ID", "SECTN_NAME", whrClause, 0);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String getZeroConsum() {
		if (validate()) {
			lstZCbean = zeroConsumRepDao.getZeroConRep(zeroConsumBean);
		} 
			return "zeroConRep";
	}

	public String cancelOk() {
		clearSession();
		return "cancel";
	}

	public boolean validate() {
		boolean isValid = true;

		if (nullCheck(zeroConsumBean.getSecId())) {
			addMessage("ERRCD537E");
			isValid = false;
			return isValid;
		} else if (nullCheck(zeroConsumBean.getYearId())) {
			addMessage("ERRCD539E");
			isValid = false;
			return isValid;

		}
		return isValid;
	}

	

	private void clearSession() {
		Map sessionMap = (Map) getBindingValue("#{sessionScope}");
		sessionMap.remove("pc_zeroConsumRep");
	}

	public List<ZeroConsumptionBean> getLstZCbean() {
		return lstZCbean;
	}

	public void setLstZCbean(List<ZeroConsumptionBean> lstZCbean) {
		this.lstZCbean = lstZCbean;
	}

	public List getSecLst() {
		return secLst;
	}

	public void setSecLst(List secLst) {
		this.secLst = secLst;
	}

	public ZeroConsumptionBean getZeroConsumBean() {
		return zeroConsumBean;
	}

	public void setZeroConsumBean(ZeroConsumptionBean zeroConsumBean) {
		this.zeroConsumBean = zeroConsumBean;
	}

}
