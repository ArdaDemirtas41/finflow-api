package com.ardademirtas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ardademirtas.entity.Transaction;

@Repository
public interface TransactionRepository  extends JpaRepository<Transaction, Long>{
	
	@Query("SELECT t FROM Transaction t WHERE t.sender_account_id.id = :senderAccountId OR t.receiver_account_id.id = :receiverAccountId")
	List<Transaction> findAccountTransactions(
	        @Param("senderAccountId") Long senderAccountId, 
	        @Param("receiverAccountId") Long receiverAccountId);
}
