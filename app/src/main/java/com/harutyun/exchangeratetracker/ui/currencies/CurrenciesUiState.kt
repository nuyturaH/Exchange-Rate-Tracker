package com.harutyun.exchangeratetracker.ui.currencies

import com.harutyun.domain.model.Currency

sealed class CurrenciesUiState {
    val baseCurrencyName: String = "USD"

    data class Success(val rates: List<Currency> = listOf()) : CurrenciesUiState()
    data object Loading : CurrenciesUiState()
    data object NoData : CurrenciesUiState()
    data class Error(val errorMessage: String) : CurrenciesUiState()
}