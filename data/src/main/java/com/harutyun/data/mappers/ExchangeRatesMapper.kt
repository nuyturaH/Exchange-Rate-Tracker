package com.harutyun.data.mappers

import com.harutyun.data.local.entity.CurrencyPairEntity
import com.harutyun.data.remote.entity.ExchangeRatesResponseNetworkEntity
import com.harutyun.domain.model.Currency
import com.harutyun.domain.model.CurrencyPair

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

    fun mapCurrencyPairEntityListToDomain(entityList: List<CurrencyPairEntity>): List<CurrencyPair> {
        return entityList.map { mapCurrencyPairToDomain(it) }
    }

    fun mapCurrencyPairToDomain(input: CurrencyPairEntity): CurrencyPair {
        return CurrencyPair(
            id = input.id,
            baseCurrencyName = input.baseCurrencyName,
            targetCurrencyName = input.targetCurrencyName,
            rate = input.rate
        )
    }

    fun mapCurrencyPairToData(input: CurrencyPair): CurrencyPairEntity {
        return CurrencyPairEntity(
            id = input.id,
            baseCurrencyName = input.baseCurrencyName,
            targetCurrencyName = input.targetCurrencyName,
            rate = input.rate
        )
    }
}
