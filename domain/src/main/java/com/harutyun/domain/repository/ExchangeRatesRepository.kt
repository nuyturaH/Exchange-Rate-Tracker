package com.harutyun.domain.repository

import com.harutyun.domain.model.Currency
import com.harutyun.domain.model.NetworkResponse


interface ExchangeRatesRepository {

    suspend fun getExchangeRatesByBaseCurrencyName(baseCurrencyName: String): NetworkResponse<List<Currency>>
}
