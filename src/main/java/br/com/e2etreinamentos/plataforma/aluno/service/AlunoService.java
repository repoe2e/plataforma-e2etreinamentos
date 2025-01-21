package br.com.e2etreinamentos.plataforma.aluno.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.e2etreinamentos.plataforma.aluno.model.Aluno;
import br.com.e2etreinamentos.plataforma.aluno.repository.AlunoRepository;

@Service
@Transactional
public class AlunoService {

	@Autowired
	private AlunoRepository alunoRepository;

	public Aluno cadastrarAluno(Aluno aluno) {

		// Verificar se o nome completo é vazio ou nulo
		if (aluno.getNomeCompleto() == null || aluno.getNomeCompleto().trim().isEmpty()) {
			throw new IllegalArgumentException("Nome completo é obrigatório.");
		}

		// Verificar se o CPF é válido (11 dígitos numéricos)
		if (aluno.getCpf() == null || aluno.getCpf().length() != 11 || !aluno.getCpf().matches("\\d{11}")) {
			throw new IllegalArgumentException("CPF inválido. Deve conter exatamente 11 dígitos numéricos.");
		}

		// Verificar se o CPF já está cadastrado
		if (alunoRepository.existsByCpf(aluno.getCpf())) {
			throw new IllegalArgumentException("CPF já cadastrado.");
		}

		// Verificar se o e-mail é válido
		if (aluno.getEmail() == null || aluno.getEmail().trim().isEmpty() || !isValidEmail(aluno.getEmail())) {
			throw new IllegalArgumentException("E-mail inválido.");
		}

		// Verificar se o e-mail já está cadastrado
		if (alunoRepository.existsByEmail(aluno.getEmail())) {
			throw new IllegalArgumentException("E-mail já cadastrado.");
		}

		// Verificar se o RG é válido
		if (aluno.getRg() == null || aluno.getRg().trim().isEmpty()) {
			throw new IllegalArgumentException("RG é obrigatório.");
		}

		// Verificar se a data de nascimento é válida
		if (aluno.getDataNascimento() == null) {
			throw new IllegalArgumentException("Data de nascimento é obrigatória.");
		}

		// Verificar o formato da data de nascimento e se é válida
		if (!isValidDateFormat(aluno.getDataNascimento())) {
			throw new IllegalArgumentException("Data de nascimento fora do padrão. Formato esperado: yyyy-MM-dd.");
		}

		// Verificar se a idade é menor que 12 anos
		if (isMenorDeIdade(aluno.getDataNascimento())) {
			throw new IllegalArgumentException("Não é possível cadastrar menores de 12 anos.");
		}

		// Verificar se a nacionalidade é válida
		if (aluno.getNacionalidade() == null || aluno.getNacionalidade().trim().isEmpty()) {
			throw new IllegalArgumentException("Nacionalidade é obrigatória.");
		}

		// Verificar se a nacionalidade é válida
		if (aluno.getNacionalidade() == null || aluno.getNacionalidade().trim().isEmpty()) {
			throw new IllegalArgumentException("Nacionalidade é obrigatória.");
		}

		// Verificar se a profissão é válida
		if (aluno.getProfissao() == null || aluno.getProfissao().trim().isEmpty()) {
			throw new IllegalArgumentException("Profissão é obrigatória.");
		}

		// Verificar se a formação acadêmica é válida
		if (aluno.getFormacaoAcademica() == null || aluno.getFormacaoAcademica().trim().isEmpty()) {
			throw new IllegalArgumentException("Formação acadêmica é obrigatória.");
		}

		// Verificar se Informações de Contato não são nulas primeiro
		if (aluno.getInformacoesContato() == null) {
			throw new IllegalArgumentException("Informações de contato são obrigatórias.");
		}

		// Agora, pode acessar os atributos de Informações de Contato sem risco de
		// NullPointerException
		if (aluno.getInformacoesContato().getTelefone() == null
				|| aluno.getInformacoesContato().getTelefone().trim().isEmpty()) {
			throw new IllegalArgumentException("Telefone é obrigatório.");
		}

		if (aluno.getInformacoesContato().getWhatsapp() == null
				|| aluno.getInformacoesContato().getWhatsapp().trim().isEmpty()) {
			throw new IllegalArgumentException("Whatsapp é obrigatório.");
		}

		if (aluno.getInformacoesContato().getEndereco() == null
				|| aluno.getInformacoesContato().getEndereco().trim().isEmpty()) {
			throw new IllegalArgumentException("Endereço é obrigatório.");
		}

		if (aluno.getInformacoesContato().getNumero() == null
				|| aluno.getInformacoesContato().getNumero().trim().isEmpty()) {
			throw new IllegalArgumentException("Número é obrigatório.");
		}

		if (aluno.getInformacoesContato().getBairro() == null
				|| aluno.getInformacoesContato().getBairro().trim().isEmpty()) {
			throw new IllegalArgumentException("Bairro é obrigatório.");
		}

		if (aluno.getInformacoesContato().getEstado() == null
				|| aluno.getInformacoesContato().getEstado().trim().isEmpty()) {
			throw new IllegalArgumentException("Estado é obrigatório.");
		}

		if (aluno.getInformacoesContato().getCidade() == null
				|| aluno.getInformacoesContato().getCidade().trim().isEmpty()) {
			throw new IllegalArgumentException("Cidade é obrigatória.");
		}

		// Verificar se o CEP é válido
		if (aluno.getInformacoesContato().getCep() == null
				|| !aluno.getInformacoesContato().getCep().matches("\\d{8}")) {
			throw new IllegalArgumentException("CEP inválido. Deve conter exatamente 8 dígitos numéricos.");
		}

		// Após todas as validações, salvar o aluno no banco
		return alunoRepository.save(aluno);
	}

	// Método para validar o formato da data
	private boolean isValidDateFormat(LocalDate dataNascimento) {
		try {
			// Tenta converter para LocalDate no formato yyyy-MM-dd
			dataNascimento.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	// Método para verificar se o aluno é menor de idade (menos de 12 anos)
	private boolean isMenorDeIdade(LocalDate dataNascimento) {
		LocalDate hoje = LocalDate.now();
		Period idade = Period.between(dataNascimento, hoje);
		return idade.getYears() < 12;
	}

	// Método para validar o formato de e-mail
	private boolean isValidEmail(String email) {
		String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
		Pattern pattern = Pattern.compile(emailRegex);
		return pattern.matcher(email).matches();
	}
}
