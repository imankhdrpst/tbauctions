package com.jetbrains.kmpapp

import com.jetbrains.kmpapp.repository.AuctionsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KoinDependencies : KoinComponent {
    val auctionsRepository: AuctionsRepository by inject()
}
