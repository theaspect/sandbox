package com.blazer.homework;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class FileSelector {
    File dir;

    public FileSelector(File dir) {
        if ((dir == null) || !dir.isDirectory()) {
            throw new IllegalArgumentException("Not a directory " + dir);
        }
        this.dir = dir;
    }

    String getMaxFile() throws FileNotFoundException {
        Long max = 0L;
        String candidate = null;
        for (String f : dir.list()) {
            Long fileNum = null;
            try {
                fileNum = Long.valueOf(f);
            } catch (NumberFormatException e) {
                continue;
            }
            if (fileNum > max) {
                max = fileNum;
                candidate = f;
            }
        }

        if (candidate == null) {
            throw new FileNotFoundException("No file with number name was found in " + dir.getAbsolutePath());
        }
        return candidate;
    }
}
