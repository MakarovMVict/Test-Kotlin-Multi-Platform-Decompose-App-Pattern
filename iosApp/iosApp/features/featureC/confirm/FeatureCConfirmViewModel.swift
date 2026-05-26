import shared

extension FeatureCConfirmViewModel {
    var state: FeatureCConfirmUiState {
        uiState.value as! FeatureCConfirmUiState
    }
}
