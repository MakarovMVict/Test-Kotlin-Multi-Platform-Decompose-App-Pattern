package com.example.testkmpdecomposeapp.android.features.c

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
import com.example.testkmpdecomposeapp.feature.c.api.presentation.FeatureCConfirmIntent
import com.example.testkmpdecomposeapp.feature.c.api.presentation.FeatureCConfirmViewModel

@Composable
internal fun FeatureCConfirmScreen(viewModel: FeatureCConfirmViewModel) {
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
