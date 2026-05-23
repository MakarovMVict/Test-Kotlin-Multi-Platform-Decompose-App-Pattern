package com.example.testkmpdecomposeapp.feature.c.impl.domain

class GetFeatureCItemDetailsUseCase(
    private val repository: FeatureCRepository
) {
    operator fun invoke(itemId: Int): FeatureCItem? = repository.getItemById(itemId)
}
