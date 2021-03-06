package com.blazer.pattern.behavioral

object CommandDemo {
    fun method() {
        val lamp = Lamp()
        val invoker = Invoker()

        val onCommand = OnLight(lamp)
        val offCommand = OffLight(lamp)

        invoker.register("on", onCommand)
        invoker.register("off", offCommand)

        invoker.registerLambda("lambda") { lamp.lambda() }

        invoker.execute("on")
        invoker.execute("off")
        invoker.execute("lambda")
    }
}

class Invoker {
    private val commands: MutableMap<String, Command> = mutableMapOf()

    fun register(name: String, command: Command) = commands.put(name, command)
    fun registerLambda(name: String, lambda: () -> Unit) = commands.put(
            name,
            object : Command() {
                override fun execute() = lambda()
            })
    fun execute(command: String) = commands[command]?.execute()
}

abstract class Command {
    abstract fun execute()
}

class OnLight(val receiver: Lamp) : Command() {
    override fun execute() = receiver.on()
}

class OffLight(val receiver: Lamp) : Command() {
    override fun execute() = receiver.off()
}

class Lamp {
    fun on() = println("Lamp turned on")
    fun off() = println("Lamp turned off")
    fun lambda() = println("Lambda invocation")
}
