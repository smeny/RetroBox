/**
 * Flags 2A03
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
 * This Enum represents all existing flags for a 2A03 CPU status register. The code was previously counting on the ordinal order of the enum
 * to get the flag position. It was modified since flags are not always one behind another (see Flags Z80).
 */
enum Flags_2A03 implements IFlags {
	/**
	 * The carry flag is used to store a potential carry or borrow after an arithmetic operation.
	 */
	CARRY(0),

	/**
	 * The zero flag is used to indicate if the last operation gave a result of zero.
	 */
	ZERO(1),

	/**
	 * The IRQ disable flag is used to enable or disable the maskable interrupts.
	 */
	IRQ_DISABLE(2),

	/**
	 * The Decimal mode flag turns the CPU into BCD computation (Binary Coded Decimal). For the 2A03 processor this mode is not activated,
	 * functions are present for information and compatibility.
	 */
	DECIMAL_MODE(3),

	BREAK_COMMAND(4),

	OVERFLOW(5),

	NEGATIVE(6);

	private Flags_2A03(int pos) {
		position = pos;
	}

	private final int position;

	@Override
	public int getPosition() {
		return position;
	}

}
