package net.subroh0508.sparkt.core.operators.nodes

interface Node {
    infix fun and(other: Node) = And(this, other)

    infix fun or(other: Node) = Or(this, other)

    infix fun eq(other: Node) = Equal(this, other)

    infix fun notEq(other: Node) = NotEqual(this, other)

    infix fun gt(other: Node) = GreaterThan(this, other)

    infix fun lt(other: Node) = LessThan(this, other)

    infix fun gtOrEq(other: Node) = GreaterThanOrEqual(this, other)

    infix fun ltOrEq(other: Node) = LessThanOrEqual(this, other)

    operator fun plus(other: Node) = Add(this, other)

    operator fun minus(other: Node) = Subtract(this, other)

    operator fun times(other: Node) = Multiple(this, other)

    operator fun div(other: Node) = Divide(this, other)
}