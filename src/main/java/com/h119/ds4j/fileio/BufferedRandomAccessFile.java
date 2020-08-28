package com.h119.ds4j.fileio;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class BufferedRandomAccessFile {
	private final String fileName;
	private int position;
	private final int bufferSize;
	private char[] buffer;
	private int fileLength;

	private static final int DEFAULT_BUFFER_SIZE = 1 << 16;

	public BufferedRandomAccessFile(String fileName, int bufferSize) {
		this.fileName = fileName;
		position = 0;
		this.bufferSize = bufferSize;
		buffer = new char[bufferSize];
		
		fileLength = -1;

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
				fileLength = from + charsRead;
			}
		}
		catch (IOException ioe) {
			throw new AssertionError(String.format("Error while reading the file: %s", ioe));
		}
	}

	private boolean isIndexWithinBuffer(int index) {
		return index >= position && index < (position + bufferSize);
	}

	public char get(int index) {
		if (!isIndexWithinBuffer(index)) {
			if (index >= position + bufferSize && index < position + (int)(1.9 * bufferSize)) {
				position = position + (int)(0.9 * bufferSize);
			}
			else if (index < position && index >= position - (int)(0.9 * bufferSize)) {
				position = Math.max(0, position - (int)(0.9 * bufferSize));
			}
			else {
				position = (index / bufferSize) * bufferSize;
			}

			readBuffer(buffer, position);
		}

		if (fileLength != -1 && index >= fileLength) {
			throw new IndexOutOfBoundsException(
				String.format(
					"Index out of bounds: %d (file size: %d)",
					index,
					fileLength
				)
			);
		}

		return buffer[(int)(index - position)];
	}
}
