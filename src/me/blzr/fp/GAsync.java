package me.blzr.fp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static me.blzr.fp.ZCommon.print;
import static me.blzr.fp.ZCommon.sleep;

public class GAsync {
    public static void main(String[] args) {
        List<Integer> a = requestDatabase1();
        List<Integer> b = requestDatabase2();
        List<Integer> joined = join(a, b);

        Integer eventResult = waitOnEvent();

        List<Integer> result = new ArrayList<>();
        for (Integer i : joined) {
            result.add(i + eventResult);
        }

        print(result);
    }

    static List<Integer> requestDatabase1() {
        sleep("request db1", 900);
        return Arrays.asList(1, 2, 3, 4, 5);
    }

    static List<Integer> requestDatabase2() {
        sleep("request db2", 1000);
        return Arrays.asList(4, 5, 6, 7, 8);
    }

    static List<Integer> join(List<Integer> a, List<Integer> b) {
        sleep("join", 900);
        List<Integer> interserct = new ArrayList<>(a);
        interserct.retainAll(b);
        return interserct;
    }

    static Integer waitOnEvent() {
        sleep("waiting", 1000);
        return 10;
    }

    //<editor-fold desc="supplyAsync">
    public static void main2(String[] args) {
        try {
            CompletableFuture<Integer> fe = CompletableFuture.supplyAsync(GAsync::waitOnEvent);

            CompletableFuture<List<Integer>> fa = CompletableFuture.supplyAsync(GAsync::requestDatabase1);
            CompletableFuture<List<Integer>> fb = CompletableFuture.supplyAsync(GAsync::requestDatabase2);

            CompletableFuture<List<Integer>> fj = fa.thenCombineAsync(fb, GAsync::join);

            List<Integer> result = fe.thenCombine(fj, (event, join) ->
                    join.stream()
                            .map(i -> i + event)
                            .collect(Collectors.toList())).get();
            print(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    //</editor-fold>
}
