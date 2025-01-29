package br.com.e2etreinamentos.plataforma.aluno.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.e2etreinamentos.plataforma.aluno.model.Usuario;
import br.com.e2etreinamentos.plataforma.aluno.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
	
	
	@Autowired
	private UsuarioService usuarioService;

	
	@Operation(summary = "Cadastrar um novo usuário", description = "Cadastra um novo usuário com nome, CPF, email e senha segura.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso", 
		             content = @Content(mediaType = "application/json", 
		                               schema = @Schema(implementation = Usuario.class))),
		@ApiResponse(responseCode = "400", description = "Dados inválidos ou falha na validação do usuário"),
		@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
	})
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuarioDTO) {
		Usuario usuario = usuarioService.cadastrarUsuario(usuarioDTO);
		return ResponseEntity.ok(usuario);
	}

}
