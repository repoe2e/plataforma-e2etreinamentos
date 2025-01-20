package br.com.e2etreinamentos.plataforma.aluno.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfig {
	
	 @Bean
	    public ObjectMapper objectMapper() {
	        return Jackson2ObjectMapperBuilder.json()
	                .modulesToInstall(new JavaTimeModule())
	                .build();
	    }
	 
	 
}
