package com.ardademirtas.service.impl;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ardademirtas.dto.AuthRequest;
import com.ardademirtas.dto.AuthResponse;
import com.ardademirtas.dto.DtoUser;
import com.ardademirtas.entity.RefreshToken;
import com.ardademirtas.entity.User;
import com.ardademirtas.exception.BaseException;
import com.ardademirtas.exception.ErrorMessage;
import com.ardademirtas.exception.MessageType;
import com.ardademirtas.jwt.JwtService;
import com.ardademirtas.repository.AuthenticationRepository;
import com.ardademirtas.repository.JwtRepository;
import com.ardademirtas.repository.RefreshTokenRepository;
import com.ardademirtas.service.IAuthenticationService;

@Service
public class AuthenticationServiceImpl  implements IAuthenticationService{

	

	@Autowired
	private AuthenticationRepository   authenticationRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private JwtRepository jwtRepository;
	
	@Value("${jwt.refresh.token.expired}")
	private Long  jwtRefreshTokenExp;
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;



	AuthenticationServiceImpl(JwtRepository jwtRepository) {
		this.jwtRepository = jwtRepository;
	}
	

	
	@Override
	public DtoUser register(AuthRequest request) {
	

		User  user = new User();
	    user.setUsername(request.getUsername());
	    user.setPassword(passwordEncoder.encode(request.getPassword()));
	    user.setCreatedTime(new Date());
		
	    User save = authenticationRepository.save(user);
	    
	    
	   DtoUser dtoUser= new DtoUser();
	    
	    BeanUtils.copyProperties(save, dtoUser);
	    
	    
		return dtoUser;
	}


	@Override
	public AuthResponse authenticate(AuthRequest request) {
	
		 try {
			
	     Optional<User> optional = jwtRepository.getUserName(request.getUsername());          
			 
		 if(optional.isEmpty() || optional==null) {
			 
	     throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, optional.get().toString()));       
			 
		 } 
			 
			 UsernamePasswordAuthenticationToken authenticaiton=
			new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());
			 
			 authenticationProvider.authenticate(authenticaiton);
			 
			  
		   String accesToken = jwtService.generateToken(optional.get());
		              
	       RefreshToken refreshToken = createRefreshToken(optional.get());
	       
	        RefreshToken save = refreshTokenRepository.save(refreshToken);
		  	 		 
		   return new  AuthResponse(accesToken, save.getRefreshToken());
			 
			 
		} catch (Exception e) {
		        
			 throw  new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID, e.getMessage()));
		}
		
	}
	
	 public  RefreshToken  createRefreshToken(User user) {
		 
		 
		RefreshToken refreshToken= new RefreshToken();
		refreshToken.setRefreshToken(UUID.randomUUID().toString());
		refreshToken.setExpiredDate(new Date(System.currentTimeMillis()+jwtRefreshTokenExp));
		refreshToken.setUser(user);
		 
      
		  return refreshToken;
		 
	 }

}
