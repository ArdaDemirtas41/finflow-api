package com.ardademirtas.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ardademirtas.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	private static final String REGISTER="/register";
	private static  final String  AUTHENTİCATE="/authenticate";
	private static  final String  REFRESH_TOKEN="/refreshtoken";
	
	
	public static final String[] SWAGGER_PATH= {
			
			"/swagger-ui/**",
			"/v3/api-docs/**",
			"/swagger-ui.html"
	
	};
	
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) {
		
		
		 http.csrf(csrf -> csrf.disable())
		 .authorizeHttpRequests(request->request.requestMatchers(REGISTER,AUTHENTİCATE,REFRESH_TOKEN)
		 .permitAll()
		 .requestMatchers(SWAGGER_PATH).permitAll()
		 .anyRequest()
		 .authenticated())
		 .exceptionHandling(exception->exception.authenticationEntryPoint(authenticationEntryPoint))
		 .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		 .authenticationProvider(authenticationProvider)
		 .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		
		 return http.build();
		
	}

}
