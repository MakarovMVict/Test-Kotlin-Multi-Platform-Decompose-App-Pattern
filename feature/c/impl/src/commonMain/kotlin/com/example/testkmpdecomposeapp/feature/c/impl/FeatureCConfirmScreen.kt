package com.example.testkmpdecomposeapp.feature.c.impl

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
internal fun ConfirmScreen(viewModel: FeatureCConfirmViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = state.title)
        Button(
            onClick = { viewModel.onIntent(FeatureCConfirmIntent.DoneClicked) },
            enabled = state.canComplete
        ) {
            Text("Done")
        }
        Button(onClick = { viewModel.onIntent(FeatureCConfirmIntent.BackClicked) }) {
            Text("Back")
        }
    }
}
