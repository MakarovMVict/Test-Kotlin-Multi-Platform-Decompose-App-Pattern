import shared

extension FeatureBListViewModel {
    var state: FeatureBListUiState {
        uiState.value as! FeatureBListUiState
    }
}
