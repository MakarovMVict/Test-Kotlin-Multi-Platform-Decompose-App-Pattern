package com.example.testkmpdecomposeapp.feature.b.impl.presentation

import com.example.testkmpdecomposeapp.feature.b.api.presentation.FeatureBDetailsIntent
import com.example.testkmpdecomposeapp.feature.b.api.presentation.FeatureBDetailsUiState
import com.example.testkmpdecomposeapp.feature.b.api.presentation.FeatureBDetailsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class FeatureBDetailsViewModelImpl(
    itemId: Int,
    private val onBack: () -> Unit
) : FeatureBDetailsViewModel {
    private val _uiState = MutableStateFlow(
        FeatureBDetailsUiState(title = "Feature B - Details #$itemId")
    )
    override val uiState: StateFlow<FeatureBDetailsUiState> = _uiState.asStateFlow()

    override fun onIntent(intent: FeatureBDetailsIntent) {
        when (intent) {
            FeatureBDetailsIntent.BackClicked -> onBack()
        }
    }

    override fun onBackClicked() {
        onIntent(FeatureBDetailsIntent.BackClicked)
    }
}
