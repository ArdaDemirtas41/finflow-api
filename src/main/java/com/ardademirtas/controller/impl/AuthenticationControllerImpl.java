package com.ardademirtas.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ardademirtas.controller.IAuthenticationController;
import com.ardademirtas.controller.RestBaseController;
import com.ardademirtas.dto.AuthRequest;
import com.ardademirtas.dto.AuthResponse;
import com.ardademirtas.dto.DtoUser;
import com.ardademirtas.dto.RefreshTokenRequest;
import com.ardademirtas.entity.RootEntity;
import com.ardademirtas.service.IAuthenticationService;
import com.ardademirtas.service.IRefreshTokenService;

import jakarta.validation.Valid;

@RestController
public class AuthenticationControllerImpl extends RestBaseController  implements IAuthenticationController{

	

	@Autowired
	private IAuthenticationService authenticationService;
	
	@Autowired
	private IRefreshTokenService refreshTokenService;
	
	
	@PostMapping(path = "/register")
	@Override
	public RootEntity<DtoUser> register(@RequestBody @Valid AuthRequest request) {
	
	
      return  ok(authenticationService.register(request));
		
	}

    
	@PostMapping(path = "/authenticate")
	@Override
	public RootEntity<AuthResponse> authentication(@RequestBody @Valid  AuthRequest request) {
	
		return  ok( authenticationService.authenticate(request));  
	}

	
	
    @PostMapping(path = "refreshtoken")
	@Override
	public RootEntity<AuthResponse> refreshtoken(@RequestBody  RefreshTokenRequest refreshTokenRequest) {
		
		return   ok(refreshTokenService.refreshToken(refreshTokenRequest));
	}

}
