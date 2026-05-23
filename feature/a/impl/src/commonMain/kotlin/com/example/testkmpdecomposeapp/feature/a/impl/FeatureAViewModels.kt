package com.example.testkmpdecomposeapp.feature.a.impl

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal data class FeatureAListUiState(
    val title: String = "Feature A - List",
    val items: List<Int> = listOf(1, 2)
)

internal sealed interface FeatureAListIntent {
    data class OpenItem(val itemId: Int) : FeatureAListIntent
}

internal class FeatureAListViewModel(
    private val onOpen: (Int) -> Unit
) {
    private val _uiState = MutableStateFlow(FeatureAListUiState())
    val uiState: StateFlow<FeatureAListUiState> = _uiState.asStateFlow()

    fun onIntent(intent: FeatureAListIntent) {
        when (intent) {
            is FeatureAListIntent.OpenItem -> onOpen(intent.itemId)
        }
    }
}

internal data class FeatureADetailsUiState(
    val title: String
)

internal sealed interface FeatureADetailsIntent {
    data object BackClicked : FeatureADetailsIntent
    data object OpenFeatureCConfirmClicked : FeatureADetailsIntent
}

internal class FeatureADetailsViewModel(
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
}
