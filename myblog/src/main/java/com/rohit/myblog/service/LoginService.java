package com.rohit.myblog.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rohit.myblog.controller.LoginController;
import com.rohit.myblog.dto.UserLogin;
import com.rohit.myblog.dto.UserRegistration;
import com.rohit.myblog.exceptions.BlogException;
import com.rohit.myblog.exceptions.Error;
import com.rohit.myblog.model.Role;
import com.rohit.myblog.model.User;
import com.rohit.myblog.repo.UserRepository;
import com.rohit.myblog.security.JwtTokenProvider;


@Service
public class LoginService {
	
	public static final Logger LOG = LogManager.getLogger(LoginService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	public void createUser(UserRegistration userRegistration) throws BlogException{
		User user = new User();
		try {
			LOG.info("Registering user {}",userRegistration.getUsername());
			user.setUsername(userRegistration.getUsername());
			user.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
			user.setEmail(userRegistration.getEmail());
			user.setRole(Role.USER.getVal());
			userRepository.save(user);
		}catch(Exception e) {
			LOG.error("Failed to register user",e);
			throw new BlogException(Error.USER_REGISTRATION_FAILED,e);
		}
		
	}

	public String login(UserLogin userLogin) throws BlogException{
		// TODO Auto-generated method stub
		LOG.info("Authenticating user {}",userLogin.getUsername());
		Authentication authentication = null;
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUsername(),
				userLogin.getPassword()));
		}catch(BadCredentialsException e) {
			LOG.error("Username or password is invalid",e);
			throw new BlogException(Error.USER_LOGIN_FAILED,new String[] {userLogin.getUsername()}, e);
		}catch(Exception e) {
			LOG.error("Unexpected error occurred while authenticating user {}",userLogin.getUsername(),e);
			throw new BlogException(Error.GENERAL_ERROR, e);
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return jwtTokenProvider.generateToken(authentication);
	}
	
}
