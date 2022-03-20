package com.mahmoudalim.data.source.remote

import com.mahmoudalim.core.utils.AppResponse
import com.mahmoudalim.data.api.CurrencyApi
import com.mahmoudalim.data.models.CurrencyResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Mahmoud Alim on 19/03/2022.
 */
class DefaultCurrencyRemoteDataSrc @Inject constructor(
    private val api: CurrencyApi
) : CurrencyRemoteDataSrc {

    override suspend fun getRates(base: String, accessKey: String): AppResponse<CurrencyResponse> {
        //TODO: make app response reusable
        return try {
            val response = api.getRates(base = base, access_key = accessKey)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                AppResponse.Success(result)
            } else {
                AppResponse.ServerError(response.message())
            }

        } catch (e: HttpException) {
            AppResponse.ServerError(e.message ?: "Aa error occurred")

        } catch (e: IOException) {
            AppResponse.NetworkError(e.message ?: "Aa error occurred")
        }
    }
}