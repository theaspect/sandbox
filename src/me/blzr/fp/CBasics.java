package me.blzr.fp;

import java.util.Arrays;
import java.util.List;

public class CBasics {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

//        print("Найдем четные числа (filter)");
//        List<Integer> evens = new ArrayList<>();
//        for (int i : numbers) {
//            if (i % 2 == 0)
//                evens.add(i);
//        }
//        print(evens);
//
//
//        print("Умножим каждое число (map)");
//        List<Integer> doubles = new ArrayList<>();
//        for (int i : numbers) {
//            doubles.add(i * 2);
//        }
//        print(doubles);
//
//
//        print("Найдем сумму чисел (reduce)");
//        int sum = 0;
//        for (int i : numbers) {
//            sum += i;
//        }
//        print(sum);
//
//
//        print("Найдем первые два числа больше 3");
//        int cnt = 0;
//        for (int i : numbers) {
//            if (i > 3) {
//                if (i % 2 == 0) {
//                    if (cnt <= 2) {
//                        cnt++;
//                        print(i);
//                    } else {
//                        break;
//                    }
//                }
//            }
//        }
//
//
//        print("Найдем первое четное число больше 3");
//        for (int i : numbers) {
//            if (i > 3) {
//                if (i % 2 == 0) {
//                    print(i);
//                    break;
//                }
//            }
//        }
//
//
//        print("Найдем первое четное число больше 10");
//        int res = -1;
//        for (int i : numbers) {
//            if (i > 10) {
//                if (i % 2 == 0) {
//                    res = i;
//                    break;
//                }
//            }
//        }
//        print(res);

        //<editor-fold desc="Example">
        /*
        print(numbers.stream().filter(i -> i % 2 == 0).collect(Collectors.toList()));
        print(numbers.stream().map(i -> i * 2).collect(Collectors.toList()));
        print(numbers.stream().reduce(0, (acc, i) -> acc + i));
        print(numbers.stream().reduce(0, Integer::sum));
        print(numbers.stream()
                .filter(i -> i > 3)
                .filter(i -> i % 2 == 0)
                .limit(2)
                .collect(Collectors.toList()));
        print(numbers.stream()
                .filter(i -> i > 3)
                .filter(i -> i % 2 == 0)
                .findFirst());
        print(numbers.stream()
                .filter(i -> i > 10)
                .filter(i -> i % 2 == 0)
                .findFirst()
                .orElse(-1));
        */
        //</editor-fold>
    }
}
