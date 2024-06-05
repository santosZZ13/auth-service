package org.authen;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableScheduling
@EnableCaching
@RestController
public class App{
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
//		BearerTokenAuthenticationFilter
	}

}
