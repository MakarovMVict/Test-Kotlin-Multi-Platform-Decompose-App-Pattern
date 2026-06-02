package com.example.testkmpdecomposeapp.feature.c.impl.presentation

import com.example.testkmpdecomposeapp.feature.c.impl.FeatureCNotificationManager
import com.example.testkmpdecomposeapp.feature.c.api.presentation.FeatureCListIntent
import com.example.testkmpdecomposeapp.feature.c.api.presentation.FeatureCListUiState
import com.example.testkmpdecomposeapp.feature.c.api.presentation.FeatureCListViewModel
import com.example.testkmpdecomposeapp.feature.c.impl.domain.GetFeatureCItemsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class FeatureCListViewModelImpl(
    getItemsUseCase: GetFeatureCItemsUseCase,
    private val notificationManager: FeatureCNotificationManager,
    private val onOpen: (Int) -> Unit
) : FeatureCListViewModel {
    private val _uiState = MutableStateFlow(
        FeatureCListUiState(items = getItemsUseCase().map { it.id })
    )
    override val uiState: StateFlow<FeatureCListUiState> = _uiState.asStateFlow()

    override fun onIntent(intent: FeatureCListIntent) {
        when (intent) {
            is FeatureCListIntent.OpenItem -> onOpen(intent.itemId)
            is FeatureCListIntent.ShowNotification -> notificationManager.showTestNotification(
                intent.itemId
            )
        }
    }

    override fun onItemClicked(itemId: Int) {
        onIntent(FeatureCListIntent.OpenItem(itemId))
    }

    override fun onShowNotificationClicked(itemId: Int) {
        onIntent(FeatureCListIntent.ShowNotification(itemId))
    }
}
