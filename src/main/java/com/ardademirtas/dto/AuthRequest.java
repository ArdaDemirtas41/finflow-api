package com.ardademirtas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

	
	@NotBlank(message = "It cannot be empty or null.")
	private String username;
	
	@NotBlank(message = "It cannot be empty or null.")
	private String  password;
	
	
}
