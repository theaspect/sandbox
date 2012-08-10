package com.blazer.homework;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class FileSelectorTest {

    @Test
    public void correct() throws FileNotFoundException {
        File file = mock(File.class);
        when(file.isDirectory()).thenReturn(true);
        when(file.list()).thenReturn(new String[]{"abc", "123", "asd"});

        assertEquals("123", new FileSelector(file).getMaxFile());
    }

    @Test
    public void correctWithPadding() throws FileNotFoundException {
        File file = mock(File.class);
        when(file.isDirectory()).thenReturn(true);
        when(file.list()).thenReturn(new String[]{"abc", "0321", "0123", "asd"});

        assertEquals("0321", new FileSelector(file).getMaxFile());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNull() {
        new FileSelector(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFile() {
        File file = mock(File.class);
        when(file.isDirectory()).thenReturn(false);

        new FileSelector(file);
    }

    @Test(expected = FileNotFoundException.class)
    public void testNotNumber() throws FileNotFoundException {
        File file = mock(File.class);
        when(file.isDirectory()).thenReturn(true);
        when(file.list()).thenReturn(new String[]{"abc", "cde"});

        new FileSelector(file).getMaxFile();
    }

    @Test(expected = FileNotFoundException.class)
    public void testEmptyDir() throws FileNotFoundException {
        File file = mock(File.class);
        when(file.isDirectory()).thenReturn(true);
        when(file.list()).thenReturn(new String[]{});

        new FileSelector(file).getMaxFile();
    }

    @Test(expected = FileNotFoundException.class)
    public void testLongName() throws FileNotFoundException {

        File file = mock(File.class);
        when(file.isDirectory()).thenReturn(true);
        when(file.list()).thenReturn(new String[]{"123456789012345678901234567890"});

        new FileSelector(file).getMaxFile();
    }
}
