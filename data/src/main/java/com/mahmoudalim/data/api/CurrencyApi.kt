package com.mahmoudalim.data.api

import com.mahmoudalim.data.models.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Mahmoud Alim on 19/03/2022.
 */
interface CurrencyApi {

    @GET("api/latest")
    suspend fun getRates(
        @Query("access_key") access_key: String,
        @Query("base") base: String
    ): Response<CurrencyResponse>

}