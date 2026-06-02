import shared

extension FeatureADetailsViewModel {
    var state: FeatureADetailsUiState {
        uiState.value as! FeatureADetailsUiState
    }
}
