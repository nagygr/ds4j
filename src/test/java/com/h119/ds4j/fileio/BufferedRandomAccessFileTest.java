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

		boolean eofReached = false;

		for (int i = 0; !eofReached; ++i) {
			try {
				System.out.print(classUnderTest.get(i));
			}
			catch (IndexOutOfBoundsException ioobe) {
				eofReached = true;
			}
		}

		System.out.println();

		assertTrue("The file should have been scanned until its end", eofReached);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testTooBigIndex() {
		BufferedRandomAccessFile classUnderTest = new BufferedRandomAccessFile(
			"src/test/resources/test.txt",
			50
		);

		char c = classUnderTest.get(46);
	}
}

