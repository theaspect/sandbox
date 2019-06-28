package com.blazer.pattern

import com.blazer.pattern.behavioral.*
import com.blazer.pattern.creational.*
import com.blazer.pattern.structural.*

fun main() {
    behavioral()
    creational()
    concurrency()
    structural()
}

private fun behavioral() {
    h1("Behavioral Patterns")

    h2("Chain of Responsibility")
    Sender.method()

    h2("Command")
    CommandDemo.method()

    h2("Interpreter")
    interpreterDemo()

    h2("Iterator")
    iteratorDemo()

    h2("Mediator")
    mediatorDemo()

    h2("Memento")
    Caretaker.method()

    h2("Observer")
    ObserverDemo.method()

    h2("State")
    stateDemo()

    h2("Strategy")
    strategyDemo()

    h2("Template Method")
    templateMethodDemo()

    h2("Visitor")
    visitorDemo()
}

fun concurrency() {
    h1("Concurrency Patterns")
}

private fun creational() {
    h1("Creational Patterns")

    h2("Abstract Factory")
    abstractFactoryDemo()

    h2("Builder")
    builderDemo()

    h2("Factory Method")
    factoryMethodDemo()

    h2("Prototype")
    prototypeDemo()

    h2("Singleton")
    repeat(10) { println(Singleton.method()) }
}

fun structural() {
    h1("Structural Patterns")

    h2("Adapter")
    adapterDemo()

    h2("Bridge")
    bridgeDemo()

    h2("Composite")
    compositeDemo()

    h2("Decorator")
    decoratorDemo()

    h2("Facade")
    facadeDemo()

    h2("Flyweight")
    flyweightDemo()

    h2("Proxy")
    proxyDemo()
}

private fun h1(message: String) = println("\n=$message=")
private fun h2(message: String) = println("\n==$message==")
