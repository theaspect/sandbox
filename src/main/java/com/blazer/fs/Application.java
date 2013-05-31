package com.blazer.fs;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Ordering;

import java.io.File;

/**
 * Delete first five files
 * which name starts with number
 * ordered in ascending order
 * first line of which starts with SHIBBOLEET
 *
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class Application {
    // Dependency injection FTW
    private static FileService fileService = new FileService();
    public static final String PREFIX = "SHIBBOLEET";
    public static final String MASK = "^\\d+.*$";
    public static final int COUNT = 5;

    public static void main(String... args) {
        if (!(args.length > 0) || (!fileService.isDir(new File(args[0])))) {
            System.out.println("Usage java -cp sandbox.jar com.blazer.fs.Application /dir/with/files/");
            return;
        }

        delete(fileService, filter(fileService, new File(args[0]), COUNT, MASK, PREFIX));
    }

    /**
     * Delete list of files
     */
    public static void delete(FileService fileService, Iterable<File> files) {
        for (File f : files) {
            fileService.delete(f);
        }
    }

    /**
     * Filter
     */
    public static Iterable<File> filter(FileService fileService, File dir, int count, String mask, String prefix) {
        return FluentIterable
                .from(Ordering.natural().immutableSortedCopy(fileService.dir(dir)))
                .filter(new NamePredicate(mask))
                .filter(new ContentPredicate(fileService, prefix))
                .limit(count)
                .toImmutableList();
    }


}
