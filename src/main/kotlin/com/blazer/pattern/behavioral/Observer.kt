package com.blazer.pattern.behavioral

object ObserverDemo {
    fun method() {
        val subject = Subject<String>()
        println("No observers")
        subject.notify("Message 1")

        println("Subscribe observers")
        val observer1 = Observer1()
        val observer2 = Observer2()
        subject.subscribe(observer1)
        subject.subscribe(observer2)
        subject.notify("Message 2")

        println("Unsubscribe Observer1")
        subject.unsubscribe(observer1)
        subject.notify("Message 3")
    }
}

class Subject<T> {
    private val observers: LinkedHashSet<Observer<T>> = LinkedHashSet()

    fun subscribe(observer: Observer<T>) =
            observers.add(observer)

    fun unsubscribe(observer: Observer<T>) =
            observers.remove(observer)

    fun notify(event: T) {
        observers.forEach { it.update(event) }
    }
}

interface Observer<T> {
    fun update(event: T)
}

class Observer1 : Observer<String> {
    override fun update(event: String) = println("Observer1: $event")
}

class Observer2 : Observer<String> {
    override fun update(event: String) = println("Observer2: $event")
}
