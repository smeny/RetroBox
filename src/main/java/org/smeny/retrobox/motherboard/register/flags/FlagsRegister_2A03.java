/**
 * FlagsRegister 2A03
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
package org.smeny.retrobox.motherboard.register.flags;

public final class FlagsRegister_2A03 extends AbstractFlagsRegister<Flags_2A03> {
    /**
     * It is the size of the CPU's status register. For the 2A03, the register
     * size is 8 bits (1 Byte).
     */
    private static final int STATUS_REGISTER_SIZE = 8;

	public FlagsRegister_2A03() {
		super(STATUS_REGISTER_SIZE, Flags_2A03.class);
	}
}
