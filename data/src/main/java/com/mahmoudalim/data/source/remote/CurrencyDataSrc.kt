package com.mahmoudalim.data.source.remote

import com.mahmoudalim.core.utils.AppResponse
import com.mahmoudalim.data.models.CurrencyResponse

/**
 * Created by Mahmoud Alim on 19/03/2022.
 */
interface CurrencyRemoteDataSrc {

    suspend fun getRates(base: String, accessKey: String): AppResponse<CurrencyResponse>

}