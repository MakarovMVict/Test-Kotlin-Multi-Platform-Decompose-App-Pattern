package com.example.testkmpdecomposeapp.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.example.testkmpdecomposeapp.AppRootComponent
import com.example.testkmpdecomposeapp.core.ui.AppTheme
import com.example.testkmpdecomposeapp.initKoin
import com.example.testkmpdecomposeapp.android.features.auth.AuthFeatureScreen
import org.koin.dsl.module

@Composable
fun AppScreen() {
    val applicationContext = LocalContext.current.applicationContext
    val androidPlatformModule = remember(applicationContext) {
        module {
            single { applicationContext }
        }
    }
    initKoin(platformModules = listOf(androidPlatformModule))
    val lifecycle = remember { LifecycleRegistry() }
    val root = remember { AppRootComponent(DefaultComponentContext(lifecycle = lifecycle)) }

    AppTheme {
        Children(stack = root.stack) { child ->
            when (val instance = child.instance) {
                is AppRootComponent.Child.Splash -> SplashScreen(onFinished = instance.onFinished)
                is AppRootComponent.Child.Auth ->
                    AuthFeatureScreen(onOutput = instance.onOutput)
                is AppRootComponent.Child.Main -> MainScreen(component = instance.component)
            }
        }
    }
}
