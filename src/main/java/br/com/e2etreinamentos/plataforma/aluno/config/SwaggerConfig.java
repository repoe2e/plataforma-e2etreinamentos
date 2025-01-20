package br.com.e2etreinamentos.plataforma.aluno.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	 @Bean
	    public GroupedOpenApi publicApi() {
	        return GroupedOpenApi.builder()
	                .group("public-api")
	                .pathsToMatch("/api/**")  // Matches endpoints under /api/*
	                .build();
	    }
}