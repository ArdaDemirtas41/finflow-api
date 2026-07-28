package com.ardademirtas.controller;

import java.util.List;

import com.ardademirtas.dto.DtoTransaction;
import com.ardademirtas.dto.DtoTransactionIU;
import com.ardademirtas.dto.DtoTransactionSpecialIU;
import com.ardademirtas.entity.RootEntity;

public interface ITransactionController {
	
	 
	 public  RootEntity<DtoTransaction>  saveTransaction(DtoTransactionIU dtoTransactionIU);
	 
	 public RootEntity<DtoTransaction>    saveSpecialTransaction(DtoTransactionSpecialIU dtoTransactionSpecialIU);
	 
	 public RootEntity<List<DtoTransaction>>   getTransaction(Long id);

}
