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
