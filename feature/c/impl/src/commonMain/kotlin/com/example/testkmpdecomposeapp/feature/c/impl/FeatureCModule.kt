package com.example.testkmpdecomposeapp.feature.c.impl

import com.example.testkmpdecomposeapp.feature.c.api.FeatureCApi
import com.example.testkmpdecomposeapp.feature.c.impl.data.FeatureCRepositoryImpl
import com.example.testkmpdecomposeapp.feature.c.impl.domain.ConfirmFeatureCItemUseCase
import com.example.testkmpdecomposeapp.feature.c.impl.domain.FeatureCRepository
import com.example.testkmpdecomposeapp.feature.c.impl.domain.GetFeatureCItemDetailsUseCase
import com.example.testkmpdecomposeapp.feature.c.impl.domain.GetFeatureCItemsUseCase
import org.koin.dsl.module

val featureCModule = module {
    single<FeatureCApi> { FeatureCImpl() }
    single<FeatureCRepository> { FeatureCRepositoryImpl() }
    factory { GetFeatureCItemsUseCase(repository = get()) }
    factory { GetFeatureCItemDetailsUseCase(repository = get()) }
    factory { ConfirmFeatureCItemUseCase(repository = get()) }

    factory { (onOpen: (Int) -> Unit) ->
        FeatureCListViewModel(
            getItemsUseCase = get(),
            onOpen = onOpen
        )
    }
    factory { (itemId: Int, onBack: () -> Unit, onOpenConfirm: () -> Unit) ->
        FeatureCDetailsViewModel(
            itemId = itemId,
            getItemDetailsUseCase = get(),
            onBack = onBack,
            onOpenConfirm = onOpenConfirm
        )
    }
    factory { (itemId: Int, onBack: () -> Unit, onDone: () -> Unit) ->
        FeatureCConfirmViewModel(
            itemId = itemId,
            getItemDetailsUseCase = get(),
            confirmItemUseCase = get(),
            onBack = onBack,
            onDone = onDone
        )
    }
}
