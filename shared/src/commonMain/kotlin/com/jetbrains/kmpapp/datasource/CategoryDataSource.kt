package com.jetbrains.kmpapp.datasource

import com.jetbrains.kmpapp.data.Category
import kotlinx.coroutines.flow.MutableStateFlow

interface CategoryDataSource {

    suspend fun storeCategories(items: List<Category>)
    suspend fun getCategories(): List<Category>
}

class CategoryDataSourceImpl: CategoryDataSource {
    private var stored = MutableStateFlow<List<Category>>(emptyList())
    override suspend fun storeCategories(items: List<Category>) {
        stored.value = items
    }

    override suspend fun getCategories(): List<Category> = stored.value

}