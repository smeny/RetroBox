/**
 * CentralProcessingUnit
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
package org.smeny.retrobox.motherboard.processor.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.smeny.retrobox.exception.ReadOutOfMemoryException;
import org.smeny.retrobox.exception.UnknownOperationException;
import org.smeny.retrobox.exception.WriteOutOfMemoryException;
import org.smeny.retrobox.motherboard.cartridge.Cartridge;
import org.smeny.retrobox.motherboard.memory.AbstractMemoryController;
import org.smeny.retrobox.motherboard.memory.nes.BasicMemoryController;
import org.smeny.retrobox.motherboard.processor.nes.AddressingMode;
import org.smeny.retrobox.motherboard.processor.nes.ArithmeticAndLogicalUnit;
import org.smeny.retrobox.motherboard.processor.nes.Decoder;
import org.smeny.retrobox.motherboard.processor.nes.InstructionSet;
import org.smeny.retrobox.motherboard.processor.nes.OperationCode;
import org.smeny.retrobox.motherboard.register.Register;
import org.smeny.retrobox.motherboard.register.flags.FlagsRegister_2A03;

/**
 * This class represents the Central Processing Unit of the NES. The CPU is a
 * 2A03 processor based on the Ricoh 6502 processor. Note that 2A03 CPU is
 * Little Endian, this means that the least significant byte (LSB) will be
 * stored in the lowest memory address.
 * 
 * @author Stéphane Meny
 */
public final class Core_2A03 extends AbstractCore {

    private static final OperationCode RESET = new OperationCode(InstructionSet.JMP, AddressingMode.ABSOLUTE);

    private static final int RESET_ADDRESS = 0xFFFC;

    private static final int CPU_START_ADDRESS = 0xC000;

    private static final int STACK_START_ADDRESS = 0x01FF;

    private static final byte MOST_SIGNIFICANT_BYTE_SHIFT = 8;

    private static final String SPACE = " ";

    /** Our default logger for this class. */
    private static final Logger LOGGER = Logger.getLogger(Core_2A03.class.getName());

    /** The status register where each resulting flag is stored. */
    private FlagsRegister_2A03 statusRegister;

    /** The accumulator where data are read/stored. */
    private Register accumulator;

    private Register programCounter;
    private Register registerX;
    private Register registerY;
    private Register stackPointer;
    private AbstractMemoryController memory;
    private boolean isPoweredOn;
    private boolean isReset;
    private Decoder decoder;
    private ArithmeticAndLogicalUnit alu;
    private OperationCode currentOpCode;
    private int currentOperand;
    private int currentOperandSize;
    private int lastInstructionOffset;

    public Core_2A03(Cartridge cart) {
        accumulator = Register.getRegister16();
        registerX = Register.getRegister8();
        registerY = Register.getRegister8();
        stackPointer = Register.getRegister8();
        statusRegister = new FlagsRegister_2A03();
        memory = new BasicMemoryController(cart);
        isPoweredOn = true;
        isReset = false;
        programCounter = Register.getRegister16();
        decoder = new Decoder();
        alu = new ArithmeticAndLogicalUnit(this);
    }

    private void readNextInstruction() throws ReadOutOfMemoryException {
        lastInstructionOffset = programCounter.getRegisterData();
        int instructionCode = memory.readMemory(lastInstructionOffset);
        programCounter.increment();
        
        currentOpCode = decoder.getOpcode(instructionCode);
        checkReset();
        readOperand();
    }
    
    private void checkReset() {
        if (isReset) {
            isReset = false;
            currentOpCode = RESET;
            programCounter.setRegisterData(RESET_ADDRESS);
        }
    }

    public void emulate(final long clockCount) throws ReadOutOfMemoryException, WriteOutOfMemoryException {
        long remainingClock = clockCount;
        programCounter.setRegisterData(CPU_START_ADDRESS);
        stackPointer.setRegisterData(STACK_START_ADDRESS);

        while (isPoweredOn && remainingClock > 0) {
            readNextInstruction();
            // Debug CPU values
            LOGGER.log(Level.INFO, this.toString());
            // Perform the decoded instruction and set program counter to next
            // instruction
            try {
                alu.performInstruction();
            } catch (UnknownOperationException e) {
                LOGGER.log(Level.SEVERE, "Error when performing instruction", e);
                System.exit(1);
            }
            remainingClock--;
        }
    }

    private void readOperand() throws ReadOutOfMemoryException {
        // Reads the remaining operand at the program counter address
        int operandValue = 0;
        currentOperandSize = currentOpCode.getAddressingMode().getOperandSize();
        for (int i = 0; i < currentOperandSize; i++) {
            int offset = programCounter.getRegisterData();
            int currentByte = memory.readMemory(offset);
            // Shifts the current read byte depending of its position
            for (int j = 0; j < i; j++) {
                currentByte = currentByte << MOST_SIGNIFICANT_BYTE_SHIFT;
            }
            // Reorder the operand value while the last byte read
            operandValue += currentByte;
            programCounter.increment();
        }
        currentOperand = operandValue;
    }

    public void stop() {
        isPoweredOn = false;
    }

    public void reset() {
        isReset = true;
    }

    public AbstractMemoryController getMemory() {
        return memory;
    }

    public Register getProgramCounter() {
        return programCounter;
    }

    public int getCurrentOperand() {
        return currentOperand;
    }

    public int getCurrentOperandSize() {
        return currentOperandSize;
    }

    public OperationCode getCurrentOpCode() {
        return currentOpCode;
    }

    public FlagsRegister_2A03 getStatusRegister() {
        return statusRegister;
    }

    public Register getRegisterX() {
        return registerX;
    }
    
    public Register getRegisterY() {
        return registerY;
    }

    public Register getStackPointer() {
        return stackPointer;
    }
    
    public Register getAccumulator() {
        return accumulator;
    }
    
    @Override
    public String toString() {
        StringBuilder sbuilder = new StringBuilder();
        sbuilder.append(Integer.toHexString(lastInstructionOffset).toUpperCase());
        sbuilder.append(SPACE);
        sbuilder.append(currentOpCode.getCode());
        sbuilder.append(SPACE);
        sbuilder.append(Integer.toHexString(currentOperand));
        sbuilder.append(SPACE);
        sbuilder.append("A:");
        sbuilder.append(Integer.toHexString(accumulator.getRegisterData()).toUpperCase());
        sbuilder.append(SPACE);
        sbuilder.append("X:");
        sbuilder.append(Integer.toHexString(registerX.getRegisterData()).toUpperCase());
        sbuilder.append(SPACE);
        sbuilder.append("Y:");
        sbuilder.append(Integer.toHexString(registerY.getRegisterData()).toUpperCase());
        return sbuilder.toString();
    }

}
