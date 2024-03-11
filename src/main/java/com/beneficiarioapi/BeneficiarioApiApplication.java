package com.beneficiarioapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Swagger OpenApi", version = "1", description = "API Beneficiário de Plano de Saúde"))
public class BeneficiarioApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeneficiarioApiApplication.class, args);
	}

}
