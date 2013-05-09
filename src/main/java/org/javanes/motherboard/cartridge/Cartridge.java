package org.javanes.motherboard.cartridge;

public class Cartridge {
    /**
     * Contains all program data read from the cartridge. First index is the
     * program ROM bank number and second index is the offset in the bank.
     */
    private byte[][] programRom;
    
    /**
     * Contains all graphics data read from the cartridge. First index is the
     * character ROM bank number and second index is the offset in the bank.
     */
    private byte[][] characterRom;
    
    /**
     * Information header read from the cartridge. Contains informations about
     * how to read the cartridge and handle data.
     */
    private byte[] header;

    public byte[][] getProgramRom() {
        return programRom;
    }

    public void setProgramRom(byte[][] programRom) {
        this.programRom = programRom;
    }

    public byte[][] getCharacterRom() {
        return characterRom;
    }

    public void setCharacterRom(byte[][] characterRom) {
        this.characterRom = characterRom;
    }

    public byte[] getHeader() {
        return header;
    }

    public void setHeader(byte[] header) {
        this.header = header;
    }
    
    public int getProgramRomPageCount() {
        return header[HeaderIndex.PRG_ROM_PAGE_COUNT.ordinal()];
    }
    
    public int getCharacterRomPageCount() {
        return header[HeaderIndex.CHR_ROM_PAGE_COUNT.ordinal()];
    }

}
