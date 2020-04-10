package com.rohit.myblog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class MyblogApplication implements CommandLineRunner{
	
	public static final Logger LOG = LogManager.getLogger(MyblogApplication.class);
		
	@Autowired
	private Environment env;
	
	@Override
    public void run(String... args) throws Exception {

		LOG.info("JAVA: {}", env.getProperty("JAVA_HOME"));
		
		for (String profileName : env.getActiveProfiles()) {
			LOG.info("Currently active profile - " + profileName);
        } 
		
    }
	
	public static void main(String[] args) {
		
		SpringApplication.run(MyblogApplication.class, args);
		
		LOG.info("Starting the Blog Application Server...");
		
	}

}
