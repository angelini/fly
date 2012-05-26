package com.angelini.fly;

import javax.servlet.ServletException;

public class HttpException extends ServletException {

	private static final long serialVersionUID = 1L;
	
	public HttpException(Exception e) {
		super(e);
	}

}
