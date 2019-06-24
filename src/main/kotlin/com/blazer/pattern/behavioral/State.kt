package com.blazer.pattern.behavioral

import java.util.*

fun stateDemo() {
    val context = Context()

    Calendar.getInstance().getDisplayNames(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()).keys.forEach {
        context.write(it)
    }
}

class Context {
    var state: State = CapitalizeState()

    fun write(name: String) {
        state.handle(this, name)
    }
}

interface State {
    fun handle(context: Context, name: String)
}

class CapitalizeState : State {
    var cnt = 0

    override fun handle(context: Context, name: String) {
        println(name.capitalize())
        if (cnt++ >= 1) context.state = LowerCaseState()
    }
}

class LowerCaseState : State {
    var cnt = 0

    override fun handle(context: Context, name: String) {
        println(name.toLowerCase())
        if (cnt++ >= 1) context.state = UpperCaseState()
    }
}

class UpperCaseState : State {
    var cnt = 0

    override fun handle(context: Context, name: String) {
        println(name.toUpperCase())
        if (cnt++ >= 1) context.state = CapitalizeState()
    }

}
