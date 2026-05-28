import SwiftUI
import shared

public struct AuthView: View {
    public let viewModel: AuthViewModel

    public init(viewModel: AuthViewModel) {
        self.viewModel = viewModel
    }

    public var body: some View {
        VStack(spacing: 16) {
            Text(viewModel.state.title)
            Button(viewModel.state.loginButtonTitle) {
                viewModel.onLoginClicked()
            }
        }
    }
}
