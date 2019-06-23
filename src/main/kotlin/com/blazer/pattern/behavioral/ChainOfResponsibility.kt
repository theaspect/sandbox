package com.blazer.pattern.behavioral

object Sender {
    fun method() {
        val handler = SomeHandler("Foo.Bar", "Foo.Bar")
                .addNext(SomeHandler("Foo", "Foo"))
                .addNext(SomeHandler("Bar", "Bar"))
                .addNext(DefaultHandler())

        handler.handle(Message("Foo", "Message 1"))
        handler.handle(Message("Bar", "Message 2"))
        handler.handle(Message("Baz", "Message 3"))
        handler.handle(Message("Foo.Bar", "Message 4"))
    }
}

data class Message(val target: String, val value: String)

abstract class Handler {
    fun addNext(next: Handler) = object : Handler() {
        override fun handle(message: Message): Boolean {
            return if (this@Handler.handle(message)) {
                next.handle(message)
            } else {
                false
            }
        }
    }

    /**
     * Return true if we want to continue handling
     */
    abstract fun handle(message: Message): Boolean

}

class DefaultHandler : Handler() {
    override fun handle(message: Message): Boolean {
        println("Handled by Root Handler for prefix ${message.target}: ${message.value}")
        return true
    }
}

class SomeHandler(val name: String, val prefix: String) : Handler() {
    override fun handle(message: Message): Boolean {
        return if (message.target.startsWith(prefix)) {
            println("Handled by $name for $prefix: ${message.value}")
            false
        } else {
            true
        }
    }

}
