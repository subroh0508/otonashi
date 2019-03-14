package net.subroh0508.core

data class Var(private val value: String) : QueryItem {
    override fun toString() = "?$value"
}