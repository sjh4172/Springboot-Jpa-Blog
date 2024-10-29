package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.cglib.proxy.Dispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.blog.config.auth.PrincipalDetailService;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;

@Configuration	// 빈 등록
//@RequiredArgsConstructor
@EnableWebSecurity	// Security 필터 추가 = Spring Security 가 활성화가 되어 있는데 어떤 설정을 해당 파일에서 하겠다.
@EnableMethodSecurity(prePostEnabled = true)	// 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다.
public class SecurityConfig{
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	// 패스워드 암호화에 사용
	@Bean
	public BCryptPasswordEncoder encoderPWD() {	
		return new BCryptPasswordEncoder();
	}
	
	// Password 가 뭐로 해시화 되었는지 알려주는 역할
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
		AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
	
		auth.userDetailsService(principalDetailService).passwordEncoder(encoderPWD());
		return auth.build();
	}
	
	@Bean	
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.
			csrf(csrf->csrf.disable()).	// csrf 토큰 비활성화 (테스트 할때는 걸어 두는 것이 좋음)
			formLogin(formLogin->formLogin.
					loginPage("/auth/loginForm").permitAll(). // 로그인 폼 페이지 설정
					loginProcessingUrl("/auth/loginProc").defaultSuccessUrl("/")).	// 스프링 시큐리티가 해당주소로 요청오는 로그인을 가로채서 대신 로그인
			authorizeHttpRequests(authz -> authz.	
					dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll().	//FORWARD 요청에 대해 추가적인 인증 없이 접근 가능
					requestMatchers("/auth/loginForm").permitAll().	
					requestMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**").permitAll().	// /auth 로 시작하는 요청에 대해 모두 허용
					anyRequest().authenticated()); 	// 나머지는 인증된 사용자만 허용
		
		return http.build();
	}
}	
