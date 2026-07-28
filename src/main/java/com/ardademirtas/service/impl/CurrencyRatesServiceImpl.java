package com.ardademirtas.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ardademirtas.dto.CurrencyRatesResponse;
import com.ardademirtas.exception.BaseException;
import com.ardademirtas.exception.ErrorMessage;
import com.ardademirtas.exception.MessageType;
import com.ardademirtas.service.ICurrencyRatesService;

@Service
public class CurrencyRatesServiceImpl implements ICurrencyRatesService {

	@Value("${evds.key}")
	String evdsKey;
	
	@Override
	public CurrencyRatesResponse getCurrencyRatesResponse(String startDate, String endDate) {

		String rootUrl = "https://evds3.tcmb.gov.tr/igmevdsms-dis/";
		String series = "TP.DK.USD.S.YTL";
		String type = "json";

		String endPoint = rootUrl + "series=" + series + "&startDate=" + startDate + "&endDate=" + endDate + "&type="
				+ type;

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("key", evdsKey);

		HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

		RestTemplate restTemplate = new RestTemplate();

	
		try {

			ResponseEntity<CurrencyRatesResponse> response = restTemplate.exchange(endPoint, HttpMethod.GET, httpEntity,
					new ParameterizedTypeReference<CurrencyRatesResponse>() {
					});
			
			if (response.getStatusCode().is2xxSuccessful()) {
				return response.getBody();
				
			}
			
			
		} catch (Exception e) {
			
			new BaseException(new ErrorMessage(MessageType.CURRENCY_RATES_IS_OCCURED, e.getMessage()));
		}
		return null;
		
		


	}

}
