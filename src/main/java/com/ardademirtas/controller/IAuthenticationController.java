package com.ardademirtas.controller;

import com.ardademirtas.dto.AuthRequest;
import com.ardademirtas.dto.AuthResponse;
import com.ardademirtas.dto.DtoUser;
import com.ardademirtas.dto.RefreshTokenRequest;
import com.ardademirtas.entity.RootEntity;

public interface IAuthenticationController {

	  public  RootEntity<DtoUser> register(AuthRequest request);
	  
	  public RootEntity<AuthResponse> authentication(AuthRequest request);
	  
	  public RootEntity<AuthResponse>  refreshtoken(RefreshTokenRequest refreshTokenRequest);
	
}
