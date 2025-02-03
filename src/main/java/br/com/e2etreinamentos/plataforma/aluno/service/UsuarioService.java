package br.com.e2etreinamentos.plataforma.aluno.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.e2etreinamentos.plataforma.aluno.model.Usuario;
import br.com.e2etreinamentos.plataforma.aluno.repository.UsuarioRepository;
import br.com.e2etreinamentos.plataforma.aluno.utils.CpfUtils;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public Usuario cadastrarUsuario(Usuario usuario) {

		if (usuario.getNomeCompleto() == null || usuario.getNomeCompleto().trim().isEmpty()
				|| !isValidName(usuario.getNomeCompleto())) {
			throw new IllegalArgumentException("Nome em branco, null ou inválido.");
		}

		if (usuarioRepository.existsByCpf(usuario.getCpf())) {
			throw new IllegalArgumentException("CPF já cadastrado.");
		}

		if (usuario.getCpf() == null || usuario.getCpf().trim().isEmpty() || !CpfUtils.isCpfValid(usuario.getCpf()) || usuario.getCpf().length() != 11) {
			throw new IllegalArgumentException(
					"CPF inexistente ou inválido. Deve conter exatamente 11 dígitos numéricos.");
		}

		if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
			throw new IllegalArgumentException("E-mail em branco ou null");
		}

		if (!isValidEmail(usuario.getEmail())) {
			throw new IllegalArgumentException("E-mail fora do padrão nome@e2etreinamentos.com.br");
		}

		if (usuarioRepository.existsByEmail(usuario.getEmail())) {
			throw new IllegalArgumentException("E-mail já cadastrado.");
		}

		if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
			throw new IllegalArgumentException("Senha em branco ou null.");
		}

		if (usuario.getSenha().length() < 10 || usuario.getSenha().length() > 15) {
			throw new IllegalArgumentException("Senha deve ter entre 10 a 15 digitos.");
		} 
		
		if(!isValidSenha(usuario.getSenha())) {
			throw new IllegalArgumentException(
					"Formato de senha inválido. A senha deve conter pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial");
		}

		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}

	public Usuario validarLogin(String email, String senha) {

		if (email == null || email.trim().isEmpty()) {
			throw new IllegalArgumentException("E-mail em branco ou null.");
		}

		if (!isValidEmail(email)) {
			throw new IllegalArgumentException("E-mail fora do padrão nome@e2etreinamentos.com.br.");
		}

		Usuario usuario = usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("O e-mail fornecido não é válido ou inexistente."));

		if (senha == null || senha.trim().isEmpty()) {
			throw new IllegalArgumentException("Senha em branco ou null.");
		}

		if (senha.length() < 10 && senha.length() > 15) {
			throw new IllegalArgumentException("Senha deve ter entre 10 a 15 digitos.");
		}

		if (!isValidSenha(senha)) {
			throw new IllegalArgumentException(
					"Formato de senha inválido. A senha deve conter pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial.");
		}

		return usuario;
	}

	static boolean isValidSenha(String senha) {
		String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$";
		// Verifica se a senha corresponde à expressão regular
		return senha != null && senha.matches(regex);
	}

	private boolean isValidEmail(String email) {
		// Expressão regular que aceita apenas e-mails com o domínio
		// @e2etreinamentos.com.br
		String emailRegex = "^[A-Za-z0-9+_.-]+@e2etreinamentos\\.com\\.br$";
		Pattern pattern = Pattern.compile(emailRegex);
		return pattern.matcher(email).matches();
	}

	private boolean isValidName(String name) {
		return name.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]{2,}(\\s[A-Za-zÀ-ÖØ-öø-ÿ]{2,})+$");
	}

}
