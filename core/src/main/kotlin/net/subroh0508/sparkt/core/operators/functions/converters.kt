package net.subroh0508.sparkt.core.operators.functions

import net.subroh0508.sparkt.core.aggregates.AggregationFunction
import net.subroh0508.sparkt.core.operators.FunctionFacade
import net.subroh0508.sparkt.core.triples.Var

fun FunctionFacade.isIRI(value: Var) = Function("isIRI", value)

fun FunctionFacade.isBlank(value: Var) = Function("isBlank", value)

fun FunctionFacade.isLiteral(value: Var) = Function("isLiteral", value)

fun FunctionFacade.isNumeric(value: Var) = Function("isNumeric", value)

fun FunctionFacade.str(value: Var) = Function("str", value)
fun FunctionFacade.str(value: AggregationFunction) = Function("str", value)

fun FunctionFacade.lang(value: Var) = Function("lang", value)
fun FunctionFacade.lang(value: AggregationFunction) = Function("lang", value)
