package com.blazer.pattern.structural

fun demoComposite() {
    val uniformity = BranchUniformity()
    uniformity.add(LeafUniformity())
    uniformity.add(BranchUniformity())

    uniformity.operation()

    val typesafe = BranchTypesafe()
    typesafe.add(LeafTypesafe())
    typesafe.add(BranchTypesafe())

    typesafe.operation()
}

interface CompositeUniformity {
    fun operation()
    fun add(element: CompositeUniformity)
}

class LeafUniformity : CompositeUniformity {
    override fun operation() = println("LeafUniformity")
    override fun add(element: CompositeUniformity) = println("No op method added for uniformity")
}

class BranchUniformity : CompositeUniformity {
    private val children: MutableList<CompositeUniformity> = mutableListOf()

    override fun operation() {
        if (children.isEmpty()) {
            println("Empty BranchUniformity")
        } else {
            children.forEach { it.operation() }
        }
    }

    override fun add(element: CompositeUniformity) {
        children.add(element)
    }
}

interface CompositeTypesafe {
    fun operation()
}

class LeafTypesafe : CompositeTypesafe {
    override fun operation() = println("LeafTypesafe")
}

class BranchTypesafe : CompositeTypesafe {
    private val children: MutableList<CompositeTypesafe> = mutableListOf()

    override fun operation() {
        if (children.isEmpty()) {
            println("Empty BranchTypesafe")
        } else {
            children.forEach { it.operation() }
        }
    }

    fun add(element: CompositeTypesafe) {
        children.add(element)
    }
}
