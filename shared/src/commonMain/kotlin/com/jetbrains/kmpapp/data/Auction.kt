package com.jetbrains.kmpapp.data

data class Auction(
    val product: Product,
    val category1: Category?,
    val category2: Category?,
    val category3: Category?,
)