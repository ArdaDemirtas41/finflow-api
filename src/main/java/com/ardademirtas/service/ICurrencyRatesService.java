package com.ardademirtas.service;

import com.ardademirtas.dto.CurrencyRatesResponse;

public interface ICurrencyRatesService {
	
	
	public CurrencyRatesResponse getCurrencyRatesResponse(String startDate, String endDate);

}
