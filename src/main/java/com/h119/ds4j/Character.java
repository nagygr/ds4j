package com.h119.ds4j;

public class Character {
	public static void instruction(Machine machine) {
		int returnAddress = machine.popStack();
		int position = machine.popStack();
		int characterSetIndex = machine.popStack();

		String characterSet = machine.getString(characterSetIndex);

		if (characterSet.indexOf(machine.getCharacter(position)) != -1) {
			machine.pushStack(position + 1);
			machine.pushStack(Machine.SUCCESS);
		}
		else {
			machine.pushStack(position);
			machine.pushStack(Machine.FAILURE);
		}

		machine.setInstructionPointer(returnAddress);
	}
}
