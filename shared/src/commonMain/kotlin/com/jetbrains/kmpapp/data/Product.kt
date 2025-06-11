package com.jetbrains.kmpapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val name: String,
    val make: String? = null,
    val description: String? = null,
    val currentBid: Int,
    val endDate: String,
    val reservePriceStatus: String,
    val municipalityName: String,

    @SerialName("image")
    val mainImage: MainImage? = null,

    val categoryLevel1: Int? = null,
    val categoryLevel2: Int? = null,
    val categoryLevel3: Int? = null
)

@Serializable
data class MainImage(
    @SerialName("thumbUrl")
    val imageUrlThumb: String,

    @SerialName("largeUrl")
    val imageUrlLarge: String
)