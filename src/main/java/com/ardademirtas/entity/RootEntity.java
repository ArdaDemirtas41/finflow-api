package com.ardademirtas.entity;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RootEntity<T> {

	private Integer status;

	private boolean result;

	private T data;
	
	private Date createdDate;

	public static <T> RootEntity<T> ok(T data) {

		RootEntity<T> rootEntity = new RootEntity<>();
		
		rootEntity.setData(data);
		rootEntity.setStatus(HttpStatus.OK.value());
		rootEntity.setResult(true);
		rootEntity.setCreatedDate(new Date());;

		return rootEntity;
	}

}
