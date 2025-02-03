package br.com.e2etreinamentos.plataforma.aluno.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "matricula")
public class Matricula {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore // Isso irá ignorar o campo na serialização e na documentação do Swagger
	private Long id;

	private String cpfAluno;

	private String emailAluno;

	private Long cursoId;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataMatricula;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate previsaoInicioAulas;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate previsaoEncerramentoAulas;

	private String statusMatricula;

	private String descricaoTurma;

	private String emailUsuario;
	
	@PrePersist
	public void prePersist() {
	    this.dataMatricula = LocalDate.now();
	}


}
