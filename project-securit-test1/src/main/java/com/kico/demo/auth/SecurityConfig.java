package com.kico.demo.auth;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kico.demo.service.ExService;

import lombok.RequiredArgsConstructor;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final ExService exService;
	/**
	 * 규칙 설정
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/user_access").hasRole("USER")
				.antMatchers("/signUp").anonymous()
				.and()
				.formLogin().and().cors().and().csrf().disable();		//로그인 창
	}

	/**
	 * 로그인 인증 처리 메소드
	 * @param auth
	 * @throws Exception
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(exService).passwordEncoder(new BCryptPasswordEncoder());
	}
}
