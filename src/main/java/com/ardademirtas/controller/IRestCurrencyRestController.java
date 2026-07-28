package com.ardademirtas.controller;

import com.ardademirtas.dto.CurrencyRatesResponse;
import com.ardademirtas.entity.RootEntity;

public interface IRestCurrencyRestController {
	
	public  RootEntity<CurrencyRatesResponse> getCurrencyRatesResponse(String startDate, String endDate);

}
