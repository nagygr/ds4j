package com.h119.ds4j;

import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.eclipse.collections.impl.stack.mutable.primitive.IntArrayStack;
import org.eclipse.collections.impl.list.mutable.FastList;

import com.h119.ds4j.fileio.BufferedRandomAccessFile;

public class Machine {
	private IntArrayStack stack;
	private IntArrayList program;

	private int instructionPointer;

	private int farthestErrorPosition;
	private String farthestErrorCause;
	private String farthestErrorRule;

	private FastList<String> stringTable;

	private BufferedRandomAccessFile file;

	public Machine() {
		stack = new IntArrayStack();
		program = new IntArrayList();
		instructionPointer = 0;

		farthestErrorPosition = -1;
		farthestErrorCause = "";
		farthestErrorRule = "";

		stringTable = new FastList<>(20);

		file = null;
	}

	public void setFile(BufferedRandomAccessFile file) {
		this.file = file;
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

	public int popStack() {
		return stack.pop();
	}

	public boolean isStackEmpty() {
		return stack.isEmpty();
	}

}
