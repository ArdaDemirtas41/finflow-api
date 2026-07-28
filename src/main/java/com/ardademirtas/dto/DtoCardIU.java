package com.ardademirtas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoCardIU {

	
	@NotNull(message = "It cannot null.")
    private Long account_id;
    
	
}
