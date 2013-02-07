package com.ample.mis.hibernate.bo;
// Generated Dec 13, 2012 11:32:17 AM by Hibernate Tools 3.4.0.CR1

/**
 * DiscrepancyDetail generated by hbm2java
 */
public class DiscrepancyDetailBO  implements  java.io.Serializable {

	private Long crpdetId;
	private DiscrepancyBO discrepancy;
	private TranHeadBO tranHead;
	private Integer crpdetNoofConsmpn;
	private Double crpdetDmndRaised;
	private Boolean crpdetRowStatus;

	public DiscrepancyDetailBO() {
	}

	public DiscrepancyDetailBO(DiscrepancyBO discrepancy, TranHeadBO tranHead) {
		this.discrepancy = discrepancy;
		this.tranHead = tranHead;
	}

	public DiscrepancyDetailBO(DiscrepancyBO discrepancy, TranHeadBO tranHead,
			Integer crpdetNoofConsmpn, Double crpdetDmndRaised,
			Boolean crpdetRowStatus) {
		this.discrepancy = discrepancy;
		this.tranHead = tranHead;
		this.crpdetNoofConsmpn = crpdetNoofConsmpn;
		this.crpdetDmndRaised = crpdetDmndRaised;
		this.crpdetRowStatus = crpdetRowStatus;
	}

	public Long getCrpdetId() {
		return this.crpdetId;
	}

	public void setCrpdetId(Long crpdetId) {
		this.crpdetId = crpdetId;
	}

	public DiscrepancyBO getDiscrepancy() {
		return this.discrepancy;
	}

	public void setDiscrepancy(DiscrepancyBO discrepancy) {
		this.discrepancy = discrepancy;
	}

	public TranHeadBO getTranHead() {
		return this.tranHead;
	}

	public void setTranHead(TranHeadBO tranHead) {
		this.tranHead = tranHead;
	}

	public Integer getCrpdetNoofConsmpn() {
		return this.crpdetNoofConsmpn;
	}

	public void setCrpdetNoofConsmpn(Integer crpdetNoofConsmpn) {
		this.crpdetNoofConsmpn = crpdetNoofConsmpn;
	}

	public Double getCrpdetDmndRaised() {
		return this.crpdetDmndRaised;
	}

	public void setCrpdetDmndRaised(Double crpdetDmndRaised) {
		this.crpdetDmndRaised = crpdetDmndRaised;
	}

	public Boolean getCrpdetRowStatus() {
		return this.crpdetRowStatus;
	}

	public void setCrpdetRowStatus(Boolean crpdetRowStatus) {
		this.crpdetRowStatus = crpdetRowStatus;
	}

}
