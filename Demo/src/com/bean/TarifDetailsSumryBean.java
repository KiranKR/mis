package com.bean;

import java.util.ArrayList;
import java.util.List;

public class TarifDetailsSumryBean {

	private String lable;
	private String id;
	private int size;
	private List<TarifDetailsBean> detailsBeans = new ArrayList<TarifDetailsBean>();

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

	public List<TarifDetailsBean> getDetailsBeans() {
		return detailsBeans;
	}

	public void setDetailsBeans(List<TarifDetailsBean> detailsBeans) {
		this.detailsBeans = detailsBeans;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
