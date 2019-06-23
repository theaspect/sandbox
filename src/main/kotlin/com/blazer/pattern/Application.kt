package com.blazer.pattern

import com.blazer.pattern.behavioral.Caretaker
import com.blazer.pattern.structural.Singleton

fun main() {
    behavioral()
    creational()
    concurrency()
    structural()
}

private fun behavioral() {
    println("Behavioral Patterns")

    println("Memento")
    Caretaker.method()
}

fun creational() {
    println("Creational Patterns")
}

fun concurrency() {
    println("Concurrency Patterns")
}

private fun structural() {
    println("Structural Patterns")

    println("Singleton")
    repeat(10) { println(Singleton.method()) }
}
