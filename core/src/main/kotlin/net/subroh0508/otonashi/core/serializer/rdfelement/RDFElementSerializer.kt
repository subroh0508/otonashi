package net.subroh0508.otonashi.core.serializer.rdfelement

import kotlinx.serialization.*
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonInput
import kotlinx.serialization.json.JsonObject
import net.subroh0508.otonashi.core.serializer.SparqlResponse
import kotlin.reflect.KClass

internal class RDFElementSerializer<T: Any>(
    dataSerializer: KSerializer<T>
) : KSerializer<SparqlResponse.Binding<T>> {
    override val descriptor: SerialDescriptor = dataSerializer.descriptor

    @Suppress("UNCHECKED_CAST")
    private val kClass: KClass<T> = Class.forName(descriptor.name).kotlin as KClass<T>

    override fun deserialize(decoder: Decoder): SparqlResponse.Binding<T> {
        val input = decoder as? JsonInput ?: throw SerializationException("This class can be loaded only by Json")
        val resultElement = input.decodeJson()

        val elements = (0 until descriptor.elementsCount).map { index ->
            if (resultElement.isNull) {
                return@map null
            }

            val elementName = descriptor.getElementName(index)
            decodeRDFElement(input, (resultElement as JsonObject)[elementName])
        }

        val elementsMap = elements.mapIndexed { index, rdfElement ->
            descriptor.getElementName(index) to rdfElement
        }.toMap()

        return SparqlResponse.Binding(
            elementsMap,
            kClass.constructors.first().call(*elements.map { it?.castedValue }.toTypedArray())
        )
        //return kClass.primaryConstructor?.call(*elements.map { it?.castedValue }.toTypedArray())
        //    ?: throw IllegalStateException("${kClass.simpleName} requires the primary constructor!")
    }

    override fun serialize(encoder: Encoder, obj: SparqlResponse.Binding<T>) = Unit

    private fun decodeRDFElement(input: JsonInput, element: JsonElement): RDFElement? {
        val jsonObject = element as JsonObject

        return when {
            jsonObject.isNull || jsonObject.isEmpty() -> null
            jsonObject.getPrimitiveOrNull("type").toString() == RDFElement.TYPE_URI
                -> @UseExperimental(ImplicitReflectionSerializer::class)
                input.json.fromJson<RDFElement.IRI>(element)
            jsonObject.getPrimitiveOrNull("type").toString() == RDFElement.TYPE_BNODE
                -> @UseExperimental(ImplicitReflectionSerializer::class)
                input.json.fromJson<RDFElement.BlankNode>(element)
            jsonObject.keys.contains(RDFElement.LANGUAGE_TAG)
                -> @UseExperimental(ImplicitReflectionSerializer::class)
                input.json.fromJson<RDFElement.LiteralWithLang>(element)
            jsonObject.keys.contains(RDFElement.DATATYPE_IRI)
                -> @UseExperimental(ImplicitReflectionSerializer::class)
                input.json.fromJson<RDFElement.LiteralWithDataType>(element)
            else
                -> @UseExperimental(ImplicitReflectionSerializer::class)
                input.json.fromJson<RDFElement.Literal>(element)
        }
    }
}