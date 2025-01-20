package br.com.e2etreinamentos.plataforma.aluno.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class InformacoesContato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Telefone é obrigatório.")
    @Column(name = "telefone")
    private String telefone;

    @NotBlank(message = "Whatsapp é obrigatório.")
    @Column(name = "whatsapp")
    private String whatsapp;

    @NotBlank(message = "Número é obrigatório.")
    @Column(name = "numero")
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @NotBlank(message = "Bairro é obrigatório.")
    @Column(name = "bairro")
    private String bairro;

    @NotBlank(message = "Estado é obrigatório.")
    @Column(name = "estado")
    private String estado;

    @NotBlank(message = "Cidade é obrigatória.")
    @Column(name = "cidade")
    private String cidade;

    @NotBlank(message = "CEP é obrigatório.")
    @Column(name = "cep")
    private String cep;

    // Construtores e getters/setters omitidos por brevidade
}
