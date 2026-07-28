package com.ardademirtas.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ardademirtas.Utils.CheckAuthorization;
import com.ardademirtas.dto.DtoAccount;
import com.ardademirtas.dto.DtoAccountIU;
import com.ardademirtas.dto.DtoCustomer;
import com.ardademirtas.entity.Account;
import com.ardademirtas.entity.Customer;
import com.ardademirtas.enums.Status;
import com.ardademirtas.exception.BaseException;
import com.ardademirtas.exception.ErrorMessage;
import com.ardademirtas.exception.MessageType;
import com.ardademirtas.repository.AccountRepository;
import com.ardademirtas.repository.CustomerRepository;
import com.ardademirtas.service.IAccountService;

@Service
public class AccountServiceImpl extends CheckAuthorization  implements IAccountService {

	 
	 @Autowired
	 private AccountRepository accountRepository;
	 
	 @Autowired
	 private CustomerRepository customerRepository;
	
	
	
	@Override
	public DtoAccount save(DtoAccountIU dtoAccountIU) {
		
		 
     Long customer_id = dtoAccountIU.getCustomer_id();
     Customer optional = customerRepository.findById(customer_id).orElseThrow(
     ()-> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, customer_id.toString())));
    		 
     
     CheckAuthorization.checkAuthorization(optional.getUser().getUsername());
     
     
     if (dtoAccountIU.getStatus()!=Status.ACTIVE) {
		
    	 throw new BaseException(new ErrorMessage(MessageType.ACCOUNT_ERROR, dtoAccountIU.getStatus().toString()));
	}
    
    
	 Account account = new Account();
	 BeanUtils.copyProperties(dtoAccountIU, account);
	 account.setCustomer(optional); 
		
	account.setCreatedTime(new Date());
    Account save = accountRepository.save(account);	
    
    DtoAccount dtoAccount= new DtoAccount();
    
    BeanUtils.copyProperties(save, dtoAccount);
    
    DtoCustomer dtoCustomer= new DtoCustomer();
    
    BeanUtils.copyProperties(optional, dtoCustomer);
    
    dtoAccount.setDtoCustomer(dtoCustomer);
		
		return dtoAccount;
	}

	@Override
	public DtoAccount getAccount(Long id) {
		
	
      Account optional = accountRepository.findById(id).orElseThrow(()-> new BaseException(
      new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString())));
     
       
     checkAuthorization(optional.getCustomer().getUser().getUsername());
     
          

      DtoAccount dtoAccount= new DtoAccount();
      
      BeanUtils.copyProperties(optional, dtoAccount);
      
      DtoCustomer dtoCustomer= new DtoCustomer();
      
      BeanUtils.copyProperties(optional.getCustomer(), dtoCustomer);
      
      dtoAccount.setDtoCustomer(dtoCustomer);
		
		return dtoAccount;
	}
	
	
	
	
}
