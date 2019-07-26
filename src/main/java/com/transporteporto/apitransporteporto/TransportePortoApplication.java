package com.transporteporto.apitransporteporto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.transporteporto.apitransporteporto", "controller", "service"})
@SpringBootApplication
public class TransportePortoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransportePortoApplication.class, args);
	}

}
