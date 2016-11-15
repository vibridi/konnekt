package com.vibridi.konnekt.exception;

import java.io.IOException;

public class HttpException extends IOException {
	private static final long serialVersionUID = 1L;

	private int responseCode;
	
	public HttpException(int responseCode) {
		super();
		this.responseCode = responseCode;
	}

	public HttpException(String message, Throwable cause, int responseCode) {
		super(message, cause);
		this.responseCode = responseCode;
	}

	public HttpException(String message, int responseCode) {
		super(message);
		this.responseCode = responseCode;
	}

	public HttpException(Throwable cause, int responseCode) {
		super(cause);
		this.responseCode = responseCode;
	}

	public int getResponseCode() {
		return responseCode;
	}
	
	
}
