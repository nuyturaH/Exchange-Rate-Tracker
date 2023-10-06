package com.harutyun.exchangeratetracker.ui.currencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harutyun.domain.model.Currency
import com.harutyun.domain.model.NetworkResponse
import com.harutyun.domain.model.SortOption
import com.harutyun.domain.usecase.GetExchangeRatesByBaseCurrencyUseCase
import com.harutyun.domain.usecase.SortExchangeRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CurrenciesViesModel @Inject constructor(
    private val getExchangeRatesByBaseCurrencyUseCase: GetExchangeRatesByBaseCurrencyUseCase,
    private val sortExchangeRatesUseCase: SortExchangeRatesUseCase
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(CurrenciesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getExchangeRates()
    }

    fun sortExchangeRatesList(sortOption: SortOption) {
        viewModelScope.launch {
            val uiStateValue = uiState.value.currencyListUiState
            if (uiStateValue is CurrencyListUiState.Success) {
                val list = uiStateValue.currencyList

                val sortedList = sortExchangeRates(sortOption, list)
                _uiState.update {
                    it.copy(
                        sortOption = sortOption,
                        currencyListUiState = CurrencyListUiState.Success(
                            sortedList
                        )
                    )
                }
            }
        }
    }

    private suspend fun sortExchangeRates(
        sortOption: SortOption,
        exchangeRates: List<Currency>
    ): List<Currency> = withContext(Dispatchers.Default) {
        SortExchangeRatesUseCase()(sortOption, exchangeRates)
    }



    fun getExchangeRates(base: String = uiState.value.baseCurrencyName) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    currencyListUiState = CurrencyListUiState.Loading,
                    baseCurrencyName = base
                )
            }
            when (val rates = getExchangeRatesByBaseCurrencyUseCase(base)) {
                is NetworkResponse.Success -> {
                    val currencyNames = rates.data.map { currency -> currency.name }

                    val sortedList =
                        sortExchangeRatesUseCase(uiState.value.sortOption, rates.data)
                    _uiState.update {
                        it.copy(
                            currenciesDropDown = currencyNames,
                            currencyListUiState = CurrencyListUiState.Success(
                                sortedList
                            )
                        )
                    }

                }

                is NetworkResponse.Failure -> _uiState.update {
                    it.copy(currencyListUiState = CurrencyListUiState.Error(rates.errorMessage))
                }
            }
        }

    }
}