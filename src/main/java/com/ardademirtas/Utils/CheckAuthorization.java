package com.ardademirtas.Utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.ardademirtas.exception.BaseException;
import com.ardademirtas.exception.ErrorMessage;
import com.ardademirtas.exception.MessageType;

public class CheckAuthorization {

	public static void checkAuthorization(String requestName) {

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();

		if (!requestName.equals(userName)) {

			throw new BaseException(new ErrorMessage(MessageType.NO_AUTHORIZATION, ""));

		}

	}

}
