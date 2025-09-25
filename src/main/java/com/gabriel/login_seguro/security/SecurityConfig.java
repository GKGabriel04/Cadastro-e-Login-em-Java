package com.gabriel.login_seguro.security;

import com.gabriel.login_seguro.infrastructure.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. BEAN DO PASSWORD ENCODER (ESSENCIAL PARA SEU USUARIOSERVICE)
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usa o algoritmo BCrypt, que é o padrão da indústria e muito seguro.
        return new BCryptPasswordEncoder();
    }

    // 2. BEAN DO USERDETAILSSERVICE (DIZ AO SPRING COMO BUSCAR SEUS USUÁRIOS)
    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
        return email -> usuarioRepository.findByEmail(email)
                .map(CustomUserDetails::new) // Adapta sua entidade para o que o Spring Security entende
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
    }

    // 3. BEAN DO SECURITYFILTERCHAIN (AS REGRAS DE ACESSO HTTP)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Libera o acesso a estas URLs para todos (página de login, cadastro e arquivos estáticos)
                        .requestMatchers("/login", "/cadastro", "/css/**", "/js/**").permitAll()
                        // Exige que o usuário tenha a role "ADMIN" para acessar qualquer URL que comece com /admin/
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Exige autenticação para qualquer outra requisição
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        // Define qual URL o usuário será redirecionado após o login bem-sucedido
                        .defaultSuccessUrl("/home", true)
                        // Permite acesso a todos para a URL de processamento de login
                        .permitAll()
                )
                .logout(logout -> logout
                        // Define a URL para qual o usuário será redirecionado após o logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}
