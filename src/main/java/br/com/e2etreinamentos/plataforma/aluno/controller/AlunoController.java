package br.com.e2etreinamentos.plataforma.aluno.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.e2etreinamentos.plataforma.aluno.model.Aluno;
import br.com.e2etreinamentos.plataforma.aluno.service.AlunoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarAluno(@Valid @RequestBody Aluno aluno) {
       
    	
    	
    	try {
            alunoService.cadastrarAluno(aluno);
            return ResponseEntity.status(HttpStatus.CREATED).body("Aluno cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
