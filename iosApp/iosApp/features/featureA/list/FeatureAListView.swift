import SwiftUI
import shared

public struct FeatureAListView: View {
    public let viewModel: FeatureAListViewModel

    public init(viewModel: FeatureAListViewModel) {
        self.viewModel = viewModel
    }

    public var body: some View {
        VStack(spacing: 16) {
            Text(viewModel.state.title)
            ForEach(viewModel.state.items.compactMap { ($0 as? KotlinInt)?.intValue }, id: \.self) { itemId in
                Button("Open A item \(itemId)") {
                    viewModel.onItemClicked(itemId: Int32(itemId))
                }
            }
        }
    }
}
