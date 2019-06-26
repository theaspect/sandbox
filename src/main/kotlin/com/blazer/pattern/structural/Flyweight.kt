package com.blazer.pattern.structural

fun flyweightDemo() {
    FlyweightFabric.getFlyweight("foo").someMethod("123")
    FlyweightFabric.getFlyweight("bar").someMethod("456")
    FlyweightFabric.getFlyweight("baz").someMethod("789")
    FlyweightFabric.getFlyweight("foo").someMethod("012")
}

data class Flyweight(val shared: String) {
    fun someMethod(extrinsic: String) {
        println("$shared: $extrinsic")
    }
}

object FlyweightFabric {
    val cache: MutableMap<String, Flyweight> = mutableMapOf()

    fun getFlyweight(shared: String) =
        cache.computeIfAbsent(shared) {
            println("New instance $it")
            
            Flyweight(it)
        }
}
