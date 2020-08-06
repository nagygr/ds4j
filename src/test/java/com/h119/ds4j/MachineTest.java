package com.h119.ds4j;

import org.junit.Test;
import static org.junit.Assert.*;

public class MachineTest {
    @Test public void testReturnOne() {
        Machine classUnderTest = new Machine();
        assertTrue("returnOne should return one", classUnderTest.returnOne() == 1);
    }
}
