package com.ardademirtas.controller;

import com.ardademirtas.dto.DtoCard;
import com.ardademirtas.dto.DtoCardIU;
import com.ardademirtas.entity.RootEntity;

public interface ICardController {
	
	

	public RootEntity<DtoCard>  save(DtoCardIU dtoCardIU);
	
	

}
