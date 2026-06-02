import shared

extension AuthViewModel {
    var state: AuthUiState {
        uiState.value as! AuthUiState
    }
}
