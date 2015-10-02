package com.hackathon.acs.lwm2m.common;

public class InvalidClientIdentifierException extends Exception {

	private static final long serialVersionUID = -5854349723349072877L;

	public InvalidClientIdentifierException() {
		super();
	}

	public InvalidClientIdentifierException(String message) {
		super(message);
	}


	public InvalidClientIdentifierException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidClientIdentifierException(Throwable cause) {
		super(cause);
	}
}
