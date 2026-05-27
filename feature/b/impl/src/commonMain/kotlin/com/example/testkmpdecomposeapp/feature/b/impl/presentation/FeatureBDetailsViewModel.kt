package com.example.testkmpdecomposeapp.feature.b.impl.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class FeatureBDetailsUiState(
    val title: String
)

sealed interface FeatureBDetailsIntent {
    data object BackClicked : FeatureBDetailsIntent
}

class FeatureBDetailsViewModel(
    itemId: Int,
    private val onBack: () -> Unit
) {
    private val _uiState = MutableStateFlow(
        FeatureBDetailsUiState(title = "Feature B - Details #$itemId")
    )
    val uiState: StateFlow<FeatureBDetailsUiState> = _uiState.asStateFlow()

    fun onIntent(intent: FeatureBDetailsIntent) {
        when (intent) {
            FeatureBDetailsIntent.BackClicked -> onBack()
        }
    }

    fun onBackClicked() {
        onIntent(FeatureBDetailsIntent.BackClicked)
    }
}
