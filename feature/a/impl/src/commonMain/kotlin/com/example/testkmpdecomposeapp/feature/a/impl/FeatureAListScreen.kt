package com.example.testkmpdecomposeapp.feature.a.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun FeatureAListScreen(viewModel: FeatureAListViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = state.title)
        state.items.forEach { itemId ->
            Button(onClick = { viewModel.onIntent(FeatureAListIntent.OpenItem(itemId)) }) {
                Text("Open A item $itemId")
            }
        }
    }
}
