package com.h119.ds4j;

import org.junit.Test;
import static org.junit.Assert.*;

public class MachineTest {
    @Test
	public void testFreshMachineProgramEmpty() {
        Machine classUnderTest = new Machine();
        assertTrue (
			"The program of a freshly created Machine should be empty",
			classUnderTest.isProgramEmpty()
		);
    }

	@Test
	public void testFreshMachineStackEmpty() {
		Machine classUnderTest = new Machine();
		assertTrue (
			"The stack of a freshly created Machine should be empty",
			classUnderTest.isStackEmpty()
		);
	}

	@Test(expected = RuntimeException.class)
	public void testFreshMachineGetInstructionOutOfBounds() {
		Machine classUnderTest = new Machine();
		classUnderTest.setInstructionPointer(100);
		// Throws RuntimeException due to an out of bounds index
		int instructionAt100 = classUnderTest.getInstruction();
	}
}
