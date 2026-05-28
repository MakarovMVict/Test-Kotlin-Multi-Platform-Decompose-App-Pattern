import SwiftUI
import shared

public struct FeatureCListView: View {
    public let viewModel: FeatureCListViewModel

    public init(viewModel: FeatureCListViewModel) {
        self.viewModel = viewModel
    }

    public var body: some View {
        VStack(spacing: 16) {
            Text(viewModel.state.title)
            Button("Show test notification") {
                viewModel.onShowNotificationClicked(itemId: 1)
            }
            ForEach(viewModel.state.items.compactMap { ($0 as? KotlinInt)?.intValue }, id: \.self) { itemId in
                Button("Open item \(itemId)") {
                    viewModel.onItemClicked(itemId: Int32(itemId))
                }
            }
        }
    }
}
