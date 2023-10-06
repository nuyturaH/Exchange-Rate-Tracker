package com.harutyun.exchangeratetracker.ui.currencies

import com.harutyun.domain.model.Currency
import com.harutyun.domain.model.SortOption


data class CurrenciesUiState(
    val baseCurrencyName: String = DEFAULT_BASE_CURRENCY,
    val currenciesDropDown: List<String> = emptyList(),
    val sortOption: SortOption = SortOption.CODE_A_Z,
    val currencyListUiState: CurrencyListUiState = CurrencyListUiState.Loading
)

sealed class CurrencyListUiState {
    data class Success(
        val currencyList: List<Currency> = emptyList()) : CurrencyListUiState()
    data object Loading : CurrencyListUiState()
    data class Error(val errorMessage: String) : CurrencyListUiState()
}

const val DEFAULT_BASE_CURRENCY = "USD"