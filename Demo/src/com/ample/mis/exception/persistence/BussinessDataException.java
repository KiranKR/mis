package com.ample.mis.exception.persistence;

public class BussinessDataException extends ApplicationException{

	private static final long serialVersionUID = 12L;
	public BussinessDataException(){super();}
	public BussinessDataException(String pErrorCode, String pErrorMessage,
			String pServiceName) {
		super(pErrorCode, pErrorMessage,
				 pServiceName);
	}
	public BussinessDataException(String pErrorMessage) {
		super(pErrorMessage);
	}
	public BussinessDataException(String pErrorMessage,Throwable throwable) {
		super(pErrorMessage,throwable);
	}
	
}
