package com.ardademirtas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ardademirtas.entity.User;

@Repository
public interface AuthenticationRepository extends JpaRepository<User, Long> {

	
	 
	
	
}
