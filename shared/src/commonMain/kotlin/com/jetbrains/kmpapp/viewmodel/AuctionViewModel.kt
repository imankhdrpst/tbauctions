package com.jetbrains.kmpapp.viewmodel

import com.jetbrains.kmpapp.data.Auction
import com.jetbrains.kmpapp.repository.AuctionsRepository
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

sealed class AuctionScreenState {
    data object Loading : AuctionScreenState()
    data class Details(val auction: Auction) : AuctionScreenState()
    data class Error(val message: String) : AuctionScreenState()
}

class AuctionViewModel(private val repository: AuctionsRepository): ViewModel() {

    private val productIdFlow = MutableStateFlow<Int?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val state: StateFlow<AuctionScreenState> = productIdFlow
        .flatMapLatest { id ->
            if (id == null) {
                flowOf(AuctionScreenState.Loading)
            } else {
                repository.auctions
                    .map { auctions ->
                        auctions.firstOrNull { it.product.id == id }?.let {
                            AuctionScreenState.Details(it)
                        } ?: AuctionScreenState.Error("Auction not found!")
                    }
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, AuctionScreenState.Loading)

    fun initialize(id: Int) {
        productIdFlow.value = id
    }

    fun placeNewBid(bid: Int) {
        viewModelScope.launch {
            productIdFlow.value?.let { id ->
                repository.placeBid(id, bid)
            }
        }
    }
}