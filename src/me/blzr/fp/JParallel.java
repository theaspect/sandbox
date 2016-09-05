package me.blzr.fp;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static me.blzr.fp.ZCommon.print;
import static me.blzr.fp.ZCommon.sleep;

public class JParallel {
    public static void main(String[] args) {
        // 5 сайтов, запрашиваем с каждого список из пяти чисел
        // если результат получен оставляем только четные положительные числа

        // Последовательная аггрегация
        iterative();
        // Конкурентная агрегация
        concurrent();
        // Функциональный подход
        functional();
    }

    public static void iterative() {
        print("Iterative");
        List<String> urls = Arrays.asList("site1.com", "site2.com", "site3.com", "site4.com", "site5.com");

        // Get all not null prices
        List<Integer> results = new ArrayList<>();
        for (String u : urls) {
            List<Integer> r = getPrices(u);
            if (r != null) {
                results.addAll(r);
            }
        }

        // Find only Positive Even
        List<Integer> positiveEven = new ArrayList<>();
        for (Integer i : results) {
            if (i > 0) {
                if (i % 2 == 0) {
                    positiveEven.add(i);
                }
            }
        }

        print(positiveEven);
    }

    public static void concurrent() {
        print("Concurrent");
        List<String> urls = Arrays.asList("site1.com", "site2.com", "site3.com", "site4.com", "site5.com");

        // От предыдущего варианта отличается вот этим блоком
        ExecutorService es = Executors.newFixedThreadPool(4);
        List<Future<List<Integer>>> futures = new ArrayList<>();
        for (String u : urls) {
            futures.add(es.submit(new Callable<List<Integer>>() {
                @Override
                public List<Integer> call() throws Exception {
                    return getPrices(u);
                }
            }));
        }

        List<Integer> results = new ArrayList<>();
        for (Future<List<Integer>> f : futures) {
            try {
                if (f.get() != null) {
                    results.addAll(f.get());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        List<Integer> positiveEven = new ArrayList<>();
        for (Integer i : results) {
            if (i > 0) {
                if (i % 2 == 0) {
                    positiveEven.add(i);
                }
            }
        }

        print(positiveEven);
        es.shutdown();
    }

    public static void functional() {
        print("Functional");
        List<String> urls = Arrays.asList("site1.com", "site2.com", "site3.com", "site4.com", "site5.com");

        print(urls.stream()
                .parallel() // Тут происходит магия
                .map(JParallel::getPrices)
                .filter(i -> i != null)
                .flatMap(Collection::stream)
                .filter(i -> i > 0)
                .filter(i -> i % 2 == 0)
                .collect(Collectors.toList()));
    }

    // Генерация случайного списка или null с 20% вероятностью
    static List<Integer> getPrices(String url) {
        sleep("Getting prices for " + url, 1000);

        Random r = new Random();
        if (r.nextFloat() > 0.8) {
            return null;
        }

        List<Integer> prices = Arrays.asList(r.nextInt(), r.nextInt(), r.nextInt(), r.nextInt(), r.nextInt());
        return prices;
    }
}
