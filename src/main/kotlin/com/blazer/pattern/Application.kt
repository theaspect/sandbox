package com.blazer.pattern

import com.blazer.pattern.structural.Singleton

fun main() {
    println("Structural Patterns")

    // Singleton trivial implementation
    repeat(10) { println(Singleton.method()) }
}
