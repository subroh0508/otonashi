package net.subroh0508.sparkt.core.aggregates

import net.subroh0508.sparkt.core.operators.nodes.Constant
import net.subroh0508.sparkt.core.operators.nodes.Node
import net.subroh0508.sparkt.core.triples.Var

interface AggregationFacade {
    fun average(node: Node) = AggregationFunction("AVG", node)
    fun average(value: Var) = AggregationFunction("AVG", value)

    fun count(node: Node) = AggregationFunction("COUNT", node)
    fun count(value: Var) = AggregationFunction("COUNT", value)

    fun groupConcat(node: Node) = AggregationFunction("GROUP_CONCAT", node)
    fun groupConcat(value: Var) = AggregationFunction("GROUP_CONCAT", value)
    fun groupConcat(value: Var, separator: String) = AggregationFunction("GROUP_CONCAT", "$value; separator=${Constant(separator)}")

    fun max(node: Node) = AggregationFunction("MAX", node)
    fun max(value: Var) = AggregationFunction("MAX", value)

    fun min(node: Node) = AggregationFunction("MIN", node)
    fun min(value: Var) = AggregationFunction("MIN", value)

    fun sample(node: Node) = AggregationFunction("SAMPLE", node)
    fun sample(value: Var) = AggregationFunction("SAMPLE", value)

    fun sum(node: Node) = AggregationFunction("SUM", node)
    fun sum(value: Var) = AggregationFunction("SUM", value)
}