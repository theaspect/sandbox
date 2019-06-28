package com.blazer.pattern.behavioral

fun iteratorDemo() {
    for (i in Range(0, 10)) {
        println(i)
    }
}

class Range(val from: Int, val to: Int) : Iterable<Int> {
    override fun iterator(): Iterator<Int> = object : Iterator<Int> {
        var current = from

        override fun next(): Int = current++

        override fun hasNext(): Boolean = current <= to
    }
}
