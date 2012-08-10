package com.blazer.homework;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class ParserTest {

    public void testCorrect() {
        assertEquals(new Domain("a", 123L), new Parser(" ").parse("a 123"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNull() {
        new Parser(null);
    }

    @Test
    public void testNotNum() {
        assertNull(new Parser(" ").parse("a b2"));
    }

    @Test
    public void testLongString() {
        assertNull(new Parser(" ").parse("a b2 cd"));
    }

    @Test
    public void testShortString() {
        assertNull(new Parser(" ").parse("a"));
    }

    @Test
    public void testLongKey() {
        assertNull(new Parser(" ").parse("1234567890. 1234"));
    }

    @Test
    public void testLongLong() {
        assertNull(new Parser(" ").parse("1234567890 123456789012345678901234567890"));
    }
}
