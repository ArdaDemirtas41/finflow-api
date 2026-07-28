package com.ardademirtas.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.ardademirtas.enums.AccountType;
import com.ardademirtas.enums.CurrencyType;
import com.ardademirtas.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name = "account_number")
	@Size(min = 16,max = 16,message = "The account number must be exactly 16 digits long.")
	private String accountNumber;
	
	
	@Column(name = "created_time")
	@DateTimeFormat(iso = ISO.DATE)
	private  Date createdTime;
	
	
	@Column(name = "balance",precision = 19,scale = 8)
	@NotNull
	private BigDecimal balance;
	
	@Column(name ="currency_type")
	@Enumerated(EnumType.STRING)
	private CurrencyType currencyType;
	
	@Column(name = "account_type")
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@ManyToOne
	private Customer customer;
	
}
