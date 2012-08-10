package com.blazer.testing;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class Divider {
    private float dividend;

    public Divider(float value) {
        this.dividend = value;
    }

    public float divide(float divisor) throws DivisionByZeroException {
        if (divisor == 0) {
            throw new DivisionByZeroException();
        }

        return dividend / divisor;
    }

    public static class DivisionByZeroException extends Exception {
    }
}
