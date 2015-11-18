/**
 * BasicMemoryController
 *
 * Copyright 2013 Stéphane MENY
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.smeny.retrobox.motherboard.nes.memory;

import org.smeny.retrobox.exception.ReadOutOfMemoryException;
import org.smeny.retrobox.exception.WriteOutOfMemoryException;
import org.smeny.retrobox.motherboard.nes.cartridge.Cartridge;
import org.smeny.retrobox.motherboard.nes.cartridge.CartridgeReader;
import org.smeny.retrobox.motherboard.nes.memory.AbstractMemoryController;

/**
 * The class BasicMemoryController will handle access and write into memory. Note that memories
 * offsets are coded onto 16 bits (2 Bytes), which means that the maximum allowed (accessible) size
 * is 64KB (0xFFFF). This type do not handle memory banks switching, so it cannot handle a ROM with
 * a program ROM size higher than 32kB.
 *
 * @author Stéphane Meny
 */
public class BasicMemoryController extends AbstractMemoryController {

  /**
   * Default constructor based on the MemoryController one.
   *
   * @param progRom The Program ROM that the controller have to handle.
   * @param charRom The Character ROM that the controller have to handle.
   */
  public BasicMemoryController(final byte[][] progRom, final byte[][] charRom) {
    super(progRom, charRom);
  }

  /**
   * Constructor using a cartridge.
   */
  public BasicMemoryController(final Cartridge cart) {
    super(cart.getProgramRom(), cart.getCharacterRom());
  }

  /**
   * Reads the memory at the specified offset and returns the value found. This method checks if the
   * access is done on the allocated memory, the first program ROM bank or the second program ROM
   * bank that is accessed.
   *
   * @param offset The offset where is the data to read.
   * @return The value read in memory.
   * @throws ReadOutOfMemoryException If the offset is incorrect.
   * @see AbstractMemoryController#readMemory(int)
   */
  @Override
  public final int readMemory(final int offset) throws ReadOutOfMemoryException {
    int byteRead = BYTE_MASK;
    final int secondBankOffset = MEMORY_SIZE + CartridgeReader.PROGRAM_BANK_SIZE;

    if (isMemoryAccessible(offset)) {
      if (!isMemoryReadOnly(offset)) {
        // We are not in the ROM
        byteRead &= getMemoryTable()[offset];
      } else if (offset < secondBankOffset) {
        // We are in PRG ROM first bank
        final int prgOffset = offset - MEMORY_SIZE;
        byteRead &= getProgramRom()[0][prgOffset];
      } else {
        // We are in PRG ROM second bank
        final int prgOffset = offset - secondBankOffset;
        byteRead &= getProgramRom()[1][prgOffset];
      }
    } else {
      throw new ReadOutOfMemoryException(offset, MEMORY_TAG);
    }

    return byteRead;
  }

  /**
   * Writes the specified data into memory at the specified offset.
   *
   * @param offset The offset where is the data to read.
   * @param data   The value to write in memory.
   * @throws WriteOutOfMemoryException If the offset is incorrect.
   * @see AbstractMemoryController#writeMemory(int, int)
   */
  @Override
  public final void writeMemory(final int offset, final int data) throws WriteOutOfMemoryException {
    if (isMemoryAccessible(offset) && !isMemoryReadOnly(offset)) {
      getMemoryTable()[offset] = (byte) (data & BYTE_MASK);
    } else {
      throw new WriteOutOfMemoryException(offset, data, MEMORY_TAG);
    }
  }

  /**
   * Reads the video memory at the specified offset and returns the value found. This method checks
   * if it is the allocated video memory, the first character ROM bank or the second character ROM
   * bank that is accessed.
   *
   * @param offset The offset where is the data to read.
   * @return The value read in video memory.
   * @throws ReadOutOfMemoryException If the offset is incorrect.
   * @see AbstractMemoryController#readVideoMemory(int)
   */
  @Override
  public final int readVideoMemory(final int offset) throws ReadOutOfMemoryException {
    int byteRead = BYTE_MASK;
    final int secondBankOffset = VIDEO_MEMORY_SIZE + CartridgeReader.CHARACTER_BANK_SIZE;

    if (isVideoMemoryAccessible(offset)) {
      if (!isVideoMemoryReadOnly(offset)) {
        byteRead &= getVideoMemoryTable()[offset];
      } else if (offset < secondBankOffset) {
        byteRead &= getCharacterRom()[0][offset];
      } else {
        byteRead &= getCharacterRom()[1][offset];
      }
    } else {
      throw new ReadOutOfMemoryException(offset, VIDEO_MEMORY_TAG);
    }

    return byteRead;
  }

  /**
   * Writes the specified data into video memory at the specified offset. The method checks if the
   * offset is in the allocated video memory.
   *
   * @param offset The offset where is the data to read.
   * @param data   The value to write in video memory.
   * @throws WriteOutOfMemoryException If the offset is incorrect.
   * @see AbstractMemoryController#writeVideoMemory(int, int)
   */
  @Override
  public final void writeVideoMemory(final int offset, final int data)
      throws WriteOutOfMemoryException {
    if (isVideoMemoryAccessible(offset) && !isVideoMemoryReadOnly(offset)) {
      getVideoMemoryTable()[offset] = (byte) (data & BYTE_MASK);
    } else {
      throw new WriteOutOfMemoryException(offset, data, VIDEO_MEMORY_TAG);
    }
  }

}
