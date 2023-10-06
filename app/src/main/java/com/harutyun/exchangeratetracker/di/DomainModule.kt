package com.harutyun.exchangeratetracker.di

import com.harutyun.domain.repository.ExchangeRatesRepository
import com.harutyun.domain.usecase.GetExchangeRatesByBaseCurrencyUseCase
import com.harutyun.domain.usecase.SortExchangeRatesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetExchangeRatesUseCase(userRepository: ExchangeRatesRepository): GetExchangeRatesByBaseCurrencyUseCase {
        return GetExchangeRatesByBaseCurrencyUseCase(userRepository)
    }

    @Provides
    fun provideSortExchangeRatesUseCase(): SortExchangeRatesUseCase {
        return SortExchangeRatesUseCase()
    }


}