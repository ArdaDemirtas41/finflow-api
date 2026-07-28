package com.ardademirtas.exception;

public class BaseException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;

	
	
	public BaseException() {
	
	}
	
	
	  public BaseException(ErrorMessage message) {
		  
		    super(message.preparedMessage());
  
	  }
	
	
}
