package com.example.testkmpdecomposeapp.feature.b.impl

import com.example.testkmpdecomposeapp.feature.b.api.FeatureBApi
import com.example.testkmpdecomposeapp.feature.b.api.presentation.FeatureBDetailsViewModel
import com.example.testkmpdecomposeapp.feature.b.api.presentation.FeatureBListViewModel
import com.example.testkmpdecomposeapp.feature.b.impl.presentation.FeatureBDetailsViewModelImpl
import com.example.testkmpdecomposeapp.feature.b.impl.presentation.FeatureBListViewModelImpl
import org.koin.dsl.module

val featureBModule = module {
    single<FeatureBApi> { FeatureBImpl() }
    factory<FeatureBListViewModel> { (onOpen: (Int) -> Unit) ->
        FeatureBListViewModelImpl(onOpen = onOpen)
    }
    factory<FeatureBDetailsViewModel> { (itemId: Int, onBack: () -> Unit) ->
        FeatureBDetailsViewModelImpl(itemId = itemId, onBack = onBack)
    }
}
