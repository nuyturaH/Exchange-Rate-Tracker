package com.harutyun.domain.usecase

import com.harutyun.domain.model.CurrencyPair
import com.harutyun.domain.repository.ExchangeRatesRepository

class RemoveFavoriteUseCase(private val exchangeRatesRepository: ExchangeRatesRepository) {

    suspend operator fun invoke(currencyPair: CurrencyPair) {
        return exchangeRatesRepository.removeFavorite(currencyPair)
    }
}
