package com.mahmoudalim.data.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.mahmoudalim.core.date.AppDate
import com.mahmoudalim.data.pref.AppPreferences
import com.mahmoudalim.data.pref.DefaultAppPreferences
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
import javax.inject.Singleton

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

    @ViewModelScoped
    @Provides
    fun provideAppDate(): AppDate = AppDate

    @Provides
    @ViewModelScoped
    fun provideSharedPreferences(
        app: Application
    ): SharedPreferences {
        return app.getSharedPreferences(AppPreferences.PREF_TAG, Context.MODE_PRIVATE)
    }

    @Provides
    @ViewModelScoped
    fun provideAppPreferences(sharedPreferences: SharedPreferences): AppPreferences {
        return DefaultAppPreferences(sharedPreferences)
    }

}