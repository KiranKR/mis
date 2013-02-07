package com.ample.mis.exception.persistence;

import com.ample.mis.exception.to.ErrorTO;




@SuppressWarnings("serial")
public class ConcurrencyException extends Exception{
	public ErrorTO errorTO = null;

	private ConcurrencyException() {
		// TODO Auto-generated constructor stub
	}

	public ConcurrencyException(String pErrorCode, String pErrorMessage,
			String pServiceName) {

		errorTO = new ErrorTO(pErrorCode, pErrorMessage, pServiceName);
	}

	public ConcurrencyException(String pErrorMessage) {

		errorTO = new ErrorTO();
		errorTO.setErrorMessage(pErrorMessage);
	}

}
