package com.harutyun.domain.usecase

import com.harutyun.domain.model.ExchangeRates
import com.harutyun.domain.model.NetworkResponse
import com.harutyun.domain.repository.ExchangeRatesRepository

class GetExchangeRatesUseCase(private val exchangeRatesRepository: ExchangeRatesRepository) {

    suspend operator fun invoke(baseCurrencyName: String): NetworkResponse<ExchangeRates> {
        return exchangeRatesRepository.getExchangeRatesByBaseCurrencyName(baseCurrencyName)
    }
}
