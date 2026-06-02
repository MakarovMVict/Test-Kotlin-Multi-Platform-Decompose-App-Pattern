package com.example.testkmpdecomposeapp.feature.b.impl.presentation

import com.example.testkmpdecomposeapp.feature.b.api.presentation.FeatureBListIntent
import com.example.testkmpdecomposeapp.feature.b.api.presentation.FeatureBListUiState
import com.example.testkmpdecomposeapp.feature.b.api.presentation.FeatureBListViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class FeatureBListViewModelImpl(
    private val onOpen: (Int) -> Unit
) : FeatureBListViewModel {
    private val _uiState = MutableStateFlow(FeatureBListUiState())
    override val uiState: StateFlow<FeatureBListUiState> = _uiState.asStateFlow()

    override fun onIntent(intent: FeatureBListIntent) {
        when (intent) {
            is FeatureBListIntent.OpenItem -> onOpen(intent.itemId)
        }
    }

    override fun onItemClicked(itemId: Int) {
        onIntent(FeatureBListIntent.OpenItem(itemId))
    }
}
