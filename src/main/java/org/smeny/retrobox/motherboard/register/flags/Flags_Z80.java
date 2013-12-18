/**
 * Flags Z80
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
package org.smeny.retrobox.motherboard.register.flags;


/**
 * This Enum represents all existing flags for a 2A03 CPU status register.
 */
enum Flags_Z80 implements IFlags {
	/**
	 * The carry flag is used to store a potential carry or borrow after an arithmetic operation.
	 */
	CARRY(0),

	ADD_SUBSTRACT(1),

	PARITY_OVERFLOW(2),

	/**
	 * The Half-Carry flag equals one if a carry resulted into or borrow from bit 4 of the accumulator.
	 */
	HALF_CARRY(4),

	/**
	 * The zero flag is used to indicate if the last operation gave a result of zero.
	 */
	ZERO(6),

	/**
	 * The Sign flag equals to one if the MSB of the result is equal to one (negative).
	 */
	SIGN(7);

	private Flags_Z80(int pos) {
		position = pos;
	}

	private final int position;

	@Override
	public int getPosition() {
		return position;
	}

}
