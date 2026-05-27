package com.example.testkmpdecomposeapp.feature.c.impl.presentation

import com.example.testkmpdecomposeapp.feature.c.impl.domain.GetFeatureCItemDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class FeatureCDetailsUiState(
    val title: String
)

sealed interface FeatureCDetailsIntent {
    data object OpenConfirmClicked : FeatureCDetailsIntent
    data object BackClicked : FeatureCDetailsIntent
}

class FeatureCDetailsViewModel(
    itemId: Int,
    getItemDetailsUseCase: GetFeatureCItemDetailsUseCase,
    private val onBack: () -> Unit,
    private val onOpenConfirm: () -> Unit
) {
    private val _uiState = MutableStateFlow(
        FeatureCDetailsUiState(
            title = getItemDetailsUseCase(itemId)?.title ?: "Feature C - Details #$itemId"
        )
    )
    val uiState: StateFlow<FeatureCDetailsUiState> = _uiState.asStateFlow()

    fun onIntent(intent: FeatureCDetailsIntent) {
        when (intent) {
            FeatureCDetailsIntent.OpenConfirmClicked -> onOpenConfirm()
            FeatureCDetailsIntent.BackClicked -> onBack()
        }
    }

    fun onOpenConfirmClicked() {
        onIntent(FeatureCDetailsIntent.OpenConfirmClicked)
    }

    fun onBackClicked() {
        onIntent(FeatureCDetailsIntent.BackClicked)
    }
}
