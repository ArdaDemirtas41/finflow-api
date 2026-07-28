package com.ardademirtas.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoCustomer {
	

	
	private String firstname;
	

	private String lastname;
	
	
	
	private String phone;
	

	private Date  createdTime;
	
	

}
