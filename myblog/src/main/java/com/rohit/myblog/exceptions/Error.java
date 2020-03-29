package com.rohit.myblog.exceptions;

import org.springframework.http.HttpStatus;

public enum Error {
	
	GENERAL_ERROR("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR),
	
	
	//User login errors
	USER_REGISTRATION_FAILED("User {0} registration failed",HttpStatus.INTERNAL_SERVER_ERROR);
	
	private String msg;
	private HttpStatus status;
	
	Error(String msg,HttpStatus status){
		this.msg = msg;
		this.status = status;
	}
	
	public String getMsg() {
		return this.msg;
	}
	
	public HttpStatus getStatus() {
		return this.status;
	}

}
