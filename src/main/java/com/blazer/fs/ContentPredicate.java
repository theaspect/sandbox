package com.blazer.fs;

import com.google.common.base.Predicate;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Slf4j
public class ContentPredicate implements Predicate<File> {
    private FileService fileService;
    private String prefix;

    public ContentPredicate(FileService fileService, String prefix) {
        this.fileService = fileService;
        this.prefix = prefix;
    }

    @Override
    public boolean apply(@Nullable File file) {
        try (BufferedReader br = new BufferedReader(fileService.openReader(file))) {
            return prefix.equals(br.readLine());
        } catch (IOException ioe) {
            ContentPredicate.log.debug("Cannot read file " + file);
        }
        return false;
    }
}
