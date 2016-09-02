package me.blzr.fp;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static me.blzr.fp.ZCommon.print;
import static me.blzr.fp.ZCommon.sleep;

public class JParallel {
    public static void main(String[] args) {
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

    static List<Integer> getPrices(String url) {
        sleep("Getting prices for " + url, 1000);

        Random r = new Random();
        if (r.nextFloat() > 0.8) {
            return null;
        }

        List<Integer> prices = Arrays.asList(r.nextInt(), r.nextInt(), r.nextInt(), r.nextInt(), r.nextInt());
        return prices;
    }

    //<editor-fold desc="Executor">
    public static void main2(String[] args) throws ExecutionException, InterruptedException {
        List<String> urls = Arrays.asList("site1.com", "site2.com", "site3.com", "site4.com", "site5.com");

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
            if (f.get() != null) {
                results.addAll(f.get());
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
    //</editor-fold>

    //<editor-fold desc="parallel">
    public static void main3(String[] args) {
        List<String> urls = Arrays.asList("site1.com", "site2.com", "site3.com", "site4.com", "site5.com");

        print(urls.stream()
                .parallel()
                .map(JParallel::getPrices)
                .filter(i -> i != null)
                .flatMap(Collection::stream)
                .filter(i -> i > 0)
                .filter(i -> i % 2 == 0)
                .collect(Collectors.toList()));
    }
    //</editor-fold>
}
