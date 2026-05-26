import SwiftUI
import Foundation
import shared

final class AppStore: ObservableObject {
    @Published var state: IosAppState

    private let bridge: IosAppBridge
    private lazy var listener = AppStateListener { [weak self] state in
        DispatchQueue.main.async {
            self?.state = state
        }
    }

    init() {
        let bridge = IosAppBridge()
        self.bridge = bridge
        self.state = bridge.getState()
        bridge.setListener(listener: listener)
    }

    deinit {
        bridge.setListener(listener: nil)
    }

    func onSplashFinished() {
        bridge.onSplashFinished()
    }

    func onAuthLogin() {
        bridge.onAuthLogin()
    }

    func selectTab(_ tab: String) {
        switch tab {
        case "a":
            bridge.selectTabA()
        case "b":
            bridge.selectTabB()
        case "c":
            bridge.selectTabC()
        default:
            break
        }
    }

    func openItem(_ itemId: Int) {
        bridge.openItem(itemId: Int32(itemId))
    }

    func onBack() {
        bridge.onBack()
    }

    func openConfirm() {
        bridge.openConfirm()
    }

    func doneConfirm() {
        bridge.doneConfirm()
    }

    func openFeatureCConfirmFromFeatureA() {
        bridge.openFeatureCConfirmFromFeatureA()
    }
}

private final class AppStateListener: NSObject, IosAppStateListener {
    private let onStateChangedCallback: (IosAppState) -> Void

    init(onStateChangedCallback: @escaping (IosAppState) -> Void) {
        self.onStateChangedCallback = onStateChangedCallback
    }

    func onStateChanged(state: IosAppState) {
        onStateChangedCallback(state)
    }
}

struct RootView: View {
    @StateObject private var store = AppStore()

    var body: some View {
        switch store.state.root {
        case "splash":
            SplashView(onFinished: store.onSplashFinished)
        case "auth":
            AuthView(onLoginSuccess: store.onAuthLogin)
        case "main":
            MainTabView(
                state: store.state,
                onSelectTab: store.selectTab,
                onOpenItem: store.openItem,
                onBack: store.onBack,
                onOpenConfirm: store.openConfirm,
                onDoneConfirm: store.doneConfirm,
                onOpenFeatureCConfirmFromFeatureA: store.openFeatureCConfirmFromFeatureA
            )
        default:
            Text("Unknown state")
        }
    }
}
