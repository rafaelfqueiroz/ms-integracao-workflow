package com.github.rafaelfqueiroz.msintegracaoworkflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MsIntegracaoWorkflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsIntegracaoWorkflowApplication.class, args);
	}

}
