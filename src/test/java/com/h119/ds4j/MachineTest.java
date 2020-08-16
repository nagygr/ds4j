package com.h119.ds4j;

import org.junit.Test;
import static org.junit.Assert.*;

public class MachineTest {
    @Test public void testReturnOne() {
        Machine classUnderTest = new Machine();
        assertTrue (
			"The program of a freshly created Machine should be empty",
			classUnderTest.isProgramEmpty()
		);
    }
}
