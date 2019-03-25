package net.subroh0508.sparkt.core.extensions

fun String.camelize() = buildString {
    val str = this@camelize.split("_")

    str.forEachIndexed { index, s ->
        if (index == 0)
            append(s)
        else
            append(s.capitalize())
    }
}
