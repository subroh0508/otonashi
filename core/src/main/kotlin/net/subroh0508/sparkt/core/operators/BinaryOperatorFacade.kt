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
}