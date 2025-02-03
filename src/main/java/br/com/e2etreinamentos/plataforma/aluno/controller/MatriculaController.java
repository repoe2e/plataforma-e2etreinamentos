package br.com.e2etreinamentos.plataforma.aluno.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.e2etreinamentos.plataforma.aluno.model.Matricula;
import br.com.e2etreinamentos.plataforma.aluno.repository.SessaoUsuarioRepository;
import br.com.e2etreinamentos.plataforma.aluno.service.MatriculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/matricula")
public class MatriculaController {

	@Autowired
	private MatriculaService matriculaService;

	@Autowired
	private SessaoUsuarioRepository sessaoUsuarioRepository;

	@Operation(summary = "Matricular um novo aluno", description = "Este endpoint cria uma nova matricula de aluno na plataforma.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Matricula cadastrada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos") })

	@PostMapping("/cadastro")
	public ResponseEntity<String> cadastrarMatricula(@Valid @RequestBody Matricula matricula) {

		// Verifica se já existe uma sessão ativa
		if (!sessaoUsuarioRepository.existsByEmail(matricula.getEmailUsuario())) {
			return ResponseEntity.badRequest().body("Permitido somente para usuários logados.");
		}

		try {
			matriculaService.cadastrarMatricula(matricula);
			return ResponseEntity.status(HttpStatus.CREATED).body("Aluno matriculado com sucesso!");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

}
