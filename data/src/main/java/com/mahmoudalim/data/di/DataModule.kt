package com.mahmoudalim.data.di

import com.google.gson.GsonBuilder
import com.mahmoudalim.core.utils.Const.BASE_URL
import com.mahmoudalim.data.api.CurrencyApi
import com.mahmoudalim.data.repo.CurrencyRepository
import com.mahmoudalim.data.repo.DefaultCurrencyRepo
import com.mahmoudalim.data.source.local.DefaultCurrencyLocalDataSrc
import com.mahmoudalim.data.source.remote.DefaultCurrencyRemoteDataSrc
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
            .baseUrl(BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientBuilder.build())
            .build()
            .create(CurrencyApi::class.java)
    }


    @Singleton
    @Provides
    fun provideCurrencyRepository(
        defaultCurrencyLocalDataSrc: DefaultCurrencyLocalDataSrc,
        defaultCurrencyRemoteDataSrc: DefaultCurrencyRemoteDataSrc
    ): CurrencyRepository =
        DefaultCurrencyRepo(defaultCurrencyRemoteDataSrc, defaultCurrencyLocalDataSrc)


    @Singleton
    @Provides
    fun provideCurrencyLocalDataSrc(): DefaultCurrencyLocalDataSrc =
        DefaultCurrencyLocalDataSrc()


    @Singleton
    @Provides
    fun provideCurrencyRemoteDataSrc(api: CurrencyApi): DefaultCurrencyRemoteDataSrc =
        DefaultCurrencyRemoteDataSrc(api)

}