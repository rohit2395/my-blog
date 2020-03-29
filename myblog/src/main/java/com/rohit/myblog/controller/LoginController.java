package com.rohit.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.myblog.dto.UserRegistration;
import com.rohit.myblog.exceptions.BlogException;
import com.rohit.myblog.service.LoginService;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@PostMapping("/signup")
	public ResponseEntity signup(@RequestBody UserRegistration userRegistration) throws BlogException {
		System.out.println("Receieved request for user registration..");
		loginService.createUser(userRegistration);
		return new ResponseEntity(HttpStatus.OK);
	}

}
