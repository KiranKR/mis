package com.ample.mis.hibernate.bo;

// default package
// Generated Dec 19, 2012 2:02:48 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Module generated by hbm2java
 */
public class ModuleBO implements java.io.Serializable {

	private Integer modId;
	private ModuleGroupBO moduleGroup;
	private String modName;
	private int modParent;
	private int modLevel;
	private String modPath;
	private int modSortOrder;
	private boolean modRowStatus;
	private boolean modIsAuthApplicable;
	private String modParentName;
	private String modLn;
	private Set<ReportsBO> reportses = new HashSet<ReportsBO>(0);
	private Set<ModActionBO> modActions = new HashSet<ModActionBO>(0);

	public ModuleBO() {
	}

	public ModuleBO(String modName, int modParent, int modLevel,
			int modSortOrder, boolean modRowStatus, boolean modIsAuthApplicable) {
		this.modName = modName;
		this.modParent = modParent;
		this.modLevel = modLevel;
		this.modSortOrder = modSortOrder;
		this.modRowStatus = modRowStatus;
		this.modIsAuthApplicable = modIsAuthApplicable;
	}

	public ModuleBO(ModuleGroupBO moduleGroup, String modName, int modParent,
			int modLevel, String modPath, int modSortOrder,
			boolean modRowStatus, boolean modIsAuthApplicable,
			String modParentName, String modLn, Set<ReportsBO> reportses,
			Set<ModActionBO> modActions) {
		this.moduleGroup = moduleGroup;
		this.modName = modName;
		this.modParent = modParent;
		this.modLevel = modLevel;
		this.modPath = modPath;
		this.modSortOrder = modSortOrder;
		this.modRowStatus = modRowStatus;
		this.modIsAuthApplicable = modIsAuthApplicable;
		this.modParentName = modParentName;
		this.modLn = modLn;
		this.reportses = reportses;
		this.modActions = modActions;
	}

	public Integer getModId() {
		return this.modId;
	}

	public void setModId(Integer modId) {
		this.modId = modId;
	}

	public ModuleGroupBO getModuleGroup() {
		return this.moduleGroup;
	}

	public void setModuleGroup(ModuleGroupBO moduleGroup) {
		this.moduleGroup = moduleGroup;
	}

	public String getModName() {
		return this.modName;
	}

	public void setModName(String modName) {
		this.modName = modName;
	}

	public int getModParent() {
		return this.modParent;
	}

	public void setModParent(int modParent) {
		this.modParent = modParent;
	}

	public int getModLevel() {
		return this.modLevel;
	}

	public void setModLevel(int modLevel) {
		this.modLevel = modLevel;
	}

	public String getModPath() {
		return this.modPath;
	}

	public void setModPath(String modPath) {
		this.modPath = modPath;
	}

	public int getModSortOrder() {
		return this.modSortOrder;
	}

	public void setModSortOrder(int modSortOrder) {
		this.modSortOrder = modSortOrder;
	}

	public boolean isModRowStatus() {
		return this.modRowStatus;
	}

	public void setModRowStatus(boolean modRowStatus) {
		this.modRowStatus = modRowStatus;
	}

	public boolean isModIsAuthApplicable() {
		return this.modIsAuthApplicable;
	}

	public void setModIsAuthApplicable(boolean modIsAuthApplicable) {
		this.modIsAuthApplicable = modIsAuthApplicable;
	}

	public String getModParentName() {
		return this.modParentName;
	}

	public void setModParentName(String modParentName) {
		this.modParentName = modParentName;
	}

	public String getModLn() {
		return this.modLn;
	}

	public void setModLn(String modLn) {
		this.modLn = modLn;
	}

	public Set<ReportsBO> getReportses() {
		return this.reportses;
	}

	public void setReportses(Set<ReportsBO> reportses) {
		this.reportses = reportses;
	}

	public Set<ModActionBO> getModActions() {
		return this.modActions;
	}

	public void setModActions(Set<ModActionBO> modActions) {
		this.modActions = modActions;
	}

}
