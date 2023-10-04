package com.harutyun.data.mappers

import com.harutyun.data.remote.entity.ExchangeRatesResponseNetworkEntity
import com.harutyun.domain.model.Currency

class ExchangeRatesMapper {

    fun mapToDomain(input: ExchangeRatesResponseNetworkEntity): List<Currency> {
        val currencies = mutableListOf<Currency>()
        for ((currencyCode, exchangeRate) in input.rates) {
            val currency = Currency(
                name = currencyCode,
                rate = exchangeRate,
                isFavorite = false
            )
            currencies.add(currency)
        }
        return currencies
    }
}
