package com.example.testkmpdecomposeapp.feature.b.impl

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class FeatureBListUiState(
    val title: String = "Feature B - List",
    val items: List<Int> = listOf(1, 2)
)

sealed interface FeatureBListIntent {
    data class OpenItem(val itemId: Int) : FeatureBListIntent
}

class FeatureBListViewModel(
    private val onOpen: (Int) -> Unit
) {
    private val _uiState = MutableStateFlow(FeatureBListUiState())
    val uiState: StateFlow<FeatureBListUiState> = _uiState.asStateFlow()

    fun onIntent(intent: FeatureBListIntent) {
        when (intent) {
            is FeatureBListIntent.OpenItem -> onOpen(intent.itemId)
        }
    }

    fun onItemClicked(itemId: Int) {
        onIntent(FeatureBListIntent.OpenItem(itemId))
    }
}

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
