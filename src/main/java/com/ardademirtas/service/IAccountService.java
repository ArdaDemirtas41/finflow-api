package com.ardademirtas.service;

import com.ardademirtas.dto.DtoAccount;
import com.ardademirtas.dto.DtoAccountIU;

public interface IAccountService {
	
	
	  public DtoAccount save(DtoAccountIU dtoAccountIU);
	  
	  public DtoAccount  getAccount(Long id);

}
