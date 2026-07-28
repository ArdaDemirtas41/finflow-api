package com.ardademirtas.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

	
	private MessageType messageType;
	
	private String ofStatic;
	
	
	
	  public String preparedMessage() {
		  
		  StringBuilder stringBuilder= new StringBuilder();
		  stringBuilder.append(messageType.getCode());
		  stringBuilder.append("-----"+ messageType.getMessage());
		  
		  if (ofStatic!=null) {
			
			  stringBuilder.append(" : "+ofStatic);
		}
		  
		return  stringBuilder.toString();
		  
	  }
	

}
