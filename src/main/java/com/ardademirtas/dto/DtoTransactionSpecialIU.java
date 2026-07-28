package com.ardademirtas.dto;

import java.math.BigDecimal;

import com.ardademirtas.enums.TransactionType;

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
public class DtoTransactionSpecialIU {
	
	
	@NotNull
	private TransactionType transactionType;
	
	
	
	@NotNull(message = "it cannot null.")
	private BigDecimal amount;
	
	

	@NotBlank
	@Size(min = 3,max = 100,message = "It can contain a minimum of 3 and a maximum of 100 words.")
	private String description;
	
	
	
	private Long account_id;

}
