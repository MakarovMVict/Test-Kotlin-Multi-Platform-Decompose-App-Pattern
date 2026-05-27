package com.example.testkmpdecomposeapp.feature.c.api.presentation

import kotlinx.coroutines.flow.StateFlow

data class FeatureCListUiState(
    val title: String = "Feature C - List",
    val items: List<Int> = emptyList()
)

sealed interface FeatureCListIntent {
    data class OpenItem(val itemId: Int) : FeatureCListIntent
    data class ShowNotification(val itemId: Int) : FeatureCListIntent
}

interface FeatureCListViewModel {
    val uiState: StateFlow<FeatureCListUiState>
    fun onIntent(intent: FeatureCListIntent)
    fun onItemClicked(itemId: Int)
    fun onShowNotificationClicked(itemId: Int = 1)
}

data class FeatureCDetailsUiState(
    val title: String
)

sealed interface FeatureCDetailsIntent {
    data object OpenConfirmClicked : FeatureCDetailsIntent
    data object BackClicked : FeatureCDetailsIntent
}

interface FeatureCDetailsViewModel {
    val uiState: StateFlow<FeatureCDetailsUiState>
    fun onIntent(intent: FeatureCDetailsIntent)
    fun onOpenConfirmClicked()
    fun onBackClicked()
}

data class FeatureCConfirmUiState(
    val title: String,
    val canComplete: Boolean
)

sealed interface FeatureCConfirmIntent {
    data object DoneClicked : FeatureCConfirmIntent
    data object BackClicked : FeatureCConfirmIntent
}

interface FeatureCConfirmViewModel {
    val uiState: StateFlow<FeatureCConfirmUiState>
    fun onIntent(intent: FeatureCConfirmIntent)
    fun onDoneClicked()
    fun onBackClicked()
}
