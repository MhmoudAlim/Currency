package com.mahmoudalim.data.repo

import com.mahmoudalim.core.utils.AppResponse
import com.mahmoudalim.data.models.CurrencyResponse

/**
 * Created by Mahmoud Alim on 19/03/2022.
 */

interface CurrencyRepository {

    suspend fun getRates(base: String, key : String) : AppResponse<CurrencyResponse>
}