package com.estaciona_ai.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. Autenticação (Pública)
                        .requestMatchers("/auth/**").permitAll()

                        // 2. Cadastro de Usuário (Público)
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()

                        // 3. Webhook do Mercado Pago (Público)
                        .requestMatchers(HttpMethod.POST, "/payments/webhook").permitAll()

                        // 4. Consulta Pública de Garagens no Mapa
                        .requestMatchers(HttpMethod.GET, "/garages", "/garages/{id}", "/garages/nearby").permitAll()
                        .requestMatchers(HttpMethod.GET, "/garages/{garageId}/reviews", "/garages/{garageId}/rating").permitAll()

                        // 5. Console H2 (Para Dev)
                        .requestMatchers("/h2-console/**").permitAll()

                        // 6. Todas as demais rotas (/bookings, /vehicles, /chat, /payments, /notifications, /storage, /drivers) exigem JWT
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}