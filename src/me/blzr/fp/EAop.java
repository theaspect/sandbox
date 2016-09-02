package me.blzr.fp;

import static me.blzr.fp.ZCommon.print;

public class EAop {
    public static void main(String[] args) {

        print("AOP");
        print(fib(40));

        //timeit
        //memoize

//        print(timeit(DAop::fib).apply(40));
//
//        print("Broken Memoize");
//        print(timeit(memoize(DAop::fib)).apply(40));
//
//        print("Memoize");
//        fibl = memoize(DAop.fibl);
//        print(timeit(fibl).apply(40));
    }

    static int fib(int i) {
        if (i <= 1) {
            return i;
        } else {
            return fib(i - 1) + fib(i - 2);
        }
    }

    //<editor-fold desc="Example">
    /*
    static <T, R> Function<T, R> timeit(Function<T, R> f) {
        return (T a) -> {
            long t1 = System.currentTimeMillis();
            R result = f.apply(a);
            long t2 = System.currentTimeMillis();
            print("Time: " + (t2 - t1));
            return result;
        };
    }

    static Function<Integer, Integer> fibl = (i) -> {
        if (i <= 1) {
            return i;
        } else {
            return DAop.fibl.apply(i - 1) + DAop.fibl.apply(i - 2);
        }
    };

    static <T, R> Function<T, R> memoize(Function<T, R> f) {
        HashMap<T,R> cache = new HashMap<>();
        return (T a) -> cache.computeIfAbsent(a, f);
    }
    */
    //</editor-fold>
}
