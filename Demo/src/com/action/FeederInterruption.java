package com.action;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Dao.FeederInterruptionDao;
import com.Util.AppUtil;
import com.Util.EnumMonth;
import com.Util.PageCodeBase;
import com.Util.UserBeanConstants;
import com.Util.UserUtil;
import com.bean.Feeder;
import com.bean.FeederInterruptionBean;

@ManagedBean(name = "fd_interruption")
@SessionScoped
public class FeederInterruption extends PageCodeBase implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<EnumMonth> enumMonthlist = new ArrayList<EnumMonth>();
	private List secList = new ArrayList();
	private List<Feeder> feederList = new ArrayList<Feeder>();
	private FeederInterruptionDao dao = new FeederInterruptionDao();
	private Feeder feeder = new Feeder();
	private FeederInterruptionBean fibean = new FeederInterruptionBean();
	private int count = 0;
	 
	private boolean displayLst = false;
	private String btnCss = "greenBtnDis";
	private String btnDisable = "true";
	private int countSave=0;
	
	
	public  FeederInterruption() {

		try {
			secList = AppUtil.getDropDownList("section","SECTN_NAME","SECTN_ID","SECTN_NAME","where SECTN_SUBDIV_ID = "	+ UserUtil.getSessionValue(UserBeanConstants.LOGIN_ID_IN_SESSION)+ " ", 0);
			enumMonthlist = Arrays.asList(EnumMonth.values());

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); 
		}
	}

	public String getFeederLst(){
		if(validate()){
			List<Feeder> fedrLst = dao.getFeederList(fibean);
			for (Feeder feeder : fedrLst) {
				fibean.setTotalFstripped_Count(feeder.getTotalcount());
			}
			fibean.setFeedList(fedrLst);
			displayLst = true;
			count =1;
			btnCss = "greenBtn";
			btnDisable = "false";
		}
		return "fdiFailed2Save";
	}
	
	public void increment() {
		List<Feeder> fdFeeders = fibean.getFeedList();
		int count = 0;
		for (Feeder bean : fdFeeders) {
			count = count + bean.getFdrtripped_count();
		}
		fibean.setTotalFstripped_Count(count);
	}

	public String fdInterruption() {
		return "fdInterruption";
	}
	
	public String disableSaveButton(){
		fibean.setOnlyView(true);
			btnCss = "greenBtnDis";
			btnDisable = "true";
			if(count > 0){
				addMessage("ERRCDALL002E");
			}
			return "/pages/feederInterruption.xhtml";
		}

	public String saveFDI() {

		if (validateFdr()) {
			List<Feeder> fedrLst = fibean.getFeedList();
			for (Feeder feeder : fedrLst) {
				fibean.setTotalFstripped_Count(feeder.getTotalcount());
				if(feeder.getTransHeadFdr() != 0){
					fibean.setTransHead(feeder.getTransHeadFdr());
				}
			}
			boolean isValid = dao.saveFDI(fibean);
			if (isValid) {
				clearFeederInterruptDetails();
				return "fdiSavedSuces"; 
			}
		}
		return "fdiFailed2Save";
	}

	public String cancelOk(){
		clearFeederInterruptDetails();
		return "cancel";
	}
	
	private void clearFeederInterruptDetails() {
		Map sessionMap = (Map) getBindingValue("#{sessionScope}");
		sessionMap.remove("fd_interruption");
	}
	
	
	public boolean validate(){
		boolean isValid = true;
		if (nullCheck(fibean.getSectionId())) {
			addMessage("ERRCD537E");
			isValid = false;
			return isValid;
		} else if (nullCheck(fibean.getMonthId())) {
			addMessage("ERRCD538E");
			isValid = false;
			return isValid;

		} else if (nullCheck(fibean.getYearId())) {
			addMessage("ERRCD539E");
			isValid = false;
			return isValid;
		}else if (nullCheck(fibean.getFeedList())) {
			addMessage("ERRCD539E");
			isValid = false;
			return isValid;
		}
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(AppUtil.getMnthId(fibean.getMonthId()));
		stringBuffer.append("/");
		stringBuffer.append(fibean.getYearId());
	
		String monthYrStr = stringBuffer.toString();
		
		
		if(AppUtil.StringToMntYr(AppUtil.currentDateMntYr()).before(AppUtil.StringToMntYr(monthYrStr))){
			addMessage("ERRCDALL001E");
			isValid = false;
			return isValid;
		}
		return isValid;
	}

	public boolean validateFdr() {
		boolean isValid = true;
		if(!validate()){
			isValid = false;
			return isValid;
		}
		boolean isEmpty = true;
		List<Feeder> lstFedr = fibean.getFeedList();
			for (Feeder feeder : lstFedr) {
					if(!nullCheck(feeder.getFdrtripped_count()) && !nullCheck(feeder.getFdr_rsn())){
							isEmpty = false;
					}
		}if(isEmpty){
			addMessage("ERRCD547E");
			isValid = false;
			return isValid;
		}
		return isValid;

	}
	
	
	 
	 
	public boolean isDisplayLst() {
		return displayLst;
	}

	public void setDisplayLst(boolean displayLst) {
		this.displayLst = displayLst;
	}

	public List<EnumMonth> getEnumMonthlist() {
		return enumMonthlist;
	}

	public void setEnumMonthlist(List<EnumMonth> enumMonthlist) {
		this.enumMonthlist = enumMonthlist;
	}

	public List getSecList() {
		return secList;
	}

	public void setSecList(List secList) {
		this.secList = secList;
	}

	public List<Feeder> getFeederList() {
		return feederList;
	}

	public void setFeederList(List<Feeder> feederList) {
		this.feederList = feederList;
	}

	public Feeder getFeeder() {
		return feeder;
	}

	public void setFeeder(Feeder feeder) {
		this.feeder = feeder;
	}

	public FeederInterruptionBean getFibean() {
		return fibean;
	}

	public void setFibean(FeederInterruptionBean fibean) {
		this.fibean = fibean;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getBtnCss() {
		return btnCss;
	}

	public void setBtnCss(String btnCss) {
		this.btnCss = btnCss;
	}

	public String getBtnDisable() {
		return btnDisable;
	}

	public void setBtnDisable(String btnDisable) {
		this.btnDisable = btnDisable;
	}

	public int getCountSave() {
		return countSave;
	}

	public void setCountSave(int countSave) {
		this.countSave = countSave;
	}

	
}
