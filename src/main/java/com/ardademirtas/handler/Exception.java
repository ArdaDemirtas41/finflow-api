package com.ardademirtas.handler;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Exception<T> {

	private String hostName;

	private String path;

	private Date exception_date;

	private T exception;

}
