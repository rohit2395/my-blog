/**
 * @author	Rohit Rajbanshi
 * @since	Apr 25, 2020
 */
package com.rohit.myblog.config;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.rohit.myblog.exceptions.Error;

/**
 * @author Rohit Rajbanshi
 * @since	Apr 25, 2020
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable{

	private static final long serialVersionUID = -7858869558953243875L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		response.sendError(Error.INALID_TOKEN.getStatus().value(),Error.INALID_TOKEN.getMsg());
	}
	
}
