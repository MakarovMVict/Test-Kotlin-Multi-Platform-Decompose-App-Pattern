import SwiftUI
import shared

struct FeatureCListView: View {
    let viewModel: FeatureCListViewModel

    var body: some View {
        VStack(spacing: 16) {
            Text(viewModel.state.title)
            ForEach(viewModel.state.items.compactMap { ($0 as? KotlinInt)?.intValue }, id: \.self) { itemId in
                Button("Open item \(itemId)") {
                    viewModel.onItemClicked(itemId: Int32(itemId))
                }
            }
        }
    }
}
