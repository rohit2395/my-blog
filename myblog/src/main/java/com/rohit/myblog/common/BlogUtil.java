package com.rohit.myblog.common;

import org.springframework.http.HttpStatus;

import com.rohit.myblog.dto.ApiResponse;
import com.rohit.myblog.exceptions.BlogException;

public class BlogUtil {
	
	
	public static ApiResponse buildApiResoponse(String message ,HttpStatus status) {
		ApiResponse apiError = new ApiResponse(status.value(),message);
		return apiError;
	}
	public static ApiResponse buildApiResoponse(BlogException e) {
		ApiResponse apiError = new ApiResponse(e.getErrorCode().value(), e.getErrorMessage());
		return apiError;
	}

}
