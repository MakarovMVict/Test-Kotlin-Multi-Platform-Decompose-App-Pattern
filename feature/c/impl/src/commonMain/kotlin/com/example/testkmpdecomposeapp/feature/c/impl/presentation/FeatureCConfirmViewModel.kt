package com.example.testkmpdecomposeapp.feature.c.impl.presentation

import com.example.testkmpdecomposeapp.feature.c.api.presentation.FeatureCConfirmIntent
import com.example.testkmpdecomposeapp.feature.c.api.presentation.FeatureCConfirmUiState
import com.example.testkmpdecomposeapp.feature.c.api.presentation.FeatureCConfirmViewModel
import com.example.testkmpdecomposeapp.feature.c.impl.domain.ConfirmFeatureCItemUseCase
import com.example.testkmpdecomposeapp.feature.c.impl.domain.GetFeatureCItemDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class FeatureCConfirmViewModelImpl(
    itemId: Int,
    getItemDetailsUseCase: GetFeatureCItemDetailsUseCase,
    private val confirmItemUseCase: ConfirmFeatureCItemUseCase,
    private val onBack: () -> Unit,
    private val onDone: () -> Unit
) : FeatureCConfirmViewModel {
    private val targetItemId = itemId
    private val itemExists = getItemDetailsUseCase(itemId) != null
    private val _uiState = MutableStateFlow(
        FeatureCConfirmUiState(
            title = "Feature C - Confirm #$itemId",
            canComplete = itemExists
        )
    )
    override val uiState: StateFlow<FeatureCConfirmUiState> = _uiState.asStateFlow()

    override fun onIntent(intent: FeatureCConfirmIntent) {
        when (intent) {
            FeatureCConfirmIntent.BackClicked -> onBack()
            FeatureCConfirmIntent.DoneClicked -> {
                if (confirmItemUseCase(targetItemId)) {
                    onDone()
                }
            }
        }
    }

    override fun onDoneClicked() {
        onIntent(FeatureCConfirmIntent.DoneClicked)
    }

    override fun onBackClicked() {
        onIntent(FeatureCConfirmIntent.BackClicked)
    }
}
