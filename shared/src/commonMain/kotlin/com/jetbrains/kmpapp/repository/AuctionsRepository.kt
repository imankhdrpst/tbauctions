package com.jetbrains.kmpapp.repository

import com.jetbrains.kmpapp.data.Auction
import com.jetbrains.kmpapp.datasource.BidDataSource
import com.jetbrains.kmpapp.datasource.CategoryDataSource
import com.jetbrains.kmpapp.datasource.ProductDataSource
import com.jetbrains.kmpapp.network.Api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuctionsRepository(
    private val api: Api,
    private val categories: CategoryDataSource,
    private val products: ProductDataSource,
    private val newBids: BidDataSource,
) {
    private val _auctions = MutableStateFlow<List<Auction>>(emptyList())
    val auctions: StateFlow<List<Auction>> = _auctions

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        scope.launch {
            api.getCategories().let { categories.storeCategories(it) }
            api.getProducts().let { products.storeProducts(it) }

            // init auctions
            _auctions.value = categories.getCategories().let { cats ->
                val catsMap = cats.associateBy { it.id }
                products.getProducts().map { product ->
                    Auction(
                        product = product,
                        category1 = catsMap[product.categoryLevel1],
                        category2 = catsMap[product.categoryLevel2],
                        category3 = catsMap[product.categoryLevel3],
                    )
                }
            }

            // listen to bids changing
            newBids.recentBidPlaced.collect { bid ->
                updateAuctionsByHighestBid(bid.first, bid.second)
            }
        }
    }

    private fun updateAuctionsByHighestBid(id: Int, bid: Int) {
        _auctions.update { auctions ->
            auctions.map { auction ->
                if (auction.product.id == id) {
                    auction.copy(
                        product = auction.product.copy(
                            currentBid = bid
                        )
                    )
                } else {
                    auction
                }
            }
        }
    }


    suspend fun placeBid(productId: Int, bid: Int) {
        newBids.placeBid(productId, bid)
    }
}