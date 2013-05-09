package org.javanes.motherboard.register;

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
	NEGATIVE_FLAG;
}
