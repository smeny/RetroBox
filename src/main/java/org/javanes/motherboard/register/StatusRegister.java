/**
 * StatusRegister
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
package org.javanes.motherboard.register;

import java.util.BitSet;

/**
 * The status register of the CPU stores all flags needed to compute arithmetic
 * operations.
 * 
 * @author Stéphane Meny
 */
public final class StatusRegister {
    /**
     * It is the size of the CPU's status register. For the 2A03, the register
     * size is 8 bits (1 Byte).
     */
    private static final int STATUS_REGISTER_SIZE = 8;

    /**
     * Mask used to get the sign bit of a byte.
     */
    private static final int SIGN_MASK = 0x80;

    /**
     * Flags from CPU are contained inside a BitSet class. It is a optimised
     * structure to handle set and clear operations on bits. Note that the 5th
     * flag is unused.
     */
    private BitSet statusData;

    private static final int UNSIGNED_BYTE_MAX_VALUE = 0xFF;

    /**
     * Default constructor that will initialise the BitSet structure and set its
     * size.
     */
    public StatusRegister() {
        statusData = new BitSet(STATUS_REGISTER_SIZE);
    }

    public boolean isCarryFlagSet() {
        return statusData.get(StatusFlag.CARRY_FLAG.ordinal());
    }

    public void setCarryFlag() {
        statusData.set(StatusFlag.CARRY_FLAG.ordinal());
    }

    public void setCarryFlag(int operationResult) {
        if (operationResult > UNSIGNED_BYTE_MAX_VALUE) {
            statusData.set(StatusFlag.CARRY_FLAG.ordinal());
        } else {
            statusData.clear(StatusFlag.CARRY_FLAG.ordinal());
        }
    }
    
    public void clearCarryFlag() {
        statusData.clear(StatusFlag.CARRY_FLAG.ordinal());
    }

    public boolean isZeroFlagSet() {
        return statusData.get(StatusFlag.ZERO_FLAG.ordinal());
    }

    public void setZeroFlag(int operationValue) {
        if (operationValue == 0) {
            statusData.set(StatusFlag.ZERO_FLAG.ordinal());
        } else {
            statusData.clear(StatusFlag.ZERO_FLAG.ordinal());
        }
    }

    public boolean isOverflowFlagSet() {
        return statusData.get(StatusFlag.OVERFLOW_FLAG.ordinal());
    }

    public void setOverflowFlag(int overflowResult) {
        if (overflowResult != 0) {
            statusData.set(StatusFlag.OVERFLOW_FLAG.ordinal());
        } else {
            statusData.clear(StatusFlag.OVERFLOW_FLAG.ordinal());
        }
    }

    public boolean isNegativeFlagSet() {
        return statusData.get(StatusFlag.NEGATIVE_FLAG.ordinal());
    }

    public void setNegativeFlag(int operationValue) {
        if ((operationValue & SIGN_MASK) != 0) {
            statusData.set(StatusFlag.NEGATIVE_FLAG.ordinal());
        } else {
            statusData.clear(StatusFlag.NEGATIVE_FLAG.ordinal());
        }
    }

}
