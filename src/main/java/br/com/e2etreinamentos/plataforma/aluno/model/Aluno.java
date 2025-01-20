package br.com.e2etreinamentos.plataforma.aluno.model;



import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "aluno")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome completo é obrigatório.")
    @Size(max = 100, message = "Nome completo não pode exceder 100 caracteres.")
    private String nomeCompleto;

    @Size(max = 100, message = "Nome social não pode exceder 100 caracteres.")
    private String nomeSocial; // Nome social pode ser em branco

    @Size(max = 50, message = "Apelido não pode exceder 50 caracteres.")
    private String apelido;

    @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos numéricos.")
    @Column(unique = true, length = 11)
    private String cpf;

    @Size(max = 20, message = "RG não pode exceder 20 caracteres.")
    private String rg;

    @Size(max = 50, message = "Nacionalidade não pode exceder 50 caracteres.")
    private String nacionalidade;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Data de nascimento é obrigatória.")
    @Past(message = "Data de nascimento deve ser uma data no passado.")
    private LocalDate dataNascimento;

    @Email(message = "E-mail deve ser válido.")
    @Size(max = 100, message = "E-mail não pode exceder 100 caracteres.")
    @Column(unique = true)
    private String email;

    @Size(max = 50, message = "Profissão não pode exceder 50 caracteres.")
    private String profissao;

    @Size(max = 100, message = "Formação acadêmica não pode exceder 100 caracteres.")
    private String formacaoAcademica;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "informacoes_contato_id", referencedColumnName = "id")
    private InformacoesContato informacoesContato;

    // Construtor obrigatório para criação de um aluno
    public Aluno(String nomeCompleto, String cpf, String rg, String nacionalidade, LocalDate dataNascimento,
                 String email, String profissao, String formacaoAcademica, InformacoesContato informacoesContato) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.rg = rg;
        this.nacionalidade = nacionalidade;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.profissao = profissao;
        this.formacaoAcademica = formacaoAcademica;
        this.informacoesContato = informacoesContato;
    }

    // Construtor vazio (se necessário)
    public Aluno() {
    }
}
