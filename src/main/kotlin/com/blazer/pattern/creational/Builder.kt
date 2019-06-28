package com.blazer.pattern.creational

fun builderDemo() {
    println(
            html {
                header {
                    title {
                        +"This is Title"
                    }
                }
                body {
                    div {
                        +"This is div"
                        span {
                            +"Span inside div"
                        }
                    }
                    a("http://example.com") {
                        +"This is link"
                    }
                }
            })
}


// Model

@DSL
abstract class Tag(val name: String) {
    protected val children = mutableListOf<Tag>()

    protected fun <T : Tag> initTag(tag: T, init: T.() -> Unit): T {
        tag.init()
        children.add(tag)
        return tag
    }

    operator fun String.unaryPlus() {
        children.add(Text(this))
    }

    override fun toString(): String =
            if (children.isEmpty()) {
                "<$name />"
            } else {
                """
                |<$name>
                |${children.joinToString("\n|") { it.toString() }}
                |</$name>
                """.trimMargin()
            }

}

// Individual tags

class Text(private val text: String) : Tag(text) {
    override fun toString(): String = text
}

class Html : Tag("html") {
    fun header(init: Header.() -> Unit): Header = initTag(Header(), init)

    fun body(init: Body.() -> Unit): Body = initTag(Body(), init)
}

class Header : Tag("header") {
    fun title(init: Title.() -> Unit): Title = initTag(Title(), init)
}

class Title : Tag("title")

abstract class BodyTag(name: String) : Tag(name) {
    fun a(href: String, init: A.() -> Unit): A = initTag(A(href), init)
    fun div(init: Div.() -> Unit): Div = initTag(Div(), init)
    fun span(init: Span.() -> Unit): Span = initTag(Span(), init)
}

class Body : BodyTag("body")

class Div : BodyTag("div")
class Span : BodyTag("span")
class A(private val href: String) : BodyTag("a") {
    override fun toString(): String =
            if (children.isEmpty()) {
                "<$name href=\"$href\" />"
            } else {
                """
                |<$name href="$href">
                |${children.joinToString("\n|") { it.toString() }}
                |</$name>
                """.trimMargin()
            }
}

// DSL
// https://kotlinlang.org/docs/reference/type-safe-builders.html

// Function literal with receiver
// https://kotlinlang.org/docs/reference/lambdas.html#function-literals-with-receiver

fun html(init: Html.() -> Unit): Html {
    val tag = Html()
    tag.init()
    return tag
}

// Special marker

@DslMarker
annotation class DSL
