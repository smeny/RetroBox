/**
 * AbstractFlagsRegister
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

import java.util.BitSet;

/**
 * The flags register of a CPU stores all flags needed to compute arithmetic operations. Only simple logic should be included here or in
 * potential subclasses.
 */
public abstract class AbstractFlagsRegister<T extends Enum<T> & IFlags> {
	/**
	 * Mask used to get the sign bit of a byte value.
	 */
	protected static final int BYTE_SIGN_MASK = 0x80;
	/**
	 * Mask used to check if we overflowed a byte value (>255)
	 */
	protected static final int UNSIGNED_BYTE_MAX_VALUE = 0xFF;
	/**
	 * Flags from CPU are contained inside a BitSet class. It is a optimized structure to handle set and clear operations on bits.
	 */
	private final BitSet statusData;
	/**
	 * Class of parameter type T is passed in the constructor because of the type erasure caused by generics.
	 */
	private final Class<T> enumClass;

	/**
	 * Default constructor that will initialize the BitSet structure and set its size.
	 */
	protected AbstractFlagsRegister(int size, Class<T> c) {
		statusData = new BitSet(size);
		enumClass = c;
	}

	private int getPosition(String flagName) {
		T enumInstance = Enum.valueOf(enumClass, flagName);
		return enumInstance.getPosition();
	}

	protected boolean isFlagSet(String flagName) {
		return statusData.get(getPosition(flagName));
	}

	protected void setFlag(String flagName) {
		statusData.set(getPosition(flagName));
	}

	protected void clearFlag(String flagName) {
		statusData.clear(getPosition(flagName));

	}

	public boolean isCarryFlagSet() throws IllegalArgumentException {
		return isFlagSet(IFlags.CARRY);
	}

	public void setCarryFlag() throws IllegalArgumentException {
		setFlag(IFlags.CARRY);
	}
	
	public void setCarryFlag(int value) throws IllegalArgumentException {
		if(value > UNSIGNED_BYTE_MAX_VALUE) {
			setCarryFlag();
		} else {
			clearCarryFlag();
		}
	}

	public void clearCarryFlag() throws IllegalArgumentException {
		clearFlag(IFlags.CARRY);
	}

	public boolean isZeroFlagSet() throws IllegalArgumentException {
		return isFlagSet(IFlags.ZERO);
	}

	public void setZeroFlag() throws IllegalArgumentException {
		setFlag(IFlags.ZERO);
	}
	
	public void setZeroFlag(int value) throws IllegalArgumentException {
		if (value == 0) {
			setZeroFlag();
		} else {
			clearZeroFlag();
		}
	}

	public void clearZeroFlag() throws IllegalArgumentException {
		clearFlag(IFlags.ZERO);
	}

	public boolean isOverflowFlagSet() throws IllegalArgumentException {
		return isFlagSet(IFlags.OVERFLOW);
	}

	public void setOverflowFlag() throws IllegalArgumentException {
		setFlag(IFlags.OVERFLOW);
	}

	public void clearOverflowFlag() throws IllegalArgumentException {
		clearFlag(IFlags.OVERFLOW);
	}

	public boolean isNegativeFlagSet() throws IllegalArgumentException {
		return isFlagSet(IFlags.NEGATIVE);
	}

	public void setNegativeFlag() throws IllegalArgumentException {
		setFlag(IFlags.NEGATIVE);
	}

	public void setNegativeFlag(int value) throws IllegalArgumentException {
		if ((value & BYTE_SIGN_MASK) == 1) {
			setNegativeFlag();
		} else {
			clearNegativeFlag();
		}
	}

	public void clearNegativeFlag() throws IllegalArgumentException {
		clearFlag(IFlags.NEGATIVE);
	}

}
