package com.blazer.fs;

import com.google.common.base.Predicate;

import javax.annotation.Nullable;
import java.io.File;
import java.util.regex.Pattern;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class NamePredicate implements Predicate<File> {
    private Pattern pattern;

    /**
     * Filter by name
     *
     * @param mask regex
     */
    public NamePredicate(String mask) {
        pattern = Pattern.compile(mask);
    }

    @Override
    public boolean apply(@Nullable File file) {
        return pattern.matcher(file.getName()).matches();
    }
}
