package com.harutyun.data.mappers

import com.harutyun.data.remote.entity.ExchangeRatesResponseNetworkEntity
import com.harutyun.domain.model.ExchangeRates

class ExchangeRatesMapper {

    fun mapToDomain(input: ExchangeRatesResponseNetworkEntity): ExchangeRates {
        return ExchangeRates(baseCurrencyName = input.base, rates = input.rates)
    }
}
