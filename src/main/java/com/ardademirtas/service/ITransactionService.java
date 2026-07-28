package com.ardademirtas.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ardademirtas.dto.DtoTransaction;
import com.ardademirtas.dto.DtoTransactionIU;
import com.ardademirtas.dto.DtoTransactionSpecialIU;

public interface ITransactionService {
	
	
	 @Transactional
	 public  DtoTransaction  saveTransaction(DtoTransactionIU dtoTransactionIU);
	 
	 @Transactional
	 public DtoTransaction    saveSpecialTransaction(DtoTransactionSpecialIU dtoTransactionSpecialIU);
	 
	 
	 
	 public List<DtoTransaction>  getTransaction(Long id);

}
