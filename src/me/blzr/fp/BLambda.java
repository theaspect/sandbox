package me.blzr.fp;

import static me.blzr.fp.ZCommon.print;

public class BLambda {
    static Integer square(Integer i) {
        return i * i;
    }

    public static void main(String[] args) {
        print("Лямбды");

        // sum
        // apply
        // mul
        // f1(f2)
        // andthen
        // compose
        // method ref

        //<editor-fold desc="Example">
        //        Function<Integer, Integer> funcSum = i -> i + 1;
        //        print(funcSum.apply(5)); // 6
        //
        //        Function<Integer, Integer> funcMul = i -> i * 2;
        //
        //        Function<Integer, Integer> f0 = i -> funcMul.apply(funcSum.apply(i));
        //        print(f0.apply(5)); // (1 + x) * 2 = 12
        //
        //        Function<Integer, Integer> f1 = funcSum.andThen(funcMul);
        //        print(f1.apply(5)); // (1 + x) * 2 = 12
        //
        //        Function<Integer, Integer> f2 = funcSum.compose(funcMul);
        //        print(f2.apply(5)); // 1 + (5 * 2) = 11
        //
        //        print("Ссылка на метод");
        //        Function<Integer, Integer> sq = ALambda::square;
        //        print(sq.apply(5));
        //</editor-fold>
    }
}
