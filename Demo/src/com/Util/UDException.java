package com.Util;


public class UDException extends Exception{

	private static final long serialVersionUID = 1L;

	private String errorCode = null;
	private String errorMsg = null;
	private String refNo = null;
	
	public UDException() {
		super();
	}
	public UDException(Throwable e) {
		super(e);
	}
	public UDException(String errorCode) {
		this.errorCode = errorCode;
	}

	public UDException(String errorCode,Throwable e) {
		super(e);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
}
