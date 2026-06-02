package com.example.testkmpdecomposeapp.feature.a.api.presentation

import kotlinx.coroutines.flow.StateFlow

data class FeatureAListUiState(
    val title: String = "Feature A - List",
    val items: List<Int> = listOf(1, 2)
)

sealed interface FeatureAListIntent {
    data class OpenItem(val itemId: Int) : FeatureAListIntent
}

interface FeatureAListViewModel {
    val uiState: StateFlow<FeatureAListUiState>
    fun onIntent(intent: FeatureAListIntent)
    fun onItemClicked(itemId: Int)
}

data class FeatureADetailsUiState(
    val title: String
)

sealed interface FeatureADetailsIntent {
    data object BackClicked : FeatureADetailsIntent
    data object OpenFeatureCConfirmClicked : FeatureADetailsIntent
}

interface FeatureADetailsViewModel {
    val uiState: StateFlow<FeatureADetailsUiState>
    fun onIntent(intent: FeatureADetailsIntent)
    fun onOpenFeatureCConfirmClicked()
    fun onBackClicked()
}
