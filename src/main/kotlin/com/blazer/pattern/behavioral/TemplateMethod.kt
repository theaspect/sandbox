package com.blazer.pattern.behavioral

fun templateMethodDemo() {
    ConcreteA().staff()
    ConcreteB().staff()
}

abstract class BaseClass {
    fun staff() {
        println("Do stuff ${doStuff()}")
        println("Do hook ${hook()}")
    }

    protected abstract fun doStuff(): String
    protected open fun hook() = "Base"
}

class ConcreteA : BaseClass() {
    override fun doStuff(): String = "Concrete A"
    override fun hook(): String = "Optional A"
}

class ConcreteB : BaseClass() {
    override fun doStuff(): String = "Concrete B"
}
