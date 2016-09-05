package me.blzr.fp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static me.blzr.fp.ZCommon.print;

public class CBasics {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        print("Найдем четные числа (filter)");
        List<Integer> evens = new ArrayList<>();
        for (int i : numbers) {
            if (i % 2 == 0)
                evens.add(i);
        }
        print(evens);
        // Функциональный подход
        print(numbers.stream().filter(i -> i % 2 == 0).collect(Collectors.toList()));


        print("Умножим каждое число (map)");
        List<Integer> doubles = new ArrayList<>();
        for (int i : numbers) {
            doubles.add(i * 2);
        }
        print(doubles);
        // Функциональный подход
        print(numbers.stream().map(i -> i * 2).collect(Collectors.toList()));


        print("Найдем сумму чисел (reduce)");
        int sum = 0;
        for (int i : numbers) {
            sum += i;
        }
        print(sum);
        // Функциональный подход
        print(numbers.stream().reduce(0, (acc, i) -> acc + i));
        // То же самое, но ссылка на метод
        print(numbers.stream().reduce(0, Integer::sum));


        print("Найдем первые два числа больше 3");
        int cnt = 0;
        for (int i : numbers) {
            if (i > 3) {
                if (i % 2 == 0) {
                    if (cnt <= 2) {
                        cnt++;
                        print(i);
                    } else {
                        break;
                    }
                }
            }
        }
        // Функциональный подход
        print(numbers.stream()
                .filter(i -> i > 3)
                .filter(i -> i % 2 == 0)
                .limit(2)
                .collect(Collectors.toList()));


        print("Найдем первое четное число больше 3");
        for (int i : numbers) {
            if (i > 3) {
                if (i % 2 == 0) {
                    print(i);
                    break;
                }
            }
        }
        // Функциональный подход
        print(numbers.stream()
                .filter(i -> i > 3)
                .filter(i -> i % 2 == 0)
                .findFirst());

        // Здесь вернулся Optional,
        // из него можно достать значение методом orElse(-1)
        // Которое вернется в случае если в Optional содержится null

        print("Найдем первое четное число больше 10");
        int res = -1;
        for (int i : numbers) {
            if (i > 10) {
                if (i % 2 == 0) {
                    res = i;
                    break;
                }
            }
        }
        print(res);
        // Функциональный подход
        print(numbers.stream()
                .filter(i -> i > 10)
                .filter(i -> i % 2 == 0)
                .findFirst()
                .orElse(-1));
    }
}
