import SwiftUI
import shared

struct FeatureCConfirmView: View {
    let viewModel: FeatureCConfirmViewModel

    var body: some View {
        VStack(spacing: 16) {
            let state: FeatureCConfirmUiState = viewModel.uiState.value as! FeatureCConfirmUiState
            Text(state.title)
            Button("Done") {
                viewModel.onDoneClicked()
            }
            .disabled(!state.canComplete)
            Button("Back") {
                viewModel.onBackClicked()
            }
        }
    }
}
