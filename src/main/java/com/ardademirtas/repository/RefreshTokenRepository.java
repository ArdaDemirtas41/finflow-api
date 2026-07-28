package com.ardademirtas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ardademirtas.entity.RefreshToken;

@Repository
public interface RefreshTokenRepository  extends JpaRepository<RefreshToken,Long>{

	 @Query("from RefreshToken r where r.refreshToken = :refreshToken")
	 public Optional<RefreshToken> getRefreshToken(String refreshToken); 
	
}
