package br.com.e2etreinamentos.plataforma.aluno.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.e2etreinamentos.plataforma.aluno.model.Matricula;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

	
	// Método para buscar um curso pelo ID, caso seja necessário
		Optional<Matricula> findById(Long cursoId);
		
		 @Query("SELECT COUNT(m) > 0 FROM Matricula m WHERE m.emailAluno = :emailAluno AND m.cursoId = :cursoId")
		    boolean existsByEmailAlunoAndCursoId(String emailAluno, Long cursoId);
		
}



