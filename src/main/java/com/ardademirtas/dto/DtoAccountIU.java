package com.ardademirtas.dto;

import java.math.BigDecimal;

import com.ardademirtas.enums.AccountType;
import com.ardademirtas.enums.CurrencyType;
import com.ardademirtas.enums.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoAccountIU {
	
	
	@NotNull(message = "cannot be null")
	@Size(min = 16,max = 16,message = "The account number must be exactly 16 digits long.")
	@NotBlank
	private String accountNumber;
	
	
	@NotNull(message = "cannot be null")
	private BigDecimal balance;
	
	
	@NotNull(message = "cannot be null")
	private CurrencyType currencyType;
	
	
	@NotNull(message = "cannot be null")
	private AccountType accountType;
	
	
	@NotNull(message = "cannot be null")
	private Status status;
	
	
	@NotNull(message = "cannot be null")
	private Long  customer_id;

}
