import SwiftUI
import shared

struct FeatureBDetailsView: View {
    let viewModel: FeatureBDetailsViewModel

    var body: some View {
        VStack(spacing: 16) {
            let state: FeatureBDetailsUiState = viewModel.uiState.value as! FeatureBDetailsUiState
            Text(state.title)
            Button("Back") {
                viewModel.onBackClicked()
            }
        }
    }
}
