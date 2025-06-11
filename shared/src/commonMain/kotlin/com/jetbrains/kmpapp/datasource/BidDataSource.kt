package com.jetbrains.kmpapp.datasource

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface BidDataSource {
    val recentBidPlaced: StateFlow<Pair<Int, Int>>
    suspend fun placeBid(productId: Int, bid: Int)
}

class InMemoryBidDataSource: BidDataSource {
    override val recentBidPlaced = MutableStateFlow<Pair<Int, Int>>(Pair(-1, -1))
    override suspend fun placeBid(productId: Int, bid: Int) {
        recentBidPlaced.value = Pair(productId, bid)
    }
}