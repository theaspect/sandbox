package me.blzr.fp;

import java.util.function.Function;

import static me.blzr.fp.ZCommon.print;

public class BLambda {
    static Integer square(Integer i) {
        return i * i;
    }

    public static void main(String[] args) {
        print("Лямбды");

        // Вызов функции одного аргумента
        Function<Integer, Integer> funcSum = i -> i + 1;
        print(funcSum.apply(5)); // 6

        Function<Integer, Integer> funcMul = i -> i * 2;

        // Простая композиция двух функций
        Function<Integer, Integer> f0 = i -> funcMul.apply(funcSum.apply(i));
        print(f0.apply(5)); // (1 + x) * 2 = 12

        // Композиция F1(F2(x))
        Function<Integer, Integer> f1 = funcSum.andThen(funcMul);
        print(f1.apply(5)); // (1 + x) * 2 = 12

        // Композиция F2(F1(x))
        Function<Integer, Integer> f2 = funcSum.compose(funcMul);
        print(f2.apply(5)); // 1 + (5 * 2) = 11

        // Ссылка на метод
        Function<Integer, Integer> sq = BLambda::square;
        print(sq.apply(5));
    }
}
