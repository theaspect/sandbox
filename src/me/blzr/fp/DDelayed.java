package me.blzr.fp;

import java.util.Random;

import static me.blzr.fp.ZCommon.print;
import static me.blzr.fp.ZCommon.sleep;
import static me.blzr.fp.ZCommon.Function;

public class DDelayed {

    public static void main(String[] args) {
        // Данный метод с 50% вероятностью вызывает длительные вычисления,
        // но все аргументы функции printIf должны быть вычислены, на момент запуска
        printIf(new Random().nextFloat() > 0.5, longCalc());

        // Чтобы не вызывать долгие вычисления мы бы написали примерно так
        Float res = new Random().nextFloat();
        if(res > 0.5){
            print(longCalc());
        }

        // В функциональном подходе можно передать функцию, которую метод вызовет, если условие истинно
        // DDelayed::longCalc то же самое что и () -> longCalc()
        printIf2(new Random().nextFloat() > 0.5, DDelayed::longCalc);
    }

    static int longCalc() {
        sleep("calc", 1000);
        return 42;
    }

    static void printIf(boolean cond, int value) {
        if (cond) print(value);
    }

    static void printIf2(boolean cond, Function<Integer> method) {
        if (cond) print(method.apply());
    }
}
