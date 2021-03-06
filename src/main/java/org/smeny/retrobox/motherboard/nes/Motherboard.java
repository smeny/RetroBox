/**
 * Motherboard
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
package org.smeny.retrobox.motherboard.nes;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.smeny.retrobox.exception.ReadOutOfMemoryException;
import org.smeny.retrobox.exception.UnknownRomFormatException;
import org.smeny.retrobox.exception.WriteOutOfMemoryException;
import org.smeny.retrobox.motherboard.nes.cartridge.Cartridge;
import org.smeny.retrobox.motherboard.nes.cartridge.CartridgeReader;
import org.smeny.retrobox.motherboard.nes.processor.Core_2A03;

/**
 * This class represents the Nintendo Entertainment System motherboard. All components will be
 * mapped under this package.
 */
public final class Motherboard {
  /** Our default logger for this class. */
  private static final Logger LOGGER = Logger.getLogger(Motherboard.class.getName());
  /**
   * Represents the maximum number of clock cycle the CPU will perform before stopping.
   */
  private static final long CLOCK_CYCLE = 50;

  /** Default private constructor. */
  private Motherboard() {
  }

  /**
   * Start point of the NES emulation.
   *
   * @param args Arguments given to the main function
   */
  public static void main(final String[] args) {
    final InputStream inputStream = Motherboard.class.getResourceAsStream("/roms/cpu_test.nes");
    System.setProperty("java.util.logging.config.file", "logging.properties");
    try {
      final Cartridge cart = CartridgeReader.loadCartridge(inputStream);
      final Core_2A03 cpu = new Core_2A03(cart);
      cpu.emulate(CLOCK_CYCLE);
    } catch (UnknownRomFormatException | IOException e) {
      LOGGER.log(Level.SEVERE, "Exception reading cartridge", e);
    } catch (ReadOutOfMemoryException | WriteOutOfMemoryException e) {
      LOGGER.log(Level.SEVERE, "Exception during cpu execution", e);
    }
  }
}
