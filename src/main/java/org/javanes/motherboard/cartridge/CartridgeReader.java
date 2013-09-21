/**
 * CartridgeReader
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
package org.javanes.motherboard.cartridge;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.javanes.exception.UnknownRomFormatException;

/**
 * This class is responsible for handling accesses to a cartridge. It validates
 * cartridge data, gets the information header and returns both program and
 * characters data in an Cartridge object.
 * 
 * @author Stéphane Meny
 */
public final class CartridgeReader {
    /**
     * Validation header used to recognise a valid NES ROM (iNES format).
     */
    private static final byte[] INES_VALIDATION_HEADER = { 'N', 'E', 'S', 0x1A };

    /**
     * Size of an iNES header containing various on the ROM. Default size is 12
     * bytes (offset : 0x0C).
     */
    private static final int INES_HEADER_SIZE = 0x0C;

    /**
     * Standard size of a program ROM bank. Default size is 16kB.
     */
    public static final int PROGRAM_BANK_SIZE = 0x4000;
    
    /**
     * Standard number of program ROM banks. Default number is 2.
     */
    public static final int PROGRAM_BANK_NUMBER = 2;

    /**
     * Standard size of a character ROM bank. Default size is 8kB.
     */
    public static final int CHARACTER_BANK_SIZE = 0x2000;

    /**
     * Private default constructor to prevent instantiation.
     */
    private CartridgeReader() {
    }

    public static Cartridge loadCartridge(final File romFile) throws IOException, UnknownRomFormatException {
        final FileInputStream fis = new FileInputStream(romFile);
        return loadCartridge(fis);
    }

    public static Cartridge loadCartridge(final InputStream is) throws IOException, UnknownRomFormatException {
        final Cartridge cart = new Cartridge();
        final BufferedInputStream bis = new BufferedInputStream(is);

        // Ensures that the file read has a known format
        if (validateNesRom(bis)) {
            // Loads all needed information in the Cartridge object
            loadRom(bis, cart);
            // Finally closes the reader
            bis.close();
        } else {
            bis.close();
            throw new UnknownRomFormatException();
        }

        return cart;
    }

    private static boolean validateNesRom(final BufferedInputStream bis) throws IOException {
        boolean result = false;
        byte[] readHeader = new byte[INES_VALIDATION_HEADER.length];

        // Reads the validation header
        bis.read(readHeader, 0, readHeader.length);
        if (Arrays.equals(readHeader, INES_VALIDATION_HEADER)) {
            result = true;
        }
        return result;
    }

    private static void loadRom(final BufferedInputStream bis, final Cartridge cartridge) throws IOException {
        // Reads the remaining information from the header
        final byte[] header = new byte[INES_HEADER_SIZE];
        bis.read(header, 0, INES_HEADER_SIZE);
        cartridge.setHeader(header);

        final int progRomPageCount = cartridge.getProgramRomPageCount();
        byte[][] programRom = new byte[PROGRAM_BANK_NUMBER][PROGRAM_BANK_SIZE];
        // Loads each program ROM bank
        for (int i = 0; i < progRomPageCount; i++) {
            bis.read(programRom[i], 0, PROGRAM_BANK_SIZE);
        }
        // If we have only one bank, set the bank at both bank addresses
        if (progRomPageCount == 1) {
            programRom[1] = programRom[0];
        }
        cartridge.setProgramRom(programRom);

        final int charRomPageCount = cartridge.getCharacterRomPageCount();
        byte[][] characterRom = new byte[charRomPageCount][CHARACTER_BANK_SIZE];
        // Loads each character ROM bank
        for (int i = 0; i < charRomPageCount; i++) {
            bis.read(characterRom[i], 0, CHARACTER_BANK_SIZE);
        }
        cartridge.setCharacterRom(characterRom);
    }

}
