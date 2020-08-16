package com.h119.ds4j;

import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.eclipse.collections.impl.stack.mutable.primitive.IntArrayStack;
import org.eclipse.collections.impl.list.mutable.FastList;

public class Machine {
	private IntArrayStack stack;
	private IntArrayList program;

	private int instructionPointer;

	private int farthestErrorPosition;
	private String farthestErrorCause;
	private String farthestErrorRule;

	private FastList<String> stringTable;

	public Machine() {
		stack = new IntArrayStack();
		program = new IntArrayList();
		instructionPointer = 0;

		farthestErrorPosition = -1;
		farthestErrorCause = "";
		farthestErrorRule = "";

		stringTable = new FastList<>(20);
	}

	public boolean isProgramEmpty() {
		return program.isEmpty();
	}

}
