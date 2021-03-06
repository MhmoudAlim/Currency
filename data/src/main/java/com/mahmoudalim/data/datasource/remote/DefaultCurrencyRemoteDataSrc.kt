package com.mahmoudalim.data.datasource.remote

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

    override suspend fun getRates(accessKey: String): AppResponse<CurrencyResponse> {
        //TODO: make app response reusable
        return try {
            val response = api.getRates(access_key = accessKey)
            val result = response.body()
            if (response.isSuccessful && result != null && result.success) {
                AppResponse.Success(result)
            } else {
                AppResponse.ServerError(result?.error?.info ?: "error occurred")
            }

        } catch (e: HttpException) {
            AppResponse.ServerError(e.message ?: "error occurred")

        } catch (e: IOException) {
            AppResponse.NetworkError(e.message ?: "error occurred")
        }
    }
}