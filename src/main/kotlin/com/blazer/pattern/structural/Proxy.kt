package com.blazer.pattern.structural

fun demoProxy() {
    val proxy = Proxy(Actual())

    println(proxy.square(1))
    println(proxy.square(2))
    println(proxy.square(3))
    println(proxy.square(1))
    println(proxy.square(4))
}

interface Subject {
    fun square(value: Int): Int
}

class Actual : Subject {
    override fun square(value: Int): Int = value * value
}

class Proxy(val actual: Actual) : Subject {
    val capacity = 3
    private val cache = object : LinkedHashMap<Int, Int>(capacity, 0.7f, true) {
        override fun removeEldestEntry(eldest: Map.Entry<Int, Int>): Boolean {
            return size > capacity
        }
    }

    override fun square(value: Int): Int {
        if (!cache.containsKey(value)) {
            println("Cache $value")
            cache[value] = actual.square(value)
        }

        return cache[value]!!
    }

}
