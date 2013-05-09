package org.javanes.exception;

public class ReadOnlyException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ReadOnlyException() {
		super("Write operation is not allowed on a read only memory");
	}

}
