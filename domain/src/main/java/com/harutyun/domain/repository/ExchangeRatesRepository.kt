package com.harutyun.domain.repository

import com.harutyun.domain.model.ExchangeRates


interface ExchangeRatesRepository {

    suspend fun getExchangeRatesByBaseCurrencyName(baseCurrencyName: String): ExchangeRates
}
