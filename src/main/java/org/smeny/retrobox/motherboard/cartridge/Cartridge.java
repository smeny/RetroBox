/**
 * Cartridge
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
package org.smeny.retrobox.motherboard.cartridge;

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
