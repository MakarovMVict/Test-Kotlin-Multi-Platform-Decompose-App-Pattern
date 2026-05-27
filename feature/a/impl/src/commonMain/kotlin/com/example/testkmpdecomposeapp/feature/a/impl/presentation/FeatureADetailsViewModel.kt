package com.example.testkmpdecomposeapp.feature.a.impl.presentation

import com.example.testkmpdecomposeapp.feature.a.api.presentation.FeatureADetailsIntent
import com.example.testkmpdecomposeapp.feature.a.api.presentation.FeatureADetailsUiState
import com.example.testkmpdecomposeapp.feature.a.api.presentation.FeatureADetailsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class FeatureADetailsViewModelImpl(
    itemId: Int,
    private val onBack: () -> Unit,
    private val onOpenFeatureCConfirm: (Int) -> Unit
) : FeatureADetailsViewModel {
    private val detailsItemId: Int = itemId
    private val _uiState = MutableStateFlow(
        FeatureADetailsUiState(title = "Feature A - Details #$itemId")
    )
    override val uiState: StateFlow<FeatureADetailsUiState> = _uiState.asStateFlow()

    override fun onIntent(intent: FeatureADetailsIntent) {
        when (intent) {
            FeatureADetailsIntent.BackClicked -> onBack()
            FeatureADetailsIntent.OpenFeatureCConfirmClicked -> onOpenFeatureCConfirm(detailsItemId)
        }
    }

    override fun onOpenFeatureCConfirmClicked() {
        onIntent(FeatureADetailsIntent.OpenFeatureCConfirmClicked)
    }

    override fun onBackClicked() {
        onIntent(FeatureADetailsIntent.BackClicked)
    }
}
