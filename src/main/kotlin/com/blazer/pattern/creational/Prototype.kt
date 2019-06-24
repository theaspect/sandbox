package com.blazer.pattern.creational

fun demoPrototype() {
    val prototype = Prototype(1, 2)
    println("Prototype: $prototype")
    val clone = prototype.copy(a = 5)
    println("Clone: $clone")
    prototype.a = 10
    println("Prototype: $prototype")
    println("Clone: $clone")

}

// Trivial Implementation
data class Prototype(var a: Int, var b: Int)
