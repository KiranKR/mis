package com.bean;


public class ReportBean {

	// program
	int slno = 0;
	private String prgWrId = "";
	private String uniqueId = "";
	private String beneficiaryName = "";
	private String village = "";
	private String dtOfRegtn = "";
	private double EstAmount = 0.0;
	private String secId = "";
	private String secName = "";
	private String sudDivId = "";
	private String sudDivName = "";
	private String divId = "";
	private String divName = "";
	private String circleId = "";
	private String circleName = "";
	private String zoneId = "";
	private String zoneName = "";
	private String castId = "";
	private String castName = "";
	private int scCnt = 0;
	private int stCnt = 0;
	private int bcCnt = 0;
	private int mineCnt = 0;
	private double scttlamnt = 0;
	private double stttlamnt = 0;
	private double bcttlamnt = 0;
	private double minettlamnt = 0;
	private String lable = "";
	private String id = "";
	private String lvl = "";
	private int ttl = 0;
	private double ttlamnt = 0.0;
	
	private String stageId ="";
	private int wrkYetCnt =0;
	private int wrkTknCnt =0;
	private int wrkComCnt =0;
	private int serviceCnt =0;
	private double wrkYetAmt =0;
	private double wrkTknAmt =0;
	private double wrkComAmt =0;
	private double serviceAmt =0;
	
	
	
	private String wrkComplDate ="";
	private int btn30to60daysCnt =0;
	private int btn60to90daysCnt =0;
	private int btn90to120daysCnt =0;
	private int btn120to150daysCnt =0;
	private int btn150to180daysCnt =0;
	private int grt180daysCnt =0;
	
	private String wrkStatus = "";
	
	private int scWrkYetCnt =0;
	private int scWrkTknCnt =0;
	private int scWrkComCnt =0;
	private int scServiceCnt =0;
	private double scWrkYetAmt =0;
	private double scWrkTknAmt =0;
	private double scWrkComAmt =0;
	private double scServiceAmt =0;
	
	private int stWrkYetCnt =0;
	private int stWrkTknCnt =0;
	private int stWrkComCnt =0;
	private int stServiceCnt =0;
	private double stWrkYetAmt =0.0;
	private double stWrkTknAmt =0.0;
	private double stWrkComAmt =0.0;
	private double stServiceAmt =0.0;
	
	private int bckWrkYetCnt =0;
	private int bckWrkTknCnt =0;
	private int bckWrkComCnt =0;
	private int bckServiceCnt =0;
	private double bckWrkYetAmt =0.0;
	private double bckWrkTknAmt =0.0;
	private double bckWrkComAmt =0.0;
	private double bckServiceAmt =0.0;
	
	private int mnWrkYetCnt =0;
	private int mnWrkTknCnt =0;
	private int mnWrkComCnt =0;
	private int mnServiceCnt =0;
	private double mnWrkYetAmt =0.0;
	private double mnWrkTknAmt =0.0;
	private double mnWrkComAmt =0.0;
	private double mnServiceAmt =0.0;
	
	private int totWrkYetCnt =0;
	private int totWrkTknCnt =0;
	private int totWrkComCnt =0;
	private int totServiceCnt =0;
	private double totWrkYetAmt =0.0;
	private double totWrkTknAmt =0.0;
	private double totWrkComAmt =0.0;
	private double totServiceAmt =0.0;
	
	private int scCountTotal=0;
	private int stCountTotal =0;
	private int bckCountTotal =0;
	private int mnCountTotal =0;
	private double scAmountTotal =0.0;
	private double stAmountTotal =0.0;
	private double bckAmountTotal =0.0;
	private double mnAmountTotal =0.0;
	
	private int totalCount =0;
	private double totalAmount =0.0;
	
	
	
	
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getScCountTotal() {
		return scCountTotal;
	}
	public void setScCountTotal(int scCountTotal) {
		this.scCountTotal = scCountTotal;
	}
	public int getStCountTotal() {
		return stCountTotal;
	}
	public void setStCountTotal(int stCountTotal) {
		this.stCountTotal = stCountTotal;
	}
	public int getBckCountTotal() {
		return bckCountTotal;
	}
	public void setBckCountTotal(int bckCountTotal) {
		this.bckCountTotal = bckCountTotal;
	}
	public int getMnCountTotal() {
		return mnCountTotal;
	}
	public void setMnCountTotal(int mnCountTotal) {
		this.mnCountTotal = mnCountTotal;
	}
	public double getScAmountTotal() {
		return scAmountTotal;
	}
	public void setScAmountTotal(double scAmountTotal) {
		this.scAmountTotal = scAmountTotal;
	}
	public double getStAmountTotal() {
		return stAmountTotal;
	}
	public void setStAmountTotal(double stAmountTotal) {
		this.stAmountTotal = stAmountTotal;
	}
	public double getBckAmountTotal() {
		return bckAmountTotal;
	}
	public void setBckAmountTotal(double bckAmountTotal) {
		this.bckAmountTotal = bckAmountTotal;
	}
	public double getMnAmountTotal() {
		return mnAmountTotal;
	}
	public void setMnAmountTotal(double mnAmountTotal) {
		this.mnAmountTotal = mnAmountTotal;
	}
	public int getTotWrkYetCnt() {
		return totWrkYetCnt;
	}
	public void setTotWrkYetCnt(int totWrkYetCnt) {
		this.totWrkYetCnt = totWrkYetCnt;
	}
	public int getTotWrkTknCnt() {
		return totWrkTknCnt;
	}
	public void setTotWrkTknCnt(int totWrkTknCnt) {
		this.totWrkTknCnt = totWrkTknCnt;
	}
	public int getTotWrkComCnt() {
		return totWrkComCnt;
	}
	public void setTotWrkComCnt(int totWrkComCnt) {
		this.totWrkComCnt = totWrkComCnt;
	}
	public int getTotServiceCnt() {
		return totServiceCnt;
	}
	public void setTotServiceCnt(int totServiceCnt) {
		this.totServiceCnt = totServiceCnt;
	}
	public double getTotWrkYetAmt() {
		return totWrkYetAmt;
	}
	public void setTotWrkYetAmt(double totWrkYetAmt) {
		this.totWrkYetAmt = totWrkYetAmt;
	}
	public double getTotWrkTknAmt() {
		return totWrkTknAmt;
	}
	public void setTotWrkTknAmt(double totWrkTknAmt) {
		this.totWrkTknAmt = totWrkTknAmt;
	}
	public double getTotWrkComAmt() {
		return totWrkComAmt;
	}
	public void setTotWrkComAmt(double totWrkComAmt) {
		this.totWrkComAmt = totWrkComAmt;
	}
	public double getTotServiceAmt() {
		return totServiceAmt;
	}
	public void setTotServiceAmt(double totServiceAmt) {
		this.totServiceAmt = totServiceAmt;
	}
	public int getStWrkYetCnt() {
		return stWrkYetCnt;
	}
	public void setStWrkYetCnt(int stWrkYetCnt) {
		this.stWrkYetCnt = stWrkYetCnt;
	}
	public int getStWrkTknCnt() {
		return stWrkTknCnt;
	}
	public void setStWrkTknCnt(int stWrkTknCnt) {
		this.stWrkTknCnt = stWrkTknCnt;
	}
	public int getStWrkComCnt() {
		return stWrkComCnt;
	}
	public void setStWrkComCnt(int stWrkComCnt) {
		this.stWrkComCnt = stWrkComCnt;
	}
	public int getStServiceCnt() {
		return stServiceCnt;
	}
	public void setStServiceCnt(int stServiceCnt) {
		this.stServiceCnt = stServiceCnt;
	}
	public double getStWrkYetAmt() {
		return stWrkYetAmt;
	}
	public void setStWrkYetAmt(double stWrkYetAmt) {
		this.stWrkYetAmt = stWrkYetAmt;
	}
	public double getStWrkTknAmt() {
		return stWrkTknAmt;
	}
	public void setStWrkTknAmt(double stWrkTknAmt) {
		this.stWrkTknAmt = stWrkTknAmt;
	}
	public double getStWrkComAmt() {
		return stWrkComAmt;
	}
	public void setStWrkComAmt(double stWrkComAmt) {
		this.stWrkComAmt = stWrkComAmt;
	}
	public double getStServiceAmt() {
		return stServiceAmt;
	}
	public void setStServiceAmt(double stServiceAmt) {
		this.stServiceAmt = stServiceAmt;
	}
	public int getScWrkYetCnt() {
		return scWrkYetCnt;
	}
	public void setScWrkYetCnt(int scWrkYetCnt) {
		this.scWrkYetCnt = scWrkYetCnt;
	}
	public int getScWrkTknCnt() {
		return scWrkTknCnt;
	}
	public void setScWrkTknCnt(int scWrkTknCnt) {
		this.scWrkTknCnt = scWrkTknCnt;
	}
	public int getScWrkComCnt() {
		return scWrkComCnt;
	}
	public void setScWrkComCnt(int scWrkComCnt) {
		this.scWrkComCnt = scWrkComCnt;
	}
	public int getScServiceCnt() {
		return scServiceCnt;
	}
	public void setScServiceCnt(int scServiceCnt) {
		this.scServiceCnt = scServiceCnt;
	}
	public double getScWrkYetAmt() {
		return scWrkYetAmt;
	}
	public void setScWrkYetAmt(double scWrkYetAmt) {
		this.scWrkYetAmt = scWrkYetAmt;
	}
	public double getScWrkTknAmt() {
		return scWrkTknAmt;
	}
	public void setScWrkTknAmt(double scWrkTknAmt) {
		this.scWrkTknAmt = scWrkTknAmt;
	}
	public double getScWrkComAmt() {
		return scWrkComAmt;
	}
	public void setScWrkComAmt(double scWrkComAmt) {
		this.scWrkComAmt = scWrkComAmt;
	}
	public double getScServiceAmt() {
		return scServiceAmt;
	}
	public void setScServiceAmt(double scServiceAmt) {
		this.scServiceAmt = scServiceAmt;
	}
	public int getSlno() {
		return slno;
	}
	public void setSlno(int slno) {
		this.slno = slno;
	}
	public String getPrgWrId() {
		return prgWrId;
	}
	public void setPrgWrId(String prgWrId) {
		this.prgWrId = prgWrId;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
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
	public double getEstAmount() {
		return EstAmount;
	}
	public void setEstAmount(double estAmount) {
		EstAmount = estAmount;
	}
	public void setDtOfRegtn(String dtOfRegtn) {
		this.dtOfRegtn = dtOfRegtn;
	}
	public String getSecId() {
		return secId;
	}
	public void setSecId(String secId) {
		this.secId = secId;
	}
	public String getSecName() {
		return secName;
	}
	public void setSecName(String secName) {
		this.secName = secName;
	}
	public String getSudDivId() {
		return sudDivId;
	}
	public void setSudDivId(String sudDivId) {
		this.sudDivId = sudDivId;
	}
	public String getSudDivName() {
		return sudDivName;
	}
	public void setSudDivName(String sudDivName) {
		this.sudDivName = sudDivName;
	}
	public String getDivId() {
		return divId;
	}
	public void setDivId(String divId) {
		this.divId = divId;
	}
	public String getDivName() {
		return divName;
	}
	public void setDivName(String divName) {
		this.divName = divName;
	}
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getZoneId() {
		return zoneId;
	}
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getCastId() {
		return castId;
	}
	public void setCastId(String castId) {
		this.castId = castId;
	}
	public String getCastName() {
		return castName;
	}
	public void setCastName(String castName) {
		this.castName = castName;
	}
	public int getScCnt() {
		return scCnt;
	}
	public void setScCnt(int scCnt) {
		this.scCnt = scCnt;
	}
	public int getStCnt() {
		return stCnt;
	}
	public void setStCnt(int stCnt) {
		this.stCnt = stCnt;
	}
	public int getBcCnt() {
		return bcCnt;
	}
	public void setBcCnt(int bcCnt) {
		this.bcCnt = bcCnt;
	}
	public int getMineCnt() {
		return mineCnt;
	}
	public void setMineCnt(int mineCnt) {
		this.mineCnt = mineCnt;
	}
	public double getScttlamnt() {
		return scttlamnt;
	}
	public void setScttlamnt(double scttlamnt) {
		this.scttlamnt = scttlamnt;
	}
	public double getStttlamnt() {
		return stttlamnt;
	}
	public void setStttlamnt(double stttlamnt) {
		this.stttlamnt = stttlamnt;
	}
	public double getBcttlamnt() {
		return bcttlamnt;
	}
	public void setBcttlamnt(double bcttlamnt) {
		this.bcttlamnt = bcttlamnt;
	}
	public double getMinettlamnt() {
		return minettlamnt;
	}
	public void setMinettlamnt(double minettlamnt) {
		this.minettlamnt = minettlamnt;
	}
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLvl() {
		return lvl;
	}
	public void setLvl(String lvl) {
		this.lvl = lvl;
	}
	public int getTtl() {
		return ttl;
	}
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
	public double getTtlamnt() {
		return ttlamnt;
	}
	public void setTtlamnt(double ttlamnt) {
		this.ttlamnt = ttlamnt;
	}
	public String getStageId() {
		return stageId;
	}
	public void setStageId(String stageId) {
		this.stageId = stageId;
	}
	public int getWrkYetCnt() {
		return wrkYetCnt;
	}
	public void setWrkYetCnt(int wrkYetCnt) {
		this.wrkYetCnt = wrkYetCnt;
	}
	public int getWrkTknCnt() {
		return wrkTknCnt;
	}
	public void setWrkTknCnt(int wrkTknCnt) {
		this.wrkTknCnt = wrkTknCnt;
	}
	public int getWrkComCnt() {
		return wrkComCnt;
	}
	public void setWrkComCnt(int wrkComCnt) {
		this.wrkComCnt = wrkComCnt;
	}
	public int getServiceCnt() {
		return serviceCnt;
	}
	public void setServiceCnt(int serviceCnt) {
		this.serviceCnt = serviceCnt;
	}
	public double getWrkYetAmt() {
		return wrkYetAmt;
	}
	public void setWrkYetAmt(double wrkYetAmt) {
		this.wrkYetAmt = wrkYetAmt;
	}
	public double getWrkTknAmt() {
		return wrkTknAmt;
	}
	public void setWrkTknAmt(double wrkTknAmt) {
		this.wrkTknAmt = wrkTknAmt;
	}
	public double getWrkComAmt() {
		return wrkComAmt;
	}
	public void setWrkComAmt(double wrkComAmt) {
		this.wrkComAmt = wrkComAmt;
	}
	public double getServiceAmt() {
		return serviceAmt;
	}
	public void setServiceAmt(double serviceAmt) {
		this.serviceAmt = serviceAmt;
	}
	public String getWrkComplDate() {
		return wrkComplDate;
	}
	public void setWrkComplDate(String wrkComplDate) {
		this.wrkComplDate = wrkComplDate;
	}
	public int getBtn30to60daysCnt() {
		return btn30to60daysCnt;
	}
	public void setBtn30to60daysCnt(int btn30to60daysCnt) {
		this.btn30to60daysCnt = btn30to60daysCnt;
	}
	public int getBtn60to90daysCnt() {
		return btn60to90daysCnt;
	}
	public void setBtn60to90daysCnt(int btn60to90daysCnt) {
		this.btn60to90daysCnt = btn60to90daysCnt;
	}
	public int getBtn90to120daysCnt() {
		return btn90to120daysCnt;
	}
	public void setBtn90to120daysCnt(int btn90to120daysCnt) {
		this.btn90to120daysCnt = btn90to120daysCnt;
	}
	public int getBtn120to150daysCnt() {
		return btn120to150daysCnt;
	}
	public void setBtn120to150daysCnt(int btn120to150daysCnt) {
		this.btn120to150daysCnt = btn120to150daysCnt;
	}
	public int getBtn150to180daysCnt() {
		return btn150to180daysCnt;
	}
	public void setBtn150to180daysCnt(int btn150to180daysCnt) {
		this.btn150to180daysCnt = btn150to180daysCnt;
	}
	public int getGrt180daysCnt() {
		return grt180daysCnt;
	}
	public void setGrt180daysCnt(int grt180daysCnt) {
		this.grt180daysCnt = grt180daysCnt;
	}
	public String getWrkStatus() {
		return wrkStatus;
	}
	public void setWrkStatus(String wrkStatus) {
		this.wrkStatus = wrkStatus;
	}
	public int getBckWrkYetCnt() {
		return bckWrkYetCnt;
	}
	public void setBckWrkYetCnt(int bckWrkYetCnt) {
		this.bckWrkYetCnt = bckWrkYetCnt;
	}
	public int getBckWrkTknCnt() {
		return bckWrkTknCnt;
	}
	public void setBckWrkTknCnt(int bckWrkTknCnt) {
		this.bckWrkTknCnt = bckWrkTknCnt;
	}
	public int getBckWrkComCnt() {
		return bckWrkComCnt;
	}
	public void setBckWrkComCnt(int bckWrkComCnt) {
		this.bckWrkComCnt = bckWrkComCnt;
	}
	public int getBckServiceCnt() {
		return bckServiceCnt;
	}
	public void setBckServiceCnt(int bckServiceCnt) {
		this.bckServiceCnt = bckServiceCnt;
	}
	public double getBckWrkYetAmt() {
		return bckWrkYetAmt;
	}
	public void setBckWrkYetAmt(double bckWrkYetAmt) {
		this.bckWrkYetAmt = bckWrkYetAmt;
	}
	public double getBckWrkTknAmt() {
		return bckWrkTknAmt;
	}
	public void setBckWrkTknAmt(double bckWrkTknAmt) {
		this.bckWrkTknAmt = bckWrkTknAmt;
	}
	public double getBckWrkComAmt() {
		return bckWrkComAmt;
	}
	public void setBckWrkComAmt(double bckWrkComAmt) {
		this.bckWrkComAmt = bckWrkComAmt;
	}
	public double getBckServiceAmt() {
		return bckServiceAmt;
	}
	public void setBckServiceAmt(double bckServiceAmt) {
		this.bckServiceAmt = bckServiceAmt;
	}
	public int getMnWrkYetCnt() {
		return mnWrkYetCnt;
	}
	public void setMnWrkYetCnt(int mnWrkYetCnt) {
		this.mnWrkYetCnt = mnWrkYetCnt;
	}
	public int getMnWrkTknCnt() {
		return mnWrkTknCnt;
	}
	public void setMnWrkTknCnt(int mnWrkTknCnt) {
		this.mnWrkTknCnt = mnWrkTknCnt;
	}
	public int getMnWrkComCnt() {
		return mnWrkComCnt;
	}
	public void setMnWrkComCnt(int mnWrkComCnt) {
		this.mnWrkComCnt = mnWrkComCnt;
	}
	public int getMnServiceCnt() {
		return mnServiceCnt;
	}
	public void setMnServiceCnt(int mnServiceCnt) {
		this.mnServiceCnt = mnServiceCnt;
	}
	public double getMnWrkYetAmt() {
		return mnWrkYetAmt;
	}
	public void setMnWrkYetAmt(double mnWrkYetAmt) {
		this.mnWrkYetAmt = mnWrkYetAmt;
	}
	public double getMnWrkTknAmt() {
		return mnWrkTknAmt;
	}
	public void setMnWrkTknAmt(double mnWrkTknAmt) {
		this.mnWrkTknAmt = mnWrkTknAmt;
	}
	public double getMnWrkComAmt() {
		return mnWrkComAmt;
	}
	public void setMnWrkComAmt(double mnWrkComAmt) {
		this.mnWrkComAmt = mnWrkComAmt;
	}
	public double getMnServiceAmt() {
		return mnServiceAmt;
	}
	public void setMnServiceAmt(double mnServiceAmt) {
		this.mnServiceAmt = mnServiceAmt;
	}
	
}
