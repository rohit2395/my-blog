package com.rohit.myblog.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.myblog.common.BlogConstants;
import com.rohit.myblog.common.BlogUtil;
import com.rohit.myblog.common.UIMessages;
import com.rohit.myblog.dto.ApiResponse;
import com.rohit.myblog.dto.UserLogin;
import com.rohit.myblog.dto.UserRegistration;
import com.rohit.myblog.exceptions.BlogException;
import com.rohit.myblog.service.LoginService;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
	
	public static final Logger LOG = LogManager.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody UserRegistration userRegistration){
		LOG.info("Receieved request for user registration..");
		try {
			loginService.createUser(userRegistration);
		} catch (BlogException e) {
			return new ResponseEntity<ApiResponse>(BlogUtil.buildApiResoponse(e),e.getErrorCode());
		}
		ResponseEntity<ApiResponse> res = new ResponseEntity<ApiResponse>(
				BlogUtil.buildApiResoponse(UIMessages.USER_REGISTERED,HttpStatus.OK)
				,HttpStatus.OK);
		return res;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserLogin userLogin){
		LOG.info("Receieved request for user login..");
		StringBuilder token = null;
		try {
			token = new StringBuilder("Bearer ");
			token.append(loginService.login(userLogin));			
		}catch(BlogException e) {
			return new ResponseEntity<ApiResponse>(BlogUtil.buildApiResoponse(e),e.getErrorCode());
		}
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(BlogConstants.TOKEN_HEADER_KEY, token.toString());
		ResponseEntity<ApiResponse> res = new ResponseEntity<ApiResponse>(
				BlogUtil.buildApiResoponse(UIMessages.TOKEN_OBTAINED,HttpStatus.OK)
				,responseHeaders
				,HttpStatus.OK);
		return res;
	}

}
