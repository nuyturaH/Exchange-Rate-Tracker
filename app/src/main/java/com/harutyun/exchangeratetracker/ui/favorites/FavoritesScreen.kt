package com.harutyun.exchangeratetracker.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.harutyun.domain.model.Currency
import com.harutyun.exchangeratetracker.R
import com.harutyun.exchangeratetracker.ui.components.AppBar
import com.harutyun.exchangeratetracker.ui.currencies.Currencies
import com.harutyun.exchangeratetracker.ui.currencies.CurrenciesShimmer
import com.harutyun.exchangeratetracker.ui.currencies.ErrorScreen

@Composable
fun FavoritesScreen(favoritesViesModel: FavoritesViesModel = hiltViewModel()) {
    val uiState by favoritesViesModel.uiState.collectAsStateWithLifecycle()

    FavoritesContent(
        uiState = uiState,
        onItemFavoriteClicked = {currency, isFavorite -> if (!isFavorite) favoritesViesModel.removeFromFavorites(currency) }
    )
}

@Composable
private fun FavoritesContent(
    uiState: FavoritesUiState,
    onItemFavoriteClicked: (Currency, Boolean) -> Unit
) {
    Scaffold(topBar = {
        AppBar(title = stringResource(R.string.favorites))
    }) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {

            when (uiState) {
                is FavoritesUiState.Loading -> CurrenciesShimmer()
                is FavoritesUiState.Success -> {
                    Currencies(
                        items = uiState.favoritesList,
                        onItemFavoriteClicked = {currency, isFavorite -> onItemFavoriteClicked(currency , isFavorite) })
                }
                is FavoritesUiState.Error -> ErrorScreen(message = uiState.errorMessage)
            }
        }
    }
}


