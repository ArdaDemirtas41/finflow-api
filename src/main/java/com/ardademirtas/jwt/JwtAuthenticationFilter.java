package com.ardademirtas.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.ardademirtas.exception.BaseException;
import com.ardademirtas.exception.ErrorMessage;
import com.ardademirtas.exception.MessageType;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsService userDetailsService;
	
	
	@Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader("Authorization");

		if (header == null || header.isBlank() || !header.startsWith("Bearer ")) {

			filterChain.doFilter(request, response);
			return;

		}

		String token = header.substring(7);

		try {

			String userNameByToken = jwtService.getUserNameByToken(token);

			if (userNameByToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetails = userDetailsService.loadUserByUsername(userNameByToken);

				if (userDetails != null && jwtService.isTokenValid(token)) {

					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(userDetails);

					SecurityContextHolder.getContext().setAuthentication(authentication);

				}

			}

		} catch (ExpiredJwtException e) {

			resolver.resolveException(request, response, null, new BaseException(new ErrorMessage(MessageType.IS_TOKEN_EXPIRED, e.getMessage()))); 
             
			return;
			 
		} catch (Exception e) {

			 resolver.resolveException(request, response, null, new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTİON,"")));
		    
			  return;
		}
		

		filterChain.doFilter(request, response);
		
	}

}
