package com.h119.ds4j;

public class Character {
	/**
	 * Realizes the machine instruction: CHAR.
	 * Takes the characterSet's index in the string table as
	 * argument and the current position from the stack.
	 * @param machine the machine
	 */
	public static void instruction(Machine machine) {
		machine.incrementInstructionPointerBy(1);
		int characterSetIndex = machine.getInstruction();

		int position = machine.popStack();

		String characterSet = machine.getString(characterSetIndex);

		if (characterSet.indexOf(machine.getCharacter(position)) != -1) {
			machine.pushStack(position + 1);
			machine.pushStack(Machine.SUCCESS);
		}
		else {
			machine.pushStack(position);
			machine.pushStack(Machine.FAILURE);
		}

		machine.incrementInstructionPointerBy(1);
	}
}
