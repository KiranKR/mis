package com.ample.mis.hibernate.bo;
// Generated Dec 13, 2012 11:32:17 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * DepartmentBO generated by hbm2java
 */
public class DepartmentBO  implements  java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5351052648684429280L;
	private Integer deptId;
	private String deptShortCode;
	private String deptName;
	private Date deptFromDate;
	private Date deptToDate;
	private boolean deptRowStatus;
	private Long deptCreatedBy;
	private Date deptCreatedDate;
	private Long deptLastUpdatedBy;
	private Date deptLastUpdatedDate;
	private Set<EmployeeBO> Employees = new HashSet<EmployeeBO>(0);

	public DepartmentBO() {
	}

	public DepartmentBO(String deptShortCode, String deptName, Date deptFromDate,
			boolean deptRowStatus) {
		this.deptShortCode = deptShortCode;
		this.deptName = deptName;
		this.deptFromDate = deptFromDate;
		this.deptRowStatus = deptRowStatus;
	}

	public DepartmentBO(String deptShortCode, String deptName, Date deptFromDate,
			Date deptToDate, boolean deptRowStatus, Long deptCreatedBy,
			Date deptCreatedDate, Long deptLastUpdatedBy,
			Date deptLastUpdatedDate, Set<EmployeeBO> EmployeeBOs) {
		this.deptShortCode = deptShortCode;
		this.deptName = deptName;
		this.deptFromDate = deptFromDate;
		this.deptToDate = deptToDate;
		this.deptRowStatus = deptRowStatus;
		this.deptCreatedBy = deptCreatedBy;
		this.deptCreatedDate = deptCreatedDate;
		this.deptLastUpdatedBy = deptLastUpdatedBy;
		this.deptLastUpdatedDate = deptLastUpdatedDate;
		this.Employees = EmployeeBOs;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptShortCode() {
		return deptShortCode;
	}

	public void setDeptShortCode(String deptShortCode) {
		this.deptShortCode = deptShortCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Date getDeptFromDate() {
		return deptFromDate;
	}

	public void setDeptFromDate(Date deptFromDate) {
		this.deptFromDate = deptFromDate;
	}

	public Date getDeptToDate() {
		return deptToDate;
	}

	public void setDeptToDate(Date deptToDate) {
		this.deptToDate = deptToDate;
	}

	public boolean isDeptRowStatus() {
		return deptRowStatus;
	}

	public void setDeptRowStatus(boolean deptRowStatus) {
		this.deptRowStatus = deptRowStatus;
	}

	public Long getDeptCreatedBy() {
		return deptCreatedBy;
	}

	public void setDeptCreatedBy(Long deptCreatedBy) {
		this.deptCreatedBy = deptCreatedBy;
	}

	public Date getDeptCreatedDate() {
		return deptCreatedDate;
	}

	public void setDeptCreatedDate(Date deptCreatedDate) {
		this.deptCreatedDate = deptCreatedDate;
	}

	public Long getDeptLastUpdatedBy() {
		return deptLastUpdatedBy;
	}

	public void setDeptLastUpdatedBy(Long deptLastUpdatedBy) {
		this.deptLastUpdatedBy = deptLastUpdatedBy;
	}

	public Date getDeptLastUpdatedDate() {
		return deptLastUpdatedDate;
	}

	public void setDeptLastUpdatedDate(Date deptLastUpdatedDate) {
		this.deptLastUpdatedDate = deptLastUpdatedDate;
	}

	public Set<EmployeeBO> getEmployees() {
		return Employees;
	}

	public void setEmployees(Set<EmployeeBO> employees) {
		Employees = employees;
	}

	

}
