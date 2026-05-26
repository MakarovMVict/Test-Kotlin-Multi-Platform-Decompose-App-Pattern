import SwiftUI
import shared

struct FeatureBListView: View {
    let viewModel: IosFeatureBListScreenViewModel

    var body: some View {
        VStack(spacing: 16) {
            Text(viewModel.title)
            ForEach(0..<Int(viewModel.itemCount()), id: \.self) { index in
                let itemId = Int(viewModel.itemAt(index: Int32(index)))
                Button("Open B item \(itemId)") {
                    viewModel.onItemClicked(itemId: Int32(itemId))
                }
            }
        }
    }
}
