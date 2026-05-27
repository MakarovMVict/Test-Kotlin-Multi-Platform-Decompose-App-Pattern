package com.example.testkmpdecomposeapp.android.features.a

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
import com.example.testkmpdecomposeapp.feature.a.api.presentation.FeatureADetailsIntent
import com.example.testkmpdecomposeapp.feature.a.api.presentation.FeatureADetailsViewModel

@Composable
internal fun FeatureADetailsScreen(viewModel: FeatureADetailsViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = state.title)
        Button(onClick = { viewModel.onIntent(FeatureADetailsIntent.OpenFeatureCConfirmClicked) }) {
            Text("Open Feature C confirm")
        }
        Button(onClick = { viewModel.onIntent(FeatureADetailsIntent.BackClicked) }) {
            Text("Back")
        }
    }
}
