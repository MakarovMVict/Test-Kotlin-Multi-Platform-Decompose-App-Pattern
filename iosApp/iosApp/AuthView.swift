import SwiftUI
import shared

struct AuthView: View {
    let viewModel: AuthViewModel

    var body: some View {
        VStack(spacing: 16) {
            let state: AuthUiState = viewModel.uiState.value as! AuthUiState
            Text(state.title)
            Button(state.loginButtonTitle) {
                viewModel.onLoginClicked()
            }
        }
    }
}
