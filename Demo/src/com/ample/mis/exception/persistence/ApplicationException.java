package com.ample.mis.exception.persistence;

import com.ample.mis.exception.to.ErrorTO;


@SuppressWarnings("serial")
public class ApplicationException  extends Exception{
	public ErrorTO errorTO = null;

	public ApplicationException() {
		// TODO Auto-generated constructor stub
	}

	/*public ApplicationException(String pErrorCode, String pErrorMessage,
			String pServiceName) {

		errorTO = new ErrorTO(pErrorCode, pErrorMessage, pServiceName);
	}

	public ApplicationException(String pErrorMessage) {

		errorTO = new ErrorTO();
		errorTO.setErrorMessage(pErrorMessage);
	}*/
	
	public ApplicationException(String pErrorCode, String pErrorMessage,
			String pServiceName) {

	}

	public ApplicationException(String pErrorMessage) {
		
	}
	public ApplicationException(String pErrorMessage,Throwable throwable) {
	
	}

}