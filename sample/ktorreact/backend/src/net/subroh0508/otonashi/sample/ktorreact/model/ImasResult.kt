package net.subroh0508.otonashi.sample.ktorreact.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.subroh0508.otonashi.core.serializer.type.MonthDaySerializer
import java.time.MonthDay

@Serializable
data class ImasResult(
    val id: String,
    val name: String,
    @SerialName("age_str")
    val age: String,
    @SerialName("color_hex")
    val color: String?,
    @SerialName("blood_type")
    val bloodType: String,
    val handedness: String,
    @SerialName("birth_place")
    val birthPlace: String,
    @Serializable(with = MonthDaySerializer::class)
    @SerialName("birth_date")
    val birthDate: MonthDay,
    @SerialName("three_size")
    val threeSize: String,
    @SerialName("unit_names")
    val unitNames: String?,
    @SerialName("clothes_names")
    val clothesNames: String?
)