package me.blzr.fp;

import java.util.Optional;

import static me.blzr.fp.ZCommon.print;

public class HChaining {
    public static void main(String[] args) {
        // Иерархия классов
        A a1 = new A(new B(new C(10)));

        // Простой случай
        print(a1.getB().getC().getVal());

        // Что если цепочка на каком-то элементе оборвется,
        // Необходимо добавлять много проверок чтобы избежать NullPointerException
        A a2 = new A(null);
        if (a2.getB() != null &&
                a2.getB().getC() != null &&
                a2.getB().getC().getVal() != null) {
            print(a2.getB().getC().getVal());
        } else {
            print(0);
        }

        // Функциональный подход, если встречаем null, заменяем его каким-то значением
        print(a2
                .getBOpt().orElse(new B(null))
                .getCOpt().orElse(new C(null))
                .getValOpt().orElse(0));

        // Альтернативный подход
        // В этом случае промежуточные вычисления проводится не будут.
        // После первого null, вычисления будут сразу проброшены до последнего этапа

        print(a2.getBOpt()
                .flatMap(B::getCOpt)
                .flatMap(C::getValOpt)
                .orElse(0));
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

        Optional<B> getBOpt() {
            return Optional.ofNullable(b);
        }
    }

    static class B {
        C c;

        public B(C c) {
            this.c = c;
        }

        C getC() {
            return c;
        }

        Optional<C> getCOpt() {
            return Optional.ofNullable(c);
        }
    }

    static class C {
        public C(Integer val) {
            this.val = val;
        }

        Integer val;

        Integer getVal() {
            return val;
        }

        Optional<Integer> getValOpt() {
            return Optional.ofNullable(val);
        }
    }
}
