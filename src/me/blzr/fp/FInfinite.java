package me.blzr.fp;

import static me.blzr.fp.ZCommon.print;

public class FInfinite {
    public static void main(String[] args) {
        print("Ленивые вычисления");
        // range peek

        print("Бесконечные генераторы");

//        Infinite
//        FifSupplier
//        RandomSupplier

    //        <editor-fold desc="Description">
        /*
        print(
        IntStream.range(0, 50)
                .peek(System.out::println)
                .skip(10)
                .peek(System.out::println)
                .limit(10)
                .sum());

        print(Stream.iterate("", s -> s + "+").limit(3).collect(Collectors.joining("")));
        print(Stream.iterate("", s -> s + "+").limit(5).collect(Collectors.joining(",")));
        print(Stream.iterate("!", s -> s + "+").limit(10).collect(Collectors.joining(",")));
        print(Stream.generate(new FibSupplier()).skip(20).limit(10).collect(Collectors.toList()));
        new Random().ints().filter(i -> i > 0 && i < 100).limit(10).mapToObj(Integer::toString).forEach(System.out::println);
        */
        //</editor-fold>

    }

    //<editor-fold desc="Supplier">
    /*
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
    */
    //</editor-fold desc="Description">
}
