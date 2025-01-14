package com.carspotter.CarSpotter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CarSpotterApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarSpotterApplication.class, args);
    }

}
