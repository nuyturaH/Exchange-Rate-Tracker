package com.harutyun.exchangeratetracker.ui.favorites

import com.harutyun.domain.model.Currency


sealed class FavoritesUiState {
    data class Success(val favoritesList: List<Currency> = emptyList()) : FavoritesUiState()
    data object Loading : FavoritesUiState()
    data class Error(val errorMessage: String) : FavoritesUiState()

}