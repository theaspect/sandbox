package com.blazer.lambda;

import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * http://www.lambdafaq.org/
 * http://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html
 * http://baddotrobot.com/blog/2014/02/18/method-references-in-java8/
 *
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Slf4j
public class LambdaDemo {
    public static void main(String[] args) {
        LambdaDemo demo = new LambdaDemo();
        demo.methodAccessorsDemo();
        demo.lazyEvaluationDemo();
        demo.aopDemo();
    }

    private void methodAccessorsDemo() {
        log.debug("*** methodAccessorsDemo");
        FooClass fc = new FooClass("foo");

        try {
            BeanInfo info = Introspector.getBeanInfo(FooClass.class);
            // [0] is getClass, [1] is field
            log.debug("Old style {}", info.getPropertyDescriptors()[1].getReadMethod().invoke(fc));
        } catch (IllegalAccessException // OH YEAH BABY, multicatcher
                | InvocationTargetException
                | IntrospectionException e) {
            log.debug("Invalid accessor", e);
        }

        // Reference to an instance method of a particular object
        closureAccessor("bar", fc::getField, fc::setField);

        // Pythonic self FTW
        // Reference to an instance method of an arbitrary object of a particular type
        lambdaAccessor(fc, "foo", FooClass::getField, FooClass::setField);
    }

    private void lazyEvaluationDemo() {
        log.debug("*** lazyEvaluationDemo");
        FooClass foo1 = null; // effectively final
        log.debug("Foo is {}", foo1);
        //noinspection ConstantConditions
        doConditionally(foo1 != null,
                () -> log.debug("value {}", foo1.getField()),
                () -> log.debug("value is null"));
        FooClass foo2 = new FooClass("Value"); // effectively final
        log.debug("Foo is {}", foo2);
        //noinspection ConstantConditions
        doConditionally(foo2 != null,
                () -> log.debug("value {}", foo2.getField()),
                () -> log.debug("value is null"));

    }

    private void aopDemo() {
        log.debug("*** aopDemo");
        FooClass foo = new FooClass("Hello world");
        log.debug("Result is {}", this.<FooClass, Object>wrap(foo,
                (closure) -> log.debug("Before {}", closure.getField()),
                (closure) -> {
                    closure.setField("it works");
                    return true;
                },
                (closure) -> log.debug("After {}", closure.getField())));
    }

    @FunctionalInterface
    public interface Getter<V> {
        V get();
    }

    @FunctionalInterface
    public interface Setter<V> {
        void set(V value);
    }

    @FunctionalInterface
    public interface LambdaGetter<T, V> {
        V get(T type);
    }

    @FunctionalInterface
    public interface LambdaSetter<T, V> {
        void set(T type, V value);
    }

    private <T> void closureAccessor(T value, Getter<T> getter, Setter<T> setter) {
        LambdaDemo.log.debug("Closure get {}", getter.get());
        setter.set(value);
        LambdaDemo.log.debug("Closure set {}", getter.get());
    }

    private <T, V> void lambdaAccessor(T type, V value, LambdaGetter<T, V> getter, LambdaSetter<T, V> setter) {
        LambdaDemo.log.debug("Lambda get {}", getter.get(type));
        setter.set(type, value);
        LambdaDemo.log.debug("Lambda set {}", getter.get(type));
    }

    private void doConditionally(boolean condition, Runnable ifTrue, Runnable ifFalse) {
        if (condition) {
            ifTrue.run();
        } else {
            ifFalse.run();
        }
    }

    private <T, R> R wrap(T value, Consumer<T> before, Function<T, R> function, Consumer<T> after) {
        if (before != null) {
            before.accept(value);
        }

        R result = function.apply(value);

        if (after != null) {
            after.accept(value);
        }

        return result;
    }
}
