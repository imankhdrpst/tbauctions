package com.jetbrains.kmpapp.network

import com.jetbrains.kmpapp.data.Category
import com.jetbrains.kmpapp.data.Product
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface Api {
    suspend fun getCategories(): List<Category>
    suspend fun getProducts(): List<Product>
}
class ApiImpl(private val client: HttpClient): Api {

    override suspend fun getProducts(): List<Product> {
        return client.get("https://app.klaravik.dev/dev-test-api/products").body()
    }

    override suspend fun getCategories(): List<Category> {
        return client.get("https://app.klaravik.dev/dev-test-api/categories").body()
    }
}



