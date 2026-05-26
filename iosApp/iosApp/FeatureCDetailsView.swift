import SwiftUI
import shared

struct FeatureCDetailsView: View {
    let viewModel: FeatureCDetailsViewModel

    var body: some View {
        VStack(spacing: 16) {
            let state: FeatureCDetailsUiState = viewModel.uiState.value as! FeatureCDetailsUiState
            Text(state.title)
            Button("Go to confirm") {
                viewModel.onOpenConfirmClicked()
            }
            Button("Back") {
                viewModel.onBackClicked()
            }
        }
    }
}
