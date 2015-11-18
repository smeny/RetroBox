/**
 * Decoder
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
package org.smeny.retrobox.motherboard.nes.processor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Decoder extends AbstractDecoder {
  /** Highest value an operation code can have. */
  private static final int HIGHER_OPCODE = 0xFF;
  /** Base use for parsing an hexadecimal value. */
  private static final int HEX_PARSE_BASE = 16;
  /** Separator used in our property file. */
  private static final String OPCODE_SEPARATOR = ";";
  /**
   * Property file containing all operation codes with associated
   * enumerations.
   */
  private static final String OPCODE_FILE = "opcodes/2A03.properties";
  /** Our default logger for this class. */
  private static final Logger LOGGER = Logger.getLogger(Decoder.class.getName());

  public Decoder() {
    super();
    initialise();
  }

  @Override
  protected void initialise() {
    final Properties properties = new Properties();
    final InputStream inputStream = Decoder.class.getClassLoader().getResourceAsStream(OPCODE_FILE);
    setOpcodeTable(new OperationCode[HIGHER_OPCODE]);
    // We parse our property file to get all CPU operations
    try {
      properties.load(inputStream);
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Error during 2A03 decoder initialisation", e);
    }
    final OperationCode[] opcodeTable = new OperationCode[HIGHER_OPCODE];
    // We get each operation code
    for (final Enumeration<Object> keys = properties.keys(); keys.hasMoreElements(); ) {
      final String opcode = (String) keys.nextElement();
      final String[] dataTable = ((String) properties.get(opcode)).split(OPCODE_SEPARATOR);
      // Removes the 0x part for parsing
      final int index = Integer.parseInt(opcode.substring(2), HEX_PARSE_BASE);
      if (index >= 0 && index < HIGHER_OPCODE) {
        opcodeTable[index] = new OperationCode(opcode, dataTable[0], dataTable[1]);
      }
    }
    setOpcodeTable(opcodeTable);
  }

}
