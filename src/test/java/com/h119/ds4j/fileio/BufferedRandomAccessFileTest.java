package com.h119.ds4j.fileio;

import java.nio.file.FileSystems;
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
			"test_data/test.txt",
			10
		);

		long length = classUnderTest.getFileLength();
		System.out.format("The length of the file: %d\n", length);

		for (long i = 0; i < length; ++i) {
			System.out.print(classUnderTest.get(i));
		}

		System.out.println();

		assertTrue("The 1st character of this file is \"á\"", classUnderTest.get(0) == 'á');
	}
}

