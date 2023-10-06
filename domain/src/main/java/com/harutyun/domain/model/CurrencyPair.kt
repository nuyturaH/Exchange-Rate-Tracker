package com.harutyun.domain.model

data class CurrencyPair(
    val id: Int = 0,
    val baseCurrencyName: String,
    val targetCurrencyName: String,
    val rate: Double
)
