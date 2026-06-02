package com.example.testkmpdecomposeapp.feature.b.api.presentation

import kotlinx.coroutines.flow.StateFlow

data class FeatureBListUiState(
    val title: String = "Feature B - List",
    val items: List<Int> = listOf(1, 2)
)

sealed interface FeatureBListIntent {
    data class OpenItem(val itemId: Int) : FeatureBListIntent
}

interface FeatureBListViewModel {
    val uiState: StateFlow<FeatureBListUiState>
    fun onIntent(intent: FeatureBListIntent)
    fun onItemClicked(itemId: Int)
}

data class FeatureBDetailsUiState(
    val title: String
)

sealed interface FeatureBDetailsIntent {
    data object BackClicked : FeatureBDetailsIntent
}

interface FeatureBDetailsViewModel {
    val uiState: StateFlow<FeatureBDetailsUiState>
    fun onIntent(intent: FeatureBDetailsIntent)
    fun onBackClicked()
}
