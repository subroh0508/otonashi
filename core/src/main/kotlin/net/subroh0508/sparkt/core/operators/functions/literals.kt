package net.subroh0508.sparkt.core.operators.functions

import net.subroh0508.sparkt.core.operators.FunctionFacade
import net.subroh0508.sparkt.core.operators.nodes.Constant
import net.subroh0508.sparkt.core.triples.Var

fun FunctionFacade.contains(value: Var, expect: String) = Function("contains", value, Constant(expect))

fun FunctionFacade.strLength(value: Var) = Function("strlen", value)

fun FunctionFacade.subString(value: Var, startIdx: Int) = Function("substr", value, Constant(startIdx))

fun FunctionFacade.subString(value: Var, startIdx: Int, length: Int) = Function("substr", value, Constant(startIdx), Constant(length))

fun FunctionFacade.upperCase(value: Var) = Function("ucase", value)

fun FunctionFacade.lowerCase(value: Var) = Function("lcase", value)

fun FunctionFacade.startWith(value: Var) = Function("strStarts", value)

fun FunctionFacade.endWith(value: Var) = Function("strEnds", value)

fun FunctionFacade.strBefore(value: Var, str: String) = Function("strbefore", Constant(str))

fun FunctionFacade.strAfter(value: Var, str: String) = Function("strafter", Constant(str))

fun FunctionFacade.encodeURI(value: Var) = Function("encode_for_uri", value)

fun FunctionFacade.concat(vararg args: Any) = Function("concat", *args.map {
    when (it) {
        is Var -> it
        else -> Constant(it)
    }
}.toTypedArray())

fun FunctionFacade.langMatches(value: Var, lang: String) = Function("lang", lang(value), Constant(lang))

fun FunctionFacade.regex(value: Var, pattern: String) = Function("regex", value, Constant(pattern))

fun FunctionFacade.replace(value: Var, pattern: String, expect: String) = Function("replace", value, Constant(pattern), Constant(expect))
