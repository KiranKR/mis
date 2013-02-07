package com.ample.mis.exception.persistence;

import com.ample.mis.exception.to.ErrorTO;


@SuppressWarnings("serial")
public class DatabaseException extends Exception{
	public ErrorTO errorTO = null;

	private DatabaseException() {
		// TODO Auto-generated constructor stub
	}

	public DatabaseException(String pErrorCode, String pErrorMessage,
			String pServiceName) {

		errorTO = new ErrorTO(pErrorCode, pErrorMessage, pServiceName);
	}

	public DatabaseException(String pErrorMessage) {

		errorTO = new ErrorTO();
		errorTO.setErrorMessage(pErrorMessage);
	}

}
