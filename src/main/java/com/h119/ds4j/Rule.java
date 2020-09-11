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

		machine.addInstruction(Machine.PEEK); // get position from top of stack
		machine.addInstruction(0); // The top of the stack
		machine.addInstruction(0); // Copy to register no. 0

		machine.addInstruction(Machine.PUR);
		machine.addInstruction(nameIndex);

		//TODO: the rest of the rule command
		/*
		 * Problem: if it's a simple built-in command, then position
		 * doesn't even have to be popped and stored as the built-in can
		 * be directly called and the position is still on the top of
		 * the stack. But if it's another rule that's called, then the
		 * return address also has to be calculated and a call plus a
		 * return address needs to be added to the program.
		 *
		 * The real problem is that we can not distinguish at compile time
		 * between adding the entire code of the rule and adding just a call.
		 *
		 * Possible solution:
		 * 	-	we might need to branch with an instanceof and do a different thing
		 *		if it is a Rule
		 *	-	we will need to have a map of the already compiled rules and
		 * 		the places where we need to branch to them and the latter
		 *		will be filled in a second run (~linking)
		 *			-> the first container will be a map:
		 *					rule name index -> rule index in program
		 *			-> the second one will also be map:
		 *					rule name index -> position of branch command's argument
		 *			=> in the second run we will iterate over the second and:
		 *					program.set	(
		 *						position of branch command's argument,
		 *						rule index in program
		 *					);
		 */
	}
}
