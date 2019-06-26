package com.blazer.pattern.structural

fun decoratorDemo() {
    Decorator(Decoratee()).method()
}

open class Decoratee {
    open fun method() = println("Some method")
}

class Decorator(val decoratee: Decoratee) : Decoratee() {
    override fun method() {
        println("Do before")
        decoratee.method()
        println("Do after")
    }
}
