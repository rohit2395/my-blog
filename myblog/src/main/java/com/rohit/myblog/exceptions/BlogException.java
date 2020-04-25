package com.rohit.myblog.exceptions;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BlogException extends Exception{
	
	private HttpStatus errorCode;
	private String errorMessage;
	private Exception e;
	
	public BlogException(Error error,Exception e) {
		super(error.getMsg());
		this.errorCode = error.getStatus();
		this.errorMessage = error.getMsg();
		this.e = e;
	}
	
	public BlogException(Error error,String[] params,Exception e) {
		super(error.getMsg());
		this.errorCode = error.getStatus();
		MessageFormat mf = new MessageFormat(error.getMsg());
		this.errorMessage = mf.format(params);
		this.e = e;
	}

	public HttpStatus getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(HttpStatus errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
