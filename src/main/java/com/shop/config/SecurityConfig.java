package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // HTTP 요청에 대한 권한 설정
        return http.authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer
                        // "/css/**", "/js/**", "/img/**" 경로는 모든 사용자에게 허용 (정적 리소스 허용)
                        .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                        // "/", "/members/**", "/item/**", "/images/**" 경로도 모든 사용자에게 허용 (공개 페이지)
                        .requestMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                        // "/admin/**" 경로는 관리자에게만 허용
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // 위에서 설정한 경로 외 모든 요청은 인증된 사용자만 접근 가능
                        .anyRequest().authenticated()
                )
                // 로그인 설정
                .formLogin(formLoginCustomizer -> formLoginCustomizer
                        // 로그인 페이지 URL 설정
                        .loginPage("/members/login")
                        // 로그인 성공 시 이동할 기본 경로 설정
                        .defaultSuccessUrl("/")
                        // 로그인 시 사용되는 사용자 ID 필드를 "email"로 설정
                        .usernameParameter("email")
                        .failureUrl("/members/login/error")
                )
                // 로그아웃 설정
                .logout(logoutCustomizer -> logoutCustomizer
                        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                        // 로그아웃 성공 시 이동할 경로 설정
                        .logoutSuccessUrl("/")
                )
                // 최종 SecurityFilterChain 객체 빌드
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}