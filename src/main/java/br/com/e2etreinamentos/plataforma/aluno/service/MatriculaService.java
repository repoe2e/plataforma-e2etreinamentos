package br.com.e2etreinamentos.plataforma.aluno.service;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.e2etreinamentos.plataforma.aluno.model.Matricula;
import br.com.e2etreinamentos.plataforma.aluno.repository.AlunoRepository;
import br.com.e2etreinamentos.plataforma.aluno.repository.CursoRepository;
import br.com.e2etreinamentos.plataforma.aluno.repository.MatriculaRepository;

@Service
@Transactional
public class MatriculaService {

	@Autowired
	private MatriculaRepository matriculaRepository;

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private AlunoRepository alunoRepository;

	private static final List<String> STATUS_VALIDOS = List.of("ativa", "pendente de contrato", "cancelada", "inativa");

	public Matricula cadastrarMatricula(Matricula matricula) {
		validarCampos(matricula);
		return matriculaRepository.save(matricula);
	}

	private void validarCampos(Matricula matricula) {
		LocalDate hoje = LocalDate.now();

		if (matricula.getCpfAluno() == null || matricula.getCpfAluno().trim().isEmpty()
				|| !alunoRepository.existsByCpf(matricula.getCpfAluno())) {
			throw new IllegalArgumentException("CPF do aluno não cadastrado, não informado branco ou null.");
		}

		if (matricula.getEmailAluno() == null || matricula.getEmailAluno().trim().isEmpty()
				|| !isValidEmail(matricula.getEmailAluno())) {
			throw new IllegalArgumentException("E-mail do aluno não pode ser null ou inválido.");
		}

		if (!isValidEmail(matricula.getEmailAluno())) {
			throw new IllegalArgumentException("E-mail do aluno não cadastrado.");
		}

		if (!cursoRepository.existsById(matricula.getCursoId())) {
			throw new IllegalArgumentException("ID não cadastrado como um curso.");
		}

		if (matriculaRepository.existsByEmailAlunoAndCursoId(matricula.getEmailAluno(), matricula.getCursoId())) {
			throw new IllegalArgumentException("Este aluno já está matriculado neste curso.");
		}

		if (matricula.getEmailUsuario() == null || matricula.getEmailUsuario().trim().isEmpty()
				|| !isValidEmail(matricula.getEmailUsuario())) {
			throw new IllegalArgumentException("E-mail do Usuário inválido.");
		}

		if (matricula.getPrevisaoInicioAulas() != null && matricula.getPrevisaoInicioAulas().isBefore(hoje)) {
			throw new IllegalArgumentException(
					"A previsão de início das aulas não pode ser menor que a data da matrícula.");
		}

	


		if (matricula.getPrevisaoInicioAulas() == null) {
			throw new IllegalArgumentException(
					"A previsão de Inicio das aulas não pode null ou em branco.");
		}
		
		if (matricula.getPrevisaoEncerramentoAulas() == null) {
			throw new IllegalArgumentException(
					"A previsão de encerramento das aulas não pode null ou em branco.");
		}
		
		if (matricula.getPrevisaoEncerramentoAulas().isBefore(hoje)) {
			throw new IllegalArgumentException(
					"A previsão de encerramento das aulas não pode ser menor que a data da matrícula.");
		}
		
		if (matricula.getPrevisaoInicioAulas() != null
				&& matricula.getPrevisaoEncerramentoAulas().isBefore(matricula.getPrevisaoInicioAulas())) {
			throw new IllegalArgumentException(
					"A previsão de encerramento das aulas não pode ser menor que a previsão de início das aulas.");
		}

		if (matricula.getDescricaoTurma() == null || matricula.getDescricaoTurma().trim().isEmpty()) {
			throw new IllegalArgumentException("A descrição da turma não pode ser vazia.");
		}

		int descricaoLength = matricula.getDescricaoTurma().trim().length();
		if (descricaoLength <= 5 || descricaoLength >= 15) {
			throw new IllegalArgumentException("A descrição da turma deve ter entre 5 e 15 caracteres.");
		}

		if (!STATUS_VALIDOS.contains(matricula.getStatusMatricula().toLowerCase())) {
			throw new IllegalArgumentException("Status de matrícula inválido. Valores permitidos: " + STATUS_VALIDOS);
		}

	}

	// Método para validar o formato de e-mail
	private boolean isValidEmail(String email) {
		String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
		Pattern pattern = Pattern.compile(emailRegex);
		return pattern.matcher(email).matches();
	}

}