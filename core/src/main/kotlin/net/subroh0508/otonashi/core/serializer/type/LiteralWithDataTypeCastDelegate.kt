package net.subroh0508.otonashi.core.serializer.type

import net.subroh0508.otonashi.core.serializer.rdfelement.RDFElement
import java.net.URI
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.reflect.KProperty

internal object LiteralWithDataTypeCastDelegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Any {
        val ref = thisRef as RDFElement.LiteralWithDataType

        return when (Type.findByURI(ref.datatype)) {
            Type.STRING, Type.NORMALIZED_STRING, Type.TOKEN -> ref.value
            Type.BOOLEAN -> ref.value.toBoolean()
            Type.DECIMAL, Type.DOUBLE -> ref.value.toDouble()
            Type.FLOAT -> ref.value.toFloat()
            Type.DURATION -> Duration.parse(ref.value)
            Type.DATE_TIME -> DateTimeFormatter.ISO_DATE_TIME.parse(ref.value) as LocalDateTime
            Type.TIME -> DateTimeFormatter.ISO_TIME.parse(ref.value) as LocalTime
            Type.DATE -> DateTimeFormatter.ISO_DATE.parse(ref.value) as LocalDate
            Type.G_YEAR_MONTH -> YearMonth.parse(ref.value)
            Type.G_YEAR -> Year.parse(ref.value)
            Type.G_MONTH_DAY -> MonthDay.parse(ref.value)
            Type.G_DAY -> ref.value.replace("---", "").toInt()
            Type.BASE_64_BINARY -> Base64.getDecoder().decode(ref.value)
            Type.ANY_URI -> URI(ref.value)
            // todo
            Type.HEX_BINARY, // ex) 2743D2
            Type.Q_NAME, // ex) prefix:local
            Type.NOTATION // ex) <xs:notation name="gif" public="image/gif" system="view.exe"/>
            -> ref.value
            Type.LANGUAGE -> Locale(ref.value)
            // todo
            Type.NMTOKEN,
            Type.NMTOKENS,
            Type.NAME,
            Type.NC_NAME,
            Type.ID,
            Type.IDREF,
            Type.IDREFS,
            Type.ENTITY,
            Type.ENTITIES
            -> ref.value
            Type.INTEGER, Type.INT, Type.NON_NEGATIVE_INTEGER, Type.NON_POSITIVE_INTEGER,
            Type.NEGATIVE_INTEGER, Type.POSITIVE_INTEGER
            -> ref.value.toInt()
            Type.SHORT -> ref.value.toShort()
            Type.LONG -> ref.value.toLong()
            Type.BYTE -> ref.value.toByte()
            Type.UNSIGNED_INT -> @UseExperimental(ExperimentalUnsignedTypes::class) ref.value.toUInt()
            Type.UNSIGNED_SHORT -> @UseExperimental(ExperimentalUnsignedTypes::class) ref.value.toUShort()
            Type.UNSIGNED_LONG -> @UseExperimental(ExperimentalUnsignedTypes::class) ref.value.toULong()
            Type.UNSIGNED_BYTE -> @UseExperimental(ExperimentalUnsignedTypes::class) ref.value.toUByte()
        }
    }
}