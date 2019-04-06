package net.subroh0508.otonashi.core.serializer.rdfelement

import kotlinx.serialization.Decoder
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonInput
import kotlinx.serialization.json.JsonObject
import kotlin.reflect.KClass

class RDFElementSerializer<T: Any>(
    private val dataSerializer: KSerializer<T>
) : KSerializer<T> by dataSerializer {
    @Suppress("UNCHECKED_CAST")
    private val kClass: KClass<T> = Class.forName(dataSerializer.descriptor.name).kotlin as KClass<T>

    override fun deserialize(decoder: Decoder): T {
        val input = decoder as? JsonInput ?: throw SerializationException("This class can be loaded only by Json")
        val resultElement = input.decodeJson()

        val elements = (0 until dataSerializer.descriptor.elementsCount).map { index ->
            if (resultElement.isNull) {
                return@map null
            }

            val elementName = dataSerializer.descriptor.getElementName(index)
            decodeRDFElement(input, (resultElement as JsonObject)[elementName])
        }

        return kClass.constructors.first().call(*elements.map { it?.castedValue }.toTypedArray())
        //return kClass.primaryConstructor?.call(*elements.map { it?.castedValue }.toTypedArray())
        //    ?: throw IllegalStateException("${kClass.simpleName} requires the primary constructor!")
    }

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