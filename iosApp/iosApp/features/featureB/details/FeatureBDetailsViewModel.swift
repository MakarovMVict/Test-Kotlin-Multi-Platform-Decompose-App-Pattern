import shared

extension FeatureBDetailsViewModel {
    var state: FeatureBDetailsUiState {
        uiState.value as! FeatureBDetailsUiState
    }
}
