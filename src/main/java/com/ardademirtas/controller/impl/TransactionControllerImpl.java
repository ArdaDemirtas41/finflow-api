package com.ardademirtas.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ardademirtas.controller.ITransactionController;
import com.ardademirtas.controller.RestBaseController;
import com.ardademirtas.dto.DtoTransaction;
import com.ardademirtas.dto.DtoTransactionIU;
import com.ardademirtas.dto.DtoTransactionSpecialIU;
import com.ardademirtas.entity.RootEntity;
import com.ardademirtas.service.ITransactionService;

import jakarta.validation.Valid;

@RequestMapping(path = "/rest/api/transaction")
@RestController
public class TransactionControllerImpl extends RestBaseController implements ITransactionController {

	@Autowired
	private ITransactionService transactionService;
	
	
	
	@PostMapping(path = "/save")
	@Override
	public RootEntity<DtoTransaction> saveTransaction(@RequestBody  @Valid   DtoTransactionIU dtoTransactionIU) {
	
		return  ok(transactionService.saveTransaction(dtoTransactionIU));
	}



    @PostMapping(path = "/save/speciel")
	@Override
	public RootEntity<DtoTransaction> saveSpecialTransaction(@RequestBody @Valid  DtoTransactionSpecialIU dtoTransactionSpecialIU) {
		
		return   ok(transactionService.saveSpecialTransaction(dtoTransactionSpecialIU));
	}


    
    @GetMapping("/get/{id}")
	@Override
	public RootEntity<List<DtoTransaction>> getTransaction(@PathVariable(value = "id")  @Valid  Long id) {
	
    	     
		return  ok( transactionService.getTransaction(id));
	}

}
