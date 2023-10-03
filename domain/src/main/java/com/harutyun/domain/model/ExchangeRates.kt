package com.harutyun.domain.model

data class ExchangeRates(
    val baseCurrencyName: String,
    val rates: Map<String, Double> = LinkedHashMap()
)
