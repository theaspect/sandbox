package com.blazer.pattern.behavioral

import java.util.function.Consumer

fun visitorDemo() {
    val elements: List<Element> = listOf(
            Circle(1, 2, 3),
            Rect(4, 5, 6, 7),
            Rect(8, 9, 0, 1),
            Line(2, 3, 4, 5)
    )

    val visitor = PrintVisitor()
    elements.forEach(visitor)
}

// Kotlin does not have recursive generics
sealed class Element

class Circle(val x: Int, val y: Int, val r: Int) : Element()
class Rect(val x1: Int, val y1: Int, val x2: Int, val y2: Int) : Element()
class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) : Element()

// Reuse java Consumer to work with forEach
interface Visitor : Consumer<Element> {
    override fun accept(element: Element)
}

class PrintVisitor : Visitor {
    override fun accept(element: Element) =
            // Sealed class force us to have exhaustive list
            when (element) {
                is Circle -> println("Visit circle x = ${element.x} y = ${element.y} r = ${element.r}")
                is Rect -> println("Visit rect x1 = ${element.x1} y1 = ${element.y1} x2 = ${element.x2} y2 = ${element.y2}")
                is Line -> println("Visit line x1 = ${element.x1} y1 = ${element.y1} x2 = ${element.x2} y2 = ${element.y2}")
            }
}


