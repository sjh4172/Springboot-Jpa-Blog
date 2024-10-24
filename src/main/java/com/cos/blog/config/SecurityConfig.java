package com.cos.blog.config;

import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration	// 빈 등록
//@RequiredArgsConstructor
@EnableWebSecurity	// Security 필터 추가 = Spring Security 가 활성화가 되어 있는데 어떤 설정을 해당 파일에서 하겠다.
//@EnableMethodSecurity(prePostEnabled = true)	// 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다.
public class SecurityConfig{
	
	@Bean
	public BCryptPasswordEncoder encoderPWD() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean	
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.
			csrf(csrf->csrf.disable()).	// csrf 토큰 비활성화 (테스트 할때는 걸어 두는 것이 좋음)
			formLogin(formLogin->formLogin.loginPage("/auth/loginForm").permitAll()).	// 로그인 페이지 설정
			authorizeHttpRequests(authz -> authz.	
					requestMatchers("/auth/loginForm").permitAll().	
					requestMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/**").permitAll().	// /auth 로 시작하는 요청에 대해 모두 허용
					anyRequest().authenticated()); 	// 나머지는 인증된 사용자만 허용
			
		
		
		return http.build();
	}
}	
