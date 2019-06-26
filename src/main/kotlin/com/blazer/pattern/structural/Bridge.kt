package com.blazer.pattern.structural

fun bridgeDemo() {
    val bridge1 = AbstractBridgeImpl(Bridge1())
    val bridge2 = AbstractBridgeImpl(Bridge2())

    bridge1.callMethod1()
    bridge2.callMethod2()
}

interface Bridge {
    fun method1()
    fun method2()
}

class Bridge1 : Bridge {
    override fun method1() = println("Bridge1 method1")
    override fun method2() = println("Bridge1 method2")
}

class Bridge2 : Bridge {
    override fun method1() = println("Bridge2 method1")
    override fun method2() = println("Bridge2 method2")
}

interface AbstractBridge {
    fun callMethod1()
    fun callMethod2()
}

class AbstractBridgeImpl(private val bridge: Bridge) : AbstractBridge {
    override fun callMethod1() = bridge.method1()
    override fun callMethod2() = bridge.method2()
}
