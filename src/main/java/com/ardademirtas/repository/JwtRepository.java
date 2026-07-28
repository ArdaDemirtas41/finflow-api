package com.ardademirtas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ardademirtas.entity.User;

@Repository
public interface JwtRepository extends  JpaRepository<User, Long> {

     
	 @Query(value = "from User  WHERE username = :username ")
	 public Optional<User>   getUserName(String username);
	
	 
}
