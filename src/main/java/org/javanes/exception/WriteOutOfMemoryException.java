/**
 * WriteOutOfMemoryException
 * 
 * Copyright 2013 Stéphane MENY
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.javanes.exception;

/**
 * A WriteOutOfMemoryException is thrown when a program operation
 * tries to write a value in memory at an incorrect offset.
 * @author Stéphane Meny
 */
public final class WriteOutOfMemoryException extends Exception {
	
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
