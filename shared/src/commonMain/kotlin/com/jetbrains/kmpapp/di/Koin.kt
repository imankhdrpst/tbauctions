package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.datasource.BidDataSource
import com.jetbrains.kmpapp.datasource.CategoryDataSource
import com.jetbrains.kmpapp.datasource.CategoryDataSourceImpl
import com.jetbrains.kmpapp.datasource.InMemoryBidDataSource
import com.jetbrains.kmpapp.datasource.ProductDataSource
import com.jetbrains.kmpapp.datasource.ProductDataSourceImpl
import com.jetbrains.kmpapp.network.Api
import com.jetbrains.kmpapp.network.ApiImpl
import com.jetbrains.kmpapp.network.httpClientEngine
import com.jetbrains.kmpapp.repository.AuctionsRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule = module {
    single {
        HttpClient(httpClientEngine()) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
            defaultRequest {
                headers.append(
                    "x-api-key",
                    "97uionf98y34oiuh3498pfy34hf43hfp9834hf9p83h4fg8ogq3hfph9348ofhiu"
                )
            }
        }
    }

    single<Api> { ApiImpl(get()) }
    single<CategoryDataSource> { CategoryDataSourceImpl() }
    single<ProductDataSource> { ProductDataSourceImpl() }
    single<BidDataSource> { InMemoryBidDataSource() }
    single {
        AuctionsRepository(get(), get(), get(), get())
    }
}

fun initKoin() = initKoin(emptyList())

fun initKoin(extraModules: List<Module>) {
    startKoin {
        modules(
            dataModule,
            *extraModules.toTypedArray(),
        )
    }
}
