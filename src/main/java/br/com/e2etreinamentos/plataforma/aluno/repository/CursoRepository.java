package br.com.e2etreinamentos.plataforma.aluno.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.e2etreinamentos.plataforma.aluno.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	// Método para buscar um curso pelo nome
	Optional<Curso> findByNomeCurso(String nomeCurso);

	// Método para buscar um curso pelo ID, caso seja necessário
	Optional<Curso> findById(Long id);

	// Método para buscar cursos pelo nível (exemplo: Básico, Intermediário,
	// Avançado)
	@Query("SELECT c FROM Curso c WHERE c.nivelCurso = :nivelCurso")
	List<Curso> findByNivelCurso(String nivelCurso);

	// Outros métodos personalizados conforme necessário
}
