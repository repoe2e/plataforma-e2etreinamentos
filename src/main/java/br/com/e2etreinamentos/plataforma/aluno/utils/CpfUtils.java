package br.com.e2etreinamentos.plataforma.aluno.utils;

public class CpfUtils {


	 public static boolean isCpfValid(String cpf) {
	        if (cpf == null || !cpf.matches("\\d{11}")) {
	            return false;
	        }
	        int[] weights = {10, 9, 8, 7, 6, 5, 4, 3, 2};
	        int sum = 0;

	        // Primeiro dígito verificador
	        for (int i = 0; i < 9; i++) {
	            sum += (cpf.charAt(i) - '0') * weights[i];
	        }
	        int firstDigit = 11 - (sum % 11);
	        if (firstDigit >= 10) firstDigit = 0;

	        // Segundo dígito verificador
	        sum = 0;
	        int[] weightsSecond = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
	        for (int i = 0; i < 10; i++) {
	            sum += (cpf.charAt(i) - '0') * weightsSecond[i];
	        }
	        int secondDigit = 11 - (sum % 11);
	        if (secondDigit >= 10) secondDigit = 0;

	        return cpf.charAt(9) - '0' == firstDigit && cpf.charAt(10) - '0' == secondDigit;
	    }
	}