package com.example.testkmpdecomposeapp.android

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.example.testkmpdecomposeapp.MainComponent
import com.example.testkmpdecomposeapp.android.features.a.FeatureAScreen
import com.example.testkmpdecomposeapp.android.features.b.FeatureBScreen
import com.example.testkmpdecomposeapp.android.features.c.FeatureCScreen
import com.example.testkmpdecomposeapp.core.common.MainTab

@Composable
internal fun MainScreen(component: MainComponent) {
    val selectedTab = component.selectedTab
    val stack by component.stack.subscribeAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == MainTab.A,
                    onClick = { component.onTabSelected(MainTab.A) },
                    icon = { Text("A") },
                    label = { Text("Feature A") }
                )
                NavigationBarItem(
                    selected = selectedTab == MainTab.B,
                    onClick = { component.onTabSelected(MainTab.B) },
                    icon = { Text("B") },
                    label = { Text("Feature B") }
                )
                NavigationBarItem(
                    selected = selectedTab == MainTab.C,
                    onClick = { component.onTabSelected(MainTab.C) },
                    icon = { Text("C") },
                    label = { Text("Feature C") }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (val child = stack.active.instance) {
                is MainComponent.Child.FeatureA -> FeatureAScreen(component = child.component)
                is MainComponent.Child.FeatureB -> FeatureBScreen(component = child.component)
                is MainComponent.Child.FeatureC -> FeatureCScreen(component = child.component)
            }
        }
    }
}
