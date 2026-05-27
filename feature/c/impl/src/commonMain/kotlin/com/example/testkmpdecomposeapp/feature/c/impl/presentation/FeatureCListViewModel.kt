package com.example.testkmpdecomposeapp.feature.c.impl.presentation

import com.example.testkmpdecomposeapp.feature.c.impl.FeatureCNotificationManager
import com.example.testkmpdecomposeapp.feature.c.impl.domain.GetFeatureCItemsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class FeatureCListUiState(
    val title: String = "Feature C - List",
    val items: List<Int> = emptyList()
)

sealed interface FeatureCListIntent {
    data class OpenItem(val itemId: Int) : FeatureCListIntent
    data class ShowNotification(val itemId: Int) : FeatureCListIntent
}

class FeatureCListViewModel(
    getItemsUseCase: GetFeatureCItemsUseCase,
    private val notificationManager: FeatureCNotificationManager,
    private val onOpen: (Int) -> Unit
) {
    private val _uiState = MutableStateFlow(
        FeatureCListUiState(items = getItemsUseCase().map { it.id })
    )
    val uiState: StateFlow<FeatureCListUiState> = _uiState.asStateFlow()

    fun onIntent(intent: FeatureCListIntent) {
        when (intent) {
            is FeatureCListIntent.OpenItem -> onOpen(intent.itemId)
            is FeatureCListIntent.ShowNotification -> notificationManager.showTestNotification(
                intent.itemId
            )
        }
    }

    fun onItemClicked(itemId: Int) {
        onIntent(FeatureCListIntent.OpenItem(itemId))
    }

    fun onShowNotificationClicked(itemId: Int = 1) {
        onIntent(FeatureCListIntent.ShowNotification(itemId))
    }
}
