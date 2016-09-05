package me.blzr.fp;

import java.util.HashMap;
import java.util.function.Function;

import static me.blzr.fp.ZCommon.print;

public class EAop {
    public static void main(String[] args) {

        print("AOP");
        // Вычисление 40 числа фиббоначи
        print(fib(40));

        // Можно обернуть вызов другой функции, например замерить время выполнения
        Function<Integer,Integer> fibbonaci = EAop::fib;
        Function<Integer,Integer> timedFibbonaci = timeit(fibbonaci);
        print(timedFibbonaci.apply(40));

        // Поскольку функция fib чистая мы можем закешировать результаты её вызовов
        // Но из-за того что функция рекурсивная, обернется только певвый вызов
        print("Broken Memoize");
        print(timeit(memoize(EAop::fib)).apply(40));

        // Правильная версия мемоизации, подменяются все вызовы функции,
        // которая хранится в переменной
        print("Memoize");
        fibl = memoize(EAop.fibl);
        print(timeit(fibl).apply(40));

        // Для более красивого кода, смотрите EMemoize
    }

    // Неоптимальная рекурсивная реализация вычисления числа Фиббоначи
    static int fib(int i) {
        if (i <= 1) {
            return i;
        } else {
            return fib(i - 1) + fib(i - 2);
        }
    }

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
            return EAop.fibl.apply(i - 1) + EAop.fibl.apply(i - 2);
        }
    };

    static <T, R> Function<T, R> memoize(Function<T, R> f) {
        HashMap<T,R> cache = new HashMap<>();
        return (T a) -> cache.computeIfAbsent(a, f);
    }
}
