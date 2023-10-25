package com.bestrongkids.authorizationserver.config;

import com.bestrongkids.authorizationserver.authentication.UserPasswordAuthenticationProvider;
import com.bestrongkids.authorizationserver.federation.FederatedIdentityAuthenticationSuccessHandler;

import com.bestrongkids.authorizationserver.model.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;


@EnableWebSecurity
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class DefaultSecurityConfig {
	private final CustomUserDetailService customUserDetailService;
	private final UserPasswordAuthenticationProvider userPasswordAuthenticationProvider;
	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
//			.addFilterAfter(new CsrfTokenLoggerFilter(), CsrfFilter.class)
				// TODO : CSRF 에러 처리할 것
				.csrf(AbstractHttpConfigurer::disable)
				.authenticationProvider(userPasswordAuthenticationProvider)
				.authorizeHttpRequests(authorize ->
					authorize
							.requestMatchers(antMatcher(HttpMethod.POST, "/user")).permitAll()
							.requestMatchers("/assets/**", "/webjars/**", "/login").permitAll()
							.anyRequest().authenticated()
				)
				.formLogin(formLogin ->
					formLogin
						.loginPage("/login")
				)
				.userDetailsService(customUserDetailService)
		;

		return http.build();
	}


	private AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new FederatedIdentityAuthenticationSuccessHandler();
	}


	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

}
