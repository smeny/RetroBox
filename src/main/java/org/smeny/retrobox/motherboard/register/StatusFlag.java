/**
 * StatusFlag
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
package org.smeny.retrobox.motherboard.register;

public enum StatusFlag {
	/**
	 * The carry flag is used to store a potential carry or borrow after an
	 * arithmetic operation.
	 */
	CARRY_FLAG,
	
	/** 
	 * The zero flag is used to indicate if the last operation gave a
	 * result of zero.
	 */
	ZERO_FLAG,
	
	/**
	 * The IRQ disable flag is used to enable or disable the maskable
	 * interrupts.
	 */
	IRQ_DISABLE_FLAG,
	
	/**
	 * The Decimal mode flag turns the CPU into BCD computation
	 * (Binary Coded Decimal). For the 2A03 processor this mode is not
	 * activated, functions are present for information and compatibility.
	 */
	DECIMAL_MODE_FLAG,
	BREAK_COMMAND_FLAG,
	OVERFLOW_FLAG,
	NEGATIVE_FLAG
}
