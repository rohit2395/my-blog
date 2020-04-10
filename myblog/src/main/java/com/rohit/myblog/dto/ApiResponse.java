package com.rohit.myblog.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiResponse {
	
	   private HttpStatus status;
	   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	   private LocalDateTime timestamp;
	   private String message;
	   
	   private ApiResponse() {
	       timestamp = LocalDateTime.now();
	   }

	   public ApiResponse(HttpStatus status) {
	       this();
	       this.status = status;
	       this.message = "Unexpected error";
	   }

	   public ApiResponse(HttpStatus status, String message) {
	       this();
	       this.status = status;
	       this.message = message;
	   }

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String errorMessage) {
		this.message = errorMessage;
	}
	   
}
