package me.blzr.fp;

import static me.blzr.fp.ZCommon.assertTrue;

public class APure {
    static int external = 0;
    static int idempotent() {
        return external+=1;
    }

//    static int monthBefore() {
//        return LocalDate.now().minus(Period.ofMonths(1)).get(ChronoField.MONTH_OF_YEAR);
//    }

    public static void main(String[] args) {
        assertTrue(1 == idempotent());
        //assertTrue(1 == idempotent());

        //assertTrue(7 == monthBefore());
    }

    //<editor-fold desc="Example">
    /*
    static int monthBefore2(LocalDate date) {
        return date.minus(Period.ofMonths(1)).get(ChronoField.MONTH_OF_YEAR);
    }
    static int external2 = 0;
    static int idempotent2(int var){
        return var+1;
    }

    // Correct
    assertTrue(8 == monthBefore2(LocalDate.of(2016, 9, 1)));

    assertTrue(1 == idempotent2(external2));
    assertTrue(1 == idempotent2(external2));
    */
    //</editor-fold>
}
