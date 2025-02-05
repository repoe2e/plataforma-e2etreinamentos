package br.com.e2etreinamentos.plataforma.aluno.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.e2etreinamentos.plataforma.aluno.model.Usuario;
import br.com.e2etreinamentos.plataforma.aluno.repository.UsuarioRepository;
import br.com.e2etreinamentos.plataforma.aluno.utils.CpfUtils;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioServiceTest {

	 @InjectMocks
	    private UsuarioService usuarioService;

	    @Mock
	    private UsuarioRepository usuarioRepository;

	    private BCryptPasswordEncoder passwordEncoder;
	    
	    private String cpfCadastrado;
	    
	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	        passwordEncoder = new BCryptPasswordEncoder();
	    }

	    @Test
	    public void testCadastrarUsuario_ComNomeEmBranco() {
	        Usuario usuario = new Usuario();
	        usuario.setNomeCompleto(" ");
	        usuario.setCpf(CpfUtils.generateValidCpf());
	        usuario.setEmail("teste@e2etreinamentos.com.br");
	        usuario.setSenha("Senha123!");

	        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
	            usuarioService.cadastrarUsuario(usuario);
	        });

	        assertEquals("Nome em branco, null ou inválido.", thrown.getMessage());
	    }

	   
	    @Test
	    public void testCadastrarUsuario_ComEmailInvalido() {
	        Usuario usuario = new Usuario();
	        usuario.setNomeCompleto("João Silva");
	        usuario.setCpf(CpfUtils.generateValidCpf());
	        usuario.setEmail("invalidemail.com");
	        usuario.setSenha("Senha123!");

	        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
	            usuarioService.cadastrarUsuario(usuario);
	        });

	        assertEquals("E-mail fora do padrão nome@e2etreinamentos.com.br", thrown.getMessage());
	    }

	    @Test
	    public void testCadastrarUsuario_ComSenhaCurta() {
	        Usuario usuario = new Usuario();
	        usuario.setNomeCompleto("João Silva");
	        usuario.setCpf(CpfUtils.generateValidCpf());
	        usuario.setEmail("teste@e2etreinamentos.com.br");
	        usuario.setSenha("@1aA20");

	        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
	            usuarioService.cadastrarUsuario(usuario);
	        });

	        assertEquals("Senha deve ter entre 10 a 15 digitos.", thrown.getMessage());
	    }

	    @Test
	    public void testCadastrarUsuario_ComSenhaSemFormatoValido() {
	        Usuario usuario = new Usuario();
	        usuario.setNomeCompleto("João Silva");
	        usuario.setCpf(CpfUtils.generateValidCpf());
	        usuario.setEmail("teste@e2etreinamentos.com.br");
	        usuario.setSenha("senha12332");

	        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
	            usuarioService.cadastrarUsuario(usuario);
	        });

	        assertEquals("Formato de senha inválido. A senha deve conter pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial", thrown.getMessage());
	    }

	    @Test
	    public void testCadastrarUsuario_Sucesso() {
	        Usuario usuario = new Usuario();
	        cpfCadastrado = CpfUtils.generateValidCpf();
	        usuario.setNomeCompleto("João Silva");
	        usuario.setCpf(cpfCadastrado);
	      
	        usuario.setEmail("teste@e2etreinamentos.com.br");
	        usuario.setSenha("Senha123!A");

	        when(usuarioRepository.existsByCpf(CpfUtils.generateValidCpf())).thenReturn(false);
	        when(usuarioRepository.existsByEmail("teste@e2etreinamentos.com.br")).thenReturn(false);
	        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

	        Usuario result = usuarioService.cadastrarUsuario(usuario);

	        assertNotNull(result);
	        assertEquals("João Silva", result.getNomeCompleto());
	        verify(usuarioRepository, times(1)).save(usuario);
	    }

	    @Test
	    public void testValidarLogin_ComEmailInvalido() {
	        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
	            usuarioService.validarLogin("invalidemail", "Senha123!");
	        });

	        assertEquals("E-mail fora do padrão nome@e2etreinamentos.com.br.", thrown.getMessage());
	    }

	    @Test
	    public void testValidarLogin_ComEmailNaoExistente() {
	        when(usuarioRepository.findByEmail("teste@e2etreinamentos.com.br"))
	            .thenReturn(java.util.Optional.empty());

	        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
	            usuarioService.validarLogin("teste@e2etreinamentos.com.br", "Senha123!");
	        });

	        assertEquals("O e-mail fornecido não é válido ou inexistente.", thrown.getMessage());
	    }

	    @Test
	    public void testValidarLogin_ComSenhaInvalida() {
	        Usuario usuario = new Usuario();
	        usuario.setEmail("teste@e2etreinamentos.com.br");
	        usuario.setSenha(passwordEncoder.encode("Senha123!"));

	        when(usuarioRepository.findByEmail("teste@e2etreinamentos.com.br"))
	            .thenReturn(java.util.Optional.of(usuario));

	        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
	            usuarioService.validarLogin("teste@e2etreinamentos.com.br", "wrongPassword");
	        });

	        assertEquals("Formato de senha inválido. A senha deve conter pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial.", thrown.getMessage());
	    }
	    
	    
	}