package com.carspotter.CarSpotter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class CarSpotterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarSpotterApplication.class, args);
	}

}
