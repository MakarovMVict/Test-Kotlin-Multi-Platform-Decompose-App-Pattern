import SwiftUI
import shared

struct FeatureCConfirmView: View {
    let viewModel: IosFeatureCConfirmScreenViewModel

    var body: some View {
        VStack(spacing: 16) {
            Text(viewModel.title)
            Button("Done") {
                viewModel.onDoneClicked()
            }
            .disabled(!viewModel.canComplete)
            Button("Back") {
                viewModel.onBackClicked()
            }
        }
    }
}
