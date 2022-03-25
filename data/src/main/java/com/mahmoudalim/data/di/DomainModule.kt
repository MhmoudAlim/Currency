package com.mahmoudalim.data.di

import com.mahmoudalim.data.repo.CurrencyRepository
import com.mahmoudalim.data.usecase.CurrencyUseCases
import com.mahmoudalim.data.usecase.FetchConversionsHistoryListUseCase
import com.mahmoudalim.data.usecase.GetAllRatesUseCase
import com.mahmoudalim.data.usecase.InsertConversionRecordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Created by Mahmoud Alim on 24/03/2022.
 */

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @ViewModelScoped
    @Provides
    fun provideConverterUseCases(
        repository: CurrencyRepository,
    ): CurrencyUseCases {
        return CurrencyUseCases(
            insertConversionRecordUseCase = InsertConversionRecordUseCase(repository),
            fetchConversionsHistoryUseCase = FetchConversionsHistoryListUseCase(repository),
            getAllRatesUseCase = GetAllRatesUseCase(repository)
        )
    }
}