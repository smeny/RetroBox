package org.javanes.exception;

/**
 * A ReadOutOfMemoryException is thrown when a program operation
 * tries to read the memory at an incorrect offset value.
 * @author Stéphane Meny
 */
public final class ReadOutOfMemoryException extends Exception {
	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs the exception with an explicit error message.
	 * @param offset The incorrect offset value.
	 * @param memoryTag Tag used to identify which memory was accessed
	 * before the exception was thrown.
	 */
	public ReadOutOfMemoryException(final int offset, final String memoryTag) {
		super("The incorrect offset " + offset
				+ " accessing " + memoryTag
				+ " was specified during a read operation");
	}
}
