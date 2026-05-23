package com.example.testkmpdecomposeapp.feature.c.impl.data

import com.example.testkmpdecomposeapp.feature.c.impl.domain.FeatureCItem
import com.example.testkmpdecomposeapp.feature.c.impl.domain.FeatureCRepository

class FeatureCRepositoryImpl : FeatureCRepository {
    private val items = listOf(
        FeatureCItem(id = 1, title = "Feature C - Item 1"),
        FeatureCItem(id = 2, title = "Feature C - Item 2")
    )

    override fun getItems(): List<FeatureCItem> = items

    override fun getItemById(itemId: Int): FeatureCItem? = items.firstOrNull { it.id == itemId }

    override fun confirmItem(itemId: Int): Boolean = items.any { it.id == itemId }
}
