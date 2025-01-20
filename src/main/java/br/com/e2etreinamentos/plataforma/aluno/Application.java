package br.com.e2etreinamentos.plataforma.aluno;

import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		SpringDocUtils.getConfig().addHiddenRestControllers("/api/**");
	}

}
