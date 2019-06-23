package com.blazer.pattern

import com.blazer.pattern.behavioral.Caretaker
import com.blazer.pattern.behavioral.ObserverDemo
import com.blazer.pattern.behavioral.Sender
import com.blazer.pattern.structural.Singleton

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

    h2("Memento")
    Caretaker.method()

    h2("Observer")
    ObserverDemo.method()
}

fun creational() {
    h1("Creational Patterns")
}

fun concurrency() {
    h1("Concurrency Patterns")
}

private fun structural() {
    h1("Structural Patterns")

    h2("Singleton")
    repeat(10) { println(Singleton.method()) }
}

private fun h1(message: String) = println("\n=$message=")
private fun h2(message: String) = println("\n==$message==")
