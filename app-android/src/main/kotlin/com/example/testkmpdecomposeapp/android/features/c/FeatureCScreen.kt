package com.example.testkmpdecomposeapp.android.features.c

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.example.testkmpdecomposeapp.feature.c.api.FeatureCComponent
import com.example.testkmpdecomposeapp.feature.c.impl.presentation.FeatureCConfirmViewModel
import com.example.testkmpdecomposeapp.feature.c.impl.presentation.FeatureCDetailsViewModel
import com.example.testkmpdecomposeapp.feature.c.impl.presentation.FeatureCListViewModel
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
                FeatureCListScreen(viewModel = viewModel)
            }
            is FeatureCComponent.Child.Details -> {
                val viewModel = remember(child) {
                    GlobalContext.get().get<FeatureCDetailsViewModel> {
                        parametersOf(child.itemId, component::onBack, component::onOpenConfirm)
                    }
                }
                FeatureCDetailsScreen(viewModel = viewModel)
            }
            is FeatureCComponent.Child.Confirm -> {
                val viewModel = remember(child) {
                    GlobalContext.get().get<FeatureCConfirmViewModel> {
                        parametersOf(child.itemId, component::onBack, component::onDone)
                    }
                }
                FeatureCConfirmScreen(viewModel = viewModel)
            }
        }
    }
}
