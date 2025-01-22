package br.com.e2etreinamentos.plataforma.aluno.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "curso")
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nomeCurso;
	private String descricaoCurso;
	private int horasCurso;
	private String recorrenciaCurso;
	private String diasAula;
	private String modalidade;
	private int maxVagas;
	private String nivelCurso;


	// Construtor sem parâmetros (já existente)
	public Curso() {
	}

	// Construtor com parâmetros
	public Curso(String nomeCurso, String descricaoCurso, int horasCurso, String recorrenciaCurso, String diasAula,
			String modalidade, int maxVagas, String nivelCurso) {
		this.nomeCurso = nomeCurso;
		this.descricaoCurso = descricaoCurso;
		this.horasCurso = horasCurso;
		this.recorrenciaCurso = recorrenciaCurso;
		this.diasAula = diasAula;
		this.modalidade = modalidade;
		this.maxVagas = maxVagas;
		this.nivelCurso = nivelCurso;
		
	}

}
