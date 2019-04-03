package net.subroh0508.otonashi.vocabulary.generator.internal

internal enum class EndpointType {
    TTL, RDF;

    companion object {
        fun fromEndpoint(endpoint: String) = when {
            endpoint.endsWith(".ttl") -> EndpointType.TTL
            endpoint.endsWith(".rdf") -> EndpointType.RDF
            else -> throw IllegalArgumentException("Invalid extension type '$endpoint'")
        }
    }
}