/**
 * ArithmeticAndLogicalUnit
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
package org.javanes.motherboard.processor;

import org.javanes.exception.ReadOutOfMemoryException;
import org.javanes.exception.UnknownOperationException;
import org.javanes.exception.WriteOutOfMemoryException;
import org.javanes.motherboard.memory.AbstractMemoryController;
import org.javanes.motherboard.register.Register;
import org.javanes.motherboard.register.StatusRegister;

public final class ArithmeticAndLogicalUnit {

    /**
     * Mask used to get the last byte on an integer.
     */
    private static final int BYTE_MASK = 0xFF;

    private static final byte MOST_SIGNIFICANT_BYTE_SHIFT = 8;

    private CentralProcessingUnit cpu;
    private AddressingMode currentAddressingMode;
    private InstructionSet currentInstructionSet;

    public ArithmeticAndLogicalUnit(CentralProcessingUnit cpu) {
        this.cpu = cpu;
    }

    private void refreshCurrentState() {
        OperationCode opcode = cpu.getCurrentOpCode();
        currentInstructionSet = opcode.getInstruction();
        currentAddressingMode = opcode.getAddressingMode();
    }

    public void performInstruction() throws UnknownOperationException, ReadOutOfMemoryException, WriteOutOfMemoryException {
        refreshCurrentState();
        switch (currentInstructionSet) {
            case ADC:
                additionWithCarry();
                break;
            case BIT:
                testBitsInMemoryWithAccumulator();
                break;
            case CLC:
                cpu.getStatusRegister().clearCarryFlag();
                break;
            case JMP:
                jump();
                break;
            case LDX:
                loadX();
                break;
            case STX:
                storeX();
                break;
            case JSR:
                jumpSavingReturn();
                break;
            case NOP:
                // Nothing to do
                break;
            case SEC:
                cpu.getStatusRegister().setCarryFlag();
                break;
            case BCS:
                branchOnCarrySet();
                break;
            case BCC:
                branchOnCarryClear();
                break;
            case BEQ:
                branchOnZeroSet();
                break;
            case BNE:
                branchOnZeroClear();
                break;
            case LDA:
                loadAccumulatorWithMemory();
                break;
            case STA:
                storeAccumulatorInMemory();
                break;
            default:
                throw new UnknownOperationException("Instruction Set " + currentInstructionSet + " is not implemented");
        }
    }

    private int getSourceValue() throws ReadOutOfMemoryException, UnknownOperationException {
        int sourceValue;
        int operand = cpu.getCurrentOperand();
        AbstractMemoryController memory = cpu.getMemory();
        int x, y, firstOffset, secondOffset;

        switch (currentAddressingMode) {
            case IMMEDIATE:
                sourceValue = operand;
                break;
            case ZERO_PAGE:
            case ABSOLUTE:
                sourceValue = memory.readMemory(operand);
                break;
            case ZERO_PAGE_X_INDEXED:
            case ABSOLUTE_X_INDEXED:
                x = cpu.getRegisterX().getRegisterData();
                sourceValue = memory.readMemory(operand + x);
                break;
            case ABSOLUTE_Y_INDEXED:
                y = cpu.getRegisterY().getRegisterData();
                sourceValue = memory.readMemory(operand + y);
                break;
            case INDIRECT_X_PREINDEXED:
                x = cpu.getRegisterX().getRegisterData();
                firstOffset = (operand + x) & BYTE_MASK;
                secondOffset = memory.readMemory(firstOffset + 1) << MOST_SIGNIFICANT_BYTE_SHIFT;
                secondOffset += memory.readMemory(firstOffset);
                sourceValue = memory.readMemory(secondOffset);
                break;
            case INDIRECT_Y_POSTINDEXED:
                y = cpu.getRegisterY().getRegisterData();
                firstOffset = memory.readMemory(operand + 1) << MOST_SIGNIFICANT_BYTE_SHIFT;
                firstOffset += memory.readMemory(operand);
                secondOffset = firstOffset + y;
                sourceValue = memory.readMemory(secondOffset);
                break;
            default:
                throw new UnknownOperationException(currentAddressingMode + " is not possible to retrieve source value");
        }
        return sourceValue;
    }

    private void jump() throws UnknownOperationException {
        int operandValue = cpu.getCurrentOperand();

        switch (currentAddressingMode) {
            case RELATIVE:
                cpu.getProgramCounter().increment(operandValue);
                break;
            case ABSOLUTE:
                cpu.getProgramCounter().setRegisterData(operandValue);
                break;
            default:
                throw new UnknownOperationException(currentAddressingMode + " is not implemented for jump instruction");
        }

    }

    private void loadX() throws UnknownOperationException {
        int operandValue = cpu.getCurrentOperand();

        switch (currentAddressingMode) {
            case IMMEDIATE:
                // Nothing to do, the load the value read directly into index X
                // register
                break;
            default:
                throw new UnknownOperationException(currentAddressingMode + " is not implemented for load index X instruction");
        }

        // Set the status register with the needed flags
        StatusRegister sr = cpu.getStatusRegister();
        sr.setNegativeFlag(operandValue);
        sr.setZeroFlag(operandValue);
        // Sets the X index register
        cpu.getRegisterX().setRegisterData(operandValue);
    }

    private void storeX() throws UnknownOperationException, WriteOutOfMemoryException {
        int operandAddress = cpu.getCurrentOperand();

        switch (currentAddressingMode) {
            case ABSOLUTE:
            case ZERO_PAGE:
                int xValue = cpu.getRegisterX().getRegisterData();
                cpu.getMemory().writeMemory(operandAddress, xValue);
                break;
            default:
                throw new UnknownOperationException(currentAddressingMode + " is not implemented for store index X instruction");
        }
    }

    private void jumpSavingReturn() throws UnknownOperationException, WriteOutOfMemoryException {
        int currentOffset = cpu.getProgramCounter().getRegisterData();
        AbstractMemoryController memory = cpu.getMemory();
        Register stackPointer = cpu.getStackPointer();

        int highByte = (currentOffset >> MOST_SIGNIFICANT_BYTE_SHIFT) & BYTE_MASK;
        int lowByte = currentOffset & BYTE_MASK;

        memory.writeMemory(stackPointer.getRegisterData(), highByte);
        stackPointer.decrement();
        memory.writeMemory(stackPointer.getRegisterData(), lowByte);

        if (currentAddressingMode != AddressingMode.ABSOLUTE) {
            throw new UnknownOperationException(currentAddressingMode + " is not implemented for jump with return address saving");
        } else {
            jump();
        }
    }

    private void branchOnCarrySet() throws UnknownOperationException {
        if (cpu.getStatusRegister().isCarryFlagSet()) {
            jump();
        }
    }

    private void branchOnCarryClear() throws UnknownOperationException {
        if (!cpu.getStatusRegister().isCarryFlagSet()) {
            jump();
        }
    }

    private void branchOnZeroSet() throws UnknownOperationException {
        if (cpu.getStatusRegister().isZeroFlagSet()) {
            jump();
        }
    }

    private void branchOnZeroClear() throws UnknownOperationException {
        if (!cpu.getStatusRegister().isZeroFlagSet()) {
            jump();
        }
    }

    /**
     * Computes an addition between a value from memory, the value contained
     * inside the accumulator and the remaining carry.
     *
     * @throws UnknownOperationException
     * @throws ReadOutOfMemoryException
     */
    private void additionWithCarry() throws ReadOutOfMemoryException, UnknownOperationException {
        int carry = 0;
        int srcValue = getSourceValue();
        StatusRegister sr = cpu.getStatusRegister();
        Register accumulator = cpu.getAccumulator();
        int accValue = accumulator.getRegisterData();

        // Sets the carry if there is one
        if (sr.isCarryFlagSet()) {
            carry = 1;
        }
        // Computes addition and computes overflow
        int result = accValue + srcValue + carry;
        int overflow = (~(accValue ^ srcValue)) & (accValue ^ result);

        // Sets every resulting flags
        sr.setCarryFlag(result);
        sr.setZeroFlag(result);
        sr.setOverflowFlag(overflow);
        sr.setNegativeFlag(result);

        // Sets the result in the accumulator
        accumulator.setRegisterData(result);
    }

    private void loadAccumulatorWithMemory() throws ReadOutOfMemoryException, UnknownOperationException {
        int srcValue = getSourceValue();
        StatusRegister statusRegister = cpu.getStatusRegister();
        Register accumulator = cpu.getAccumulator();

        // Sets every resulting flags
        statusRegister.setZeroFlag(srcValue);
        statusRegister.setNegativeFlag(srcValue);
        accumulator.setRegisterData(srcValue);
    }

    private void storeAccumulatorInMemory() throws ReadOutOfMemoryException, UnknownOperationException, WriteOutOfMemoryException {
        int srcValue = getSourceValue();
        AbstractMemoryController memory = cpu.getMemory();
        Register accumulator = cpu.getAccumulator();

        memory.writeMemory(srcValue, accumulator.getRegisterData());
    }

    private void logicalAnd(final byte memoryValue) {
        Register accumulator = cpu.getAccumulator();
        int accumulatorValue = accumulator.getRegisterData();
        StatusRegister statusRegister = cpu.getStatusRegister();

        // Computes logical AND
        accumulatorValue &= memoryValue;

        // Sets every resulting flags
        statusRegister.setZeroFlag(accumulatorValue);
        statusRegister.setNegativeFlag(accumulatorValue);

        // Sets the result on one byte
        accumulator.setRegisterData(accumulatorValue & BYTE_MASK);
    }

    /*private void arithmeticShiftLeft(final byte memoryValue) {
        StatusRegister statusRegister = cpu.getStatusRegister();
        
        statusRegister.setCarryFlag(memoryValue);
        int result = memoryValue << 1;

        // Code the value from memory on an unsigned byte
        int unsignedMemoryValue = memoryValue & BYTE_MASK;

    }*/

    private void testBitsInMemoryWithAccumulator() throws ReadOutOfMemoryException, UnknownOperationException {
        switch (currentAddressingMode) {
            case ABSOLUTE:
            case ZERO_PAGE:
                StatusRegister statusRegister = cpu.getStatusRegister();
                Register accumulator = cpu.getAccumulator();
                int srcValue = getSourceValue();

                statusRegister.setNegativeFlag(srcValue);
                statusRegister.setOverflowFlag(0x40 & srcValue);   /* Copy bit 6 to OVERFLOW flag. */
                statusRegister.setZeroFlag(srcValue & accumulator.getRegisterData());
                break;
            default:
                throw new UnknownOperationException(currentAddressingMode + " is not implemented test bits in memory instruction");
        }


    }

}
