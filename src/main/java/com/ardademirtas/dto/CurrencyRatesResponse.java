package com.ardademirtas.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyRatesResponse {

	
	private Integer totalCount;
	
	
	private List<CurrencyRatesItems>items;
	
	
	
	
//	{
//	    "totalCount": 1,
//	    "items": [
//	        {
//	            "Tarih": "27-07-2026",
//	            "TP_DK_USD_S_YTL": "47.24970000",
//	            "UNIXTIME": {
//	                "$numberLong": "1785099600"
//	            }
//	        }
//	    ]
//	}
	
	
}
