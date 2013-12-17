/**
 * InstructionSet
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
package org.smeny.retrobox.motherboard.processor.nes;

/**
 * An Instruction Set enumeration represents all possible instruction available
 * to the 6602 CPU.
 * 
 * @author Stéphane Meny
 */
public enum InstructionSet {
    /** Add Memory to Accumulator with Carry. */
    ADC,

    /** Logical AND (Memory with Accumulator). */
    AND,

    /** Shift Left One Bit (Memory or Accumulator). */
    ASL,

    /** Branch on Carry Clear. */
    BCC,

    /** Branch on Carry Set. */
    BCS,

    /** Branch on Result Zero. */
    BEQ,

    /** Test Bits in Memory with Accumulator. */
    BIT,

    /** Branch on Result Minus. */
    BMI,

    /** Branch on Result not Zero. */
    BNE,

    /** Branch on Result Plus. */
    BPL,

    /** Force Break. */
    BRK,

    /** Branch on Overflow Clear. */
    BVC,

    /** Branch on Overflow Set. */
    BVS,

    /** Clear Carry Flag. */
    CLC,

    /** Clear Decimal Mode. */
    CLD,

    /** Clear Interrupt Disable Bit. */
    CLI,

    /** Clear Overflow Flag. */
    CLV,

    /** Compare Memory and Accumulator. */
    CMP,

    /** Compare Memory and Index X. */
    CPX,

    /** Compare Memory and Index Y. */
    CPY,

    /** Decrement Memory by One. */
    DEC,

    /** Decrement Index X by One. */
    DEX,

    /** Decrement Index Y by One. */
    DEY,

    /** Exclusive-Or (Memory with Accumulator). */
    EOR,

    /** Increment Memory by One. */
    INC,

    /** Increment Index X by One. */
    INX,

    /** Increment Index Y by One. */
    INY,

    /** Jump to New Location. */
    JMP,

    /** Jump to New Location Saving Return Address. */
    JSR,

    /** Load Accumulator with Memory. */
    LDA,

    /** Load Index X with Memory. */
    LDX,

    /** Load Index Y with Memory. */
    LDY,

    /** Shift Right One Bit (Memory or Accumulator). */
    LSR,

    /** No Operation. */
    NOP,

    /** OR (Memory with Accumulator). */
    ORA,

    /** Push Accumulator on Stack. */
    PHA,

    /** Push Processor Status on Stack. */
    PHP,

    /** Pull Accumulator from Stack. */
    PLA,

    /** Pull Processor Status from Stack. */
    PLP,

    /** Rotate One Bit Left (Memory or Accumulator). */
    ROL,

    /** Rotate One Bit Right (Memory or Accumulator). */
    ROR,

    /** Return from Interrupt. */
    RTI,

    /** Return from Subroutine. */
    RTS,

    /** Subtract Memory from Accumulator with Borrow. */
    SBC,

    /** Set Carry Flag. */
    SEC,

    /** Set Decimal Mode. */
    SED,

    /** Set Interrupt Disable Status. */
    SEI,

    /** Store Accumulator in Memory. */
    STA,

    /** Store Index X in Memory. */
    STX,

    /** Store Index Y in Memory. */
    STY,

    /** Transfer Accumulator to Index X. */
    TAX,

    /** Transfer Accumulator to Index Y. */
    TAY,

    /** Transfer Stack Pointer to Index X. */
    TSX,

    /** Transfer Index X to Accumulator. */
    TXA,

    /** Transfer Index X to Stack Pointer. */
    TXS,

    /** Transfer Index Y to Accumulator. */
    TYA
}
