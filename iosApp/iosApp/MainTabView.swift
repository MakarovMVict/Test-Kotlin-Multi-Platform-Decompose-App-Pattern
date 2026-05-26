import SwiftUI
import shared

struct MainTabView: View {
    let state: IosAppState
    let onSelectTab: (String) -> Void
    let onOpenItem: (Int) -> Void
    let onBack: () -> Void
    let onOpenConfirm: () -> Void
    let onDoneConfirm: () -> Void
    let onOpenFeatureCConfirmFromFeatureA: () -> Void

    var body: some View {
        VStack(spacing: 0) {
            featureContent
                .frame(maxWidth: .infinity, maxHeight: .infinity)

            Divider()

            HStack {
                tabButton(title: "Feature A", tab: "a")
                tabButton(title: "Feature B", tab: "b")
                tabButton(title: "Feature C", tab: "c")
            }
            .padding(.vertical, 10)
        }
    }

    @ViewBuilder
    private var featureContent: some View {
        switch state.selectedTab {
        case "a":
            featureAContent
        case "b":
            featureBContent
        default:
            featureCContent
        }
    }

    @ViewBuilder
    private var featureAContent: some View {
        VStack(spacing: 16) {
            if state.activeScreen == "list" {
                Text("Feature A - List")
                Button("Open A item 1") { onOpenItem(1) }
                Button("Open A item 2") { onOpenItem(2) }
            } else {
                Text("Feature A - Details #\(state.activeItemId)")
                Button("Open Feature C confirm") {
                    onOpenFeatureCConfirmFromFeatureA()
                }
                Button("Back") { onBack() }
            }
        }
    }

    @ViewBuilder
    private var featureBContent: some View {
        VStack(spacing: 16) {
            if state.activeScreen == "list" {
                Text("Feature B - List")
                Button("Open B item 1") { onOpenItem(1) }
                Button("Open B item 2") { onOpenItem(2) }
            } else {
                Text("Feature B - Details #\(state.activeItemId)")
                Button("Back") { onBack() }
            }
        }
    }

    @ViewBuilder
    private var featureCContent: some View {
        VStack(spacing: 16) {
            switch state.activeScreen {
            case "list":
                Text("Feature C - List")
                Button("Open item 1") { onOpenItem(1) }
                Button("Open item 2") { onOpenItem(2) }
            case "details":
                Text("Feature C - Item \(state.activeItemId)")
                Button("Go to confirm") { onOpenConfirm() }
                Button("Back") { onBack() }
            default:
                Text("Feature C - Confirm #\(state.activeItemId)")
                Button("Done") { onDoneConfirm() }
                Button("Back") { onBack() }
            }
        }
    }

    private func tabButton(title: String, tab: String) -> some View {
        Button(action: { onSelectTab(tab) }) {
            Text(title)
                .frame(maxWidth: .infinity)
        }
        .buttonStyle(.borderless)
    }
}
