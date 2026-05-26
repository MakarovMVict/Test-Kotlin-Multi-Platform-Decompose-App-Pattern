import shared

extension FeatureCListViewModel {
    var state: FeatureCListUiState {
        uiState.value as! FeatureCListUiState
    }
}
