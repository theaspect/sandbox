package com.blazer.homework;

import junit.framework.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class DataReaderTest {

    @Test
    public void parseTest() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("a 123", "wrong", "c 321", null);

        Parser parser = mock(Parser.class);
        when(parser.parse("a 123")).thenReturn(new Domain("a", 123L));
        when(parser.parse("wrong")).thenReturn(null);
        when(parser.parse("c 321")).thenReturn(new Domain("c", 321L));

        Assert.assertEquals(new HashSet<Domain>() {{
            add(new Domain("a", 123L));
            add(new Domain("c", 321L));
        }}, new DataReader(parser).parse(reader));
    }

    @Test(expected = IOException.class)
    public void parseException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenThrow(new IOException());

        Parser parser = mock(Parser.class);

        new DataReader(parser).parse(reader);
    }
}
