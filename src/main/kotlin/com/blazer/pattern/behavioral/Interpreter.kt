package com.blazer.pattern.behavioral

import java.util.*

fun interpreterDemo() {
    val context = InterpreterContext(mapOf(
            "w" to 5,
            "x" to 10,
            "z" to 42
    ))

    val expression = Parser().parse("w x z - +")
    println("Parsed $expression = ${expression.evaluate(context)}")
}

class InterpreterContext(val variables: Map<String, Int>)

/**
 * Reverse Polish notation interpreter
 *
 * expression ::= plus | minus | variable | number
 * plus       ::= expression expression "+"
 * minus      ::= expression expression "-"
 * variable   ::= [a-z]
 * number     ::= [0-9]+
 */
interface Expression {
    fun evaluate(context: InterpreterContext): Int
    override fun toString(): String
}

// Not sure about left/right order in RPN
class PlusExpression(private val right: Expression, private val left: Expression) : Expression {
    override fun evaluate(context: InterpreterContext) = left.evaluate(context) + right.evaluate(context)

    override fun toString(): String = "( $left + $right )"
}

// Not sure about left/right order in RPN
class MinusExpression(private val right: Expression, private val left: Expression) : Expression {
    override fun evaluate(context: InterpreterContext) = left.evaluate(context) - right.evaluate(context)

    override fun toString(): String = "( $left - $right )"
}

class VariableExpression(private val variable: String) : Expression {
    override fun evaluate(context: InterpreterContext) = context.variables[variable] ?: 0

    override fun toString(): String = variable
}

class NumberExpression(private val number: String) : Expression {
    override fun evaluate(context: InterpreterContext) = number.toInt()

    override fun toString(): String = number
}

class Parser {
    fun parseToken(token: String, stack: ArrayDeque<Expression>) {
        when {
            token == "+" -> stack.push(PlusExpression(stack.pop(), stack.pop()))
            token == "-" -> stack.push(MinusExpression(stack.pop(), stack.pop()))
            token.matches(Regex("[0-9]+")) -> stack.push(NumberExpression(token))
            token.matches(Regex("[a-z]")) -> stack.push(VariableExpression(token))
        }
    }

    fun parse(expression: String): Expression {
        val stack = ArrayDeque<Expression>()
        for (token in expression.split(" ")) {
            parseToken(token, stack)
        }
        return stack.pop()
    }
}
