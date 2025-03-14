package com.example.project.config;

import com.example.project.JWT.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                /**
                 * requestMatchers() : 해당 경로에 대해 접근
                 * permitAll() : 누구나 접근 가능
                 */
                .authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()) // 그 외 모든 요청은 인증 필요
                /**
                 * jwt를 사용하면 반드시 SessionCreationPolicy.STATELESS를 사용해야 함
                 * SessionCreationPolicy.STATELESS : 세션을 사용하지 않음
                 */
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                /**
                 * BasicAuthenticationFilter : security에서 기본적으로 제공하는 basic 인증을 처리하는 기본인증 or 세션 기반 인증 필터
                 * jwt는 세션을 사용하지 않기 때문에 BasicAuthenticationFilter 보다 먼저 실행 되어야함
                 * ※ BasicAuthenticationFilter가 먼저 실행되면 인증 정보가 없어 인증되지 않은 요청으로 처리 됨
                 */
                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
                .build();
    }
}
