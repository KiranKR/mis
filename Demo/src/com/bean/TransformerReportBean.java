package com.bean;

import java.util.ArrayList;
import java.util.List;

public class TransformerReportBean {
   
	
	 private String monthNum;
	 private String MonthId="";
	 private List<TransformerDetailsBean> transformerDetailsBeans = new ArrayList<TransformerDetailsBean>();
	 
	 
	 
	 private int totAdditionCnt =0;
	 private int totFailureCnt =0;
	 private int totReplacedCnt =0;
	 private int totMaintainceCnt =0;
	 
	 private int grandTotAdditionCnt =0;
	 private int grandTotFailureCnt =0;
	 private int grandTotReplacedCnt =0;
	 private int grandTotMaintainceCnt =0;

	 private String dispName="";
	 private String lvl = "";
	 
	 private String label="";
	 private String id = "";
	 private int size =0;
	public String getMonthNum() {
		return monthNum;
	}
	public void setMonthNum(String monthNum) {
		this.monthNum = monthNum;
	}
	public String getMonthId() {
		return MonthId;
	}
	public void setMonthId(String monthId) {
		MonthId = monthId;
	}
	public List<TransformerDetailsBean> getTransformerDetailsBeans() {
		return transformerDetailsBeans;
	}
	public void setTransformerDetailsBeans(
			List<TransformerDetailsBean> transformerDetailsBeans) {
		this.transformerDetailsBeans = transformerDetailsBeans;
	}
	public int getTotAdditionCnt() {
		return totAdditionCnt;
	}
	public void setTotAdditionCnt(int totAdditionCnt) {
		this.totAdditionCnt = totAdditionCnt;
	}
	public int getTotFailureCnt() {
		return totFailureCnt;
	}
	public void setTotFailureCnt(int totFailureCnt) {
		this.totFailureCnt = totFailureCnt;
	}
	public int getTotReplacedCnt() {
		return totReplacedCnt;
	}
	public void setTotReplacedCnt(int totReplacedCnt) {
		this.totReplacedCnt = totReplacedCnt;
	}
	public int getTotMaintainceCnt() {
		return totMaintainceCnt;
	}
	public void setTotMaintainceCnt(int totMaintainceCnt) {
		this.totMaintainceCnt = totMaintainceCnt;
	}
	public int getGrandTotAdditionCnt() {
		return grandTotAdditionCnt;
	}
	public void setGrandTotAdditionCnt(int grandTotAdditionCnt) {
		this.grandTotAdditionCnt = grandTotAdditionCnt;
	}
	public int getGrandTotFailureCnt() {
		return grandTotFailureCnt;
	}
	public void setGrandTotFailureCnt(int grandTotFailureCnt) {
		this.grandTotFailureCnt = grandTotFailureCnt;
	}
	public int getGrandTotReplacedCnt() {
		return grandTotReplacedCnt;
	}
	public void setGrandTotReplacedCnt(int grandTotReplacedCnt) {
		this.grandTotReplacedCnt = grandTotReplacedCnt;
	}
	public int getGrandTotMaintainceCnt() {
		return grandTotMaintainceCnt;
	}
	public void setGrandTotMaintainceCnt(int grandTotMaintainceCnt) {
		this.grandTotMaintainceCnt = grandTotMaintainceCnt;
	}
	public String getDispName() {
		return dispName;
	}
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}
	public String getLvl() {
		return lvl;
	}
	public void setLvl(String lvl) {
		this.lvl = lvl;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

}
