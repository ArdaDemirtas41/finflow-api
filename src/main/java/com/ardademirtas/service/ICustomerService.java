package com.ardademirtas.service;

import com.ardademirtas.dto.DtoCustomer;
import com.ardademirtas.dto.DtoCustomerIU;

public interface ICustomerService {
	
	public DtoCustomer   saveCustomer(DtoCustomerIU dtoCustomerIU);
	
   

}
