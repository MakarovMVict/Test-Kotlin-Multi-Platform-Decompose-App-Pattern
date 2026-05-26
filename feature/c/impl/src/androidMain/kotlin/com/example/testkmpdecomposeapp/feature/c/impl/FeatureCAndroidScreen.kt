package com.example.testkmpdecomposeapp.feature.c.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.example.testkmpdecomposeapp.feature.c.api.FeatureCComponent
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf

@Composable
fun FeatureCScreen(component: FeatureCComponent) {
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
