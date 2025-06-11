package com.jetbrains.kmpapp.datasource

import com.jetbrains.kmpapp.data.Product
import kotlinx.coroutines.flow.MutableStateFlow

interface ProductDataSource {

    suspend fun storeProducts(items: List<Product>)
    suspend fun getProducts(): List<Product>
}

class ProductDataSourceImpl: ProductDataSource {
    private var stored = MutableStateFlow<List<Product>>(emptyList())
    override suspend fun storeProducts(items: List<Product>) {
        stored.value = items
    }

    override suspend fun getProducts(): List<Product> = stored.value

}