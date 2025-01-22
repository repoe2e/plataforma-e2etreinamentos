package br.com.e2etreinamentos.plataforma.aluno.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.e2etreinamentos.plataforma.aluno.model.Aluno;
import br.com.e2etreinamentos.plataforma.aluno.model.InformacoesContato;
import br.com.e2etreinamentos.plataforma.aluno.repository.AlunoRepository;

public class AlunoServiceTest {

	@Mock
	private AlunoRepository alunoRepository;

	@InjectMocks
	private AlunoService alunoService;

	private Aluno aluno;
	private InformacoesContato informacoesContato;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		InformacoesContato contato = new InformacoesContato();
		contato.setTelefone("(11) 1234-5678");
		contato.setWhatsapp("(11) 91234-5678");
		contato.setEndereco("Alameda um");
		contato.setNumero("123");
		contato.setBairro("Centro");
		contato.setEstado("SP");
		contato.setCidade("São Paulo");
		contato.setCep("12345678");

		aluno = new Aluno();
		aluno.setNomeCompleto("João Silva");
		aluno.setCpf("69863004090");
		aluno.setRg("254125484");
		aluno.setEmail("joao@email.com");
		aluno.setDataNascimento(LocalDate.of(1990, 5, 15));
		aluno.setNacionalidade("Brasileiro");
		aluno.setProfissao("Engenheiro");
		aluno.setFormacaoAcademica("Graduação");
		aluno.setInformacoesContato(contato);
	}

	@Test
    public void testCadastrarAlunoComSucesso() {
        when(alunoRepository.save(any(Aluno.class))).thenReturn(aluno);

        Aluno alunoSalvo = alunoService.cadastrarAluno(aluno);

        assertNotNull(alunoSalvo);
        assertEquals("João Silva", alunoSalvo.getNomeCompleto());
        verify(alunoRepository, times(1)).save(aluno);
    }

	@Test
	public void testCadastrarAlunoNomeCompletoObrigatorio() {
		aluno.setNomeCompleto(null);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			alunoService.cadastrarAluno(aluno);
		});

		assertEquals("Nome completo é obrigatório.", exception.getMessage());
	}

	@Test
	public void testCadastrarAlunoCpfInvalido() {
		aluno.setCpf("123");

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			alunoService.cadastrarAluno(aluno);
		});

		assertEquals("CPF inválido. Deve conter exatamente 11 dígitos numéricos.", exception.getMessage());
	}

	@Test
    public void testCadastrarAlunoCpfJaCadastrado() {
        when(alunoRepository.existsByCpf("69863004090")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            alunoService.cadastrarAluno(aluno);
        });

        assertEquals("CPF já cadastrado.", exception.getMessage());
    }

	@Test
	public void testCadastrarAlunoEmailInvalido() {
		aluno.setEmail("email_invalido");

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			alunoService.cadastrarAluno(aluno);
		});

		assertEquals("E-mail inválido.", exception.getMessage());
	}

	@Test
	public void testCadastrarAlunoMenorDeIdade() {
		aluno.setDataNascimento(LocalDate.now().minusYears(10));

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			alunoService.cadastrarAluno(aluno);
		});

		assertEquals("Não é possível cadastrar menores de 12 anos.", exception.getMessage());
	}

	@Test
	public void testCadastrarAlunoComCepInvalido() {
		aluno.getInformacoesContato().setCep("123");

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			alunoService.cadastrarAluno(aluno);
		});

		assertEquals("CEP inválido. Deve conter exatamente 8 dígitos numéricos.", exception.getMessage());
	}

	@Test
	public void testCadastrarAlunoInformacoesContatoObrigatorias() {
		// Definir aluno com InformacoesContato nulo
		aluno.setInformacoesContato(null);

		// Tentar cadastrar o aluno e verificar se a exceção é lançada
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			alunoService.cadastrarAluno(aluno);
		});

		assertEquals("Informações de contato são obrigatórias.", exception.getMessage());
	}

	@Test
	public void testCadastrarAlunoCpfComMaisDe11Caracteres() {
		aluno.setCpf("123456789000");

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			alunoService.cadastrarAluno(aluno);
		});

		assertEquals("CPF inválido. Deve conter exatamente 11 dígitos numéricos.", exception.getMessage());
	}

	@Test
    public void testCadastrarAlunoEmailJaCadastrado() {
        when(alunoRepository.existsByEmail("joao@email.com")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            alunoService.cadastrarAluno(aluno);
        });

        assertEquals("E-mail já cadastrado.", exception.getMessage());
    }

	@Test
	public void testCadastrarAlunoRgObrigatorio() {
		aluno.setRg(null);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			alunoService.cadastrarAluno(aluno);
		});

		assertEquals("RG é obrigatório.", exception.getMessage());
	}

	@Test
	public void testCadastrarAlunoComTelefoneInvalido() {
		aluno.getInformacoesContato().setTelefone(" ");

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			alunoService.cadastrarAluno(aluno);
		});

		assertEquals("Telefone é obrigatório, formato (xx) xxxx-xxxx.", exception.getMessage());
	}

	@Test
	public void testCadastrarAlunoComWhatsappInvalido() {
		aluno.getInformacoesContato().setWhatsapp(" ");

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			alunoService.cadastrarAluno(aluno);
		});

		assertEquals("WhatsApp é inválido, formato (xx) xxxxx-xxxx.", exception.getMessage());
	}

	@Test
	public void testCadastrarAlunoProfissaoObrigatoria() {
		aluno.setProfissao(null);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			alunoService.cadastrarAluno(aluno);
		});

		assertEquals("Profissão obrigatória.", exception.getMessage());
	}

	@Test
	public void testCadastrarAlunoFormacaoAcademicaObrigatoria() {
		aluno.setFormacaoAcademica(null);
		aluno.setRg("12.345.678-9"); // RG válido no formato esperado

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			alunoService.cadastrarAluno(aluno);
		});

		assertEquals("Formação acadêmica é obrigatória.", exception.getMessage());
	}

	@Test
    public void testCadastrarAlunoFalhaNaVerificacaoEmail() {
        when(alunoRepository.existsByEmail("joao@email.com")).thenThrow(new RuntimeException("Banco de dados indisponível"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            alunoService.cadastrarAluno(aluno);
        });

        assertEquals("Banco de dados indisponível", exception.getMessage());
    }

	@Test
	public void testCadastrarAlunoNacionalidadeObrigatoria() {
		aluno.setNacionalidade(null);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			alunoService.cadastrarAluno(aluno);
		});

		assertEquals("Nacionalidade é obrigatória.", exception.getMessage());
	}

}
