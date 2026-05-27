package com.example.testkmpdecomposeapp.feature.a.impl.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class FeatureADetailsUiState(
    val title: String
)

sealed interface FeatureADetailsIntent {
    data object BackClicked : FeatureADetailsIntent
    data object OpenFeatureCConfirmClicked : FeatureADetailsIntent
}

class FeatureADetailsViewModel(
    itemId: Int,
    private val onBack: () -> Unit,
    private val onOpenFeatureCConfirm: (Int) -> Unit
) {
    private val detailsItemId: Int = itemId
    private val _uiState = MutableStateFlow(
        FeatureADetailsUiState(title = "Feature A - Details #$itemId")
    )
    val uiState: StateFlow<FeatureADetailsUiState> = _uiState.asStateFlow()

    fun onIntent(intent: FeatureADetailsIntent) {
        when (intent) {
            FeatureADetailsIntent.BackClicked -> onBack()
            FeatureADetailsIntent.OpenFeatureCConfirmClicked -> onOpenFeatureCConfirm(detailsItemId)
        }
    }

    fun onOpenFeatureCConfirmClicked() {
        onIntent(FeatureADetailsIntent.OpenFeatureCConfirmClicked)
    }

    fun onBackClicked() {
        onIntent(FeatureADetailsIntent.BackClicked)
    }
}
