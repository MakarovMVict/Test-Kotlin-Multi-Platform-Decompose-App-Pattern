package com.example.testkmpdecomposeapp.feature.c.impl.domain

data class FeatureCItem(
    val id: Int,
    val title: String
)

interface FeatureCRepository {
    fun getItems(): List<FeatureCItem>
    fun getItemById(itemId: Int): FeatureCItem?
    fun confirmItem(itemId: Int): Boolean
}
