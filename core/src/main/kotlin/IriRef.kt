package net.subroh0508.core

data class IriRef(private val value: String) : QueryItem {
    override fun toString() = value
}