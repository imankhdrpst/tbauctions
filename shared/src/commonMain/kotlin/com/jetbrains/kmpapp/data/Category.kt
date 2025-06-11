package com.jetbrains.kmpapp.data

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val headline: String,
    val level: Int,
    val parentId: Int?,
)