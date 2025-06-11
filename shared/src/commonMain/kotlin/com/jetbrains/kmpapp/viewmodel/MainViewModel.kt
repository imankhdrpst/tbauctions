package com.jetbrains.kmpapp.viewmodel

import com.jetbrains.kmpapp.data.Auction
import com.jetbrains.kmpapp.repository.AuctionsRepository
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

sealed class MainScreenState {
    data object Loading : MainScreenState()
    data class Content(val auctions: List<Auction>) : MainScreenState()
    data class Error(val message: String) : MainScreenState()
}

class MainViewModel(repository: AuctionsRepository) : ViewModel() {

    @NativeCoroutinesState
    val state: StateFlow<MainScreenState> = repository.auctions
        .map { MainScreenState.Content(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, MainScreenState.Loading)
}