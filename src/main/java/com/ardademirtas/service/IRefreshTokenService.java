package com.ardademirtas.service;

import com.ardademirtas.dto.AuthResponse;
import com.ardademirtas.dto.RefreshTokenRequest;

public interface IRefreshTokenService {

	
	
	 public AuthResponse refreshToken(RefreshTokenRequest request);
	
}
