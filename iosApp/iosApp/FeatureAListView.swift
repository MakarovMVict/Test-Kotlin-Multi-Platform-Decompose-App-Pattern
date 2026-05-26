import SwiftUI
import shared

struct FeatureAListView: View {
    let viewModel: FeatureAListViewModel

    var body: some View {
        VStack(spacing: 16) {
            let state: FeatureAListUiState = viewModel.uiState.value as! FeatureAListUiState
            Text(state.title)
            ForEach(state.items.compactMap { ($0 as? KotlinInt)?.intValue }, id: \.self) { itemId in
                Button("Open A item \(itemId)") {
                    viewModel.onItemClicked(itemId: Int32(itemId))
                }
            }
        }
    }
}
