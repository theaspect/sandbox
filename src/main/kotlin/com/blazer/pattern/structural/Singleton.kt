package com.blazer.pattern.structural

object Singleton {
    init {
        println("Singleton Init")
    }

    private var counter = 0
    fun method() = "Singleton method counter ${counter++}"
}
