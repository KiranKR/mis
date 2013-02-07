package com.ample.mis.hibernate.bo;
// Generated Dec 13, 2012 11:32:17 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Division generated by hbm2java
 */
public class DivisionBO  implements  java.io.Serializable {

	private Integer divId;
	private CircleBO circle;
	private String divShortCode;
	private String divName;
	private Date divFromDate;
	private Date divToDate;
	private boolean divRowStatus;
	private Long divCreatedBy;
	private Date divCreatedDate;
	private Long divLastUpdatedBy;
	private Date divLastUpdatedDate;
	private Set<SubDivisionBO> subDivisions = new HashSet<SubDivisionBO>(0);

	public DivisionBO() {
	}

	public DivisionBO(CircleBO circle, String divShortCode, String divName,
			Date divFromDate, boolean divRowStatus) {
		this.circle = circle;
		this.divShortCode = divShortCode;
		this.divName = divName;
		this.divFromDate = divFromDate;
		this.divRowStatus = divRowStatus;
	}

	public DivisionBO(CircleBO circle, String divShortCode, String divName,
			Date divFromDate, Date divToDate, boolean divRowStatus,
			Long divCreatedBy, Date divCreatedDate, Long divLastUpdatedBy,
			Date divLastUpdatedDate, Set<SubDivisionBO> subDivisions) {
		this.circle = circle;
		this.divShortCode = divShortCode;
		this.divName = divName;
		this.divFromDate = divFromDate;
		this.divToDate = divToDate;
		this.divRowStatus = divRowStatus;
		this.divCreatedBy = divCreatedBy;
		this.divCreatedDate = divCreatedDate;
		this.divLastUpdatedBy = divLastUpdatedBy;
		this.divLastUpdatedDate = divLastUpdatedDate;
		this.subDivisions = subDivisions;
	}

	public Integer getDivId() {
		return this.divId;
	}

	public void setDivId(Integer divId) {
		this.divId = divId;
	}

	public CircleBO getCircle() {
		return this.circle;
	}

	public void setCircle(CircleBO circle) {
		this.circle = circle;
	}

	public String getDivShortCode() {
		return this.divShortCode;
	}

	public void setDivShortCode(String divShortCode) {
		this.divShortCode = divShortCode;
	}

	public String getDivName() {
		return this.divName;
	}

	public void setDivName(String divName) {
		this.divName = divName;
	}

	public Date getDivFromDate() {
		return this.divFromDate;
	}

	public void setDivFromDate(Date divFromDate) {
		this.divFromDate = divFromDate;
	}

	public Date getDivToDate() {
		return this.divToDate;
	}

	public void setDivToDate(Date divToDate) {
		this.divToDate = divToDate;
	}

	public boolean isDivRowStatus() {
		return this.divRowStatus;
	}

	public void setDivRowStatus(boolean divRowStatus) {
		this.divRowStatus = divRowStatus;
	}

	public Long getDivCreatedBy() {
		return this.divCreatedBy;
	}

	public void setDivCreatedBy(Long divCreatedBy) {
		this.divCreatedBy = divCreatedBy;
	}

	public Date getDivCreatedDate() {
		return this.divCreatedDate;
	}

	public void setDivCreatedDate(Date divCreatedDate) {
		this.divCreatedDate = divCreatedDate;
	}

	public Long getDivLastUpdatedBy() {
		return this.divLastUpdatedBy;
	}

	public void setDivLastUpdatedBy(Long divLastUpdatedBy) {
		this.divLastUpdatedBy = divLastUpdatedBy;
	}

	public Date getDivLastUpdatedDate() {
		return this.divLastUpdatedDate;
	}

	public void setDivLastUpdatedDate(Date divLastUpdatedDate) {
		this.divLastUpdatedDate = divLastUpdatedDate;
	}

	public Set<SubDivisionBO> getSubDivisions() {
		return this.subDivisions;
	}

	public void setSubDivisions(Set<SubDivisionBO> subDivisions) {
		this.subDivisions = subDivisions;
	}

}
