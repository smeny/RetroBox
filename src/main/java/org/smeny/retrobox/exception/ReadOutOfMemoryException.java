/**
 * ReadOutOfMemoryException
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
package org.smeny.retrobox.exception;

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
