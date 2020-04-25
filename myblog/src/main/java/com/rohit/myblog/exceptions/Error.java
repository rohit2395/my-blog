package com.rohit.myblog.exceptions;

import org.springframework.http.HttpStatus;

public enum Error {
	
	GENERAL_ERROR("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR),
	
	
	//User login errors
	USER_REGISTRATION_FAILED("User {0} registration failed",HttpStatus.INTERNAL_SERVER_ERROR),
	USER_LOGIN_FAILED("Authentication failed for user {0}",HttpStatus.UNAUTHORIZED),
	USER_NOT_LOGGED_IN("User not logged in",HttpStatus.UNAUTHORIZED),
	INALID_TOKEN("Token is invalid",HttpStatus.FORBIDDEN),
	
	
	//Blog posts errors
	FAILED_TO_CREATE_NEW_POST("Failed to create new blog post",HttpStatus.INTERNAL_SERVER_ERROR),
	FAILED_TO_GE_ALL_POSTS("Failed to all posts",HttpStatus.INTERNAL_SERVER_ERROR),
	BLOG_POST_NOT_FOUND("The blog post not found with ID {0}",HttpStatus.NOT_FOUND), 
	FAILED_TO_LOAD_KEYSTORE("Failed to load keystore",HttpStatus.INTERNAL_SERVER_ERROR),
	FAILED_TO_LOAD_KEY("Failed to get private key from keystore",HttpStatus.INTERNAL_SERVER_ERROR),
	
	
	;
	
	
	
	
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
