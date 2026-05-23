package com.example.testkmpdecomposeapp.feature.c.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.example.testkmpdecomposeapp.feature.c.api.FeatureCApi
import com.example.testkmpdecomposeapp.feature.c.api.FeatureCComponent
import kotlinx.serialization.Serializable
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf

class FeatureCImpl : FeatureCApi {
    override fun create(componentContext: ComponentContext): FeatureCComponent {
        return DefaultFeatureCComponent(componentContext)
    }

    @Composable
    override fun Screen(component: FeatureCComponent) {
        Children(stack = component.stack) {
            when (val child = it.instance) {
                FeatureCComponent.Child.List -> {
                    val viewModel = remember(child) {
                        GlobalContext.get().get<FeatureCListViewModel> {
                            parametersOf(component::onItemClick)
                        }
                    }
                    ListScreen(viewModel = viewModel)
                }
                is FeatureCComponent.Child.Details -> {
                    val viewModel = remember(child) {
                        GlobalContext.get().get<FeatureCDetailsViewModel> {
                            parametersOf(child.itemId, component::onBack, component::onOpenConfirm)
                        }
                    }
                    DetailsScreen(viewModel = viewModel)
                }
                is FeatureCComponent.Child.Confirm -> {
                    val viewModel = remember(child) {
                        GlobalContext.get().get<FeatureCConfirmViewModel> {
                            parametersOf(child.itemId, component::onBack, component::onDone)
                        }
                    }
                    ConfirmScreen(viewModel = viewModel)
                }
            }
        }
    }
}

private class DefaultFeatureCComponent(componentContext: ComponentContext) :
    FeatureCComponent,
    ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    private var selectedItemId: Int = 1

    override val stack: Value<ChildStack<*, FeatureCComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.List,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(config: Config, componentContext: ComponentContext): FeatureCComponent.Child {
        return when (config) {
            Config.List -> FeatureCComponent.Child.List
            is Config.Details -> FeatureCComponent.Child.Details(itemId = config.itemId)
            is Config.Confirm -> FeatureCComponent.Child.Confirm(itemId = config.itemId)
        }
    }

    override fun onItemClick(itemId: Int) {
        selectedItemId = itemId
        navigation.push(Config.Details(itemId = itemId))
    }

    override fun openConfirm(itemId: Int) {
        selectedItemId = itemId
        navigation.replaceAll(
            Config.List,
            Config.Details(itemId = itemId),
            Config.Confirm(itemId = itemId)
        )
    }

    override fun onOpenConfirm() {
        navigation.push(Config.Confirm(itemId = selectedItemId))
    }

    override fun onDone() {
        navigation.replaceAll(Config.List)
    }

    override fun onBack() {
        navigation.pop()
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object List : Config

        @Serializable
        data class Details(val itemId: Int) : Config

        @Serializable
        data class Confirm(val itemId: Int) : Config
    }
}
