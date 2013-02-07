package com.ample.mis.gk.registration.bean;

import java.util.ArrayList;
import java.util.List;

import com.bean.DepositeBean;
import com.bean.StatusDateBean;


public class PendingRep {
	
	//program
	int slno = 0;
	private String talukId 			= "";
	private String sectionId 		= "";
	private String schemeId 		= "";
	private String subSchemeId 		= "";
	private String beneficiaryName	 = "";
	private String village 			= "";
	private String dtOfRegtn 		= "";
	private String programId  		= "";
	private String uniqueId 		= "";
	private String prgWrId			="";
	
	//estimation
	private String estimateNo="";
	private Double estimateCost = 0.0;
	private String estimateDate = "";
	
	
	private String taluk ="";
	private String section ="";
	private String scheme ="";
	private String subScheme ="";
	private String estiNum ="";
	private String estiDate ="";
	private String intimatnIssDate = "";
	private String woIssNum = "";
	private String woIssDate = "";
	/*private String wrkStatus ="";
	private String wrkStatusDate ="";*/
	String pwStageIden = "";
	private String rrNumber ="";
	private String dateofService ="";
	private List<DepositeBean> depositeBeans = new ArrayList<DepositeBean>();
	private List<StatusDateBean> statusDateBeans = new ArrayList<StatusDateBean>();
	
	
	private String division = "";
	private String subdiv = "";
	private String estimatePrepared = "";
	private String wrkYetToBeTaken = "";
	private String wrkUndrProgress = "";
	private String wrkCompleted = "";
	private String pendFrmLocBdy = "";
	private String divId = "";
	private String subDivId = "";
	
	private String WorkOrderNum = "";
	private String WrkOrderIssDt = "";
	
	private String wrkAwrdDt = "";
	private String htLine = "";
	private String ltLine = "";
	private String tfCap = "";
	private String tfLoad = "";
	private double regstrFee = 0.0;
	private String regstrNo = ""; 

	
	
	public PendingRep() {
	}
	
	public PendingRep(String talukId,
			String sectionId ,
			String schemeId,
			String subSchemeId,
			String beneficiaryName,
			String village,
			String dtOfRegtn ,
			String programId,
			String uniqueId,
			String prgWrId,
			double regstrFee,
			String regstrNo){
			super();
			this.talukId=talukId;
			this.sectionId=sectionId ;
			this.schemeId=schemeId;
			this.subSchemeId=subSchemeId ;
			this.beneficiaryName=beneficiaryName;
			this.village=village ;
			this.dtOfRegtn=dtOfRegtn ;
			this.programId=programId ;
			this.uniqueId=uniqueId ;
			this.prgWrId=prgWrId;
			this.regstrFee = regstrFee;
			this.regstrNo = regstrNo;
			}



	public int getSlno() {
		return slno;
	}

	public void setSlno(int slno) {
		this.slno = slno;
	}

	public String getTalukId() {
		return talukId;
	}

	public void setTalukId(String talukId) {
		this.talukId = talukId;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	public String getSubSchemeId() {
		return subSchemeId;
	}

	public void setSubSchemeId(String subSchemeId) {
		this.subSchemeId = subSchemeId;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getDtOfRegtn() {
		return dtOfRegtn;
	}

	public void setDtOfRegtn(String dtOfRegtn) {
		this.dtOfRegtn = dtOfRegtn;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getPrgWrId() {
		return prgWrId;
	}

	public void setPrgWrId(String prgWrId) {
		this.prgWrId = prgWrId;
	}

	public String getEstimateNo() {
		return estimateNo;
	}

	public void setEstimateNo(String estimateNo) {
		this.estimateNo = estimateNo;
	}

	public Double getEstimateCost() {
		return estimateCost;
	}

	public void setEstimateCost(Double estimateCost) {
		this.estimateCost = estimateCost;
	}

	public String getEstimateDate() {
		return estimateDate;
	}

	public void setEstimateDate(String estimateDate) {
		this.estimateDate = estimateDate;
	}

	public String getTaluk() {
		return taluk;
	}

	public void setTaluk(String taluk) {
		this.taluk = taluk;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getSubScheme() {
		return subScheme;
	}

	public void setSubScheme(String subScheme) {
		this.subScheme = subScheme;
	}

	public String getEstiNum() {
		return estiNum;
	}

	public void setEstiNum(String estiNum) {
		this.estiNum = estiNum;
	}

	public String getEstiDate() {
		return estiDate;
	}

	public void setEstiDate(String estiDate) {
		this.estiDate = estiDate;
	}

	public String getIntimatnIssDate() {
		return intimatnIssDate;
	}

	public void setIntimatnIssDate(String intimatnIssDate) {
		this.intimatnIssDate = intimatnIssDate;
	}

	public String getWoIssNum() {
		return woIssNum;
	}

	public void setWoIssNum(String woIssNum) {
		this.woIssNum = woIssNum;
	}

	public String getWoIssDate() {
		return woIssDate;
	}

	public void setWoIssDate(String woIssDate) {
		this.woIssDate = woIssDate;
	}

	public String getPwStageIden() {
		return pwStageIden;
	}

	public void setPwStageIden(String pwStageIden) {
		this.pwStageIden = pwStageIden;
	}

	public String getRrNumber() {
		return rrNumber;
	}

	public void setRrNumber(String rrNumber) {
		this.rrNumber = rrNumber;
	}

	public String getDateofService() {
		return dateofService;
	}

	public void setDateofService(String dateofService) {
		this.dateofService = dateofService;
	}

	public List<DepositeBean> getDepositeBeans() {
		return depositeBeans;
	}

	public void setDepositeBeans(List<DepositeBean> depositeBeans) {
		this.depositeBeans = depositeBeans;
	}

	public List<StatusDateBean> getStatusDateBeans() {
		return statusDateBeans;
	}

	public void setStatusDateBeans(List<StatusDateBean> statusDateBeans) {
		this.statusDateBeans = statusDateBeans;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getSubdiv() {
		return subdiv;
	}

	public void setSubdiv(String subdiv) {
		this.subdiv = subdiv;
	}

	public String getEstimatePrepared() {
		return estimatePrepared;
	}

	public void setEstimatePrepared(String estimatePrepared) {
		this.estimatePrepared = estimatePrepared;
	}

	public String getWrkYetToBeTaken() {
		return wrkYetToBeTaken;
	}

	public void setWrkYetToBeTaken(String wrkYetToBeTaken) {
		this.wrkYetToBeTaken = wrkYetToBeTaken;
	}

	public String getWrkUndrProgress() {
		return wrkUndrProgress;
	}

	public void setWrkUndrProgress(String wrkUndrProgress) {
		this.wrkUndrProgress = wrkUndrProgress;
	}

	public String getWrkCompleted() {
		return wrkCompleted;
	}

	public void setWrkCompleted(String wrkCompleted) {
		this.wrkCompleted = wrkCompleted;
	}

	public String getPendFrmLocBdy() {
		return pendFrmLocBdy;
	}

	public void setPendFrmLocBdy(String pendFrmLocBdy) {
		this.pendFrmLocBdy = pendFrmLocBdy;
	}

	public String getDivId() {
		return divId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
	}

	public String getSubDivId() {
		return subDivId;
	}

	public void setSubDivId(String subDivId) {
		this.subDivId = subDivId;
	}

	public String getWorkOrderNum() {
		return WorkOrderNum;
	}

	public void setWorkOrderNum(String workOrderNum) {
		WorkOrderNum = workOrderNum;
	}

	public String getWrkOrderIssDt() {
		return WrkOrderIssDt;
	}

	public void setWrkOrderIssDt(String wrkOrderIssDt) {
		WrkOrderIssDt = wrkOrderIssDt;
	}

	public String getWrkAwrdDt() {
		return wrkAwrdDt;
	}

	public void setWrkAwrdDt(String wrkAwrdDt) {
		this.wrkAwrdDt = wrkAwrdDt;
	}

	
	public String getHtLine() {
		return htLine;
	}

	public void setHtLine(String htLine) {
		this.htLine = htLine;
	}

	public String getLtLine() {
		return ltLine;
	}

	public void setLtLine(String ltLine) {
		this.ltLine = ltLine;
	}

	public String getTfCap() {
		return tfCap;
	}

	public void setTfCap(String tfCap) {
		this.tfCap = tfCap;
	}

	public String getTfLoad() {
		return tfLoad;
	}

	public void setTfLoad(String tfLoad) {
		this.tfLoad = tfLoad;
	}

	public double getRegstrFee() {
		return regstrFee;
	}

	public void setRegstrFee(double regstrFee) {
		this.regstrFee = regstrFee;
	}

	public String getRegstrNo() {
		return regstrNo;
	}

	public void setRegstrNo(String regstrNo) {
		this.regstrNo = regstrNo;
	}
	
}
