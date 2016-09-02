package me.blzr.fp;

import java.util.Random;

import static me.blzr.fp.ZCommon.print;
import static me.blzr.fp.ZCommon.sleep;

public class DDelayed {

    public static void main(String[] args) {
        printIf(new Random().nextFloat() > 0.6, longCalc());
    }

    static void printIf(boolean cond, int value) {
        if (cond) print(value);
    }

    static int longCalc() {
        sleep("calc", 1000);
        return 42;
    }

    //<editor-fold desc="Example">
    /*
    static void printIfTrue2(boolean cond, Function<Integer> method) {
        if (cond) print(method.apply());
    }
    */
    //</editor-fold>
}
