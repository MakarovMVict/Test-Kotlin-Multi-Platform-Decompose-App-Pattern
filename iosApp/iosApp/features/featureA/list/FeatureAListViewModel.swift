import shared

extension FeatureAListViewModel {
    var state: FeatureAListUiState {
        uiState.value as! FeatureAListUiState
    }
}
