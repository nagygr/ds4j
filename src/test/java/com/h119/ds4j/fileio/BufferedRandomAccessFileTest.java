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
}

