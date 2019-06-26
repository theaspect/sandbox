package com.blazer.pattern.structural

fun facadeDemo() {
    val facade: Facade = FacadeImpl(A(), B(), C())
    facade.doA()
    facade.doB()
    facade.doC()
}

interface Facade {
    fun doA()
    fun doB()
    fun doC()
}

class FacadeImpl(val a: A, val b: B, val c: C) : Facade {
    override fun doA() = a.doA()

    override fun doB() = b.doB()

    override fun doC() = c.doC()

}

class A {
    fun doA() = println("Do A")
}

class B {
    fun doB() = println("Do B")
}

class C {
    fun doC() = println("Do C")
}
