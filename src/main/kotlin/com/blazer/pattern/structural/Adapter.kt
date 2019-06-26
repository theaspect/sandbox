package com.blazer.pattern.structural

fun adapterDemo() {
    val client = Client(Wrapper(Adaptee()))
    client.callMethod()
}

class Client(val adapter: Adapter) {
    fun callMethod() {
        println("Call stuff")
        adapter.doStuff()
    }
}

interface Adapter {
    fun doStuff()
}

class Wrapper(val adaptee: Adaptee) : Adapter {
    override fun doStuff() = adaptee.doActualStuff()
}

class Adaptee {
    fun doActualStuff() = println("Do actual stuff")
}
