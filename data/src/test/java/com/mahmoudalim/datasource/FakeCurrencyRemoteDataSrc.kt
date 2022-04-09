package com.mahmoudalim.datasource

import com.mahmoudalim.core.date.AppDate
import com.mahmoudalim.core.utils.AppResponse
import com.mahmoudalim.data.datasource.remote.CurrencyRemoteDataSrc
import com.mahmoudalim.data.models.CurrencyResponse
import com.mahmoudalim.data.models.Rates

/**
 * Created by Mahmoud Alim on 02/04/2022.
 */

class FakeCurrencyRemoteDataSrc : CurrencyRemoteDataSrc {

    override suspend fun getRates(accessKey: String): AppResponse<CurrencyResponse> {
        return AppResponse.Success(
            CurrencyResponse(
                "EUR",
                "",
                Rates(),
                true,
                111,
                null
            )
        )
    }
}