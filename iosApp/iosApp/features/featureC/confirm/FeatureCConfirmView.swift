import SwiftUI
import shared

public struct FeatureCConfirmView: View {
    public let viewModel: FeatureCConfirmViewModel

    public init(viewModel: FeatureCConfirmViewModel) {
        self.viewModel = viewModel
    }

    public var body: some View {
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
