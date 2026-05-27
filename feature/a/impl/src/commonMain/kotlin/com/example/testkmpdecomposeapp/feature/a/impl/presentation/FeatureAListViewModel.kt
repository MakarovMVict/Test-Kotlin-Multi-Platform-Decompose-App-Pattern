package com.example.testkmpdecomposeapp.feature.a.impl.presentation

import com.example.testkmpdecomposeapp.feature.a.api.presentation.FeatureAListIntent
import com.example.testkmpdecomposeapp.feature.a.api.presentation.FeatureAListUiState
import com.example.testkmpdecomposeapp.feature.a.api.presentation.FeatureAListViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class FeatureAListViewModelImpl(
    private val onOpen: (Int) -> Unit
) : FeatureAListViewModel {
    private val _uiState = MutableStateFlow(FeatureAListUiState())
    override val uiState: StateFlow<FeatureAListUiState> = _uiState.asStateFlow()

    override fun onIntent(intent: FeatureAListIntent) {
        when (intent) {
            is FeatureAListIntent.OpenItem -> onOpen(intent.itemId)
        }
    }

    override fun onItemClicked(itemId: Int) {
        onIntent(FeatureAListIntent.OpenItem(itemId))
    }
}
