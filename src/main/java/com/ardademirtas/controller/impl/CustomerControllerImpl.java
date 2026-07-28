package com.ardademirtas.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ardademirtas.controller.ICustomerController;
import com.ardademirtas.controller.RestBaseController;
import com.ardademirtas.dto.DtoCustomer;
import com.ardademirtas.dto.DtoCustomerIU;
import com.ardademirtas.entity.RootEntity;
import com.ardademirtas.service.ICustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/rest/api/customer")
public class CustomerControllerImpl extends RestBaseController implements ICustomerController {

	
	@Autowired
	private ICustomerService customerService;
	
	
	@Override
	@PostMapping(path = "/save")
	public RootEntity<DtoCustomer> saveCustomer(@RequestBody @Valid  DtoCustomerIU dtoCustomerIU) {
	
		return ok(customerService.saveCustomer(dtoCustomerIU));
	}

}
