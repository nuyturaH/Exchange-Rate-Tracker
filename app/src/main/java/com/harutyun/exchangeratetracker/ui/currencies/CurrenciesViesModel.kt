package com.harutyun.exchangeratetracker.ui.currencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harutyun.domain.model.NetworkResponse
import com.harutyun.domain.usecase.GetExchangeRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesViesModel @Inject constructor(private val getExchangeRatesUseCase: GetExchangeRatesUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow<CurrenciesUiState>(CurrenciesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getExchangeRates()
    }


    fun getExchangeRates(base: String = uiState.value.baseCurrencyName) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { CurrenciesUiState.Loading }

            when (val rates = getExchangeRatesUseCase(base)) {
                is NetworkResponse.Success -> _uiState.update { CurrenciesUiState.Success(rates.data) }
                is NetworkResponse.Failure -> _uiState.update { CurrenciesUiState.Error(rates.errorMessage) }
            }
        }

    }
}