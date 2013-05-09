package org.javanes.motherboard.memory;

import org.javanes.exception.ReadOutOfMemoryException;
import org.javanes.exception.WriteOutOfMemoryException;

/**
 * The abstract MemoryController defines the representation of a memory
 * controller. A memory controller is responsible for data accessing and storing
 * both specified by a given offset.
 * @author Stéphane Meny
 *
 */
public abstract class AbstractMemoryController {
	/**
	 * Highest offset reachable in memory by the CPU.
	 * (since the program counter has a size of 16 bits)
	 */ 
	protected static final int HIGHEST_MEMORY_OFFSET = 0xFFFF;
	
	/**
	 * Highest offset reachable in video memory by the PPU.
	 */
	protected static final int HIGHEST_VIDEO_MEMORY_OFFSET = 0x4000;
	
	/**
	 * Size of memory without the program ROM.
	 * It is also the offset where the program code starts in memory.
	 */
	protected static final int MEMORY_SIZE = 0x8000;
	
	/**
	 * Size of video memory without the character ROM.
	 * It is also the offset where the graphics data ends in memory.
	 */
	protected static final int VIDEO_MEMORY_SIZE = 0x2000;
	
	/**
	 * This mask is used to convert a byte type value into an integer value
	 * and vice-versa. It removes the signed part of an integer type.
	 */
	protected static final int BYTE_MASK = 0xFF;
	
	/**
	 * String used to specify that central memory have been accessed during
	 * an exception.
	 */
	protected static final String MEMORY_TAG = "Memory";
	
	/**
	 * String used to specify that video memory have been accessed during
	 * an exception.
	 */
	protected static final String VIDEO_MEMORY_TAG = "Video Memory";
	
	/**
	 * Table containing all the data (bytes) of the memory. It is accessed
	 * by the Central Processing Unit.
	 */
	private byte[] memoryTable;
	
	/**
	 * Table where all the graphics related data are stored.
	 * This table should be accessed only by the Picture Processing Unit.
	 */
	private byte[] videoMemoryTable;
	
	/**
	 * Table resulting from the cartridge load. It contains all the instructions
	 * that will be processed by the Central Processing Unit.
	 */
	private byte[][] programRom;
	
	/**
	 * Table resulting from the cartridge load. It contains all the graphics
	 * data needed by the Picture Processing Unit which handles display.
	 */
	private byte[][] characterRom;
	
	
	/**
	 * Default constructor for a MemoryController type.
	 * The defined size of both memory and video memory table are used.
	 * @param progRom The Program ROM that the controller have to handle.
	 * @param charRom The Character ROM that the controller have to handle.
	 */
	public AbstractMemoryController(
			final byte[][] progRom, final byte[][] charRom) {
		memoryTable = new byte[MEMORY_SIZE];
		videoMemoryTable = new byte[VIDEO_MEMORY_SIZE];
		programRom = progRom;
		characterRom = charRom;
	}
	
	/**
	 * Default getter used by the implemented memory controller types.
	 * It will return the central memory table handled by the CPU.
	 * @return Program data in memory as a table.
	 */
	protected final byte[] getMemoryTable() {
		return memoryTable;
	}
	
	/**
	 * Default getter used by the implemented memory controller types.
	 * It will return the video memory table handled by the PPU.
	 * @return Graphics data in memory as a table.
	 */
	protected final byte[] getVideoMemoryTable() {
		return videoMemoryTable;
	}
	
	/**
	 * Default getter used by the implemented memory controller types.
	 * Returns the program ROM read in the cartridge. 
	 * @return The read program ROM as a double sized table.
	 */
	protected final byte[][] getProgramRom() {
		return programRom;
	}

	/**
	 * Default getter used by the implemented memory controller types.
	 * Returns the character ROM read in the cartridge. 
	 * @return The read character ROM as a double sized table.
	 */
	protected final byte[][] getCharacterRom() {
		return characterRom;
	}
	
	/**
	 * Indicates if the offset in memory is accessible by the CPU.
	 * @param offset The offset to access.
	 * @return true is the offset is reachable, false otherwise.
	 */
	public final boolean isMemoryAccessible(final int offset) {
		return (offset >= 0) && (offset < HIGHEST_MEMORY_OFFSET);
	}
	
	/**
	 * Indicates if the offset in video memory is accessible by the PPU.
	 * @param offset The offset to access.
	 * @return true is the offset is reachable, false otherwise.
	 */
	public final boolean isVideoMemoryAccessible(final int offset) {
		return (offset >= 0) && (offset < HIGHEST_VIDEO_MEMORY_OFFSET);
	}
	
	/**
	 * Indicates if the offset is not outside the allocated memory.
	 * @param offset The offset to access.
	 * @return true is the offset is reachable, false otherwise.
	 */
	public final boolean isMemoryReadOnly(final int offset) {
		return (offset >= MEMORY_SIZE);
	}
	
	/**
	 * Indicates if the offset is not outside the allocated video memory.
	 * @param offset The offset to access.
	 * @return true is the offset is reachable, false otherwise.
	 */
	public final boolean isVideoMemoryReadOnly(final int offset) {
		return (offset >= VIDEO_MEMORY_SIZE);
	}
	
	/**
	 * Defines the method used when the memory have to be accessed.
	 * @param offset The offset where are located the data to read
	 * @return The data read in memory as a byte.
	 * @throws ReadOutOfMemoryException If the offset specified is out of
	 * memory or negative.
	 */
	public abstract int readMemory(final int offset)
	throws ReadOutOfMemoryException;
	
	/**
	 * Defines the method used when the video memory have to be accessed.
	 * @param offset The offset where are located the data to read
	 * @return The data read in memory as a byte.
	 * @throws ReadOutOfMemoryException If the offset specified is out of
	 * memory or negative.
	 */
	public abstract int readVideoMemory(final int offset)
	throws ReadOutOfMemoryException;
	
	/**
	 * Defines the method used when data have to be stored in memory.
	 * @param offset The offset where data will be stored.
	 * @param data The data to store as an integer.
	 * @throws WriteOutOfMemoryException If the offset specified is out of
	 * memory or negative.
	 */
	public abstract void writeMemory(final int offset, final int data)
	throws WriteOutOfMemoryException;
	
	/**
	 * Defines the method used when data have to be stored in video memory.
	 * @param offset The offset where data will be stored.
	 * @param data The data to store as an integer.
	 * @throws WriteOutOfMemoryException If the offset specified is out of
	 * memory or negative.
	 */
	public abstract void writeVideoMemory(final int offset, final int data)
	throws WriteOutOfMemoryException;

}
