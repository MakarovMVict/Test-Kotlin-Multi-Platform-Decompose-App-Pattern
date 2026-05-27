package com.example.testkmpdecomposeapp.android.features.b

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
import com.example.testkmpdecomposeapp.feature.b.impl.presentation.FeatureBListIntent
import com.example.testkmpdecomposeapp.feature.b.impl.presentation.FeatureBListViewModel

@Composable
internal fun FeatureBListScreen(viewModel: FeatureBListViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = state.title)
        state.items.forEach { itemId ->
            Button(onClick = { viewModel.onIntent(FeatureBListIntent.OpenItem(itemId)) }) {
                Text("Open B item $itemId")
            }
        }
    }
}
