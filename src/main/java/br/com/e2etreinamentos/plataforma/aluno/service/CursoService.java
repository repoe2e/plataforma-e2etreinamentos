package br.com.e2etreinamentos.plataforma.aluno.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.e2etreinamentos.plataforma.aluno.model.Curso;
import br.com.e2etreinamentos.plataforma.aluno.repository.CursoRepository;

@Service
@Transactional
public class CursoService {

    @Autowired
    CursoRepository cursoRepository;

    public Curso cadastrarCurso(Curso curso) {

        // Validação do nome do curso
        if (curso.getNomeCurso() == null || curso.getNomeCurso().isEmpty()) {
            throw new IllegalArgumentException("O nome do curso não pode ser nulo ou vazio.");
        }

        // Validação da descrição do curso
        if (curso.getDescricaoCurso() == null || curso.getDescricaoCurso().isEmpty()) {
            throw new IllegalArgumentException("A descrição do curso não pode ser nula ou vazia.");
        }

        // Validação da duração do curso (em horas)
        if (curso.getHorasCurso() <= 0) {
            throw new IllegalArgumentException("A duração do curso deve ser maior que zero.");
        }

        // Validação da recorrência das aulas
        if (curso.getRecorrenciaCurso() == null || curso.getRecorrenciaCurso().isEmpty()) {
            throw new IllegalArgumentException("A recorrência de aulas não pode ser nula ou vazia.");
        } else if (!curso.getRecorrenciaCurso().matches("Quinzenal|Semanal|Diário")) {
            throw new IllegalArgumentException("A recorrência de aulas deve ser Quinzenal, Semanal ou Diário.");
        }

        // Validação dos dias de aula
        if (curso.getDiasAula() == null || curso.getDiasAula().isEmpty()) {
            throw new IllegalArgumentException("Os dias de aula não podem ser nulos ou vazios.");
        }

        // Validação da modalidade
        if (curso.getModalidade() == null || curso.getModalidade().isEmpty()) {
            throw new IllegalArgumentException("A modalidade do curso não pode ser nula ou vazia.");
        } else if (!curso.getModalidade().matches("presencial|online|híbrida")) {
            throw new IllegalArgumentException("A modalidade do curso deve ser presencial, online ou híbrida.");
        }

        // Validação do número máximo de vagas
        if (curso.getMaxVagas() < 1) {
            throw new IllegalArgumentException("O número máximo de vagas deve ser maior ou igual a 1.");
        }

        // Validação do nível do curso
        if (curso.getNivelCurso() == null || curso.getNivelCurso().isEmpty()) {
            throw new IllegalArgumentException("O nível do curso não pode ser nulo ou vazio.");
        } else if (!curso.getNivelCurso().matches("Básico|Intermediário|Avançado")) {
            throw new IllegalArgumentException("O nível do curso deve ser Básico, Intermediário ou Avançado.");
        }

        // Salvar o curso no banco de dados
        return cursoRepository.save(curso);
    }
}
