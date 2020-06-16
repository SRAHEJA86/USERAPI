package com.herokuapp.api;

import com.herokuapp.api.model.UsersInfo;
import com.herokuapp.api.service.UserInfoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class UserApplication {

public static void main(String[] args) {

	SpringApplication.run(UserApplication.class, args);
	}


	@Bean
	public RestTemplate getRestTemplate() {
	  return new RestTemplate();
	}

	@Bean
	@Scope("prototype")
	public UsersInfo getUserInfo(){
	  return new UsersInfo();
	}

	@Bean
	public UserInfoService getUserInfoService(){
	   return new UserInfoService();
	}

}
