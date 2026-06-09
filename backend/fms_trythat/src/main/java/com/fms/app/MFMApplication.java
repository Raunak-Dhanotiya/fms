package com.fms.app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;


import com.fms.app.security.AuthorizeUserInfo;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
//@EnableCaching
public class MFMApplication extends ServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MFMApplication.class, args);
		
		
	}
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}

	@Bean
	public AuthorizeUserInfo authoriseInfo() {
		return new AuthorizeUserInfo();
	}


	@Bean
	@Profile({ "dev" })
	public void configDev() {
		System.out.println("[INFO] : Running Enviroment : Development Mode is on");
	}

	@Bean
	@Profile({ "prod" })
	public void configProd()  {
		System.out.println("[INFO] : Running Enviroment : Productions Mode is on");
	}

}


