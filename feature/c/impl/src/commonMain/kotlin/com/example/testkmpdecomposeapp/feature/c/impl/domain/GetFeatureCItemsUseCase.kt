package com.example.testkmpdecomposeapp.feature.c.impl.domain

class GetFeatureCItemsUseCase(
    private val repository: FeatureCRepository
) {
    operator fun invoke(): List<FeatureCItem> = repository.getItems()
}
