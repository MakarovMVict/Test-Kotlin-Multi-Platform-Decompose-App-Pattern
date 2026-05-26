import SwiftUI
import shared

struct FeatureBListView: View {
    let viewModel: FeatureBListViewModel

    var body: some View {
        VStack(spacing: 16) {
            Text(viewModel.state.title)
            ForEach(viewModel.state.items.compactMap { ($0 as? KotlinInt)?.intValue }, id: \.self) { itemId in
                Button("Open B item \(itemId)") {
                    viewModel.onItemClicked(itemId: Int32(itemId))
                }
            }
        }
    }
}
