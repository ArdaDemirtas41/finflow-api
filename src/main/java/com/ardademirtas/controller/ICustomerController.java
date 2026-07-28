package com.ardademirtas.controller;

import com.ardademirtas.dto.DtoCustomer;
import com.ardademirtas.dto.DtoCustomerIU;
import com.ardademirtas.entity.RootEntity;

public interface ICustomerController {
	
	
	
	public RootEntity<DtoCustomer>   saveCustomer(DtoCustomerIU dtoCustomerIU);
	
	

}
