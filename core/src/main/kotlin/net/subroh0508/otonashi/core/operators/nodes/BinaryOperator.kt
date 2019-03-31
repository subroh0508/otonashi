package net.subroh0508.otonashi.core.operators.nodes

abstract class BinaryOperator(
    private val left: Node,
    private val right: Node,
    private val operator: String
) : Node  {
    override fun toString() = "($left $operator $right)"
}

class And(left: Node, right: Node) : BinaryOperator(left, right, "&&")
class Or(left: Node, right: Node) : BinaryOperator(left, right, "||")
class Equal(left: Node, right: Node) : BinaryOperator(left, right, "=")
class NotEqual(left: Node, right: Node) : BinaryOperator(left, right, "!=")
class GreaterThan(left: Node, right: Node) : BinaryOperator(left, right, ">")
class LessThan(left: Node, right: Node) : BinaryOperator(left, right, "<")
class GreaterThanOrEqual(left: Node, right: Node) : BinaryOperator(left, right, ">=")
class LessThanOrEqual(left: Node, right: Node) : BinaryOperator(left, right, "<=")
class Multiple(left: Node, right: Node) : BinaryOperator(left, right, "*")
class Divide(left: Node, right: Node) : BinaryOperator(left, right, "/")
class Add(left: Node, right: Node) : BinaryOperator(left, right, "+")
class Subtract(left: Node, right: Node) : BinaryOperator(left, right, "-")
