package com.ardademirtas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoCustomerIU {
	

	
	@NotBlank
	private String firstname;
	

	@NotBlank
	private String lastname;
	
	
	
	@NotBlank
	@Size(min = 11, max = 11,message = "TC No must be exactly 11 digits")
	private String tcno;
	
	
	@NotBlank
	@Email(message = "It must comply with the email format.")
	private String email;
	

	@NotBlank
	@Pattern(regexp = "\\d{11}", message = "Phone number must contain exactly 11 digits.")
	private String phone;
	
	

}
