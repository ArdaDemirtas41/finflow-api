package com.ardademirtas.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ardademirtas.controller.ICardController;
import com.ardademirtas.controller.RestBaseController;
import com.ardademirtas.dto.DtoCard;
import com.ardademirtas.dto.DtoCardIU;
import com.ardademirtas.entity.RootEntity;
import com.ardademirtas.service.ICardService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/rest/api/card")
public class CardControllerImpl extends RestBaseController implements ICardController {

	@Autowired
	private ICardService cardService;

	@PostMapping(path = "/save")
	@Override
	public RootEntity<DtoCard> save(@RequestBody @Valid DtoCardIU dtoCardIU) {

		return ok(cardService.save(dtoCardIU));
	}


}
