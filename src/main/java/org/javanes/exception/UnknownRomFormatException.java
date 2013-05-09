package org.javanes.exception;

public final class UnknownRomFormatException extends Exception {
	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	public UnknownRomFormatException() {
		super("The loaded rom format is not supported");
	}
}
