package net.subroh0508.otonashi.sample.ktorreact.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityResult(
    @SerialName("prefecture_name")
    val prefectureName: String,
    @SerialName("city_name")
    val cityName: String,
    val abstract: String
)