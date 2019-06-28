package com.blazer.pattern.creational

fun demoAbstractFactory() {
    val foo: Foo = FactoryA.getFoo()
    val bar: Bar = FactoryB.getBar()

    foo.say()
    bar.say()
}

interface AbstractFactory {
    fun getFoo(): Foo
    fun getBar(): Bar
}

interface Foo {
    fun say()
}

interface Bar {
    fun say()
}

object FactoryA : AbstractFactory {
    override fun getFoo(): Foo =
            object : Foo {
                override fun say() = println("I'm A.Foo")
            }


    override fun getBar(): Bar =
            object : Bar {
                override fun say() = println("I'm A.Bar")
            }

}

object FactoryB : AbstractFactory {
    override fun getFoo(): Foo =
            object : Foo {
                override fun say() = println("I'm B.Foo")
            }


    override fun getBar(): Bar =
            object : Bar {
                override fun say() = println("I'm B.Bar")
            }

}
