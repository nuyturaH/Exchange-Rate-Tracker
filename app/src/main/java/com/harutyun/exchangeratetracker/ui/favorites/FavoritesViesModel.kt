package com.harutyun.exchangeratetracker.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harutyun.domain.model.Currency
import com.harutyun.domain.model.CurrencyPair
import com.harutyun.domain.usecase.GetFavoritesUseCase
import com.harutyun.domain.usecase.RemoveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViesModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) :
    ViewModel() {

    private val _uiState = MutableStateFlow<FavoritesUiState>(FavoritesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        favorites()
    }


    fun favorites() {
        viewModelScope.launch {
            getFavoritesUseCase().collect { list ->
                if (list.isEmpty()) {
                    _uiState.value = FavoritesUiState.Error("No favorites added")
                    return@collect
                }


                val currencies = list.map { x ->
                    Currency(
                        name = x.baseCurrencyName + "/" + x.targetCurrencyName,
                        rate = x.rate,
                        isFavorite = true
                    )
                }
                _uiState.value = FavoritesUiState.Success(currencies)
            }
        }
    }

    fun removeFromFavorites(target: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            val pairText = target.name.split("/")

            val currencyPair = CurrencyPair(
                baseCurrencyName = pairText[0],
                targetCurrencyName = pairText[1],
                rate = target.rate
            )
            removeFavoriteUseCase(currencyPair)
        }
    }


}