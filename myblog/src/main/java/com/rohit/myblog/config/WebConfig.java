package com.rohit.myblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.rohit.myblog.common.BlogConstants;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{
	
	@Override
	public void addCorsMappings(CorsRegistry corsRegistry) {
		corsRegistry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("*")
			.maxAge(3600L)
			.allowedHeaders("*")
			.exposedHeaders(BlogConstants.AUTHORIZATION,BlogConstants.TOKEN_HEADER_KEY)
			.allowCredentials(true);
	}
}
