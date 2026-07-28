package com.ardademirtas.service;

import com.ardademirtas.dto.AuthRequest;
import com.ardademirtas.dto.AuthResponse;
import com.ardademirtas.dto.DtoUser;

public interface IAuthenticationService {

	
	public DtoUser register(AuthRequest request); 
	
	public AuthResponse  authenticate(AuthRequest request);
	
	
}
