package com.harutyun.domain.usecase

import com.harutyun.domain.model.Currency
import com.harutyun.domain.model.NetworkResponse
import com.harutyun.domain.repository.ExchangeRatesRepository

class GetExchangeRatesUseCase(private val exchangeRatesRepository: ExchangeRatesRepository) {

    suspend operator fun invoke(baseCurrencyName: String): NetworkResponse<List<Currency>> {
        return exchangeRatesRepository.getExchangeRatesByBaseCurrencyName(baseCurrencyName)
    }
}
