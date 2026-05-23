package com.example.testkmpdecomposeapp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.example.testkmpdecomposeapp.core.common.AppDestination
import com.example.testkmpdecomposeapp.core.common.AppNavigator
import com.example.testkmpdecomposeapp.feature.a.api.FeatureAApi
import com.example.testkmpdecomposeapp.feature.auth.api.AuthFeatureApi
import com.example.testkmpdecomposeapp.feature.b.api.FeatureBApi
import com.example.testkmpdecomposeapp.feature.c.api.FeatureCApi
import kotlinx.serialization.Serializable

class AppRootComponent(componentContext: ComponentContext) : ComponentContext by componentContext, AppNavigator {
    private val navigation = StackNavigation<Config>()
    private var pendingMainDestination: AppDestination? = null

    val stack: Value<ChildStack<Config, Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Splash,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(config: Config, componentContext: ComponentContext): Child {
        return when (config) {
            Config.Splash -> Child.Splash(onFinished = ::onSplashFinished)
            Config.Auth -> Child.Auth(onOutput = ::onAuthOutput)
            Config.Main -> Child.Main(
                MainComponent(
                    componentContext = componentContext,
                    featureAApi = getKoinInstance<FeatureAApi>(),
                    featureBApi = getKoinInstance<FeatureBApi>(),
                    featureCApi = getKoinInstance<FeatureCApi>(),
                    appNavigator = this,
                    initialDestination = pendingMainDestination.also { pendingMainDestination = null }
                )
            )
        }
    }

    private fun onSplashFinished() {
        navigate(AppDestination.Auth)
    }

    private fun onAuthOutput(output: AuthFeatureApi.Output) {
        when (output) {
            AuthFeatureApi.Output.Authorized -> navigate(AppDestination.Main)
        }
    }

    override fun navigate(destination: AppDestination) {
        when (destination) {
            AppDestination.Splash -> navigation.replaceAll(Config.Splash)
            AppDestination.Auth -> navigation.replaceAll(Config.Auth)
            AppDestination.Main -> navigation.replaceAll(Config.Main)
            AppDestination.FeatureA,
            AppDestination.FeatureB,
            AppDestination.FeatureC,
            is AppDestination.FeatureCConfirm -> {
                val currentChild = stack.value.active.instance
                if (currentChild is Child.Main) {
                    currentChild.component.navigate(destination)
                } else {
                    pendingMainDestination = destination
                    navigation.replaceAll(Config.Main)
                }
            }
        }
    }

    sealed interface Child {
        class Splash(val onFinished: () -> Unit) : Child
        class Auth(val onOutput: (AuthFeatureApi.Output) -> Unit) : Child
        class Main(val component: MainComponent) : Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object Splash : Config

        @Serializable
        data object Auth : Config

        @Serializable
        data object Main : Config
    }
}
