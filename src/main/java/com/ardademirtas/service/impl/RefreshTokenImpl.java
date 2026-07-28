package com.ardademirtas.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ardademirtas.dto.AuthResponse;
import com.ardademirtas.dto.RefreshTokenRequest;
import com.ardademirtas.entity.RefreshToken;
import com.ardademirtas.entity.User;
import com.ardademirtas.exception.BaseException;
import com.ardademirtas.exception.ErrorMessage;
import com.ardademirtas.exception.MessageType;
import com.ardademirtas.jwt.JwtService;
import com.ardademirtas.repository.RefreshTokenRepository;
import com.ardademirtas.service.IRefreshTokenService;

@Service
public class RefreshTokenImpl implements IRefreshTokenService {

   @Autowired	 
   private RefreshTokenRepository  refreshTokenRepository;
   
   @Autowired
   private JwtService jwtService;
   
   @Autowired
   private  AuthenticationServiceImpl authenticationService;


   
    public Boolean refreshTokenIsValıd(Date date) {
    	
    	
     return	new Date().before(date);
    	
    }
   
     
   @Override
   public AuthResponse refreshToken(RefreshTokenRequest request) {
         
	   
       Optional<RefreshToken> optional = refreshTokenRepository.getRefreshToken(request.getRefreshToken());
	   
          if (optional.isEmpty()) {
			
        	  throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_INVALID,request.getRefreshToken())); 
		}	   
 
          
          if (! refreshTokenIsValıd(optional.get().getExpiredDate())) {
			
        	  throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_EXPIRED, request.getRefreshToken()));
		}
          
                    User user= optional.get().getUser();
          
             String accesToken = jwtService.generateToken(user);     
                                   
            RefreshToken refreshToken = authenticationService.createRefreshToken(user);
            
            RefreshToken save = refreshTokenRepository.save(refreshToken);  
                          
                                     
             return new AuthResponse(accesToken, save.getRefreshToken());         
       
   }
	
	


}
