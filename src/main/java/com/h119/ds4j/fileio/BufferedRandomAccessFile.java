package com.h119.ds4j.fileio;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class BufferedRandomAccessFile {
	private final String fileName;
	private long position;
	private long fileLength;
	private final int bufferSize;
	private char[] buffer;

	private static final int DEFAULT_BUFFER_SIZE = 1 << 16;

	public BufferedRandomAccessFile(String fileName, int bufferSize) {
		this.fileName = fileName;
		position = 0;
		this.bufferSize = bufferSize;
		buffer = new char[bufferSize];
		fileLength = new File(fileName).length();

		readBuffer(buffer, position);
	}

	public BufferedRandomAccessFile(String fileName) {
		this(fileName, DEFAULT_BUFFER_SIZE);
	}

	private void readBuffer(char[] buf, long from) {
		try (
			BufferedReader reader = new BufferedReader(new FileReader(fileName), buf.length);
		)
		{
			if (!isIndexWithinBounds(from))
				throw new AssertionError(String.format(
					"Index out of bounds: %d", from
			));

			reader.skip(from);
			long charsRead = reader.read(buf);
			
			if (charsRead == -1)
				throw new AssertionError(String.format(
					"Index out of bounds (charsRead == -1): %d", from
				));
		}
		catch (IOException ioe) {
			throw new AssertionError(String.format("Error while reading the file: %s", ioe));
		}
	}

	public long getFileLength() {
		return fileLength;
	}

	public boolean isIndexWithinBounds(long index) {
		return index >= 0 && index < fileLength;
	}

	private boolean isIndexWithinBuffer(long index) {
		return index >= position && index < (position + bufferSize);
	}

	public char get(long index) {
		/*
		 * TODO: if index is out of bounds and it indexes an adjacent
		 * buffer, then there should be an overlap between the current
		 * buffer and the newly read one.
		 */
		if (!isIndexWithinBuffer(index)) {
			position = (index / bufferSize) * bufferSize;
			readBuffer(buffer, position);
		}

		return buffer[(int)(index - position)];
	}
}
