import SwiftUI
import shared

struct FeatureAListView: View {
    let viewModel: IosFeatureAListScreenViewModel

    var body: some View {
        VStack(spacing: 16) {
            Text(viewModel.title)
            ForEach(0..<Int(viewModel.itemCount()), id: \.self) { index in
                let itemId = Int(viewModel.itemAt(index: Int32(index)))
                Button("Open A item \(itemId)") {
                    viewModel.onItemClicked(itemId: Int32(itemId))
                }
            }
        }
    }
}
