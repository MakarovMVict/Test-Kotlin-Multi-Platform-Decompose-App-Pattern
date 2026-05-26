import shared

extension FeatureCDetailsViewModel {
    var state: FeatureCDetailsUiState {
        uiState.value as! FeatureCDetailsUiState
    }
}
