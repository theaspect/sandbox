package com.blazer.testing;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class DividerTest {
    @Test
    public void testDivision() throws Divider.DivisionByZeroException {
        Divider divider = new Divider(5f);
        assertEquals(2.5f, divider.divide(2f));
    }

    @Test
    public void testZeroDividend() throws Divider.DivisionByZeroException {
        Divider divider = new Divider(0f);
        assertEquals(0f, divider.divide(5f));
    }

    @Test(expected = Divider.DivisionByZeroException.class)
    public void testDivisionByZero() throws Divider.DivisionByZeroException {
        Divider divider = new Divider(5f);
        divider.divide(0f);
    }
}
