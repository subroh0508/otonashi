package net.subroh0508.sparkt.core.operators

import net.subroh0508.sparkt.core.operators.nodes.*
import net.subroh0508.sparkt.core.triples.Var

interface BinaryOperatorFacade {
    infix fun Var.and(other: Var) = And(Variable(this), Variable(other))

    infix fun Var.or(other: Var) = Or(Variable(this), Variable(other))

    infix fun Var.eq(other: Var) = Equal(Variable(this), Variable(other))

    infix fun Var.notEq(other: Var) = NotEqual(Variable(this), Variable(other))

    infix fun Var.gt(other: Var) = GreaterThan(Variable(this), Variable(other))

    infix fun Var.lt(other: Var) = LessThan(Variable(this), Variable(other))

    infix fun Var.gtOrEq(other: Var) = GreaterThanOrEqual(Variable(this), Variable(other))

    infix fun Var.ltOrEq(other: Var) = LessThanOrEqual(Variable(this), Variable(other))

    operator fun Var.plus(other: Var) = Add(Variable(this), Variable(other))

    operator fun Var.minus(other: Var) = Subtract(Variable(this), Variable(other))

    operator fun Var.times(other: Var) = Multiple(Variable(this), Variable(other))

    operator fun Var.div(other: Var) = Divide(Variable(this), Variable(other))

    infix fun Var.eq(other: Number) = Equal(Variable(this), Constant(other))

    infix fun Var.notEq(other: Number) = NotEqual(Variable(this), Constant(other))

    infix fun Var.gt(other: Number) = GreaterThan(Variable(this), Constant(other))

    infix fun Var.lt(other: Number) = LessThan(Variable(this), Constant(other))

    infix fun Var.gtOrEq(other: Number) = GreaterThanOrEqual(Variable(this), Constant(other))

    infix fun Var.ltOrEq(other: Number) = LessThanOrEqual(Variable(this), Constant(other))

    operator fun Var.plus(other: Number) = Add(Variable(this), Constant(other))

    operator fun Var.minus(other: Number) = Subtract(Variable(this), Constant(other))

    operator fun Var.times(other: Number) = Multiple(Variable(this), Constant(other))

    operator fun Var.div(other: Number) = Divide(Variable(this), Constant(other))

    infix fun Var.eq(other: String) = Equal(Variable(this), Constant(other))

    infix fun Var.notEq(other: String) = NotEqual(Variable(this), Constant(other))

    infix fun Var.gt(other: String) = GreaterThan(Variable(this), Constant(other))

    infix fun Var.lt(other: String) = LessThan(Variable(this), Constant(other))

    infix fun Var.gtOrEq(other: String) = GreaterThanOrEqual(Variable(this), Constant(other))

    infix fun Var.ltOrEq(other: String) = LessThanOrEqual(Variable(this), Constant(other))
}