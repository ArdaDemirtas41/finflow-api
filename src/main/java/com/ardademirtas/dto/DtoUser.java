package com.ardademirtas.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoUser {


	   @NotBlank
	   private String username;
	   
	   @NotBlank
	   private String password;
	   
	   @NotBlank
	   private Date  createdTime;
	
	
}
