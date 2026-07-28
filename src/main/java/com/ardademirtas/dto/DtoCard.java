package com.ardademirtas.dto;

import java.util.Date;

import com.ardademirtas.enums.CardType;
import com.ardademirtas.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoCard {

	
	
	private String cardNumber;     
	
	private String  cvv;
	
	private Date expiredDate;
	
	private CardType  cardType;
	
	private Status status;
	
	
	
}
