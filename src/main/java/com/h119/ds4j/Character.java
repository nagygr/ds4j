package com.h119.ds4j;

public class final Character implements Compilable {
	private String characterSet;

	public Character(String characterSet) {
		this.characterSet = characterSet;
	}

	@Override
	public void compile(Machine machine) {
		int stringIndex = machine.addString(characterSet);

		machine.addInstruction(Machine.CHAR);
		machine.addInstruction(stringIndex);
	}

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
			machine.setError(position, Machine.CHAR, Machine.getCurrentRuleNameIndex());
			machine.pushStack(position);
			machine.pushStack(Machine.FAILURE);
		}

		machine.incrementInstructionPointerBy(1);
	}
}
