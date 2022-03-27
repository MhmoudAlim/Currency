package com.mahmoudalim.data.di

import android.content.Context
import androidx.room.Room
import com.mahmoudalim.core.utils.Const.DATABASE_NAME
import com.mahmoudalim.data.BuildConfig
import com.mahmoudalim.data.api.CurrencyApi
import com.mahmoudalim.data.database.CurrencyDatabase
import com.mahmoudalim.data.database.HistoryDao
import com.mahmoudalim.data.datasource.local.CurrencyLocalDataSrc
import com.mahmoudalim.data.datasource.local.DefaultCurrencyLocalDataSrc
import com.mahmoudalim.data.datasource.remote.CurrencyRemoteDataSrc
import com.mahmoudalim.data.datasource.remote.DefaultCurrencyRemoteDataSrc

import com.mahmoudalim.data.repo.CurrencyRepository
import com.mahmoudalim.data.repo.DefaultCurrencyRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Mahmoud Alim on 19/03/2022.
 */

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()


    @Singleton
    @Provides
    fun provideCurrencyApi(
        okHttpClientBuilder: OkHttpClient.Builder,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): CurrencyApi {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        okHttpClientBuilder.addNetworkInterceptor(httpLoggingInterceptor)
        okHttpClientBuilder.connectTimeout(1, TimeUnit.MINUTES)
        okHttpClientBuilder.readTimeout(1, TimeUnit.MINUTES)
        okHttpClientBuilder.retryOnConnectionFailure(false)
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientBuilder.build())
            .build()
            .create(CurrencyApi::class.java)
    }


    @Singleton
    @Provides
    fun provideCurrencyRepository(
        currencyLocalDataSrc: CurrencyLocalDataSrc,
        currencyRemoteDataSrc: CurrencyRemoteDataSrc
    ): CurrencyRepository =
        DefaultCurrencyRepo(currencyRemoteDataSrc, currencyLocalDataSrc)


    @Singleton
    @Provides
    fun provideCurrencyLocalDataSrc(historyDao: HistoryDao): CurrencyLocalDataSrc =
        DefaultCurrencyLocalDataSrc(historyDao)


    @Singleton
    @Provides
    fun provideCurrencyRemoteDataSrc(api: CurrencyApi): CurrencyRemoteDataSrc =
        DefaultCurrencyRemoteDataSrc(api)


    @Provides
    @Singleton
    fun provideCurrencyDataBase(@ApplicationContext context: Context): CurrencyDatabase =
        Room.databaseBuilder(context, CurrencyDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideHistoryDao(dataBase: CurrencyDatabase): HistoryDao = dataBase.historyDao()



}