package br.com.e2etreinamentos.plataforma.aluno.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.e2etreinamentos.plataforma.aluno.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

}
