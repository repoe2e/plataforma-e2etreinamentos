package br.com.e2etreinamentos.plataforma.aluno.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.e2etreinamentos.plataforma.aluno.model.Curso;
import br.com.e2etreinamentos.plataforma.aluno.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/curso")
public class CursoCrontoller {
	
	@Autowired
	private CursoService cursoService;

	 @Operation(summary = "Cadastrar um novo curso", description = "Este endpoint cria um novo curso na plataforma.")
	    @ApiResponses(value = {
	        @ApiResponse(responseCode = "201", description = "Curso cadastrado com sucesso"),
	        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
	    })	
	
	 @PostMapping("/cadastro")
	public ResponseEntity<String> cadastrarCurso(@Valid @RequestBody Curso curso){
	
		try {
			cursoService.cadastrarCurso(curso);
            return ResponseEntity.status(HttpStatus.CREATED).body("Curso cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
	}
	
}
