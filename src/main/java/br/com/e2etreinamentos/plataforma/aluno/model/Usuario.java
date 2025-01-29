package br.com.e2etreinamentos.plataforma.aluno.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Nome completo é obrigatório.")
	@Size(max = 100, message = "Nome completo não pode exceder 100 caracteres.")
	private String nomeCompleto;

	@Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos numéricos.")
	@Column(unique = true, length = 11)
	private String cpf;

	@Email(message = "E-mail deve ser válido.")
	@Pattern(regexp = ".*@e2etreinamentos\\.com\\.br", message = "E-mail deve ser do domínio e2etreinamentos.com.br.")
	@Column(unique = true)
	private String email;

	@NotBlank(message = "Senha é obrigatória.")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$", message = "A senha deve ter entre 8 e 15 caracteres, incluindo letras maiúsculas, minúsculas, números e caracteres especiais.")
	private String senha;
}