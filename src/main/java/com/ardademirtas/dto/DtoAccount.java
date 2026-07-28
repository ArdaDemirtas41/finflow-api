package com.ardademirtas.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.ardademirtas.enums.AccountType;
import com.ardademirtas.enums.CurrencyType;
import com.ardademirtas.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoAccount {
	
	
	
	private String accountNumber;
	
	
	private BigDecimal balance;
	
	
	private CurrencyType currencyType;
	
	
	private AccountType accountType;
	
	private  Date createdTime;
	
	
	private Status status;
	
	
	private DtoCustomer dtoCustomer;
	

}
