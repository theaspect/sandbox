package com.blazer.pattern.behavioral

object Caretaker {
    fun method() {
        val originator = Originator()
        println("Took first 5 fibonacci numbers")
        repeat(5) { println(originator.nextFibonacci()) }
        val memento = originator.getState()
        println("Took next 5 fibonacci numbers")
        repeat(5) { println(originator.nextFibonacci()) }

        println("Restore state")
        originator.setState(memento)
        println(originator.nextFibonacci())
    }
}

class Originator {
    var a: Long = 0
    var b: Long = 1

    fun nextFibonacci(): Long {
        // No destructuring assignment in Kotlin
        // see https://youtrack.jetbrains.com/issue/KT-11362
        val next = a + b
        a = b
        b = next
        return next
    }

    fun getState(): Memento = Memento(a, b)

    fun setState(memento: Memento) {
        a = memento.a
        b = memento.b
    }
}

data class Memento(val a: Long, val b: Long)
