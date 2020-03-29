package com.rohit.myblog.exceptions;

import java.text.MessageFormat;

public class BlogException extends Exception{
	
	private int errorCode;
	private String errorMessage;
	private Exception e;
	
	public BlogException(Error error,Exception e) {
		this.errorCode = error.getStatus().value();
		this.errorMessage = error.getMsg();
		this.e = e;
	}
	
	public BlogException(Error error,String[] params,Exception e) {
		this.errorCode = error.getStatus().value();
		MessageFormat mf = new MessageFormat(error.getMsg());
		this.errorMessage = mf.format(params);
		this.e = e;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
