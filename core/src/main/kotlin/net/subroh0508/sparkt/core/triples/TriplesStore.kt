package net.subroh0508.sparkt.core.triples

class TriplesStore(
    val v: VarStore = VarStore(),
    val iri: IriRefStore = IriRefStore()
) {
    class VarStore(internal val store: MutableMap<String, Var> = mutableMapOf())
    class IriRefStore(internal val store: MutableMap<String, IriRef> = mutableMapOf())
}
