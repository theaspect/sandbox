package com.blazer.homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DataReader {
    private Parser parser;

    public DataReader(Parser parser) {
        this.parser = parser;
    }

    public Set<Domain> parse(BufferedReader reader) throws IOException {
        Set<Domain> data = new HashSet<Domain>();

        String line;
        while ((line = reader.readLine()) != null) {
            Domain item = parser.parse(line);
            if (item != null) {
                data.add(item);
            }
        }

        return data;
    }
}
