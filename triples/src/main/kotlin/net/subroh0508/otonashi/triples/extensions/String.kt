package net.subroh0508.otonashi.triples.extensions

fun String.camelize() = buildString {
    val str = this@camelize.split("_")

    str.forEachIndexed { index, s ->
        if (index == 0)
            append(s)
        else
            append(s.capitalize())
    }
}
