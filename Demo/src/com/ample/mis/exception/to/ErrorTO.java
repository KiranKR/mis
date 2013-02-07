package com.ample.mis.exception.to;


public class ErrorTO {
	private String errorCode = "";
	private String errorMessage = "";
	private String serviceName = "";

	public ErrorTO() {
		super();
	}

	public ErrorTO(String errorCode, String errorMessage, String serviceName) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.serviceName = serviceName;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceName() {
		return serviceName;
	}

}
