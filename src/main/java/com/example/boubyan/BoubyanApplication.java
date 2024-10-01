package com.example.boubyan;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
@EnableCaching
public class BoubyanApplication  extends SpringBootServletInitializer  implements WebApplicationInitializer {




	public static void main(String[] args) {
		SpringApplication.run(BoubyanApplication.class, args);
	}



}
