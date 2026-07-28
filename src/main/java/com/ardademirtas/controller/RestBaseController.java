package com.ardademirtas.controller;

import com.ardademirtas.entity.RootEntity;

public class RestBaseController {

	
	public <T> RootEntity<T> ok(T data) {

		return RootEntity.ok(data);

	}

}
