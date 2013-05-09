package org.javanes.motherboard.processor;

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
