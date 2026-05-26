package com.example.testkmpdecomposeapp.feature.c.impl

import com.example.testkmpdecomposeapp.feature.c.impl.domain.ConfirmFeatureCItemUseCase
import com.example.testkmpdecomposeapp.feature.c.impl.domain.GetFeatureCItemDetailsUseCase
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
}

class FeatureCListViewModel(
    getItemsUseCase: GetFeatureCItemsUseCase,
    private val onOpen: (Int) -> Unit
) {
    private val _uiState = MutableStateFlow(
        FeatureCListUiState(items = getItemsUseCase().map { it.id })
    )
    val uiState: StateFlow<FeatureCListUiState> = _uiState.asStateFlow()

    fun onIntent(intent: FeatureCListIntent) {
        when (intent) {
            is FeatureCListIntent.OpenItem -> onOpen(intent.itemId)
        }
    }
}

data class FeatureCDetailsUiState(
    val title: String
)

sealed interface FeatureCDetailsIntent {
    data object OpenConfirmClicked : FeatureCDetailsIntent
    data object BackClicked : FeatureCDetailsIntent
}

class FeatureCDetailsViewModel(
    itemId: Int,
    getItemDetailsUseCase: GetFeatureCItemDetailsUseCase,
    private val onBack: () -> Unit,
    private val onOpenConfirm: () -> Unit
) {
    private val _uiState = MutableStateFlow(
        FeatureCDetailsUiState(
            title = getItemDetailsUseCase(itemId)?.title ?: "Feature C - Details #$itemId"
        )
    )
    val uiState: StateFlow<FeatureCDetailsUiState> = _uiState.asStateFlow()

    fun onIntent(intent: FeatureCDetailsIntent) {
        when (intent) {
            FeatureCDetailsIntent.OpenConfirmClicked -> onOpenConfirm()
            FeatureCDetailsIntent.BackClicked -> onBack()
        }
    }
}

data class FeatureCConfirmUiState(
    val title: String,
    val canComplete: Boolean
)

sealed interface FeatureCConfirmIntent {
    data object DoneClicked : FeatureCConfirmIntent
    data object BackClicked : FeatureCConfirmIntent
}

class FeatureCConfirmViewModel(
    itemId: Int,
    getItemDetailsUseCase: GetFeatureCItemDetailsUseCase,
    private val confirmItemUseCase: ConfirmFeatureCItemUseCase,
    private val onBack: () -> Unit,
    private val onDone: () -> Unit
) {
    private val targetItemId = itemId
    private val itemExists = getItemDetailsUseCase(itemId) != null
    private val _uiState = MutableStateFlow(
        FeatureCConfirmUiState(
            title = "Feature C - Confirm #$itemId",
            canComplete = itemExists
        )
    )
    val uiState: StateFlow<FeatureCConfirmUiState> = _uiState.asStateFlow()

    fun onIntent(intent: FeatureCConfirmIntent) {
        when (intent) {
            FeatureCConfirmIntent.BackClicked -> onBack()
            FeatureCConfirmIntent.DoneClicked -> {
                if (confirmItemUseCase(targetItemId)) {
                    onDone()
                }
            }
        }
    }
}
