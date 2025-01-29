package br.com.e2etreinamentos.plataforma.aluno.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
