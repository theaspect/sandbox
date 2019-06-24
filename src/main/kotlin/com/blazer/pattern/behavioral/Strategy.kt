package com.blazer.pattern.behavioral

fun strategyDemo() {
    val numberStrategy: (String) -> Boolean = { it.matches(Regex("[0-9]+")) }
    val wordStrategy: (String) -> Boolean = { it.matches(Regex("[a-z]+")) }

    val strategy = StrategyContext(numberStrategy)

    val list = listOf("123", "abcd", "!@#$", "456", "zxcv")
    println("Numbers:" + strategy.filter(list))

    strategy.strategy = wordStrategy
    println("Words:" + strategy.filter(list))
}

class StrategyContext(var strategy: (String) -> Boolean) {
    fun filter(strings: List<String>) = strings.filter(strategy)
}
