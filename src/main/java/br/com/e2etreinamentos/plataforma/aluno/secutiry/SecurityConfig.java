package br.com.e2etreinamentos.plataforma.aluno.secutiry;





import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/error").permitAll() // Permite acesso público ao endpoint /error
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/webjars/**").permitAll() // Permite acesso ao Swagger
                .requestMatchers("/api/**").authenticated() // Exige autenticação para endpoints sob /api
                .anyRequest().denyAll() // Bloqueia todos os outros endpoints
            )
            .csrf(csrf -> csrf.disable()); // Desabilita CSRF para simplificar (não recomendado para produção)

        return http.build();
    }
}