package com.harutyun.domain.usecase

import com.harutyun.domain.model.Currency
import com.harutyun.domain.model.SortOption

class SortExchangeRatesUseCase {

    operator fun invoke(sortOption: SortOption, exchangeRates: List<Currency>): List<Currency> {

        return when (sortOption) {
            SortOption.CODE_A_Z -> exchangeRates.sortedBy { it.name }
            SortOption.CODE_Z_A -> exchangeRates.sortedByDescending { it.name }
            SortOption.QUOTE_ASC -> exchangeRates.sortedBy { it.rate }
            SortOption.QUOTE_DESC -> exchangeRates.sortedByDescending { it.rate }
        }
    }
}
