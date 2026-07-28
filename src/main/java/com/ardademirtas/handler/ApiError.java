package com.ardademirtas.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError <T> {
	

	private Integer status;
	
	
	private Exception<T> exception;
	


}
