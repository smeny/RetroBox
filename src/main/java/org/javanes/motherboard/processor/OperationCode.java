/**
 * OperationCode
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

import java.util.logging.Level;
import java.util.logging.Logger;

public class OperationCode {

    /** Our default logger for this class. */
    private static final Logger LOGGER = Logger.getLogger(OperationCode.class.getName());

    private InstructionSet instruction;
    private AddressingMode addressingMode;
    private String code;

    public OperationCode(final String code, final String instruction, final String addressingMode) {
        try {
            this.addressingMode = Enum.valueOf(AddressingMode.class, addressingMode);
            this.instruction = Enum.valueOf(InstructionSet.class, instruction);
            this.code = code;
        } catch (IllegalArgumentException iae) {
            final String errorMsg = "Error decoding opcode " + this;
            LOGGER.log(Level.SEVERE, errorMsg, iae);
        }
    }

    public OperationCode(final InstructionSet instruction, final AddressingMode addressingMode) {
        this.instruction = instruction;
        this.addressingMode = addressingMode;
    }

    public InstructionSet getInstruction() {
        return this.instruction;
    }

    public void setInstruction(InstructionSet instruction) {
        this.instruction = instruction;
    }

    public AddressingMode getAddressingMode() {
        return this.addressingMode;
    }

    public void setAddressingMode(AddressingMode addressingMode) {
        this.addressingMode = addressingMode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public String toString() {
    	return "Opcode " + instruction + " " + addressingMode;
    }

}
