package br.com.e2etreinamentos.plataforma.aluno.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.e2etreinamentos.plataforma.aluno.model.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{
	boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
}
