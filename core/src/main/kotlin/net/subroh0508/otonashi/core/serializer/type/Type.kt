package net.subroh0508.otonashi.core.serializer.type

/**
 * See
 * - [XML Schema Part 2: Datatypes Second Edition - 3 Built-in datatypes](https://www.w3.org/TR/2004/REC-xmlschema-2-20041028/datatypes.html#built-in-datatypes)
 * - (http://www.datypic.com/sc/xsd/ns-xsd.html)
 */

internal enum class Type(private val rawName: String) {
    STRING("string"),
    BOOLEAN("boolean"),
    DECIMAL("decimal"),
    FLOAT("float"),
    DOUBLE("double"),
    DURATION("duration"),
    DATE_TIME("dateTime"),
    TIME("time"),
    DATE("date"),
    G_YEAR_MONTH("gYearMonth"),
    G_YEAR("gYear"),
    G_MONTH_DAY("gMonthDay"),
    G_DAY("gMonth"),
    HEX_BINARY("hexBinary"),
    BASE_64_BINARY("base64Binary"),
    ANY_URI("anyURI"),
    Q_NAME("QName"),
    NOTATION("NOTATION"),

    NORMALIZED_STRING("normalizedString"),
    TOKEN("token"),
    LANGUAGE("language"),
    NMTOKEN("NMTOKEN"),
    NMTOKENS("NMTOKENS"),
    NAME("Name"),
    NC_NAME("NCName"),
    ID("ID"),
    IDREF("IDREF"),
    IDREFS("IDREFS"),
    ENTITY("ENTITY"),
    ENTITIES("ENTITIES"),
    INTEGER("integer"),
    NON_POSITIVE_INTEGER("nonPositiveInteger"),
    NEGATIVE_INTEGER("negativeInteger"),
    LONG("long"),
    INT("int"),
    SHORT("short"),
    BYTE("byte"),
    NON_NEGATIVE_INTEGER("nonNegativeInteger"),
    UNSIGNED_LONG("unsignedLong"),
    UNSIGNED_INT("unsignedInt"),
    UNSIGNED_SHORT("unsignedShort"),
    UNSIGNED_BYTE("unsignedByte"),
    POSITIVE_INTEGER("positiveInteger");

    companion object {
        private const val TARGET_NAME_SPACE = "http://www.w3.org/2001/XMLSchema"

        fun findByURI(uri: String): Type {
            val rawNameFromUri = uri.replace("$TARGET_NAME_SPACE#", "")

            return values().find { it.rawName == rawNameFromUri } ?: throw IllegalArgumentException("undefined: $rawNameFromUri")
        }
    }
}