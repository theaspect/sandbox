package com.blazer.fs;

import com.blazer.fs.ContentPredicate;
import com.blazer.fs.FileService;
import com.blazer.fs.NamePredicate;
import junit.framework.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class PredicateTest {
    @Test
    public void nameFilter() {
        NamePredicate namePredicate = new NamePredicate("^\\d+.*$");

        Assert.assertTrue(namePredicate.apply(new File("/test/test/000name.ext")));
        Assert.assertTrue(namePredicate.apply(new File("/test/test/000.ext")));
        Assert.assertFalse(namePredicate.apply(new File("/test/test/a000.ext")));
    }

    @Test
    public void contentFilter() throws FileNotFoundException {
        FileService fileService = Mockito.mock(FileService.class);

        ContentPredicate contentPredicate = new ContentPredicate(fileService, "TEST");

        File f = new File("test");
        Mockito.when(fileService.openReader(f)).thenReturn(new StringReader("aaa"));
        Assert.assertFalse(contentPredicate.apply(f));

        Mockito.when(fileService.openReader(f)).thenReturn(new StringReader(""));
        Assert.assertFalse(contentPredicate.apply(f));

        Mockito.when(fileService.openReader(f)).thenReturn(new StringReader("TEST\nblah-blah"));
        Assert.assertTrue(contentPredicate.apply(f));
    }
}
