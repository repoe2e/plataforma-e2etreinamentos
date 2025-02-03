package br.com.e2etreinamentos.plataforma.aluno.model;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SessaoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore  // Isso irá ignorar o campo na serialização e na documentação do Swagger
    private Long id;

    private String email;
    private String token;

    public SessaoUsuario() {}

    public SessaoUsuario(String email, String token) {
        this.email = email;
        this.token = token;
    }
}
