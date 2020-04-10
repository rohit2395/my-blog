package com.rohit.myblog.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rohit.myblog.exceptions.BlogException;
import com.rohit.myblog.exceptions.Error;
import com.rohit.myblog.model.Role;
import com.rohit.myblog.model.User;
import com.rohit.myblog.repo.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user;
		UserDetails userDetails = null;
		user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
		userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				true, true, true, true, getAuthorites(user.getRole()));
		return userDetails;
	}

	private Collection<? extends GrantedAuthority> getAuthorites(String role) {
		return Collections.singletonList(new SimpleGrantedAuthority(role));
	}
}
