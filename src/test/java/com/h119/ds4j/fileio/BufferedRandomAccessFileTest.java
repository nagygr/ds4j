package com.h119.ds4j.fileio;

import java.util.Optional;

import org.junit.Test;

import static org.junit.Assert.*;

public class BufferedRandomAccessFileTest {
    @Test
	public void testGetCharacterAtIndex() {
		BufferedRandomAccessFile classUnderTest = new BufferedRandomAccessFile(
			"src/test/java/com/h119/ds4j/fileio/BufferedRandomAccessFileTest.java",
			10
		);
		assertTrue("The 26th character of this file is \"e\"", classUnderTest.get(25) == 'e');
    }

	@Test
	public void testAccents() {
		BufferedRandomAccessFile classUnderTest = new BufferedRandomAccessFile(
			"src/test/resources/test.txt",
			10
		);

		assertTrue("The 1st character of this file is \"á\"", classUnderTest.get(0) == 'á');
	}

	@Test
	public void testFileLength() {
		BufferedRandomAccessFile classUnderTest = new BufferedRandomAccessFile(
			"src/test/resources/test.txt",
			10
		);

		int length = classUnderTest.getFileLength().orElse(-1);
		System.out.format("The length of the file: %d\n", length);

		boolean eofReached = false;
		int i = 0;

		do {
			length = classUnderTest.getFileLength().orElse(-1);

			if (length == -1 || i < length) {
				System.out.print(classUnderTest.get(i));
				++i;
			}
			else eofReached = true;
		} while (!eofReached);

		System.out.println();

		assertTrue("The should have been scanned until its end", eofReached);
	}
}

