package br.com.e2etreinamentos.plataforma.aluno.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.e2etreinamentos.plataforma.aluno.model.SessaoUsuario;

public interface SessaoUsuarioRepository extends JpaRepository<SessaoUsuario, Long> {
    boolean existsByEmail(String email);
    void deleteByEmail(String email);
}