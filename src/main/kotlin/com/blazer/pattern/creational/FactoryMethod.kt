package com.blazer.pattern.creational

fun factoryMethodDemo() {
    val fooFactoryMethod: FactoryMethod = FooFactoryMethod()
    val barFactoryMethod: FactoryMethod = BarFactoryMethod()


    fooFactoryMethod.doStuff()
    barFactoryMethod.doStuff()
}

abstract class FactoryMethod {
    fun doStuff() {
        getNewSay().say()
    }

    abstract fun getNewSay(): Say
}

interface Say {
    fun say()
}

class FooFactoryMethod() : FactoryMethod() {
    override fun getNewSay(): Say =
            object : Say {
                override fun say() = println("I'm say from Foo")
            }
}

class BarFactoryMethod() : FactoryMethod() {
    override fun getNewSay(): Say =
            object : Say {
                override fun say() = println("I'm say from Bar")
            }
}
