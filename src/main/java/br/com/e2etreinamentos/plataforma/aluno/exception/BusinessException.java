package br.com.e2etreinamentos.plataforma.aluno.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BusinessException extends RuntimeException {

	 // Construtor que recebe uma mensagem de erro
    public BusinessException(String message) {
        super(message);
    }

    // Construtor que recebe uma mensagem de erro e uma causa
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> 
            errors.append(error.getDefaultMessage()).append("\n"));
        return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException ex) {
        // Retorna o erro de business (como email duplicado) com código 400
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
