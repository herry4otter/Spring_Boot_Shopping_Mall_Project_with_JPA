package com.shop.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/members/**", "/item/**", "/images/**").permitAll() // 공개 경로
                        .requestMatchers("/admin/**").hasRole("ADMIN")                            // ADMIN 권한 필요
                        .anyRequest().authenticated()                                            // 나머지 요청은 인증 필요
                )
                .formLogin(form -> form
                        .loginPage("/members/login")      // 로그인 페이지 설정
                        .defaultSuccessUrl("/")           // 로그인 성공 시 이동할 URL
                        .usernameParameter("email")       // 사용자명 파라미터 설정
                        .failureUrl("/members/login/error") // 로그인 실패 시 이동할 URL
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) // 로그아웃 경로 설정
                        .logoutSuccessUrl("/")           // 로그아웃 성공 시 이동할 URL
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 인증되지 않은 사용자 처리
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/css/**", "/js/**", "/img/**"); // 정적 리소스 무시
    }
}
