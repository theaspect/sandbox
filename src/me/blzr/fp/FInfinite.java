package me.blzr.fp;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static me.blzr.fp.ZCommon.print;

public class FInfinite {
    public static void main(String[] args) {
        print("Бесконечные генераторы");

        // Цепочный метод peek позволяет подглядеть на текущий элемент
        print(
        IntStream.range(0, 50)
                .peek(System.out::println)
                .skip(10)
                .peek(System.out::println)
                .limit(10)
                .sum());

        // В бесконечный генератор, передается два аргумента: нулевой элемент, и лямбда для генерации следующего
        print(Stream.iterate("", s -> s + "+").limit(3).collect(Collectors.joining("")));
        print(Stream.iterate("", s -> s + "+").limit(5).collect(Collectors.joining(",")));
        print(Stream.iterate("!", s -> s + "+").limit(10).collect(Collectors.joining(",")));

        // Бесконечный генератор случайных чисел
        new Random().ints().filter(i -> i > 0 && i < 100).limit(10).mapToObj(Integer::toString).forEach(System.out::println);

        // Правильная реализация бесконечной последовательности Фиббоначи
        print(Stream.generate(new FibSupplier()).skip(20).limit(10).collect(Collectors.toList()));
    }

    static class FibSupplier implements Supplier<Integer> {
        int p0 = 0;
        int p1 = 1;

        @Override
        public Integer get() {
            int res = p0 + p1;
            p0 = p1;
            p1 = res;
            return res;
        }
    }
}
