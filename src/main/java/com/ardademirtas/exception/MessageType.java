package com.ardademirtas.exception;

import lombok.Getter;

@Getter
public enum MessageType {

	
	NO_RECORD_EXIST("1004","record not found"),
	INETADDRES_NOT_FOUND("1005","Inetadress not found"),
	IS_TOKEN_EXPIRED("1006","Token is expired"),
	USERNAME_NOT_FOUND("1007"," username is not found"),
	USERNAME_OR_PASSWORD_INVALID("1008","Username or password is not found"),
	REFRESH_TOKEN_INVALID("1009","Refresh token is not found"),
	REFRESH_TOKEN_EXPIRED("1010","Refreshtoken is expired.Please buy a new token"),
	NO_AUTHORIZATION("1011","You do not have the authorization to do this."),
	ACCOUNT_ERROR("1012" ,"The account is blocked or has expired."),
	ACCOUNT_BLOKE("1013","The account is blocked; no transactions can be made."),
	CARD_EXPIRE("1014","Your card has expired and no transactions can be processed. Please renew it."),
	CARD_BLOKE("1015","The card is blocked; no transactions can be made."),
	INSUFFICIENT_BALANCE("1016","You do not have sufficient balance for this transaction."), 
	INVALID_BALANCE("1017","faulty balancing attempt"),
	INVALID_AMOUNT("1018","invalid amount value."),
	INVALID_TYPE("1019","Invalıd transactıon type"),
	CURRENCY_RATES_IS_OCCURED("1020","Exchange rate could not be obtained."),
	GENERAL_EXCEPTİON("9999","A general error occurred.");

	
	
	  private String code;
	
	  private String message;
	  
	  
	   MessageType(String code, String message) {
		  
		  this.code=code;
		  this.message=message;
	  }
	
	
}
