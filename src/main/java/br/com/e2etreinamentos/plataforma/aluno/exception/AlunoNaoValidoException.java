package br.com.e2etreinamentos.plataforma.aluno.exception;

public class AlunoNaoValidoException extends RuntimeException {

	// Construtor que recebe uma mensagem de erro
	public AlunoNaoValidoException(String message) {
		super(message);
	}

	// Construtor que recebe uma mensagem de erro e uma causa
	public AlunoNaoValidoException(String message, Throwable cause) {
		super(message, cause);
	}

}
