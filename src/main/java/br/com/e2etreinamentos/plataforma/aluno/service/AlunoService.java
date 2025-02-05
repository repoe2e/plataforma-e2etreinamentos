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
import br.com.e2etreinamentos.plataforma.aluno.utils.CpfUtils;

@Service
@Transactional
public class AlunoService {

	@Autowired
	private AlunoRepository alunoRepository;

	public Aluno cadastrarAluno(Aluno aluno) {
		// Validação do Nome Completo
		if (aluno.getNomeCompleto() == null || aluno.getNomeCompleto().trim().isEmpty()
				|| !isValidName(aluno.getNomeCompleto())) {
			throw new IllegalArgumentException("Nome completo é obrigatório.");
		}

		if (alunoRepository.existsByCpf(aluno.getCpf())) {
			throw new IllegalArgumentException("CPF já cadastrado.");
		}
		// Validação do CPF
		if (aluno.getCpf() == null || aluno.getCpf().length() != 11 || !aluno.getCpf().matches("\\d{11}")
				|| !CpfUtils.isCpfValid(aluno.getCpf())) {
			throw new IllegalArgumentException("CPF inexistente ou inválido. Deve conter exatamente 11 dígitos numéricos.");
		}
		

		// Validação do E-mail
		if (aluno.getEmail() == null || aluno.getEmail().trim().isEmpty() || !isValidEmail(aluno.getEmail())) {
			throw new IllegalArgumentException("E-mail inválido.");
		}
		if (alunoRepository.existsByEmail(aluno.getEmail())) {
			throw new IllegalArgumentException("E-mail já cadastrado.");
		}

		// Validação do RG
		if (aluno.getRg() == null || aluno.getRg().trim().isEmpty()) {
			throw new IllegalArgumentException("RG é obrigatório.");
		}
		// Validação da formatação do RG

		if (!isValidRg(aluno.getRg())) {
			throw new IllegalArgumentException("RG inválido. O formato esperado é 'XX.XXX.XXX-X'");

		}

		// Validação da Data de Nascimento
		if (aluno.getDataNascimento() == null || !isValidDateFormat(aluno.getDataNascimento())
				|| aluno.getDataNascimento().isAfter(LocalDate.now()) || isMenorDeIdade(aluno.getDataNascimento())) {
			throw new IllegalArgumentException("Não é possível cadastrar menores de 12 anos.");
		}

		
		// Validação da Data de Nascimento
				if (aluno.getDataNascimento() == null || !isValidDateFormat(aluno.getDataNascimento())
						|| aluno.getDataNascimento().isAfter(LocalDate.now()) || isMaiorDeIdade(aluno.getDataNascimento())) {
					throw new IllegalArgumentException("Não é possível cadastrar maiores de 85 anos.");
				}
		
		// Validação da Nacionalidade
		if (aluno.getNacionalidade() == null || aluno.getNacionalidade().trim().isEmpty()
				|| !isValidText(aluno.getNacionalidade())) {
			throw new IllegalArgumentException("Nacionalidade é obrigatória.");
		}

		// Validação da Profissão
		if (aluno.getProfissao() == null || aluno.getProfissao().trim().isEmpty()
				|| !isValidText(aluno.getProfissao())) {
			throw new IllegalArgumentException("Profissão obrigatória.");
		}

		// Validação da Formação Acadêmica
		if (aluno.getFormacaoAcademica() == null || aluno.getFormacaoAcademica().trim().isEmpty()
				|| !isValidText(aluno.getFormacaoAcademica())) {
			throw new IllegalArgumentException("Formação acadêmica é obrigatória.");
		}

		// Validação das Informações de Contato
		if (aluno.getInformacoesContato() == null) {
			throw new IllegalArgumentException("Informações de contato são obrigatórias.");
		}

		// Validação do Telefone
		if (!isValidPhone(aluno.getInformacoesContato().getTelefone())) {
			throw new IllegalArgumentException("Telefone é obrigatório, formato (xx) xxxx-xxxx.");
		}

		// Validação do WhatsApp
		if (!isValidPhone(aluno.getInformacoesContato().getWhatsapp())) {
			throw new IllegalArgumentException("WhatsApp é inválido, formato (xx) xxxxx-xxxx.");
		}

		// Validação do Endereço
		if (aluno.getInformacoesContato().getEndereco() == null
				|| aluno.getInformacoesContato().getEndereco().trim().isEmpty()) {
			throw new IllegalArgumentException("Endereço inválido.");
		}

		// Validação do Número
		if (aluno.getInformacoesContato().getNumero() == null
				|| aluno.getInformacoesContato().getNumero().trim().isEmpty()) {
			throw new IllegalArgumentException("Número é obrigatório.");
		}

		// Validação do Bairro
		if (aluno.getInformacoesContato().getBairro() == null
				|| aluno.getInformacoesContato().getBairro().trim().isEmpty()) {
			throw new IllegalArgumentException("Bairro é obrigatório.");
		}

		// Validação do Estado
		if (!isValidState(aluno.getInformacoesContato().getEstado())) {
			throw new IllegalArgumentException("Estado inválido.");
		}

		// Validação da Cidade
		if (aluno.getInformacoesContato().getCidade() == null
				|| aluno.getInformacoesContato().getCidade().trim().isEmpty()) {
			throw new IllegalArgumentException("Cidade é obrigatória.");
		}

		// Validação do CEP
		if (!isValidCep(aluno.getInformacoesContato().getCep())) {
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

	private boolean isValidName(String name) {
	    return name.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]{2,}(\\s[A-Za-zÀ-ÖØ-öø-ÿ]{2,})+$");
	}

	private boolean isValidText(String text) {
		return text.matches("[A-Za-zÀ-ÖØ-öø-ÿ ]{2,}");
	}

	// Método para verificar se o aluno é menor de idade (menos de 12 anos)
	private boolean isMenorDeIdade(LocalDate dataNascimento) {
		LocalDate hoje = LocalDate.now();
		Period idade = Period.between(dataNascimento, hoje);
		return idade.getYears() <= 12;
	}
	
	// Método para verificar se o aluno é menor de idade (menos de 12 anos)
		private boolean isMaiorDeIdade(LocalDate dataNascimento) {
			LocalDate hoje = LocalDate.now();
			Period idade = Period.between(dataNascimento, hoje);
			return idade.getYears() >= 85;
		}

	// Método para validar o formato de e-mail
	private boolean isValidEmail(String email) {
		String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
		Pattern pattern = Pattern.compile(emailRegex);
		return pattern.matcher(email).matches();
	}

	private boolean isValidState(String state) {
		String[] validStates = { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
				"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" };
		return java.util.Arrays.asList(validStates).contains(state);
	}

	private boolean isValidCep(String cep) {
		return cep.matches("\\d{8}");
	}

	private boolean isValidPhone(String phone) {
		return phone.matches("\\(\\d{2}\\) \\d{4,5}-\\d{4}");
	}

	private boolean isValidRg(String rg) {
		return rg.matches("\\d{2}\\.\\d{3}\\.\\d{3}-\\d{1}|\\d{9}");
	}

	

}
