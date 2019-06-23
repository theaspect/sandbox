package com.blazer.pattern.behavioral

object CommandDemo {
    fun method() {
        val lamp = Lamp()
        val invoker = Invoker()

        val onCommand = OnLight(lamp)
        val offCommand = OffLight(lamp)

        invoker.register("on", onCommand)
        invoker.register("off", offCommand)

        invoker.execute("on")
        invoker.execute("off")
    }
}

class Invoker {
    private val commands: MutableMap<String, Command> = mutableMapOf()

    fun register(name: String, command: Command) = commands.put(name, command)
    fun execute(command: String) = commands[command]?.execute()
}

abstract class Command(val receiver: Lamp) {
    abstract fun execute()
}

class OnLight(receiver: Lamp) : Command(receiver) {
    override fun execute() = receiver.on()
}

class OffLight(receiver: Lamp) : Command(receiver) {
    override fun execute() = receiver.off()
}

class Lamp {
    fun on() = println("Lamp turned on")
    fun off() = println("Lamp turned off")
}
