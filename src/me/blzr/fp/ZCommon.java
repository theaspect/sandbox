package me.blzr.fp;

public class ZCommon {
    static void sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void sleep(String text, int delay) {
        try {
            print("Begin "+text);
            Thread.sleep(delay);
            print("End "+text);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void print(Object obj) {
        System.out.println(obj);
    }

    static void assertTrue(boolean exp) {
        System.out.println(exp ? "OK" : "Fail");
    }

    @FunctionalInterface
    interface Function<R>{
        R apply();
    }
}
