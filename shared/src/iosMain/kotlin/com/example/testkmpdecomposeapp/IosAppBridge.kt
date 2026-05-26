package com.example.testkmpdecomposeapp

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.example.testkmpdecomposeapp.core.common.MainTab
import com.example.testkmpdecomposeapp.feature.a.api.FeatureAApi
import com.example.testkmpdecomposeapp.feature.a.api.FeatureAComponent
import com.example.testkmpdecomposeapp.feature.auth.api.AuthFeatureApi
import com.example.testkmpdecomposeapp.feature.b.api.FeatureBComponent
import com.example.testkmpdecomposeapp.feature.c.api.FeatureCComponent

data class IosAppState(
    val root: String,
    val selectedTab: String,
    val activeScreen: String,
    val activeItemId: Int
)

interface IosAppStateListener {
    fun onStateChanged(state: IosAppState)
}

class IosAppBridge {
    private val lifecycle = LifecycleRegistry()
    private val root: AppRootComponent
    private var listener: IosAppStateListener? = null

    init {
        initKoin()
        root = AppRootComponent(DefaultComponentContext(lifecycle = lifecycle))
    }

    fun setListener(listener: IosAppStateListener?) {
        this.listener = listener
        notifyState()
    }

    fun getState(): IosAppState = buildState()

    fun onSplashFinished() {
        val child = root.stack.value.active.instance as? AppRootComponent.Child.Splash ?: return
        child.onFinished()
        notifyState()
    }

    fun onAuthLogin() {
        val child = root.stack.value.active.instance as? AppRootComponent.Child.Auth ?: return
        child.onOutput(AuthFeatureApi.Output.Authorized)
        notifyState()
    }

    fun selectTabA() {
        currentMainComponent()?.onTabSelected(MainTab.A)
        notifyState()
    }

    fun selectTabB() {
        currentMainComponent()?.onTabSelected(MainTab.B)
        notifyState()
    }

    fun selectTabC() {
        currentMainComponent()?.onTabSelected(MainTab.C)
        notifyState()
    }

    fun openItem(itemId: Int) {
        when (val child = currentMainChild()) {
            is MainComponent.Child.FeatureA -> child.component.onOpenDetails(itemId)
            is MainComponent.Child.FeatureB -> child.component.onOpenDetails(itemId)
            is MainComponent.Child.FeatureC -> child.component.onItemClick(itemId)
            null -> Unit
        }
        notifyState()
    }

    fun onBack() {
        when (val child = currentMainChild()) {
            is MainComponent.Child.FeatureA -> child.component.onBack()
            is MainComponent.Child.FeatureB -> child.component.onBack()
            is MainComponent.Child.FeatureC -> child.component.onBack()
            null -> Unit
        }
        notifyState()
    }

    fun openConfirm() {
        val child = currentMainChild() as? MainComponent.Child.FeatureC ?: return
        child.component.onOpenConfirm()
        notifyState()
    }

    fun doneConfirm() {
        val child = currentMainChild() as? MainComponent.Child.FeatureC ?: return
        child.component.onDone()
        notifyState()
    }

    fun openFeatureCConfirmFromFeatureA() {
        val featureA = currentMainChild() as? MainComponent.Child.FeatureA ?: return
        val detailsChild = featureA.component.stack.value.active.instance as? FeatureAComponent.Child.Details ?: return
        featureA.component.onOpenFeatureCConfirm(detailsChild.itemId)
        notifyState()
    }

    private fun notifyState() {
        listener?.onStateChanged(buildState())
    }

    private fun currentMainComponent(): MainComponent? {
        val child = root.stack.value.active.instance as? AppRootComponent.Child.Main ?: return null
        return child.component
    }

    private fun currentMainChild(): MainComponent.Child? {
        val component = currentMainComponent() ?: return null
        return component.stack.value.active.instance
    }

    private fun buildState(): IosAppState {
        val rootChild = root.stack.value.active.instance
        return when (rootChild) {
            is AppRootComponent.Child.Splash -> IosAppState(
                root = "splash",
                selectedTab = "a",
                activeScreen = "list",
                activeItemId = 0
            )
            is AppRootComponent.Child.Auth -> IosAppState(
                root = "auth",
                selectedTab = "a",
                activeScreen = "list",
                activeItemId = 0
            )
            is AppRootComponent.Child.Main -> {
                val tab = rootChild.component.selectedTab.toIosTab()
                val child = rootChild.component.stack.value.active.instance

                when (child) {
                    is MainComponent.Child.FeatureA -> mapFeatureAState(tab, child.component)
                    is MainComponent.Child.FeatureB -> mapFeatureBState(tab, child.component)
                    is MainComponent.Child.FeatureC -> mapFeatureCState(tab, child.component)
                }
            }
        }
    }

    private fun mapFeatureAState(
        tab: String,
        component: FeatureAComponent
    ): IosAppState {
        return when (val child = component.stack.value.active.instance) {
            FeatureAComponent.Child.List -> IosAppState(
                root = "main",
                selectedTab = tab,
                activeScreen = "list",
                activeItemId = 0
            )
            is FeatureAComponent.Child.Details -> IosAppState(
                root = "main",
                selectedTab = tab,
                activeScreen = "details",
                activeItemId = child.itemId
            )
        }
    }

    private fun mapFeatureBState(
        tab: String,
        component: FeatureBComponent
    ): IosAppState {
        return when (val child = component.stack.value.active.instance) {
            FeatureBComponent.Child.List -> IosAppState(
                root = "main",
                selectedTab = tab,
                activeScreen = "list",
                activeItemId = 0
            )
            is FeatureBComponent.Child.Details -> IosAppState(
                root = "main",
                selectedTab = tab,
                activeScreen = "details",
                activeItemId = child.itemId
            )
        }
    }

    private fun mapFeatureCState(
        tab: String,
        component: FeatureCComponent
    ): IosAppState {
        return when (val child = component.stack.value.active.instance) {
            FeatureCComponent.Child.List -> IosAppState(
                root = "main",
                selectedTab = tab,
                activeScreen = "list",
                activeItemId = 0
            )
            is FeatureCComponent.Child.Details -> IosAppState(
                root = "main",
                selectedTab = tab,
                activeScreen = "details",
                activeItemId = child.itemId
            )
            is FeatureCComponent.Child.Confirm -> IosAppState(
                root = "main",
                selectedTab = tab,
                activeScreen = "confirm",
                activeItemId = child.itemId
            )
        }
    }

    private fun MainTab.toIosTab(): String {
        return when (this) {
            MainTab.A -> "a"
            MainTab.B -> "b"
            MainTab.C -> "c"
        }
    }
}
