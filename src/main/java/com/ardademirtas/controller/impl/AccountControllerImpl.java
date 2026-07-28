package com.ardademirtas.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ardademirtas.controller.IAccountController;
import com.ardademirtas.controller.RestBaseController;
import com.ardademirtas.dto.DtoAccount;
import com.ardademirtas.dto.DtoAccountIU;
import com.ardademirtas.entity.RootEntity;
import com.ardademirtas.service.IAccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/rest/api/account")
public class AccountControllerImpl extends RestBaseController implements IAccountController {

	@Autowired
	private IAccountService accountService;

	@PostMapping(path = "/save")
	@Override
	public RootEntity<DtoAccount> save(@RequestBody @Valid DtoAccountIU dtoAccountIU) {

		return ok(accountService.save(dtoAccountIU));
	}

	@GetMapping(path = "/get/{id}")
	@Override
	public RootEntity<DtoAccount> getAccount(@PathVariable(value = "id") Long id) {

		return ok(accountService.getAccount(id));
	}

}
