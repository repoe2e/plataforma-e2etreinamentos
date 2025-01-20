package br.com.e2etreinamentos.plataforma.aluno.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.e2etreinamentos.plataforma.aluno.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
	boolean existsByCpf(String cpf);

	boolean existsByEmail(String email);
}
