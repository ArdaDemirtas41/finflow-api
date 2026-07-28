package com.ardademirtas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ardademirtas.entity.Account;
import com.ardademirtas.entity.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>  {
	
	
	Optional<Card> findByAccount(Account account);

}
