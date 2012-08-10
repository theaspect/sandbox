package com.blazer.homework;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class Parser {
    String delim;

    public Parser(String delim) {
        if (delim == null) {
            throw new IllegalArgumentException("Divider is null");
        }
        this.delim = delim;
    }

    public Domain parse(String line) {
        try {
            String[] cells = line.split(delim, 2);

            if (cells.length != 2 || cells[0].length() > 10) {
                return null;
            }

            return new Domain(cells[0], Long.valueOf(cells[1]));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
