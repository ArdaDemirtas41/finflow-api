package com.ardademirtas.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ardademirtas.Utils.CheckAuthorization;
import com.ardademirtas.dto.DtoCustomer;
import com.ardademirtas.dto.DtoCustomerIU;
import com.ardademirtas.entity.Customer;
import com.ardademirtas.entity.User;
import com.ardademirtas.exception.BaseException;
import com.ardademirtas.exception.ErrorMessage;
import com.ardademirtas.exception.MessageType;
import com.ardademirtas.repository.CustomerRepository;
import com.ardademirtas.repository.JwtRepository;
import com.ardademirtas.service.ICustomerService;

@Service
public class CustomerServiceImpl extends CheckAuthorization implements ICustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private JwtRepository jwtRepository;
	

	@Override
	public DtoCustomer saveCustomer(DtoCustomerIU dtoCustomerIU) {
	
		 
		 String name=SecurityContextHolder.getContext().getAuthentication().getName();
		
	      User optional = jwtRepository.getUserName(name).orElseThrow(()->new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTİON, "user not found")));
	       
	    
	       Customer customer= new Customer();
	       
	       BeanUtils.copyProperties(dtoCustomerIU, customer);
	       customer.setUser(optional);
	       
	       customer.setCreatedTime(new Date());
	       
	       Customer save = customerRepository.save(customer);
		 
		   DtoCustomer dtoCustomer= new DtoCustomer();
		  
		   BeanUtils.copyProperties(save, dtoCustomer);
		  
		
		return dtoCustomer;
	}
	
	
	        

}
