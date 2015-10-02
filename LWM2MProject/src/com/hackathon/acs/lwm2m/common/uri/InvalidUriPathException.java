package com.hackathon.acs.lwm2m.common.uri;

public class InvalidUriPathException extends Exception {

	private static final long serialVersionUID = -5854349723349072877L;

	public InvalidUriPathException() {
		super();
	}

	public InvalidUriPathException(String message) {
		super(message);
	}


	public InvalidUriPathException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidUriPathException(Throwable cause) {
		super(cause);
	}
}
