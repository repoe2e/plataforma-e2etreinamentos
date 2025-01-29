package br.com.e2etreinamentos.plataforma.aluno.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.e2etreinamentos.plataforma.aluno.model.SessaoUsuario;
import br.com.e2etreinamentos.plataforma.aluno.model.Usuario;
import br.com.e2etreinamentos.plataforma.aluno.repository.SessaoUsuarioRepository;
import br.com.e2etreinamentos.plataforma.aluno.secutiry.JwtUtil;
import br.com.e2etreinamentos.plataforma.aluno.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private SessaoUsuarioRepository sessaoUsuarioRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Operation(summary = "Realizar login", description = "Este endpoint valida o login de um usuário e retorna um token JWT se os dados forem corretos.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Login realizado com sucesso", content = {
					@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Usuário já está logado ou dados de login inválidos") })
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String email, @RequestParam String senha) {
		Usuario usuario = usuarioService.validarLogin(email, senha);

		// Verifica se já existe uma sessão ativa
		if (sessaoUsuarioRepository.existsByEmail(email)) {
			return ResponseEntity.badRequest().body("Usuário já está logado em outra sessão!");
		}

		// Gera token JWT
		String token = jwtUtil.generateToken(usuario.getEmail());

		// Armazena a sessão no banco de dados
		SessaoUsuario sessao = new SessaoUsuario(email, token);
		sessaoUsuarioRepository.save(sessao);

		return ResponseEntity.ok(Map.of("token", token));
	}

	 @Operation(summary = "Realizar logout", description = "Este endpoint realiza o logout de um usuário, removendo a sessão ativa.")
	    @ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Logout realizado com sucesso"),
	        @ApiResponse(responseCode = "400", description = "E-mail não está logado ou não existe sessão ativa")
	    })
	@PostMapping("/logout")
	@Transactional
	public ResponseEntity<?> logout(@RequestParam String email) {
		// Verifica se o e-mail está correto
		if (!sessaoUsuarioRepository.existsByEmail(email)) {
			return ResponseEntity.badRequest().body("E-mail não está logado ou não existe sessão ativa.");
		}

		// Remove a sessão do banco de dados
		sessaoUsuarioRepository.deleteByEmail(email);
		return ResponseEntity.ok("Logout realizado com sucesso!");
	}
}
