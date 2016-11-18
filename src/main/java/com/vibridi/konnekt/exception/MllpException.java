package com.vibridi.konnekt.exception;

import java.io.IOException;

public class MllpException extends IOException {
	private static final long serialVersionUID = 1L;
	
	public MllpException(String message, Throwable cause) {
		super(message, cause);
	}

	public MllpException(String message) {
		super(message);
	}
}
