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

    infix fun eq(other: Number) = Equal(this, Constant(other))

    infix fun notEq(other: Number) = NotEqual(this, Constant(other))

    infix fun gt(other: Number) = GreaterThan(this, Constant(other))

    infix fun lt(other: Number) = LessThan(this, Constant(other))

    infix fun gtOrEq(other: Number) = GreaterThanOrEqual(this, Constant(other))

    infix fun ltOrEq(other: Number) = LessThanOrEqual(this, Constant(other))

    operator fun plus(other: Number) = Add(this, Constant(other))

    operator fun minus(other: Number) = Subtract(this, Constant(other))

    operator fun times(other: Number) = Multiple(this, Constant(other))

    operator fun div(other: Number) = Divide(this, Constant(other))

    infix fun eq(other: String) = Equal(this, Constant(other))

    infix fun notEq(other: String) = NotEqual(this, Constant(other))

    infix fun gt(other: String) = GreaterThan(this, Constant(other))

    infix fun lt(other: String) = LessThan(this, Constant(other))

    infix fun gtOrEq(other: String) = GreaterThanOrEqual(this, Constant(other))

    infix fun ltOrEq(other: String) = LessThanOrEqual(this, Constant(other))
}