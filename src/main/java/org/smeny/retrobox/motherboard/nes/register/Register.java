/**
 * Register
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
package org.smeny.retrobox.motherboard.nes.register;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a 8 or 16 bits register that can be accessed and modified.
 *
 * @author Stéphane Meny
 */
public final class Register {
  /** Our default logger for this class. */
  private static final Logger LOGGER = Logger.getLogger(Register.class.getName());

  /**
   * Mask used to get the first byte from the register.
   */
  private static final int FIRST_BYTE_MASK = 0xFF;

  /**
   * Mask used to get the second byte from the register.
   */
  private static final int SECOND_BYTE_MASK = 0xFF00;

  /**
   * Shift used to move the second byte on the first byte.
   */
  private static final int SECOND_BYTE_SHIFT = 8;

  /**
   * Mask used to insure that value of register will never be more than two bytes long.
   */
  private static final int TWO_BYTES_MASK = 0xFFFF;

  private static final int EIGHT_BITS_SIZE = 8;

  private static final int SIXTEEN_BITS_SIZE = 16;

  private final int registerSize;

  /**
   * The register's data represented as a integer. Integer primary type is used to optimise further
   * computations since in Java all computations are done on integers.
   */
  private int registerData;

  /**
   * Default constructor which initializes the register's data to 0.
   */
  private Register(int size) {
    if (size == EIGHT_BITS_SIZE || size == SIXTEEN_BITS_SIZE) {
      registerSize = size;
      registerData = 0;
    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Default constructor which initializes the register's data to given address.
   */
  private Register(int address, int size) {
    this(size);
    registerData = address;
  }

  public static Register getRegister8() {
    return new Register(EIGHT_BITS_SIZE);
  }

  public static Register getRegister16() {
    return new Register(SIXTEEN_BITS_SIZE);
  }

  /**
   * Returns the data contained inside the register.
   *
   * @return The register's data as an integer.
   */
  public int getRegisterData() {
    int value = 0;
    if (registerSize == EIGHT_BITS_SIZE) {
      value = registerData & FIRST_BYTE_MASK;
    } else if (registerSize == SIXTEEN_BITS_SIZE) {
      value = registerData & TWO_BYTES_MASK;
    }
    return value;
  }

  /**
   * Returns the first ordered byte extracted from the register's data.
   *
   * @return The register's low byte.
   */
  public byte getRegisterFirstByte() {
    return (byte) (registerData & FIRST_BYTE_MASK);
  }

  /**
   * Returns the second ordered byte extracted from the register's data.
   *
   * @return The register's high byte.
   */
  public byte getRegisterSecondByte() {
    return (byte) ((registerData & SECOND_BYTE_MASK) >>> SECOND_BYTE_SHIFT);
  }

  /**
   * Sets the data to store inside the register.
   *
   * @param data The data to store as an integer.
   */
  public void setRegisterData(final int data) {
    if (registerSize == EIGHT_BITS_SIZE) {
      registerData = data & FIRST_BYTE_MASK;
    } else if (registerSize == SIXTEEN_BITS_SIZE) {
      registerData = data & TWO_BYTES_MASK;
    }
  }

  public void increment() {
    increment(1);
  }

  public void increment(final int times) {
    for (int i = 0; i < times; i++) {
      if (registerData < (1 << registerSize)) {
        registerData++;
      } else {
        LOGGER.log(Level.SEVERE, "Buffer overflow in register during increment");
        System.exit(1);
      }
    }
  }

  public void decrement() {
    decrement(1);
  }

  public void decrement(final int times) {
    for (int i = 0; i < times; i++) {
      if (registerData > 0) {
        registerData--;
      } else {
        LOGGER.log(Level.SEVERE, "Value under zero in register during decrement");
        System.exit(1);
      }
    }
  }

}
