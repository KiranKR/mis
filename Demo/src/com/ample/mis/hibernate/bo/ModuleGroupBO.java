package com.ample.mis.hibernate.bo;

// default package
// Generated Dec 19, 2012 2:02:48 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * ModuleGroup generated by hbm2java
 */
public class ModuleGroupBO implements java.io.Serializable {

	private Integer modgrpId;
	private String modgrpName;
	private int modgrpDisplayIden;
	private boolean modgrpRowStatus;
	private Set<ModuleBO> modules = new HashSet<ModuleBO>(0);

	public ModuleGroupBO() {
	}

	public ModuleGroupBO(String modgrpName, int modgrpDisplayIden,
			boolean modgrpRowStatus) {
		this.modgrpName = modgrpName;
		this.modgrpDisplayIden = modgrpDisplayIden;
		this.modgrpRowStatus = modgrpRowStatus;
	}

	public ModuleGroupBO(String modgrpName, int modgrpDisplayIden,
			boolean modgrpRowStatus, Set<ModuleBO> modules) {
		this.modgrpName = modgrpName;
		this.modgrpDisplayIden = modgrpDisplayIden;
		this.modgrpRowStatus = modgrpRowStatus;
		this.modules = modules;
	}

	public Integer getModgrpId() {
		return this.modgrpId;
	}

	public void setModgrpId(Integer modgrpId) {
		this.modgrpId = modgrpId;
	}

	public String getModgrpName() {
		return this.modgrpName;
	}

	public void setModgrpName(String modgrpName) {
		this.modgrpName = modgrpName;
	}

	public int getModgrpDisplayIden() {
		return this.modgrpDisplayIden;
	}

	public void setModgrpDisplayIden(int modgrpDisplayIden) {
		this.modgrpDisplayIden = modgrpDisplayIden;
	}

	public boolean isModgrpRowStatus() {
		return this.modgrpRowStatus;
	}

	public void setModgrpRowStatus(boolean modgrpRowStatus) {
		this.modgrpRowStatus = modgrpRowStatus;
	}

	public Set<ModuleBO> getModules() {
		return this.modules;
	}

	public void setModules(Set<ModuleBO> modules) {
		this.modules = modules;
	}

}
