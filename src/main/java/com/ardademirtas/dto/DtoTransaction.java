package com.ardademirtas.dto;

import java.math.BigDecimal;

import com.ardademirtas.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoTransaction {
	
	
	
	    private TransactionType transactionType;
	
	
	    private BigDecimal amount; 
	    
	    
        private String description;
		
		private BigDecimal exchangeRate;   
		
		
		
		private BigDecimal convertedAmount;
		
	 

}
