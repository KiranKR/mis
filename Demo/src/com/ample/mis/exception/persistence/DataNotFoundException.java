package com.ample.mis.exception.persistence;

public class DataNotFoundException extends ApplicationException

{
	
	private static final long serialVersionUID = 14L;
	public DataNotFoundException(){super();}
	public DataNotFoundException(String pErrorCode, String pErrorMessage,
			String pServiceName) {
		super(pErrorCode, pErrorMessage,
				 pServiceName);
	}
	public DataNotFoundException(String pErrorMessage) {
		super(pErrorMessage);
	}
	public DataNotFoundException(String pErrorMessage,Throwable throwable) {
		super(pErrorMessage,throwable);
	}

}
