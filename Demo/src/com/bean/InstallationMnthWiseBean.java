package com.bean;

import java.util.ArrayList;
import java.util.List;

public class InstallationMnthWiseBean {

	private String mnthNm = "";
	private List<InstalationBean> instalationBeans = new ArrayList<InstalationBean>();
	int size = 0;

	public String getMnthNm() {
		return mnthNm;
	}

	public void setMnthNm(String mnthNm) {
		this.mnthNm = mnthNm;
	}

	public List<InstalationBean> getInstalationBeans() {
		return instalationBeans;
	}

	public void setInstalationBeans(List<InstalationBean> instalationBeans) {
		this.instalationBeans = instalationBeans;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
