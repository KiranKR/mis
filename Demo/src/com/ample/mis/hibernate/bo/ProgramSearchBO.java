package com.ample.mis.hibernate.bo;
// Generated Dec 13, 2012 11:32:17 AM by Hibernate Tools 3.4.0.CR1

/**
 * ProgramSearch generated by hbm2java
 */
public class ProgramSearchBO  implements  java.io.Serializable {

	private Integer psrchId;
	private String psrchName;
	private String psrchRefColumn;
	private int psrchIdenFlag;
	private boolean psrchRowStatus;

	public ProgramSearchBO() {
	}

	public ProgramSearchBO(String psrchName, String psrchRefColumn,
			int psrchIdenFlag, boolean psrchRowStatus) {
		this.psrchName = psrchName;
		this.psrchRefColumn = psrchRefColumn;
		this.psrchIdenFlag = psrchIdenFlag;
		this.psrchRowStatus = psrchRowStatus;
	}

	public Integer getPsrchId() {
		return this.psrchId;
	}

	public void setPsrchId(Integer psrchId) {
		this.psrchId = psrchId;
	}

	public String getPsrchName() {
		return this.psrchName;
	}

	public void setPsrchName(String psrchName) {
		this.psrchName = psrchName;
	}

	public String getPsrchRefColumn() {
		return this.psrchRefColumn;
	}

	public void setPsrchRefColumn(String psrchRefColumn) {
		this.psrchRefColumn = psrchRefColumn;
	}

	public int getPsrchIdenFlag() {
		return this.psrchIdenFlag;
	}

	public void setPsrchIdenFlag(int psrchIdenFlag) {
		this.psrchIdenFlag = psrchIdenFlag;
	}

	public boolean isPsrchRowStatus() {
		return this.psrchRowStatus;
	}

	public void setPsrchRowStatus(boolean psrchRowStatus) {
		this.psrchRowStatus = psrchRowStatus;
	}

}
