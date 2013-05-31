package com.blazer.fs;

import lombok.extern.log4j.Log4j;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Log4j
public class FileService {
    /**
     * Get files in path
     */
    public Collection<File> dir(File path) {
        return Arrays.asList(path.listFiles());
    }

    /**
     * Delete path
     */
    public void delete(File file) {
        if (!file.delete()) {
            log.debug("Cannot delete " + file.getPath());
        }
    }

    /**
     * Open file
     */
    public InputStream openStream(File path) throws FileNotFoundException {
        return new FileInputStream(path);
    }

    public Reader openReader(File path) throws FileNotFoundException {
        return new FileReader(path);
    }

    public boolean isDir(File path) {
        return path.isDirectory();
    }
}
