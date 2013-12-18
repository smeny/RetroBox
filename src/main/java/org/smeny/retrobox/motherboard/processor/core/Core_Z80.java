/**
 * Core Z80
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
package org.smeny.retrobox.motherboard.processor.core;

import java.util.logging.Logger;

import org.smeny.retrobox.motherboard.memory.AbstractMemoryController;
import org.smeny.retrobox.motherboard.register.Register;
import org.smeny.retrobox.motherboard.register.flags.FlagsRegister_Z80;

/**
 * This class represents a Zilog 80 Central Processing Unit. The CPU is Little Endian, this means that the least significant byte (LSB) will
 * be stored in the lowest memory address.
 * 
 * @author Stéphane Meny
 */
@SuppressWarnings("unused")
public final class Core_Z80 extends AbstractCore {

	/** Our default logger for this class. */
	private static final Logger LOGGER = Logger.getLogger(Core_Z80.class.getName());

	/** The accumulator where data are read/stored. */
	private final Register regAcc;
	/** The status register where each resulting flag is stored. */
	private final FlagsRegister_Z80 regFlag;
	private final Register regBC;
	private final Register regDE;
	private final Register regHL;
	private final Register regIX;
	private final Register regIY;
	private final Register regInterrupt;
	private final Register regRefresh;

	private final Register shadowAcc;
	private final FlagsRegister_Z80 shadowFlag;
	private final Register shadowBC;
	private final Register shadowDE;
	private final Register shadowHL;

	private final Register programCounter;
	private final Register stackPointer;

	/** Not final at the moment because of possible bank switching. */
	private AbstractMemoryController memory;

	public Core_Z80(AbstractMemoryController memory) {
		regAcc = Register.getRegister8();
		regFlag = new FlagsRegister_Z80();
		regBC = Register.getRegister16();
		regDE = Register.getRegister16();
		regHL = Register.getRegister16();
		regIX = Register.getRegister16();
		regIY = Register.getRegister16();
		regInterrupt = Register.getRegister8();
		regRefresh = Register.getRegister8();

		shadowAcc = Register.getRegister8();
		shadowFlag = new FlagsRegister_Z80();
		shadowBC = Register.getRegister16();
		shadowDE = Register.getRegister16();
		shadowHL = Register.getRegister16();

		programCounter = Register.getRegister16();
		stackPointer = Register.getRegister16();

		this.memory = memory;
	}

}
