package com.ardademirtas.controller;

import com.ardademirtas.dto.DtoAccount;
import com.ardademirtas.dto.DtoAccountIU;
import com.ardademirtas.entity.RootEntity;

public interface IAccountController {
	
	
	 public RootEntity<DtoAccount> save(DtoAccountIU dtoAccountIU);
	 
	 public RootEntity<DtoAccount>  getAccount(Long id);

}