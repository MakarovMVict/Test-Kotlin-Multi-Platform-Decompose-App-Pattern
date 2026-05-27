package com.example.testkmpdecomposeapp.feature.b.impl.presentation

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
