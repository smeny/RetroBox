/**
 * FlagsRegister Z80
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


public final class FlagsRegister_Z80 extends AbstractFlagsRegister<Flags_Z80> {
	
    /**
     * It is the size of the CPU's status register. For the Z80, register F and F'
     * size are 8 bits (1 Byte).
     */
    private static final int STATUS_REGISTER_SIZE = 8;
    
	public FlagsRegister_Z80() {
		super(STATUS_REGISTER_SIZE, Flags_Z80.class);
	}
	
	@Override
	public boolean isOverflowFlagSet() throws IllegalArgumentException {
		return isFlagSet(IFlags.PARITY_OVERFLOW);
	}

	@Override
	public void setOverflowFlag() throws IllegalArgumentException {
		setFlag(IFlags.PARITY_OVERFLOW);
	}
	
	@Override
	public void clearOverflowFlag() throws IllegalArgumentException {
		clearFlag(IFlags.PARITY_OVERFLOW);
	}
	
	public boolean isParityFlagSet() throws IllegalArgumentException {
		return isFlagSet(IFlags.PARITY_OVERFLOW);
	}

	public void setParityFlag() throws IllegalArgumentException {
		setFlag(IFlags.PARITY_OVERFLOW);
	}
	
	public void clearParityFlag() throws IllegalArgumentException {
		clearFlag(IFlags.PARITY_OVERFLOW);
	}
	
	@Override
	public boolean isNegativeFlagSet() throws IllegalArgumentException {
		return isFlagSet(IFlags.SIGN);
	}

	@Override
	public void setNegativeFlag() throws IllegalArgumentException {
		setFlag(IFlags.SIGN);
	}
	
	@Override
	public void clearNegativeFlag() throws IllegalArgumentException {
		clearFlag(IFlags.SIGN);
	}
	
	public boolean isHalfCarryFlagSet() throws IllegalArgumentException {
		return isFlagSet(IFlags.HALF_CARRY);
	}

	public void setHalfCarryFlag() throws IllegalArgumentException {
		setFlag(IFlags.HALF_CARRY);
	}
	
	public void clearHalfCarryFlag() throws IllegalArgumentException {
		clearFlag(IFlags.HALF_CARRY);
	}
	
	public boolean isSubstractFlagSet() throws IllegalArgumentException {
		return isFlagSet(IFlags.ADD_SUBSTRACT);
	}

	public void setSubstractFlag() throws IllegalArgumentException {
		setFlag(IFlags.ADD_SUBSTRACT);
	}
	
	public void clearSubstractFlag() throws IllegalArgumentException {
		clearFlag(IFlags.ADD_SUBSTRACT);
	}
	
}
