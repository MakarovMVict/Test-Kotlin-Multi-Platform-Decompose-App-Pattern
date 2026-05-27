package com.example.testkmpdecomposeapp.feature.a.impl.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class FeatureAListUiState(
    val title: String = "Feature A - List",
    val items: List<Int> = listOf(1, 2)
)

sealed interface FeatureAListIntent {
    data class OpenItem(val itemId: Int) : FeatureAListIntent
}

class FeatureAListViewModel(
    private val onOpen: (Int) -> Unit
) {
    private val _uiState = MutableStateFlow(FeatureAListUiState())
    val uiState: StateFlow<FeatureAListUiState> = _uiState.asStateFlow()

    fun onIntent(intent: FeatureAListIntent) {
        when (intent) {
            is FeatureAListIntent.OpenItem -> onOpen(intent.itemId)
        }
    }

    fun onItemClicked(itemId: Int) {
        onIntent(FeatureAListIntent.OpenItem(itemId))
    }
}
