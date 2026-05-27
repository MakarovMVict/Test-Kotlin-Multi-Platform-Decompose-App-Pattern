package com.example.testkmpdecomposeapp.feature.c.impl.presentation

import com.example.testkmpdecomposeapp.feature.c.impl.domain.ConfirmFeatureCItemUseCase
import com.example.testkmpdecomposeapp.feature.c.impl.domain.GetFeatureCItemDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class FeatureCConfirmUiState(
    val title: String,
    val canComplete: Boolean
)

sealed interface FeatureCConfirmIntent {
    data object DoneClicked : FeatureCConfirmIntent
    data object BackClicked : FeatureCConfirmIntent
}

class FeatureCConfirmViewModel(
    itemId: Int,
    getItemDetailsUseCase: GetFeatureCItemDetailsUseCase,
    private val confirmItemUseCase: ConfirmFeatureCItemUseCase,
    private val onBack: () -> Unit,
    private val onDone: () -> Unit
) {
    private val targetItemId = itemId
    private val itemExists = getItemDetailsUseCase(itemId) != null
    private val _uiState = MutableStateFlow(
        FeatureCConfirmUiState(
            title = "Feature C - Confirm #$itemId",
            canComplete = itemExists
        )
    )
    val uiState: StateFlow<FeatureCConfirmUiState> = _uiState.asStateFlow()

    fun onIntent(intent: FeatureCConfirmIntent) {
        when (intent) {
            FeatureCConfirmIntent.BackClicked -> onBack()
            FeatureCConfirmIntent.DoneClicked -> {
                if (confirmItemUseCase(targetItemId)) {
                    onDone()
                }
            }
        }
    }

    fun onDoneClicked() {
        onIntent(FeatureCConfirmIntent.DoneClicked)
    }

    fun onBackClicked() {
        onIntent(FeatureCConfirmIntent.BackClicked)
    }
}
