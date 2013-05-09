package org.javanes.motherboard.processor;

public abstract class AbstractDecoder {
    private OperationCode[] opcodeTable;

    protected abstract void initialise();

    protected final OperationCode[] getOpcodeTable() {
        return opcodeTable;
    }

    protected final void setOpcodeTable(final OperationCode[] opcodeTable) {
        this.opcodeTable = opcodeTable;
    }

    public final OperationCode getOpcode(final int index) {
        OperationCode opcode = null;
        if (index >= 0 && (index - 1) < opcodeTable.length) {
            opcode = opcodeTable[index];
        }
        return opcode;
    }

}
