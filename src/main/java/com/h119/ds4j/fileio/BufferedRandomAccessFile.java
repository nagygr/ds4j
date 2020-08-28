package com.h119.ds4j.fileio;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.Optional;

public class BufferedRandomAccessFile {
	private final String fileName;
	private int position;
	private final int bufferSize;
	private char[] buffer;
	private Optional<Integer> fileLength;

	private static final int DEFAULT_BUFFER_SIZE = 1 << 16;

	public BufferedRandomAccessFile(String fileName, int bufferSize) {
		this.fileName = fileName;
		position = 0;
		this.bufferSize = bufferSize;
		buffer = new char[bufferSize];
		
		fileLength = Optional.<Integer>empty();

		readBuffer(buffer, position);
	}

	public BufferedRandomAccessFile(String fileName) {
		this(fileName, DEFAULT_BUFFER_SIZE);
	}

	private void readBuffer(char[] buf, int from) {
		try (
			BufferedReader reader = new BufferedReader(new FileReader(fileName), buf.length);
		)
		{
			reader.skip(from);
			int charsRead = reader.read(buf);
			
			if (charsRead == -1) {
				throw new IndexOutOfBoundsException(String.format(
					"Index out of bounds (charsRead == -1): %d", from
				));
			}
			else if (charsRead < bufferSize) {
				fileLength = Optional.<Integer>of(from + charsRead);
			}
		}
		catch (IOException ioe) {
			throw new AssertionError(String.format("Error while reading the file: %s", ioe));
		}
	}

	public final Optional<Integer> getFileLength() {
		return fileLength;
	}

	private boolean isIndexWithinBuffer(int index) {
		return index >= position && index < (position + bufferSize);
	}

	public char get(int index) {
		/*
		 * TODO: if index is out of bounds and it indexes an adjacent
		 * buffer, then there should be an overlap between the current
		 * buffer and the newly read one.
		 */
		if (!isIndexWithinBuffer(index)) {
			position = (index / bufferSize) * bufferSize;
			readBuffer(buffer, position);
		}

		if (fileLength.isPresent() && index >= fileLength.get())
			throw new IndexOutOfBoundsException(
				String.format(
					"Index out of bounds: %d (file size: %d)",
					index,
					fileLength.get()
				)
			);

		return buffer[(int)(index - position)];
	}
}
