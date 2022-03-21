package com.mahmoudalim.data.repo

import com.mahmoudalim.core.utils.AppResponse
import com.mahmoudalim.data.models.CurrencyResponse
import com.mahmoudalim.data.datasource.local.CurrencyLocalDataSrc
import com.mahmoudalim.data.datasource.remote.CurrencyRemoteDataSrc
import javax.inject.Inject

/**
 * Created by Mahmoud Alim on 19/03/2022.
 */
class DefaultCurrencyRepo @Inject constructor(
    private val remoteDataSrc: CurrencyRemoteDataSrc,
    private val localDataSrc: CurrencyLocalDataSrc
) : CurrencyRepository {

    override suspend fun getRates(base: String, key: String):
            AppResponse<CurrencyResponse> {
//        /when(enum) retuen datsrc.get
        return remoteDataSrc.getRates(base, key)
    }
}