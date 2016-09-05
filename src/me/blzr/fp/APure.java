package me.blzr.fp;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoField;

import static me.blzr.fp.ZCommon.assertTrue;

public class APure {
    public static void main(String[] args) {
        // У данной функции сайд-эффекты, которые изменяют внешнюю переменную
        assertTrue(1 == nonIdempotent()); // OK
        assertTrue(1 == nonIdempotent()); // FAIL

        // Данная функция зависит от внешнего окружения
        assertTrue(7 == monthBefore()); // FAIL

        // Чистая функция, можно запускать сколько угодно раз
        assertTrue(1 == idempotent2(external2)); // OK
        assertTrue(1 == idempotent2(external2)); // OK

        // Чистая функция, дата передается аргументом
        assertTrue(8 == monthBefore2(LocalDate.of(2016, 9, 1))); // OK
    }

    static int external = 0;

    static int nonIdempotent() {
        return external+=1;
    }

    static int monthBefore() {
        return LocalDate.now().minus(Period.ofMonths(1)).get(ChronoField.MONTH_OF_YEAR);
    }

    static int monthBefore2(LocalDate date) {
        return date.minus(Period.ofMonths(1)).get(ChronoField.MONTH_OF_YEAR);
    }
    static int external2 = 0;

    static int idempotent2(int var) {
        return var + 1;
    }
}
