package br.com.e2etreinamentos.plataforma.aluno.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(HttpMessageNotReadableException.class)
	    public ResponseEntity<String> handleInvalidDateFormat(HttpMessageNotReadableException ex) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body("Erro no JSON: Formato de data inválido. Use o padrão yyyy-MM-dd.");
	    }

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleValidationException(IllegalArgumentException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	// Tratar exceções genéricas de negócio (BusinessException)
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<String> handleBusinessException(BusinessException ex) {
		// Retorna a mensagem de erro com o código de status 400 (Bad Request)
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	// Tratar exceções específicas de AlunoNaoValidoException
	@ExceptionHandler(AlunoNaoValidoException.class)
	public ResponseEntity<String> handleAlunoNaoValidoException(AlunoNaoValidoException ex) {
		// Retorna a mensagem de erro com o código de status 400 (Bad Request)
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	// Tratar exceções inesperadas (não capturadas)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex) {
		// Retorna a mensagem de erro e status 500 (Internal Server Error) em caso de
		// erro inesperado
		return new ResponseEntity<>("Erro interno do servidor: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
