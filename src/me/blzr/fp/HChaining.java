package me.blzr.fp;

import static me.blzr.fp.ZCommon.print;

public class HChaining {
    public static void main(String[] args) {
        A a1 = new A(new B(new C(10)));

        print(a1.getB().getC().getVal());

        //<editor-fold desc="safe">
        //        A a2 = new A(null);
//        if (a2.getB() != null &&
//                a2.getB().getC() != null &&
//                a2.getB().getC().getVal() != null) {
//            print(a2.getB().getC().getVal());
//        } else {
//            print(0);
//        }
        //</editor-fold>

        //<editor-fold desc="orElse">
        /*
        print(a2
        .getBOpt().orElse(new B(null))
        .getCOpt().orElse(new C(null))
        .getValOpt().orElse(0));
        */
        //</editor-fold>
    }

    /**
     * "Optional is not meant to be used in these contexts, as it won't buy us anything:
     * in the domain model layer (not serializable)
     * in DTOs (same reason)
     * in input parameters of methods
     * in constructor parameters"
     */
    static class A {
        B b;

        public A(B b) {
            this.b = b;
        }

        B getB() {
            return b;
        }

//        Optional<B> getBOpt() {
//            return Optional.ofNullable(b);
//        }
    }

    static class B {
        C c;

        public B(C c) {
            this.c = c;
        }

        C getC() {
            return c;
        }

//        Optional<C> getCOpt() {
//            return Optional.ofNullable(c);
//        }
    }

    static class C {
        public C(Integer val) {
            this.val = val;
        }

        Integer val;

        Integer getVal() {
            return val;
        }

//        Optional<Integer> getValOpt() {
//            return Optional.ofNullable(val);
//        }
    }
}
