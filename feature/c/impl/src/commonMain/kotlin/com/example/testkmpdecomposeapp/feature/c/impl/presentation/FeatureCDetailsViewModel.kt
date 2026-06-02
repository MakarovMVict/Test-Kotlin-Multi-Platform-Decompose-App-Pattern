package com.example.testkmpdecomposeapp.feature.c.impl.presentation

import com.example.testkmpdecomposeapp.feature.c.api.presentation.FeatureCDetailsIntent
import com.example.testkmpdecomposeapp.feature.c.api.presentation.FeatureCDetailsUiState
import com.example.testkmpdecomposeapp.feature.c.api.presentation.FeatureCDetailsViewModel
import com.example.testkmpdecomposeapp.feature.c.impl.domain.GetFeatureCItemDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class FeatureCDetailsViewModelImpl(
    itemId: Int,
    getItemDetailsUseCase: GetFeatureCItemDetailsUseCase,
    private val onBack: () -> Unit,
    private val onOpenConfirm: () -> Unit
) : FeatureCDetailsViewModel {
    private val _uiState = MutableStateFlow(
        FeatureCDetailsUiState(
            title = getItemDetailsUseCase(itemId)?.title ?: "Feature C - Details #$itemId"
        )
    )
    override val uiState: StateFlow<FeatureCDetailsUiState> = _uiState.asStateFlow()

    override fun onIntent(intent: FeatureCDetailsIntent) {
        when (intent) {
            FeatureCDetailsIntent.OpenConfirmClicked -> onOpenConfirm()
            FeatureCDetailsIntent.BackClicked -> onBack()
        }
    }

    override fun onOpenConfirmClicked() {
        onIntent(FeatureCDetailsIntent.OpenConfirmClicked)
    }

    override fun onBackClicked() {
        onIntent(FeatureCDetailsIntent.BackClicked)
    }
}
