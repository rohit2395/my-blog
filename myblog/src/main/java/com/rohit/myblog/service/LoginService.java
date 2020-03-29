package com.rohit.myblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohit.myblog.dto.UserRegistration;
import com.rohit.myblog.exceptions.BlogException;
import com.rohit.myblog.exceptions.Error;
import com.rohit.myblog.model.User;
import com.rohit.myblog.repo.UserRepository;

@Service
public class LoginService {
	
	@Autowired
	private UserRepository userRepository;
	
	public void createUser(UserRegistration userRegistration) throws BlogException{
		User user = new User();
		try {
			user.setUsername(userRegistration.getUsername());
			user.setPassword(userRegistration.getPassword());
			user.setEmail(userRegistration.getEmail());
			userRepository.save(user);
		}catch(Exception e) {
			System.out.println("Failed to register user");
			e.printStackTrace();
			throw new BlogException(Error.USER_REGISTRATION_FAILED,e);
		}
		
	}
	
}
