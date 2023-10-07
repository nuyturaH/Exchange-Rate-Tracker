package com.harutyun.domain.usecase

import com.harutyun.domain.model.CurrencyPair
import com.harutyun.domain.repository.ExchangeRatesRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(private val exchangeRatesRepository: ExchangeRatesRepository) {

    operator fun invoke(): Flow<List<CurrencyPair>> {
        return exchangeRatesRepository.getFavorites()
    }
}
