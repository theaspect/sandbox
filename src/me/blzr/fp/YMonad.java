package me.blzr.fp;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static me.blzr.fp.ZCommon.print;
import static me.blzr.fp.ZCommon.sleep;

public class YMonad {
    public static void main(String[] args) {
        // The unary return operation takes a value from a plain type and
        // puts it into a container using the constructor,
        // creating a monadic value: M a.
        print(Optional.of(5));

        // The binary bind operation ">>=" takes as its arguments a monadic value M a and a function (a → M b)
        // that can transform the value.
        print(Optional.of(5)
                .flatMap((Integer o) -> Optional.of(o += 1))
                .flatMap((Integer o) -> Optional.of(o *= 2))
        );

        // Unit
        Stream.of(1, 2, 3, 4, 5);

        // Bind
        Stream.of(1, 2, 3)
                .flatMap(i -> Stream.of(i + 1))
                .flatMap(i -> Stream.of(i * 2, i * 2));

        // Unit
        CompletableFuture.supplyAsync(() -> {
            sleep("calculating", 1000);
            return 42;
        });

        // Bind
        CompletableFuture.supplyAsync(() -> {
            sleep("calculating 1", 1000);
            return 42;
        }).thenApplyAsync((i) -> {
            sleep("calculating 2", 1000);
            return i * 2;
        });

    }
}
