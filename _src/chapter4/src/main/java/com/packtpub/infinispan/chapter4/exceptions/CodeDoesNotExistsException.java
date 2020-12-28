package com.packtpub.infinispan.chapter4.exceptions;

public class CodeDoesNotExistsException extends Exception {

	public CodeDoesNotExistsException() {
		super();
	}

	public CodeDoesNotExistsException(String message, Throwable cause,
                                      boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CodeDoesNotExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public CodeDoesNotExistsException(String message) {
		super(message);
	}

	public CodeDoesNotExistsException(Throwable cause) {
		super(cause);
	}

}
