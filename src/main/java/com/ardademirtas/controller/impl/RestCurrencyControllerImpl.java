package com.ardademirtas.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ardademirtas.controller.IRestCurrencyRestController;
import com.ardademirtas.controller.RestBaseController;
import com.ardademirtas.dto.CurrencyRatesResponse;
import com.ardademirtas.entity.RootEntity;
import com.ardademirtas.service.ICurrencyRatesService;

@RestController
@RequestMapping(path = "/rest/api/")
public class RestCurrencyControllerImpl extends RestBaseController implements IRestCurrencyRestController{

	@Autowired
	private ICurrencyRatesService currencyRatesService;
	
	
	@GetMapping(path = "/currency-rates")
	@Override
	public RootEntity<CurrencyRatesResponse> getCurrencyRatesResponse(
	@RequestParam("startDate")String startDate, @RequestParam("endDate")String endDate) {
		
		return ok( currencyRatesService.getCurrencyRatesResponse(startDate, endDate));
	}
	
	

}
