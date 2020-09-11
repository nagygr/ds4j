package com.h119.ds4j;

public final class Rule implements Compilable {
	private String name;
	private Compilable innerRule;

	public Rule(String name) {
		this.name = name;
		this.innerRule = null;
	}

	public void set(Compilable innerRule) {
		this.innerRule = innerRule;
	}
	
	@Override
	public void compile(Machine machine) {
		if (innerRule == null)
			throw new RuntimeException (
				String.format(
					"The inner rule of rule %s has not been set.",
					name
				)
			);

		int nameIndex = machine.addString(name);

		machine.addInstruction(Machine.PUR);
		machine.addInstruction(nameIndex);

		//TODO: Find out how the initial position should be handled.
		//machine.addInstruction(Machine.PUSH);
		//machine.addInstruction(machine.
		//TODO: the rest of the rule command
	}
}
