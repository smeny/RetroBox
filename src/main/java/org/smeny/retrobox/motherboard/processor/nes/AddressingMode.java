/**
 * AddressingMode
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
package org.smeny.retrobox.motherboard.processor.nes;

public enum AddressingMode {
    
    IMPLIED(0),
    
    ACCUMULATOR(0),
    
    IMMEDIATE(1),
    
    RELATIVE(1),
    
    ZERO_PAGE(1),
    
    ZERO_PAGE_X_INDEXED(1),
    
    ZERO_PAGE_Y_INDEXED(1),
    
    ABSOLUTE(2),
    
    INDIRECT(2),
    
    ABSOLUTE_X_INDEXED(2),
    
    ABSOLUTE_Y_INDEXED(2),
    
    INDIRECT_X_PREINDEXED(1),
    
    INDIRECT_Y_POSTINDEXED(2);
    
    private int operandSize;
    
    private AddressingMode(int operandSize) {
        this.operandSize = operandSize;
    }
    
    public int getOperandSize() {
        return this.operandSize;
    }

}
