package com.java.fs;

import com.blazer.fs.Application;
import com.blazer.fs.FileService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class FullTest {

    @Test
    public void FilterTest() throws FileNotFoundException {
        FileService fileService = Mockito.mock(FileService.class);
        File dir = new File("/test/dir");
        List<File> files = Arrays.asList(
                new File(dir, "100file.name"), // 0
                new File(dir, "bl"), // 1
                new File(dir, "099file.name"), // 2
                new File(dir, "bla"), // 3
                new File(dir, "098file.name"), // 4
                new File(dir, "blah"), // 5
                new File(dir, "097file.name")); // 6
        Mockito.when(fileService.dir(dir)).thenReturn(files);
        Mockito.when(fileService.openReader(files.get(6))).thenReturn(new StringReader("TEST"));
        Mockito.when(fileService.openReader(files.get(4))).thenReturn(new StringReader("TEST"));
        Mockito.when(fileService.openReader(files.get(2))).thenReturn(new StringReader("!!!!"));
        Mockito.when(fileService.openReader(files.get(0))).thenReturn(new StringReader("TEST"));

        Iterable<File> result = Application.filter(fileService, dir, 3, "^\\d+.*$", "TEST");
        Iterator<File> i = result.iterator();
        Assert.assertEquals(files.get(6), i.next());
        Assert.assertEquals(files.get(4), i.next());
        Assert.assertEquals(files.get(0), i.next());
        Assert.assertFalse(i.hasNext());
    }
}
