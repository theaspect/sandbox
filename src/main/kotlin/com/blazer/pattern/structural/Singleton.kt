package com.blazer.pattern.structural

object Singleton {
    var counter = 0
    fun method() = "Singleton method counter ${counter++}"
}
