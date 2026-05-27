package com.example.testkmpdecomposeapp

import com.example.testkmpdecomposeapp.feature.a.impl.featureAModule
import com.example.testkmpdecomposeapp.feature.auth.impl.authFeatureModule
import com.example.testkmpdecomposeapp.feature.b.impl.featureBModule
import com.example.testkmpdecomposeapp.feature.c.impl.featureCModule
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.parameter.ParametersDefinition
import kotlin.reflect.KClass

private val sharedModules: List<Module> = listOf(
    authFeatureModule,
    featureAModule,
    featureBModule,
    featureCModule
)

private var appKoin: Koin? = null

fun initKoin(platformModules: List<Module> = emptyList()) {
    if (appKoin == null) {
        appKoin = startKoin {
            modules(sharedModules + platformModules)
        }.koin
    }
}

inline fun <reified T : Any> getKoinInstance(): T {
    return getKoinInstance(T::class)
}

inline fun <reified T : Any> getKoinInstance(noinline parameters: ParametersDefinition?): T {
    return getKoinInstance(clazz = T::class, parameters = parameters)
}

fun <T : Any> getKoinInstance(clazz: KClass<T>): T {
    return requireNotNull(appKoin) { "Koin is not initialized. Call initKoin() first." }.get(clazz = clazz)
}

fun <T : Any> getKoinInstance(clazz: KClass<T>, parameters: ParametersDefinition?): T {
    return requireNotNull(appKoin) { "Koin is not initialized. Call initKoin() first." }
        .get(clazz = clazz, parameters = parameters)
}
