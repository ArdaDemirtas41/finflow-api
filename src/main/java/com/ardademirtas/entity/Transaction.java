package com.ardademirtas.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.ardademirtas.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name = "amount",precision = 19,scale = 8)
	private BigDecimal amount;
	
	@Column(name = "transaction_type")
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	
	@Column(name = "description")
	@Size(min = 3,max = 100,message = "It can contain a minimum of 3 and a maximum of 100 words.")
	private String description;
	
	
	@Column(name = "transaction_date")
	@DateTimeFormat(iso = ISO.DATE)
	private  Date  transactionDate;
	
	
	@Column(name = "exchange_rate",precision = 19,scale = 8)
	private BigDecimal exchangeRate;   
	
	@Column(name = "converted_amount",precision = 19,scale = 8)
	private BigDecimal convertedAmount;
	
	
	@ManyToOne
	private Account sender_account_id;
	
	@ManyToOne
	private Account  receiver_account_id;
	
}
