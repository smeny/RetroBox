package org.javanes.exception;

/**
 * A WriteOutOfMemoryException is thrown when a program operation
 * tries to write a value in memory at an incorrect offset.
 * @author Stéphane Meny
 */
public final class WriteOutOfMemoryException extends Exception {
	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs the exception with an explicit error message.
	 * @param offset The incorrect offset value.
	 * @param value The value to write at the specified offset.
	 * @param memoryTag Tag used to identify which memory was accessed
	 * before the exception was thrown.
	 */
	public WriteOutOfMemoryException(final int offset, final int value,
			final String memoryTag) {
		super("The value " + value + "tried to be written at "
				+ "incorrect offset " + offset
				+ " accessing " + memoryTag
				+ " in write mode");
	}
}
