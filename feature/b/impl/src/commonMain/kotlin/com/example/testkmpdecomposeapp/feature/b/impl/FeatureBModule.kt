package com.example.testkmpdecomposeapp.feature.b.impl

import com.example.testkmpdecomposeapp.feature.b.api.FeatureBApi
import org.koin.dsl.module

val featureBModule = module {
    single<FeatureBApi> { FeatureBImpl() }
    factory { (onOpen: (Int) -> Unit) ->
        FeatureBListViewModel(onOpen = onOpen)
    }
    factory { (itemId: Int, onBack: () -> Unit) ->
        FeatureBDetailsViewModel(itemId = itemId, onBack = onBack)
    }
}
