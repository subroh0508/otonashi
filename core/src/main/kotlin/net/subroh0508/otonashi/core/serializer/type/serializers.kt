package net.subroh0508.otonashi.core.serializer.type

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.net.URI
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

@Serializer(forClass = Duration::class)
class DurationSerializer : KSerializer<Duration> {
    override val descriptor: SerialDescriptor = StringDescriptor

    override fun serialize(encoder: Encoder, obj: Duration) = encoder.encodeString(obj.toString())
    override fun deserialize(decoder: Decoder): Duration = Duration.parse(decoder.decodeString())
}

@Serializer(forClass = Year::class)
class YearSerializer : KSerializer<Year> {
    override val descriptor: SerialDescriptor = StringDescriptor

    override fun serialize(encoder: Encoder, obj: Year) = encoder.encodeString(obj.toString())
    override fun deserialize(decoder: Decoder): Year = Year.parse(decoder.decodeString())
}

@Serializer(forClass = MonthDay::class)
class MonthDaySerializer : KSerializer<MonthDay> {
    override val descriptor: SerialDescriptor = StringDescriptor

    override fun serialize(encoder: Encoder, obj: MonthDay) = encoder.encodeString(obj.toString())
    override fun deserialize(decoder: Decoder): MonthDay = MonthDay.parse(decoder.decodeString())
}

@Serializer(forClass = YearMonth::class)
class YearMonthSerializer : KSerializer<YearMonth> {
    override val descriptor: SerialDescriptor = StringDescriptor

    override fun serialize(encoder: Encoder, obj: YearMonth) = encoder.encodeString(obj.toString())
    override fun deserialize(decoder: Decoder): YearMonth = YearMonth.parse(decoder.decodeString())
}

@Serializer(forClass = LocalDateTime::class)
class LocaleDateTimeSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor = StringDescriptor

    override fun serialize(encoder: Encoder, obj: LocalDateTime) = encoder.encodeString(obj.toString())
    override fun deserialize(decoder: Decoder): LocalDateTime = DateTimeFormatter.ISO_DATE_TIME.parse(decoder.decodeString()) as LocalDateTime
}

@Serializer(forClass = LocalTime::class)
class LocalTimeSerialzer : KSerializer<LocalTime> {
    override val descriptor: SerialDescriptor = StringDescriptor

    override fun serialize(encoder: Encoder, obj: LocalTime) = encoder.encodeString(obj.toString())
    override fun deserialize(decoder: Decoder): LocalTime = DateTimeFormatter.ISO_TIME.parse(decoder.decodeString()) as LocalTime
}

@Serializer(forClass = LocalDate::class)
class LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = StringDescriptor

    override fun serialize(encoder: Encoder, obj: LocalDate) = encoder.encodeString(obj.toString())
    override fun deserialize(decoder: Decoder): LocalDate = DateTimeFormatter.ISO_DATE.parse(decoder.decodeString()) as LocalDate
}

@Serializer(forClass = URI::class)
class URISerializer : KSerializer<URI> {
    override val descriptor: SerialDescriptor = StringDescriptor

    override fun serialize(encoder: Encoder, obj: URI) = encoder.encodeString(obj.toString())
    override fun deserialize(decoder: Decoder): URI = URI(decoder.decodeString())
}

@Serializer(forClass = Locale::class)
class LanguageSerializer : KSerializer<Locale> {
    override val descriptor: SerialDescriptor = StringDescriptor

    override fun serialize(encoder: Encoder, obj: Locale) = encoder.encodeString(obj.language)
    override fun deserialize(decoder: Decoder): Locale = Locale(decoder.decodeString())
}
