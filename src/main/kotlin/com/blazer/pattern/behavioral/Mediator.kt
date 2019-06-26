package com.blazer.pattern.behavioral

fun mediatorDemo() {
    // Cyclic dependency
    val mediator = Mediator()
    mediator.fan = Fan(mediator)
    mediator.power = Power(mediator)
    mediator.button = Button(mediator)

    mediator.button.press()
    mediator.button.press()
}

class Mediator {
    lateinit var fan: Fan
    lateinit var power: Power
    lateinit var button: Button

    var isOn = false


    fun start() {
        println("Mediator start")
        power.turnOn()
    }

    fun stop() {
        println("Mediator stop")
        power.turnOff()
    }

    fun press() {
        println("Mediator press")
        if (isOn) {
            fan.turnOff()
            isOn = true
        } else {
            fan.turnOn()
            isOn = true
        }
    }

}

abstract class Colleague(val mediator: Mediator)

class Fan(mediator: Mediator) : Colleague(mediator) {
    fun turnOn() {
        mediator.start()
        println("Fan turned on")
    }

    fun turnOff() {
        mediator.stop()
        println("Fan turned off")
    }
}

class Power(mediator: Mediator) : Colleague(mediator) {
    fun turnOn() = println("Power turned on")
    fun turnOff() = println("Power turned off")
}

class Button(mediator: Mediator) : Colleague(mediator) {
    fun press() = mediator.press()
}
