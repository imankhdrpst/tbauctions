package com.jetbrains.kmpapp

import android.app.Application
import com.jetbrains.kmpapp.di.initKoin
import com.jetbrains.kmpapp.viewmodel.AuctionViewModel
import com.jetbrains.kmpapp.viewmodel.MainViewModel
import org.koin.dsl.module

class KMPApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            listOf(
                module {
                    factory { MainViewModel(get()) }
                    factory { AuctionViewModel(get()) }
                }
            )
        )
    }
}
