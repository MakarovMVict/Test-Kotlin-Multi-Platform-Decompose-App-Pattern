package com.example.testkmpdecomposeapp.feature.c.impl.domain

class ConfirmFeatureCItemUseCase(
    private val repository: FeatureCRepository
) {
    operator fun invoke(itemId: Int): Boolean = repository.confirmItem(itemId)
}
