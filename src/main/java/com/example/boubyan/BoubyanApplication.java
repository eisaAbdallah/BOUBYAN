package com.example.boubyan;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class BoubyanApplication{




	public static void main(String[] args) {
		SpringApplication.run(BoubyanApplication.class, args);
	}



}
