package com.weatherwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class WeatherApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}

}
