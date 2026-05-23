package com.example.testkmpdecomposeapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.example.testkmpdecomposeapp.core.common.MainTab
import com.example.testkmpdecomposeapp.core.ui.AppTheme
import com.example.testkmpdecomposeapp.feature.a.api.FeatureAApi
import com.example.testkmpdecomposeapp.feature.auth.api.AuthFeatureApi
import com.example.testkmpdecomposeapp.feature.b.api.FeatureBApi
import com.example.testkmpdecomposeapp.feature.c.api.FeatureCApi
import kotlinx.coroutines.delay

@Composable
fun AppScreen() {
    initKoin()
    val lifecycle = remember { LifecycleRegistry() }
    val root = remember { AppRootComponent(DefaultComponentContext(lifecycle = lifecycle)) }

    AppTheme {
        Children(stack = root.stack) { child ->
            when (val instance = child.instance) {
                is AppRootComponent.Child.Splash -> SplashScreen(onFinished = instance.onFinished)
                is AppRootComponent.Child.Auth -> {
                    val authFeatureApi = rememberKoinInstance<AuthFeatureApi>()
                    authFeatureApi.Screen(onOutput = instance.onOutput)
                }
                is AppRootComponent.Child.Main -> MainScreen(component = instance.component)
            }
        }
    }
}

@Composable
private fun SplashScreen(onFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1200)
        onFinished()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Splash")
    }
}

@Composable
private fun MainScreen(component: MainComponent) {
    val featureAApi = rememberKoinInstance<FeatureAApi>()
    val featureBApi = rememberKoinInstance<FeatureBApi>()
    val featureCApi = rememberKoinInstance<FeatureCApi>()
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
                is MainComponent.Child.FeatureA -> featureAApi.Screen(component = child.component)
                is MainComponent.Child.FeatureB -> featureBApi.Screen(component = child.component)
                is MainComponent.Child.FeatureC -> featureCApi.Screen(component = child.component)
            }
        }
    }
}

@Composable
private inline fun <reified T : Any> rememberKoinInstance(): T {
    return remember { getKoinInstance<T>() }
}
