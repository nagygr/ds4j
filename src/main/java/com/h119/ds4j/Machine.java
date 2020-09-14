package com.h119.ds4j;

import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.eclipse.collections.impl.stack.mutable.primitive.IntArrayStack;
import org.eclipse.collections.impl.list.mutable.FastList;

import com.h119.ds4j.fileio.BufferedRandomAccessFile;

public class Machine {
	public static final class FarthestError {
		public int position;
		public int commandId;
		public int ruleIndex;

		public FarthestError() {
			position = -1;
			commandId = -1;
			ruleIndex = -1;
		}
	}

	public static final int END_OF_PROGRAM = -1;

	public static final int CHAR = 0;
	public static final int JMP = 1;
	public static final int PUSH = 2;
	public static final int POP = 3;
	public static final int PEEK = 4;
	public static final int PRS = 5; // Push Register to Stack
	public static final int PUR = 6; // PUsh Rule name to ruleNameStack
	public static final int POR = 7; // POp Rule name from ruleNameStack

	public static final int FAILURE = 0;
	public static final int SUCCESS = 1;

	private IntArrayStack stack;
	private IntArrayList program;

	private FarthestError farthestError;

	/**
	 * The stack for the rule name indices.
	 * Whenever a rule starts, it pushes its name
	 * index, and whenever it finishes, it pops it.
	 * The terminal rules can use this information for
	 * the failure information.
	 */
	private IntArrayStack ruleNameStack;

	private int instructionPointer;

	private FastList<String> stringTable;
	
	private IntArrayList registers;

	private BufferedRandomAccessFile file = null;

	private boolean endOfProgramReached = false;

	public Machine() {
		stack = new IntArrayStack();

		program = new IntArrayList(100);
		instructionPointer = 0;

		farthestError = new FarthestError();

		ruleNameStack = new IntArrayStack();

		stringTable = new FastList<>(50);

		registers = new IntArrayList(10);
	}

	public void setError(int position, int commandId, int ruleIndex) {
		if (position > farthestError.position) {
			farthestError.position = position;
			farthestError.commandId = commandId;
			farthestError.ruleIndex = ruleIndex;
		}
	}

	public void setFile(BufferedRandomAccessFile file) {
		this.file = file;
	}

	public void setEndOfProgramReached() {
		endOfProgramReached = true;
	}

	/**
	 * @return the index of the added instruction
	 */
	public int addInstruction(int value) {
		int newIndex = program.size();
		if (!program.add(value))
			throw new RuntimeException (
				String.format (
					"Could not add instruction (%d) to program at index (%d)",
					value,
					newIndex
				)
			);
		return newIndex;
	}

	public void setInstructionPointer(int value) {
		instructionPointer = value;
	}

	public void incrementInstructionPointerBy(int value) {
		instructionPointer += value;
	}

	public int getProgramLength() {
		return program.size();
	}

	public int getInstruction() {
		if (instructionPointer >= program.size())
			throw new RuntimeException (
				String.format (
					"The instruction pointer (%d) points out of the program (size: %d)",
					instructionPointer,
					program.size()
				)
			);
		return program.get(instructionPointer);
	}

	public char getCharacter(int index) {
		return file.get(index);
	}

	public boolean isProgramEmpty() {
		return program.isEmpty();
	}

	public void pushStack(int value) {
		stack.push(value);
	}

	/**
	 * @throws java.util.EmptyStackException if the stack is empty
	 */
	public int peekStack() {
		return stack.peek();
	}

	/**
	 * @throws java.lang.IllegalArgumentException when the index is
	 * out of range
	 */
	public int peekStackAt(int index) {
		return stack.peekAt(index);
	}

	public int popStack() {
		return stack.pop();
	}

	public boolean isStackEmpty() {
		return stack.isEmpty();
	}

	public int addString(String value) {
		int newIndex = stringTable.indexOf(value);

		if (newIndex == -1) {
			newIndex = stringTable.size();

			if (!stringTable.add(value)) {
				throw new RuntimeException (
					String.format (
						"Couldn't add string (%s) to the string table",
						value
					)
				);
			}
		}

		return newIndex;
	}

	public String getString(int index) {
		return stringTable.get(index);
	}

	public int getCurrentRuleNameIndex() {
		return ruleNameStack.peek();
	}

	public void run() {
		if (file == null) {
			throw new RuntimeException (
				"The file is null, the machine can not run without a file."
			);
		}

		stack.clear();
		stack.push(END_OF_PROGRAM); // return address signalling the end of the program
		stack.push(0); // the start position

		instructionPointer = 0;
		endOfProgramReached = false;

		while (!endOfProgramReached) {
			int instruction = program.get(instructionPointer);

			switch(instruction) {
				case CHAR:
					Character.instruction(this);
				break;

				case JMP:
				{
					int argument = program.get(instructionPointer + 1);
					instructionPointer = argument;
				}
				break;

				case PUSH:
				{
					int argument = program.get(instructionPointer + 1);
					stack.push(argument);
					instructionPointer += 2;
				}
				break;

				case POP:
				{
					stack.pop();
					instructionPointer += 1;
				}
				break;

				case PEEK:
				{
					int peekIndex = program.get(instructionPointer + 1);
					int register = program.get(instructionPointer + 2);
					registers.set(register, stack.peekAt(peekIndex));
					instructionPointer += 3;
				}
				break;

				case PRS:
				{
					int registerIndex = program.get(instructionPointer + 1);
					stack.push(registers.get(registerIndex));
					instructionPointer += 2;
				}
				break;

				case PUR:
				{
					int argument = program.get(instructionPointer + 1);
					ruleNameStack.push(argument);
					instructionPointer += 2;
				}
				break;

				case POR:
					ruleNameStack.pop();
					instructionPointer += 1;
			}
		}
	}

}
