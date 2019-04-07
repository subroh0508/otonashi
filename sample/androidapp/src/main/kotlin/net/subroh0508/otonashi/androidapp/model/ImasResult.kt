package net.subroh0508.otonashi.androidapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
    @SerialName("three_size")
    val threeSize: String,
    val description: String?
)