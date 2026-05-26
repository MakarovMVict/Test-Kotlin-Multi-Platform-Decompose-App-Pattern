import SwiftUI
import shared

struct FeatureCConfirmView: View {
    let viewModel: FeatureCConfirmViewModel

    var body: some View {
        VStack(spacing: 16) {
            Text(viewModel.state.title)
            Button("Done") {
                viewModel.onDoneClicked()
            }
            .disabled(!viewModel.state.canComplete)
            Button("Back") {
                viewModel.onBackClicked()
            }
        }
    }
}
