package com.harutyun.exchangeratetracker.di

import com.harutyun.domain.repository.ExchangeRatesRepository
import com.harutyun.domain.usecase.AddFavoriteUseCase
import com.harutyun.domain.usecase.GetExchangeRatesByBaseCurrencyUseCase
import com.harutyun.domain.usecase.RemoveFavoriteUseCase
import com.harutyun.domain.usecase.SortExchangeRatesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetExchangeRatesUseCase(exchangeRatesRepository: ExchangeRatesRepository): GetExchangeRatesByBaseCurrencyUseCase {
        return GetExchangeRatesByBaseCurrencyUseCase(exchangeRatesRepository)
    }

    @Provides
    fun provideSortExchangeRatesUseCase(): SortExchangeRatesUseCase {
        return SortExchangeRatesUseCase()
    }

    @Provides
    fun provideAddFavoriteUseCase(exchangeRatesRepository: ExchangeRatesRepository): AddFavoriteUseCase {
        return AddFavoriteUseCase(exchangeRatesRepository)
    }

    @Provides
    fun provideRemoveFavoriteUseCase(exchangeRatesRepository: ExchangeRatesRepository): RemoveFavoriteUseCase {
        return RemoveFavoriteUseCase(exchangeRatesRepository)
    }

}